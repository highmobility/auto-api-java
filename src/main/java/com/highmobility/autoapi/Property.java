package com.highmobility.autoapi;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Property {
    public static long getLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static boolean getBit(int n, int k) {
        return ((n >> k) & 1) == 1;
    }

    public static float getFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static float getFloat(byte[] bytes, int at) throws CommandParseException {
        int intValue = getInt(bytes, at, 4);
        return Float.intBitsToFloat(intValue);
    }

    public static byte[] floatToBytes(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static int getInt(byte[] bytes) {
        if (bytes.length == 3) {
            int result = (bytes[0] & 0xff) << 16 | (bytes[1] & 0xff) << 8 | (bytes[2] & 0xff);
            return result;
        }
        else if (bytes.length == 2) {
            int result = ((bytes[0] & 0xff) << 8) | (bytes[1] & 0xff);
            return result;
        }

        return ByteBuffer.wrap(bytes).getInt();
    }

    public static int getInt(byte[] bytes, int at, int length) throws CommandParseException {
        if (length == 4) {
            int result = ((0xFF & bytes[at]) << 24) | ((0xFF & bytes[at + 1]) << 16) |
                    ((0xFF & bytes[at + 2]) << 8) | (0xFF & bytes[at + 3]);
            return result;
        }
        else if (length == 3) {
            int result = (bytes[at] & 0xff) << 16 | (bytes[at + 1] & 0xff) << 8 | (bytes[at + 2] & 0xff);
            return result;
        }
        else if (length == 2) {
            int result = ((bytes[at] & 0xff) << 8) | (bytes[at + 1] & 0xff);
            return result;
        }
        else if (length == 1) {
            return (int)bytes[at];
        }

        throw new CommandParseException();
    }

    public static int getInt(byte value) {
        return (int)value;
    }

    public static int getSignedInt(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length == 2) {
            return bytes[0] << 8 | bytes[1];
        }

        throw new IllegalArgumentException();
    }

    /**
     *
     * @param value the value converted to byte[]
     * @param length the returned byte[] length
     * @return the bytes representing the value
     * @throws IllegalArgumentException when input is invalid
     */
    public static byte[] intToBytes(int value, int length) throws IllegalArgumentException {
        byte[] bytes = BigInteger.valueOf(value).toByteArray();

        if (bytes.length == length) {
            return bytes;
        }
        else if (bytes.length < length) {
            // put the bytes to last elements
            byte[] withZeroBytes = new byte[length];

            for (int i = 0; i < bytes.length; i++) {
                withZeroBytes[length - 1 - i] = bytes[bytes.length - 1 - i];
            }

            return withZeroBytes;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean getBool(byte value) {
        return value == 0x00 ? false : true;
    }

    public static byte boolToByte(boolean value) {
        return (byte) (value ? 0x01 : 0x00);
    }

    public static byte getByte(boolean value) {
        return (byte) (value == false ? 0x00 : 0x01);
    }

    public static String getString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "parse error";
        }
    }

    // 5 bytes eg yy mm dd mm ss. year is from 2000
    public static Date getDate(byte[] bytes) throws IllegalArgumentException {
        for (int i = 0; i < bytes.length; i++) {
            if (i == bytes.length - 1 && bytes[i] == 0x00) return null; // all bytes are 0x00
            else if (bytes[i] != 0x00) break; // one byte is not 0x00, some date is set
        }

        Calendar c = Calendar.getInstance();

        if (bytes.length == 5) {
            c.set(2000 + bytes[0], bytes[1] - 1, bytes[2], bytes[3], bytes[4], 0x00);
        }
        else if (bytes.length == 6) {
            c.set(2000 + bytes[0], bytes[1] - 1, bytes[2], bytes[3], bytes[4], bytes[5]);
        }

        else {
            throw new IllegalArgumentException();
        }

        return c.getTime();
    }

    public static Calendar getCalendar(byte[] bytes) throws IllegalArgumentException {
        for (int i = 0; i < bytes.length; i++) {
            if (i == bytes.length - 1 && bytes[i] == 0x00) return null; // all bytes are 0x00
            else if (bytes[i] != 0x00) break; // one byte is not 0x00, some date is set
        }

        Calendar c = new GregorianCalendar();

        if (bytes.length == 8) {
            c.set(2000 + bytes[0], bytes[1] - 1, bytes[2], bytes[3], bytes[4], bytes[5]);
            int minutesOffset = getSignedInt(new byte[] {bytes[6], bytes[7]});

            int msOffset = minutesOffset * 60 * 1000;
            String[] availableIds = TimeZone.getAvailableIDs(msOffset);
            if (availableIds.length == 0) {
                c.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            else {
                TimeZone timeZone = TimeZone.getTimeZone(availableIds[0]);
                c.setTimeZone(timeZone);
            }
        }
        else {
            throw new IllegalArgumentException();
        }

        c.getTime(); // this is needed to set the right time...
        return c;
    }
}
