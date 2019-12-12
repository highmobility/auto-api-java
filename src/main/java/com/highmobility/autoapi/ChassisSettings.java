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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.SpringRate;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

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
     * Get chassis settings
     */
    public static class GetChassisSettings extends GetCommand {
        public GetChassisSettings() {
            super(IDENTIFIER);
        }
    
        GetChassisSettings(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }
    
    /**
     * Get specific chassis settings properties
     */
    public static class GetChassisSettingsProperties extends GetCommand {
        Bytes propertyIdentifiers;
    
        /**
         * @return The property identifiers.
         */
        public Bytes getPropertyIdentifiers() {
            return propertyIdentifiers;
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetChassisSettingsProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetChassisSettingsProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The chassis settings state
     */
    public static class State extends SetCommand {
        Property<DrivingMode> drivingMode = new Property(DrivingMode.class, PROPERTY_DRIVING_MODE);
        Property<SportChrono> sportChrono = new Property(SportChrono.class, PROPERTY_SPORT_CHRONO);
        Property<SpringRate>[] currentSpringRates;
        Property<SpringRate>[] maximumSpringRates;
        Property<SpringRate>[] minimumSpringRates;
        PropertyInteger currentChassisPosition = new PropertyInteger(PROPERTY_CURRENT_CHASSIS_POSITION, true);
        PropertyInteger maximumChassisPosition = new PropertyInteger(PROPERTY_MAXIMUM_CHASSIS_POSITION, true);
        PropertyInteger minimumChassisPosition = new PropertyInteger(PROPERTY_MINIMUM_CHASSIS_POSITION, true);
    
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
        public Property<SpringRate>[] getCurrentSpringRates() {
            return currentSpringRates;
        }
    
        /**
         * @return The maximum possible values for the spring rates
         */
        public Property<SpringRate>[] getMaximumSpringRates() {
            return maximumSpringRates;
        }
    
        /**
         * @return The minimum possible values for the spring rates
         */
        public Property<SpringRate>[] getMinimumSpringRates() {
            return minimumSpringRates;
        }
    
        /**
         * @return The chassis position in mm calculated from the lowest point
         */
        public PropertyInteger getCurrentChassisPosition() {
            return currentChassisPosition;
        }
    
        /**
         * @return The maximum possible value for the chassis position
         */
        public PropertyInteger getMaximumChassisPosition() {
            return maximumChassisPosition;
        }
    
        /**
         * @return The minimum possible value for the chassis position
         */
        public PropertyInteger getMinimumChassisPosition() {
            return minimumChassisPosition;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> currentSpringRatesBuilder = new ArrayList<>();
            ArrayList<Property> maximumSpringRatesBuilder = new ArrayList<>();
            ArrayList<Property> minimumSpringRatesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVING_MODE: return drivingMode.update(p);
                        case PROPERTY_SPORT_CHRONO: return sportChrono.update(p);
                        case PROPERTY_CURRENT_SPRING_RATES:
                            Property<SpringRate> currentSpringRate = new Property(SpringRate.class, p);
                            currentSpringRatesBuilder.add(currentSpringRate);
                            return currentSpringRate;
                        case PROPERTY_MAXIMUM_SPRING_RATES:
                            Property<SpringRate> maximumSpringRate = new Property(SpringRate.class, p);
                            maximumSpringRatesBuilder.add(maximumSpringRate);
                            return maximumSpringRate;
                        case PROPERTY_MINIMUM_SPRING_RATES:
                            Property<SpringRate> minimumSpringRate = new Property(SpringRate.class, p);
                            minimumSpringRatesBuilder.add(minimumSpringRate);
                            return minimumSpringRate;
                        case PROPERTY_CURRENT_CHASSIS_POSITION: return currentChassisPosition.update(p);
                        case PROPERTY_MAXIMUM_CHASSIS_POSITION: return maximumChassisPosition.update(p);
                        case PROPERTY_MINIMUM_CHASSIS_POSITION: return minimumChassisPosition.update(p);
                    }
    
                    return null;
                });
            }
    
            currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
            maximumSpringRates = maximumSpringRatesBuilder.toArray(new Property[0]);
            minimumSpringRates = minimumSpringRatesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            drivingMode = builder.drivingMode;
            sportChrono = builder.sportChrono;
            currentSpringRates = builder.currentSpringRates.toArray(new Property[0]);
            maximumSpringRates = builder.maximumSpringRates.toArray(new Property[0]);
            minimumSpringRates = builder.minimumSpringRates.toArray(new Property[0]);
            currentChassisPosition = builder.currentChassisPosition;
            maximumChassisPosition = builder.maximumChassisPosition;
            minimumChassisPosition = builder.minimumChassisPosition;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<DrivingMode> drivingMode;
            private Property<SportChrono> sportChrono;
            private List<Property> currentSpringRates = new ArrayList<>();
            private List<Property> maximumSpringRates = new ArrayList<>();
            private List<Property> minimumSpringRates = new ArrayList<>();
            private PropertyInteger currentChassisPosition;
            private PropertyInteger maximumChassisPosition;
            private PropertyInteger minimumChassisPosition;
    
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
             * Add an array of current spring rates.
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
             * Add a single current spring rate.
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
             * Add an array of maximum spring rates.
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
             * Add a single maximum spring rate.
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
             * Add an array of minimum spring rates.
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
             * Add a single minimum spring rate.
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
             * @param currentChassisPosition The chassis position in mm calculated from the lowest point
             * @return The builder
             */
            public Builder setCurrentChassisPosition(Property<Integer> currentChassisPosition) {
                this.currentChassisPosition = new PropertyInteger(PROPERTY_CURRENT_CHASSIS_POSITION, true, 1, currentChassisPosition);
                addProperty(this.currentChassisPosition);
                return this;
            }
            
            /**
             * @param maximumChassisPosition The maximum possible value for the chassis position
             * @return The builder
             */
            public Builder setMaximumChassisPosition(Property<Integer> maximumChassisPosition) {
                this.maximumChassisPosition = new PropertyInteger(PROPERTY_MAXIMUM_CHASSIS_POSITION, true, 1, maximumChassisPosition);
                addProperty(this.maximumChassisPosition);
                return this;
            }
            
            /**
             * @param minimumChassisPosition The minimum possible value for the chassis position
             * @return The builder
             */
            public Builder setMinimumChassisPosition(Property<Integer> minimumChassisPosition) {
                this.minimumChassisPosition = new PropertyInteger(PROPERTY_MINIMUM_CHASSIS_POSITION, true, 1, minimumChassisPosition);
                addProperty(this.minimumChassisPosition);
                return this;
            }
        }
    }

    /**
     * Set driving mode
     */
    public static class SetDrivingMode extends SetCommand {
        Property<DrivingMode> drivingMode = new Property(DrivingMode.class, PROPERTY_DRIVING_MODE);
    
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
    
        SetDrivingMode(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVING_MODE: return drivingMode.update(p);
                    }
                    return null;
                });
            }
            if (this.drivingMode.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Start stop sports chrono
     */
    public static class StartStopSportsChrono extends SetCommand {
        Property<SportChrono> sportChrono = new Property(SportChrono.class, PROPERTY_SPORT_CHRONO);
    
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
    
        StartStopSportsChrono(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_SPORT_CHRONO: return sportChrono.update(p);
                    }
                    return null;
                });
            }
            if (this.sportChrono.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set spring rates
     */
    public static class SetSpringRates extends SetCommand {
        Property<SpringRate>[] currentSpringRates;
    
        /**
         * @return The current spring rates
         */
        public Property<SpringRate>[] getCurrentSpringRates() {
            return currentSpringRates;
        }
        
        /**
         * Set spring rates
         *
         * @param currentSpringRates The current values for the spring rates
         */
        public SetSpringRates(SpringRate[] currentSpringRates) {
            super(IDENTIFIER);
        
            ArrayList<Property> currentSpringRatesBuilder = new ArrayList<>();
            if (currentSpringRates != null) {
                for (SpringRate currentSpringRate : currentSpringRates) {
                    Property prop = new Property(0x05, currentSpringRate);
                    currentSpringRatesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
            createBytes();
        }
    
        SetSpringRates(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            ArrayList<Property<SpringRate>> currentSpringRatesBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CURRENT_SPRING_RATES: {
                            Property currentSpringRate = new Property(SpringRate.class, p);
                            currentSpringRatesBuilder.add(currentSpringRate);
                            return currentSpringRate;
                        }
                    }
                    return null;
                });
            }
        
            currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
            if (this.currentSpringRates.length == 0) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set chassis position
     */
    public static class SetChassisPosition extends SetCommand {
        PropertyInteger currentChassisPosition = new PropertyInteger(PROPERTY_CURRENT_CHASSIS_POSITION, true);
    
        /**
         * @return The current chassis position
         */
        public PropertyInteger getCurrentChassisPosition() {
            return currentChassisPosition;
        }
        
        /**
         * Set chassis position
         *
         * @param currentChassisPosition The chassis position in mm calculated from the lowest point
         */
        public SetChassisPosition(Integer currentChassisPosition) {
            super(IDENTIFIER);
        
            addProperty(this.currentChassisPosition.update(true, 1, currentChassisPosition));
            createBytes();
        }
    
        SetChassisPosition(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CURRENT_CHASSIS_POSITION: return currentChassisPosition.update(p);
                    }
                    return null;
                });
            }
            if (this.currentChassisPosition.getValue() == null) 
                throw new NoPropertiesException();
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        SportChrono(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}