/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
 * Set charge limit
 */
public class SetChargeLimit extends SetCommand {
    public static final Identifier identifier = Identifier.CHARGING;

    Property<Double> chargeLimit = new Property(Double.class, 0x08);

    /**
     * @return The charge limit
     */
    public Property<Double> getChargeLimit() {
        return chargeLimit;
    }
    
    /**
     * Set charge limit
     *
     * @param chargeLimit Charge limit percentage between 0.0-1.0
     */
    public SetChargeLimit(Double chargeLimit) {
        super(identifier);
    
        addProperty(this.chargeLimit.update(chargeLimit), true);
    }

    SetChargeLimit(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x08: return chargeLimit.update(p);
                }
                return null;
            });
        }
        if (this.chargeLimit.getValue() == null) 
            throw new NoPropertiesException();
    }
}