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

import com.highmobility.autoapi.property.ChargePortState;
import com.highmobility.autoapi.property.Property;

/**
 * Open or close the charge port of the car. This is possible even if the car is locked.
 */
public class OpenCloseChargePort extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x14);
    private static final byte PROPERTY_IDENTIFIER = 0x01;
    ChargePortState chargePortState;

    /**
     * @return The charge port state.
     */
    public ChargePortState getChargePortState() {
        return chargePortState;
    }

    public OpenCloseChargePort(ChargePortState chargePortState) {
        super(getBytes(chargePortState));
        this.chargePortState = chargePortState;
    }

    static byte[] getBytes(ChargePortState chargePortState) {
        chargePortState.setIdentifier(PROPERTY_IDENTIFIER);
        return TYPE.addProperty(new Property(PROPERTY_IDENTIFIER, chargePortState.getByte()));
    }

    OpenCloseChargePort(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case PROPERTY_IDENTIFIER:
                    chargePortState = ChargePortState.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}
