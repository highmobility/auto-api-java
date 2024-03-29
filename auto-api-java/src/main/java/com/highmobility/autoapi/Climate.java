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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.WeekdayTime;
import com.highmobility.autoapi.value.measurement.Power;
import com.highmobility.autoapi.value.measurement.Temperature;
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
    public static final byte PROPERTY_HUMIDITY = 0x0d;
    public static final byte PROPERTY_AIR_CONDITIONER_COMPRESSOR_POWER = 0x0e;

    /**
     * Get Climate property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Climate property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Climate property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Climate property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Climate properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Climate properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Climate properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Climate properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Climate properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Change starting times
     */
    public static class ChangeStartingTimes extends SetCommand {
        List<Property<WeekdayTime>> hvacWeekdayStartingTimes;
    
        /**
         * @return The hvac weekday starting times
         */
        public List<Property<WeekdayTime>> getHvacWeekdayStartingTimes() {
            return hvacWeekdayStartingTimes;
        }
        
        /**
         * Change starting times
         * 
         * @param hvacWeekdayStartingTimes The hvac weekday starting times
         */
        public ChangeStartingTimes(List<WeekdayTime> hvacWeekdayStartingTimes) {
            super(IDENTIFIER);
        
            final ArrayList<Property<WeekdayTime>> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
            if (hvacWeekdayStartingTimes != null) {
                for (WeekdayTime hvacWeekdayStartingTime : hvacWeekdayStartingTimes) {
                    Property<WeekdayTime> prop = new Property<>(0x0b, hvacWeekdayStartingTime);
                    hvacWeekdayStartingTimesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder;
            createBytes();
        }
    
        ChangeStartingTimes(byte[] bytes) throws PropertyParseException {
            super(bytes);
        
            final ArrayList<Property<WeekdayTime>> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_HVAC_WEEKDAY_STARTING_TIMES) {
                        Property<WeekdayTime> hvacWeekdayStartingTime = new Property<>(WeekdayTime.class, p);
                        hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                        return hvacWeekdayStartingTime;
                    }
                    
                    return null;
                });
            }
        
            hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder;
            if (this.hvacWeekdayStartingTimes.size() == 0) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Start stop hvac
     */
    public static class StartStopHvac extends SetCommand {
        Property<ActiveState> hvacState = new Property<>(ActiveState.class, PROPERTY_HVAC_STATE);
    
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
    
        StartStopHvac(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_HVAC_STATE) return hvacState.update(p);
                    
                    return null;
                });
            }
            if (this.hvacState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Start stop defogging
     */
    public static class StartStopDefogging extends SetCommand {
        Property<ActiveState> defoggingState = new Property<>(ActiveState.class, PROPERTY_DEFOGGING_STATE);
    
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
    
        StartStopDefogging(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_DEFOGGING_STATE) return defoggingState.update(p);
                    
                    return null;
                });
            }
            if (this.defoggingState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Start stop defrosting
     */
    public static class StartStopDefrosting extends SetCommand {
        Property<ActiveState> defrostingState = new Property<>(ActiveState.class, PROPERTY_DEFROSTING_STATE);
    
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
    
        StartStopDefrosting(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_DEFROSTING_STATE) return defrostingState.update(p);
                    
                    return null;
                });
            }
            if (this.defrostingState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Start stop ionising
     */
    public static class StartStopIonising extends SetCommand {
        Property<ActiveState> ionisingState = new Property<>(ActiveState.class, PROPERTY_IONISING_STATE);
    
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
    
        StartStopIonising(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_IONISING_STATE) return ionisingState.update(p);
                    
                    return null;
                });
            }
            if (this.ionisingState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set temperature settings
     */
    public static class SetTemperatureSettings extends SetCommand {
        Property<Temperature> driverTemperatureSetting = new Property<>(Temperature.class, PROPERTY_DRIVER_TEMPERATURE_SETTING);
        Property<Temperature> passengerTemperatureSetting = new Property<>(Temperature.class, PROPERTY_PASSENGER_TEMPERATURE_SETTING);
        Property<Temperature> rearTemperatureSetting = new Property<>(Temperature.class, PROPERTY_REAR_TEMPERATURE_SETTING);
    
        /**
         * @return The driver temperature setting
         */
        public Property<Temperature> getDriverTemperatureSetting() {
            return driverTemperatureSetting;
        }
        
        /**
         * @return The passenger temperature setting
         */
        public Property<Temperature> getPassengerTemperatureSetting() {
            return passengerTemperatureSetting;
        }
        
        /**
         * @return The rear temperature setting
         */
        public Property<Temperature> getRearTemperatureSetting() {
            return rearTemperatureSetting;
        }
        
        /**
         * Set temperature settings
         * 
         * @param driverTemperatureSetting The driver temperature setting
         * @param passengerTemperatureSetting The passenger temperature setting
         * @param rearTemperatureSetting The rear temperature
         */
        public SetTemperatureSettings(@Nullable Temperature driverTemperatureSetting, @Nullable Temperature passengerTemperatureSetting, @Nullable Temperature rearTemperatureSetting) {
            super(IDENTIFIER);
        
            addProperty(this.driverTemperatureSetting.update(driverTemperatureSetting));
            addProperty(this.passengerTemperatureSetting.update(passengerTemperatureSetting));
            addProperty(this.rearTemperatureSetting.update(rearTemperatureSetting));
            if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        SetTemperatureSettings(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVER_TEMPERATURE_SETTING: return driverTemperatureSetting.update(p);
                        case PROPERTY_PASSENGER_TEMPERATURE_SETTING: return passengerTemperatureSetting.update(p);
                        case PROPERTY_REAR_TEMPERATURE_SETTING: return rearTemperatureSetting.update(p);
                    }
        
                    return null;
                });
            }
            if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) {
                throw new PropertyParseException(optionalPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The climate state
     */
    public static class State extends SetCommand {
        Property<Temperature> insideTemperature = new Property<>(Temperature.class, PROPERTY_INSIDE_TEMPERATURE);
        Property<Temperature> outsideTemperature = new Property<>(Temperature.class, PROPERTY_OUTSIDE_TEMPERATURE);
        Property<Temperature> driverTemperatureSetting = new Property<>(Temperature.class, PROPERTY_DRIVER_TEMPERATURE_SETTING);
        Property<Temperature> passengerTemperatureSetting = new Property<>(Temperature.class, PROPERTY_PASSENGER_TEMPERATURE_SETTING);
        Property<ActiveState> hvacState = new Property<>(ActiveState.class, PROPERTY_HVAC_STATE);
        Property<ActiveState> defoggingState = new Property<>(ActiveState.class, PROPERTY_DEFOGGING_STATE);
        Property<ActiveState> defrostingState = new Property<>(ActiveState.class, PROPERTY_DEFROSTING_STATE);
        Property<ActiveState> ionisingState = new Property<>(ActiveState.class, PROPERTY_IONISING_STATE);
        Property<Temperature> defrostingTemperatureSetting = new Property<>(Temperature.class, PROPERTY_DEFROSTING_TEMPERATURE_SETTING);
        List<Property<WeekdayTime>> hvacWeekdayStartingTimes;
        Property<Temperature> rearTemperatureSetting = new Property<>(Temperature.class, PROPERTY_REAR_TEMPERATURE_SETTING);
        Property<Double> humidity = new Property<>(Double.class, PROPERTY_HUMIDITY);
        Property<Power> airConditionerCompressorPower = new Property<>(Power.class, PROPERTY_AIR_CONDITIONER_COMPRESSOR_POWER);
    
        /**
         * @return The inside temperature
         */
        public Property<Temperature> getInsideTemperature() {
            return insideTemperature;
        }
    
        /**
         * @return The outside temperature
         */
        public Property<Temperature> getOutsideTemperature() {
            return outsideTemperature;
        }
    
        /**
         * @return The driver temperature setting
         */
        public Property<Temperature> getDriverTemperatureSetting() {
            return driverTemperatureSetting;
        }
    
        /**
         * @return The passenger temperature setting
         */
        public Property<Temperature> getPassengerTemperatureSetting() {
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
         * @return The defrosting temperature setting
         */
        public Property<Temperature> getDefrostingTemperatureSetting() {
            return defrostingTemperatureSetting;
        }
    
        /**
         * @return The hvac weekday starting times
         */
        public List<Property<WeekdayTime>> getHvacWeekdayStartingTimes() {
            return hvacWeekdayStartingTimes;
        }
    
        /**
         * @return The rear temperature
         */
        public Property<Temperature> getRearTemperatureSetting() {
            return rearTemperatureSetting;
        }
    
        /**
         * @return Measured relative humidity between 0.0 - 1.0.
         */
        public Property<Double> getHumidity() {
            return humidity;
        }
    
        /**
         * @return Electric air conditioner compressor power.
         */
        public Property<Power> getAirConditionerCompressorPower() {
            return airConditionerCompressorPower;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<WeekdayTime>> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
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
                            Property<WeekdayTime> hvacWeekdayStartingTime = new Property<>(WeekdayTime.class, p);
                            hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                            return hvacWeekdayStartingTime;
                        case PROPERTY_REAR_TEMPERATURE_SETTING: return rearTemperatureSetting.update(p);
                        case PROPERTY_HUMIDITY: return humidity.update(p);
                        case PROPERTY_AIR_CONDITIONER_COMPRESSOR_POWER: return airConditionerCompressorPower.update(p);
                    }
    
                    return null;
                });
            }
    
            hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * @param insideTemperature The inside temperature
             * @return The builder
             */
            public Builder setInsideTemperature(Property<Temperature> insideTemperature) {
                Property property = insideTemperature.setIdentifier(PROPERTY_INSIDE_TEMPERATURE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param outsideTemperature The outside temperature
             * @return The builder
             */
            public Builder setOutsideTemperature(Property<Temperature> outsideTemperature) {
                Property property = outsideTemperature.setIdentifier(PROPERTY_OUTSIDE_TEMPERATURE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param driverTemperatureSetting The driver temperature setting
             * @return The builder
             */
            public Builder setDriverTemperatureSetting(Property<Temperature> driverTemperatureSetting) {
                Property property = driverTemperatureSetting.setIdentifier(PROPERTY_DRIVER_TEMPERATURE_SETTING);
                addProperty(property);
                return this;
            }
            
            /**
             * @param passengerTemperatureSetting The passenger temperature setting
             * @return The builder
             */
            public Builder setPassengerTemperatureSetting(Property<Temperature> passengerTemperatureSetting) {
                Property property = passengerTemperatureSetting.setIdentifier(PROPERTY_PASSENGER_TEMPERATURE_SETTING);
                addProperty(property);
                return this;
            }
            
            /**
             * @param hvacState The hvac state
             * @return The builder
             */
            public Builder setHvacState(Property<ActiveState> hvacState) {
                Property property = hvacState.setIdentifier(PROPERTY_HVAC_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param defoggingState The defogging state
             * @return The builder
             */
            public Builder setDefoggingState(Property<ActiveState> defoggingState) {
                Property property = defoggingState.setIdentifier(PROPERTY_DEFOGGING_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param defrostingState The defrosting state
             * @return The builder
             */
            public Builder setDefrostingState(Property<ActiveState> defrostingState) {
                Property property = defrostingState.setIdentifier(PROPERTY_DEFROSTING_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param ionisingState The ionising state
             * @return The builder
             */
            public Builder setIonisingState(Property<ActiveState> ionisingState) {
                Property property = ionisingState.setIdentifier(PROPERTY_IONISING_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param defrostingTemperatureSetting The defrosting temperature setting
             * @return The builder
             */
            public Builder setDefrostingTemperatureSetting(Property<Temperature> defrostingTemperatureSetting) {
                Property property = defrostingTemperatureSetting.setIdentifier(PROPERTY_DEFROSTING_TEMPERATURE_SETTING);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of hvac weekday starting times
             * 
             * @param hvacWeekdayStartingTimes The hvac weekday starting times
             * @return The builder
             */
            public Builder setHvacWeekdayStartingTimes(Property<WeekdayTime>[] hvacWeekdayStartingTimes) {
                for (int i = 0; i < hvacWeekdayStartingTimes.length; i++) {
                    addHvacWeekdayStartingTime(hvacWeekdayStartingTimes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single hvac weekday starting time
             * 
             * @param hvacWeekdayStartingTime The hvac weekday starting time
             * @return The builder
             */
            public Builder addHvacWeekdayStartingTime(Property<WeekdayTime> hvacWeekdayStartingTime) {
                hvacWeekdayStartingTime.setIdentifier(PROPERTY_HVAC_WEEKDAY_STARTING_TIMES);
                addProperty(hvacWeekdayStartingTime);
                return this;
            }
            
            /**
             * @param rearTemperatureSetting The rear temperature
             * @return The builder
             */
            public Builder setRearTemperatureSetting(Property<Temperature> rearTemperatureSetting) {
                Property property = rearTemperatureSetting.setIdentifier(PROPERTY_REAR_TEMPERATURE_SETTING);
                addProperty(property);
                return this;
            }
            
            /**
             * @param humidity Measured relative humidity between 0.0 - 1.0.
             * @return The builder
             */
            public Builder setHumidity(Property<Double> humidity) {
                Property property = humidity.setIdentifier(PROPERTY_HUMIDITY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param airConditionerCompressorPower Electric air conditioner compressor power.
             * @return The builder
             */
            public Builder setAirConditionerCompressorPower(Property<Power> airConditionerCompressorPower) {
                Property property = airConditionerCompressorPower.setIdentifier(PROPERTY_AIR_CONDITIONER_COMPRESSOR_POWER);
                addProperty(property);
                return this;
            }
        }
    }
}