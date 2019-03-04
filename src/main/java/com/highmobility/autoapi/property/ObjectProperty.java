/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Nullable;

/**
 * Property is a representation of some AutoAPI data. Specific data have specific subclasses like
 * StringProperty and ObjectProperty<Float>.
 * <p>
 * Property has to have a value with a size greater or equal to 1.
 */
// TODO: 2019-02-28 clear comments
public class ObjectProperty<T> extends Bytes {
    protected static final byte[] unknownBytes = new byte[]{0x00, 0x00, 0x00};

    @Nullable private PropertyValueComponent<T> value;
    @Nullable private PropertyTimestampComponent timestamp;
    @Nullable private PropertyFailureComponent failure;

    protected Class<T> valueClass = null;

    /**
     * @return The property value.
     */
    @Nullable public T getValue() {
        return value.getValue();
    }

    /**
     * @return The timestamp of the property.
     */
    @Nullable public Calendar getTimestamp() {
        if (timestamp == null) return null;
        return timestamp.getCalendar();
    }

    @Nullable PropertyTimestampComponent getPropertyTimestamp() {
        return timestamp;
    }

    /**
     * @return The failure of the property.
     */
    @Nullable public PropertyFailureComponent getFailure() {
        return failure;
    }

    @Nullable public Class<T> getValueClass() { // TODO: 2019-02-28 should be private
        return valueClass;
    }

    /**
     * @return The one value byte. Returns null if property has no value set.
     */

    @Nullable public Byte getValueByte() {
        return (value != null && value.getLength() > 0) ? value.get(0) : null;
    }

    // MARK: builder ctor

