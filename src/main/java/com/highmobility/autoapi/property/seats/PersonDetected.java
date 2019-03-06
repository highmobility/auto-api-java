/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
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

package com.highmobility.autoapi.property.seats;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class PersonDetected extends PropertyValueObject {
    public static final byte IDENTIFIER = 0x02;

    SeatLocation seatLocation;
    boolean detected;

    /**
     * @return The seat location.
     */
    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    /**
     * @return Whether a person is detected.
     */
    public boolean isDetected() {
        return detected;
    }

    public PersonDetected(SeatLocation seatLocation, boolean detected) {
        super(2);
        update(seatLocation, detected);
    }

    public PersonDetected() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();
        seatLocation = SeatLocation.fromByte(get(0));
        detected = Property.getBool(get(1));
    }

    public void update(SeatLocation seatLocation, boolean detected) {
        this.seatLocation = seatLocation;
        this.detected = detected;
        bytes = new byte[2];

        set(0, seatLocation.getByte());
        set(1, Property.boolToByte(detected));
    }

    public void update(PersonDetected value) {
        update(value.seatLocation, value.detected);
    }
}
