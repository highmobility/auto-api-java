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

public class SeatBeltFastened extends PropertyValueObject {
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
        super(2);
        update(seatLocation, fastened);
    }

    public SeatBeltFastened() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();
        seatLocation = SeatLocation.fromByte(get(0));
        fastened = Property.getBool(get(1));
    }

    public void update(SeatLocation seatLocation, boolean fastened) {
        this.seatLocation = seatLocation;
        this.fastened = fastened;
        bytes = new byte[2];

        set(0, seatLocation.getByte());
        set(1, Property.boolToByte(fastened));
    }

    public void update(SeatBeltFastened value) {
        update(value.seatLocation, value.fastened);
    }
}
