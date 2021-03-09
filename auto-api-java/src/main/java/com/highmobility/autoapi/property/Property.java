/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi.property;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.autoapi.value.Availability;
import com.highmobility.value.Bytes;

import javax.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static com.highmobility.autoapi.AutoApiLogger.getLogger;

/**
 * Property is a representation of some AutoAPI data. It consists of 4 optional components: data, unit
 * timestamp, failure
 *
 * @param <V> The value type. Eg Integer, CheckControlMessage, Duration
 */
public class Property<V> extends Bytes {
    /*
    Property is created/updated in 3 places:

    1:
    Incoming command:
    Base Command parses the components but doesn't know the type. Sub command updates its
    properties with base components.

    base command:
    properties[i] = new Property(bytes);
    findComponents(bytes)

    sub command update is needed because base does not know the property type:
     >> field.update(property) > copy the components to sub property, replace the base property
      with parsed one

    2:
    User creates the property herself and passes it to builder.
    IntegerProperty is updated later in the builder to set length/sign
    * new Property(GasFlapState) > update(GasFlapState, timestamp, null) >> goto 3
    or
    * new Property(null, null, failure) > update(null, null, failure) >> goto 3

    3:
    in control commands the object is created as field, but updated with real value (eg
    GasFlapState).
    Property<GasFlapState> field = new Property(GasFlapState.class, IDENTIFIER)

    ControlGasFlap((GasFlapState, timestamp, failure) {
        field.update(GasFlapState, timestamp, failure)
         > bytes = setBytes(GasFlapState, timestamp, failure);
         > findComponents()
    }
     */

    protected static final byte[] unknownBytes = new byte[]{0x00, 0x00, 0x00};

    @Nullable
    protected PropertyComponentValue<V> value;
    @Nullable
    protected PropertyComponentTimestamp timestamp;
    @Nullable
    protected PropertyComponentFailure failure;
    @Nullable
    protected PropertyComponentAvailability availability;

    protected List<PropertyComponent> components;

    private Class<V> valueClass = null;

    public byte getPropertyIdentifier() {
        return bytes[0];
    }

    /**
     * @return The property value.
     */
    @Nullable
    public V getValue() {
        return value != null ? value.getValue() : null;
    }

    /**
     * @return All of the components.
     */
    public List<PropertyComponent> getComponents() {
        return components;
    }

    /**
     * @param identifier The component identifier.
     * @return The component with the given identifier.
     */
    @Nullable
    public PropertyComponent getComponent(byte identifier) {
        for (int i = 0; i < components.size(); i++) {
            PropertyComponent component = components.get(i);
            if (component.getIdentifier() == identifier) return component;
        }
        return null;
    }

    /**
     * @return The value component.
     */
    @Nullable
    public PropertyComponentValue getValueComponent() {
        return value;
    }

    /**
     * @return The timestamp of the property.
     */
    @Nullable
    public Calendar getTimestamp() {
        if (timestamp == null) return null;
        return timestamp.getCalendar();
    }

    @Nullable
    PropertyComponentTimestamp getTimestampComponent() {
        return timestamp;
    }

    /**
     * @return The availability of the property.
     */
    @Nullable
    public Availability getAvailability() {
        return availability.getAvailability();
    }

    @Nullable
    PropertyComponentAvailability getAvailabilityComponent() {
        return availability;
    }

    /**
     * @return The failure of the property.
     */
    @Nullable
    public PropertyComponentFailure getFailureComponent() {
        return failure;
    }

    @Nullable
    Class<V> getValueClass() {
        return valueClass;
    }

    /**
     * @return The length of the property components(not including the header).
     */
    public int getPropertyLength() {
        return getLength() - 3;
    }

    // MARK: incoming ctor

    // cannot create Bytes ctor because it would overlap with some Bytes subclasses that need to use
    // the class ctor.
    public Property(byte[] bytes) {
        if (bytes == null || bytes.length == 0) bytes = unknownBytes;
        if (bytes.length < 3) bytes = Arrays.copyOf(bytes, 3);
        this.bytes = bytes;
        findComponents();
    }

