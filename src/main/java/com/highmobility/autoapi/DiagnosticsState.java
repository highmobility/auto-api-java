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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyPercentage;
import com.highmobility.autoapi.property.diagnostics.BrakeFluidLevel;
import com.highmobility.autoapi.property.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.property.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.property.diagnostics.TirePressure;
import com.highmobility.autoapi.property.diagnostics.TireTemperature;
import com.highmobility.autoapi.property.diagnostics.WasherFluidLevel;
import com.highmobility.autoapi.property.diagnostics.WheelRpm;
import com.highmobility.autoapi.property.value.TireLocation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Diagnostics State command is received by the car. The new status is
 * included in the command payload and may be the result of user, device or car triggered action.
 */
public class DiagnosticsState extends CommandWithProperties {
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

    ObjectPropertyInteger mileage = new ObjectPropertyInteger(IDENTIFIER_MILEAGE, false);
    ObjectPropertyInteger oilTemperature = new ObjectPropertyInteger(IDENTIFIER_OIL_TEMPERATURE,
            false);
    ObjectPropertyInteger speed = new ObjectPropertyInteger(IDENTIFIER_SPEED, false);
    ObjectPropertyInteger rpm = new ObjectPropertyInteger(IDENTIFIER_RPM, false);
    ObjectPropertyPercentage fuelLevel = new ObjectPropertyPercentage(IDENTIFIER_FUEL_LEVEL);
    ObjectPropertyInteger range = new ObjectPropertyInteger(IDENTIFIER_RANGE, false);
    WasherFluidLevel washerFluidLevel = new WasherFluidLevel(IDENTIFIER_WASHER_FLUID_LEVEL);
    FloatProperty batteryVoltage = new FloatProperty(IDENTIFIER_BATTERY_VOLTAGE);
    FloatProperty adBlueLevel = new FloatProperty(IDENTIFIER_AD_BLUE_LEVEL);
    ObjectPropertyInteger distanceDrivenSinceReset =
            new ObjectPropertyInteger(IDENTIFIER_DISTANCE_DRIVEN_SINCE_RESET, false);
    ObjectPropertyInteger distanceDrivenSinceEngineStart =
            new ObjectPropertyInteger(IDENTIFIER_DISTANCE_DRIVEN_SINCE_ENGINE_START, false);
    FloatProperty fuelVolume = new FloatProperty(IDENTIFIER_FUEL_VOLUME);

    // level7
    ObjectProperty<Boolean> antiLockBrakingActive =
            new ObjectProperty<>(Boolean.class, IDENTIFIER_ANTI_LOCK_BRAKING_ACTIVE);
    ObjectPropertyInteger engineCoolantTemperature =
            new ObjectPropertyInteger(IDENTIFIER_ENGINE_COOLANT_TEMPERATURE, false);
    FloatProperty engineTotalOperatingHours =
            new FloatProperty(IDENTIFIER_ENGINE_TOTAL_OPERATING_HOURS);
    FloatProperty engineTotalFuelConsumption =
            new FloatProperty(IDENTIFIER_ENGINE_TOTAL_FUEL_CONSUMPTION);
    BrakeFluidLevel brakeFluidLevel = new BrakeFluidLevel(IDENTIFIER_BRAKE_FLUID_LEVEL);
    ObjectPropertyPercentage engineTorque = new ObjectPropertyPercentage(IDENTIFIER_ENGINE_TORQUE);
    ObjectPropertyPercentage engineLoad = new ObjectPropertyPercentage(IDENTIFIER_ENGINE_LOAD);
    ObjectPropertyInteger wheelBasedSpeed =
            new ObjectPropertyInteger(IDENTIFIER_WHEEL_BASED_SPEED, true);

    // level8
    ObjectPropertyPercentage batteryLevel = new ObjectPropertyPercentage(IDENTIFIER_BATTERY_LEVEL);
    CheckControlMessage[] checkControlMessages;
    TirePressure[] tirePressures;
    TireTemperature[] tireTemperatures;
    WheelRpm[] wheelRpms;
    DiagnosticsTroubleCode[] troubleCodes;

    ObjectPropertyInteger mileageMeters = new ObjectPropertyInteger(IDENTIFIER_MILEAGE_METERS,
            false);

    /**
     * @return The car mileage (odometer) in km.
     */
    @Nullable public ObjectPropertyInteger getMileage() {
        return mileage;
    }

    /**
     * @return The engine oil temperature in Celsius, whereas can be negative.
     */
    @Nullable public ObjectPropertyInteger getOilTemperature() {
        return oilTemperature;
    }

