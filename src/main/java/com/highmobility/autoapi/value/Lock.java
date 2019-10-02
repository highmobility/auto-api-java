// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Lock extends PropertyValueObject {
    public static final int SIZE = 2;

    Location location;
    LockState lockState;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The lock state.
     */
    public LockState getLockState() {
        return lockState;
    }

    public Lock(Location location, LockState lockState) {
        super(2);
        update(location, lockState);
    }

    public Lock(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Lock() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        lockState = LockState.fromByte(get(bytePosition));
    }

    public void update(Location location, LockState lockState) {
        this.location = location;
        this.lockState = lockState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, lockState.getByte());
    }

    public void update(Lock value) {
        update(value.location, value.lockState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}