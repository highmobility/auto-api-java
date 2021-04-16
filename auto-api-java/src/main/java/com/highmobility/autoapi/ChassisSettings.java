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
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.SpringRate;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Chassis Settings capability
 */
public class ChassisSettings {
    public static final int IDENTIFIER = Identifier.CHASSIS_SETTINGS;

    public static final byte PROPERTY_DRIVING_MODE = 0x01;
    public static final byte PROPERTY_SPORT_CHRONO = 0x02;
    public static final byte PROPERTY_CURRENT_SPRING_RATES = 0x05;
    public static final byte PROPERTY_MAXIMUM_SPRING_RATES = 0x06;
    public static final byte PROPERTY_MINIMUM_SPRING_RATES = 0x07;
    public static final byte PROPERTY_CURRENT_CHASSIS_POSITION = 0x08;
    public static final byte PROPERTY_MAXIMUM_CHASSIS_POSITION = 0x09;
    public static final byte PROPERTY_MINIMUM_CHASSIS_POSITION = 0x0a;

    /**
     * Get Chassis Settings property availability information
     */
    public static class GetChassisSettingsAvailability extends GetAvailabilityCommand {
        /**
         * Get every Chassis Settings property availability
         */
        public GetChassisSettingsAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Chassis Settings property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetChassisSettingsAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Chassis Settings property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetChassisSettingsAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetChassisSettingsAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get chassis settings
     */
    public static class GetChassisSettings extends GetCommand<State> {
        /**
         * Get all Chassis Settings properties
         */
        public GetChassisSettings() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Chassis Settings properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetChassisSettings(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Chassis Settings properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetChassisSettings(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetChassisSettings(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Chassis Settings properties
     * 
     * @deprecated use {@link GetChassisSettings#GetChassisSettings(byte...)} instead
     */
    @Deprecated
    public static class GetChassisSettingsProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetChassisSettingsProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetChassisSettingsProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetChassisSettingsProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Set driving mode
     */
    public static class SetDrivingMode extends SetCommand {
        Property<DrivingMode> drivingMode = new Property<>(DrivingMode.class, PROPERTY_DRIVING_MODE);
    
        /**
         * @return The driving mode
         */
        public Property<DrivingMode> getDrivingMode() {
            return drivingMode;
        }
        
        /**
         * Set driving mode
         * 
         * @param drivingMode The driving mode
         */
        public SetDrivingMode(DrivingMode drivingMode) {
            super(IDENTIFIER);
        
            addProperty(this.drivingMode.update(drivingMode));
            createBytes();
        }
    
        SetDrivingMode(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_DRIVING_MODE) return drivingMode.update(p);
                    
                    return null;
                });
            }
            if (this.drivingMode.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Start stop sports chrono
     */
    public static class StartStopSportsChrono extends SetCommand {
        Property<SportChrono> sportChrono = new Property<>(SportChrono.class, PROPERTY_SPORT_CHRONO);
    
        /**
         * @return The sport chrono
         */
        public Property<SportChrono> getSportChrono() {
            return sportChrono;
        }
        
        /**
         * Start stop sports chrono
         * 
         * @param sportChrono The sport chrono
         */
        public StartStopSportsChrono(SportChrono sportChrono) {
            super(IDENTIFIER);
        
            addProperty(this.sportChrono.update(sportChrono));
            createBytes();
        }
    
        StartStopSportsChrono(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_SPORT_CHRONO) return sportChrono.update(p);
                    
                    return null;
                });
            }
            if (this.sportChrono.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set spring rates
     */
    public static class SetSpringRates extends SetCommand {
        List<Property<SpringRate>> currentSpringRates;
    
        /**
         * @return The current spring rates
         */
        public List<Property<SpringRate>> getCurrentSpringRates() {
            return currentSpringRates;
        }
        
        /**
         * Set spring rates
         * 
         * @param currentSpringRates The current values for the spring rates
         */
        public SetSpringRates(List<SpringRate> currentSpringRates) {
            super(IDENTIFIER);
        
            final ArrayList<Property<SpringRate>> currentSpringRatesBuilder = new ArrayList<>();
            if (currentSpringRates != null) {
                for (SpringRate currentSpringRate : currentSpringRates) {
                    Property<SpringRate> prop = new Property<>(0x05, currentSpringRate);
                    currentSpringRatesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.currentSpringRates = currentSpringRatesBuilder;
            createBytes();
        }
    
        SetSpringRates(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
        
            final ArrayList<Property<SpringRate>> currentSpringRatesBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_CURRENT_SPRING_RATES) {
                        Property<SpringRate> currentSpringRate = new Property<>(SpringRate.class, p);
                        currentSpringRatesBuilder.add(currentSpringRate);
                        return currentSpringRate;
                    }
                    
                    return null;
                });
            }
        
            currentSpringRates = currentSpringRatesBuilder;
            if (this.currentSpringRates.size() == 0) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set chassis position
     */
    public static class SetChassisPosition extends SetCommand {
        Property<Length> currentChassisPosition = new Property<>(Length.class, PROPERTY_CURRENT_CHASSIS_POSITION);
    
        /**
         * @return The current chassis position
         */
        public Property<Length> getCurrentChassisPosition() {
            return currentChassisPosition;
        }
        
        /**
         * Set chassis position
         * 
         * @param currentChassisPosition The chassis position calculated from the lowest point
         */
        public SetChassisPosition(Length currentChassisPosition) {
            super(IDENTIFIER);
        
            addProperty(this.currentChassisPosition.update(currentChassisPosition));
            createBytes();
        }
    
        SetChassisPosition(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_CURRENT_CHASSIS_POSITION) return currentChassisPosition.update(p);
                    
                    return null;
                });
            }
            if (this.currentChassisPosition.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The chassis settings state
     */
    public static class State extends SetCommand {
        Property<DrivingMode> drivingMode = new Property<>(DrivingMode.class, PROPERTY_DRIVING_MODE);
        Property<SportChrono> sportChrono = new Property<>(SportChrono.class, PROPERTY_SPORT_CHRONO);
        List<Property<SpringRate>> currentSpringRates;
        List<Property<SpringRate>> maximumSpringRates;
        List<Property<SpringRate>> minimumSpringRates;
        Property<Length> currentChassisPosition = new Property<>(Length.class, PROPERTY_CURRENT_CHASSIS_POSITION);
        Property<Length> maximumChassisPosition = new Property<>(Length.class, PROPERTY_MAXIMUM_CHASSIS_POSITION);
        Property<Length> minimumChassisPosition = new Property<>(Length.class, PROPERTY_MINIMUM_CHASSIS_POSITION);
    
        /**
         * @return The driving mode
         */
        public Property<DrivingMode> getDrivingMode() {
            return drivingMode;
        }
    
        /**
         * @return The sport chrono
         */
        public Property<SportChrono> getSportChrono() {
            return sportChrono;
        }
    
        /**
         * @return The current values for the spring rates
         */
        public List<Property<SpringRate>> getCurrentSpringRates() {
            return currentSpringRates;
        }
    
        /**
         * @return The maximum possible values for the spring rates
         */
        public List<Property<SpringRate>> getMaximumSpringRates() {
            return maximumSpringRates;
        }
    
        /**
         * @return The minimum possible values for the spring rates
         */
        public List<Property<SpringRate>> getMinimumSpringRates() {
            return minimumSpringRates;
        }
    
        /**
         * @return The chassis position calculated from the lowest point
         */
        public Property<Length> getCurrentChassisPosition() {
            return currentChassisPosition;
        }
    
        /**
         * @return The maximum possible value for the chassis position
         */
        public Property<Length> getMaximumChassisPosition() {
            return maximumChassisPosition;
        }
    
        /**
         * @return The minimum possible value for the chassis position
         */
        public Property<Length> getMinimumChassisPosition() {
            return minimumChassisPosition;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
    
            final ArrayList<Property<SpringRate>> currentSpringRatesBuilder = new ArrayList<>();
            final ArrayList<Property<SpringRate>> maximumSpringRatesBuilder = new ArrayList<>();
            final ArrayList<Property<SpringRate>> minimumSpringRatesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVING_MODE: return drivingMode.update(p);
                        case PROPERTY_SPORT_CHRONO: return sportChrono.update(p);
                        case PROPERTY_CURRENT_SPRING_RATES:
                            Property<SpringRate> currentSpringRate = new Property<>(SpringRate.class, p);
                            currentSpringRatesBuilder.add(currentSpringRate);
                            return currentSpringRate;
                        case PROPERTY_MAXIMUM_SPRING_RATES:
                            Property<SpringRate> maximumSpringRate = new Property<>(SpringRate.class, p);
                            maximumSpringRatesBuilder.add(maximumSpringRate);
                            return maximumSpringRate;
                        case PROPERTY_MINIMUM_SPRING_RATES:
                            Property<SpringRate> minimumSpringRate = new Property<>(SpringRate.class, p);
                            minimumSpringRatesBuilder.add(minimumSpringRate);
                            return minimumSpringRate;
                        case PROPERTY_CURRENT_CHASSIS_POSITION: return currentChassisPosition.update(p);
                        case PROPERTY_MAXIMUM_CHASSIS_POSITION: return maximumChassisPosition.update(p);
                        case PROPERTY_MINIMUM_CHASSIS_POSITION: return minimumChassisPosition.update(p);
                    }
    
                    return null;
                });
            }
    