    /**
     * @return The car speed in km/h, whereas can be negative.
     */
    @Nullable public ObjectPropertyInteger getSpeed() {
        return speed;
    }

    /**
     * @return The RPM of the engine.
     */
    @Nullable public ObjectPropertyInteger getRpm() {
        return rpm;
    }

    /**
     * @return The Fuel level percentage.
     */
    @Nullable public ObjectPropertyPercentage getFuelLevel() {
        return fuelLevel;
    }

    /**
     * @return The estimated range.
     */
    @Nullable public ObjectPropertyInteger getRange() {
        return range;
    }

    /**
     * @return Washer fluid level.
     */
    @Nullable public WasherFluidLevel getWasherFluidLevel() {
        return washerFluidLevel;
    }

    /**
     * @return The battery voltage.
     */
    @Nullable public FloatProperty getBatteryVoltage() {
        return batteryVoltage;
    }

    /**
     * @return AdBlue level in liters.
     */
    @Nullable public FloatProperty getAdBlueLevel() {
        return adBlueLevel;
    }

    /**
     * @return The distance driven in km since reset.
     */
    @Nullable public ObjectPropertyInteger getDistanceDrivenSinceReset() {
        return distanceDrivenSinceReset;
    }

    /**
     * @return The distance driven in km since engine start.
     */
    @Nullable public ObjectPropertyInteger getDistanceDrivenSinceEngineStart() {
        return distanceDrivenSinceEngineStart;
    }

    /**
     * @return The fuel volume measured in liters.
     */
    @Nullable public FloatProperty getFuelVolume() {
        return fuelVolume;
    }

    /**
     * @return The anti-lock braking system (ABS) state.
     */
    @Nullable public ObjectProperty<Boolean> isAntiLockBrakingActive() {
        return antiLockBrakingActive;
    }

