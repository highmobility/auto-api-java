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

import com.highmobility.autoapi.ChargingState.ChargeMode;
import com.highmobility.autoapi.property.Property;

/**
 * Set charge mode
 */
public class SetChargeMode extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.CHARGING;

    public static final byte IDENTIFIER_CHARGE_MODE = 0x0c;

    Property<ChargeMode> chargeMode = new Property(ChargeMode.class, IDENTIFIER_CHARGE_MODE);

    /**
     * @return The charge mode
     */
    public Property<ChargeMode> getChargeMode() {
        return chargeMode;
    }
    
    /**
     * Set charge mode
     *
     * @param chargeMode The charge mode
     */
    public SetChargeMode(ChargeMode chargeMode) {
        super(IDENTIFIER);
    
        if (chargeMode == ChargeMode.INDUCTIVE) throw new IllegalArgumentException();
    
        addProperty(this.chargeMode.update(chargeMode), true);
    }

    SetChargeMode(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CHARGE_MODE: return chargeMode.update(p);
                }
                return null;
            });
        }
        if (this.chargeMode.getValue() == null) 
            throw new NoPropertiesException();
    }
}