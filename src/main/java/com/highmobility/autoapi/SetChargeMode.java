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
import com.highmobility.autoapi.property.charging.ChargeMode;

/**
 * Set the charge mode of the car.
 */
public class SetChargeMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x15);
    private static final byte PROPERTY_IDENTIFIER = 0x01;
    Property<ChargeMode> chargeMode = new Property<>(ChargeMode.class,
            PROPERTY_IDENTIFIER);

    /**
     * @return The charge mode.
     */
    public Property<ChargeMode> getChargeMode() {
        return chargeMode;
    }

    /**
     * Set the charge mode of the car.
     *
     * @param chargeMode The charge mode.
     * @throws IllegalArgumentException for {@link ChargeMode#IMMEDIATE}.
     */
    public SetChargeMode(ChargeMode chargeMode) {
        super(TYPE);
        if (chargeMode == ChargeMode.IMMEDIATE) throw new IllegalArgumentException();
        this.chargeMode.update(chargeMode);
        createBytes(this.chargeMode);
    }

    SetChargeMode(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == PROPERTY_IDENTIFIER) {
                    return chargeMode.update(p);
                }

                return null;
            });
        }
    }
}
