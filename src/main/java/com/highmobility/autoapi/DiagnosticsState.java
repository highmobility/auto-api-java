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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.FluidLevel;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.CheckControlMessage;
import com.highmobility.autoapi.value.TirePressure;
import com.highmobility.autoapi.value.TireTemperature;
import com.highmobility.autoapi.value.WheelRpm;
import com.highmobility.autoapi.value.TroubleCode;
import java.util.ArrayList;
import java.util.List;

/**
 * The diagnostics state
 */
public class DiagnosticsState extends SetCommand {
    public static final Identifier identifier = Identifier.DIAGNOSTICS;

    PropertyInteger mileage = new PropertyInteger(0x01, false);
    PropertyInteger engineOilTemperature = new PropertyInteger(0x02, true);
    PropertyInteger speed = new PropertyInteger(0x03, true);
    PropertyInteger engineRPM = new PropertyInteger(0x04, false);
    Property<Double> fuelLevel = new Property(Double.class, 0x05);
    PropertyInteger estimatedRange = new PropertyInteger(0x06, true);
    Property<FluidLevel> washerFluidLevel = new Property(FluidLevel.class, 0x09);
    Property<Float> batteryVoltage = new Property(Float.class, 0x0b);
    Property<Float> adBlueLevel = new Property(Float.class, 0x0c);
    PropertyInteger distanceSinceReset = new PropertyInteger(0x0d, false);
    PropertyInteger distanceSinceStart = new PropertyInteger(0x0e, false);
    Property<Float> fuelVolume = new Property(Float.class, 0x0f);
    Property<ActiveState> antiLockBraking = new Property(ActiveState.class, 0x10);
    PropertyInteger engineCoolantTemperature = new PropertyInteger(0x11, true);
    Property<Float> engineTotalOperatingHours = new Property(Float.class, 0x12);
    Property<Float> engineTotalFuelConsumption = new Property(Float.class, 0x13);
    Property<FluidLevel> brakeFluidLevel = new Property(FluidLevel.class, 0x14);
    Property<Double> engineTorque = new Property(Double.class, 0x15);
    Property<Double> engineLoad = new Property(Double.class, 0x16);
    PropertyInteger wheelBasedSpeed = new PropertyInteger(0x17, true);
    Property<Double> batteryLevel = new Property(Double.class, 0x18);
    Property<CheckControlMessage>[] checkControlMessages;
    Property<TirePressure>[] tirePressures;
    Property<TireTemperature>[] tireTemperatures;
    Property<WheelRpm>[] wheelRPMs;
    Property<TroubleCode>[] troubleCodes;
    PropertyInteger mileageMeters = new PropertyInteger(0x1e, false);

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

    DiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> checkControlMessagesBuilder = new ArrayList<>();
        ArrayList<Property> tirePressuresBuilder = new ArrayList<>();
        ArrayList<Property> tireTemperaturesBuilder = new ArrayList<>();
        ArrayList<Property> wheelRPMsBuilder = new ArrayList<>();
        ArrayList<Property> troubleCodesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return mileage.update(p);
                    case 0x02: return engineOilTemperature.update(p);
                    case 0x03: return speed.update(p);
                    case 0x04: return engineRPM.update(p);
                    case 0x05: return fuelLevel.update(p);
                    case 0x06: return estimatedRange.update(p);
                    case 0x09: return washerFluidLevel.update(p);
                    case 0x0b: return batteryVoltage.update(p);
                    case 0x0c: return adBlueLevel.update(p);
                    case 0x0d: return distanceSinceReset.update(p);
                    case 0x0e: return distanceSinceStart.update(p);
                    case 0x0f: return fuelVolume.update(p);
                    case 0x10: return antiLockBraking.update(p);
                    case 0x11: return engineCoolantTemperature.update(p);
                    case 0x12: return engineTotalOperatingHours.update(p);
                    case 0x13: return engineTotalFuelConsumption.update(p);
                    case 0x14: return brakeFluidLevel.update(p);
                    case 0x15: return engineTorque.update(p);
                    case 0x16: return engineLoad.update(p);
                    case 0x17: return wheelBasedSpeed.update(p);
                    case 0x18: return batteryLevel.update(p);
                    case 0x19:
                        Property<CheckControlMessage> checkControlMessage = new Property(CheckControlMessage.class, p);
                        checkControlMessagesBuilder.add(checkControlMessage);
                        return checkControlMessage;
                    case 0x1a:
                        Property<TirePressure> tirePressure = new Property(TirePressure.class, p);
                        tirePressuresBuilder.add(tirePressure);
                        return tirePressure;
                    case 0x1b:
                        Property<TireTemperature> tireTemperature = new Property(TireTemperature.class, p);
                        tireTemperaturesBuilder.add(tireTemperature);
                        return tireTemperature;
                    case 0x1c:
                        Property<WheelRpm> wheelRPM = new Property(WheelRpm.class, p);
                        wheelRPMsBuilder.add(wheelRPM);
                        return wheelRPM;
                    case 0x1d:
                        Property<TroubleCode> troubleCode = new Property(TroubleCode.class, p);
                        troubleCodesBuilder.add(troubleCode);
                        return troubleCode;
                    case 0x1e: return mileageMeters.update(p);
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

