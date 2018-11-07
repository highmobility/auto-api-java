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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindscreenReplacementState;

/**
 * Set if the windscreen needs replacement. The result is sent through the Windscreen State
 * message.
 */
public class SetWindscreenReplacementNeeded extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x13);
    private static final byte IDENTIFIER = 0x01;

    private WindscreenReplacementState state;

    /**
     * @return The windscreen replacement state.
     */
    public WindscreenReplacementState getState() {
        return state;
    }

    /**
     * @param state The windscreen replacement state.
     */
    public SetWindscreenReplacementNeeded(WindscreenReplacementState state) {
        super(TYPE.addProperty(getProperty(state)));
        this.state = state;
    }

    static HMProperty getProperty(WindscreenReplacementState state) {
        return new Property(IDENTIFIER, state.getByte());
    }

    SetWindscreenReplacementNeeded(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property prop = getProperty(IDENTIFIER);
        if (prop == null) throw new CommandParseException();
        state = WindscreenReplacementState.fromByte(prop.getValueByte());
    }
}
