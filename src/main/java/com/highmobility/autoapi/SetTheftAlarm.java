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

/**
 * Unarm or arm the theft alarm of the car. The result is sent through the evented Theft Alarm State
 * message.
 */
public class SetTheftAlarm extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.THEFT_ALARM, 0x12);
    private static final byte IDENTIFIER = 0x01;

    TheftAlarmState.State state;

    /**
     * @return The theft alarm state.
     */
    public TheftAlarmState.State getState() {
        return state;
    }

    /**
     * @param state The theft alarm state.
     */
    public SetTheftAlarm(TheftAlarmState.State state) {
        super(TYPE.addProperty(new Property(IDENTIFIER, state.getByte())));
    }

    SetTheftAlarm(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property prop = getProperty(IDENTIFIER);
        state = TheftAlarmState.State.fromByte(prop.getValueByte());
    }
}
