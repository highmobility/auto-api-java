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

package com.highmobility.autoapi.property.doors;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.value.Bytes;

public class DoorPosition extends PropertyValueObject {
    Location location;
    Position position;

    /**
     * @return The door location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The door lock state.
     */
    public Position getPosition() {
        return position;
    }

    public DoorPosition(Location location, Position lock) {
        super(2);
        update(location, lock);
    }

    public DoorPosition() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes bytes) throws CommandParseException {
        super.update(bytes);
        if (getLength() < 2) throw new CommandParseException();
        location = Location.fromByte(get(0));
        position = Position.fromByte(get(1));
    }

    public void update(Location location, Position doorPosition) {
        this.location = location;
        this.position = doorPosition;
        bytes = new byte[2];
        set(0, location.getByte());
        set(1, doorPosition.getByte());
    }

    public void update(DoorPosition value) {
        update(value.location, value.position);
    }
}