    protected void findComponents() {
        ArrayList<PropertyComponent> builder = new ArrayList<>();

        for (int i = 3; i < bytes.length; i++) {
            int size = getUnsignedInt(bytes, i + 1, 2);
            byte componentIdentifier = bytes[i];
            Bytes componentBytes = getRange(i, i + 3 + size);

            try {
                switch (componentIdentifier) {
                    case 0x01:
                        value = new PropertyComponentValue<>(componentBytes, valueClass);
                        builder.add(value);
                        break;
                    case 0x02:
                        timestamp = new PropertyComponentTimestamp(componentBytes);
                        builder.add(timestamp);
                        break;
                    case 0x03:
                        failure = new PropertyComponentFailure(componentBytes);
                        builder.add(failure);
                        break;
                    case 0x05:
                        availability = new PropertyComponentAvailability(componentBytes);
                        builder.add(availability);
                        break;
                }
            } catch (Exception e) {
                builder.add(new PropertyComponent(componentBytes));
                printFailedToParse(e, componentBytes);
            }

            i += size + 2;
        }

        components = builder;
    }

    public Property(@Nullable V value) {
        update((byte) 0, value, null, null, null);
    }

    public Property update(Property p) throws CommandParseException {
        if (valueClass == null)
            throw new IllegalArgumentException("Initialise with a class to update.");

        this.bytes = p.getByteArray();
        this.value = p.value;

        if (this.value != null) {
            try {
                this.value.setClass(valueClass);
            } catch (Exception e) {
                getLogger().debug(String.format("Invalid bytes %s for property: %s\n%s", p, valueClass.getSimpleName(), e));
            }
        }

        this.components = p.components;
        this.timestamp = p.timestamp;
        this.failure = p.failure;
        this.availability = p.availability;

        return this;
    }

    // MARK: builder ctor

    public Property(byte identifier,
                    @Nullable V value,
                    @Nullable Calendar timestamp,
                    @Nullable PropertyComponentFailure failure,
                    @Nullable PropertyComponentAvailability availability) {
        update(identifier, value, timestamp, failure, availability);
    }

    // MARK: internal ctor

    public Property(int identifier, V value) {
        this((byte) identifier, value);
    }

    public Property(byte identifier, V value) {
        update(identifier, value, null, null, null);
    }

    public Property(Class<V> valueClass, int identifier) {
        this(valueClass, ((byte) identifier));
    }

    public Property(Class<V> valueClass, byte identifier) {
        this.bytes = new byte[]{identifier, 0, 0};
        this.valueClass = valueClass;
    }

    public Property(Class<V> valueClass, Property property) throws CommandParseException {
        this.valueClass = valueClass;
        if (property == null || property.getLength() == 0) this.bytes = unknownBytes;
        if (property.getLength() < 3) this.bytes = Arrays.copyOf(property.getByteArray(), 3);
        else this.bytes = property.getByteArray();
        update(property);
    }

    public Property update(V value) {
        return update(bytes[0], value, null, null, null);
    }

    public Property addValueComponent(Bytes valueComponentValue) {
        try {
            byte[] valueLength = Property.intToBytes(valueComponentValue.getLength(), 2);
            Bytes value = new Bytes(3 + valueComponentValue.getLength());
            value.set(0, (byte) 0x01);
            value.set(1, valueLength);
            value.set(3, valueComponentValue);
            this.value = new PropertyComponentValue<>(value, valueClass);
        } catch (CommandParseException e) {
            throw new IllegalArgumentException();
        }
        createBytesFromComponents(getPropertyIdentifier());
        return this;
    }

    private Property update(byte identifier,
                            @Nullable V value,
                            @Nullable Calendar timestamp,
                            @Nullable PropertyComponentFailure failure,
                            @Nullable PropertyComponentAvailability availability) {
        if (value != null && failure == null) this.value = new PropertyComponentValue<>(value);
        if (timestamp != null) this.timestamp = new PropertyComponentTimestamp(timestamp);
        if (failure != null) this.failure = failure;
        if (availability != null) this.availability = availability;

        createBytesFromComponents(identifier);

        return this;
    }

