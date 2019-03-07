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
 * Authenticate or expire the charging session. Only if the session is authenticated can the
 * charging be started by the vehicle.
 */
public class AuthenticateHomeCharger extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x16);
    private static final byte IDENTIFIER = 0x01;

    private Property<Boolean> authenticate = new Property(Boolean.class, IDENTIFIER);

    /**
     * @return Whether to authenticate or not.
     */
    public Property<Boolean> getAuthenticate() {
        return authenticate;
    }

    /**
     * @param authenticate Authentication state.
     */
    public AuthenticateHomeCharger(Boolean authenticate) {
        super(TYPE);

        this.authenticate.update(authenticate);
        createBytes(this.authenticate);
    }

    AuthenticateHomeCharger(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return authenticate.update(p);
                }
                return null;
            });
        }
    }
}
