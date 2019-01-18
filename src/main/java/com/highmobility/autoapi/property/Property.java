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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.StringProperty.CHARSET;

/**
 * Property is a representation of some AutoAPI data. Specific data have specific subclasses like
 * StringProperty and FloatProperty.
 * <p>
 * Property has to have a value with a size greater or equal to 1.
 */
public class Property extends Bytes {
    public static final int CALENDAR_SIZE = 8;
    // set when bytes do not exist
    protected static final byte[] unknownBytes = new byte[]{0x00, 0x00, 0x00};

    protected PropertyTimestamp timestamp;
    protected PropertyFailure failure;

    public static Property update(ArrayList<? extends Property> departureTimes, Property p,
                                  PropertyFailure timestamp, PropertyTimestamp failure) throws CommandParseException {
        for (Property existingProperty : departureTimes) {
            if (existingProperty.update(p, timestamp, failure, true))
                return existingProperty;
        }

        return null;
    }

    /**
     * @return The timestamp of the property.
     */
    @Nullable public Calendar getTimestamp() {
        if (timestamp == null) return null;
        return timestamp.getCalendar();
    }

    /**
     * @return The failure of the property.
     */
    @Nullable public PropertyFailure getFailure() {
        return failure;
    }

    protected Property(byte identifier, int valueSize) {
        this.bytes = baseBytes(identifier, valueSize);
    }

    /**
     * @param identifier The identifier byte of the property.
     * @param value      The value of the property.
     */
    public Property(byte identifier, byte value) {
        this(identifier, 1);
        bytes[3] = value;
    }

    /**
     * @param identifier The identifier byte of the property.
     * @param value      The value of the property.
     */
    public Property(byte identifier, byte[] value) {
        this(identifier, value != null ? value.length : 0);
        if (value != null) ByteUtils.setBytes(bytes, value, 3);
    }

    /**
     * @param identifier The identifier byte of the property.
     * @param value      The value of the property.
     */
    public Property(byte identifier, Bytes value) {
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
        return Property.getUnsignedInt(bytes, 1, 2);
    }

    /**
     * @return The value bytes.
     */
    public byte[] getValueBytes() {
        if (bytes.length == 3) return new byte[0];
        return Arrays.copyOfRange(bytes, 3, bytes.length);
    }

    /**
     * @return The one value byte. Returns null if property has no value set.
     */
    @Nullable public Byte getValueByte() {
        if (bytes.length == 3) return null;
        return bytes[3];
    }

    // TODO: 2019-01-08 these should be package private?

    /**
     * Set a new identifier for the property
     *
     * @param identifier The identifier.
     */
    public Property setIdentifier(byte identifier) {
        bytes[0] = identifier;
        return this;
    }

    protected void setTimestampFailure(Calendar timestamp, PropertyFailure failure) {
        if (timestamp != null) this.timestamp = new PropertyTimestamp(timestamp);
        this.failure = failure;
    }

    public void printFailedToParse(Exception e) {
        Command.logger.info("Failed to parse property: " + toString() + (e != null ? (". " + e
                .getClass().getSimpleName() + ": " + e.getMessage()) : ""));
    }

    protected static byte[] baseBytes(byte identifier, int valueSize) {
        byte[] bytes = new byte[3 + valueSize];

        bytes[0] = identifier;
        if (valueSize > 255) {
            byte[] lengthBytes = intToBytes(valueSize, 2);
            bytes[1] = lengthBytes[0];
            bytes[2] = lengthBytes[1];
        } else if (valueSize != 0) {
            bytes[1] = 0x00;
            bytes[2] = (byte) valueSize;
        }

        return bytes;
    }

    public byte getPropertyIdentifier() {
        return bytes[0];
    }

    /**
     * @param propertyInArray Whether there could be multiple properties with this identifier.
     * @return true if property updated.
     */
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (failure != null && failure.getFailedPropertyIdentifier() == getPropertyIdentifier()) {
            this.failure = failure;
            return true;
        }

        /* If property in array, need to check the additional data. Otherwise can just use the
        identifier because property is unique. */
        if (timestamp != null &&
                ((propertyInArray && timestamp.getAdditionalData() != null && this.equals(timestamp.getAdditionalData())) ||
                        (propertyInArray == false && timestamp.getTimestampPropertyIdentifier() == getPropertyIdentifier()))) {
            // we expect that property bytes are set before failure/timestamp.
            this.timestamp = timestamp;
            return true;
        }

        if (p != null) {
            this.bytes = p.getByteArray();
            return true;
        }

        return false;
    }

    public boolean isUniversalProperty() {
        return this instanceof PropertyFailure || this instanceof PropertyTimestamp;
    }

    // helper methods

    public static byte[] getPropertyBytes(byte identifier, byte value) throws
            IllegalArgumentException {
        byte[] bytes = new byte[4];
        bytes[0] = identifier;
        byte[] lengthBytes = intToBytes(1, 2);
        bytes[1] = lengthBytes[0];
        bytes[2] = lengthBytes[1];
        bytes[3] = value;
        return bytes;
    }

    public static byte[] getPropertyBytes(byte identifier, int length, byte[] value) throws
            IllegalArgumentException {
        byte[] bytes = new byte[3];
        bytes[0] = identifier;
        byte[] lengthBytes = intToBytes(length, 2);
        bytes[1] = lengthBytes[0];
        bytes[2] = lengthBytes[1];
        bytes = ByteUtils.concatBytes(bytes, value);
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

    public static boolean getBool(byte value) {
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
            return string.getBytes(StringProperty.CHARSET);
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

    public static Calendar getCalendar(byte[] bytes, int at) throws IllegalArgumentException {
        Calendar c = new GregorianCalendar();

        if (bytes.length >= at + CALENDAR_SIZE) {
            c.set(2000 + bytes[at], bytes[at + 1] - 1, bytes[at + 2], bytes[at + 3], bytes[at +
                    4], bytes[at + 5]);
            int minutesOffset = getSignedInt(new byte[]{bytes[at + 6], bytes[at + 7]});

            int msOffset = minutesOffset * 60 * 1000;
            String[] availableIds = TimeZone.getAvailableIDs(msOffset);
            if (availableIds.length == 0) {
                c.setTimeZone(TimeZone.getTimeZone("UTC"));
            } else {
                TimeZone timeZone = TimeZone.getTimeZone(availableIds[0]);
                c.setTimeZone(timeZone);
            }
        } else {
            throw new IllegalArgumentException();
        }

        c.getTime(); // this is needed to set the right time...
        return c;
    }

    public static byte[] calendarToBytes(Calendar calendar) {
        byte[] bytes = new byte[CALENDAR_SIZE];

        bytes[0] = (byte) (calendar.get(Calendar.YEAR) - 2000);
        bytes[1] = (byte) (calendar.get(Calendar.MONTH) + 1);
        bytes[2] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        bytes[3] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        bytes[4] = (byte) calendar.get(Calendar.MINUTE);
        bytes[5] = (byte) calendar.get(Calendar.SECOND);

        int msOffset = calendar.getTimeZone().getRawOffset(); // in ms
        int minuteOffset = msOffset / (60 * 1000);

        byte[] bytesOffset = Property.intToBytes(minuteOffset, 2);
        bytes[6] = bytesOffset[0];
        bytes[7] = bytesOffset[1];

        return bytes;
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
