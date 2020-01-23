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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.CheckControlMessage;
import com.highmobility.autoapi.value.FluidLevel;
import com.highmobility.autoapi.value.TirePressure;
import com.highmobility.autoapi.value.TireTemperature;
import com.highmobility.autoapi.value.TroubleCode;
import com.highmobility.autoapi.value.WheelRpm;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

/**
 * The Diagnostics capability
 */
public class Diagnostics {
    public static final int IDENTIFIER = Identifier.DIAGNOSTICS;

    public static final byte PROPERTY_MILEAGE = 0x01;
    public static final byte PROPERTY_ENGINE_OIL_TEMPERATURE = 0x02;
    public static final byte PROPERTY_SPEED = 0x03;
    public static final byte PROPERTY_ENGINE_RPM = 0x04;
    public static final byte PROPERTY_FUEL_LEVEL = 0x05;
    public static final byte PROPERTY_ESTIMATED_RANGE = 0x06;
    public static final byte PROPERTY_WASHER_FLUID_LEVEL = 0x09;
    public static final byte PROPERTY_BATTERY_VOLTAGE = 0x0b;
    public static final byte PROPERTY_ADBLUE_LEVEL = 0x0c;
    public static final byte PROPERTY_DISTANCE_SINCE_RESET = 0x0d;
    public static final byte PROPERTY_DISTANCE_SINCE_START = 0x0e;
    public static final byte PROPERTY_FUEL_VOLUME = 0x0f;
    public static final byte PROPERTY_ANTI_LOCK_BRAKING = 0x10;
    public static final byte PROPERTY_ENGINE_COOLANT_TEMPERATURE = 0x11;
    public static final byte PROPERTY_ENGINE_TOTAL_OPERATING_HOURS = 0x12;
    public static final byte PROPERTY_ENGINE_TOTAL_FUEL_CONSUMPTION = 0x13;
    public static final byte PROPERTY_BRAKE_FLUID_LEVEL = 0x14;
    public static final byte PROPERTY_ENGINE_TORQUE = 0x15;
    public static final byte PROPERTY_ENGINE_LOAD = 0x16;
    public static final byte PROPERTY_WHEEL_BASED_SPEED = 0x17;
    public static final byte PROPERTY_BATTERY_LEVEL = 0x18;
    public static final byte PROPERTY_CHECK_CONTROL_MESSAGES = 0x19;
    public static final byte PROPERTY_TIRE_PRESSURES = 0x1a;
    public static final byte PROPERTY_TIRE_TEMPERATURES = 0x1b;
    public static final byte PROPERTY_WHEEL_RPMS = 0x1c;
    public static final byte PROPERTY_TROUBLE_CODES = 0x1d;
    public static final byte PROPERTY_MILEAGE_METERS = 0x1e;

