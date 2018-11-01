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
 * Authenticate or expire the charging session. Only if the session is authenticated can the
 * charging be started by the vehicle.
 */
public class AuthenticateHomeCharger extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x16);
    private static final byte IDENTIFIER = 0x01;

    private boolean authenticate;

    /**
     * @return Whether to authenticate or not.
     */
    public boolean getAuthenticate() {
        return authenticate;
    }

    /**
     * @param authenticate Authentication state.
     */
    public AuthenticateHomeCharger(boolean authenticate) {
        super(TYPE.addProperty(new BooleanProperty(IDENTIFIER, authenticate)));
        this.authenticate = authenticate;
    }

    AuthenticateHomeCharger(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property prop = getProperty(IDENTIFIER);
        if (prop == null) throw new CommandParseException();
        authenticate = Property.getBool(prop.getValueByte());
    }
}
