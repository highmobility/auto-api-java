// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class BrakeTorqueVectoring extends PropertyValueObject {
    public static final int SIZE = 2;

    Axle axle;
    ActiveState activeState;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     * @return The active state.
     */
    public ActiveState getActiveState() {
        return activeState;
    }

    public BrakeTorqueVectoring(Axle axle, ActiveState activeState) {
        super(2);
        update(axle, activeState);
    }

    public BrakeTorqueVectoring() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        axle = Axle.fromByte(get(bytePosition));
        bytePosition += 1;

        activeState = ActiveState.fromByte(get(bytePosition));
    }

    public void update(Axle axle, ActiveState activeState) {
        this.axle = axle;
        this.activeState = activeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, axle.getByte());
        bytePosition += 1;

        set(bytePosition, activeState.getByte());
    }

    public void update(BrakeTorqueVectoring value) {
        update(value.axle, value.activeState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}