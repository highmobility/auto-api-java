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

import com.highmobility.autoapi.property.DoubleProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Set the charge limit, to which point the car will charge itself. The result is sent through the
 * evented Charge State command.
 */
public class SetChargeLimit extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x13);
    private static final byte PROPERTY_IDENTIFIER = 0x01;
    Double percentage;

    /**
     * @return The charge limit percentage.
     */
    public Double getChargeLimit() {
        return percentage;
    }

    /**
     * Get the set charge limit command bytes.
     *
     * @param percentage The charge limit percentage.
     */
    public SetChargeLimit(Double percentage) throws IllegalArgumentException {
        super(TYPE.addProperty(new DoubleProperty(PROPERTY_IDENTIFIER, percentage)));
        this.percentage = percentage;
    }

    SetChargeLimit(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case PROPERTY_IDENTIFIER:
                    percentage = Property.getDouble(property.getValueBytes());
                    break;
            }
        }
    }
}
