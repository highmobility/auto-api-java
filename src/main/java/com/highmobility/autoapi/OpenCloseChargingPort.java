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
import com.highmobility.autoapi.value.Position;

/**
 * Open close charging port
 */
public class OpenCloseChargingPort extends SetCommand {
    public static final int IDENTIFIER = Identifier.CHARGING;

    public static final byte IDENTIFIER_CHARGE_PORT_STATE = 0x0b;

    Property<Position> chargePortState = new Property(Position.class, IDENTIFIER_CHARGE_PORT_STATE);

    /**
     * @return The charge port state
     */
    public Property<Position> getChargePortState() {
        return chargePortState;
    }
    
    /**
     * Open close charging port
     *
     * @param chargePortState The charge port state
     */
    public OpenCloseChargingPort(Position chargePortState) {
        super(IDENTIFIER);
    
        addProperty(this.chargePortState.update(chargePortState), true);
    }

    OpenCloseChargingPort(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CHARGE_PORT_STATE: return chargePortState.update(p);
                }
                return null;
            });
        }
        if (this.chargePortState.getValue() == null) 
            throw new NoPropertiesException();
    }
}