    /**
     * Get all diagnostics properties
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
     * Get specific diagnostics properties
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
     * The diagnostics state
     */
    public static class State extends SetCommand {
        PropertyInteger mileage = new PropertyInteger(PROPERTY_MILEAGE, false);
        PropertyInteger engineOilTemperature = new PropertyInteger(PROPERTY_ENGINE_OIL_TEMPERATURE, true);
        PropertyInteger speed = new PropertyInteger(PROPERTY_SPEED, true);
        PropertyInteger engineRPM = new PropertyInteger(PROPERTY_ENGINE_RPM, false);
        Property<Double> fuelLevel = new Property(Double.class, PROPERTY_FUEL_LEVEL);
        PropertyInteger estimatedRange = new PropertyInteger(PROPERTY_ESTIMATED_RANGE, true);
        Property<FluidLevel> washerFluidLevel = new Property(FluidLevel.class, PROPERTY_WASHER_FLUID_LEVEL);
        Property<Float> batteryVoltage = new Property(Float.class, PROPERTY_BATTERY_VOLTAGE);
        Property<Float> adBlueLevel = new Property(Float.class, PROPERTY_ADBLUE_LEVEL);
        PropertyInteger distanceSinceReset = new PropertyInteger(PROPERTY_DISTANCE_SINCE_RESET, false);
        PropertyInteger distanceSinceStart = new PropertyInteger(PROPERTY_DISTANCE_SINCE_START, false);
        Property<Float> fuelVolume = new Property(Float.class, PROPERTY_FUEL_VOLUME);
        Property<ActiveState> antiLockBraking = new Property(ActiveState.class, PROPERTY_ANTI_LOCK_BRAKING);
        PropertyInteger engineCoolantTemperature = new PropertyInteger(PROPERTY_ENGINE_COOLANT_TEMPERATURE, true);
        Property<Float> engineTotalOperatingHours = new Property(Float.class, PROPERTY_ENGINE_TOTAL_OPERATING_HOURS);
        Property<Float> engineTotalFuelConsumption = new Property(Float.class, PROPERTY_ENGINE_TOTAL_FUEL_CONSUMPTION);
        Property<FluidLevel> brakeFluidLevel = new Property(FluidLevel.class, PROPERTY_BRAKE_FLUID_LEVEL);
        Property<Double> engineTorque = new Property(Double.class, PROPERTY_ENGINE_TORQUE);
        Property<Double> engineLoad = new Property(Double.class, PROPERTY_ENGINE_LOAD);
        PropertyInteger wheelBasedSpeed = new PropertyInteger(PROPERTY_WHEEL_BASED_SPEED, true);
        Property<Double> batteryLevel = new Property(Double.class, PROPERTY_BATTERY_LEVEL);
        Property<CheckControlMessage>[] checkControlMessages;
        Property<TirePressure>[] tirePressures;
        Property<TireTemperature>[] tireTemperatures;
        Property<WheelRpm>[] wheelRPMs;
        Property<TroubleCode>[] troubleCodes;
        PropertyInteger mileageMeters = new PropertyInteger(PROPERTY_MILEAGE_METERS, false);
    
        /**
         * @return The car mileage (odometer) in km
         */
        public PropertyInteger getMileage() {
            return mileage;
        }
    
        /**
         * @return Engine oil temperature in Celsius, whereas can be negative
         */
        public PropertyInteger getEngineOilTemperature() {
            return engineOilTemperature;
        }
    
        /**
         * @return The vehicle speed in km/h, whereas can be negative
         */
        public PropertyInteger getSpeed() {
            return speed;
        }
    
        /**
         * @return Engine RPM (revolutions per minute)
         */
        public PropertyInteger getEngineRPM() {
            return engineRPM;
        }
    
        /**
         * @return Fuel level percentage between 0.0-1.0
         */
        public Property<Double> getFuelLevel() {
            return fuelLevel;
        }
    
        /**
         * @return Estimated range (with combustion engine)
         */
        public PropertyInteger getEstimatedRange() {
            return estimatedRange;
        }
    
        /**
         * @return The washer fluid level
         */
        public Property<FluidLevel> getWasherFluidLevel() {
            return washerFluidLevel;
        }
    
        /**
         * @return Battery voltage
         */
        public Property<Float> getBatteryVoltage() {
            return batteryVoltage;
        }
    
        /**
         * @return AdBlue level in liters
         */
        public Property<Float> getAdBlueLevel() {
            return adBlueLevel;
        }
    
        /**
         * @return The distance driven in km since reset
         */
        public PropertyInteger getDistanceSinceReset() {
            return distanceSinceReset;
        }
    
        /**
         * @return The distance driven in km since trip start
         */
        public PropertyInteger getDistanceSinceStart() {
            return distanceSinceStart;
        }
    
        /**
         * @return The fuel volume measured in liters
         */
        public Property<Float> getFuelVolume() {
            return fuelVolume;
        }
    
        /**
         * @return The anti lock braking
         */
        public Property<ActiveState> getAntiLockBraking() {
            return antiLockBraking;
        }
    
        /**
         * @return Engine coolant temperature in Celsius, whereas can be negative
         */
        public PropertyInteger getEngineCoolantTemperature() {
            return engineCoolantTemperature;
        }
    
        /**
         * @return The accumulated time of engine operation
         */
        public Property<Float> getEngineTotalOperatingHours() {
            return engineTotalOperatingHours;
        }
    
