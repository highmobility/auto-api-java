/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
    
        GetWeatherConditions(byte[] bytes) throws CommandParseException {
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