    @Override public boolean isState() {
        return true;
    }

    private DiagnosticsState(Builder builder) {
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
            super(identifier);
        }

        public DiagnosticsState build() {
            return new DiagnosticsState(this);
        }

        /**
         * @param mileage The car mileage (odometer) in km
         * @return The builder
         */
        public Builder setMileage(Property<Integer> mileage) {
            this.mileage = new PropertyInteger(0x01, false, 4, mileage);
            addProperty(this.mileage);
            return this;
        }
        
        /**
         * @param engineOilTemperature Engine oil temperature in Celsius, whereas can be negative
         * @return The builder
         */
        public Builder setEngineOilTemperature(Property<Integer> engineOilTemperature) {
            this.engineOilTemperature = new PropertyInteger(0x02, true, 2, engineOilTemperature);
            addProperty(this.engineOilTemperature);
            return this;
        }
        
        /**
         * @param speed The vehicle speed in km/h, whereas can be negative
         * @return The builder
         */
        public Builder setSpeed(Property<Integer> speed) {
            this.speed = new PropertyInteger(0x03, true, 2, speed);
            addProperty(this.speed);
            return this;
        }
        
        /**
         * @param engineRPM Engine RPM (revolutions per minute)
         * @return The builder
         */
        public Builder setEngineRPM(Property<Integer> engineRPM) {
            this.engineRPM = new PropertyInteger(0x04, false, 2, engineRPM);
            addProperty(this.engineRPM);
            return this;
        }
        
        /**
         * @param fuelLevel Fuel level percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setFuelLevel(Property<Double> fuelLevel) {
            this.fuelLevel = fuelLevel.setIdentifier(0x05);
            addProperty(this.fuelLevel);
            return this;
        }
        
        /**
         * @param estimatedRange Estimated range (with combustion engine)
         * @return The builder
         */
        public Builder setEstimatedRange(Property<Integer> estimatedRange) {
            this.estimatedRange = new PropertyInteger(0x06, true, 2, estimatedRange);
            addProperty(this.estimatedRange);
            return this;
        }
        
        /**
         * @param washerFluidLevel The washer fluid level
         * @return The builder
         */
        public Builder setWasherFluidLevel(Property<FluidLevel> washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel.setIdentifier(0x09);
            addProperty(this.washerFluidLevel);
            return this;
        }
        
        /**
         * @param batteryVoltage Battery voltage
         * @return The builder
         */
        public Builder setBatteryVoltage(Property<Float> batteryVoltage) {
            this.batteryVoltage = batteryVoltage.setIdentifier(0x0b);
            addProperty(this.batteryVoltage);
            return this;
        }
        
        /**
         * @param adBlueLevel AdBlue level in liters
         * @return The builder
         */
        public Builder setAdBlueLevel(Property<Float> adBlueLevel) {
            this.adBlueLevel = adBlueLevel.setIdentifier(0x0c);
            addProperty(this.adBlueLevel);
            return this;
        }
        
        /**
         * @param distanceSinceReset The distance driven in km since reset
         * @return The builder
         */
        public Builder setDistanceSinceReset(Property<Integer> distanceSinceReset) {
            this.distanceSinceReset = new PropertyInteger(0x0d, false, 2, distanceSinceReset);
            addProperty(this.distanceSinceReset);
            return this;
        }
        
        /**
         * @param distanceSinceStart The distance driven in km since trip start
         * @return The builder
         */
        public Builder setDistanceSinceStart(Property<Integer> distanceSinceStart) {
            this.distanceSinceStart = new PropertyInteger(0x0e, false, 2, distanceSinceStart);
            addProperty(this.distanceSinceStart);
            return this;
        }
        
