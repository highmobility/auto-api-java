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
import com.highmobility.autoapi.value.TireLocation;
import com.highmobility.autoapi.value.diagnostics.BrakeFluidLevel;
import com.highmobility.autoapi.value.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.value.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.value.diagnostics.TirePressure;
import com.highmobility.autoapi.value.diagnostics.TireTemperature;
import com.highmobility.autoapi.value.diagnostics.WasherFluidLevel;
import com.highmobility.autoapi.value.diagnostics.WheelRpm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Diagnostics State command is received by the car. The new status is
 * included in the command payload and may be the result of user, device or car triggered action.
 */
public class DiagnosticsState extends Command {
    public static final Type TYPE = new Type(Identifier.DIAGNOSTICS, 0x01);

    private static final byte IDENTIFIER_MILEAGE = 0x01;
    private static final byte IDENTIFIER_OIL_TEMPERATURE = 0x02;
    private static final byte IDENTIFIER_SPEED = 0x03;
    private static final byte IDENTIFIER_RPM = 0x04;
    private static final byte IDENTIFIER_FUEL_LEVEL = 0x05;
    private static final byte IDENTIFIER_RANGE = 0x06;
    private static final byte IDENTIFIER_WASHER_FLUID_LEVEL = 0x09;
    private static final byte IDENTIFIER_BATTERY_VOLTAGE = 0x0B;
    private static final byte IDENTIFIER_AD_BLUE_LEVEL = 0x0C;
    private static final byte IDENTIFIER_DISTANCE_DRIVEN_SINCE_RESET = 0x0D;
    private static final byte IDENTIFIER_DISTANCE_DRIVEN_SINCE_ENGINE_START = 0x0E;
    private static final byte IDENTIFIER_FUEL_VOLUME = 0x0F;

    private static final byte IDENTIFIER_ANTI_LOCK_BRAKING_ACTIVE = 0x10;
    private static final byte IDENTIFIER_ENGINE_COOLANT_TEMPERATURE = 0x11;
    private static final byte IDENTIFIER_ENGINE_TOTAL_OPERATING_HOURS = 0x12;
    private static final byte IDENTIFIER_ENGINE_TOTAL_FUEL_CONSUMPTION = 0x13;

    private static final byte IDENTIFIER_BRAKE_FLUID_LEVEL = 0x14;
    private static final byte IDENTIFIER_ENGINE_TORQUE = 0x15;
    private static final byte IDENTIFIER_ENGINE_LOAD = 0x16;
    private static final byte IDENTIFIER_WHEEL_BASED_SPEED = 0x17;

    private static final byte IDENTIFIER_BATTERY_LEVEL = 0x18;
    private static final byte IDENTIFIER_CHECK_CONTROL_MESSAGES = 0x19;
    private static final byte IDENTIFIER_TIRE_PRESSURES = 0x1A;
    private static final byte IDENTIFIER_TIRE_TEMPERATURES = 0x1B;
    private static final byte IDENTIFIER_WHEEL_RPM = 0x1C;
    private static final byte IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE = 0x1D;

    private static final byte IDENTIFIER_MILEAGE_METERS = 0x1E;

    PropertyInteger mileage = new PropertyInteger(IDENTIFIER_MILEAGE, false);
    PropertyInteger oilTemperature = new PropertyInteger(IDENTIFIER_OIL_TEMPERATURE,
            false);
    PropertyInteger speed = new PropertyInteger(IDENTIFIER_SPEED, false);
    PropertyInteger rpm = new PropertyInteger(IDENTIFIER_RPM, false);
    Property<Double> fuelLevel = new Property(Double.class, IDENTIFIER_FUEL_LEVEL);
    PropertyInteger range = new PropertyInteger(IDENTIFIER_RANGE, false);
    Property<WasherFluidLevel> washerFluidLevel =
            new Property(WasherFluidLevel.class, IDENTIFIER_WASHER_FLUID_LEVEL);
    Property<Float> batteryVoltage = new Property(Float.class, IDENTIFIER_BATTERY_VOLTAGE);
    Property<Float> adBlueLevel = new Property(Float.class, IDENTIFIER_AD_BLUE_LEVEL);
    PropertyInteger distanceDrivenSinceReset =
            new PropertyInteger(IDENTIFIER_DISTANCE_DRIVEN_SINCE_RESET, false);
    PropertyInteger distanceDrivenSinceEngineStart =
            new PropertyInteger(IDENTIFIER_DISTANCE_DRIVEN_SINCE_ENGINE_START, false);
    Property<Float> fuelVolume = new Property(Float.class, IDENTIFIER_FUEL_VOLUME);

