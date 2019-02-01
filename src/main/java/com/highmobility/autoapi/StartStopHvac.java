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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Start or stop the HVAC system to reach driver and passenger set temperatures. The car will use
 * cooling, defrosting and defogging as appropriate. The result is sent through the evented Climate
 * State message.
 */
public class StartStopHvac extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x13);
    private static final byte IDENTIFIER = 0x01;

    /**
     * @return Whether HVAC should be started.
     */
    public boolean start() {
        return start;
    }

    private final boolean start;

    /**
     * @param start The HVAC state.
     */
    public StartStopHvac(boolean start) {
        super(TYPE.addProperty(new BooleanProperty(start).setIdentifier(IDENTIFIER)));
        this.start = start;
    }

    StartStopHvac(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property prop = getProperty(IDENTIFIER);
        if (prop == null) throw new CommandParseException();
        start = Property.getBool(prop.getValueByte());
    }
}
