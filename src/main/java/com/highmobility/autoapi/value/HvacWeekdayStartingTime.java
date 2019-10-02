// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class HvacWeekdayStartingTime extends PropertyValueObject {
    public static final int SIZE = 3;

    Weekday weekday;
    Time time;

    /**
     * @return The weekday.
     */
    public Weekday getWeekday() {
        return weekday;
    }

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    public HvacWeekdayStartingTime(Weekday weekday, Time time) {
        super(3);
        update(weekday, time);
    }

    public HvacWeekdayStartingTime(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public HvacWeekdayStartingTime() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        weekday = Weekday.fromByte(get(bytePosition));
        bytePosition += 1;

        int timeSize = Time.SIZE;
        time = new Time();
        time.update(getRange(bytePosition, bytePosition + timeSize));
    }

    public void update(Weekday weekday, Time time) {
        this.weekday = weekday;
        this.time = time;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, weekday.getByte());
        bytePosition += 1;

        set(bytePosition, time);
    }

    public void update(HvacWeekdayStartingTime value) {
        update(value.weekday, value.time);
    }

    @Override public int getLength() {
        return 1 + 2;
    }

    public enum Weekday implements ByteEnum {
        MONDAY((byte) 0x00),
        TUESDAY((byte) 0x01),
        WEDNESDAY((byte) 0x02),
        THURSDAY((byte) 0x03),
        FRIDAY((byte) 0x04),
        SATURDAY((byte) 0x05),
        SUNDAY((byte) 0x06),
        AUTOMATIC((byte) 0x07);
    
        public static Weekday fromByte(byte byteValue) throws CommandParseException {
            Weekday[] values = Weekday.values();
    
            for (int i = 0; i < values.length; i++) {
                Weekday state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Weekday(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}