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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Climate capability
 */
public class Climate {
    public static final int IDENTIFIER = Identifier.CLIMATE;

    public static final byte PROPERTY_INSIDE_TEMPERATURE = 0x01;
    public static final byte PROPERTY_OUTSIDE_TEMPERATURE = 0x02;
    public static final byte PROPERTY_DRIVER_TEMPERATURE_SETTING = 0x03;
    public static final byte PROPERTY_PASSENGER_TEMPERATURE_SETTING = 0x04;
    public static final byte PROPERTY_HVAC_STATE = 0x05;
    public static final byte PROPERTY_DEFOGGING_STATE = 0x06;
    public static final byte PROPERTY_DEFROSTING_STATE = 0x07;
    public static final byte PROPERTY_IONISING_STATE = 0x08;
    public static final byte PROPERTY_DEFROSTING_TEMPERATURE_SETTING = 0x09;
    public static final byte PROPERTY_HVAC_WEEKDAY_STARTING_TIMES = 0x0b;
    public static final byte PROPERTY_REAR_TEMPERATURE_SETTING = 0x0c;

    /**
     * Get all climate properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }
    
    /**
     * Get specific climate properties
     */
    public static class GetProperties extends GetCommand {
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
        public GetProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(COMMAND_TYPE_POSITION + 1, getLength());
        }
    }

    /**
     * The climate state
     */
    public static class State extends SetCommand {
        Property<Float> insideTemperature = new Property(Float.class, PROPERTY_INSIDE_TEMPERATURE);
        Property<Float> outsideTemperature = new Property(Float.class, PROPERTY_OUTSIDE_TEMPERATURE);
        Property<Float> driverTemperatureSetting = new Property(Float.class, PROPERTY_DRIVER_TEMPERATURE_SETTING);
        Property<Float> passengerTemperatureSetting = new Property(Float.class, PROPERTY_PASSENGER_TEMPERATURE_SETTING);
        Property<ActiveState> hvacState = new Property(ActiveState.class, PROPERTY_HVAC_STATE);
        Property<ActiveState> defoggingState = new Property(ActiveState.class, PROPERTY_DEFOGGING_STATE);
        Property<ActiveState> defrostingState = new Property(ActiveState.class, PROPERTY_DEFROSTING_STATE);
        Property<ActiveState> ionisingState = new Property(ActiveState.class, PROPERTY_IONISING_STATE);
        Property<Float> defrostingTemperatureSetting = new Property(Float.class, PROPERTY_DEFROSTING_TEMPERATURE_SETTING);
        Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes;
        Property<Float> rearTemperatureSetting = new Property(Float.class, PROPERTY_REAR_TEMPERATURE_SETTING);
    
        /**
         * @return The inside temperature in celsius
         */
        public Property<Float> getInsideTemperature() {
            return insideTemperature;
        }
    
        /**
         * @return The outside temperature in celsius
         */
        public Property<Float> getOutsideTemperature() {
            return outsideTemperature;
        }
    
        /**
         * @return The driver temperature setting in celsius
         */
        public Property<Float> getDriverTemperatureSetting() {
            return driverTemperatureSetting;
        }
    
        /**
         * @return The passenger temperature setting in celsius
         */
        public Property<Float> getPassengerTemperatureSetting() {
            return passengerTemperatureSetting;
        }
    
        /**
         * @return The hvac state
         */
        public Property<ActiveState> getHvacState() {
            return hvacState;
        }
    
        /**
         * @return The defogging state
         */
        public Property<ActiveState> getDefoggingState() {
            return defoggingState;
        }
    
        /**
         * @return The defrosting state
         */
        public Property<ActiveState> getDefrostingState() {
            return defrostingState;
        }
    
        /**
         * @return The ionising state
         */
        public Property<ActiveState> getIonisingState() {
            return ionisingState;
        }
    
        /**
         * @return The defrosting temperature setting in celsius
         */
        public Property<Float> getDefrostingTemperatureSetting() {
            return defrostingTemperatureSetting;
        }
    
        /**
         * @return The hvac weekday starting times
         */
        public Property<HvacWeekdayStartingTime>[] getHvacWeekdayStartingTimes() {
            return hvacWeekdayStartingTimes;
        }
    
        /**
         * @return The rear temperature in celsius
         */
        public Property<Float> getRearTemperatureSetting() {
            return rearTemperatureSetting;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_INSIDE_TEMPERATURE: return insideTemperature.update(p);
                        case PROPERTY_OUTSIDE_TEMPERATURE: return outsideTemperature.update(p);
                        case PROPERTY_DRIVER_TEMPERATURE_SETTING: return driverTemperatureSetting.update(p);
                        case PROPERTY_PASSENGER_TEMPERATURE_SETTING: return passengerTemperatureSetting.update(p);
                        case PROPERTY_HVAC_STATE: return hvacState.update(p);
                        case PROPERTY_DEFOGGING_STATE: return defoggingState.update(p);
                        case PROPERTY_DEFROSTING_STATE: return defrostingState.update(p);
                        case PROPERTY_IONISING_STATE: return ionisingState.update(p);
                        case PROPERTY_DEFROSTING_TEMPERATURE_SETTING: return defrostingTemperatureSetting.update(p);
                        case PROPERTY_HVAC_WEEKDAY_STARTING_TIMES:
                            Property<HvacWeekdayStartingTime> hvacWeekdayStartingTime = new Property(HvacWeekdayStartingTime.class, p);
                            hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                            return hvacWeekdayStartingTime;
                        case PROPERTY_REAR_TEMPERATURE_SETTING: return rearTemperatureSetting.update(p);
                    }
    
                    return null;
                });
            }
    
            hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            insideTemperature = builder.insideTemperature;
            outsideTemperature = builder.outsideTemperature;
            driverTemperatureSetting = builder.driverTemperatureSetting;
            passengerTemperatureSetting = builder.passengerTemperatureSetting;
            hvacState = builder.hvacState;
            defoggingState = builder.defoggingState;
            defrostingState = builder.defrostingState;
            ionisingState = builder.ionisingState;
            defrostingTemperatureSetting = builder.defrostingTemperatureSetting;
            hvacWeekdayStartingTimes = builder.hvacWeekdayStartingTimes.toArray(new Property[0]);
            rearTemperatureSetting = builder.rearTemperatureSetting;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Float> insideTemperature;
            private Property<Float> outsideTemperature;
            private Property<Float> driverTemperatureSetting;
            private Property<Float> passengerTemperatureSetting;
            private Property<ActiveState> hvacState;
            private Property<ActiveState> defoggingState;
            private Property<ActiveState> defrostingState;
            private Property<ActiveState> ionisingState;
            private Property<Float> defrostingTemperatureSetting;
            private List<Property> hvacWeekdayStartingTimes = new ArrayList<>();
            private Property<Float> rearTemperatureSetting;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param insideTemperature The inside temperature in celsius
             * @return The builder
             */
            public Builder setInsideTemperature(Property<Float> insideTemperature) {
                this.insideTemperature = insideTemperature.setIdentifier(PROPERTY_INSIDE_TEMPERATURE);
                addProperty(this.insideTemperature);
                return this;
            }
            
            /**
             * @param outsideTemperature The outside temperature in celsius
             * @return The builder
             */
            public Builder setOutsideTemperature(Property<Float> outsideTemperature) {
                this.outsideTemperature = outsideTemperature.setIdentifier(PROPERTY_OUTSIDE_TEMPERATURE);
                addProperty(this.outsideTemperature);
                return this;
            }
            
            /**
             * @param driverTemperatureSetting The driver temperature setting in celsius
             * @return The builder
             */
            public Builder setDriverTemperatureSetting(Property<Float> driverTemperatureSetting) {
                this.driverTemperatureSetting = driverTemperatureSetting.setIdentifier(PROPERTY_DRIVER_TEMPERATURE_SETTING);
                addProperty(this.driverTemperatureSetting);
                return this;
            }
            
            /**
             * @param passengerTemperatureSetting The passenger temperature setting in celsius
             * @return The builder
             */
            public Builder setPassengerTemperatureSetting(Property<Float> passengerTemperatureSetting) {
                this.passengerTemperatureSetting = passengerTemperatureSetting.setIdentifier(PROPERTY_PASSENGER_TEMPERATURE_SETTING);
                addProperty(this.passengerTemperatureSetting);
                return this;
            }
            
            /**
             * @param hvacState The hvac state
             * @return The builder
             */
            public Builder setHvacState(Property<ActiveState> hvacState) {
                this.hvacState = hvacState.setIdentifier(PROPERTY_HVAC_STATE);
                addProperty(this.hvacState);
                return this;
            }
            
            /**
             * @param defoggingState The defogging state
             * @return The builder
             */
            public Builder setDefoggingState(Property<ActiveState> defoggingState) {
                this.defoggingState = defoggingState.setIdentifier(PROPERTY_DEFOGGING_STATE);
                addProperty(this.defoggingState);
                return this;
            }
            
            /**
             * @param defrostingState The defrosting state
             * @return The builder
             */
            public Builder setDefrostingState(Property<ActiveState> defrostingState) {
                this.defrostingState = defrostingState.setIdentifier(PROPERTY_DEFROSTING_STATE);
                addProperty(this.defrostingState);
                return this;
            }
            
            /**
             * @param ionisingState The ionising state
             * @return The builder
             */
            public Builder setIonisingState(Property<ActiveState> ionisingState) {
                this.ionisingState = ionisingState.setIdentifier(PROPERTY_IONISING_STATE);
                addProperty(this.ionisingState);
                return this;
            }
            
            /**
             * @param defrostingTemperatureSetting The defrosting temperature setting in celsius
             * @return The builder
             */
            public Builder setDefrostingTemperatureSetting(Property<Float> defrostingTemperatureSetting) {
                this.defrostingTemperatureSetting = defrostingTemperatureSetting.setIdentifier(PROPERTY_DEFROSTING_TEMPERATURE_SETTING);
                addProperty(this.defrostingTemperatureSetting);
                return this;
            }
            
            /**
             * Add an array of hvac weekday starting times.
             * 
             * @param hvacWeekdayStartingTimes The hvac weekday starting times
             * @return The builder
             */
            public Builder setHvacWeekdayStartingTimes(Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes) {
                this.hvacWeekdayStartingTimes.clear();
                for (int i = 0; i < hvacWeekdayStartingTimes.length; i++) {
                    addHvacWeekdayStartingTime(hvacWeekdayStartingTimes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single hvac weekday starting time.
             * 
             * @param hvacWeekdayStartingTime The hvac weekday starting time
             * @return The builder
             */
            public Builder addHvacWeekdayStartingTime(Property<HvacWeekdayStartingTime> hvacWeekdayStartingTime) {
                hvacWeekdayStartingTime.setIdentifier(PROPERTY_HVAC_WEEKDAY_STARTING_TIMES);
                addProperty(hvacWeekdayStartingTime);
                hvacWeekdayStartingTimes.add(hvacWeekdayStartingTime);
                return this;
            }
            
            /**
             * @param rearTemperatureSetting The rear temperature in celsius
             * @return The builder
             */
            public Builder setRearTemperatureSetting(Property<Float> rearTemperatureSetting) {
                this.rearTemperatureSetting = rearTemperatureSetting.setIdentifier(PROPERTY_REAR_TEMPERATURE_SETTING);
                addProperty(this.rearTemperatureSetting);
                return this;
            }
        }
    }

    /**
     * Change starting times
     */
    public static class ChangeStartingTimes extends SetCommand {
        Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes;
    
        /**
         * @return The hvac weekday starting times
         */
        public Property<HvacWeekdayStartingTime>[] getHvacWeekdayStartingTimes() {
            return hvacWeekdayStartingTimes;
        }
        
        /**
         * Change starting times
         *
         * @param hvacWeekdayStartingTimes The hvac weekday starting times
         */
        public ChangeStartingTimes(HvacWeekdayStartingTime[] hvacWeekdayStartingTimes) {
            super(IDENTIFIER);
        
            ArrayList<Property> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
            if (hvacWeekdayStartingTimes != null) {
                for (HvacWeekdayStartingTime hvacWeekdayStartingTime : hvacWeekdayStartingTimes) {
                    Property prop = new Property(0x0b, hvacWeekdayStartingTime);
                    hvacWeekdayStartingTimesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
            createBytes();
        }
    
        ChangeStartingTimes(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            ArrayList<Property<HvacWeekdayStartingTime>> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_HVAC_WEEKDAY_STARTING_TIMES: {
                            Property hvacWeekdayStartingTime = new Property(HvacWeekdayStartingTime.class, p);
                            hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                            return hvacWeekdayStartingTime;
                        }
                    }
                    return null;
                });
            }
        
            hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
            if (this.hvacWeekdayStartingTimes.length == 0) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Start stop hvac
     */
    public static class StartStopHvac extends SetCommand {
        Property<ActiveState> hvacState = new Property(ActiveState.class, PROPERTY_HVAC_STATE);
    
        /**
         * @return The hvac state
         */
        public Property<ActiveState> getHvacState() {
            return hvacState;
        }
        
        /**
         * Start stop hvac
         *
         * @param hvacState The hvac state
         */
        public StartStopHvac(ActiveState hvacState) {
            super(IDENTIFIER);
        
            addProperty(this.hvacState.update(hvacState));
            createBytes();
        }
    
        StartStopHvac(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_HVAC_STATE: return hvacState.update(p);
                    }
                    return null;
                });
            }
            if (this.hvacState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Start stop defogging
     */
    public static class StartStopDefogging extends SetCommand {
        Property<ActiveState> defoggingState = new Property(ActiveState.class, PROPERTY_DEFOGGING_STATE);
    
        /**
         * @return The defogging state
         */
        public Property<ActiveState> getDefoggingState() {
            return defoggingState;
        }
        
        /**
         * Start stop defogging
         *
         * @param defoggingState The defogging state
         */
        public StartStopDefogging(ActiveState defoggingState) {
            super(IDENTIFIER);
        
            addProperty(this.defoggingState.update(defoggingState));
            createBytes();
        }
    
        StartStopDefogging(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DEFOGGING_STATE: return defoggingState.update(p);
                    }
                    return null;
                });
            }
            if (this.defoggingState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Start stop defrosting
     */
    public static class StartStopDefrosting extends SetCommand {
        Property<ActiveState> defrostingState = new Property(ActiveState.class, PROPERTY_DEFROSTING_STATE);
    
        /**
         * @return The defrosting state
         */
        public Property<ActiveState> getDefrostingState() {
            return defrostingState;
        }
        
        /**
         * Start stop defrosting
         *
         * @param defrostingState The defrosting state
         */
        public StartStopDefrosting(ActiveState defrostingState) {
            super(IDENTIFIER);
        
            addProperty(this.defrostingState.update(defrostingState));
            createBytes();
        }
    
        StartStopDefrosting(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DEFROSTING_STATE: return defrostingState.update(p);
                    }
                    return null;
                });
            }
            if (this.defrostingState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Start stop ionising
     */
    public static class StartStopIonising extends SetCommand {
        Property<ActiveState> ionisingState = new Property(ActiveState.class, PROPERTY_IONISING_STATE);
    
        /**
         * @return The ionising state
         */
        public Property<ActiveState> getIonisingState() {
            return ionisingState;
        }
        
        /**
         * Start stop ionising
         *
         * @param ionisingState The ionising state
         */
        public StartStopIonising(ActiveState ionisingState) {
            super(IDENTIFIER);
        
            addProperty(this.ionisingState.update(ionisingState));
            createBytes();
        }
    
        StartStopIonising(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_IONISING_STATE: return ionisingState.update(p);
                    }
                    return null;
                });
            }
            if (this.ionisingState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set temperature settings
     */
    public static class SetTemperatureSettings extends SetCommand {
        Property<Float> driverTemperatureSetting = new Property(Float.class, PROPERTY_DRIVER_TEMPERATURE_SETTING);
        Property<Float> passengerTemperatureSetting = new Property(Float.class, PROPERTY_PASSENGER_TEMPERATURE_SETTING);
        Property<Float> rearTemperatureSetting = new Property(Float.class, PROPERTY_REAR_TEMPERATURE_SETTING);
    
        /**
         * @return The driver temperature setting
         */
        public Property<Float> getDriverTemperatureSetting() {
            return driverTemperatureSetting;
        }
        
        /**
         * @return The passenger temperature setting
         */
        public Property<Float> getPassengerTemperatureSetting() {
            return passengerTemperatureSetting;
        }
        
        /**
         * @return The rear temperature setting
         */
        public Property<Float> getRearTemperatureSetting() {
            return rearTemperatureSetting;
        }
        
        /**
         * Set temperature settings
         *
         * @param driverTemperatureSetting The driver temperature setting in celsius
         * @param passengerTemperatureSetting The passenger temperature setting in celsius
         * @param rearTemperatureSetting The rear temperature in celsius
         */
        public SetTemperatureSettings(@Nullable Float driverTemperatureSetting, @Nullable Float passengerTemperatureSetting, @Nullable Float rearTemperatureSetting) {
            super(IDENTIFIER);
        
            addProperty(this.driverTemperatureSetting.update(driverTemperatureSetting));
            addProperty(this.passengerTemperatureSetting.update(passengerTemperatureSetting));
            addProperty(this.rearTemperatureSetting.update(rearTemperatureSetting));
            if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        SetTemperatureSettings(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVER_TEMPERATURE_SETTING: return driverTemperatureSetting.update(p);
                        case PROPERTY_PASSENGER_TEMPERATURE_SETTING: return passengerTemperatureSetting.update(p);
                        case PROPERTY_REAR_TEMPERATURE_SETTING: return rearTemperatureSetting.update(p);
                    }
                    return null;
                });
            }
            if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new NoPropertiesException();
        }
    }
}