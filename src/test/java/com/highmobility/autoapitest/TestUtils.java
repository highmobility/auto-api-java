package com.highmobility.autoapitest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static junit.framework.TestCase.assertTrue;

public class TestUtils {

    public static boolean dateIsSame(Calendar c, String date) throws ParseException {
        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = 0;
        assertTrue(rawOffset == expectedRawOffset);

        Date expectedDate = getFormat().parse(date);
        Date commandDate = c.getTime();
        String commandDateString = getFormat().format(commandDate);
        String expectedDateString = getFormat().format(expectedDate);

        return (commandDateString.equals(expectedDateString));
    }

    /**
     * This does not consider time zone.
     */
    public static boolean dateIsSameIgnoreTimezone(Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)) return false;
        if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY)) return false;
        if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE)) return false;
        if (c1.get(Calendar.SECOND) != c2.get(Calendar.SECOND)) return false;
        return true;
    }

    private static DateFormat format;

    public static DateFormat getFormat() {
        if (format == null) {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        return format;
    }

    public static Calendar getCalendar(String dateString, int timeZoneMinuteOffset) throws
            ParseException {
        Date date = getFormat().parse(dateString);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs(timeZoneMinuteOffset * 60 *
                1000)[0]));

        return c;
    }

    public static Calendar getCalendar(String dateString) throws ParseException {
        return getCalendar(dateString, 0);
    }
}
