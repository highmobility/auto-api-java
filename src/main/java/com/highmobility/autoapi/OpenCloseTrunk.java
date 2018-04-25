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
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;

import java.util.ArrayList;

/**
 * Unlock/Lock and Open/Close the trunk. The result is received through the evented Trunk State
 * command.
 */
public class OpenCloseTrunk extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x02);

    TrunkLockState state;
    TrunkPosition position;

    /**
     * @return The trunk lock state
     */
    public TrunkLockState getState() {
        return state;
    }

    /**
     * @return The trunk position
     */
    public TrunkPosition getPosition() {
        return position;
    }

    /**
     * Create the command from lock state and position. One of the properties can be null.
     *
     * @param state    The trunk lock state.
     * @param position The trunk position.
     * @throws IllegalArgumentException If all arguments are null
     */
    public OpenCloseTrunk(TrunkLockState state, TrunkPosition position) {
        super(TYPE, getProperties(state, position));
        this.state = state;
        this.position = position;
    }

    static HMProperty[] getProperties(TrunkLockState state, TrunkPosition position) {
        ArrayList<HMProperty> properties = new ArrayList<>();

        if (state != null) {
            properties.add(new ByteProperty(TrunkLockState.defaultIdentifier, state.getByte()));
        }

        if (position != null) {
            properties.add(new ByteProperty(TrunkPosition.defaultIdentifier, position.getByte()));
        }

        return properties.toArray(new HMProperty[properties.size()]);
    }

    OpenCloseTrunk(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            if (property.getPropertyIdentifier() == TrunkLockState.defaultIdentifier) {
                state = TrunkLockState.fromByte(property.getValueByte());
            } else if (property.getPropertyIdentifier() == TrunkPosition.defaultIdentifier) {
                position = TrunkPosition.fromByte(property.getValueByte());
            }
        }
    }
}
