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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Light extends PropertyValueObject {
    public static final int SIZE = 2;

    LocationLongitudinal locationLongitudinal;
    ActiveState activeState;

    /**
     * @return The location longitudinal.
     */
    public LocationLongitudinal getLocationLongitudinal() {
        return locationLongitudinal;
    }

    /**
     * @return The active state.
     */
    public ActiveState getActiveState() {
        return activeState;
    }

    public Light(LocationLongitudinal locationLongitudinal, ActiveState activeState) {
        super(2);
        update(locationLongitudinal, activeState);
    }

    public Light(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Light() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        locationLongitudinal = LocationLongitudinal.fromByte(get(bytePosition));
        bytePosition += 1;

        activeState = ActiveState.fromByte(get(bytePosition));
    }

    public void update(LocationLongitudinal locationLongitudinal, ActiveState activeState) {
        this.locationLongitudinal = locationLongitudinal;
        this.activeState = activeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, locationLongitudinal.getByte());
        bytePosition += 1;

        set(bytePosition, activeState.getByte());
    }

    public void update(Light value) {
        update(value.locationLongitudinal, value.activeState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}