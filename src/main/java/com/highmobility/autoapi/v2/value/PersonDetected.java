// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class PersonDetected extends PropertyValueObject {
    public static final int SIZE = 2;

    SeatLocation seatLocation;
    Detected detected;

    /**
     * @return The seat location.
     */
    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    /**
     * @return The detected.
     */
    public Detected getDetected() {
        return detected;
    }

    public PersonDetected(SeatLocation seatLocation, Detected detected) {
        super(2);
        update(seatLocation, detected);
    }

    public PersonDetected() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        seatLocation = SeatLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        detected = Detected.fromByte(get(bytePosition));
    }

    public void update(SeatLocation seatLocation, Detected detected) {
        this.seatLocation = seatLocation;
        this.detected = detected;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, seatLocation.getByte());
        bytePosition += 1;

        set(bytePosition, detected.getByte());
    }

    public void update(PersonDetected value) {
        update(value.seatLocation, value.detected);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}