/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
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

    public PersonDetected(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
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