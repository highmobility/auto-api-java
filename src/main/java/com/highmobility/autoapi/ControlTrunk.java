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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteProperty;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Lock;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Unlock/Lock and Open/Close the trunk. The result is received through the evented Trunk State
 * command.
 */
public class ControlTrunk extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x12);

    private static final byte IDENTIFIER_LOCK = 0x01;
    private static final byte IDENTIFIER_POSITION = 0x02;

    Lock.Value lock;
    Position.Value position;

    /**
     * @return The trunk lock state.
     */
    @Nullable public Lock.Value getLock() {
        return lock;
    }

    /**
     * @return The trunk position.
     */
    @Nullable public Position.Value getPosition() {
        return position;
    }

    /**
     * Create the command from lock state and position. One of the properties can be null.
     *
     * @param lock     The trunk lock state.
     * @param position The trunk position.
     * @throws IllegalArgumentException If all arguments are null
     */
    public ControlTrunk(@Nullable Lock.Value lock, @Nullable Position.Value position) {
        super(TYPE, getProperties(lock, position));
        this.lock = lock;
        this.position = position;
    }

    static Property[] getProperties(Lock.Value state, Position.Value position) {
        ArrayList<Property> properties = new ArrayList<>();

        if (state != null) {
            properties.add(new ByteProperty(IDENTIFIER_LOCK, state.getByte()));
        }

        if (position != null) {
            properties.add(new ByteProperty(IDENTIFIER_POSITION, position.getByte()));
        }

        return properties.toArray(new Property[0]);
    }

    ControlTrunk(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER_LOCK) {
                    lock = Lock.Value.fromByte(p.getValueByte());
                    return lock;
                } else if (p.getPropertyIdentifier() == IDENTIFIER_POSITION) {
                    position = Position.Value.fromByte(p.getValueByte());
                    return position;
                }
                return null;
            });
        }
    }
}