    // level7
    Property<Boolean> antiLockBrakingActive =
            new Property(Boolean.class, IDENTIFIER_ANTI_LOCK_BRAKING_ACTIVE);
    PropertyInteger engineCoolantTemperature =
            new PropertyInteger(IDENTIFIER_ENGINE_COOLANT_TEMPERATURE, false);
    Property<Float> engineTotalOperatingHours =
            new Property(Float.class, IDENTIFIER_ENGINE_TOTAL_OPERATING_HOURS);
    Property<Float> engineTotalFuelConsumption =
            new Property(Float.class, IDENTIFIER_ENGINE_TOTAL_FUEL_CONSUMPTION);
    Property<BrakeFluidLevel> brakeFluidLevel = new Property(BrakeFluidLevel.class,
            IDENTIFIER_BRAKE_FLUID_LEVEL);
    Property<Double> engineTorque = new Property(Double.class, IDENTIFIER_ENGINE_TORQUE);
    Property<Double> engineLoad = new Property(Double.class, IDENTIFIER_ENGINE_LOAD);
    PropertyInteger wheelBasedSpeed = new PropertyInteger(IDENTIFIER_WHEEL_BASED_SPEED, true);

    // level8
    Property<Double> batteryLevel = new Property(Double.class, IDENTIFIER_BATTERY_LEVEL);
    Property<CheckControlMessage>[] checkControlMessages;
    Property<TirePressure>[] tirePressures;
    Property<TireTemperature>[] tireTemperatures;
    Property<WheelRpm>[] wheelRpms;
    Property<DiagnosticsTroubleCode>[] troubleCodes;

    PropertyInteger mileageMeters = new PropertyInteger(IDENTIFIER_MILEAGE_METERS, false);

    /**
     * @return The car mileage (odometer) in km.
     */
    public Property<Integer> getMileage() {
        return mileage;
    }

    /**
     * @return The engine oil temperature in Celsius, whereas can be negative.
     */
    public Property<Integer> getOilTemperature() {
        return oilTemperature;
    }

    /**
     * @return The car speed in km/h, whereas can be negative.
     */
    public Property<Integer> getSpeed() {
        return speed;
    }

    /**
     * @return The RPM of the engine.
     */
    public Property<Integer> getRpm() {
        return rpm;
    }

    /**
     * @return The Fuel level percentage.
     */
    public Property<Double> getFuelLevel() {
        return fuelLevel;
    }

    /**
     * @return The estimated range.
     */
    public Property<Integer> getRange() {
        return range;
    }

    /**
     * @return Washer fluid level.
     */
    public Property<WasherFluidLevel> getWasherFluidLevel() {
        return washerFluidLevel;
    }

    /**
     * @return The battery voltage.
     */
    public Property<Float> getBatteryVoltage() {
        return batteryVoltage;
    }

    /**
     * @return AdBlue level in liters.
     */
    public Property<Float> getAdBlueLevel() {
        return adBlueLevel;
    }

    /**
     * @return The distance driven in km since reset.
     */
    public Property<Integer> getDistanceDrivenSinceReset() {
        return distanceDrivenSinceReset;
    }

