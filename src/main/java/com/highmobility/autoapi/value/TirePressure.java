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

public class TirePressure extends PropertyValueObject {
    public static final int SIZE = 5;

    Location location;
    Float pressure;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Tire pressure in BAR.
     */
    public Float getPressure() {
        return pressure;
    }

    public TirePressure(Location location, Float pressure) {
        super(5);
        update(location, pressure);
    }

    public TirePressure(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public TirePressure() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        pressure = Property.getFloat(bytes, bytePosition);
    }

    public void update(Location location, Float pressure) {
        this.location = location;
        this.pressure = pressure;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(pressure));
    }

    public void update(TirePressure value) {
        update(value.location, value.pressure);
    }

    @Override public int getLength() {
        return 1 + 4;
    }
}