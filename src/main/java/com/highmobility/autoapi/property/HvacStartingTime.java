package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.autoapi.property.value.Weekday;

import java.util.Arrays;
import java.util.Calendar;

import javax.annotation.Nullable;

public class HvacStartingTime extends Property {
    Value value;

    public Value getValue() {
        return value;
    }

    public HvacStartingTime(@Nullable Value value, @Nullable Calendar timestamp,
                            @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public HvacStartingTime(Value value) {
        this((byte) 0x00, value);
    }

    public HvacStartingTime(byte identifier, Value value) {
        super(identifier, value == null ? 0 : 2);
        if (value != null) setBytes(value);
    }

    public HvacStartingTime(Weekday weekday, Time time) {
        this((byte) 0x00, weekday, time);
    }

    public HvacStartingTime(byte identifier) {
        super(identifier);
    }

    public HvacStartingTime(byte identifier, Weekday weekday, Time time) {
        super(identifier, 3);
        setBytes(new Value(weekday, time));
    }

    public HvacStartingTime(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    void setBytes(Value value) {
        bytes[3] = value.weekday.getByte();
        bytes[4] = value.time.getByteArray()[0];
        bytes[5] = value.time.getByteArray()[1];
        this.value = value;
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 3) value = new Value(p);
        return this;
    }

    public static class Value {
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

        public Value(Weekday weekday, Time time) {
            this.weekday = weekday;
            this.time = time;
        }

        public Value(Property p) throws CommandParseException {
            this(p.getByteArray());
        }

        public Value(byte[] bytes) throws CommandParseException {
            if (bytes.length < 6) throw new IllegalArgumentException();
            this.weekday = Weekday.fromByte(bytes[3]);
            this.time = new Time(Arrays.copyOfRange(bytes, 4, 6));
        }

        @Override public boolean equals(Object obj) {
            if (obj instanceof HvacStartingTime == false) return false;
            HvacStartingTime.Value otherTime = (HvacStartingTime.Value) obj;
            return otherTime.getTime().getHour() == this.getTime().getHour() && otherTime.getTime()
                    .getMinute() == this.getTime().getMinute() && otherTime.getWeekday() == this
                    .getWeekday();
        }
    }

}