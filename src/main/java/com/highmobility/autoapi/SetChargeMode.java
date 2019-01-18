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

import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.Property;

/**
 * Set the charge mode of the car.
 */
public class SetChargeMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x15);
    private static final byte PROPERTY_IDENTIFIER = 0x01;
    ChargeMode.Value chargeMode;

    /**
     * @return The charge mode.
     */
    public ChargeMode.Value getChargeMode() {
        return chargeMode;
    }

    /**
     * Set the charge mode of the car.
     *
     * @param chargeMode The charge mode.
     * @throws IllegalArgumentException for {@link ChargeMode.Value#IMMEDIATE}.
     */
    public SetChargeMode(ChargeMode.Value chargeMode) {
        super(TYPE.addProperty(new ChargeMode(chargeMode).setIdentifier(PROPERTY_IDENTIFIER)));
        if (chargeMode == ChargeMode.Value.IMMEDIATE) throw new IllegalArgumentException();
        this.chargeMode = chargeMode;
    }

    SetChargeMode(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 7) throw new CommandParseException();
        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            if (property.getPropertyIdentifier() == PROPERTY_IDENTIFIER) {
                this.chargeMode = ChargeMode.Value.fromByte(property.getValueByte());
            }
        }
    }
}