    public ObjectProperty(@Nullable T value,
                          @Nullable Calendar timestamp,
                          @Nullable PropertyFailureComponent failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public ObjectProperty(@Nullable T value) {
        if (value == null) {
            bytes = baseBytes((byte) 0, 0);
        } else {
            update((byte) 0, value);
        }
    }

    // TODO: 2019-03-04 make private
    public ObjectProperty(byte[] bytes) {
        if (bytes == null || bytes.length == 0) bytes = unknownBytes;
        if (bytes.length < 3) bytes = Arrays.copyOf(bytes, 3);
        this.bytes = bytes;

        // TODO: 2019-03-04 find components
    }

    // MARK: internal ctor
    // TODO: 2019-02-04 make internal

    public ObjectProperty(byte identifier, T value) {
        update(identifier, value);
    }

    public ObjectProperty(Class<T> valueClass, byte identifier) {
        this.valueClass = valueClass;
        this.bytes = baseBytes(identifier, 0);
    }

    public ObjectProperty(Class<T> valueClass, ObjectProperty property) throws CommandParseException {
        this.valueClass = valueClass;
        if (property == null || property.getLength() == 0) this.bytes = unknownBytes;
        if (property.getLength() < 3) this.bytes = Arrays.copyOf(property.getByteArray(), 3);
        else this.bytes = property.getByteArray();
        update(property);
    }

    // TODO: 2019-02-05 make internal
    public ObjectProperty update(T value) {
        return update(bytes[0], value);
    }

    // TODO: 2019-02-05 make internal
    public ObjectProperty update(byte identifier, T value) {
        this.value = getBytes(value);
        this.bytes = baseBytes(identifier, this.value.getLength());
        ByteUtils.setBytes(bytes, this.value.getByteArray(), 6);
        this.value = value;
        return this;
    }

    protected Bytes getBytes(T value) {
        // this is for builder/set command

        if (value instanceof Bytes) {
            return ((Bytes) value);
        } else if (value instanceof PropertyValueObject) {
            return ((PropertyValueObject) value);
        } else if (value instanceof PropertyValueSingleByte) {
            byte byteValue = ((PropertyValueSingleByte) value).getByte();
            return new Bytes(new byte[]{byteValue});
        } else if (value instanceof Boolean) {
            return new Bytes(new byte[]{boolToByte((Boolean) value)});
        } else if (value instanceof Number) {
            if (value instanceof Float) {
                return new Bytes(floatToBytes((Float) value));
            } else if (value instanceof Integer) {
                // integer length is defaulted to 4 here. It should be set to the correct one in
                // the IntegerProperty's update.
                return new Bytes(intToBytes((Integer) value, 4));
            } else if (value instanceof Double) {
                return new Bytes(doubleToBytes((Double) value));
            }
        } else {
            throw new IllegalArgumentException("Type not supported for Property");
        }

        return null;
    }

    // TODO: 2019-02-04 make private 

    /**
     * Set a new identifier for the property
     *
     * @param identifier The identifier.
     */
    public ObjectProperty setIdentifier(byte identifier) {
        bytes[0] = identifier;
        return this;
    }

    // TODO: 2019-02-04 make private

    public void printFailedToParse(Exception e) {
        Command.logger.info("Failed to parse property: " + toString() + (e != null ? (". " + e
                .getClass().getSimpleName() + ": " + e.getMessage()) : ""));
//        e.printStackTrace();
    }

    public byte getPropertyIdentifier() {
        return bytes[0];
    }

    // TODO: 2019-02-04 private
    public ObjectProperty update(ObjectProperty p) throws CommandParseException {
        if (valueClass == null)
            throw new IllegalArgumentException("Initialise with a class to update.");

        this.bytes = p.getByteArray();

        this.value = p.value;
        this.failure = p.failure;
        this.timestamp = p.timestamp;

        /*// TODO: 2019-03-04 dont use position 6, find the data component instead
        if (bytes.length <= 6) value = new Bytes();
        else value = new Bytes(Arrays.copyOfRange(bytes, 6, bytes.length));*/

        int index = 3;
        for (int i = 3; i < bytes.length; i++) {
            int size = getUnsignedInt(bytes, index + 1, 2);

            if (bytes[index] == 0x01) {
                // data component
                value = p.getRange(index + 3, index + 3 + size);
            } else if (bytes[index] == 0x02) {
                // timestamp component
                timestamp = new PropertyTimestampComponent(p.getRange(index + 3, index + 3 + size));
            } else if (bytes[index] == 0x03) {
                // failure component
                failure = new PropertyFailureComponent(p.getRange(index + 3, index + 3 + size));
            }

            i += size + 3;
        }

        try {
            if (PropertyValueObject.class.isAssignableFrom(valueClass)) {
                value = valueClass.newInstance();
                ((PropertyValueObject) value).update(p.getValueBytes());
            } else if (Enum.class.isAssignableFrom(valueClass)) {
                value = valueClass.getEnumConstants()[p.getValueByte()];
            } else if (Boolean.class.isAssignableFrom(valueClass)) {
                value = (T) getBool(p.getValueByte());
            } else if (Float.class.isAssignableFrom(valueClass)) {
                value = (T) (Float) getFloat(p.getValueBytesArray());
            } else if (Double.class.isAssignableFrom(valueClass)) {
                value = (T) (Double) getDouble(p.getValueBytesArray());
            }

        } catch (IllegalAccessException e) {

        } catch (InstantiationException e) {

        } catch (CommandParseException e) {
            // TODO: 2019-02-01 error handling
        }

        return this;
    }

    /*ObjectProperty update(T value) {
        if (value != null) {
            Bytes newValue = value.getBytes();

            if (newValue != null) {
                if (getValueLength() < newValue.getLength()) {
                    // reset the bytes
                    bytes = baseBytes(getPropertyIdentifier(), newValue.getLength());
                }

                ByteUtils.setBytes(bytes, newValue.getByteArray(), 3);
            }
        }

        return this;
    }*/

    protected void setTimestampFailure(Calendar timestamp, PropertyFailureComponent failure) {
        if (timestamp != null) this.timestamp = new PropertyTimestampComponent(timestamp);
        // TODO: 2019-02-28 verify that bytes are correct when timestamp/failure are set
        this.failure = failure;
    }

    // helper methods
    protected void setValueBytes(byte[] valueBytes) {
        ByteUtils.setBytes(bytes, valueBytes, 6);
    }

    /*public boolean isUniversalProperty() {
        return this instanceof PropertyFailure || this instanceof PropertyTimestamp;
    }*/
    // TODO: 2019-02-01

    // MARK: ctor helpers

    public static byte[] baseBytes(byte identifier, int dataComponentSize) {
        // if have a value, create bytes for data component
        int propertySize = dataComponentSize + 3;

        byte[] bytes = new byte[3 + (dataComponentSize > 0 ? dataComponentSize + 3 : 0)];

        bytes[0] = identifier;

        if (propertySize > 255) {
            byte[] propertyLengthBytes = intToBytes(propertySize, 2);
            bytes[1] = propertyLengthBytes[0];
            bytes[2] = propertyLengthBytes[1];
        } else if (propertySize != 3) {
            // if property size 3, we don't have data component and can omit the bytes.
            bytes[1] = 0x00;
            bytes[2] = (byte) propertySize;
        }

        if (dataComponentSize > 0) {
            bytes[3] = 0x01; // data component
            ByteUtils.setBytes(bytes, intToBytes(dataComponentSize, 2), 4); // data component size
        }

        return bytes;
    }

    public static byte[] getIntProperty(byte identifier, int value, int length) throws
            IllegalArgumentException {
        byte[] bytes = new byte[]{
                identifier,
                0x00,
                (byte) length
        };

        byte[] valueBytes = intToBytes(value, length);
        return ByteUtils.concatBytes(bytes, valueBytes);
    }

    public static long getLong(byte[] b, int at) throws IllegalArgumentException {
        if (b.length - at < 8) throw new IllegalArgumentException();

        long result = 0;
        for (int i = at; i < at + 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }

        return result;
    }

    public static long getLong(byte[] b) throws IllegalArgumentException {
        return getLong(b, 0);
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
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
                int result = ((0xFF & bytes[at]) << 24) | ((0xFF & bytes[at + 1]) << 16) |
                        ((0xFF & bytes[at + 2]) << 8) | (0xFF & bytes[at + 3]);
                return result;
            } else if (length == 3) {
                int result = (bytes[at] & 0xff) << 16 | (bytes[at + 1] & 0xff) << 8 | (bytes[at + 2]
                        & 0xff);
                return result;
            } else if (length == 2) {
                int result = ((bytes[at] & 0xff) << 8) | (bytes[at + 1] & 0xff);
                return result;
            } else if (length == 1) {
                return bytes[at] & 0xFF;
            }
        }

