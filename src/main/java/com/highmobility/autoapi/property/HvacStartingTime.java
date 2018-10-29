package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.value.Time;

import java.util.Arrays;

public class HvacStartingTime extends Property {
    Time time;
    Weekday weekday;

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    /**
     * @return The weekday.
     */
    public Weekday getWeekday() {
        return weekday;
    }

    public HvacStartingTime(Weekday weekday, Time time) {
        super((byte) 0x00, getInitBytes(weekday, time));
    }

    static byte[] getInitBytes(Weekday weekday, Time time) {
        byte[] bytes = new byte[3];
        bytes[0] = weekday.getByte();
        bytes[1] = time.getByteArray()[0];
        bytes[2] = time.getByteArray()[1];
        return bytes;
    }

    public HvacStartingTime(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 6) throw new IllegalArgumentException();
        this.weekday = Weekday.fromByte(bytes[3]);
        this.time = new Time(Arrays.copyOfRange(bytes, 4, 6));
        this.bytes = bytes;
    }

    @Override public boolean equals(Object obj) {
        if (obj instanceof HvacStartingTime == false) return false;
        HvacStartingTime otherTime = (HvacStartingTime) obj;
        return otherTime.getTime().getHour() == this.getTime().getHour() && otherTime.getTime()
                .getMinute() == this.getTime().getMinute() && otherTime.getWeekday() == this
                .getWeekday();
    }

    public enum Weekday {
        MONDAY((byte) 0x00),
        TUESDAY((byte) 0x01),
        WEDNESDAY((byte) 0x02),
        THURSDAY((byte) 0x03),
        FRIDAY((byte) 0x04),
        SATURDAY((byte) 0x05),
        SUNDAY((byte) 0x06),
        AUTOMATIC((byte) 0x07);

        public static Weekday fromByte(byte value) throws CommandParseException {
            Weekday[] values = Weekday.values();

            for (int i = 0; i < values.length; i++) {
                Weekday value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Weekday(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}