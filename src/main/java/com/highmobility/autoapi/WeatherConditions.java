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

import com.highmobility.autoapi.property.PercentageProperty;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Weather Conditions is received by the car.
 */
public class WeatherConditions extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WEATHER_CONDITIONS, 0x01);
    private static final byte RAIN_IDENTIFIER = 0x01;
    PercentageProperty rainIntensity;

    /**
     * @return The rain intensity.
     */
    @Nullable public PercentageProperty getRainIntensity() {
        return rainIntensity;
    }

    WeatherConditions(byte[] bytes) {
        super(bytes);
        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == RAIN_IDENTIFIER) {
                    rainIntensity = new PercentageProperty(p);
                    return rainIntensity;
                }

                return null;
            });
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
        private PercentageProperty rainIntensity;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param rainIntensity The rain intensity percentage.
         * @return The builder.
         */
        public Builder setRainIntensity(PercentageProperty rainIntensity) {
            this.rainIntensity = rainIntensity;
            rainIntensity.setIdentifier(RAIN_IDENTIFIER);
            addProperty(rainIntensity);
            return this;
        }

        public WeatherConditions build() {
            return new WeatherConditions(this);
        }
    }
}