        /**
         * @return The accumulated lifespan fuel consumption in liters
         */
        public Property<Float> getEngineTotalFuelConsumption() {
            return engineTotalFuelConsumption;
        }
    
        /**
         * @return The brake fluid level
         */
        public Property<FluidLevel> getBrakeFluidLevel() {
            return brakeFluidLevel;
        }
    
        /**
         * @return Current engine torque percentage between 0.0-1.0
         */
        public Property<Double> getEngineTorque() {
            return engineTorque;
        }
    
        /**
         * @return Current engine load percentage between 0.0-1.0
         */
        public Property<Double> getEngineLoad() {
            return engineLoad;
        }
    
        /**
         * @return The vehicle speed in km/h measured at the wheel base, whereas can be negative
         */
        public PropertyInteger getWheelBasedSpeed() {
            return wheelBasedSpeed;
        }
    
        /**
         * @return Battery level in %, value between 0.0 and 1.0
         */
        public Property<Double> getBatteryLevel() {
            return batteryLevel;
        }
    
        /**
         * @return The check control messages
         */
        public Property<CheckControlMessage>[] getCheckControlMessages() {
            return checkControlMessages;
        }
    
        /**
         * @return The tire pressures
         */
        public Property<TirePressure>[] getTirePressures() {
            return tirePressures;
        }
    
        /**
         * @return The tire temperatures
         */
        public Property<TireTemperature>[] getTireTemperatures() {
            return tireTemperatures;
        }
    
        /**
         * @return The wheel rpms
         */
        public Property<WheelRpm>[] getWheelRPMs() {
            return wheelRPMs;
        }
    
        /**
         * @return The trouble codes
         */
        public Property<TroubleCode>[] getTroubleCodes() {
            return troubleCodes;
        }
    
        /**
         * @return The car mileage (odometer) in meters
         */
        public PropertyInteger getMileageMeters() {
            return mileageMeters;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> checkControlMessagesBuilder = new ArrayList<>();
            ArrayList<Property> tirePressuresBuilder = new ArrayList<>();
            ArrayList<Property> tireTemperaturesBuilder = new ArrayList<>();
            ArrayList<Property> wheelRPMsBuilder = new ArrayList<>();
            ArrayList<Property> troubleCodesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_MILEAGE: return mileage.update(p);
                        case PROPERTY_ENGINE_OIL_TEMPERATURE: return engineOilTemperature.update(p);
                        case PROPERTY_SPEED: return speed.update(p);
                        case PROPERTY_ENGINE_RPM: return engineRPM.update(p);
                        case PROPERTY_FUEL_LEVEL: return fuelLevel.update(p);
                        case PROPERTY_ESTIMATED_RANGE: return estimatedRange.update(p);
                        case PROPERTY_WASHER_FLUID_LEVEL: return washerFluidLevel.update(p);
                        case PROPERTY_BATTERY_VOLTAGE: return batteryVoltage.update(p);
                        case PROPERTY_ADBLUE_LEVEL: return adBlueLevel.update(p);
                        case PROPERTY_DISTANCE_SINCE_RESET: return distanceSinceReset.update(p);
                        case PROPERTY_DISTANCE_SINCE_START: return distanceSinceStart.update(p);
                        case PROPERTY_FUEL_VOLUME: return fuelVolume.update(p);
                        case PROPERTY_ANTI_LOCK_BRAKING: return antiLockBraking.update(p);
                        case PROPERTY_ENGINE_COOLANT_TEMPERATURE: return engineCoolantTemperature.update(p);
                        case PROPERTY_ENGINE_TOTAL_OPERATING_HOURS: return engineTotalOperatingHours.update(p);
                        case PROPERTY_ENGINE_TOTAL_FUEL_CONSUMPTION: return engineTotalFuelConsumption.update(p);
                        case PROPERTY_BRAKE_FLUID_LEVEL: return brakeFluidLevel.update(p);
                        case PROPERTY_ENGINE_TORQUE: return engineTorque.update(p);
                        case PROPERTY_ENGINE_LOAD: return engineLoad.update(p);
                        case PROPERTY_WHEEL_BASED_SPEED: return wheelBasedSpeed.update(p);
                        case PROPERTY_BATTERY_LEVEL: return batteryLevel.update(p);
                        case PROPERTY_CHECK_CONTROL_MESSAGES:
                            Property<CheckControlMessage> checkControlMessage = new Property(CheckControlMessage.class, p);
                            checkControlMessagesBuilder.add(checkControlMessage);
                            return checkControlMessage;
                        case PROPERTY_TIRE_PRESSURES:
                            Property<TirePressure> tirePressure = new Property(TirePressure.class, p);
                            tirePressuresBuilder.add(tirePressure);
                            return tirePressure;
                        case PROPERTY_TIRE_TEMPERATURES:
                            Property<TireTemperature> tireTemperature = new Property(TireTemperature.class, p);
                            tireTemperaturesBuilder.add(tireTemperature);
                            return tireTemperature;
                        case PROPERTY_WHEEL_RPMS:
                            Property<WheelRpm> wheelRpm = new Property(WheelRpm.class, p);
                            wheelRPMsBuilder.add(wheelRpm);
                            return wheelRpm;
                        case PROPERTY_TROUBLE_CODES:
                            Property<TroubleCode> troubleCode = new Property(TroubleCode.class, p);
                            troubleCodesBuilder.add(troubleCode);
                            return troubleCode;
                        case PROPERTY_MILEAGE_METERS: return mileageMeters.update(p);
                    }
    
