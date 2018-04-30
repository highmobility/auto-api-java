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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Command sent when a Get Weather Conditions is received by the car.
 */
public class WeatherConditions extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WEATHER_CONDITIONS, 0x01);
    private static final byte RAIN_IDENTIFIER = 0x01;
    Float rainIntensity;

    /**
     * @return The rain intensity percentage.
     */
    public Float getRainIntensity() {
        return rainIntensity;
    }

    public WeatherConditions(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case RAIN_IDENTIFIER:
                    rainIntensity = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WeatherConditions(Builder builder) {
        super(builder);
        rainIntensity = builder.rainIntensity;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Float rainIntensity;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param rainIntensity The rain intensity percentage.
         * @return The builder.
         */
        public Builder setRainIntensity(Float rainIntensity) {
            this.rainIntensity = rainIntensity;
            addProperty(new IntegerProperty(RAIN_IDENTIFIER, Property.floatToIntPercentage(rainIntensity), 1));
            return this;
        }

        public WeatherConditions build() {
            return new WeatherConditions(this);
        }
    }
}