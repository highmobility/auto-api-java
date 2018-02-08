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

    private static DateFormat format;

    public static DateFormat getFormat() {
        if (format == null) {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        return format;
    }

    public static Calendar getCalendar(String dateString) throws ParseException {
        Date date = getFormat().parse(dateString);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        return c;
    }
}