                    return null;
                });
            }
    
            checkControlMessages = checkControlMessagesBuilder.toArray(new Property[0]);
            tirePressures = tirePressuresBuilder.toArray(new Property[0]);
            tireTemperatures = tireTemperaturesBuilder.toArray(new Property[0]);
            wheelRPMs = wheelRPMsBuilder.toArray(new Property[0]);
            troubleCodes = troubleCodesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            mileage = builder.mileage;
            engineOilTemperature = builder.engineOilTemperature;
            speed = builder.speed;
            engineRPM = builder.engineRPM;
            fuelLevel = builder.fuelLevel;
            estimatedRange = builder.estimatedRange;
            washerFluidLevel = builder.washerFluidLevel;
            batteryVoltage = builder.batteryVoltage;
            adBlueLevel = builder.adBlueLevel;
            distanceSinceReset = builder.distanceSinceReset;
            distanceSinceStart = builder.distanceSinceStart;
            fuelVolume = builder.fuelVolume;
            antiLockBraking = builder.antiLockBraking;
            engineCoolantTemperature = builder.engineCoolantTemperature;
            engineTotalOperatingHours = builder.engineTotalOperatingHours;
            engineTotalFuelConsumption = builder.engineTotalFuelConsumption;
            brakeFluidLevel = builder.brakeFluidLevel;
            engineTorque = builder.engineTorque;
            engineLoad = builder.engineLoad;
            wheelBasedSpeed = builder.wheelBasedSpeed;
            batteryLevel = builder.batteryLevel;
            checkControlMessages = builder.checkControlMessages.toArray(new Property[0]);
            tirePressures = builder.tirePressures.toArray(new Property[0]);
            tireTemperatures = builder.tireTemperatures.toArray(new Property[0]);
            wheelRPMs = builder.wheelRPMs.toArray(new Property[0]);
            troubleCodes = builder.troubleCodes.toArray(new Property[0]);
            mileageMeters = builder.mileageMeters;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private PropertyInteger mileage;
            private PropertyInteger engineOilTemperature;
            private PropertyInteger speed;
            private PropertyInteger engineRPM;
            private Property<Double> fuelLevel;
            private PropertyInteger estimatedRange;
            private Property<FluidLevel> washerFluidLevel;
            private Property<Float> batteryVoltage;
            private Property<Float> adBlueLevel;
            private PropertyInteger distanceSinceReset;
            private PropertyInteger distanceSinceStart;
            private Property<Float> fuelVolume;
            private Property<ActiveState> antiLockBraking;
            private PropertyInteger engineCoolantTemperature;
            private Property<Float> engineTotalOperatingHours;
            private Property<Float> engineTotalFuelConsumption;
            private Property<FluidLevel> brakeFluidLevel;
            private Property<Double> engineTorque;
            private Property<Double> engineLoad;
            private PropertyInteger wheelBasedSpeed;
            private Property<Double> batteryLevel;
            private List<Property> checkControlMessages = new ArrayList<>();
            private List<Property> tirePressures = new ArrayList<>();
            private List<Property> tireTemperatures = new ArrayList<>();
            private List<Property> wheelRPMs = new ArrayList<>();
            private List<Property> troubleCodes = new ArrayList<>();
            private PropertyInteger mileageMeters;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param mileage The car mileage (odometer) in km
             * @return The builder
             */
            public Builder setMileage(Property<Integer> mileage) {
                this.mileage = new PropertyInteger(PROPERTY_MILEAGE, false, 4, mileage);
                addProperty(this.mileage);
                return this;
            }
            
            /**
             * @param engineOilTemperature Engine oil temperature in Celsius, whereas can be negative
             * @return The builder
             */
            public Builder setEngineOilTemperature(Property<Integer> engineOilTemperature) {
                this.engineOilTemperature = new PropertyInteger(PROPERTY_ENGINE_OIL_TEMPERATURE, true, 2, engineOilTemperature);
                addProperty(this.engineOilTemperature);
                return this;
            }
            
            /**
             * @param speed The vehicle speed in km/h, whereas can be negative
             * @return The builder
             */
            public Builder setSpeed(Property<Integer> speed) {
                this.speed = new PropertyInteger(PROPERTY_SPEED, true, 2, speed);
                addProperty(this.speed);
                return this;
            }
            
            /**
             * @param engineRPM Engine RPM (revolutions per minute)
             * @return The builder
             */
            public Builder setEngineRPM(Property<Integer> engineRPM) {
                this.engineRPM = new PropertyInteger(PROPERTY_ENGINE_RPM, false, 2, engineRPM);
                addProperty(this.engineRPM);
                return this;
            }
            
            /**
             * @param fuelLevel Fuel level percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setFuelLevel(Property<Double> fuelLevel) {
                this.fuelLevel = fuelLevel.setIdentifier(PROPERTY_FUEL_LEVEL);
                addProperty(this.fuelLevel);
                return this;
            }
            
            /**
             * @param estimatedRange Estimated range (with combustion engine)
             * @return The builder
             */
            public Builder setEstimatedRange(Property<Integer> estimatedRange) {
                this.estimatedRange = new PropertyInteger(PROPERTY_ESTIMATED_RANGE, true, 2, estimatedRange);
                addProperty(this.estimatedRange);
                return this;
            }
            
            /**
             * @param washerFluidLevel The washer fluid level
             * @return The builder
             */
            public Builder setWasherFluidLevel(Property<FluidLevel> washerFluidLevel) {
                this.washerFluidLevel = washerFluidLevel.setIdentifier(PROPERTY_WASHER_FLUID_LEVEL);
                addProperty(this.washerFluidLevel);
                return this;
            }
            
            /**
             * @param batteryVoltage Battery voltage
             * @return The builder
             */
            public Builder setBatteryVoltage(Property<Float> batteryVoltage) {
                this.batteryVoltage = batteryVoltage.setIdentifier(PROPERTY_BATTERY_VOLTAGE);
                addProperty(this.batteryVoltage);
                return this;
            }
            
            /**
             * @param adBlueLevel AdBlue level in liters
             * @return The builder
             */
            public Builder setAdBlueLevel(Property<Float> adBlueLevel) {
                this.adBlueLevel = adBlueLevel.setIdentifier(PROPERTY_ADBLUE_LEVEL);
                addProperty(this.adBlueLevel);
                return this;
            }
            
            /**
             * @param distanceSinceReset The distance driven in km since reset
             * @return The builder
             */
            public Builder setDistanceSinceReset(Property<Integer> distanceSinceReset) {
                this.distanceSinceReset = new PropertyInteger(PROPERTY_DISTANCE_SINCE_RESET, false, 2, distanceSinceReset);
                addProperty(this.distanceSinceReset);
                return this;
            }
            
            /**
             * @param distanceSinceStart The distance driven in km since trip start
             * @return The builder
             */
            public Builder setDistanceSinceStart(Property<Integer> distanceSinceStart) {
                this.distanceSinceStart = new PropertyInteger(PROPERTY_DISTANCE_SINCE_START, false, 2, distanceSinceStart);
                addProperty(this.distanceSinceStart);
                return this;
            }
            
            /**
             * @param fuelVolume The fuel volume measured in liters
             * @return The builder
             */
            public Builder setFuelVolume(Property<Float> fuelVolume) {
                this.fuelVolume = fuelVolume.setIdentifier(PROPERTY_FUEL_VOLUME);
                addProperty(this.fuelVolume);
                return this;
            }
            
            /**
             * @param antiLockBraking The anti lock braking
             * @return The builder
             */
            public Builder setAntiLockBraking(Property<ActiveState> antiLockBraking) {
                this.antiLockBraking = antiLockBraking.setIdentifier(PROPERTY_ANTI_LOCK_BRAKING);
                addProperty(this.antiLockBraking);
                return this;
            }
            
            /**
             * @param engineCoolantTemperature Engine coolant temperature in Celsius, whereas can be negative
             * @return The builder
             */
            public Builder setEngineCoolantTemperature(Property<Integer> engineCoolantTemperature) {
                this.engineCoolantTemperature = new PropertyInteger(PROPERTY_ENGINE_COOLANT_TEMPERATURE, true, 2, engineCoolantTemperature);
                addProperty(this.engineCoolantTemperature);
                return this;
            }
            
            /**
             * @param engineTotalOperatingHours The accumulated time of engine operation
             * @return The builder
             */
            public Builder setEngineTotalOperatingHours(Property<Float> engineTotalOperatingHours) {
                this.engineTotalOperatingHours = engineTotalOperatingHours.setIdentifier(PROPERTY_ENGINE_TOTAL_OPERATING_HOURS);
                addProperty(this.engineTotalOperatingHours);
                return this;
            }
            
            /**
             * @param engineTotalFuelConsumption The accumulated lifespan fuel consumption in liters
             * @return The builder
             */
            public Builder setEngineTotalFuelConsumption(Property<Float> engineTotalFuelConsumption) {
                this.engineTotalFuelConsumption = engineTotalFuelConsumption.setIdentifier(PROPERTY_ENGINE_TOTAL_FUEL_CONSUMPTION);
                addProperty(this.engineTotalFuelConsumption);
                return this;
            }
            
            /**
             * @param brakeFluidLevel The brake fluid level
             * @return The builder
             */
            public Builder setBrakeFluidLevel(Property<FluidLevel> brakeFluidLevel) {
                this.brakeFluidLevel = brakeFluidLevel.setIdentifier(PROPERTY_BRAKE_FLUID_LEVEL);
                addProperty(this.brakeFluidLevel);
                return this;
            }
            
            /**
             * @param engineTorque Current engine torque percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setEngineTorque(Property<Double> engineTorque) {
                this.engineTorque = engineTorque.setIdentifier(PROPERTY_ENGINE_TORQUE);
                addProperty(this.engineTorque);
                return this;
            }
            
            /**
             * @param engineLoad Current engine load percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setEngineLoad(Property<Double> engineLoad) {
                this.engineLoad = engineLoad.setIdentifier(PROPERTY_ENGINE_LOAD);
                addProperty(this.engineLoad);
                return this;
            }
            
            /**
             * @param wheelBasedSpeed The vehicle speed in km/h measured at the wheel base, whereas can be negative
             * @return The builder
             */
            public Builder setWheelBasedSpeed(Property<Integer> wheelBasedSpeed) {
                this.wheelBasedSpeed = new PropertyInteger(PROPERTY_WHEEL_BASED_SPEED, true, 2, wheelBasedSpeed);
                addProperty(this.wheelBasedSpeed);
                return this;
            }
            
            /**
             * @param batteryLevel Battery level in %, value between 0.0 and 1.0
             * @return The builder
             */
            public Builder setBatteryLevel(Property<Double> batteryLevel) {
                this.batteryLevel = batteryLevel.setIdentifier(PROPERTY_BATTERY_LEVEL);
                addProperty(this.batteryLevel);
                return this;
            }
            
            /**
             * Add an array of check control messages.
             * 
             * @param checkControlMessages The check control messages
             * @return The builder
             */
            public Builder setCheckControlMessages(Property<CheckControlMessage>[] checkControlMessages) {
                this.checkControlMessages.clear();
                for (int i = 0; i < checkControlMessages.length; i++) {
                    addCheckControlMessage(checkControlMessages[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single check control message.
             * 
             * @param checkControlMessage The check control message
             * @return The builder
             */
            public Builder addCheckControlMessage(Property<CheckControlMessage> checkControlMessage) {
                checkControlMessage.setIdentifier(PROPERTY_CHECK_CONTROL_MESSAGES);
                addProperty(checkControlMessage);
                checkControlMessages.add(checkControlMessage);
                return this;
            }
            
            /**
             * Add an array of tire pressures.
             * 
             * @param tirePressures The tire pressures
             * @return The builder
             */
            public Builder setTirePressures(Property<TirePressure>[] tirePressures) {
                this.tirePressures.clear();
                for (int i = 0; i < tirePressures.length; i++) {
                    addTirePressure(tirePressures[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single tire pressure.
             * 
             * @param tirePressure The tire pressure
             * @return The builder
             */
            public Builder addTirePressure(Property<TirePressure> tirePressure) {
                tirePressure.setIdentifier(PROPERTY_TIRE_PRESSURES);
                addProperty(tirePressure);
                tirePressures.add(tirePressure);
                return this;
            }
            
            /**
             * Add an array of tire temperatures.
             * 
             * @param tireTemperatures The tire temperatures
             * @return The builder
             */
            public Builder setTireTemperatures(Property<TireTemperature>[] tireTemperatures) {
                this.tireTemperatures.clear();
                for (int i = 0; i < tireTemperatures.length; i++) {
                    addTireTemperature(tireTemperatures[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single tire temperature.
             * 
             * @param tireTemperature The tire temperature
             * @return The builder
             */
            public Builder addTireTemperature(Property<TireTemperature> tireTemperature) {
                tireTemperature.setIdentifier(PROPERTY_TIRE_TEMPERATURES);
                addProperty(tireTemperature);
                tireTemperatures.add(tireTemperature);
                return this;
            }
            
            /**
             * Add an array of wheel rpms.
             * 
             * @param wheelRPMs The wheel rpms
             * @return The builder
             */
            public Builder setWheelRPMs(Property<WheelRpm>[] wheelRPMs) {
                this.wheelRPMs.clear();
                for (int i = 0; i < wheelRPMs.length; i++) {
                    addWheelRpm(wheelRPMs[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single wheel rpm.
             * 
             * @param wheelRpm The wheel rpm
             * @return The builder
             */
            public Builder addWheelRpm(Property<WheelRpm> wheelRpm) {
                wheelRpm.setIdentifier(PROPERTY_WHEEL_RPMS);
                addProperty(wheelRpm);
                wheelRPMs.add(wheelRpm);
                return this;
            }
            
            /**
             * Add an array of trouble codes.
             * 
             * @param troubleCodes The trouble codes
             * @return The builder
             */
            public Builder setTroubleCodes(Property<TroubleCode>[] troubleCodes) {
                this.troubleCodes.clear();
                for (int i = 0; i < troubleCodes.length; i++) {
                    addTroubleCode(troubleCodes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single trouble code.
             * 
             * @param troubleCode The trouble code
             * @return The builder
             */
            public Builder addTroubleCode(Property<TroubleCode> troubleCode) {
                troubleCode.setIdentifier(PROPERTY_TROUBLE_CODES);
                addProperty(troubleCode);
                troubleCodes.add(troubleCode);
                return this;
            }
            
            /**
             * @param mileageMeters The car mileage (odometer) in meters
             * @return The builder
             */
            public Builder setMileageMeters(Property<Integer> mileageMeters) {
                this.mileageMeters = new PropertyInteger(PROPERTY_MILEAGE_METERS, false, 4, mileageMeters);
                addProperty(this.mileageMeters);
                return this;
            }
        }
    }
}