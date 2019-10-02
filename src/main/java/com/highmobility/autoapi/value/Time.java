// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Time extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer hour;
    Integer minute;

    /**
     * @return Value between 0 and 23.
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * @return Value between 0 and 59.
     */
    public Integer getMinute() {
        return minute;
    }

    public Time(Integer hour, Integer minute) {
        super(2);
        update(hour, minute);
    }

    public Time(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Time() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        hour = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        minute = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(hour, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(minute, 1));
    }

    public void update(Time value) {
        update(value.hour, value.minute);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}