    protected void createBytesFromComponents(byte propertyIdentifier) {
        int componentBytesLength = 0;
        int componentsSize = 0;

        if (value != null) {
            componentBytesLength += value.getLength();
            componentsSize++;
        }

        if (timestamp != null) {
            componentBytesLength += timestamp.getLength();
            componentsSize++;
        }

        if (failure != null) {
            componentBytesLength += failure.getLength();
            componentsSize++;
        }

        if (availability != null) {
            componentBytesLength += availability.getLength();
            componentsSize++;
        }

        ArrayList<PropertyComponent> componentsBuilder = new ArrayList<>(componentsSize);
        Bytes builder = new Bytes(3 + componentBytesLength);

        builder.set(0, propertyIdentifier);
        builder.set(1, Property.intToBytes(componentBytesLength, 2));

        int pointer = 3;

        if (value != null) {
            builder.set(pointer, value);
            pointer += value.getLength();
            componentsBuilder.add(value);
        }

        if (timestamp != null) {
            builder.set(pointer, timestamp);
            pointer += timestamp.getLength();
            componentsBuilder.add(timestamp);
        }

        if (failure != null) {
            builder.set(pointer, failure);
            pointer += failure.getLength();
            componentsBuilder.add(failure);
        }

        if (availability != null) {
            builder.set(pointer, availability);
            componentsBuilder.add(availability);
        }

        bytes = builder.getByteArray();
        components = componentsBuilder;
    }

    /**
     * Set a new identifier for the property
     *
     * @param identifier The identifier.
     * @return Self.
     */
    public Property<V> setIdentifier(byte identifier) {
        bytes[0] = identifier;
        return this;
    }

    public Property<V> setIdentifier(int identifier) {
        setIdentifier((byte) identifier);
        return this;
    }

    public boolean isUniversalProperty() {
        byte propertyIdentifier = getPropertyIdentifier();
        return propertyIdentifier == Command.SIGNATURE_IDENTIFIER ||
                propertyIdentifier == Command.NONCE_IDENTIFIER ||
                propertyIdentifier == Command.TIMESTAMP_IDENTIFIER ||
                propertyIdentifier == Command.VIN_IDENTIFIER ||
                propertyIdentifier == Command.BRAND_IDENTIFIER;
    }

    public void printFailedToParse(Exception e, Bytes component) {
        String componentString = (component != null ? " | Component " + component : "");

        String exceptionString = (e != null ?
                (" | " + e.getClass().getSimpleName() + ": " + e.getMessage()) : "");

        getLogger().error(String.format("Failed to parse property: " + toString() + componentString + exceptionString + "\n%s", e));
    }

    // MARK: ctor helpers

    public static long getLong(byte[] b, int at) throws IllegalArgumentException {
        return getLong(b, at, 8);
    }

    public static long getLong(byte[] b, int at, int length) throws IllegalArgumentException {
        if (b.length - at < length) throw new IllegalArgumentException();

        long result = 0;
        for (int i = at; i < at + length; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }

        return result;
    }

    public static long getLong(byte[] b) throws IllegalArgumentException {
        return getLong(b, 0, b.length);
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static float getFloat(Bytes bytes) throws IllegalArgumentException {
        return getFloat(bytes.getByteArray());
    }

    public static float getFloat(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length < 4) throw new IllegalArgumentException();
        return Float.intBitsToFloat(getUnsignedInt(bytes, 0, 4));
    }

    public static float getFloat(byte[] bytes, int at) throws IllegalArgumentException {
        int intValue = getUnsignedInt(bytes, at, 4);
        return Float.intBitsToFloat(intValue);
    }

