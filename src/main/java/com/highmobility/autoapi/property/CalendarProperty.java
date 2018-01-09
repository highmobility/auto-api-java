package com.highmobility.autoapi.property;

import java.util.Calendar;

public class CalendarProperty extends Property {
    public  CalendarProperty(byte identifier, Calendar cal) {
        super(identifier, 8);

        bytes[3] = (byte) (cal.get(Calendar.YEAR) - 2000);
        bytes[4] = (byte) (cal.get(Calendar.MONTH) + 1);
        bytes[5] = (byte) cal.get(Calendar.DAY_OF_MONTH);
        bytes[6] = (byte) cal.get(Calendar.HOUR_OF_DAY);
        bytes[7] = (byte) cal.get(Calendar.MINUTE);
        bytes[8] = (byte) cal.get(Calendar.SECOND);

        int msOffset = cal.getTimeZone().getRawOffset(); // in ms
        int minuteOffset = msOffset / (60 * 1000);

        byte[] bytesOffset = Property.intToBytes(minuteOffset, 2);
        bytes[9] = bytesOffset[0];
        bytes[10] = bytesOffset[1];
    }
}