            currentSpringRates = currentSpringRatesBuilder;
            maximumSpringRates = maximumSpringRatesBuilder;
            minimumSpringRates = minimumSpringRatesBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            drivingMode = builder.drivingMode;
            sportChrono = builder.sportChrono;
            currentSpringRates = builder.currentSpringRates;
            maximumSpringRates = builder.maximumSpringRates;
            minimumSpringRates = builder.minimumSpringRates;
            currentChassisPosition = builder.currentChassisPosition;
            maximumChassisPosition = builder.maximumChassisPosition;
            minimumChassisPosition = builder.minimumChassisPosition;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<DrivingMode> drivingMode;
            private Property<SportChrono> sportChrono;
            private final List<Property<SpringRate>> currentSpringRates = new ArrayList<>();
            private final List<Property<SpringRate>> maximumSpringRates = new ArrayList<>();
            private final List<Property<SpringRate>> minimumSpringRates = new ArrayList<>();
            private Property<Length> currentChassisPosition;
            private Property<Length> maximumChassisPosition;
            private Property<Length> minimumChassisPosition;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param drivingMode The driving mode
             * @return The builder
             */
            public Builder setDrivingMode(Property<DrivingMode> drivingMode) {
                this.drivingMode = drivingMode.setIdentifier(PROPERTY_DRIVING_MODE);
                addProperty(this.drivingMode);
                return this;
            }
            