    public static float getFloat(Bytes bytes, int at) throws IllegalArgumentException {
        int intValue = getUnsignedInt(bytes.getByteArray(), at, 4);
        return Float.intBitsToFloat(intValue);
    }

    public static byte[] floatToBytes(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static double getDouble(Bytes bytes) throws IllegalArgumentException {
        return getDouble(bytes.getByteArray());
    }

    public static double getDouble(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length < 8) throw new IllegalArgumentException();
        return Double.longBitsToDouble(getLong(bytes));
    }

    public static double getDouble(byte[] bytes, int at) throws IllegalArgumentException {
        return Double.longBitsToDouble(getLong(bytes, at));
    }

    public static double getDouble(Bytes bytes, int at) throws IllegalArgumentException {
        return getDouble(bytes.getByteArray(), at);
    }

    public static byte[] doubleToBytes(double value) {
        long bits = Double.doubleToLongBits(value);
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(bits);
        return buffer.array();
    }

    public static int floatToIntPercentage(float value) {
        return Math.round(value * 100f);
    }

    public static byte floatToIntPercentageByte(float value) {
        return (byte) Math.round(value * 100f);
    }

    public static float getPercentage(byte value) {
        return getUnsignedInt(value) / 100f;
    }

    public static int getUnsignedInt(byte value) {
        return value & 0xFF;
    }

    public static int getUnsignedInt(Bytes bytes) throws IllegalArgumentException {
        return getUnsignedInt(bytes.getByteArray());
    }

    public static int getUnsignedInt(byte[] bytes) throws IllegalArgumentException {
        return getUnsignedInt(bytes, 0, bytes.length);
    }

    public static int getUnsignedInt(Bytes bytes, int at, int length) throws
            IllegalArgumentException {
        return getUnsignedInt(bytes.getByteArray(), at, length);
    }

    public static int getUnsignedInt(byte[] bytes, int at, int length) throws
            IllegalArgumentException {
        if (bytes.length >= at + length) {
            if (length == 4) {
                return ((0xFF & bytes[at]) << 24) | ((0xFF & bytes[at + 1]) << 16) |
                        ((0xFF & bytes[at + 2]) << 8) | (0xFF & bytes[at + 3]);
            } else if (length == 3) {
                return (bytes[at] & 0xff) << 16 | (bytes[at + 1] & 0xff) << 8 | (bytes[at + 2]
                        & 0xff);
            } else if (length == 2) {
                return ((bytes[at] & 0xff) << 8) | (bytes[at + 1] & 0xff);
            } else if (length == 1) {
                return bytes[at] & 0xFF;
            }
        }

        throw new IllegalArgumentException();
    }

    public static int getSignedInt(byte value) {
        return value;
    }

    public static int getSignedInt(Bytes bytes) {
        return getSignedInt(bytes.getByteArray());
    }

    public static int getSignedInt(byte[] bytes) {
        if (bytes.length == 1) return getSignedInt(bytes[0]);
        else if (bytes.length >= 2) {
            return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort();
        }

        throw new IllegalArgumentException();
    }

    public static int getSignedInt(byte[] bytes, int at, int length) {
        if (bytes.length >= 2) return bytes[at] << 8 | bytes[at + 1];
        throw new IllegalArgumentException();
    }