        /**
         * @param fuelVolume The fuel volume measured in liters
         * @return The builder
         */
        public Builder setFuelVolume(Property<Float> fuelVolume) {
            this.fuelVolume = fuelVolume.setIdentifier(0x0f);
            addProperty(this.fuelVolume);
            return this;
        }
        
        /**
         * @param antiLockBraking The anti lock braking
         * @return The builder
         */
        public Builder setAntiLockBraking(Property<ActiveState> antiLockBraking) {
            this.antiLockBraking = antiLockBraking.setIdentifier(0x10);
            addProperty(this.antiLockBraking);
            return this;
        }
        
        /**
         * @param engineCoolantTemperature Engine coolant temperature in Celsius, whereas can be negative
         * @return The builder
         */
        public Builder setEngineCoolantTemperature(Property<Integer> engineCoolantTemperature) {
            this.engineCoolantTemperature = new PropertyInteger(0x11, true, 2, engineCoolantTemperature);
            addProperty(this.engineCoolantTemperature);
            return this;
        }
        
        /**
         * @param engineTotalOperatingHours The accumulated time of engine operation
         * @return The builder
         */
        public Builder setEngineTotalOperatingHours(Property<Float> engineTotalOperatingHours) {
            this.engineTotalOperatingHours = engineTotalOperatingHours.setIdentifier(0x12);
            addProperty(this.engineTotalOperatingHours);
            return this;
        }
        
        /**
         * @param engineTotalFuelConsumption The accumulated lifespan fuel consumption in liters
         * @return The builder
         */
        public Builder setEngineTotalFuelConsumption(Property<Float> engineTotalFuelConsumption) {
            this.engineTotalFuelConsumption = engineTotalFuelConsumption.setIdentifier(0x13);
            addProperty(this.engineTotalFuelConsumption);
            return this;
        }
        
        /**
         * @param brakeFluidLevel The brake fluid level
         * @return The builder
         */
        public Builder setBrakeFluidLevel(Property<FluidLevel> brakeFluidLevel) {
            this.brakeFluidLevel = brakeFluidLevel.setIdentifier(0x14);
            addProperty(this.brakeFluidLevel);
            return this;
        }
        
        /**
         * @param engineTorque Current engine torque percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setEngineTorque(Property<Double> engineTorque) {
            this.engineTorque = engineTorque.setIdentifier(0x15);
            addProperty(this.engineTorque);
            return this;
        }
        
        /**
         * @param engineLoad Current engine load percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setEngineLoad(Property<Double> engineLoad) {
            this.engineLoad = engineLoad.setIdentifier(0x16);
            addProperty(this.engineLoad);
            return this;
        }
        
        /**
         * @param wheelBasedSpeed The vehicle speed in km/h measured at the wheel base, whereas can be negative
         * @return The builder
         */
        public Builder setWheelBasedSpeed(Property<Integer> wheelBasedSpeed) {
            this.wheelBasedSpeed = new PropertyInteger(0x17, true, 2, wheelBasedSpeed);
            addProperty(this.wheelBasedSpeed);
            return this;
        }
        
        /**
         * @param batteryLevel Battery level in %, value between 0.0 and 1.0
         * @return The builder
         */
        public Builder setBatteryLevel(Property<Double> batteryLevel) {
            this.batteryLevel = batteryLevel.setIdentifier(0x18);
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
            checkControlMessage.setIdentifier(0x19);
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
            tirePressure.setIdentifier(0x1a);
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
            tireTemperature.setIdentifier(0x1b);
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
                addWheelRPM(wheelRPMs[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single wheel rpm.
         * 
         * @param wheelRPM The wheel rpm
         * @return The builder
         */
        public Builder addWheelRPM(Property<WheelRpm> wheelRPM) {
            wheelRPM.setIdentifier(0x1c);
            addProperty(wheelRPM);
            wheelRPMs.add(wheelRPM);
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
            troubleCode.setIdentifier(0x1d);
            addProperty(troubleCode);
            troubleCodes.add(troubleCode);
            return this;
        }
        
        /**
         * @param mileageMeters The car mileage (odometer) in meters
         * @return The builder
         */
        public Builder setMileageMeters(Property<Integer> mileageMeters) {
            this.mileageMeters = new PropertyInteger(0x1e, false, 4, mileageMeters);
            addProperty(this.mileageMeters);
            return this;
        }
    }
}