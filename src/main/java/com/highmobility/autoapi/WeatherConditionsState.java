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
 * The weather conditions state
 */
public class WeatherConditionsState extends SetCommand {
    public static final int IDENTIFIER = Identifier.WEATHER_CONDITIONS;

    public static final byte IDENTIFIER_RAIN_INTENSITY = 0x01;

    Property<Double> rainIntensity = new Property(Double.class, IDENTIFIER_RAIN_INTENSITY);

    /**
     * @return Measured raining intensity percentage, whereas 0% is no rain and 100% is maximum rain
     */
    public Property<Double> getRainIntensity() {
        return rainIntensity;
    }

    WeatherConditionsState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_RAIN_INTENSITY: return rainIntensity.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WeatherConditionsState(Builder builder) {
        super(builder);

        rainIntensity = builder.rainIntensity;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Double> rainIntensity;

        public Builder() {
            super(IDENTIFIER);
        }

        public WeatherConditionsState build() {
            return new WeatherConditionsState(this);
        }

        /**
         * @param rainIntensity Measured raining intensity percentage, whereas 0% is no rain and 100% is maximum rain
         * @return The builder
         */
        public Builder setRainIntensity(Property<Double> rainIntensity) {
            this.rainIntensity = rainIntensity.setIdentifier(IDENTIFIER_RAIN_INTENSITY);
            addProperty(this.rainIntensity);
            return this;
        }
    }
}