    /**
     * @return The engine coolant temperature in Celsius, whereas can be negative.
     */
    @Nullable public ObjectPropertyInteger getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }

    /**
     * @return The the accumulated time of engine operation.
     */
    @Nullable public FloatProperty getEngineTotalOperatingHours() {
        return engineTotalOperatingHours;
    }

    /**
     * @return The the accumulated lifespan fuel consumption in liters.
     */
    @Nullable public FloatProperty getEngineTotalFuelConsumption() {
        return engineTotalFuelConsumption;
    }

    /**
     * @return The brake fluid level.
     */
    @Nullable public BrakeFluidLevel getBrakeFluidLevel() {
        return brakeFluidLevel;
    }

    /**
     * @return The current engine torque percentage.
     */
    @Nullable public ObjectPropertyPercentage getEngineTorque() {
        return engineTorque;
    }

    /**
     * @return The current engine load percentage.
     */
    @Nullable public ObjectPropertyPercentage getEngineLoad() {
        return engineLoad;
    }

    /**
     * @return The vehicle speed in km/h measured at the wheel base, whereas can be negative.
     */
    @Nullable public ObjectPropertyInteger getWheelBasedSpeed() {
        return wheelBasedSpeed;
    }

    /**
     * @return The battery level percentage.
     */
    @Nullable public ObjectPropertyPercentage getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The Check Control Message.
     */
    @Nullable public CheckControlMessage[] getCheckControlMessages() {
        return checkControlMessages;
    }

    /**
     * @return The tire pressures.
     */
    public TirePressure[] getTirePressures() {
        return tirePressures;
    }

    /**
     * Get the tire pressure for a tire.
     *
     * @param tireLocation The tire location
     * @return The tire pressure.
     */
    @Nullable public TirePressure getTirePressure(TireLocation tireLocation) {
        for (int i = 0; i < tirePressures.length; i++) {
            TirePressure pressure = tirePressures[i];
            if (pressure != null && pressure.getValue().getTireLocation() == tireLocation)
                return pressure;
        }

        return null;
    }

    /**
     * @return The tire temperatures.
     */
    public TireTemperature[] getTireTemperatures() {
        return tireTemperatures;
    }

    /**
     * The tire temperature for a tire.
     *
     * @param tireLocation The tire location.
     * @return The tire temperature.
     */
    @Nullable public TireTemperature getTireTemperature(TireLocation tireLocation) {
        for (int i = 0; i < tireTemperatures.length; i++) {
            TireTemperature temperature = tireTemperatures[i];
            if (temperature != null && temperature.getValue().getTireLocation() == tireLocation)
                return temperature;
        }

        return null;
    }

    /**
     * @return The wheel rpms.
     */
    public WheelRpm[] getWheelRpms() {
        return wheelRpms;
    }

    /**
     * The tire temperature for a tire.
     *
     * @param tireLocation The tire location.
     * @return The tire temperature.
     */
    @Nullable public WheelRpm getWheelRpm(TireLocation tireLocation) {
        for (int i = 0; i < wheelRpms.length; i++) {
            WheelRpm wheelRpm = wheelRpms[i];
            if (wheelRpm.getValue() != null && wheelRpm.getValue().getTireLocation() == tireLocation)
                return wheelRpm;
        }

        return null;
    }

    /**
     * @return The trouble codes.
     */
    public DiagnosticsTroubleCode[] getTroubleCodes() {
        return troubleCodes;
    }

    /**
     * @return The mileage meters.
     */
    public ObjectPropertyInteger getMileageMeters() {
        return mileageMeters;
    }

    DiagnosticsState(byte[] bytes) {
        super(bytes);

        ArrayList<CheckControlMessage> checkControlMessages = new ArrayList<>();
        ArrayList<TirePressure> tirePressures = new ArrayList<>();
        ArrayList<TireTemperature> tireTemperatures = new ArrayList<>();
        ArrayList<WheelRpm> wheelRpms = new ArrayList<>();
        ArrayList<DiagnosticsTroubleCode> troubleCodes = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
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
                        CheckControlMessage message = new CheckControlMessage(p);
                        checkControlMessages.add(message);
                        return message;
                    case IDENTIFIER_TIRE_PRESSURES:
                        TirePressure tirePressure = new TirePressure(p);
                        tirePressures.add(tirePressure);
                        return tirePressure;
                    case IDENTIFIER_TIRE_TEMPERATURES:
                        TireTemperature tireTemperature = new TireTemperature(p);
                        tireTemperatures.add(tireTemperature);
                        return tireTemperature;
                    case IDENTIFIER_WHEEL_RPM:
                        WheelRpm wheelRpm = new WheelRpm(p);
                        wheelRpms.add(wheelRpm);
                        return wheelRpm;
                    case IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE:
                        DiagnosticsTroubleCode troubleCode = new DiagnosticsTroubleCode(p);
                        troubleCodes.add(troubleCode);
                        return troubleCode;
                    case IDENTIFIER_MILEAGE_METERS:
                        return mileageMeters.update(p);
                }

                return null;
            });

            this.checkControlMessages = checkControlMessages.toArray(new CheckControlMessage[0]);
            this.tirePressures = tirePressures.toArray(new TirePressure[0]);
            this.tireTemperatures = tireTemperatures.toArray(new TireTemperature[0]);
            this.wheelRpms = wheelRpms.toArray(new WheelRpm[0]);
            this.troubleCodes = troubleCodes.toArray(new DiagnosticsTroubleCode[0]);
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

        checkControlMessages = builder.checkControlMessages.toArray(new CheckControlMessage[0]);
        tirePressures = builder.tirePressures.toArray(new TirePressure[0]);
        tireTemperatures = builder.tireTemperatures.toArray(new TireTemperature[0]);
        wheelRpms = builder.wheelRpms.toArray(new WheelRpm[0]);
        troubleCodes = builder.troubleCodes.toArray(new DiagnosticsTroubleCode[0]);
        mileageMeters = builder.mileageMeters;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ObjectPropertyInteger mileage;
        private ObjectPropertyInteger oilTemperature;
        private ObjectPropertyInteger speed;
        private ObjectPropertyInteger rpm;
        private ObjectPropertyPercentage fuelLevel;
        private ObjectPropertyInteger range;
        private WasherFluidLevel washerFluidLevel;
        private FloatProperty batteryVoltage;
        private FloatProperty adBlueLevel;
        private ObjectPropertyInteger distanceDrivenSinceReset;
        private ObjectPropertyInteger distanceDrivenSinceEngineStart;
        private FloatProperty fuelVolume;

        private ObjectProperty<Boolean> antiLockBrakingActive;
        private ObjectPropertyInteger engineCoolantTemperature;
        private FloatProperty engineTotalOperatingHours;
        private FloatProperty engineTotalFuelConsumption;
        private BrakeFluidLevel brakeFluidLevel;
        private ObjectPropertyPercentage engineTorque;
        private ObjectPropertyPercentage engineLoad;
        private ObjectPropertyInteger wheelBasedSpeed;

        private ObjectPropertyPercentage batteryLevel;
        private List<TirePressure> tirePressures = new ArrayList<>();
        private List<TireTemperature> tireTemperatures = new ArrayList<>();
        private List<WheelRpm> wheelRpms = new ArrayList<>();
        private List<CheckControlMessage> checkControlMessages = new ArrayList<>();
        private List<DiagnosticsTroubleCode> troubleCodes = new ArrayList<>();
        private ObjectPropertyInteger mileageMeters;

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
        public Builder setMileage(ObjectPropertyInteger mileage) {
            this.mileage = mileage;
            mileage.update(IDENTIFIER_MILEAGE, false, 3);
            addProperty(mileage);
            return this;
        }

        /**
         * @param oilTemperature The oil temperature in Celsius.
         * @return The builder.
         */
        public Builder setOilTemperature(ObjectPropertyInteger oilTemperature) {
            this.oilTemperature = oilTemperature;
            oilTemperature.update(IDENTIFIER_OIL_TEMPERATURE, false, 2);
            addProperty(oilTemperature);
            return this;
        }

        /**
         * @param speed The speed in km/h.
         * @return The builder.
         */
        public Builder setSpeed(ObjectPropertyInteger speed) {
            this.speed = speed;
            speed.update(IDENTIFIER_SPEED, false, 2);
            addProperty(speed);
            return this;
        }

        /**
         * @param rpm The RPM of the engine.
         * @return The builder.
         */
        public Builder setRpm(ObjectPropertyInteger rpm) {
            this.rpm = rpm;
            rpm.update(IDENTIFIER_RPM, false, 2);
            addProperty(rpm);
            return this;
        }

        /**
         * @param fuelLevel The fuel level percentage between 0 and 1.
         * @return The builder.
         */
        public Builder setFuelLevel(ObjectPropertyPercentage fuelLevel) {
            fuelLevel.setIdentifier(IDENTIFIER_FUEL_LEVEL);
            this.fuelLevel = fuelLevel;
            addProperty(fuelLevel);
            return this;
        }

        /**
         * @param range The estimated range.
         * @return The builder.
         */
        public Builder setRange(ObjectPropertyInteger range) {
            this.range = range;
            range.update(IDENTIFIER_RANGE, false, 2);
            addProperty(range);
            return this;
        }

        /**
         * @param washerFluidLevel The washer fluid level.
         * @return The builder.
         */
        public Builder setWasherFluidLevel(WasherFluidLevel washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel;
            washerFluidLevel.setIdentifier(IDENTIFIER_WASHER_FLUID_LEVEL);
            addProperty(washerFluidLevel);
            return this;
        }

        /**
         * @param batteryVoltage The battery voltage.
         * @return The builder.
         */
        public Builder setBatteryVoltage(FloatProperty batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
            batteryVoltage.setIdentifier(IDENTIFIER_BATTERY_VOLTAGE);
            addProperty(batteryVoltage);
            return this;
        }

        /**
         * @param adBlueLevel The adBlue level in liters.
         * @return The builder.
         */
        public Builder setAdBlueLevel(FloatProperty adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
            adBlueLevel.setIdentifier(IDENTIFIER_AD_BLUE_LEVEL);
            addProperty(adBlueLevel);
            return this;
        }

        /**
         * @param distanceDrivenSinceReset The distance driven in km since reset.
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceReset(ObjectPropertyInteger distanceDrivenSinceReset) {
            this.distanceDrivenSinceReset = distanceDrivenSinceReset;
            distanceDrivenSinceReset.update(IDENTIFIER_DISTANCE_DRIVEN_SINCE_RESET, false, 2);
            addProperty(distanceDrivenSinceReset);
            return this;
        }

        /**
         * @param distanceDrivenSinceEngineStart The distance driven in km since engine start
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceEngineStart(ObjectPropertyInteger distanceDrivenSinceEngineStart) {
            this.distanceDrivenSinceEngineStart = distanceDrivenSinceEngineStart;
            distanceDrivenSinceEngineStart.update(IDENTIFIER_DISTANCE_DRIVEN_SINCE_ENGINE_START,
                    false, 2);
            addProperty(distanceDrivenSinceEngineStart);
            return this;
        }

        /**
         * @param fuelVolume The fuel volume measured in liters.
         * @return The builder.
         */
        public Builder setFuelVolume(FloatProperty fuelVolume) {
            this.fuelVolume = fuelVolume;
            fuelVolume.setIdentifier(IDENTIFIER_FUEL_VOLUME);
            addProperty(fuelVolume);
            return this;
        }

        /**
         * @param antiLockBrakingActive The anti-lock braking system (ABS) state.
         * @return The builder.
         */
        public Builder setAntiLockBrakingActive(ObjectProperty<Boolean> antiLockBrakingActive) {
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
        public Builder setEngineCoolantTemperature(ObjectPropertyInteger engineCoolantTemperature) {
            this.engineCoolantTemperature = engineCoolantTemperature;
            engineCoolantTemperature.update(IDENTIFIER_ENGINE_COOLANT_TEMPERATURE, false, 2);
            addProperty(engineCoolantTemperature);
            return this;
        }

        /**
         * @param engineTotalOperatingHours The the accumulated time of engine operation.
         * @return The builder.
         */
        public Builder setEngineTotalOperatingHours(FloatProperty engineTotalOperatingHours) {
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
        public Builder setEngineTotalFuelConsumption(FloatProperty engineTotalFuelConsumption) {
            this.engineTotalFuelConsumption = engineTotalFuelConsumption;
            engineTotalFuelConsumption.setIdentifier(IDENTIFIER_ENGINE_TOTAL_FUEL_CONSUMPTION);
            addProperty(engineTotalFuelConsumption);
            return this;
        }

        /**
         * @param brakeFluidLevel The brake fluid level.
         * @return The builder.
         */
        public Builder setBrakeFluidLevel(BrakeFluidLevel brakeFluidLevel) {
            this.brakeFluidLevel = brakeFluidLevel;
            brakeFluidLevel.setIdentifier(IDENTIFIER_BRAKE_FLUID_LEVEL);
            addProperty(brakeFluidLevel);
            return this;
        }

        /**
         * @param engineTorque The current engine torque percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineTorque(ObjectPropertyPercentage engineTorque) {
            this.engineTorque = engineTorque;
            engineTorque.setIdentifier(IDENTIFIER_ENGINE_TORQUE);
            addProperty(engineTorque);
            return this;
        }

        /**
         * @param engineLoad The current engine load percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineLoad(ObjectPropertyPercentage engineLoad) {
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
        public Builder setWheelBasedSpeed(ObjectPropertyInteger wheelBasedSpeed) {
            this.wheelBasedSpeed = wheelBasedSpeed;
            wheelBasedSpeed.update(IDENTIFIER_WHEEL_BASED_SPEED, true, 2);
            addProperty(wheelBasedSpeed);
            return this;
        }

        /**
         * Set the battery level.
         *
         * @param batteryLevel The battery level.
         * @return The builder.
         */
        public Builder setBatteryLevel(ObjectPropertyPercentage batteryLevel) {
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
        public Builder setTirePressures(TirePressure[] pressures) {
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
        public Builder addTirePressure(TirePressure pressure) {
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
        public Builder setTireTemperatures(TireTemperature[] temperatures) {
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
        public Builder addTireTemperature(TireTemperature temperature) {
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
        public Builder setWheelRpms(WheelRpm[] wheelRpms) {
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
        public Builder addWheelRpm(WheelRpm wheelRpm) {
            wheelRpm.setIdentifier(IDENTIFIER_WHEEL_RPM);
            addProperty(wheelRpm);
            wheelRpms.add(wheelRpm);
            return this;
        }

        public Builder setCheckControlMessages(CheckControlMessage[] checkControlMessages) {
            this.checkControlMessages.clear();

            for (CheckControlMessage checkControlMessage : checkControlMessages) {
                addCheckControlMessage(checkControlMessage);
            }

            return this;
        }

        public void addCheckControlMessage(CheckControlMessage checkControlMessage) {
            checkControlMessage.setIdentifier(IDENTIFIER_CHECK_CONTROL_MESSAGES);
            addProperty(checkControlMessage);
            checkControlMessages.add(checkControlMessage);
        }

        public Builder setTroubleCodes(DiagnosticsTroubleCode[] troubleCodes) {
            this.troubleCodes.clear();

            for (DiagnosticsTroubleCode troubleCode : troubleCodes) {
                addTroubleCode(troubleCode);
            }

            return this;
        }

        public Builder addTroubleCode(DiagnosticsTroubleCode code) {
            code.setIdentifier(IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE);
            addProperty(code);
            troubleCodes.add(code);
            return this;
        }

        /**
         * @param mileageMeters The mileage meters.
         * @return The builder.
         */
        public Builder setMileageMeters(ObjectPropertyInteger mileageMeters) {
            this.mileageMeters = mileageMeters;
            mileageMeters.update(IDENTIFIER_MILEAGE_METERS, false, 4);
            addProperty(mileageMeters);
            return this;
        }
    }
}