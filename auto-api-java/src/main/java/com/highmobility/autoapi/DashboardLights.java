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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DashboardLight;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Dashboard Lights capability
 */
public class DashboardLights {
    public static final int IDENTIFIER = Identifier.DASHBOARD_LIGHTS;

    public static final byte PROPERTY_DASHBOARD_LIGHTS = 0x01;
    public static final byte PROPERTY_BULB_FAILURES = 0x02;

    /**
     * Get Dashboard Lights property availability information
     */
    public static class GetDashboardLightsAvailability extends GetAvailabilityCommand {
        /**
         * Get every Dashboard Lights property availability
         */
        public GetDashboardLightsAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Dashboard Lights property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetDashboardLightsAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Dashboard Lights property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetDashboardLightsAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetDashboardLightsAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get dashboard lights
     */
    public static class GetDashboardLights extends GetCommand<State> {
        /**
         * Get all Dashboard Lights properties
         */
        public GetDashboardLights() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Dashboard Lights properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetDashboardLights(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Dashboard Lights properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetDashboardLights(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetDashboardLights(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Dashboard Lights properties
     * 
     * @deprecated use {@link GetDashboardLights#GetDashboardLights(byte...)} instead
     */
    @Deprecated
    public static class GetDashboardLightsProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetDashboardLightsProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetDashboardLightsProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetDashboardLightsProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The dashboard lights state
     */
    public static class State extends SetCommand {
        List<Property<DashboardLight>> dashboardLights;
        List<Property<BulbFailures>> bulbFailures;
    
        /**
         * @return The dashboard lights
         */
        public List<Property<DashboardLight>> getDashboardLights() {
            return dashboardLights;
        }
    
        /**
         * @return Vehicle light bulb failure
         */
        public List<Property<BulbFailures>> getBulbFailures() {
            return bulbFailures;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
    
            final ArrayList<Property<DashboardLight>> dashboardLightsBuilder = new ArrayList<>();
            final ArrayList<Property<BulbFailures>> bulbFailuresBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DASHBOARD_LIGHTS:
                            Property<DashboardLight> dashboardLight = new Property<>(DashboardLight.class, p);
                            dashboardLightsBuilder.add(dashboardLight);
                            return dashboardLight;
                        case PROPERTY_BULB_FAILURES:
                            Property<BulbFailures> bulbFailure = new Property<>(BulbFailures.class, p);
                            bulbFailuresBuilder.add(bulbFailure);
                            return bulbFailure;
                    }
    
                    return null;
                });
            }
    
            dashboardLights = dashboardLightsBuilder;
            bulbFailures = bulbFailuresBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            dashboardLights = builder.dashboardLights;
            bulbFailures = builder.bulbFailures;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private final List<Property<DashboardLight>> dashboardLights = new ArrayList<>();
            private final List<Property<BulbFailures>> bulbFailures = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of dashboard lights
             * 
             * @param dashboardLights The dashboard lights
             * @return The builder
             */
            public Builder setDashboardLights(Property<DashboardLight>[] dashboardLights) {
                this.dashboardLights.clear();
                for (int i = 0; i < dashboardLights.length; i++) {
                    addDashboardLight(dashboardLights[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single dashboard light
             * 
             * @param dashboardLight The dashboard light
             * @return The builder
             */
            public Builder addDashboardLight(Property<DashboardLight> dashboardLight) {
                dashboardLight.setIdentifier(PROPERTY_DASHBOARD_LIGHTS);
                addProperty(dashboardLight);
                dashboardLights.add(dashboardLight);
                return this;
            }
            
            /**
             * Add an array of bulb failures
             * 
             * @param bulbFailures The bulb failures. Vehicle light bulb failure
             * @return The builder
             */
            public Builder setBulbFailures(Property<BulbFailures>[] bulbFailures) {
                this.bulbFailures.clear();
                for (int i = 0; i < bulbFailures.length; i++) {
                    addBulbFailure(bulbFailures[i]);
                }
            
                return this;
            }
            /**
             * Add a single bulb failure
             * 
             * @param bulbFailure The bulb failure. Vehicle light bulb failure
             * @return The builder
             */
            public Builder addBulbFailure(Property<BulbFailures> bulbFailure) {
                bulbFailure.setIdentifier(PROPERTY_BULB_FAILURES);
                addProperty(bulbFailure);
                bulbFailures.add(bulbFailure);
                return this;
            }
        }
    }

    public enum BulbFailures implements ByteEnum {
        /**
         * Any left turn signal
         */
        TURN_SIGNAL_LEFT((byte) 0x00),
        /**
         * Any right turn signal
         */
        TURN_SIGNAL_RIGHT((byte) 0x01),
        /**
         * Any low beam
         */
        LOW_BEAM((byte) 0x02),
        LOW_BEAM_LEFT((byte) 0x03),
        LOW_BEAM_RIGHT((byte) 0x04),
        /**
         * Any high beam
         */
        HIGH_BEAM((byte) 0x05),
        HIGH_BEAM_LEFT((byte) 0x06),
        HIGH_BEAM_RIGHT((byte) 0x07),
        /**
         * Any front fog light
         */
        FOG_LIGHT_FRONT((byte) 0x08),
        /**
         * Any rear fog light
         */
        FOG_LIGHT_REAR((byte) 0x09),
        /**
         * Any stop light
         */
        STOP((byte) 0x0a),
        /**
         * Any position light
         */
        POSITION((byte) 0x0b),
        /**
         * Any day light running light
         */
        DAY_RUNNING((byte) 0x0c),
        /**
         * Any trailer turn light
         */
        TRAILER_TURN((byte) 0x0d),
        /**
         * Any left trailer turn signal
         */
        TRAILER_TURN_LEFT((byte) 0x0e),
        /**
         * Any right trailer turn signal
         */
        TRAILER_TURN_RIGHT((byte) 0x0f),
        /**
         * Any trailer stop light
         */
        TRAILER_STOP((byte) 0x10),
        TRAILER_ELECTRICAL_FAILURE((byte) 0x11),
        MULTIPLE((byte) 0x12);
    
        public static BulbFailures fromByte(byte byteValue) throws CommandParseException {
            BulbFailures[] values = BulbFailures.values();
    
            for (int i = 0; i < values.length; i++) {
                BulbFailures state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(BulbFailures.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        BulbFailures(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}