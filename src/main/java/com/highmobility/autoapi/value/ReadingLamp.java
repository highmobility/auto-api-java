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

public class ReadingLamp extends PropertyValueObject {
    public static final int SIZE = 2;

    Location location;
    ActiveState activeState;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The active state.
     */
    public ActiveState getActiveState() {
        return activeState;
    }

    public ReadingLamp(Location location, ActiveState activeState) {
        super(2);
        update(location, activeState);
    }

    public ReadingLamp(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public ReadingLamp() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        activeState = ActiveState.fromByte(get(bytePosition));
    }

    public void update(Location location, ActiveState activeState) {
        this.location = location;
        this.activeState = activeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, activeState.getByte());
    }

    public void update(ReadingLamp value) {
        update(value.location, value.activeState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}