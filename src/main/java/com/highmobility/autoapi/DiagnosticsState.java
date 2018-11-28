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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
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

    private static final byte MILEAGE_IDENTIFIER = 0x01;
    private static final byte OIL_TEMPERATURE_IDENTIFIER = 0x02;
    private static final byte SPEED_IDENTIFIER = 0x03;
    private static final byte RPM_IDENTIFIER = 0x04;
    private static final byte FUEL_LEVEL_IDENTIFIER = 0x05;
    private static final byte RANGE_IDENTIFIER = 0x06;
    private static final byte WASHER_FLUID_LEVEL_IDENTIFIER = 0x09;
    private static final byte BATTERY_VOLTAGE_IDENTIFIER = 0x0B;
    private static final byte AD_BLUE_LEVEL_IDENTIFIER = 0x0C;
    private static final byte DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER = 0x0D;
    private static final byte DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER = 0x0E;
    private static final byte FUEL_VOLUME_IDENTIFIER = 0x0F;

    private static final byte ANTI_LOCK_BRAKING_ACTIVE_IDENTIFIER = 0x10;
    private static final byte ENGINE_COOLANT_TEMPERATURE_IDENTIFIER = 0x11;
    private static final byte ENGINE_TOTAL_OPERATING_HOURS_IDENTIFIER = 0x12;
    private static final byte ENGINE_TOTAL_FUEL_CONSUMPTION_IDENTIFIER = 0x13;

    private static final byte ENGINE_TORQUE_IDENTIFIER = 0x15;
    private static final byte ENGINE_LOAD_IDENTIFIER = 0x16;
    private static final byte WHEEL_BASE_SPEED_IDENTIFIER = 0x17;

    private static final byte IDENTIFIER_BATTERY_LEVEL = 0x18;
    private static final byte IDENTIFIER_CHECK_CONTROL_MESSAGES = 0x19;
    private static final byte IDENTIFIER_TIRE_PRESSURES = 0x1A;
    private static final byte IDENTIFIER_TIRE_TEMPERATURES = 0x1B;
    private static final byte IDENTIFIER_WHEEL_RPM = 0x1C;
    private static final byte IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE = 0x1D;

    private static final byte IDENTIFIER_BRAKE_FLUID = 0x14;
    private static final byte MILEAGE_METERS_IDENTIFIER = 0x1E;

    Integer mileage;
    Integer oilTemperature;
    Integer speed;
    Integer rpm;
    Float fuelLevel;
    Integer range;
    WasherFluidLevel washerFluidLevel;
    Float batteryVoltage;
    Float adBlueLevel;
    Integer distanceDrivenSinceReset;
    Integer distanceDrivenSinceEngineStart;
    Float fuelVolume;

    // level7
    Boolean antiLockBrakingActive;
    Integer engineCoolantTemperature;
    Float engineTotalOperatingHours;
    Float engineTotalFuelConsumption;
    BrakeFluidLevel brakeFluidLevel;
    Float engineTorque;
    Float engineLoad;
    Integer wheelBasedSpeed;

    // level8
    Float batteryLevel;
    CheckControlMessage[] checkControlMessages;
    TirePressure[] tirePressures;
    TireTemperature[] tireTemperatures;
    WheelRpm[] wheelRpms;
    DiagnosticsTroubleCode[] troubleCodes;

    Integer mileageMeters;

    /**
     * @return The car mileage (odometer) in km.
     */
    @Nullable public Integer getMileage() {
        return mileage;
    }

    /**
     * @return The engine oil temperature in Celsius, whereas can be negative.
     */
    @Nullable public Integer getOilTemperature() {
        return oilTemperature;
    }

    /**
     * @return The car speed in km/h, whereas can be negative.
     */
    @Nullable public Integer getSpeed() {
        return speed;
    }

    /**
     * @return The RPM of the engine.
     */
    @Nullable public Integer getRpm() {
        return rpm;
    }

    /**
     * @return The Fuel level percentage between 0-100.
     */
    @Nullable public Float getFuelLevel() {
        return fuelLevel;
    }

    /**
     * @return The estimated range.
     */
    @Nullable public Integer getRange() {
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
    @Nullable public Float getBatteryVoltage() {
        return batteryVoltage;
    }

    /**
     * @return AdBlue level in liters.
     */
    @Nullable public Float getAdBlueLevel() {
        return adBlueLevel;
    }

    /**
     * @return The distance driven in km since reset.
     */
    @Nullable public Integer getDistanceDrivenSinceReset() {
        return distanceDrivenSinceReset;
    }

    /**
     * @return The distance driven in km since engine start.
     */
    @Nullable public Integer getDistanceDrivenSinceEngineStart() {
        return distanceDrivenSinceEngineStart;
    }

    /**
     * @return The fuel volume measured in liters.
     */
    @Nullable public Float getFuelVolume() {
        return fuelVolume;
    }

    /**
     * @return The anti-lock braking system (ABS) state.
     */
    @Nullable public Boolean isAntiLockBrakingActive() {
        return antiLockBrakingActive;
    }

    /**
     * @return The engine coolant temperature in Celsius, whereas can be negative.
     */
    @Nullable public Integer getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }

    /**
     * @return The the accumulated time of engine operation.
     */
    @Nullable public Float getEngineTotalOperatingHours() {
        return engineTotalOperatingHours;
    }

    /**
     * @return The the accumulated lifespan fuel consumption in liters.
     */
    @Nullable public Float getEngineTotalFuelConsumption() {
        return engineTotalFuelConsumption;
    }

    /**
     * @return The brake fluid level.
     */
    @Nullable public BrakeFluidLevel getBrakeFluidLevel() {
        return brakeFluidLevel;
    }

    /**
     * @return The current engine torque percentage between 0-1.
     */
    @Nullable public Float getEngineTorque() {
        return engineTorque;
    }

    /**
     * @return The current engine load percentage between 0-1.
     */
    @Nullable public Float getEngineLoad() {
        return engineLoad;
    }

    /**
     * @return The vehicle speed in km/h measured at the wheel base, whereas can be negative.
     */
    @Nullable public Integer getWheelBasedSpeed() {
        return wheelBasedSpeed;
    }

    /**
     * @return The battery level percentage.
     */
    @Nullable public Float getBatteryLevel() {
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
            if (pressure.getTireLocation() == tireLocation) return pressure;
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
            if (temperature.getTireLocation() == tireLocation) return temperature;
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
            if (wheelRpm.getTireLocation() == tireLocation) return wheelRpm;
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
    public Integer getMileageMeters() {
        return mileageMeters;
    }

    public DiagnosticsState(byte[] bytes) {
        super(bytes);

        ArrayList<CheckControlMessage> checkControlMessages = new ArrayList<>();
        ArrayList<TirePressure> tirePressures = new ArrayList<>();
        ArrayList<TireTemperature> tireTemperatures = new ArrayList<>();
        ArrayList<WheelRpm> wheelRpms = new ArrayList<>();
        ArrayList<DiagnosticsTroubleCode> troubleCodes = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case MILEAGE_IDENTIFIER:
                        mileage = Property.getUnsignedInt(p.getValueBytes());
                        return mileage;
                    case OIL_TEMPERATURE_IDENTIFIER:
                        oilTemperature = Property.getUnsignedInt(p.getValueBytes());
                        return oilTemperature;
                    case SPEED_IDENTIFIER:
                        speed = Property.getUnsignedInt(p.getValueBytes());
                        return speed;
                    case RPM_IDENTIFIER:
                        rpm = Property.getUnsignedInt(p.getValueBytes());
                        return rpm;
                    case FUEL_LEVEL_IDENTIFIER:
                        fuelLevel = Property.getUnsignedInt(p.getValueBytes()) / 100f;
                        return fuelLevel;
                    case RANGE_IDENTIFIER:
                        range = Property.getUnsignedInt(p.getValueBytes());
                        return range;
                    case WASHER_FLUID_LEVEL_IDENTIFIER:
                        washerFluidLevel = WasherFluidLevel.fromByte(p.getValueByte());
                        return washerFluidLevel;
                    case BATTERY_VOLTAGE_IDENTIFIER:
                        batteryVoltage = Property.getFloat(p.getValueBytes());
                        return batteryVoltage;
                    case AD_BLUE_LEVEL_IDENTIFIER:
                        adBlueLevel = Property.getFloat(p.getValueBytes());
                        return adBlueLevel;
                    case DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER:
                        distanceDrivenSinceReset = Property.getUnsignedInt(p.getValueBytes());
                        return distanceDrivenSinceReset;
                    case DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER:
                        distanceDrivenSinceEngineStart = Property.getUnsignedInt(p.getValueBytes());
                        return distanceDrivenSinceEngineStart;
                    case FUEL_VOLUME_IDENTIFIER:
                        fuelVolume = Property.getFloat(p.getValueBytes());
                        return fuelVolume;
                    case ANTI_LOCK_BRAKING_ACTIVE_IDENTIFIER:
                        antiLockBrakingActive = Property.getBool(p.getValueByte());
                        return antiLockBrakingActive;
                    case ENGINE_COOLANT_TEMPERATURE_IDENTIFIER:
                        engineCoolantTemperature = Property.getUnsignedInt(p.getValueBytes());
                        return engineCoolantTemperature;
                    case ENGINE_TOTAL_OPERATING_HOURS_IDENTIFIER:
                        engineTotalOperatingHours = Property.getFloat(p.getValueBytes());
                        return engineTotalOperatingHours;
                    case ENGINE_TOTAL_FUEL_CONSUMPTION_IDENTIFIER:
                        engineTotalFuelConsumption = Property.getFloat(p.getValueBytes());
                        return engineTotalFuelConsumption;
                    case IDENTIFIER_BRAKE_FLUID:
                        brakeFluidLevel = BrakeFluidLevel.fromByte(p.getValueByte());
                        return brakeFluidLevel;
                    case ENGINE_TORQUE_IDENTIFIER:
                        engineTorque = Property.getPercentage(p.getValueByte());
                        return engineTorque;
                    case ENGINE_LOAD_IDENTIFIER:
                        engineLoad = Property.getPercentage(p.getValueByte());
                        return engineLoad;
                    case WHEEL_BASE_SPEED_IDENTIFIER:
                        wheelBasedSpeed = Property.getSignedInt(p.getValueBytes());
                        return wheelBasedSpeed;
                    case IDENTIFIER_BATTERY_LEVEL:
                        batteryLevel = Property.getPercentage(p.getValueByte());
                        return batteryLevel;
                    case IDENTIFIER_CHECK_CONTROL_MESSAGES:
                        CheckControlMessage message = new CheckControlMessage(p.getPropertyBytes());
                        checkControlMessages.add(message);
                        return message;
                    case IDENTIFIER_TIRE_PRESSURES:
                        TirePressure pressure = new TirePressure(p.getPropertyBytes());
                        tirePressures.add(pressure);
                        return pressure;
                    case IDENTIFIER_TIRE_TEMPERATURES:
                        TireTemperature temp = new TireTemperature(p.getPropertyBytes());
                        tireTemperatures.add(temp);
                        return temp;
                    case IDENTIFIER_WHEEL_RPM:
                        WheelRpm rpm = new WheelRpm(p.getPropertyBytes());
                        wheelRpms.add(rpm);
                        return rpm;
                    case IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE:
                        DiagnosticsTroubleCode code =
                                new DiagnosticsTroubleCode(p.getPropertyBytes());
                        troubleCodes.add(code);
                        return code;
                    case MILEAGE_METERS_IDENTIFIER:
                        mileageMeters = Property.getUnsignedInt(p.getValueBytes());
                        return mileageMeters;
                }

                return null;
            });

            this.checkControlMessages =
                    checkControlMessages.toArray(new CheckControlMessage[0]);
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
        private Integer mileage;
        private Integer oilTemperature;
        private Integer speed;
        private Integer rpm;
        private Float fuelLevel;
        private Integer range;
        private WasherFluidLevel washerFluidLevel;
        private Float batteryVoltage;
        private Float adBlueLevel;
        private Integer distanceDrivenSinceReset;
        private Integer distanceDrivenSinceEngineStart;
        private Float fuelVolume;

        Boolean antiLockBrakingActive;
        Integer engineCoolantTemperature;
        Float engineTotalOperatingHours;
        Float engineTotalFuelConsumption;
        BrakeFluidLevel brakeFluidLevel;
        Float engineTorque;
        Float engineLoad;
        Integer wheelBasedSpeed;

        Float batteryLevel;
        private List<TirePressure> tirePressures = new ArrayList<>();
        private List<TireTemperature> tireTemperatures = new ArrayList<>();
        private List<WheelRpm> wheelRpms = new ArrayList<>();
        private List<CheckControlMessage> checkControlMessages = new ArrayList<>();
        private List<DiagnosticsTroubleCode> troubleCodes = new ArrayList<>();
        Integer mileageMeters;

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
        public Builder setMileage(Integer mileage) {
            this.mileage = mileage;
            addProperty(new IntegerProperty(MILEAGE_IDENTIFIER, mileage, 3));
            return this;
        }

        /**
         * @param oilTemperature The oil temperature in Celsius.
         * @return The builder.
         */
        public Builder setOilTemperature(Integer oilTemperature) {
            this.oilTemperature = oilTemperature;
            addProperty(new IntegerProperty(OIL_TEMPERATURE_IDENTIFIER, oilTemperature, 2));
            return this;
        }

        /**
         * @param speed The speed in km/h.
         * @return The builder.
         */
        public Builder setSpeed(Integer speed) {
            this.speed = speed;
            addProperty(new IntegerProperty(SPEED_IDENTIFIER, speed, 2));
            return this;
        }

        /**
         * @param rpm The RPM of the engine.
         * @return The builder.
         */
        public Builder setRpm(Integer rpm) {
            this.rpm = rpm;
            addProperty(new IntegerProperty(RPM_IDENTIFIER, rpm, 2));
            return this;
        }

        /**
         * @param fuelLevel The fuel level percentage between 0 and 1.
         * @return The builder.
         */
        public Builder setFuelLevel(Float fuelLevel) {
            this.fuelLevel = fuelLevel;
            addProperty(new PercentageProperty(FUEL_LEVEL_IDENTIFIER, fuelLevel));
            return this;
        }

        /**
         * @param range The estimated range.
         * @return The builder.
         */
        public Builder setRange(Integer range) {
            this.range = range;
            addProperty(new IntegerProperty(RANGE_IDENTIFIER, range, 2));
            return this;
        }

        /**
         * @param washerFluidLevel The washer fluid level.
         * @return The builder.
         */
        public Builder setWasherFluidLevel(WasherFluidLevel washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel;
            addProperty(new Property(WASHER_FLUID_LEVEL_IDENTIFIER,
                    washerFluidLevel.getByte()));
            return this;
        }

        /**
         * @param batteryVoltage The battery voltage.
         * @return The builder.
         */
        public Builder setBatteryVoltage(Float batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
            addProperty(new FloatProperty(BATTERY_VOLTAGE_IDENTIFIER, batteryVoltage));
            return this;
        }

        /**
         * @param adBlueLevel The adBlue level in liters.
         * @return The builder.
         */
        public Builder setAdBlueLevel(Float adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
            addProperty(new FloatProperty(AD_BLUE_LEVEL_IDENTIFIER, adBlueLevel));
            return this;
        }

        /**
         * @param distanceDrivenSinceReset The distance driven in km since reset.
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceReset(Integer distanceDrivenSinceReset) {
            this.distanceDrivenSinceReset = distanceDrivenSinceReset;
            addProperty(new IntegerProperty(DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER,
                    distanceDrivenSinceReset, 2));
            return this;
        }

        /**
         * @param distanceDrivenSinceEngineStart The distance driven in km since engine start
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceEngineStart(Integer distanceDrivenSinceEngineStart) {
            this.distanceDrivenSinceEngineStart = distanceDrivenSinceEngineStart;
            addProperty(new IntegerProperty(DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER,
                    distanceDrivenSinceEngineStart, 2));
            return this;
        }

        /**
         * @param fuelVolume The fuel volume measured in liters.
         * @return The builder.
         */
        public Builder setFuelVolume(Float fuelVolume) {
            this.fuelVolume = fuelVolume;
            addProperty(new FloatProperty(FUEL_VOLUME_IDENTIFIER, fuelVolume));
            return this;
        }

        /**
         * @param antiLockBrakingActive The anti-lock braking system (ABS) state.
         * @return The builder.
         */
        public Builder setAntiLockBrakingActive(Boolean antiLockBrakingActive) {
            this.antiLockBrakingActive = antiLockBrakingActive;
            addProperty(new BooleanProperty(ANTI_LOCK_BRAKING_ACTIVE_IDENTIFIER,
                    antiLockBrakingActive));
            return this;
        }

        /**
         * @param engineCoolantTemperature The engine coolant temperature in Celsius, whereas can be
         *                                 negative.
         * @return The builder.
         */
        public Builder setEngineCoolantTemperature(Integer engineCoolantTemperature) {
            this.engineCoolantTemperature = engineCoolantTemperature;
            addProperty(new IntegerProperty(ENGINE_COOLANT_TEMPERATURE_IDENTIFIER,
                    engineCoolantTemperature, 2));
            return this;
        }

        /**
         * @param engineTotalOperatingHours The the accumulated time of engine operation.
         * @return The builder.
         */
        public Builder setEngineTotalOperatingHours(Float engineTotalOperatingHours) {
            this.engineTotalOperatingHours = engineTotalOperatingHours;
            addProperty(new FloatProperty(ENGINE_TOTAL_OPERATING_HOURS_IDENTIFIER,
                    engineTotalOperatingHours));
            return this;
        }

        /**
         * @param engineTotalFuelConsumption The the accumulated lifespan fuel consumption in
         *                                   liters.
         * @return The builder.
         */
        public Builder setEngineTotalFuelConsumption(Float engineTotalFuelConsumption) {
            this.engineTotalFuelConsumption = engineTotalFuelConsumption;
            addProperty(new FloatProperty(ENGINE_TOTAL_FUEL_CONSUMPTION_IDENTIFIER,
                    engineTotalFuelConsumption));
            return this;
        }

        /**
         * @param brakeFluidLevel The brake fluid level.
         * @return The builder.
         */
        public Builder setBrakeFluidLevel(BrakeFluidLevel brakeFluidLevel) {
            this.brakeFluidLevel = brakeFluidLevel;
            addProperty(new Property(IDENTIFIER_BRAKE_FLUID, brakeFluidLevel.getByte()));
            return this;
        }

        /**
         * @param engineTorque The current engine torque percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineTorque(Float engineTorque) {
            this.engineTorque = engineTorque;
            addProperty(new PercentageProperty(ENGINE_TORQUE_IDENTIFIER, engineTorque));
            return this;
        }

        /**
         * @param engineLoad The current engine load percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineLoad(Float engineLoad) {
            this.engineLoad = engineLoad;
            addProperty(new PercentageProperty(ENGINE_LOAD_IDENTIFIER, engineLoad));
            return this;
        }

        /**
         * @param wheelBasedSpeed The vehicle speed in km/h measured at the wheel base, whereas can
         *                        be negative.
         * @return The builder.
         */
        public Builder setWheelBasedSpeed(Integer wheelBasedSpeed) {
            this.wheelBasedSpeed = wheelBasedSpeed;
            addProperty(new IntegerProperty(WHEEL_BASE_SPEED_IDENTIFIER, wheelBasedSpeed,
                    2));
            return this;
        }

        /**
         * <<<<<<< HEAD Set the battery level.
         *
         * @param batteryLevel The battery level.
         */
        public void setBatteryLevel(Float batteryLevel) {
            this.batteryLevel = batteryLevel;
            addProperty(new PercentageProperty(IDENTIFIER_BATTERY_LEVEL, batteryLevel));
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
        public Builder setMileageMeters(Integer mileageMeters) {
            this.mileageMeters = mileageMeters;
            addProperty(new IntegerProperty(MILEAGE_METERS_IDENTIFIER, mileageMeters, 4));
            return this;
        }
    }
}