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

public class PersonDetected extends Property {
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
        super(IDENTIFIER, 2);
        this.seatLocation = seatLocation;
        this.detected = detected;
        this.bytes[6] = seatLocation.getByte();
        this.bytes[7] = Property.boolToByte(detected);
    }

    public PersonDetected(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);

        if (p.getValueComponent().getValueBytes().getLength() >= 2) {
            this.seatLocation = SeatLocation.fromByte(p.getValueComponent().getValueBytes().get(0));
            this.detected = Property.getBool(p.getValueComponent().getValueBytes().get(1));
        }

        return this;
    }
}
