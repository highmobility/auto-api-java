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
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ObjectPropertyString.CHARSET;

/**
 * Property is a representation of some AutoAPI data. Specific data have specific subclasses like
 * StringProperty and ObjectProperty<Float>.
 * <p>
 * Property has to have a value with a size greater or equal to 1.
 */
public class Property extends Bytes {
    // set when bytes do not exist
    protected static final byte[] unknownBytes = new byte[]{0x00, 0x00, 0x00};

    protected PropertyTimestamp timestamp;
    protected PropertyFailure failure;
    @Nullable PropertyValueByteArray propertyValue; // TODO: use this for all of the properties.
    // use it
    // to get the property length and bytes

    /**
     * @return The timestamp of the property.
     */
    @Nullable public Calendar getTimestamp() {
        if (timestamp == null) return null;
        return timestamp.getCalendar();
    }

    @Nullable PropertyTimestamp getPropertyTimestamp() {
        return timestamp;
    }

    /**
     * @return The failure of the property.
     */
    @Nullable public PropertyFailure getFailure() {
        return failure;
    }

    protected Property() {
    }

    protected Property(@Nullable PropertyValue value) {
        this(value == null ? 0 : value.getLength());
    }

    protected Property(@Nullable PropertyValueByteArray value) {
        this((PropertyValue) value);
        this.propertyValue = value;
    }

    protected Property(int length) {
        this.bytes = baseBytes((byte) 0x00, length);
    }

    /*protected Property(byte identifier, @Nullable PropertyValue value) {
        this.bytes = baseBytes(identifier, value == null ? 0 : value.getLength());
    }*/

    protected Property(byte identifier, int valueSize) {
        this.bytes = baseBytes(identifier, valueSize);
    }

    /**
     * @param identifier The identifier byte of the property.
     * @param value      The value of the property.
     */
    public Property(byte identifier, byte value) {
        bytes = getPropertyBytes(identifier, value);
    }

    /**
     * @param identifier The identifier byte of the property.
     * @param value      The value of the property.
     */
    public Property(byte identifier, byte[] value) {
        bytes = getPropertyBytes(identifier, value);
    }

    public Property(@Nullable PropertyValueObject value, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        this((byte) 0x00, value != null ? value.getLength() : 0);
        setTimestampFailure(timestamp, failure);
    }

    public Property(@Nullable PropertyValue value, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        this((byte) 0x00, value != null ? value.getLength() : 0);
        setTimestampFailure(timestamp, failure);
    }

    public Property(@Nullable PropertyValueByteArray value, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        this((byte) 0x00, value != null ? value.getLength() : 0);
        setTimestampFailure(timestamp, failure);
        this.propertyValue = value;
    }

    /**
     * @param identifier The identifier byte of the property.
     * @param value      The value of the property.
     */
    private Property(byte identifier, Bytes value) {
        this(identifier, value.getByteArray());
    }

    public Property(Bytes bytes) {
        this(bytes == null ? null : bytes.getByteArray());
    }

    public Property(String bytes) {
        this(ByteUtils.bytesFromHexOrBase64(bytes));
    }

    public Property(byte[] bytes) {
        if (bytes == null || bytes.length == 0) bytes = unknownBytes;
        if (bytes.length < 3) bytes = Arrays.copyOf(bytes, 3);
        this.bytes = bytes;
    }

    public Property(byte identifier) {
        this(identifier, 0);
    }

    public int getValueLength() {
        return Property.getUnsignedInt(bytes, 4, 2);
    }

    /**
     * @return The value bytes.
     */
    public byte[] getValueBytesArray() {
        if (bytes.length <= 6) return new byte[0];
        return Arrays.copyOfRange(bytes, 6, bytes.length);
    }

    public Bytes getValueBytes() {
        return new Bytes(getValueBytesArray());
    }

    /**
     * @return The one value byte. Returns null if property has no value set.
     */

    @Nullable
    public Byte getValueByte() {
        if (bytes.length < 7) return null;
        return bytes[6];
    }

    /**
     * Set a new identifier for the property
     *
     * @param identifier The identifier.
     */
    public Property setIdentifier(byte identifier) {
        bytes[0] = identifier;
        return this;
    }

    // TODO: 2019-02-04 make private
    public void setPropertyFailure(PropertyFailure propertyFailure) {
        this.failure = propertyFailure;
    }

    // TODO: 2019-02-04 make private
    public void setPropertyTimestamp(PropertyTimestamp propertyTimestamp) {
        this.timestamp = propertyTimestamp;
    }

    // TODO: 2019-01-31 set to private. has to call the proper ctor with these arguments
    // or nvm if easier for primitive types to use this
    protected void setTimestampFailure(Calendar timestamp, PropertyFailure failure) {
        if (timestamp != null) this.timestamp = new PropertyTimestamp(timestamp);
        this.failure = failure;
    }

    public void printFailedToParse(Exception e) {
        Command.logger.info("Failed to parse property: " + toString() + (e != null ? (". " + e
                .getClass().getSimpleName() + ": " + e.getMessage()) : ""));
//        e.printStackTrace();
    }

    public static void ignoreInvalidByteSizeException(RunnableExc r) throws CommandParseException {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
        } catch (NullPointerException e2) {

        }
    }

    @FunctionalInterface
    public interface RunnableExc {
        void run() throws CommandParseException;
    }

    public byte getPropertyIdentifier() {
        return bytes[0];
    }

    public Property update(Property p) throws CommandParseException {
        this.bytes = p.bytes;
        this.failure = p.failure;
        this.timestamp = p.timestamp;
        return this;
    }

    public Property update(PropertyValue p) {
        if (p != null) {
            Bytes newValue = null;

            if (p instanceof PropertyValueSingleByte) {
                newValue = new Bytes(new byte[]{((PropertyValueSingleByte) p).getByte()});
            } else if (p instanceof PropertyValueByteArray) {
                newValue = ((PropertyValueByteArray) p).getBytes();
            }

            if (newValue != null) {
                if (getValueLength() < newValue.getLength()) {
                    // reset the bytes
                    bytes = baseBytes(getPropertyIdentifier(), newValue.getLength());
                }

                ByteUtils.setBytes(bytes, newValue.getByteArray(), 3);
            }
        }

        return this;
    }

    public boolean isUniversalProperty() {
        return this instanceof PropertyFailure || this instanceof PropertyTimestamp;
    }

    protected void setValueBytes(byte[] valueBytes) {
        ByteUtils.setBytes(bytes, valueBytes, 6);
    }

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

    protected static byte[] getPropertyBytes(byte identifier, byte value) throws IllegalArgumentException {
        return getPropertyBytes(identifier, new byte[]{value});
    }

    protected static byte[] getPropertyBytes(byte identifier, byte[] value) throws
            IllegalArgumentException {
        byte[] bytes = baseBytes(identifier, value.length);
        ByteUtils.setBytes(bytes, value, 6);
        return bytes;
    }

    // MARK: static helpers

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
                return (int) bytes[at];
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

    public static String getString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET);
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

    public static class SortForParsing implements Comparator<Property> {
        public int compare(Property a, Property b) {
            // -1 before, 1 after
            if (a.getPropertyIdentifier() != PropertyFailure.IDENTIFIER &&
                    a.getPropertyIdentifier() != PropertyTimestamp.IDENTIFIER) {
                return -1;
            }

            return 0;
        }
    }
}
