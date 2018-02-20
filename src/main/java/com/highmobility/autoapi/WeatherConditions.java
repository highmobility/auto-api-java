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
 * This message is sent when a Get Weather Conditions is received by the car.
 */
public class WeatherConditions extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.WEATHER_CONDITIONS, 0x01);

    Float rainIntensity;

    /**
     * @return Measured raining intensity percentage, whereas 0 is no rain and 1 is maximum rain
     */
    public Float getRainIntensity() {
        return rainIntensity;
    }

    public WeatherConditions(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    rainIntensity = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }
}