    /**
     * This works for both negative and positive ints.
     *
     * @param value  the valueBytes converted to byte[]
     * @param length the returned byte[] length
     * @return the allBytes representing the valueBytes
     * @throws IllegalArgumentException when input is invalid
     */
    public static byte[] intToBytes(int value, int length) throws IllegalArgumentException {
        if (length == 1) return new byte[]{(byte) value};

        byte[] bytes = BigInteger.valueOf(value).toByteArray();

        if (bytes.length == length) {
            return bytes;
        } else if (bytes.length < length) {
            // put the allBytes to last elements
            byte[] withZeroBytes = new byte[length];

            for (int i = 0; i < bytes.length; i++) {
                withZeroBytes[length - 1 - i] = bytes[bytes.length - 1 - i];
            }

            return withZeroBytes;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Boolean getBool(byte value) {
        return value != 0x00;
    }

    public static byte boolToByte(boolean value) {
        return (byte) (value ? 0x01 : 0x00);
    }

    public static byte getByte(boolean value) {
        return (byte) (value == false ? 0x00 : 0x01);
    }

    public static String getString(Bytes bytes) {
        return getString(bytes.getByteArray());
    }

    public static String getString(byte[] bytes) {
        try {
            return new String(bytes, PropertyComponentValue.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ParseException();
        }
    }

    public static String getString(Bytes bytes, int at, int length) {
        return getString(bytes.getByteArray(), at, length);
    }

    public static String getString(byte[] bytes, int at, int length) {
        return getString(Arrays.copyOfRange(bytes, at, at + length));
    }

    public static byte[] stringToBytes(String string) {
        try {
            return string.getBytes(PropertyComponentValue.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ParseException();
        }
    }

    public static int getUtf8Length(CharSequence sequence) {
        int count = 0;
        for (int i = 0, len = sequence.length(); i < len; i++) {
            char ch = sequence.charAt(i);
            if (ch <= 0x7F) {
                count++;
            } else if (ch <= 0x7FF) {
                count += 2;
            } else if (Character.isHighSurrogate(ch)) {
                count += 4;
                ++i;
            } else {
                count += 3;
            }
        }
        return count;
    }


    // 5 allBytes eg yy mm dd mm ss. year is from 2000
    public static Date getDate(byte[] bytes) throws IllegalArgumentException {
        for (int i = 0; i < bytes.length; i++) {
            if (i == bytes.length - 1 && bytes[i] == 0x00) return null; // all allBytes are 0x00
            else if (bytes[i] != 0x00) break; // one byte is not 0x00, some date is set
        }

        Calendar c = Calendar.getInstance();

        if (bytes.length == 5) {
            c.set(2000 + bytes[0], bytes[1] - 1, bytes[2], bytes[3], bytes[4], 0x00);
        } else if (bytes.length >= 6) {
            c.set(2000 + bytes[0], bytes[1] - 1, bytes[2], bytes[3], bytes[4], bytes[5]);
        } else {
            throw new IllegalArgumentException();
        }

        return c.getTime();
    }

    public static Calendar getCalendar(Property property) throws IllegalArgumentException {
        return getCalendar(property.getValueComponent().getValueBytes());
    }

    public static Calendar getCalendar(Bytes bytes) throws IllegalArgumentException {
        return getCalendar(bytes.getByteArray());
    }

    public static Calendar getCalendar(byte[] bytes) throws IllegalArgumentException {
        return getCalendar(bytes, 0, bytes.length);
    }

    public static Calendar getCalendar(byte[] bytes, int at) throws IllegalArgumentException {
        return getCalendar(bytes, at, PropertyComponentValue.CALENDAR_SIZE);
    }

    public static Calendar getCalendar(byte[] bytes, int at, int length) throws IllegalArgumentException {
        Calendar c;
        if (bytes.length >= at + length) {
            c = new GregorianCalendar();
            long epoch = Property.getLong(bytes, at, length);
            c.setTimeInMillis(epoch);
        } else {
            throw new IllegalArgumentException();
        }

        c.getTime(); // this is needed to set the right time...
        return c;
    }

    public static byte[] calendarToBytes(Calendar calendar) {
        return Property.longToBytes(calendar.getTimeInMillis());
    }

    public static int[] getIntegerArray(Bytes valueBytes) {
        int length = valueBytes.getLength();

        int[] value = new int[length];
        for (int i = 0; i < length; i++) {
            value[i] = Property.getUnsignedInt(valueBytes.get(i));
        }

        return value;
    }

    public static Bytes integerArrayToBytes(int[] value) {
        Bytes result = new Bytes(value.length);

        for (int i = 0; i < value.length; i++) {
            byte byteValue = Property.intToBytes(value[i], 1)[0];
            result.set(i, byteValue);
        }

        return result;
    }
}