        throw new IllegalArgumentException();
    }

    public static int getSignedInt(byte value) {
        return (int) value;
    }

    public static int getSignedInt(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length == 1) return getSignedInt(bytes[0]);
        else if (bytes.length >= 2) {
            int result = bytes[0] << 8 | bytes[1];
            return result;
        }

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

    public static String getString(byte[] bytes) {
        try {
            return new String(bytes, ObjectPropertyString.CHARSET);
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
            return string.getBytes(ObjectPropertyString.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ParseException();
        }
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

    public static Calendar getCalendar(byte[] bytes) throws IllegalArgumentException {
        return getCalendar(bytes, 0);
    }

    public static Calendar getCalendar(Bytes bytes, int at) throws IllegalArgumentException {
        return getCalendar(bytes.getByteArray(), at);
    }

    public static Calendar getCalendar(byte[] bytes, int at) throws IllegalArgumentException {
        Calendar c = new GregorianCalendar();
        if (bytes.length >= at + CalendarProperty.CALENDAR_SIZE) {
            Long epoch = Property.getLong(bytes, at);
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

//    public static class SortForParsing implements Comparator<ObjectProperty> {
//        public int compare(ObjectProperty a, ObjectProperty b) {
//            // -1 before, 1 after
//            if (a.getPropertyIdentifier() != PropertyFailure.IDENTIFIER &&
//                    a.getPropertyIdentifier() != PropertyTimestamp.IDENTIFIER) {
//                return -1;
//            }
//
//            return 0;
//        }
//    }
}
