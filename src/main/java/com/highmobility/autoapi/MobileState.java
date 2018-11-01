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

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Mobile State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class MobileState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MOBILE, 0x01);
    private static final byte IDENTIFIER = 0x01;
    Boolean connected;

    /**
     * @return Whether mobile phone is connected.
     */
    @Nullable public Boolean isConnected() {
        // not mandatory
        return connected;
    }

    public MobileState(byte[] bytes) {
        super(bytes);

        Property prop = getProperty(IDENTIFIER);
        if (prop == null) return;
        connected = Property.getBool(prop.getValueByte());
    }

    @Override public boolean isState() {
        return true;
    }

    // TBODO:
    /*private MobileState(Builder builder) {
        super(builder);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        public Builder() {
            super(TYPE);
        }

        public MobileState build() {
            return new MobileState(this);
        }
    }*/
}