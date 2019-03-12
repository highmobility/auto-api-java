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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.windscreen.WindscreenReplacementState;

/**
 * Set if the windscreen needs replacement. The result is sent through the Windscreen State
 * message.
 */
public class SetWindscreenReplacementNeeded extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x13);
    private static final byte IDENTIFIER = 0x01;

    private Property<WindscreenReplacementState> state =
            new Property(WindscreenReplacementState.class, IDENTIFIER);

    /**
     * @return The windscreen replacement state.
     */
    public Property<WindscreenReplacementState> getState() {
        return state;
    }

    /**
     * @param state The windscreen replacement state.
     */
    public SetWindscreenReplacementNeeded(WindscreenReplacementState state) {
        super(TYPE);
        this.state.update(state);
        createBytes(this.state);
    }

    SetWindscreenReplacementNeeded(byte[] bytes) {
        super(bytes);
        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return state.update(p);
                }
                return null;
            });
        }
    }
}
