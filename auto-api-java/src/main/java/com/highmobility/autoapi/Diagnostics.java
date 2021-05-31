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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.CheckControlMessage;
import com.highmobility.autoapi.value.ConfirmedTroubleCode;
import com.highmobility.autoapi.value.DieselExhaustFilterStatus;
import com.highmobility.autoapi.value.FluidLevel;
import com.highmobility.autoapi.value.OemTroubleCodeValue;
import com.highmobility.autoapi.value.TirePressure;
import com.highmobility.autoapi.value.TirePressureStatus;
import com.highmobility.autoapi.value.TireTemperature;
import com.highmobility.autoapi.value.TroubleCode;
import com.highmobility.autoapi.value.WheelRpm;
import com.highmobility.autoapi.value.measurement.AngularVelocity;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.ElectricPotentialDifference;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.Speed;
import com.highmobility.autoapi.value.measurement.Temperature;
import com.highmobility.autoapi.value.measurement.Volume;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

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
    public static final byte PROPERTY_ODOMETER = 0x1f;
    public static final byte PROPERTY_ENGINE_TOTAL_OPERATING_TIME = 0x20;
    public static final byte PROPERTY_TIRE_PRESSURE_STATUSES = 0x21;
    public static final byte PROPERTY_BRAKE_LINING_WEAR_PRE_WARNING = 0x22;
    public static final byte PROPERTY_ENGINE_OIL_LIFE_REMAINING = 0x23;
    public static final byte PROPERTY_OEM_TROUBLE_CODE_VALUES = 0x24;
    public static final byte PROPERTY_DIESEL_EXHAUST_FLUID_RANGE = 0x25;
    public static final byte PROPERTY_DIESEL_PARTICULATE_FILTER_SOOT_LEVEL = 0x26;
    public static final byte PROPERTY_CONFIRMED_TROUBLE_CODES = 0x27;
    public static final byte PROPERTY_DIESEL_EXHAUST_FILTER_STATUS = 0x28;
    public static final byte PROPERTY_ENGINE_TOTAL_IDLE_OPERATING_TIME = 0x2a;
    public static final byte PROPERTY_ENGINE_OIL_AMOUNT = 0x2b;
    public static final byte PROPERTY_ENGINE_OIL_LEVEL = 0x2c;
    public static final byte PROPERTY_ESTIMATED_SECONDARY_POWERTRAIN_RANGE = 0x2d;
    public static final byte PROPERTY_FUEL_LEVEL_ACCURACY = 0x2e;
    public static final byte PROPERTY_TIRE_PRESSURES_TARGETS = 0x2f;
    public static final byte PROPERTY_TIRE_PRESSURES_DIFFERENCES = 0x30;
    public static final byte PROPERTY_BACKUP_BATTERY_REMAINING_TIME = 0x31;
    public static final byte PROPERTY_ENGINE_COOLANT_FLUID_LEVEL = 0x32;
    public static final byte PROPERTY_ENGINE_OIL_FLUID_LEVEL = 0x33;
    public static final byte PROPERTY_ENGINE_OIL_PRESSURE_LEVEL = 0x34;
    public static final byte PROPERTY_ENGINE_TIME_TO_NEXT_SERVICE = 0x35;
    public static final byte PROPERTY_LOW_VOLTAGE_BATTERY_CHARGE_LEVEL = 0x36;

    /**
     * Get Diagnostics property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Diagnostics property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Diagnostics property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Diagnostics property availabilities
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
     * Get Diagnostics properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Diagnostics properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Diagnostics properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Diagnostics properties
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
     * Get specific Diagnostics properties
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
     * The diagnostics state
     */
    public static class State extends SetCommand {
        Property<Length> mileage = new Property<>(Length.class, PROPERTY_MILEAGE);
        Property<Temperature> engineOilTemperature = new Property<>(Temperature.class, PROPERTY_ENGINE_OIL_TEMPERATURE);
        Property<Speed> speed = new Property<>(Speed.class, PROPERTY_SPEED);
        Property<AngularVelocity> engineRPM = new Property<>(AngularVelocity.class, PROPERTY_ENGINE_RPM);
        Property<Double> fuelLevel = new Property<>(Double.class, PROPERTY_FUEL_LEVEL);
        Property<Length> estimatedRange = new Property<>(Length.class, PROPERTY_ESTIMATED_RANGE);
        Property<FluidLevel> washerFluidLevel = new Property<>(FluidLevel.class, PROPERTY_WASHER_FLUID_LEVEL);
        Property<ElectricPotentialDifference> batteryVoltage = new Property<>(ElectricPotentialDifference.class, PROPERTY_BATTERY_VOLTAGE);
        Property<Double> adBlueLevel = new Property<>(Double.class, PROPERTY_ADBLUE_LEVEL);
        Property<Length> distanceSinceReset = new Property<>(Length.class, PROPERTY_DISTANCE_SINCE_RESET);
        Property<Length> distanceSinceStart = new Property<>(Length.class, PROPERTY_DISTANCE_SINCE_START);
        Property<Volume> fuelVolume = new Property<>(Volume.class, PROPERTY_FUEL_VOLUME);
        Property<ActiveState> antiLockBraking = new Property<>(ActiveState.class, PROPERTY_ANTI_LOCK_BRAKING);
        Property<Temperature> engineCoolantTemperature = new Property<>(Temperature.class, PROPERTY_ENGINE_COOLANT_TEMPERATURE);
        Property<Duration> engineTotalOperatingHours = new Property<>(Duration.class, PROPERTY_ENGINE_TOTAL_OPERATING_HOURS);
        Property<Volume> engineTotalFuelConsumption = new Property<>(Volume.class, PROPERTY_ENGINE_TOTAL_FUEL_CONSUMPTION);
        Property<FluidLevel> brakeFluidLevel = new Property<>(FluidLevel.class, PROPERTY_BRAKE_FLUID_LEVEL);
        Property<Double> engineTorque = new Property<>(Double.class, PROPERTY_ENGINE_TORQUE);
        Property<Double> engineLoad = new Property<>(Double.class, PROPERTY_ENGINE_LOAD);
        Property<Speed> wheelBasedSpeed = new Property<>(Speed.class, PROPERTY_WHEEL_BASED_SPEED);
        Property<Double> batteryLevel = new Property<>(Double.class, PROPERTY_BATTERY_LEVEL);
        List<Property<CheckControlMessage>> checkControlMessages;
        List<Property<TirePressure>> tirePressures;
        List<Property<TireTemperature>> tireTemperatures;
        List<Property<WheelRpm>> wheelRPMs;
        List<Property<TroubleCode>> troubleCodes;
        Property<Length> mileageMeters = new Property<>(Length.class, PROPERTY_MILEAGE_METERS);
        Property<Length> odometer = new Property<>(Length.class, PROPERTY_ODOMETER);
        Property<Duration> engineTotalOperatingTime = new Property<>(Duration.class, PROPERTY_ENGINE_TOTAL_OPERATING_TIME);
        List<Property<TirePressureStatus>> tirePressureStatuses;
        Property<ActiveState> brakeLiningWearPreWarning = new Property<>(ActiveState.class, PROPERTY_BRAKE_LINING_WEAR_PRE_WARNING);
        Property<Double> engineOilLifeRemaining = new Property<>(Double.class, PROPERTY_ENGINE_OIL_LIFE_REMAINING);
        List<Property<OemTroubleCodeValue>> oemTroubleCodeValues;
        Property<Length> dieselExhaustFluidRange = new Property<>(Length.class, PROPERTY_DIESEL_EXHAUST_FLUID_RANGE);
        Property<Double> dieselParticulateFilterSootLevel = new Property<>(Double.class, PROPERTY_DIESEL_PARTICULATE_FILTER_SOOT_LEVEL);
        List<Property<ConfirmedTroubleCode>> confirmedTroubleCodes;
        List<Property<DieselExhaustFilterStatus>> dieselExhaustFilterStatus;
        Property<Duration> engineTotalIdleOperatingTime = new Property<>(Duration.class, PROPERTY_ENGINE_TOTAL_IDLE_OPERATING_TIME);
        Property<Volume> engineOilAmount = new Property<>(Volume.class, PROPERTY_ENGINE_OIL_AMOUNT);
        Property<Double> engineOilLevel = new Property<>(Double.class, PROPERTY_ENGINE_OIL_LEVEL);
        Property<Length> estimatedSecondaryPowertrainRange = new Property<>(Length.class, PROPERTY_ESTIMATED_SECONDARY_POWERTRAIN_RANGE);
        Property<FuelLevelAccuracy> fuelLevelAccuracy = new Property<>(FuelLevelAccuracy.class, PROPERTY_FUEL_LEVEL_ACCURACY);
        List<Property<TirePressure>> tirePressuresTargets;
        List<Property<TirePressure>> tirePressuresDifferences;
        Property<Duration> backupBatteryRemainingTime = new Property<>(Duration.class, PROPERTY_BACKUP_BATTERY_REMAINING_TIME);
        Property<FluidLevel> engineCoolantFluidLevel = new Property<>(FluidLevel.class, PROPERTY_ENGINE_COOLANT_FLUID_LEVEL);
        Property<FluidLevel> engineOilFluidLevel = new Property<>(FluidLevel.class, PROPERTY_ENGINE_OIL_FLUID_LEVEL);
        Property<EngineOilPressureLevel> engineOilPressureLevel = new Property<>(EngineOilPressureLevel.class, PROPERTY_ENGINE_OIL_PRESSURE_LEVEL);
        Property<Duration> engineTimeToNextService = new Property<>(Duration.class, PROPERTY_ENGINE_TIME_TO_NEXT_SERVICE);
        Property<LowVoltageBatteryChargeLevel> lowVoltageBatteryChargeLevel = new Property<>(LowVoltageBatteryChargeLevel.class, PROPERTY_LOW_VOLTAGE_BATTERY_CHARGE_LEVEL);
    
        /**
         * @return The vehicle mileage (odometer)
         * @deprecated 'mileage' is an incorrect term for this. Replaced by {@link #getOdometer()}
         */
        @Deprecated
        public Property<Length> getMileage() {
            return mileage;
        }
    
        /**
         * @return Engine oil temperature
         */
        public Property<Temperature> getEngineOilTemperature() {
            return engineOilTemperature;
        }
    
        /**
         * @return The vehicle speed
         */
        public Property<Speed> getSpeed() {
            return speed;
        }
    
        /**
         * @return Engine RPM (revolutions per minute)
         */
        public Property<AngularVelocity> getEngineRPM() {
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
        public Property<Length> getEstimatedRange() {
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
        public Property<ElectricPotentialDifference> getBatteryVoltage() {
            return batteryVoltage;
        }
    
        /**
         * @return AdBlue level percentage between 0.0-1.0
         */
        public Property<Double> getAdBlueLevel() {
            return adBlueLevel;
        }
    
        /**
         * @return The distance driven since reset
         */
        public Property<Length> getDistanceSinceReset() {
            return distanceSinceReset;
        }
    
        /**
         * @return The distance driven since trip start
         */
        public Property<Length> getDistanceSinceStart() {
            return distanceSinceStart;
        }
    
        /**
         * @return The fuel volume measured in liters
         */
        public Property<Volume> getFuelVolume() {
            return fuelVolume;
        }
    
        /**
         * @return The anti lock braking
         */
        public Property<ActiveState> getAntiLockBraking() {
            return antiLockBraking;
        }
    
        /**
         * @return Engine coolant temperature
         */
        public Property<Temperature> getEngineCoolantTemperature() {
            return engineCoolantTemperature;
        }
    
        /**
         * @return The accumulated time of engine operation
         * @deprecated removed the unit from the name. Replaced by {@link #getEngineTotalOperatingTime()}
         */
        @Deprecated
        public Property<Duration> getEngineTotalOperatingHours() {
            return engineTotalOperatingHours;
        }
    
        /**
         * @return The accumulated lifespan fuel consumption
         */
        public Property<Volume> getEngineTotalFuelConsumption() {
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
         * @return The vehicle speed measured at the wheel base
         */
        public Property<Speed> getWheelBasedSpeed() {
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
        public List<Property<CheckControlMessage>> getCheckControlMessages() {
            return checkControlMessages;
        }
    
        /**
         * @return The tire pressures
         */
        public List<Property<TirePressure>> getTirePressures() {
            return tirePressures;
        }
    
        /**
         * @return The tire temperatures
         */
        public List<Property<TireTemperature>> getTireTemperatures() {
            return tireTemperatures;
        }
    
        /**
         * @return The wheel rpms
         */
        public List<Property<WheelRpm>> getWheelRPMs() {
            return wheelRPMs;
        }
    
        /**
         * @return The trouble codes
         */
        public List<Property<TroubleCode>> getTroubleCodes() {
            return troubleCodes;
        }
    
        /**
         * @return The vehicle mileage (odometer) in meters
         * @deprecated 'mileage' is an incorrect term for this. Replaced by {@link #getOdometer()}
         */
        @Deprecated
        public Property<Length> getMileageMeters() {
            return mileageMeters;
        }
    
        /**
         * @return The vehicle odometer value in a given units
         */
        public Property<Length> getOdometer() {
            return odometer;
        }
    
        /**
         * @return The accumulated time of engine operation
         */
        public Property<Duration> getEngineTotalOperatingTime() {
            return engineTotalOperatingTime;
        }
    
        /**
         * @return The tire pressure statuses
         */
        public List<Property<TirePressureStatus>> getTirePressureStatuses() {
            return tirePressureStatuses;
        }
    
        /**
         * @return Status of brake lining wear pre-warning
         */
        public Property<ActiveState> getBrakeLiningWearPreWarning() {
            return brakeLiningWearPreWarning;
        }
    
        /**
         * @return Remaining life of engine oil which decreases over time
         */
        public Property<Double> getEngineOilLifeRemaining() {
            return engineOilLifeRemaining;
        }
    
        /**
         * @return Additional OEM trouble codes
         */
        public List<Property<OemTroubleCodeValue>> getOemTroubleCodeValues() {
            return oemTroubleCodeValues;
        }
    
        /**
         * @return Distance remaining until diesel exhaust fluid is empty
         */
        public Property<Length> getDieselExhaustFluidRange() {
            return dieselExhaustFluidRange;
        }
    
        /**
         * @return Level of soot in diesel exhaust particulate filter
         */
        public Property<Double> getDieselParticulateFilterSootLevel() {
            return dieselParticulateFilterSootLevel;
        }
    
        /**
         * @return The confirmed trouble codes
         */
        public List<Property<ConfirmedTroubleCode>> getConfirmedTroubleCodes() {
            return confirmedTroubleCodes;
        }
    
        /**
         * @return The diesel exhaust filter status
         */
        public List<Property<DieselExhaustFilterStatus>> getDieselExhaustFilterStatus() {
            return dieselExhaustFilterStatus;
        }
    
        /**
         * @return The accumulated time of engine operation
         */
        public Property<Duration> getEngineTotalIdleOperatingTime() {
            return engineTotalIdleOperatingTime;
        }
    
        /**
         * @return The current estimated oil tank liquid fill.
         */
        public Property<Volume> getEngineOilAmount() {
            return engineOilAmount;
        }
    
        /**
         * @return The current estimated oil tank liquid fill in percentage.
         */
        public Property<Double> getEngineOilLevel() {
            return engineOilLevel;
        }
    
        /**
         * @return Estimated secondary powertrain range
         */
        public Property<Length> getEstimatedSecondaryPowertrainRange() {
            return estimatedSecondaryPowertrainRange;
        }
    
        /**
         * @return This value includes the information, if the fuel level has been calculated or measured.
         */
        public Property<FuelLevelAccuracy> getFuelLevelAccuracy() {
            return fuelLevelAccuracy;
        }
    
        /**
         * @return Target tire pressures for the vehicle.
         */
        public List<Property<TirePressure>> getTirePressuresTargets() {
            return tirePressuresTargets;
        }
    
        /**
         * @return Tire pressures difference from the target pressure.
         */
        public List<Property<TirePressure>> getTirePressuresDifferences() {
            return tirePressuresDifferences;
        }
    
        /**
         * @return Remaining time the backup battery can work.
         */
        public Property<Duration> getBackupBatteryRemainingTime() {
            return backupBatteryRemainingTime;
        }
    
        /**
         * @return Engine coolant fluid level
         */
        public Property<FluidLevel> getEngineCoolantFluidLevel() {
            return engineCoolantFluidLevel;
        }
    
        /**
         * @return Engine oil fluid level
         */
        public Property<FluidLevel> getEngineOilFluidLevel() {
            return engineOilFluidLevel;
        }
    
        /**
         * @return Engine oil pressure level
         */
        public Property<EngineOilPressureLevel> getEngineOilPressureLevel() {
            return engineOilPressureLevel;
        }
    
        /**
         * @return Engine time until next service of the vehicle
         */
        public Property<Duration> getEngineTimeToNextService() {
            return engineTimeToNextService;
        }
    
        /**
         * @return Indicates if the charge level of the low voltage battery is too low to use other systems
         */
        public Property<LowVoltageBatteryChargeLevel> getLowVoltageBatteryChargeLevel() {
            return lowVoltageBatteryChargeLevel;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
    
            final ArrayList<Property<CheckControlMessage>> checkControlMessagesBuilder = new ArrayList<>();
            final ArrayList<Property<TirePressure>> tirePressuresBuilder = new ArrayList<>();
            final ArrayList<Property<TireTemperature>> tireTemperaturesBuilder = new ArrayList<>();
            final ArrayList<Property<WheelRpm>> wheelRPMsBuilder = new ArrayList<>();
            final ArrayList<Property<TroubleCode>> troubleCodesBuilder = new ArrayList<>();
            final ArrayList<Property<TirePressureStatus>> tirePressureStatusesBuilder = new ArrayList<>();
            final ArrayList<Property<OemTroubleCodeValue>> oemTroubleCodeValuesBuilder = new ArrayList<>();
            final ArrayList<Property<ConfirmedTroubleCode>> confirmedTroubleCodesBuilder = new ArrayList<>();
            final ArrayList<Property<DieselExhaustFilterStatus>> dieselExhaustFilterStatusBuilder = new ArrayList<>();
            final ArrayList<Property<TirePressure>> tirePressuresTargetsBuilder = new ArrayList<>();
            final ArrayList<Property<TirePressure>> tirePressuresDifferencesBuilder = new ArrayList<>();
    
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
                            Property<CheckControlMessage> checkControlMessage = new Property<>(CheckControlMessage.class, p);
                            checkControlMessagesBuilder.add(checkControlMessage);
                            return checkControlMessage;
                        case PROPERTY_TIRE_PRESSURES:
                            Property<TirePressure> tirePressure = new Property<>(TirePressure.class, p);
                            tirePressuresBuilder.add(tirePressure);
                            return tirePressure;
                        case PROPERTY_TIRE_TEMPERATURES:
                            Property<TireTemperature> tireTemperature = new Property<>(TireTemperature.class, p);
                            tireTemperaturesBuilder.add(tireTemperature);
                            return tireTemperature;
                        case PROPERTY_WHEEL_RPMS:
                            Property<WheelRpm> wheelRpm = new Property<>(WheelRpm.class, p);
                            wheelRPMsBuilder.add(wheelRpm);
                            return wheelRpm;
                        case PROPERTY_TROUBLE_CODES:
                            Property<TroubleCode> troubleCode = new Property<>(TroubleCode.class, p);
                            troubleCodesBuilder.add(troubleCode);
                            return troubleCode;
                        case PROPERTY_MILEAGE_METERS: return mileageMeters.update(p);
                        case PROPERTY_ODOMETER: return odometer.update(p);
                        case PROPERTY_ENGINE_TOTAL_OPERATING_TIME: return engineTotalOperatingTime.update(p);
                        case PROPERTY_TIRE_PRESSURE_STATUSES:
                            Property<TirePressureStatus> tirePressureStatus = new Property<>(TirePressureStatus.class, p);
                            tirePressureStatusesBuilder.add(tirePressureStatus);
                            return tirePressureStatus;
                        case PROPERTY_BRAKE_LINING_WEAR_PRE_WARNING: return brakeLiningWearPreWarning.update(p);
                        case PROPERTY_ENGINE_OIL_LIFE_REMAINING: return engineOilLifeRemaining.update(p);
                        case PROPERTY_OEM_TROUBLE_CODE_VALUES:
                            Property<OemTroubleCodeValue> engineTroubleCodeValue = new Property<>(OemTroubleCodeValue.class, p);
                            oemTroubleCodeValuesBuilder.add(engineTroubleCodeValue);
                            return engineTroubleCodeValue;
                        case PROPERTY_DIESEL_EXHAUST_FLUID_RANGE: return dieselExhaustFluidRange.update(p);
                        case PROPERTY_DIESEL_PARTICULATE_FILTER_SOOT_LEVEL: return dieselParticulateFilterSootLevel.update(p);
                        case PROPERTY_CONFIRMED_TROUBLE_CODES:
                            Property<ConfirmedTroubleCode> confirmedTroubleCode = new Property<>(ConfirmedTroubleCode.class, p);
                            confirmedTroubleCodesBuilder.add(confirmedTroubleCode);
                            return confirmedTroubleCode;
                        case PROPERTY_DIESEL_EXHAUST_FILTER_STATUS:
                            Property<DieselExhaustFilterStatus> dieselExhaustFilterStatu = new Property<>(DieselExhaustFilterStatus.class, p);
                            dieselExhaustFilterStatusBuilder.add(dieselExhaustFilterStatu);
                            return dieselExhaustFilterStatu;
                        case PROPERTY_ENGINE_TOTAL_IDLE_OPERATING_TIME: return engineTotalIdleOperatingTime.update(p);
                        case PROPERTY_ENGINE_OIL_AMOUNT: return engineOilAmount.update(p);
                        case PROPERTY_ENGINE_OIL_LEVEL: return engineOilLevel.update(p);
                        case PROPERTY_ESTIMATED_SECONDARY_POWERTRAIN_RANGE: return estimatedSecondaryPowertrainRange.update(p);
                        case PROPERTY_FUEL_LEVEL_ACCURACY: return fuelLevelAccuracy.update(p);
                        case PROPERTY_TIRE_PRESSURES_TARGETS:
                            Property<TirePressure> tirePressureTarget = new Property<>(TirePressure.class, p);
                            tirePressuresTargetsBuilder.add(tirePressureTarget);
                            return tirePressureTarget;
                        case PROPERTY_TIRE_PRESSURES_DIFFERENCES:
                            Property<TirePressure> tirePressureDifference = new Property<>(TirePressure.class, p);
                            tirePressuresDifferencesBuilder.add(tirePressureDifference);
                            return tirePressureDifference;
                        case PROPERTY_BACKUP_BATTERY_REMAINING_TIME: return backupBatteryRemainingTime.update(p);
                        case PROPERTY_ENGINE_COOLANT_FLUID_LEVEL: return engineCoolantFluidLevel.update(p);
                        case PROPERTY_ENGINE_OIL_FLUID_LEVEL: return engineOilFluidLevel.update(p);
                        case PROPERTY_ENGINE_OIL_PRESSURE_LEVEL: return engineOilPressureLevel.update(p);
                        case PROPERTY_ENGINE_TIME_TO_NEXT_SERVICE: return engineTimeToNextService.update(p);
                        case PROPERTY_LOW_VOLTAGE_BATTERY_CHARGE_LEVEL: return lowVoltageBatteryChargeLevel.update(p);
                    }
    
                    return null;
                });
            }
    
            checkControlMessages = checkControlMessagesBuilder;
            tirePressures = tirePressuresBuilder;
            tireTemperatures = tireTemperaturesBuilder;
            wheelRPMs = wheelRPMsBuilder;
            troubleCodes = troubleCodesBuilder;
            tirePressureStatuses = tirePressureStatusesBuilder;
            oemTroubleCodeValues = oemTroubleCodeValuesBuilder;
            confirmedTroubleCodes = confirmedTroubleCodesBuilder;
            dieselExhaustFilterStatus = dieselExhaustFilterStatusBuilder;
            tirePressuresTargets = tirePressuresTargetsBuilder;
            tirePressuresDifferences = tirePressuresDifferencesBuilder;
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
            checkControlMessages = builder.checkControlMessages;
            tirePressures = builder.tirePressures;
            tireTemperatures = builder.tireTemperatures;
            wheelRPMs = builder.wheelRPMs;
            troubleCodes = builder.troubleCodes;
            mileageMeters = builder.mileageMeters;
            odometer = builder.odometer;
            engineTotalOperatingTime = builder.engineTotalOperatingTime;
            tirePressureStatuses = builder.tirePressureStatuses;
            brakeLiningWearPreWarning = builder.brakeLiningWearPreWarning;
            engineOilLifeRemaining = builder.engineOilLifeRemaining;
            oemTroubleCodeValues = builder.oemTroubleCodeValues;
            dieselExhaustFluidRange = builder.dieselExhaustFluidRange;
            dieselParticulateFilterSootLevel = builder.dieselParticulateFilterSootLevel;
            confirmedTroubleCodes = builder.confirmedTroubleCodes;
            dieselExhaustFilterStatus = builder.dieselExhaustFilterStatus;
            engineTotalIdleOperatingTime = builder.engineTotalIdleOperatingTime;
            engineOilAmount = builder.engineOilAmount;
            engineOilLevel = builder.engineOilLevel;
            estimatedSecondaryPowertrainRange = builder.estimatedSecondaryPowertrainRange;
            fuelLevelAccuracy = builder.fuelLevelAccuracy;
            tirePressuresTargets = builder.tirePressuresTargets;
            tirePressuresDifferences = builder.tirePressuresDifferences;
            backupBatteryRemainingTime = builder.backupBatteryRemainingTime;
            engineCoolantFluidLevel = builder.engineCoolantFluidLevel;
            engineOilFluidLevel = builder.engineOilFluidLevel;
            engineOilPressureLevel = builder.engineOilPressureLevel;
            engineTimeToNextService = builder.engineTimeToNextService;
            lowVoltageBatteryChargeLevel = builder.lowVoltageBatteryChargeLevel;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            private Property<Length> mileage;
            private Property<Temperature> engineOilTemperature;
            private Property<Speed> speed;
            private Property<AngularVelocity> engineRPM;
            private Property<Double> fuelLevel;
            private Property<Length> estimatedRange;
            private Property<FluidLevel> washerFluidLevel;
            private Property<ElectricPotentialDifference> batteryVoltage;
            private Property<Double> adBlueLevel;
            private Property<Length> distanceSinceReset;
            private Property<Length> distanceSinceStart;
            private Property<Volume> fuelVolume;
            private Property<ActiveState> antiLockBraking;
            private Property<Temperature> engineCoolantTemperature;
            private Property<Duration> engineTotalOperatingHours;
            private Property<Volume> engineTotalFuelConsumption;
            private Property<FluidLevel> brakeFluidLevel;
            private Property<Double> engineTorque;
            private Property<Double> engineLoad;
            private Property<Speed> wheelBasedSpeed;
            private Property<Double> batteryLevel;
            private final List<Property<CheckControlMessage>> checkControlMessages = new ArrayList<>();
            private final List<Property<TirePressure>> tirePressures = new ArrayList<>();
            private final List<Property<TireTemperature>> tireTemperatures = new ArrayList<>();
            private final List<Property<WheelRpm>> wheelRPMs = new ArrayList<>();
            private final List<Property<TroubleCode>> troubleCodes = new ArrayList<>();
            private Property<Length> mileageMeters;
            private Property<Length> odometer;
            private Property<Duration> engineTotalOperatingTime;
            private final List<Property<TirePressureStatus>> tirePressureStatuses = new ArrayList<>();
            private Property<ActiveState> brakeLiningWearPreWarning;
            private Property<Double> engineOilLifeRemaining;
            private final List<Property<OemTroubleCodeValue>> oemTroubleCodeValues = new ArrayList<>();
            private Property<Length> dieselExhaustFluidRange;
            private Property<Double> dieselParticulateFilterSootLevel;
            private final List<Property<ConfirmedTroubleCode>> confirmedTroubleCodes = new ArrayList<>();
            private final List<Property<DieselExhaustFilterStatus>> dieselExhaustFilterStatus = new ArrayList<>();
            private Property<Duration> engineTotalIdleOperatingTime;
            private Property<Volume> engineOilAmount;
            private Property<Double> engineOilLevel;
            private Property<Length> estimatedSecondaryPowertrainRange;
            private Property<FuelLevelAccuracy> fuelLevelAccuracy;
            private final List<Property<TirePressure>> tirePressuresTargets = new ArrayList<>();
            private final List<Property<TirePressure>> tirePressuresDifferences = new ArrayList<>();
            private Property<Duration> backupBatteryRemainingTime;
            private Property<FluidLevel> engineCoolantFluidLevel;
            private Property<FluidLevel> engineOilFluidLevel;
            private Property<EngineOilPressureLevel> engineOilPressureLevel;
            private Property<Duration> engineTimeToNextService;
            private Property<LowVoltageBatteryChargeLevel> lowVoltageBatteryChargeLevel;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param mileage The vehicle mileage (odometer)
             * @return The builder
             * @deprecated 'mileage' is an incorrect term for this. Replaced by {@link #getOdometer()}
             */
            @Deprecated
            public Builder setMileage(Property<Length> mileage) {
                this.mileage = mileage.setIdentifier(PROPERTY_MILEAGE);
                addProperty(this.mileage);
                return this;
            }
            
            /**
             * @param engineOilTemperature Engine oil temperature
             * @return The builder
             */
            public Builder setEngineOilTemperature(Property<Temperature> engineOilTemperature) {
                this.engineOilTemperature = engineOilTemperature.setIdentifier(PROPERTY_ENGINE_OIL_TEMPERATURE);
                addProperty(this.engineOilTemperature);
                return this;
            }
            
            /**
             * @param speed The vehicle speed
             * @return The builder
             */
            public Builder setSpeed(Property<Speed> speed) {
                this.speed = speed.setIdentifier(PROPERTY_SPEED);
                addProperty(this.speed);
                return this;
            }
            
            /**
             * @param engineRPM Engine RPM (revolutions per minute)
             * @return The builder
             */
            public Builder setEngineRPM(Property<AngularVelocity> engineRPM) {
                this.engineRPM = engineRPM.setIdentifier(PROPERTY_ENGINE_RPM);
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
            public Builder setEstimatedRange(Property<Length> estimatedRange) {
                this.estimatedRange = estimatedRange.setIdentifier(PROPERTY_ESTIMATED_RANGE);
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
            public Builder setBatteryVoltage(Property<ElectricPotentialDifference> batteryVoltage) {
                this.batteryVoltage = batteryVoltage.setIdentifier(PROPERTY_BATTERY_VOLTAGE);
                addProperty(this.batteryVoltage);
                return this;
            }
            
            /**
             * @param adBlueLevel AdBlue level percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setAdBlueLevel(Property<Double> adBlueLevel) {
                this.adBlueLevel = adBlueLevel.setIdentifier(PROPERTY_ADBLUE_LEVEL);
                addProperty(this.adBlueLevel);
                return this;
            }
            
            /**
             * @param distanceSinceReset The distance driven since reset
             * @return The builder
             */
            public Builder setDistanceSinceReset(Property<Length> distanceSinceReset) {
                this.distanceSinceReset = distanceSinceReset.setIdentifier(PROPERTY_DISTANCE_SINCE_RESET);
                addProperty(this.distanceSinceReset);
                return this;
            }
            
            /**
             * @param distanceSinceStart The distance driven since trip start
             * @return The builder
             */
            public Builder setDistanceSinceStart(Property<Length> distanceSinceStart) {
                this.distanceSinceStart = distanceSinceStart.setIdentifier(PROPERTY_DISTANCE_SINCE_START);
                addProperty(this.distanceSinceStart);
                return this;
            }
            
            /**
             * @param fuelVolume The fuel volume measured in liters
             * @return The builder
             */
            public Builder setFuelVolume(Property<Volume> fuelVolume) {
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
             * @param engineCoolantTemperature Engine coolant temperature
             * @return The builder
             */
            public Builder setEngineCoolantTemperature(Property<Temperature> engineCoolantTemperature) {
                this.engineCoolantTemperature = engineCoolantTemperature.setIdentifier(PROPERTY_ENGINE_COOLANT_TEMPERATURE);
                addProperty(this.engineCoolantTemperature);
                return this;
            }
            
            /**
             * @param engineTotalOperatingHours The accumulated time of engine operation
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getEngineTotalOperatingTime()}
             */
            @Deprecated
            public Builder setEngineTotalOperatingHours(Property<Duration> engineTotalOperatingHours) {
                this.engineTotalOperatingHours = engineTotalOperatingHours.setIdentifier(PROPERTY_ENGINE_TOTAL_OPERATING_HOURS);
                addProperty(this.engineTotalOperatingHours);
                return this;
            }
            
            /**
             * @param engineTotalFuelConsumption The accumulated lifespan fuel consumption
             * @return The builder
             */
            public Builder setEngineTotalFuelConsumption(Property<Volume> engineTotalFuelConsumption) {
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
             * @param wheelBasedSpeed The vehicle speed measured at the wheel base
             * @return The builder
             */
            public Builder setWheelBasedSpeed(Property<Speed> wheelBasedSpeed) {
                this.wheelBasedSpeed = wheelBasedSpeed.setIdentifier(PROPERTY_WHEEL_BASED_SPEED);
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
             * Add an array of check control messages
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
             * Add a single check control message
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
             * Add an array of tire pressures
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
             * Add a single tire pressure
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
             * Add an array of tire temperatures
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
             * Add a single tire temperature
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
             * Add an array of wheel rpms
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
             * Add a single wheel rpm
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
             * Add an array of trouble codes
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
             * Add a single trouble code
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
             * @param mileageMeters The vehicle mileage (odometer) in meters
             * @return The builder
             * @deprecated 'mileage' is an incorrect term for this. Replaced by {@link #getOdometer()}
             */
            @Deprecated
            public Builder setMileageMeters(Property<Length> mileageMeters) {
                this.mileageMeters = mileageMeters.setIdentifier(PROPERTY_MILEAGE_METERS);
                addProperty(this.mileageMeters);
                return this;
            }
            
            /**
             * @param odometer The vehicle odometer value in a given units
             * @return The builder
             */
            public Builder setOdometer(Property<Length> odometer) {
                this.odometer = odometer.setIdentifier(PROPERTY_ODOMETER);
                addProperty(this.odometer);
                return this;
            }
            
            /**
             * @param engineTotalOperatingTime The accumulated time of engine operation
             * @return The builder
             */
            public Builder setEngineTotalOperatingTime(Property<Duration> engineTotalOperatingTime) {
                this.engineTotalOperatingTime = engineTotalOperatingTime.setIdentifier(PROPERTY_ENGINE_TOTAL_OPERATING_TIME);
                addProperty(this.engineTotalOperatingTime);
                return this;
            }
            
            /**
             * Add an array of tire pressure statuses
             * 
             * @param tirePressureStatuses The tire pressure statuses
             * @return The builder
             */
            public Builder setTirePressureStatuses(Property<TirePressureStatus>[] tirePressureStatuses) {
                this.tirePressureStatuses.clear();
                for (int i = 0; i < tirePressureStatuses.length; i++) {
                    addTirePressureStatus(tirePressureStatuses[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single tire pressure status
             * 
             * @param tirePressureStatus The tire pressure status
             * @return The builder
             */
            public Builder addTirePressureStatus(Property<TirePressureStatus> tirePressureStatus) {
                tirePressureStatus.setIdentifier(PROPERTY_TIRE_PRESSURE_STATUSES);
                addProperty(tirePressureStatus);
                tirePressureStatuses.add(tirePressureStatus);
                return this;
            }
            
            /**
             * @param brakeLiningWearPreWarning Status of brake lining wear pre-warning
             * @return The builder
             */
            public Builder setBrakeLiningWearPreWarning(Property<ActiveState> brakeLiningWearPreWarning) {
                this.brakeLiningWearPreWarning = brakeLiningWearPreWarning.setIdentifier(PROPERTY_BRAKE_LINING_WEAR_PRE_WARNING);
                addProperty(this.brakeLiningWearPreWarning);
                return this;
            }
            
            /**
             * @param engineOilLifeRemaining Remaining life of engine oil which decreases over time
             * @return The builder
             */
            public Builder setEngineOilLifeRemaining(Property<Double> engineOilLifeRemaining) {
                this.engineOilLifeRemaining = engineOilLifeRemaining.setIdentifier(PROPERTY_ENGINE_OIL_LIFE_REMAINING);
                addProperty(this.engineOilLifeRemaining);
                return this;
            }
            
            /**
             * Add an array of oem trouble code values
             * 
             * @param oemTroubleCodeValues The oem trouble code values. Additional OEM trouble codes
             * @return The builder
             */
            public Builder setOemTroubleCodeValues(Property<OemTroubleCodeValue>[] oemTroubleCodeValues) {
                this.oemTroubleCodeValues.clear();
                for (int i = 0; i < oemTroubleCodeValues.length; i++) {
                    addEngineTroubleCodeValue(oemTroubleCodeValues[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single engine trouble code value
             * 
             * @param engineTroubleCodeValue The engine trouble code value. Additional OEM trouble codes
             * @return The builder
             */
            public Builder addEngineTroubleCodeValue(Property<OemTroubleCodeValue> engineTroubleCodeValue) {
                engineTroubleCodeValue.setIdentifier(PROPERTY_OEM_TROUBLE_CODE_VALUES);
                addProperty(engineTroubleCodeValue);
                oemTroubleCodeValues.add(engineTroubleCodeValue);
                return this;
            }
            
            /**
             * @param dieselExhaustFluidRange Distance remaining until diesel exhaust fluid is empty
             * @return The builder
             */
            public Builder setDieselExhaustFluidRange(Property<Length> dieselExhaustFluidRange) {
                this.dieselExhaustFluidRange = dieselExhaustFluidRange.setIdentifier(PROPERTY_DIESEL_EXHAUST_FLUID_RANGE);
                addProperty(this.dieselExhaustFluidRange);
                return this;
            }
            
            /**
             * @param dieselParticulateFilterSootLevel Level of soot in diesel exhaust particulate filter
             * @return The builder
             */
            public Builder setDieselParticulateFilterSootLevel(Property<Double> dieselParticulateFilterSootLevel) {
                this.dieselParticulateFilterSootLevel = dieselParticulateFilterSootLevel.setIdentifier(PROPERTY_DIESEL_PARTICULATE_FILTER_SOOT_LEVEL);
                addProperty(this.dieselParticulateFilterSootLevel);
                return this;
            }
            
            /**
             * Add an array of confirmed trouble codes
             * 
             * @param confirmedTroubleCodes The confirmed trouble codes
             * @return The builder
             */
            public Builder setConfirmedTroubleCodes(Property<ConfirmedTroubleCode>[] confirmedTroubleCodes) {
                this.confirmedTroubleCodes.clear();
                for (int i = 0; i < confirmedTroubleCodes.length; i++) {
                    addConfirmedTroubleCode(confirmedTroubleCodes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single confirmed trouble code
             * 
             * @param confirmedTroubleCode The confirmed trouble code
             * @return The builder
             */
            public Builder addConfirmedTroubleCode(Property<ConfirmedTroubleCode> confirmedTroubleCode) {
                confirmedTroubleCode.setIdentifier(PROPERTY_CONFIRMED_TROUBLE_CODES);
                addProperty(confirmedTroubleCode);
                confirmedTroubleCodes.add(confirmedTroubleCode);
                return this;
            }
            
            /**
             * Add an array of diesel exhaust filter status
             * 
             * @param dieselExhaustFilterStatus The diesel exhaust filter status
             * @return The builder
             */
            public Builder setDieselExhaustFilterStatus(Property<DieselExhaustFilterStatus>[] dieselExhaustFilterStatus) {
                this.dieselExhaustFilterStatus.clear();
                for (int i = 0; i < dieselExhaustFilterStatus.length; i++) {
                    addDieselExhaustFilterStatu(dieselExhaustFilterStatus[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single diesel exhaust filter statu
             * 
             * @param dieselExhaustFilterStatu The diesel exhaust filter statu
             * @return The builder
             */
            public Builder addDieselExhaustFilterStatu(Property<DieselExhaustFilterStatus> dieselExhaustFilterStatu) {
                dieselExhaustFilterStatu.setIdentifier(PROPERTY_DIESEL_EXHAUST_FILTER_STATUS);
                addProperty(dieselExhaustFilterStatu);
                dieselExhaustFilterStatus.add(dieselExhaustFilterStatu);
                return this;
            }
            
            /**
             * @param engineTotalIdleOperatingTime The accumulated time of engine operation
             * @return The builder
             */
            public Builder setEngineTotalIdleOperatingTime(Property<Duration> engineTotalIdleOperatingTime) {
                this.engineTotalIdleOperatingTime = engineTotalIdleOperatingTime.setIdentifier(PROPERTY_ENGINE_TOTAL_IDLE_OPERATING_TIME);
                addProperty(this.engineTotalIdleOperatingTime);
                return this;
            }
            
            /**
             * @param engineOilAmount The current estimated oil tank liquid fill.
             * @return The builder
             */
            public Builder setEngineOilAmount(Property<Volume> engineOilAmount) {
                this.engineOilAmount = engineOilAmount.setIdentifier(PROPERTY_ENGINE_OIL_AMOUNT);
                addProperty(this.engineOilAmount);
                return this;
            }
            
            /**
             * @param engineOilLevel The current estimated oil tank liquid fill in percentage.
             * @return The builder
             */
            public Builder setEngineOilLevel(Property<Double> engineOilLevel) {
                this.engineOilLevel = engineOilLevel.setIdentifier(PROPERTY_ENGINE_OIL_LEVEL);
                addProperty(this.engineOilLevel);
                return this;
            }
            
            /**
             * @param estimatedSecondaryPowertrainRange Estimated secondary powertrain range
             * @return The builder
             */
            public Builder setEstimatedSecondaryPowertrainRange(Property<Length> estimatedSecondaryPowertrainRange) {
                this.estimatedSecondaryPowertrainRange = estimatedSecondaryPowertrainRange.setIdentifier(PROPERTY_ESTIMATED_SECONDARY_POWERTRAIN_RANGE);
                addProperty(this.estimatedSecondaryPowertrainRange);
                return this;
            }
            
            /**
             * @param fuelLevelAccuracy This value includes the information, if the fuel level has been calculated or measured.
             * @return The builder
             */
            public Builder setFuelLevelAccuracy(Property<FuelLevelAccuracy> fuelLevelAccuracy) {
                this.fuelLevelAccuracy = fuelLevelAccuracy.setIdentifier(PROPERTY_FUEL_LEVEL_ACCURACY);
                addProperty(this.fuelLevelAccuracy);
                return this;
            }
            
            /**
             * Add an array of tire pressures targets
             * 
             * @param tirePressuresTargets The tire pressures targets. Target tire pressures for the vehicle.
             * @return The builder
             */
            public Builder setTirePressuresTargets(Property<TirePressure>[] tirePressuresTargets) {
                this.tirePressuresTargets.clear();
                for (int i = 0; i < tirePressuresTargets.length; i++) {
                    addTirePressureTarget(tirePressuresTargets[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single tire pressure target
             * 
             * @param tirePressureTarget The tire pressure target. Target tire pressures for the vehicle.
             * @return The builder
             */
            public Builder addTirePressureTarget(Property<TirePressure> tirePressureTarget) {
                tirePressureTarget.setIdentifier(PROPERTY_TIRE_PRESSURES_TARGETS);
                addProperty(tirePressureTarget);
                tirePressuresTargets.add(tirePressureTarget);
                return this;
            }
            
            /**
             * Add an array of tire pressures differences
             * 
             * @param tirePressuresDifferences The tire pressures differences. Tire pressures difference from the target pressure.
             * @return The builder
             */
            public Builder setTirePressuresDifferences(Property<TirePressure>[] tirePressuresDifferences) {
                this.tirePressuresDifferences.clear();
                for (int i = 0; i < tirePressuresDifferences.length; i++) {
                    addTirePressureDifference(tirePressuresDifferences[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single tire pressure difference
             * 
             * @param tirePressureDifference The tire pressure difference. Tire pressures difference from the target pressure.
             * @return The builder
             */
            public Builder addTirePressureDifference(Property<TirePressure> tirePressureDifference) {
                tirePressureDifference.setIdentifier(PROPERTY_TIRE_PRESSURES_DIFFERENCES);
                addProperty(tirePressureDifference);
                tirePressuresDifferences.add(tirePressureDifference);
                return this;
            }
            
            /**
             * @param backupBatteryRemainingTime Remaining time the backup battery can work.
             * @return The builder
             */
            public Builder setBackupBatteryRemainingTime(Property<Duration> backupBatteryRemainingTime) {
                this.backupBatteryRemainingTime = backupBatteryRemainingTime.setIdentifier(PROPERTY_BACKUP_BATTERY_REMAINING_TIME);
                addProperty(this.backupBatteryRemainingTime);
                return this;
            }
            
            /**
             * @param engineCoolantFluidLevel Engine coolant fluid level
             * @return The builder
             */
            public Builder setEngineCoolantFluidLevel(Property<FluidLevel> engineCoolantFluidLevel) {
                this.engineCoolantFluidLevel = engineCoolantFluidLevel.setIdentifier(PROPERTY_ENGINE_COOLANT_FLUID_LEVEL);
                addProperty(this.engineCoolantFluidLevel);
                return this;
            }
            
            /**
             * @param engineOilFluidLevel Engine oil fluid level
             * @return The builder
             */
            public Builder setEngineOilFluidLevel(Property<FluidLevel> engineOilFluidLevel) {
                this.engineOilFluidLevel = engineOilFluidLevel.setIdentifier(PROPERTY_ENGINE_OIL_FLUID_LEVEL);
                addProperty(this.engineOilFluidLevel);
                return this;
            }
            
            /**
             * @param engineOilPressureLevel Engine oil pressure level
             * @return The builder
             */
            public Builder setEngineOilPressureLevel(Property<EngineOilPressureLevel> engineOilPressureLevel) {
                this.engineOilPressureLevel = engineOilPressureLevel.setIdentifier(PROPERTY_ENGINE_OIL_PRESSURE_LEVEL);
                addProperty(this.engineOilPressureLevel);
                return this;
            }
            
            /**
             * @param engineTimeToNextService Engine time until next service of the vehicle
             * @return The builder
             */
            public Builder setEngineTimeToNextService(Property<Duration> engineTimeToNextService) {
                this.engineTimeToNextService = engineTimeToNextService.setIdentifier(PROPERTY_ENGINE_TIME_TO_NEXT_SERVICE);
                addProperty(this.engineTimeToNextService);
                return this;
            }
            
            /**
             * @param lowVoltageBatteryChargeLevel Indicates if the charge level of the low voltage battery is too low to use other systems
             * @return The builder
             */
            public Builder setLowVoltageBatteryChargeLevel(Property<LowVoltageBatteryChargeLevel> lowVoltageBatteryChargeLevel) {
                this.lowVoltageBatteryChargeLevel = lowVoltageBatteryChargeLevel.setIdentifier(PROPERTY_LOW_VOLTAGE_BATTERY_CHARGE_LEVEL);
                addProperty(this.lowVoltageBatteryChargeLevel);
                return this;
            }
        }
    }

    public enum FuelLevelAccuracy implements ByteEnum {
        MEASURED((byte) 0x00),
        CALCULATED((byte) 0x01);
    
        public static FuelLevelAccuracy fromByte(byte byteValue) throws CommandParseException {
            FuelLevelAccuracy[] values = FuelLevelAccuracy.values();
    
            for (int i = 0; i < values.length; i++) {
                FuelLevelAccuracy state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(FuelLevelAccuracy.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        FuelLevelAccuracy(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum EngineOilPressureLevel implements ByteEnum {
        LOW((byte) 0x00),
        NORMAL((byte) 0x01),
        HIGH((byte) 0x02),
        LOW_SOFT((byte) 0x03),
        LOW_HARD((byte) 0x04),
        NO_SENSOR((byte) 0x05),
        SYSTEM_FAULT((byte) 0x06);
    
        public static EngineOilPressureLevel fromByte(byte byteValue) throws CommandParseException {
            EngineOilPressureLevel[] values = EngineOilPressureLevel.values();
    
            for (int i = 0; i < values.length; i++) {
                EngineOilPressureLevel state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(EngineOilPressureLevel.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        EngineOilPressureLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum LowVoltageBatteryChargeLevel implements ByteEnum {
        /**
         * Charge level is ok and no other systems are deactivated
         */
        OK((byte) 0x00),
        /**
         * High voltage users are cut off from low voltage management
         */
        DEACTIVATION_LEVEL_1((byte) 0x01),
        /**
         * Infotainment cut off, from that point on no data will be sent to vehicle backend
         */
        DEACTIVATION_LEVEL_2((byte) 0x02),
        /**
         * Wake up blocked, vehicle cannot be woken up externally
         */
        DEACTIVATION_LEVEL_3((byte) 0x03);
    
        public static LowVoltageBatteryChargeLevel fromByte(byte byteValue) throws CommandParseException {
            LowVoltageBatteryChargeLevel[] values = LowVoltageBatteryChargeLevel.values();
    
            for (int i = 0; i < values.length; i++) {
                LowVoltageBatteryChargeLevel state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(LowVoltageBatteryChargeLevel.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        LowVoltageBatteryChargeLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}