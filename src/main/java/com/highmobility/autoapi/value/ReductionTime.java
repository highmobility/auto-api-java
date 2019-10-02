// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ReductionTime extends PropertyValueObject {
    public static final int SIZE = 3;

    StartStop startStop;
    Time time;

    /**
     * @return The start stop.
     */
    public StartStop getStartStop() {
        return startStop;
    }

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    public ReductionTime(StartStop startStop, Time time) {
        super(3);
        update(startStop, time);
    }

    public ReductionTime(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public ReductionTime() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        startStop = StartStop.fromByte(get(bytePosition));
        bytePosition += 1;

        int timeSize = Time.SIZE;
        time = new Time();
        time.update(getRange(bytePosition, bytePosition + timeSize));
    }

    public void update(StartStop startStop, Time time) {
        this.startStop = startStop;
        this.time = time;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, startStop.getByte());
        bytePosition += 1;

        set(bytePosition, time);
    }

    public void update(ReductionTime value) {
        update(value.startStop, value.time);
    }

    @Override public int getLength() {
        return 1 + 2;
    }
}