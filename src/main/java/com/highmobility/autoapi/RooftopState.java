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
 * This is an evented message that is sent from the car every time the rooftop state changes. This
 * message is also sent when a Get Rooftop State is received by the car.
 */
public class RooftopState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x01);

    Float dimmingPercentage;
    Float openPercentage;

    /**
     *
     * @return The dim percentage of the rooftop.
     */
    public Float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     *
     * @return The percentage of how much the rooftop is open.
     */
    public Float getOpenPercentage() {
        return openPercentage;
    }

    RooftopState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    dimmingPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x02:
                    openPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }
}
