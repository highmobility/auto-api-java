// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DepartureTime extends PropertyValueObject {
    public static final int SIZE = 3;

    ActiveState activeState;
    Time time;

    /**
     * @return The active state.
     */
    public ActiveState getActiveState() {
        return activeState;
    }

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    public DepartureTime(ActiveState activeState, Time time) {
        super(3);
        update(activeState, time);
    }

    public DepartureTime() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        activeState = ActiveState.fromByte(get(bytePosition));
        bytePosition += 1;

        int timeSize = Time.SIZE;
        time = new Time();
        time.update(getRange(bytePosition, bytePosition + timeSize));
    }

    public void update(ActiveState activeState, Time time) {
        this.activeState = activeState;
        this.time = time;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, activeState.getByte());
        bytePosition += 1;

        set(bytePosition, time);
    }

    public void update(DepartureTime value) {
        update(value.activeState, value.time);
    }

    @Override public int getLength() {
        return 1 + 2;
    }
}