            /**
             * @param sportChrono The sport chrono
             * @return The builder
             */
            public Builder setSportChrono(Property<SportChrono> sportChrono) {
                this.sportChrono = sportChrono.setIdentifier(PROPERTY_SPORT_CHRONO);
                addProperty(this.sportChrono);
                return this;
            }
            
            /**
             * Add an array of current spring rates
             * 
             * @param currentSpringRates The current spring rates. The current values for the spring rates
             * @return The builder
             */
            public Builder setCurrentSpringRates(Property<SpringRate>[] currentSpringRates) {
                this.currentSpringRates.clear();
                for (int i = 0; i < currentSpringRates.length; i++) {
                    addCurrentSpringRate(currentSpringRates[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single current spring rate
             * 
             * @param currentSpringRate The current spring rate. The current values for the spring rates
             * @return The builder
             */
            public Builder addCurrentSpringRate(Property<SpringRate> currentSpringRate) {
                currentSpringRate.setIdentifier(PROPERTY_CURRENT_SPRING_RATES);
                addProperty(currentSpringRate);
                currentSpringRates.add(currentSpringRate);
                return this;
            }
            
            /**
             * Add an array of maximum spring rates
             * 
             * @param maximumSpringRates The maximum spring rates. The maximum possible values for the spring rates
             * @return The builder
             */
            public Builder setMaximumSpringRates(Property<SpringRate>[] maximumSpringRates) {
                this.maximumSpringRates.clear();
                for (int i = 0; i < maximumSpringRates.length; i++) {
                    addMaximumSpringRate(maximumSpringRates[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single maximum spring rate
             * 
             * @param maximumSpringRate The maximum spring rate. The maximum possible values for the spring rates
             * @return The builder
             */
            public Builder addMaximumSpringRate(Property<SpringRate> maximumSpringRate) {
                maximumSpringRate.setIdentifier(PROPERTY_MAXIMUM_SPRING_RATES);
                addProperty(maximumSpringRate);
                maximumSpringRates.add(maximumSpringRate);
                return this;
            }
            
            /**
             * Add an array of minimum spring rates
             * 
             * @param minimumSpringRates The minimum spring rates. The minimum possible values for the spring rates
             * @return The builder
             */
            public Builder setMinimumSpringRates(Property<SpringRate>[] minimumSpringRates) {
                this.minimumSpringRates.clear();
                for (int i = 0; i < minimumSpringRates.length; i++) {
                    addMinimumSpringRate(minimumSpringRates[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single minimum spring rate
             * 
             * @param minimumSpringRate The minimum spring rate. The minimum possible values for the spring rates
             * @return The builder
             */
            public Builder addMinimumSpringRate(Property<SpringRate> minimumSpringRate) {
                minimumSpringRate.setIdentifier(PROPERTY_MINIMUM_SPRING_RATES);
                addProperty(minimumSpringRate);
                minimumSpringRates.add(minimumSpringRate);
                return this;
            }
            
            /**
             * @param currentChassisPosition The chassis position calculated from the lowest point
             * @return The builder
             */
            public Builder setCurrentChassisPosition(Property<Length> currentChassisPosition) {
                this.currentChassisPosition = currentChassisPosition.setIdentifier(PROPERTY_CURRENT_CHASSIS_POSITION);
                addProperty(this.currentChassisPosition);
                return this;
            }
            
            /**
             * @param maximumChassisPosition The maximum possible value for the chassis position
             * @return The builder
             */
            public Builder setMaximumChassisPosition(Property<Length> maximumChassisPosition) {
                this.maximumChassisPosition = maximumChassisPosition.setIdentifier(PROPERTY_MAXIMUM_CHASSIS_POSITION);
                addProperty(this.maximumChassisPosition);
                return this;
            }
            
            /**
             * @param minimumChassisPosition The minimum possible value for the chassis position
             * @return The builder
             */
            public Builder setMinimumChassisPosition(Property<Length> minimumChassisPosition) {
                this.minimumChassisPosition = minimumChassisPosition.setIdentifier(PROPERTY_MINIMUM_CHASSIS_POSITION);
                addProperty(this.minimumChassisPosition);
                return this;
            }
        }
    }

    public enum SportChrono implements ByteEnum {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        RESET((byte) 0x02);
    
        public static SportChrono fromByte(byte byteValue) throws CommandParseException {
            SportChrono[] values = SportChrono.values();
    
            for (int i = 0; i < values.length; i++) {
                SportChrono state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(SportChrono.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        SportChrono(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}