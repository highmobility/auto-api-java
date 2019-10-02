package com.highmobility.autoapi;

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {
    static FakeLogger fakeLogger = new FakeLogger();

    public static void errorLogExpected(Runnable runnable) {
        errorLogExpected(1, runnable);
    }

    public static void errorLogExpected(int count, Runnable runnable) {
        // could add a log count to logger, and if it exceeds the param count, write an error
        Logger previousLogger = Command.logger;
        int logsBefore = fakeLogger.logCount;
        Command.logger = fakeLogger;
        runnable.run();
        Command.logger = previousLogger;
        // if this fails, don't use fake logger and check what error message should not be present
        assertTrue(logsBefore + count == fakeLogger.logCount);
    }

    public static boolean dateIsSame(Calendar c, String dateString) {
        return dateIsSameIgnoreTimezone(c, dateString); // currently ignoring time zone.
        /*DateFormat format = getFormat(dateString);
        Date expectedDate;
        try {
            expectedDate = format.parse(dateString);
        } catch (ParseException e) {
            expectedDate = format.parse(dateString + "+0000"); // add timezone
        }

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expectedDate);
        expectedCalendar.setTimeZone(format.getTimeZone());
        Date commandDate = c.getTime();

        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = expectedCalendar.getTimeZone().getRawOffset();
        assertTrue(rawOffset == expectedRawOffset);

        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);

        return (commandDateString.equals(expectedDateString));*/
    }

    public static boolean dateIsSame(Calendar c, Calendar c2) {
        return dateIsSameIgnoreTimezone(c, c2);
    }

    public static DateFormat getFormat(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String offset = date.substring(19);
        TimeZone tz = TimeZone.getTimeZone("GMT" + offset);
        format.setTimeZone(tz);
        return format;
    }

    public static boolean dateIsSameUTC(Calendar c, String date) throws ParseException {
        float rawOffset = c.getTimeZone().getRawOffset();

        Date expectedDate = getUTCFormat().parse(date);
        Date commandDate = c.getTime();

        float expectedRawOffset = 0;
        assertTrue(rawOffset == expectedRawOffset);

        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);

        return (commandDateString.equals(expectedDateString));
    }

    /**
     * This does not consider time zone.
     */
    public static boolean dateIsSameIgnoreTimezone(Calendar c1, Calendar c2) {
        return c1.getTimeInMillis() == c2.getTimeInMillis();
        /*if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)) return false;
        if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY)) return false;
        if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE)) return false;
        if (c1.get(Calendar.SECOND) != c2.get(Calendar.SECOND)) return false;*/
    }

    public static boolean dateIsSameIgnoreTimezone(Calendar c1, String c2) {
        Date expectedDate = null;
        try {
            expectedDate = getUTCFormat().parse(c2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date commandDate = c1.getTime();
        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);
        return (commandDateString.equals(expectedDateString));
    }

//    private static DateFormat format;

    public static DateFormat getUTCFormat() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    }

    public static Calendar getUTCCalendar(String dateString, int timeZoneMinuteOffset) throws
            ParseException {
        Date date = getUTCFormat().parse(dateString);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs(timeZoneMinuteOffset * 60 *
                1000)[0]));

        return c;
    }

    public static Calendar getUTCCalendar(String dateString) throws ParseException {
        return getUTCCalendar(dateString, 0);
    }

    public static Calendar getCalendar(String dateString) {
        DateFormat format = getFormat(dateString);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            try {
                date = format.parse(dateString + "+0000"); // add timezone
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setTimeZone(format.getTimeZone());
        return c;
    }

    public static boolean bytesTheSame(Bytes state, Bytes bytes) {
        for (int i = 0; i < state.getLength(); i++) {
            byte stateByte = state.getByteArray()[i];

            if (bytes.getByteArray().length < i + 1) {
                System.out.println("state bytes has more bytes\nbytes ex: " + bytes + "\nbytes in: " + state);
                return false;
            }

            byte otherByte = bytes.getByteArray()[i];
            if (stateByte != otherByte) {
                System.out.println("bytes not equal at index " + i + ". expected: " + ByteUtils
                        .hexFromBytes(new byte[]{otherByte}) + ", actual: " + ByteUtils
                        .hexFromBytes(new byte[]{stateByte}) +
                        "\nbytes1: " + ByteUtils.hexFromBytes(Arrays.copyOf
                        (bytes.getByteArray(), i + 1)) +
                        "\nbytes2: " + ByteUtils.hexFromBytes(Arrays.copyOf(state
                        .getByteArray(), i + 1)));

                System.out.println("bytes ex: " + bytes);
                System.out.println("bytes in: " + state);
                return false;
            }
        }

        if (bytes.getLength() > state.getLength()) {
            System.out.println("expected bytes has more bytes\nbytes ex: " + bytes + "\nbytes in: " + state);
            return false;
        }

        return true;
    }
}
