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

package com.highmobility.autoapi.value.doors;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.value.Bytes;

public class DoorLockState extends PropertyValueObject {
    Location location;
    Lock doorLock;

    /**
     * @return The door location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The door lock state.
     */
    public Lock getLock() {
        return doorLock;
    }

    public DoorLockState(Location location, Lock lock) {
        super(2);
        update(location, lock);
    }

    public DoorLockState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes bytes) throws CommandParseException {
        super.update(bytes);
        if (getLength() < 2) throw new CommandParseException();
        location = Location.fromByte(get(0));
        doorLock = Lock.fromByte(get(1));
    }

    public void update(Location location, Lock doorLock) {
        this.location = location;
        this.doorLock = doorLock;
        bytes = new byte[2];
        set(0, location.getByte());
        set(1, doorLock.getByte());
    }

    public void update(DoorLockState value) {
        update(value.location, value.doorLock);
    }
}