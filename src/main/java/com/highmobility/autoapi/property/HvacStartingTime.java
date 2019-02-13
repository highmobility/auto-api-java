package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.autoapi.property.value.Weekday;
import com.highmobility.value.Bytes;

public class HvacStartingTime extends PropertyValueObject {
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
        super(3);

        set(0, weekday.getByte());
        set(1, time.getByteArray());
        this.weekday = weekday;
        this.time = time;
    }

    public HvacStartingTime() {
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        this.weekday = Weekday.fromByte(get(0));
        time = new Time(getRange(1, 3));
    }

    @Override public boolean equals(Object obj) {
        if (obj instanceof HvacStartingTime == false) return false;
        HvacStartingTime otherTime = (HvacStartingTime) obj;
        return otherTime.getTime().getHour() == this.getTime().getHour() && otherTime.getTime()
                .getMinute() == this.getTime().getMinute() && otherTime.getWeekday() == this
                .getWeekday();
    }
}