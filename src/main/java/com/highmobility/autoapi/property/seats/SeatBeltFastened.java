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

public class SeatBeltFastened extends Property {
    public static final byte IDENTIFIER = 0x03;

    SeatLocation seatLocation;
    boolean fastened;

    /**
     * @return The seat location.
     */
    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    /**
     * @return Whether a person is detected.
     */
    public boolean isFastened() {
        return fastened;
    }

    public SeatBeltFastened(SeatLocation seatLocation, boolean fastened) {
        super(IDENTIFIER, 2);
        this.seatLocation = seatLocation;
        this.fastened = fastened;
        this.bytes[6] = seatLocation.getByte();
        this.bytes[7] = Property.boolToByte(fastened);
    }

    public SeatBeltFastened(Property bytes) throws CommandParseException {
        super(bytes);
        update(bytes);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);

        if (p.getValueLength() >= 2) {
            this.seatLocation = SeatLocation.fromByte(p.getValueBytes().get(3));
            this.fastened = Property.getBool(p.getValueBytes().get(4));
        }

        return this;
    }
}
