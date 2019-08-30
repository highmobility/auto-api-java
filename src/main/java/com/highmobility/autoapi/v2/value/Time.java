// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Time extends PropertyValueObject {
    public static final int SIZE = 2;

    int hour;
    int minute;

    /**
     * @return Value between 0 and 23.
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return Value between 0 and 59.
     */
    public int getMinute() {
        return minute;
    }

    public Time(int hour, int minute) {
        super(2);
        update(hour, minute);
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

    public void update(int hour, int minute) {
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