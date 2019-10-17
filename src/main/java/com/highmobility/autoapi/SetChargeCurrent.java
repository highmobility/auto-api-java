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
 * Set charge current
 */
public class SetChargeCurrent extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.HOME_CHARGER;

    public static final byte IDENTIFIER_CHARGE_CURRENT_DC = 0x0e;

    Property<Float> chargeCurrentDC = new Property(Float.class, IDENTIFIER_CHARGE_CURRENT_DC);

    /**
     * @return The charge current dc
     */
    public Property<Float> getChargeCurrentDC() {
        return chargeCurrentDC;
    }
    
    /**
     * Set charge current
     *
     * @param chargeCurrentDC The charge direct current
     */
    public SetChargeCurrent(Float chargeCurrentDC) {
        super(IDENTIFIER);
    
        addProperty(this.chargeCurrentDC.update(chargeCurrentDC), true);
    }

    SetChargeCurrent(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CHARGE_CURRENT_DC: return chargeCurrentDC.update(p);
                }
                return null;
            });
        }
        if (this.chargeCurrentDC.getValue() == null) 
            throw new NoPropertiesException();
    }
}