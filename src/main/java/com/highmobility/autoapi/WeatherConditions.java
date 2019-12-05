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
 * The Weather Conditions capability
 */
public class WeatherConditions {
    public static final int IDENTIFIER = Identifier.WEATHER_CONDITIONS;

    public static final byte PROPERTY_RAIN_INTENSITY = 0x01;

    /**
     * Get weather conditions
     */
    public static class GetWeatherConditions extends GetCommand {
        public GetWeatherConditions() {
            super(IDENTIFIER);
        }
    
        GetWeatherConditions(byte[] bytes) {
            super(bytes);
        }
    }

    /**
     * The weather conditions state
     */
    public static class State extends SetCommand {
        Property<Double> rainIntensity = new Property(Double.class, PROPERTY_RAIN_INTENSITY);
    
        /**
         * @return Measured raining intensity percentage, whereas 0% is no rain and 100% is maximum rain
         */
        public Property<Double> getRainIntensity() {
            return rainIntensity;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_RAIN_INTENSITY: return rainIntensity.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            rainIntensity = builder.rainIntensity;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Double> rainIntensity;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param rainIntensity Measured raining intensity percentage, whereas 0% is no rain and 100% is maximum rain
             * @return The builder
             */
            public Builder setRainIntensity(Property<Double> rainIntensity) {
                this.rainIntensity = rainIntensity.setIdentifier(PROPERTY_RAIN_INTENSITY);
                addProperty(this.rainIntensity);
                return this;
            }
        }
    }
}