    /**
     * @return The distance driven in km since engine start.
     */
    public Property<Integer> getDistanceDrivenSinceEngineStart() {
        return distanceDrivenSinceEngineStart;
    }

    /**
     * @return The fuel volume measured in liters.
     */
    public Property<Float> getFuelVolume() {
        return fuelVolume;
    }

    /**
     * @return The anti-lock braking system (ABS) state.
     */
    public Property<Boolean> isAntiLockBrakingActive() {
        return antiLockBrakingActive;
    }

    /**
     * @return The engine coolant temperature in Celsius, whereas can be negative.
     */
    public Property<Integer> getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }

    /**
     * @return The the accumulated time of engine operation.
     */
    public Property<Float> getEngineTotalOperatingHours() {
        return engineTotalOperatingHours;
    }

    /**
     * @return The the accumulated lifespan fuel consumption in liters.
     */
    public Property<Float> getEngineTotalFuelConsumption() {
        return engineTotalFuelConsumption;
    }

    /**
     * @return The brake fluid level.
     */
    public Property<BrakeFluidLevel> getBrakeFluidLevel() {
        return brakeFluidLevel;
    }

    /**
     * @return The current engine torque percentage.
     */
    public Property<Double> getEngineTorque() {
        return engineTorque;
    }

    /**
     * @return The current engine load percentage.
     */
    public Property<Double> getEngineLoad() {
        return engineLoad;
    }

    /**
     * @return The vehicle speed in km/h measured at the wheel base, whereas can be negative.
     */
    public Property<Integer> getWheelBasedSpeed() {
        return wheelBasedSpeed;
    }

    /**
     * @return The battery level percentage.
     */
    public Property<Double> getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The Check Control Message.
     */
    public Property<CheckControlMessage>[] getCheckControlMessages() {
        return checkControlMessages;
    }

    /**
     * @return The tire pressures.
     */
    public Property<TirePressure>[] getTirePressures() {
        return tirePressures;
    }

    /**
     * Get the tire pressure for a tire.
     *
     * @param tireLocation The tire location
     * @return The tire pressure.
     */
    @Nullable public Property<TirePressure> getTirePressure(TireLocation tireLocation) {
        for (int i = 0; i < tirePressures.length; i++) {
            Property<TirePressure> pressure = tirePressures[i];
            if (pressure != null && pressure.getValue().getTireLocation() == tireLocation)
                return pressure;
        }

        return null;
    }

    /**
     * @return The tire temperatures.
     */
    public Property<TireTemperature>[] getTireTemperatures() {
        return tireTemperatures;
    }

    /**
     * The tire temperature for a tire.
     *
     * @param tireLocation The tire location.
     * @return The tire temperature.
     */
    @Nullable public Property<TireTemperature> getTireTemperature(TireLocation
                                                                          tireLocation) {
        for (int i = 0; i < tireTemperatures.length; i++) {
            Property<TireTemperature> temperature = tireTemperatures[i];
            if (temperature != null && temperature.getValue().getTireLocation() == tireLocation)
                return temperature;
        }

        return null;
    }

    /**
     * @return The wheel rpms.
     */
    public Property<WheelRpm>[] getWheelRpms() {
        return wheelRpms;
    }

    /**
     * The tire temperature for a tire.
     *
     * @param tireLocation The tire location.
     * @return The tire temperature.
     */
    @Nullable public Property<WheelRpm> getWheelRpm(TireLocation tireLocation) {
        for (int i = 0; i < wheelRpms.length; i++) {
            Property<WheelRpm> wheelRpm = wheelRpms[i];
            if (wheelRpm.getValue() != null && wheelRpm.getValue().getTireLocation() == tireLocation)
                return wheelRpm;
        }

        return null;
    }

    /**
     * @return The trouble codes.
     */
    public Property<DiagnosticsTroubleCode>[] getTroubleCodes() {
        return troubleCodes;
    }

    /**
     * @return The mileage meters.
     */
    public Property<Integer> getMileageMeters() {
        return mileageMeters;
    }

    DiagnosticsState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> checkControlMessages = new ArrayList<>();
        ArrayList<Property> tirePressures = new ArrayList<>();
        ArrayList<Property> tireTemperatures = new ArrayList<>();
        ArrayList<Property> wheelRpms = new ArrayList<>();
        ArrayList<Property> troubleCodes = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_MILEAGE:
                        return mileage.update(p);
                    case IDENTIFIER_OIL_TEMPERATURE:
                        return oilTemperature.update(p);
                    case IDENTIFIER_SPEED:
                        return speed.update(p);
                    case IDENTIFIER_RPM:
                        return rpm.update(p);
                    case IDENTIFIER_FUEL_LEVEL:
                        return fuelLevel.update(p);
                    case IDENTIFIER_RANGE:
                        return range.update(p);
                    case IDENTIFIER_WASHER_FLUID_LEVEL:
                        return washerFluidLevel.update(p);
                    case IDENTIFIER_BATTERY_VOLTAGE:
                        return batteryVoltage.update(p);
                    case IDENTIFIER_AD_BLUE_LEVEL:
                        return adBlueLevel.update(p);
                    case IDENTIFIER_DISTANCE_DRIVEN_SINCE_RESET:
                        return distanceDrivenSinceReset.update(p);
                    case IDENTIFIER_DISTANCE_DRIVEN_SINCE_ENGINE_START:
                        return distanceDrivenSinceEngineStart.update(p);
                    case IDENTIFIER_FUEL_VOLUME:
                        return fuelVolume.update(p);
                    case IDENTIFIER_ANTI_LOCK_BRAKING_ACTIVE:
                        return antiLockBrakingActive.update(p);
                    case IDENTIFIER_ENGINE_COOLANT_TEMPERATURE:
                        return engineCoolantTemperature.update(p);
                    case IDENTIFIER_ENGINE_TOTAL_OPERATING_HOURS:
                        return engineTotalOperatingHours.update(p);
                    case IDENTIFIER_ENGINE_TOTAL_FUEL_CONSUMPTION:
                        return engineTotalFuelConsumption.update(p);
                    case IDENTIFIER_BRAKE_FLUID_LEVEL:
                        return brakeFluidLevel.update(p);
                    case IDENTIFIER_ENGINE_TORQUE:
                        return engineTorque.update(p);
                    case IDENTIFIER_ENGINE_LOAD:
                        return engineLoad.update(p);
                    case IDENTIFIER_WHEEL_BASED_SPEED:
                        return wheelBasedSpeed.update(p);
                    case IDENTIFIER_BATTERY_LEVEL:
                        return batteryLevel.update(p);
                    case IDENTIFIER_CHECK_CONTROL_MESSAGES:
                        Property<CheckControlMessage> message =
                                new Property(CheckControlMessage.class, p);
                        checkControlMessages.add(message);
                        return message;
                    case IDENTIFIER_TIRE_PRESSURES:
                        Property<TirePressure> tirePressure = new Property(TirePressure.class, p);
                        tirePressures.add(tirePressure);
                        return tirePressure;
                    case IDENTIFIER_TIRE_TEMPERATURES:
                        Property<TireTemperature> tireTemperature =
                                new Property(TireTemperature.class, p);
                        tireTemperatures.add(tireTemperature);
                        return tireTemperature;
                    case IDENTIFIER_WHEEL_RPM:
                        Property<WheelRpm> wheelRpm = new Property(WheelRpm.class, p);
                        wheelRpms.add(wheelRpm);
                        return wheelRpm;
                    case IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE:
                        Property<DiagnosticsTroubleCode> troubleCode =
                                new Property(DiagnosticsTroubleCode.class, p);
                        troubleCodes.add(troubleCode);
                        return troubleCode;
                    case IDENTIFIER_MILEAGE_METERS:
                        return mileageMeters.update(p);
                }

                return null;
            });

            this.checkControlMessages = checkControlMessages.toArray(new Property[0]);
            this.tirePressures = tirePressures.toArray(new Property[0]);
            this.tireTemperatures = tireTemperatures.toArray(new Property[0]);
            this.wheelRpms = wheelRpms.toArray(new Property[0]);
            this.troubleCodes = troubleCodes.toArray(new Property[0]);
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private DiagnosticsState(Builder builder) {
        super(builder);
        mileage = builder.mileage;
        oilTemperature = builder.oilTemperature;
        speed = builder.speed;
        rpm = builder.rpm;
        range = builder.range;
        fuelLevel = builder.fuelLevel;
        washerFluidLevel = builder.washerFluidLevel;
        batteryVoltage = builder.batteryVoltage;
        adBlueLevel = builder.adBlueLevel;
        distanceDrivenSinceReset = builder.distanceDrivenSinceReset;
        distanceDrivenSinceEngineStart = builder.distanceDrivenSinceEngineStart;
        fuelVolume = builder.fuelVolume;

        // level7
        antiLockBrakingActive = builder.antiLockBrakingActive;
        engineCoolantTemperature = builder.engineCoolantTemperature;
        engineTotalOperatingHours = builder.engineTotalOperatingHours;
        engineTotalFuelConsumption = builder.engineTotalFuelConsumption;
        brakeFluidLevel = builder.brakeFluidLevel;
        engineTorque = builder.engineTorque;
        engineLoad = builder.engineLoad;
        wheelBasedSpeed = builder.wheelBasedSpeed;

        // level 8
        batteryLevel = builder.batteryLevel;

        checkControlMessages = builder.checkControlMessages.toArray(new Property[0]);
        tirePressures = builder.tirePressures.toArray(new Property[0]);
        tireTemperatures = builder.tireTemperatures.toArray(new Property[0]);
        wheelRpms = builder.wheelRpms.toArray(new Property[0]);
        troubleCodes = builder.troubleCodes.toArray(new Property[0]);
        mileageMeters = builder.mileageMeters;
    }

    public static final class Builder extends Command.Builder {
        private PropertyInteger mileage;
        private PropertyInteger oilTemperature;
        private PropertyInteger speed;
        private PropertyInteger rpm;
        private Property<Double> fuelLevel;
        private PropertyInteger range;
        private Property<WasherFluidLevel> washerFluidLevel;
        private Property<Float> batteryVoltage;
        private Property<Float> adBlueLevel;
        private PropertyInteger distanceDrivenSinceReset;
        private PropertyInteger distanceDrivenSinceEngineStart;
        private Property<Float> fuelVolume;

        private Property<Boolean> antiLockBrakingActive;
        private PropertyInteger engineCoolantTemperature;
        private Property<Float> engineTotalOperatingHours;
        private Property<Float> engineTotalFuelConsumption;
        private Property<BrakeFluidLevel> brakeFluidLevel;
        private Property<Double> engineTorque;
        private Property<Double> engineLoad;
        private PropertyInteger wheelBasedSpeed;

        private Property<Double> batteryLevel;
        private List<Property> tirePressures = new ArrayList<>();
        private List<Property> tireTemperatures = new ArrayList<>();
        private List<Property> wheelRpms = new ArrayList<>();
        private List<Property> checkControlMessages = new ArrayList<>();
        private List<Property> troubleCodes = new ArrayList<>();
        private PropertyInteger mileageMeters;

        public Builder() {
            super(TYPE);
        }

        public DiagnosticsState build() {
            return new DiagnosticsState(this);
        }

        /**
         * @param mileage The mileage.
         * @return The builder.
         */
        public Builder setMileage(Property<Integer> mileage) {
            this.mileage = new PropertyInteger(IDENTIFIER_MILEAGE, false, 4, mileage);
            addProperty(this.mileage);
            return this;
        }

        /**
         * @param oilTemperature The oil temperature in Celsius.
         * @return The builder.
         */
        public Builder setOilTemperature(Property<Integer> oilTemperature) {
            this.oilTemperature = new PropertyInteger(IDENTIFIER_OIL_TEMPERATURE, false, 2,
                    oilTemperature);
            addProperty(this.oilTemperature);
            return this;
        }

        /**
         * @param speed The speed in km/h.
         * @return The builder.
         */
        public Builder setSpeed(Property<Integer> speed) {
            this.speed = new PropertyInteger(IDENTIFIER_SPEED, false, 2, speed);
            addProperty(this.speed);
            return this;
        }

        /**
         * @param rpm The RPM of the engine.
         * @return The builder.
         */
        public Builder setRpm(Property<Integer> rpm) {
            this.rpm = new PropertyInteger(IDENTIFIER_RPM, false, 2, rpm);
            addProperty(this.rpm);
            return this;
        }

        /**
         * @param fuelLevel The fuel level percentage between 0 and 1.
         * @return The builder.
         */
        public Builder setFuelLevel(Property<Double> fuelLevel) {
            fuelLevel.setIdentifier(IDENTIFIER_FUEL_LEVEL);
            this.fuelLevel = fuelLevel;
            addProperty(fuelLevel);
            return this;
        }

        /**
         * @param range The estimated range.
         * @return The builder.
         */
        public Builder setRange(Property<Integer> range) {
            this.range = new PropertyInteger(IDENTIFIER_RANGE, false, 2, range);
            addProperty(this.range);
            return this;
        }

        /**
         * @param washerFluidLevel The washer fluid level.
         * @return The builder.
         */
        public Builder setWasherFluidLevel
        (Property<WasherFluidLevel> washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel;
            washerFluidLevel.setIdentifier(IDENTIFIER_WASHER_FLUID_LEVEL);
            addProperty(washerFluidLevel);
            return this;
        }

        /**
         * @param batteryVoltage The battery voltage.
         * @return The builder.
         */
        public Builder setBatteryVoltage(Property<Float> batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
            batteryVoltage.setIdentifier(IDENTIFIER_BATTERY_VOLTAGE);
            addProperty(batteryVoltage);
            return this;
        }

        /**
         * @param adBlueLevel The adBlue level in liters.
         * @return The builder.
         */
        public Builder setAdBlueLevel(Property<Float> adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
            adBlueLevel.setIdentifier(IDENTIFIER_AD_BLUE_LEVEL);
            addProperty(adBlueLevel);
            return this;
        }

        /**
         * @param distanceDrivenSinceReset The distance driven in km since reset.
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceReset(Property<Integer> distanceDrivenSinceReset) {
            this.distanceDrivenSinceReset =
                    new PropertyInteger(IDENTIFIER_DISTANCE_DRIVEN_SINCE_RESET, false, 2,
                            distanceDrivenSinceReset);
            addProperty(this.distanceDrivenSinceReset);
            return this;
        }

        /**
         * @param distanceDrivenSinceEngineStart The distance driven in km since engine start
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceEngineStart(Property<Integer> distanceDrivenSinceEngineStart) {
            this.distanceDrivenSinceEngineStart =
                    new PropertyInteger(IDENTIFIER_DISTANCE_DRIVEN_SINCE_ENGINE_START, false, 2,
                            distanceDrivenSinceEngineStart);
            addProperty(this.distanceDrivenSinceEngineStart);
            return this;
        }

        /**
         * @param fuelVolume The fuel volume measured in liters.
         * @return The builder.
         */
        public Builder setFuelVolume(Property<Float> fuelVolume) {
            this.fuelVolume = fuelVolume;
            fuelVolume.setIdentifier(IDENTIFIER_FUEL_VOLUME);
            addProperty(fuelVolume);
            return this;
        }

        /**
         * @param antiLockBrakingActive The anti-lock braking system (ABS) state.
         * @return The builder.
         */
        public Builder setAntiLockBrakingActive
        (Property<Boolean> antiLockBrakingActive) {
            this.antiLockBrakingActive = antiLockBrakingActive;
            antiLockBrakingActive.setIdentifier(IDENTIFIER_ANTI_LOCK_BRAKING_ACTIVE);
            addProperty(antiLockBrakingActive);
            return this;
        }

        /**
         * @param engineCoolantTemperature The engine coolant temperature in Celsius, whereas can be
         *                                 negative.
         * @return The builder.
         */
        public Builder setEngineCoolantTemperature(Property<Integer> engineCoolantTemperature) {
            this.engineCoolantTemperature =
                    new PropertyInteger(IDENTIFIER_ENGINE_COOLANT_TEMPERATURE, false, 2,
                            engineCoolantTemperature);
            addProperty(this.engineCoolantTemperature);
            return this;
        }

        /**
         * @param engineTotalOperatingHours The the accumulated time of engine operation.
         * @return The builder.
         */
        public Builder setEngineTotalOperatingHours(Property<Float> engineTotalOperatingHours) {
            this.engineTotalOperatingHours = engineTotalOperatingHours;
            engineTotalOperatingHours.setIdentifier(IDENTIFIER_ENGINE_TOTAL_OPERATING_HOURS);
            addProperty(engineTotalOperatingHours);
            return this;
        }

        /**
         * @param engineTotalFuelConsumption The the accumulated lifespan fuel consumption in
         *                                   liters.
         * @return The builder.
         */
        public Builder setEngineTotalFuelConsumption(Property<Float> engineTotalFuelConsumption) {
            this.engineTotalFuelConsumption = engineTotalFuelConsumption;
            engineTotalFuelConsumption.setIdentifier(IDENTIFIER_ENGINE_TOTAL_FUEL_CONSUMPTION);
            addProperty(engineTotalFuelConsumption);
            return this;
        }

        /**
         * @param brakeFluidLevel The brake fluid level.
         * @return The builder.
         */
        public Builder setBrakeFluidLevel(Property<BrakeFluidLevel> brakeFluidLevel) {
            this.brakeFluidLevel = brakeFluidLevel;
            brakeFluidLevel.setIdentifier(IDENTIFIER_BRAKE_FLUID_LEVEL);
            addProperty(brakeFluidLevel);
            return this;
        }

        /**
         * @param engineTorque The current engine torque percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineTorque(Property<Double> engineTorque) {
            this.engineTorque = engineTorque;
            engineTorque.setIdentifier(IDENTIFIER_ENGINE_TORQUE);
            addProperty(engineTorque);
            return this;
        }

        /**
         * @param engineLoad The current engine load percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineLoad(Property<Double> engineLoad) {
            this.engineLoad = engineLoad;
            engineLoad.setIdentifier(IDENTIFIER_ENGINE_LOAD);
            addProperty(engineLoad);
            return this;
        }

        /**
         * @param wheelBasedSpeed The vehicle speed in km/h measured at the wheel base, whereas can
         *                        be negative.
         * @return The builder.
         */
        public Builder setWheelBasedSpeed(Property<Integer> wheelBasedSpeed) {
            this.wheelBasedSpeed = new PropertyInteger(IDENTIFIER_WHEEL_BASED_SPEED, true, 2,
                    wheelBasedSpeed);
            addProperty(this.wheelBasedSpeed);
            return this;
        }

        /**
         * Set the battery level.
         *
         * @param batteryLevel The battery level.
         * @return The builder.
         */
        public Builder setBatteryLevel(Property<Double> batteryLevel) {
            this.batteryLevel = batteryLevel;
            batteryLevel.setIdentifier(IDENTIFIER_BATTERY_LEVEL);
            addProperty(batteryLevel);
            return this;
        }

        /**
         * Add an array of tire pressures.
         *
         * @param pressures The tire pressures.
         * @return The builder.
         */
        public Builder setTirePressures(Property<TirePressure>[] pressures) {
            tirePressures.clear();
            for (int i = 0; i < pressures.length; i++) {
                addTirePressure(pressures[i]);
            }

            return this;
        }

        /**
         * Add a single tire pressure.
         *
         * @param pressure The tire pressure.
         * @return The builder.
         */
        public Builder addTirePressure(Property<TirePressure> pressure) {
            pressure.setIdentifier(IDENTIFIER_TIRE_PRESSURES);
            addProperty(pressure);
            tirePressures.add(pressure);
            return this;
        }

        /**
         * Add an array of tire temperatures.
         *
         * @param temperatures The tire temperatures.
         * @return The builder.
         */
        public Builder setTireTemperatures(Property<TireTemperature>[] temperatures) {
            this.tireTemperatures.clear();

            for (int i = 0; i < temperatures.length; i++) {
                addTireTemperature(temperatures[i]);
            }

            return this;
        }

        /**
         * Add a single tire temperature.
         *
         * @param temperature The tire temperature.
         * @return The builder.
         */
        public Builder addTireTemperature(Property<TireTemperature> temperature) {
            temperature.setIdentifier(IDENTIFIER_TIRE_TEMPERATURES);
            addProperty(temperature);
            tireTemperatures.add(temperature);
            return this;
        }

        /**
         * Add an array of wheel RPM's.
         *
         * @param wheelRpms The wheel's RPM.
         * @return The builder.
         */
        public Builder setWheelRpms(Property<WheelRpm>[] wheelRpms) {
            for (int i = 0; i < wheelRpms.length; i++) {
                addWheelRpm(wheelRpms[i]);
            }

            return this;
        }

        /**
         * Add a single wheel Rpm.
         *
         * @param wheelRpm The wheel RPM.
         * @return The builder.
         */
        public Builder addWheelRpm(Property<WheelRpm> wheelRpm) {
            wheelRpm.setIdentifier(IDENTIFIER_WHEEL_RPM);
            addProperty(wheelRpm);
            wheelRpms.add(wheelRpm);
            return this;
        }

        /**
         * @param checkControlMessages The check control messages.
         * @return The builder.
         */
        public Builder setCheckControlMessages(Property<CheckControlMessage>[] checkControlMessages) {
            this.checkControlMessages.clear();

            for (Property<CheckControlMessage> checkControlMessage : checkControlMessages) {
                addCheckControlMessage(checkControlMessage);
            }

            return this;
        }

        /**
         * @param checkControlMessage The check control message.
         * @return The builder.
         */
        public Builder addCheckControlMessage(Property<CheckControlMessage> checkControlMessage) {
            addProperty(checkControlMessage);
            checkControlMessages.add(checkControlMessage.setIdentifier(IDENTIFIER_CHECK_CONTROL_MESSAGES));
            return this;
        }

        /**
         * @param troubleCodes The trouble codes
         * @return The builder.
         */
        public Builder setTroubleCodes(Property<DiagnosticsTroubleCode>[] troubleCodes) {
            this.troubleCodes.clear();

            for (Property<DiagnosticsTroubleCode> troubleCode :
                    troubleCodes) {
                addTroubleCode(troubleCode);
            }

            return this;
        }

        /**
         * @param code The trouble code.
         * @return The builder.
         */
        public Builder addTroubleCode(Property<DiagnosticsTroubleCode> code) {
            code.setIdentifier(IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE);
            addProperty(code);
            troubleCodes.add(code);
            return this;
        }

        /**
         * @param mileageMeters The mileage meters.
         * @return The builder.
         */
        public Builder setMileageMeters(Property<Integer> mileageMeters) {
            this.mileageMeters = new PropertyInteger(IDENTIFIER_MILEAGE_METERS, false, 4,
                    mileageMeters);
            addProperty(this.mileageMeters);
            return this;
        }
    }
}