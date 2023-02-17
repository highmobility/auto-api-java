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
import com.highmobility.autoapi.value.ChargingRestriction;
import com.highmobility.autoapi.value.DepartureTime;
import com.highmobility.autoapi.value.DrivingModePhev;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.ReductionTime;
import com.highmobility.autoapi.value.TemperatureExtreme;
import com.highmobility.autoapi.value.Time;
import com.highmobility.autoapi.value.Timer;
import com.highmobility.autoapi.value.WeekdayTime;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.ElectricCurrent;
import com.highmobility.autoapi.value.measurement.ElectricPotentialDifference;
import com.highmobility.autoapi.value.measurement.Energy;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.Power;
import com.highmobility.autoapi.value.measurement.Temperature;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Charging capability
 */
public class Charging {
    public static final int IDENTIFIER = Identifier.CHARGING;

    public static final byte PROPERTY_ESTIMATED_RANGE = 0x02;
    public static final byte PROPERTY_BATTERY_LEVEL = 0x03;
    public static final byte PROPERTY_BATTERY_CURRENT_AC = 0x04;
    public static final byte PROPERTY_BATTERY_CURRENT_DC = 0x05;
    public static final byte PROPERTY_CHARGER_VOLTAGE_AC = 0x06;
    public static final byte PROPERTY_CHARGER_VOLTAGE_DC = 0x07;
    public static final byte PROPERTY_CHARGE_LIMIT = 0x08;
    public static final byte PROPERTY_TIME_TO_COMPLETE_CHARGE = 0x09;
    public static final byte PROPERTY_CHARGING_RATE_KW = 0x0a;
    public static final byte PROPERTY_CHARGE_PORT_STATE = 0x0b;
    public static final byte PROPERTY_CHARGE_MODE = 0x0c;
    public static final byte PROPERTY_MAX_CHARGING_CURRENT = 0x0e;
    public static final byte PROPERTY_PLUG_TYPE = 0x0f;
    public static final byte PROPERTY_CHARGING_WINDOW_CHOSEN = 0x10;
    public static final byte PROPERTY_DEPARTURE_TIMES = 0x11;
    public static final byte PROPERTY_REDUCTION_TIMES = 0x13;
    public static final byte PROPERTY_BATTERY_TEMPERATURE = 0x14;
    public static final byte PROPERTY_TIMERS = 0x15;
    public static final byte PROPERTY_PLUGGED_IN = 0x16;
    public static final byte PROPERTY_STATUS = 0x17;
    public static final byte PROPERTY_CHARGING_RATE = 0x18;
    public static final byte PROPERTY_BATTERY_CURRENT = 0x19;
    public static final byte PROPERTY_CHARGER_VOLTAGE = 0x1a;
    public static final byte PROPERTY_CURRENT_TYPE = 0x1b;
    public static final byte PROPERTY_MAX_RANGE = 0x1c;
    public static final byte PROPERTY_STARTER_BATTERY_STATE = 0x1d;
    public static final byte PROPERTY_SMART_CHARGING_STATUS = 0x1e;
    public static final byte PROPERTY_BATTERY_LEVEL_AT_DEPARTURE = 0x1f;
    public static final byte PROPERTY_PRECONDITIONING_DEPARTURE_STATUS = 0x20;
    public static final byte PROPERTY_PRECONDITIONING_IMMEDIATE_STATUS = 0x21;
    public static final byte PROPERTY_PRECONDITIONING_DEPARTURE_ENABLED = 0x22;
    public static final byte PROPERTY_PRECONDITIONING_ERROR = 0x23;
    public static final byte PROPERTY_BATTERY_CAPACITY = 0x24;
    public static final byte PROPERTY_AUXILIARY_POWER = 0x25;
    public static final byte PROPERTY_CHARGING_COMPLETE_LOCK = 0x26;
    public static final byte PROPERTY_BATTERY_MAX_AVAILABLE = 0x27;
    public static final byte PROPERTY_CHARGING_END_REASON = 0x28;
    public static final byte PROPERTY_CHARGING_PHASES = 0x29;
    public static final byte PROPERTY_BATTERY_ENERGY = 0x2a;
    public static final byte PROPERTY_BATTERY_ENERGY_CHARGABLE = 0x2b;
    public static final byte PROPERTY_CHARGING_SINGLE_IMMEDIATE = 0x2c;
    public static final byte PROPERTY_CHARGING_TIME_DISPLAY = 0x2d;
    public static final byte PROPERTY_DEPARTURE_TIME_DISPLAY = 0x2e;
    public static final byte PROPERTY_RESTRICTION = 0x2f;
    public static final byte PROPERTY_LIMIT_STATUS = 0x30;
    public static final byte PROPERTY_CURRENT_LIMIT = 0x31;
    public static final byte PROPERTY_SMART_CHARGING_OPTION = 0x32;
    public static final byte PROPERTY_PLUG_LOCK_STATUS = 0x33;
    public static final byte PROPERTY_FLAP_LOCK_STATUS = 0x34;
    public static final byte PROPERTY_ACOUSTIC_LIMIT = 0x35;
    public static final byte PROPERTY_MIN_CHARGING_CURRENT = 0x36;
    public static final byte PROPERTY_ESTIMATED_RANGE_TARGET = 0x37;
    public static final byte PROPERTY_FULLY_CHARGED_END_TIMES = 0x38;
    public static final byte PROPERTY_PRECONDITIONING_SCHEDULED_TIME = 0x39;
    public static final byte PROPERTY_PRECONDITIONING_REMAINING_TIME = 0x3a;
    public static final byte PROPERTY_BATTERY_VOLTAGE = 0x3b;
    public static final byte PROPERTY_BATTERY_TEMPRETATURE_EXTREMES = 0x3c;
    public static final byte PROPERTY_BATTERY_TEMPERATURE_CONTROL_DEMAND = 0x3d;
    public static final byte PROPERTY_CHARGING_CURRENT = 0x3e;
    public static final byte PROPERTY_BATTERY_STATUS = 0x3f;
    public static final byte PROPERTY_BATTERY_LED = 0x40;
    public static final byte PROPERTY_BATTERY_COOLING_TEMPERATURE = 0x41;
    public static final byte PROPERTY_BATTERY_TEMPERATURE_EXTREMES = 0x42;
    public static final byte PROPERTY_DRIVING_MODE_PHEV = 0x43;
    public static final byte PROPERTY_BATTERY_CHARGE_TYPE = 0x44;

    /**
     * Get Charging property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Charging property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Charging property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Charging property availabilities
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
     * Get Charging properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Charging properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Charging properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Charging properties
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
     * Get specific Charging properties
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
     * Start stop charging
     */
    public static class StartStopCharging extends SetCommand {
        Property<Status> status = new Property<>(Status.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<Status> getStatus() {
            return status;
        }
        
        /**
         * Start stop charging
         * 
         * @param status The status
         */
        public StartStopCharging(Status status) {
            super(IDENTIFIER);
        
            if (status == Status.CHARGING_COMPLETE ||
                status == Status.INITIALISING ||
                status == Status.CHARGING_PAUSED ||
                status == Status.CHARGING_ERROR ||
                status == Status.CABLE_UNPLUGGED ||
                status == Status.SLOW_CHARGING ||
                status == Status.FAST_CHARGING ||
                status == Status.DISCHARGING ||
                status == Status.FOREIGN_OBJECT_DETECTED ||
                status == Status.CONDITIONING ||
                status == Status.FLAP_OPEN) throw new IllegalArgumentException();
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        StartStopCharging(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    
                    return null;
                });
            }
            if (this.status.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set charge limit
     */
    public static class SetChargeLimit extends SetCommand {
        Property<Double> chargeLimit = new Property<>(Double.class, PROPERTY_CHARGE_LIMIT);
    
        /**
         * @return The charge limit
         */
        public Property<Double> getChargeLimit() {
            return chargeLimit;
        }
        
        /**
         * Set charge limit
         * 
         * @param chargeLimit Charge limit percentage between 0.0-1.0
         */
        public SetChargeLimit(Double chargeLimit) {
            super(IDENTIFIER);
        
            addProperty(this.chargeLimit.update(chargeLimit));
            createBytes();
        }
    
        SetChargeLimit(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_CHARGE_LIMIT) return chargeLimit.update(p);
                    
                    return null;
                });
            }
            if (this.chargeLimit.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Open close charging port
     */
    public static class OpenCloseChargingPort extends SetCommand {
        Property<Position> chargePortState = new Property<>(Position.class, PROPERTY_CHARGE_PORT_STATE);
    
        /**
         * @return The charge port state
         */
        public Property<Position> getChargePortState() {
            return chargePortState;
        }
        
        /**
         * Open close charging port
         * 
         * @param chargePortState The charge port state
         */
        public OpenCloseChargingPort(Position chargePortState) {
            super(IDENTIFIER);
        
            addProperty(this.chargePortState.update(chargePortState));
            createBytes();
        }
    
        OpenCloseChargingPort(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_CHARGE_PORT_STATE) return chargePortState.update(p);
                    
                    return null;
                });
            }
            if (this.chargePortState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set charge mode
     */
    public static class SetChargeMode extends SetCommand {
        Property<ChargeMode> chargeMode = new Property<>(ChargeMode.class, PROPERTY_CHARGE_MODE);
    
        /**
         * @return The charge mode
         */
        public Property<ChargeMode> getChargeMode() {
            return chargeMode;
        }
        
        /**
         * Set charge mode
         * 
         * @param chargeMode The charge mode
         */
        public SetChargeMode(ChargeMode chargeMode) {
            super(IDENTIFIER);
        
            if (chargeMode == ChargeMode.INDUCTIVE ||
                chargeMode == ChargeMode.CONDUCTIVE ||
                chargeMode == ChargeMode.PUSH_BUTTON) throw new IllegalArgumentException();
        
            addProperty(this.chargeMode.update(chargeMode));
            createBytes();
        }
    
        SetChargeMode(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_CHARGE_MODE) return chargeMode.update(p);
                    
                    return null;
                });
            }
            if (this.chargeMode.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set charging timers
     */
    public static class SetChargingTimers extends SetCommand {
        List<Property<Timer>> timers;
    
        /**
         * @return The timers
         */
        public List<Property<Timer>> getTimers() {
            return timers;
        }
        
        /**
         * Set charging timers
         * 
         * @param timers The timers
         */
        public SetChargingTimers(List<Timer> timers) {
            super(IDENTIFIER);
        
            final ArrayList<Property<Timer>> timersBuilder = new ArrayList<>();
            if (timers != null) {
                for (Timer timer : timers) {
                    Property<Timer> prop = new Property<>(0x15, timer);
                    timersBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.timers = timersBuilder;
            createBytes();
        }
    
        SetChargingTimers(byte[] bytes) throws PropertyParseException {
            super(bytes);
        
            final ArrayList<Property<Timer>> timersBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_TIMERS) {
                        Property<Timer> timer = new Property<>(Timer.class, p);
                        timersBuilder.add(timer);
                        return timer;
                    }
                    
                    return null;
                });
            }
        
            timers = timersBuilder;
            if (this.timers.size() == 0) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set reduction of charging current times
     */
    public static class SetReductionOfChargingCurrentTimes extends SetCommand {
        List<Property<ReductionTime>> reductionTimes;
    
        /**
         * @return The reduction times
         */
        public List<Property<ReductionTime>> getReductionTimes() {
            return reductionTimes;
        }
        
        /**
         * Set reduction of charging current times
         * 
         * @param reductionTimes The reduction times
         */
        public SetReductionOfChargingCurrentTimes(List<ReductionTime> reductionTimes) {
            super(IDENTIFIER);
        
            final ArrayList<Property<ReductionTime>> reductionTimesBuilder = new ArrayList<>();
            if (reductionTimes != null) {
                for (ReductionTime reductionTime : reductionTimes) {
                    Property<ReductionTime> prop = new Property<>(0x13, reductionTime);
                    reductionTimesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.reductionTimes = reductionTimesBuilder;
            createBytes();
        }
    
        SetReductionOfChargingCurrentTimes(byte[] bytes) throws PropertyParseException {
            super(bytes);
        
            final ArrayList<Property<ReductionTime>> reductionTimesBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_REDUCTION_TIMES) {
                        Property<ReductionTime> reductionTime = new Property<>(ReductionTime.class, p);
                        reductionTimesBuilder.add(reductionTime);
                        return reductionTime;
                    }
                    
                    return null;
                });
            }
        
            reductionTimes = reductionTimesBuilder;
            if (this.reductionTimes.size() == 0) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The charging state
     */
    public static class State extends SetCommand {
        Property<Length> estimatedRange = new Property<>(Length.class, PROPERTY_ESTIMATED_RANGE);
        Property<Double> batteryLevel = new Property<>(Double.class, PROPERTY_BATTERY_LEVEL);
        Property<ElectricCurrent> batteryCurrentAC = new Property<>(ElectricCurrent.class, PROPERTY_BATTERY_CURRENT_AC);
        Property<ElectricCurrent> batteryCurrentDC = new Property<>(ElectricCurrent.class, PROPERTY_BATTERY_CURRENT_DC);
        Property<ElectricPotentialDifference> chargerVoltageAC = new Property<>(ElectricPotentialDifference.class, PROPERTY_CHARGER_VOLTAGE_AC);
        Property<ElectricPotentialDifference> chargerVoltageDC = new Property<>(ElectricPotentialDifference.class, PROPERTY_CHARGER_VOLTAGE_DC);
        Property<Double> chargeLimit = new Property<>(Double.class, PROPERTY_CHARGE_LIMIT);
        Property<Duration> timeToCompleteCharge = new Property<>(Duration.class, PROPERTY_TIME_TO_COMPLETE_CHARGE);
        Property<Power> chargingRateKW = new Property<>(Power.class, PROPERTY_CHARGING_RATE_KW);
        Property<Position> chargePortState = new Property<>(Position.class, PROPERTY_CHARGE_PORT_STATE);
        Property<ChargeMode> chargeMode = new Property<>(ChargeMode.class, PROPERTY_CHARGE_MODE);
        Property<ElectricCurrent> maxChargingCurrent = new Property<>(ElectricCurrent.class, PROPERTY_MAX_CHARGING_CURRENT);
        Property<PlugType> plugType = new Property<>(PlugType.class, PROPERTY_PLUG_TYPE);
        Property<ChargingWindowChosen> chargingWindowChosen = new Property<>(ChargingWindowChosen.class, PROPERTY_CHARGING_WINDOW_CHOSEN);
        List<Property<DepartureTime>> departureTimes;
        List<Property<ReductionTime>> reductionTimes;
        Property<Temperature> batteryTemperature = new Property<>(Temperature.class, PROPERTY_BATTERY_TEMPERATURE);
        List<Property<Timer>> timers;
        Property<PluggedIn> pluggedIn = new Property<>(PluggedIn.class, PROPERTY_PLUGGED_IN);
        Property<Status> status = new Property<>(Status.class, PROPERTY_STATUS);
        Property<Power> chargingRate = new Property<>(Power.class, PROPERTY_CHARGING_RATE);
        Property<ElectricCurrent> batteryCurrent = new Property<>(ElectricCurrent.class, PROPERTY_BATTERY_CURRENT);
        Property<ElectricPotentialDifference> chargerVoltage = new Property<>(ElectricPotentialDifference.class, PROPERTY_CHARGER_VOLTAGE);
        Property<CurrentType> currentType = new Property<>(CurrentType.class, PROPERTY_CURRENT_TYPE);
        Property<Length> maxRange = new Property<>(Length.class, PROPERTY_MAX_RANGE);
        Property<StarterBatteryState> starterBatteryState = new Property<>(StarterBatteryState.class, PROPERTY_STARTER_BATTERY_STATE);
        Property<SmartChargingStatus> smartChargingStatus = new Property<>(SmartChargingStatus.class, PROPERTY_SMART_CHARGING_STATUS);
        Property<Double> batteryLevelAtDeparture = new Property<>(Double.class, PROPERTY_BATTERY_LEVEL_AT_DEPARTURE);
        Property<ActiveState> preconditioningDepartureStatus = new Property<>(ActiveState.class, PROPERTY_PRECONDITIONING_DEPARTURE_STATUS);
        Property<ActiveState> preconditioningImmediateStatus = new Property<>(ActiveState.class, PROPERTY_PRECONDITIONING_IMMEDIATE_STATUS);
        Property<EnabledState> preconditioningDepartureEnabled = new Property<>(EnabledState.class, PROPERTY_PRECONDITIONING_DEPARTURE_ENABLED);
        Property<PreconditioningError> preconditioningError = new Property<>(PreconditioningError.class, PROPERTY_PRECONDITIONING_ERROR);
        Property<Energy> batteryCapacity = new Property<>(Energy.class, PROPERTY_BATTERY_CAPACITY);
        Property<Power> auxiliaryPower = new Property<>(Power.class, PROPERTY_AUXILIARY_POWER);
        Property<ActiveState> chargingCompleteLock = new Property<>(ActiveState.class, PROPERTY_CHARGING_COMPLETE_LOCK);
        Property<Energy> batteryMaxAvailable = new Property<>(Energy.class, PROPERTY_BATTERY_MAX_AVAILABLE);
        Property<ChargingEndReason> chargingEndReason = new Property<>(ChargingEndReason.class, PROPERTY_CHARGING_END_REASON);
        Property<ChargingPhases> chargingPhases = new Property<>(ChargingPhases.class, PROPERTY_CHARGING_PHASES);
        Property<Energy> batteryEnergy = new Property<>(Energy.class, PROPERTY_BATTERY_ENERGY);
        Property<Energy> batteryEnergyChargable = new Property<>(Energy.class, PROPERTY_BATTERY_ENERGY_CHARGABLE);
        Property<ActiveState> chargingSingleImmediate = new Property<>(ActiveState.class, PROPERTY_CHARGING_SINGLE_IMMEDIATE);
        Property<ChargingTimeDisplay> chargingTimeDisplay = new Property<>(ChargingTimeDisplay.class, PROPERTY_CHARGING_TIME_DISPLAY);
        Property<DepartureTimeDisplay> departureTimeDisplay = new Property<>(DepartureTimeDisplay.class, PROPERTY_DEPARTURE_TIME_DISPLAY);
        Property<ChargingRestriction> restriction = new Property<>(ChargingRestriction.class, PROPERTY_RESTRICTION);
        Property<ActiveState> limitStatus = new Property<>(ActiveState.class, PROPERTY_LIMIT_STATUS);
        Property<ElectricCurrent> currentLimit = new Property<>(ElectricCurrent.class, PROPERTY_CURRENT_LIMIT);
        Property<SmartChargingOption> smartChargingOption = new Property<>(SmartChargingOption.class, PROPERTY_SMART_CHARGING_OPTION);
        Property<LockState> plugLockStatus = new Property<>(LockState.class, PROPERTY_PLUG_LOCK_STATUS);
        Property<LockState> flapLockStatus = new Property<>(LockState.class, PROPERTY_FLAP_LOCK_STATUS);
        Property<AcousticLimit> acousticLimit = new Property<>(AcousticLimit.class, PROPERTY_ACOUSTIC_LIMIT);
        Property<ElectricCurrent> minChargingCurrent = new Property<>(ElectricCurrent.class, PROPERTY_MIN_CHARGING_CURRENT);
        Property<Length> estimatedRangeTarget = new Property<>(Length.class, PROPERTY_ESTIMATED_RANGE_TARGET);
        Property<WeekdayTime> fullyChargedEndTimes = new Property<>(WeekdayTime.class, PROPERTY_FULLY_CHARGED_END_TIMES);
        Property<Time> preconditioningScheduledTime = new Property<>(Time.class, PROPERTY_PRECONDITIONING_SCHEDULED_TIME);
        Property<Duration> preconditioningRemainingTime = new Property<>(Duration.class, PROPERTY_PRECONDITIONING_REMAINING_TIME);
        Property<ElectricPotentialDifference> batteryVoltage = new Property<>(ElectricPotentialDifference.class, PROPERTY_BATTERY_VOLTAGE);
        Property<TemperatureExtreme> batteryTempretatureExtremes = new Property<>(TemperatureExtreme.class, PROPERTY_BATTERY_TEMPRETATURE_EXTREMES);
        Property<BatteryTemperatureControlDemand> batteryTemperatureControlDemand = new Property<>(BatteryTemperatureControlDemand.class, PROPERTY_BATTERY_TEMPERATURE_CONTROL_DEMAND);
        Property<ElectricCurrent> chargingCurrent = new Property<>(ElectricCurrent.class, PROPERTY_CHARGING_CURRENT);
        Property<BatteryStatus> batteryStatus = new Property<>(BatteryStatus.class, PROPERTY_BATTERY_STATUS);
        Property<BatteryLed> batteryLed = new Property<>(BatteryLed.class, PROPERTY_BATTERY_LED);
        Property<Temperature> batteryCoolingTemperature = new Property<>(Temperature.class, PROPERTY_BATTERY_COOLING_TEMPERATURE);
        Property<TemperatureExtreme> batteryTemperatureExtremes = new Property<>(TemperatureExtreme.class, PROPERTY_BATTERY_TEMPERATURE_EXTREMES);
        Property<DrivingModePhev> drivingModePHEV = new Property<>(DrivingModePhev.class, PROPERTY_DRIVING_MODE_PHEV);
        Property<BatteryChargeType> batteryChargeType = new Property<>(BatteryChargeType.class, PROPERTY_BATTERY_CHARGE_TYPE);
    
        /**
         * @return Estimated range
         */
        public Property<Length> getEstimatedRange() {
            return estimatedRange;
        }
    
        /**
         * @return Battery level percentage between 0.0-1.0
         */
        public Property<Double> getBatteryLevel() {
            return batteryLevel;
        }
    
        /**
         * @return Battery alternating current
         * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getBatteryCurrent()}
         */
        @Deprecated
        public Property<ElectricCurrent> getBatteryCurrentAC() {
            return batteryCurrentAC;
        }
    
        /**
         * @return Battery direct current
         * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getBatteryCurrent()}
         */
        @Deprecated
        public Property<ElectricCurrent> getBatteryCurrentDC() {
            return batteryCurrentDC;
        }
    
        /**
         * @return Charger voltage for alternating current
         * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getChargerVoltage()}
         */
        @Deprecated
        public Property<ElectricPotentialDifference> getChargerVoltageAC() {
            return chargerVoltageAC;
        }
    
        /**
         * @return Charger voltage for direct current
         * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getChargerVoltage()}
         */
        @Deprecated
        public Property<ElectricPotentialDifference> getChargerVoltageDC() {
            return chargerVoltageDC;
        }
    
        /**
         * @return Charge limit percentage between 0.0-1.0
         */
        public Property<Double> getChargeLimit() {
            return chargeLimit;
        }
    
        /**
         * @return Time until charging completed
         */
        public Property<Duration> getTimeToCompleteCharge() {
            return timeToCompleteCharge;
        }
    
        /**
         * @return Charging rate
         * @deprecated removed the unit from the name. Replaced by {@link #getChargingRate()}
         */
        @Deprecated
        public Property<Power> getChargingRateKW() {
            return chargingRateKW;
        }
    
        /**
         * @return The charge port state
         */
        public Property<Position> getChargePortState() {
            return chargePortState;
        }
    
        /**
         * @return The charge mode
         */
        public Property<ChargeMode> getChargeMode() {
            return chargeMode;
        }
    
        /**
         * @return Maximum charging current
         */
        public Property<ElectricCurrent> getMaxChargingCurrent() {
            return maxChargingCurrent;
        }
    
        /**
         * @return The plug type
         */
        public Property<PlugType> getPlugType() {
            return plugType;
        }
    
        /**
         * @return The charging window chosen
         */
        public Property<ChargingWindowChosen> getChargingWindowChosen() {
            return chargingWindowChosen;
        }
    
        /**
         * @return The departure times
         */
        public List<Property<DepartureTime>> getDepartureTimes() {
            return departureTimes;
        }
    
        /**
         * @return The reduction times
         */
        public List<Property<ReductionTime>> getReductionTimes() {
            return reductionTimes;
        }
    
        /**
         * @return Battery temperature
         */
        public Property<Temperature> getBatteryTemperature() {
            return batteryTemperature;
        }
    
        /**
         * @return The timers
         */
        public List<Property<Timer>> getTimers() {
            return timers;
        }
    
        /**
         * @return The plugged in
         */
        public Property<PluggedIn> getPluggedIn() {
            return pluggedIn;
        }
    
        /**
         * @return The status
         */
        public Property<Status> getStatus() {
            return status;
        }
    
        /**
         * @return Charge rate when charging
         */
        public Property<Power> getChargingRate() {
            return chargingRate;
        }
    
        /**
         * @return Battery current - charging if posititive and discharning when negative.
         */
        public Property<ElectricCurrent> getBatteryCurrent() {
            return batteryCurrent;
        }
    
        /**
         * @return Charger voltage
         */
        public Property<ElectricPotentialDifference> getChargerVoltage() {
            return chargerVoltage;
        }
    
        /**
         * @return Type of current in use
         */
        public Property<CurrentType> getCurrentType() {
            return currentType;
        }
    
        /**
         * @return Maximum electric range with 100% of battery
         */
        public Property<Length> getMaxRange() {
            return maxRange;
        }
    
        /**
         * @return State of the starter battery
         */
        public Property<StarterBatteryState> getStarterBatteryState() {
            return starterBatteryState;
        }
    
        /**
         * @return Status of optimized/intelligent charging
         */
        public Property<SmartChargingStatus> getSmartChargingStatus() {
            return smartChargingStatus;
        }
    
        /**
         * @return Battery charge level expected at time of departure
         */
        public Property<Double> getBatteryLevelAtDeparture() {
            return batteryLevelAtDeparture;
        }
    
        /**
         * @return Status of preconditioning at departure time
         */
        public Property<ActiveState> getPreconditioningDepartureStatus() {
            return preconditioningDepartureStatus;
        }
    
        /**
         * @return Status of immediate preconditioning
         */
        public Property<ActiveState> getPreconditioningImmediateStatus() {
            return preconditioningImmediateStatus;
        }
    
        /**
         * @return Preconditioning activation status at departure
         */
        public Property<EnabledState> getPreconditioningDepartureEnabled() {
            return preconditioningDepartureEnabled;
        }
    
        /**
         * @return Preconditioning error if one is encountered
         */
        public Property<PreconditioningError> getPreconditioningError() {
            return preconditioningError;
        }
    
        /**
         * @return Indicates the battery capacity
         */
        public Property<Energy> getBatteryCapacity() {
            return batteryCapacity;
        }
    
        /**
         * @return Auxiliary power used for predictions.
         */
        public Property<Power> getAuxiliaryPower() {
            return auxiliaryPower;
        }
    
        /**
         * @return Locking status of the charging plug after charging complete.
         */
        public Property<ActiveState> getChargingCompleteLock() {
            return chargingCompleteLock;
        }
    
        /**
         * @return Maximum available energy content of the high-voltage battery.
         */
        public Property<Energy> getBatteryMaxAvailable() {
            return batteryMaxAvailable;
        }
    
        /**
         * @return Reason for ending a charging process.
         */
        public Property<ChargingEndReason> getChargingEndReason() {
            return chargingEndReason;
        }
    
        /**
         * @return Charging process count of the high-voltage battery (phases).
         */
        public Property<ChargingPhases> getChargingPhases() {
            return chargingPhases;
        }
    
        /**
         * @return Energy content of the high-voltage battery.
         */
        public Property<Energy> getBatteryEnergy() {
            return batteryEnergy;
        }
    
        /**
         * @return Energy required until high-voltage battery is fully charged.
         */
        public Property<Energy> getBatteryEnergyChargable() {
            return batteryEnergyChargable;
        }
    
        /**
         * @return Single instant charging function status.
         */
        public Property<ActiveState> getChargingSingleImmediate() {
            return chargingSingleImmediate;
        }
    
        /**
         * @return Charging time displayed in the vehicle.
         */
        public Property<ChargingTimeDisplay> getChargingTimeDisplay() {
            return chargingTimeDisplay;
        }
    
        /**
         * @return Departure time displayed in the vehicle.
         */
        public Property<DepartureTimeDisplay> getDepartureTimeDisplay() {
            return departureTimeDisplay;
        }
    
        /**
         * @return Charging limit and state
         */
        public Property<ChargingRestriction> getRestriction() {
            return restriction;
        }
    
        /**
         * @return Indicates whether charging limit is active.
         */
        public Property<ActiveState> getLimitStatus() {
            return limitStatus;
        }
    
        /**
         * @return Limit for the charging current.
         */
        public Property<ElectricCurrent> getCurrentLimit() {
            return currentLimit;
        }
    
        /**
         * @return Smart charging option being used to charge with.
         */
        public Property<SmartChargingOption> getSmartChargingOption() {
            return smartChargingOption;
        }
    
        /**
         * @return Locking status of charging plug.
         */
        public Property<LockState> getPlugLockStatus() {
            return plugLockStatus;
        }
    
        /**
         * @return Locking status of charging flap.
         */
        public Property<LockState> getFlapLockStatus() {
            return flapLockStatus;
        }
    
        /**
         * @return Acoustic limitation of charging process.
         */
        public Property<AcousticLimit> getAcousticLimit() {
            return acousticLimit;
        }
    
        /**
         * @return Minimum charging current.
         */
        public Property<ElectricCurrent> getMinChargingCurrent() {
            return minChargingCurrent;
        }
    
        /**
         * @return Remaining electric range depending on target charging status.
         */
        public Property<Length> getEstimatedRangeTarget() {
            return estimatedRangeTarget;
        }
    
        /**
         * @return Time and weekday when the vehicle will be fully charged.
         */
        public Property<WeekdayTime> getFullyChargedEndTimes() {
            return fullyChargedEndTimes;
        }
    
        /**
         * @return Preconditioning scheduled departure time.
         */
        public Property<Time> getPreconditioningScheduledTime() {
            return preconditioningScheduledTime;
        }
    
        /**
         * @return Time until preconditioning is complete.
         */
        public Property<Duration> getPreconditioningRemainingTime() {
            return preconditioningRemainingTime;
        }
    
        /**
         * @return High-voltage battery electric potential difference (aka voltage).
         */
        public Property<ElectricPotentialDifference> getBatteryVoltage() {
            return batteryVoltage;
        }
    
        /**
         * @return Current highest-lowest temperature inside the battery.
         * @deprecated fixed the name typo. Replaced by {@link #getBatteryTemperatureExtremes()}
         */
        @Deprecated
        public Property<TemperatureExtreme> getBatteryTempretatureExtremes() {
            return batteryTempretatureExtremes;
        }
    
        /**
         * @return Current demand of HV battery temperature control system.
         */
        public Property<BatteryTemperatureControlDemand> getBatteryTemperatureControlDemand() {
            return batteryTemperatureControlDemand;
        }
    
        /**
         * @return Charging electric current.
         */
        public Property<ElectricCurrent> getChargingCurrent() {
            return chargingCurrent;
        }
    
        /**
         * @return Battery state.
         */
        public Property<BatteryStatus> getBatteryStatus() {
            return batteryStatus;
        }
    
        /**
         * @return State of LED for the battery.
         */
        public Property<BatteryLed> getBatteryLed() {
            return batteryLed;
        }
    
        /**
         * @return Battery cooling temperature.
         */
        public Property<Temperature> getBatteryCoolingTemperature() {
            return batteryCoolingTemperature;
        }
    
        /**
         * @return Current highest-lowest temperature inside the battery.
         */
        public Property<TemperatureExtreme> getBatteryTemperatureExtremes() {
            return batteryTemperatureExtremes;
        }
    
        /**
         * @return Indicates the current driving mode for Plug-In Hybrid Vehicle.
         */
        public Property<DrivingModePhev> getDrivingModePHEV() {
            return drivingModePHEV;
        }
    
        /**
         * @return Battery charge type.
         */
        public Property<BatteryChargeType> getBatteryChargeType() {
            return batteryChargeType;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<DepartureTime>> departureTimesBuilder = new ArrayList<>();
            final ArrayList<Property<ReductionTime>> reductionTimesBuilder = new ArrayList<>();
            final ArrayList<Property<Timer>> timersBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_ESTIMATED_RANGE: return estimatedRange.update(p);
                        case PROPERTY_BATTERY_LEVEL: return batteryLevel.update(p);
                        case PROPERTY_BATTERY_CURRENT_AC: return batteryCurrentAC.update(p);
                        case PROPERTY_BATTERY_CURRENT_DC: return batteryCurrentDC.update(p);
                        case PROPERTY_CHARGER_VOLTAGE_AC: return chargerVoltageAC.update(p);
                        case PROPERTY_CHARGER_VOLTAGE_DC: return chargerVoltageDC.update(p);
                        case PROPERTY_CHARGE_LIMIT: return chargeLimit.update(p);
                        case PROPERTY_TIME_TO_COMPLETE_CHARGE: return timeToCompleteCharge.update(p);
                        case PROPERTY_CHARGING_RATE_KW: return chargingRateKW.update(p);
                        case PROPERTY_CHARGE_PORT_STATE: return chargePortState.update(p);
                        case PROPERTY_CHARGE_MODE: return chargeMode.update(p);
                        case PROPERTY_MAX_CHARGING_CURRENT: return maxChargingCurrent.update(p);
                        case PROPERTY_PLUG_TYPE: return plugType.update(p);
                        case PROPERTY_CHARGING_WINDOW_CHOSEN: return chargingWindowChosen.update(p);
                        case PROPERTY_DEPARTURE_TIMES:
                            Property<DepartureTime> departureTime = new Property<>(DepartureTime.class, p);
                            departureTimesBuilder.add(departureTime);
                            return departureTime;
                        case PROPERTY_REDUCTION_TIMES:
                            Property<ReductionTime> reductionTime = new Property<>(ReductionTime.class, p);
                            reductionTimesBuilder.add(reductionTime);
                            return reductionTime;
                        case PROPERTY_BATTERY_TEMPERATURE: return batteryTemperature.update(p);
                        case PROPERTY_TIMERS:
                            Property<Timer> timer = new Property<>(Timer.class, p);
                            timersBuilder.add(timer);
                            return timer;
                        case PROPERTY_PLUGGED_IN: return pluggedIn.update(p);
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_CHARGING_RATE: return chargingRate.update(p);
                        case PROPERTY_BATTERY_CURRENT: return batteryCurrent.update(p);
                        case PROPERTY_CHARGER_VOLTAGE: return chargerVoltage.update(p);
                        case PROPERTY_CURRENT_TYPE: return currentType.update(p);
                        case PROPERTY_MAX_RANGE: return maxRange.update(p);
                        case PROPERTY_STARTER_BATTERY_STATE: return starterBatteryState.update(p);
                        case PROPERTY_SMART_CHARGING_STATUS: return smartChargingStatus.update(p);
                        case PROPERTY_BATTERY_LEVEL_AT_DEPARTURE: return batteryLevelAtDeparture.update(p);
                        case PROPERTY_PRECONDITIONING_DEPARTURE_STATUS: return preconditioningDepartureStatus.update(p);
                        case PROPERTY_PRECONDITIONING_IMMEDIATE_STATUS: return preconditioningImmediateStatus.update(p);
                        case PROPERTY_PRECONDITIONING_DEPARTURE_ENABLED: return preconditioningDepartureEnabled.update(p);
                        case PROPERTY_PRECONDITIONING_ERROR: return preconditioningError.update(p);
                        case PROPERTY_BATTERY_CAPACITY: return batteryCapacity.update(p);
                        case PROPERTY_AUXILIARY_POWER: return auxiliaryPower.update(p);
                        case PROPERTY_CHARGING_COMPLETE_LOCK: return chargingCompleteLock.update(p);
                        case PROPERTY_BATTERY_MAX_AVAILABLE: return batteryMaxAvailable.update(p);
                        case PROPERTY_CHARGING_END_REASON: return chargingEndReason.update(p);
                        case PROPERTY_CHARGING_PHASES: return chargingPhases.update(p);
                        case PROPERTY_BATTERY_ENERGY: return batteryEnergy.update(p);
                        case PROPERTY_BATTERY_ENERGY_CHARGABLE: return batteryEnergyChargable.update(p);
                        case PROPERTY_CHARGING_SINGLE_IMMEDIATE: return chargingSingleImmediate.update(p);
                        case PROPERTY_CHARGING_TIME_DISPLAY: return chargingTimeDisplay.update(p);
                        case PROPERTY_DEPARTURE_TIME_DISPLAY: return departureTimeDisplay.update(p);
                        case PROPERTY_RESTRICTION: return restriction.update(p);
                        case PROPERTY_LIMIT_STATUS: return limitStatus.update(p);
                        case PROPERTY_CURRENT_LIMIT: return currentLimit.update(p);
                        case PROPERTY_SMART_CHARGING_OPTION: return smartChargingOption.update(p);
                        case PROPERTY_PLUG_LOCK_STATUS: return plugLockStatus.update(p);
                        case PROPERTY_FLAP_LOCK_STATUS: return flapLockStatus.update(p);
                        case PROPERTY_ACOUSTIC_LIMIT: return acousticLimit.update(p);
                        case PROPERTY_MIN_CHARGING_CURRENT: return minChargingCurrent.update(p);
                        case PROPERTY_ESTIMATED_RANGE_TARGET: return estimatedRangeTarget.update(p);
                        case PROPERTY_FULLY_CHARGED_END_TIMES: return fullyChargedEndTimes.update(p);
                        case PROPERTY_PRECONDITIONING_SCHEDULED_TIME: return preconditioningScheduledTime.update(p);
                        case PROPERTY_PRECONDITIONING_REMAINING_TIME: return preconditioningRemainingTime.update(p);
                        case PROPERTY_BATTERY_VOLTAGE: return batteryVoltage.update(p);
                        case PROPERTY_BATTERY_TEMPRETATURE_EXTREMES: return batteryTempretatureExtremes.update(p);
                        case PROPERTY_BATTERY_TEMPERATURE_CONTROL_DEMAND: return batteryTemperatureControlDemand.update(p);
                        case PROPERTY_CHARGING_CURRENT: return chargingCurrent.update(p);
                        case PROPERTY_BATTERY_STATUS: return batteryStatus.update(p);
                        case PROPERTY_BATTERY_LED: return batteryLed.update(p);
                        case PROPERTY_BATTERY_COOLING_TEMPERATURE: return batteryCoolingTemperature.update(p);
                        case PROPERTY_BATTERY_TEMPERATURE_EXTREMES: return batteryTemperatureExtremes.update(p);
                        case PROPERTY_DRIVING_MODE_PHEV: return drivingModePHEV.update(p);
                        case PROPERTY_BATTERY_CHARGE_TYPE: return batteryChargeType.update(p);
                    }
    
                    return null;
                });
            }
    
            departureTimes = departureTimesBuilder;
            reductionTimes = reductionTimesBuilder;
            timers = timersBuilder;
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
             * @param estimatedRange Estimated range
             * @return The builder
             */
            public Builder setEstimatedRange(Property<Length> estimatedRange) {
                Property property = estimatedRange.setIdentifier(PROPERTY_ESTIMATED_RANGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryLevel Battery level percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setBatteryLevel(Property<Double> batteryLevel) {
                Property property = batteryLevel.setIdentifier(PROPERTY_BATTERY_LEVEL);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryCurrentAC Battery alternating current
             * @return The builder
             * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getBatteryCurrent()}
             */
            @Deprecated
            public Builder setBatteryCurrentAC(Property<ElectricCurrent> batteryCurrentAC) {
                Property property = batteryCurrentAC.setIdentifier(PROPERTY_BATTERY_CURRENT_AC);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryCurrentDC Battery direct current
             * @return The builder
             * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getBatteryCurrent()}
             */
            @Deprecated
            public Builder setBatteryCurrentDC(Property<ElectricCurrent> batteryCurrentDC) {
                Property property = batteryCurrentDC.setIdentifier(PROPERTY_BATTERY_CURRENT_DC);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargerVoltageAC Charger voltage for alternating current
             * @return The builder
             * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getChargerVoltage()}
             */
            @Deprecated
            public Builder setChargerVoltageAC(Property<ElectricPotentialDifference> chargerVoltageAC) {
                Property property = chargerVoltageAC.setIdentifier(PROPERTY_CHARGER_VOLTAGE_AC);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargerVoltageDC Charger voltage for direct current
             * @return The builder
             * @deprecated moved AC/DC distinction into a separate property. Replaced by {@link #getChargerVoltage()}
             */
            @Deprecated
            public Builder setChargerVoltageDC(Property<ElectricPotentialDifference> chargerVoltageDC) {
                Property property = chargerVoltageDC.setIdentifier(PROPERTY_CHARGER_VOLTAGE_DC);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargeLimit Charge limit percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setChargeLimit(Property<Double> chargeLimit) {
                Property property = chargeLimit.setIdentifier(PROPERTY_CHARGE_LIMIT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param timeToCompleteCharge Time until charging completed
             * @return The builder
             */
            public Builder setTimeToCompleteCharge(Property<Duration> timeToCompleteCharge) {
                Property property = timeToCompleteCharge.setIdentifier(PROPERTY_TIME_TO_COMPLETE_CHARGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingRateKW Charging rate
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getChargingRate()}
             */
            @Deprecated
            public Builder setChargingRateKW(Property<Power> chargingRateKW) {
                Property property = chargingRateKW.setIdentifier(PROPERTY_CHARGING_RATE_KW);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargePortState The charge port state
             * @return The builder
             */
            public Builder setChargePortState(Property<Position> chargePortState) {
                Property property = chargePortState.setIdentifier(PROPERTY_CHARGE_PORT_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargeMode The charge mode
             * @return The builder
             */
            public Builder setChargeMode(Property<ChargeMode> chargeMode) {
                Property property = chargeMode.setIdentifier(PROPERTY_CHARGE_MODE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param maxChargingCurrent Maximum charging current
             * @return The builder
             */
            public Builder setMaxChargingCurrent(Property<ElectricCurrent> maxChargingCurrent) {
                Property property = maxChargingCurrent.setIdentifier(PROPERTY_MAX_CHARGING_CURRENT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param plugType The plug type
             * @return The builder
             */
            public Builder setPlugType(Property<PlugType> plugType) {
                Property property = plugType.setIdentifier(PROPERTY_PLUG_TYPE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingWindowChosen The charging window chosen
             * @return The builder
             */
            public Builder setChargingWindowChosen(Property<ChargingWindowChosen> chargingWindowChosen) {
                Property property = chargingWindowChosen.setIdentifier(PROPERTY_CHARGING_WINDOW_CHOSEN);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of departure times
             * 
             * @param departureTimes The departure times
             * @return The builder
             */
            public Builder setDepartureTimes(Property<DepartureTime>[] departureTimes) {
                for (int i = 0; i < departureTimes.length; i++) {
                    addDepartureTime(departureTimes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single departure time
             * 
             * @param departureTime The departure time
             * @return The builder
             */
            public Builder addDepartureTime(Property<DepartureTime> departureTime) {
                departureTime.setIdentifier(PROPERTY_DEPARTURE_TIMES);
                addProperty(departureTime);
                return this;
            }
            
            /**
             * Add an array of reduction times
             * 
             * @param reductionTimes The reduction times
             * @return The builder
             */
            public Builder setReductionTimes(Property<ReductionTime>[] reductionTimes) {
                for (int i = 0; i < reductionTimes.length; i++) {
                    addReductionTime(reductionTimes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single reduction time
             * 
             * @param reductionTime The reduction time
             * @return The builder
             */
            public Builder addReductionTime(Property<ReductionTime> reductionTime) {
                reductionTime.setIdentifier(PROPERTY_REDUCTION_TIMES);
                addProperty(reductionTime);
                return this;
            }
            
            /**
             * @param batteryTemperature Battery temperature
             * @return The builder
             */
            public Builder setBatteryTemperature(Property<Temperature> batteryTemperature) {
                Property property = batteryTemperature.setIdentifier(PROPERTY_BATTERY_TEMPERATURE);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of timers
             * 
             * @param timers The timers
             * @return The builder
             */
            public Builder setTimers(Property<Timer>[] timers) {
                for (int i = 0; i < timers.length; i++) {
                    addTimer(timers[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single timer
             * 
             * @param timer The timer
             * @return The builder
             */
            public Builder addTimer(Property<Timer> timer) {
                timer.setIdentifier(PROPERTY_TIMERS);
                addProperty(timer);
                return this;
            }
            
            /**
             * @param pluggedIn The plugged in
             * @return The builder
             */
            public Builder setPluggedIn(Property<PluggedIn> pluggedIn) {
                Property property = pluggedIn.setIdentifier(PROPERTY_PLUGGED_IN);
                addProperty(property);
                return this;
            }
            
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<Status> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingRate Charge rate when charging
             * @return The builder
             */
            public Builder setChargingRate(Property<Power> chargingRate) {
                Property property = chargingRate.setIdentifier(PROPERTY_CHARGING_RATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryCurrent Battery current - charging if posititive and discharning when negative.
             * @return The builder
             */
            public Builder setBatteryCurrent(Property<ElectricCurrent> batteryCurrent) {
                Property property = batteryCurrent.setIdentifier(PROPERTY_BATTERY_CURRENT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargerVoltage Charger voltage
             * @return The builder
             */
            public Builder setChargerVoltage(Property<ElectricPotentialDifference> chargerVoltage) {
                Property property = chargerVoltage.setIdentifier(PROPERTY_CHARGER_VOLTAGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param currentType Type of current in use
             * @return The builder
             */
            public Builder setCurrentType(Property<CurrentType> currentType) {
                Property property = currentType.setIdentifier(PROPERTY_CURRENT_TYPE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param maxRange Maximum electric range with 100% of battery
             * @return The builder
             */
            public Builder setMaxRange(Property<Length> maxRange) {
                Property property = maxRange.setIdentifier(PROPERTY_MAX_RANGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param starterBatteryState State of the starter battery
             * @return The builder
             */
            public Builder setStarterBatteryState(Property<StarterBatteryState> starterBatteryState) {
                Property property = starterBatteryState.setIdentifier(PROPERTY_STARTER_BATTERY_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param smartChargingStatus Status of optimized/intelligent charging
             * @return The builder
             */
            public Builder setSmartChargingStatus(Property<SmartChargingStatus> smartChargingStatus) {
                Property property = smartChargingStatus.setIdentifier(PROPERTY_SMART_CHARGING_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryLevelAtDeparture Battery charge level expected at time of departure
             * @return The builder
             */
            public Builder setBatteryLevelAtDeparture(Property<Double> batteryLevelAtDeparture) {
                Property property = batteryLevelAtDeparture.setIdentifier(PROPERTY_BATTERY_LEVEL_AT_DEPARTURE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningDepartureStatus Status of preconditioning at departure time
             * @return The builder
             */
            public Builder setPreconditioningDepartureStatus(Property<ActiveState> preconditioningDepartureStatus) {
                Property property = preconditioningDepartureStatus.setIdentifier(PROPERTY_PRECONDITIONING_DEPARTURE_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningImmediateStatus Status of immediate preconditioning
             * @return The builder
             */
            public Builder setPreconditioningImmediateStatus(Property<ActiveState> preconditioningImmediateStatus) {
                Property property = preconditioningImmediateStatus.setIdentifier(PROPERTY_PRECONDITIONING_IMMEDIATE_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningDepartureEnabled Preconditioning activation status at departure
             * @return The builder
             */
            public Builder setPreconditioningDepartureEnabled(Property<EnabledState> preconditioningDepartureEnabled) {
                Property property = preconditioningDepartureEnabled.setIdentifier(PROPERTY_PRECONDITIONING_DEPARTURE_ENABLED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningError Preconditioning error if one is encountered
             * @return The builder
             */
            public Builder setPreconditioningError(Property<PreconditioningError> preconditioningError) {
                Property property = preconditioningError.setIdentifier(PROPERTY_PRECONDITIONING_ERROR);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryCapacity Indicates the battery capacity
             * @return The builder
             */
            public Builder setBatteryCapacity(Property<Energy> batteryCapacity) {
                Property property = batteryCapacity.setIdentifier(PROPERTY_BATTERY_CAPACITY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param auxiliaryPower Auxiliary power used for predictions.
             * @return The builder
             */
            public Builder setAuxiliaryPower(Property<Power> auxiliaryPower) {
                Property property = auxiliaryPower.setIdentifier(PROPERTY_AUXILIARY_POWER);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingCompleteLock Locking status of the charging plug after charging complete.
             * @return The builder
             */
            public Builder setChargingCompleteLock(Property<ActiveState> chargingCompleteLock) {
                Property property = chargingCompleteLock.setIdentifier(PROPERTY_CHARGING_COMPLETE_LOCK);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryMaxAvailable Maximum available energy content of the high-voltage battery.
             * @return The builder
             */
            public Builder setBatteryMaxAvailable(Property<Energy> batteryMaxAvailable) {
                Property property = batteryMaxAvailable.setIdentifier(PROPERTY_BATTERY_MAX_AVAILABLE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingEndReason Reason for ending a charging process.
             * @return The builder
             */
            public Builder setChargingEndReason(Property<ChargingEndReason> chargingEndReason) {
                Property property = chargingEndReason.setIdentifier(PROPERTY_CHARGING_END_REASON);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingPhases Charging process count of the high-voltage battery (phases).
             * @return The builder
             */
            public Builder setChargingPhases(Property<ChargingPhases> chargingPhases) {
                Property property = chargingPhases.setIdentifier(PROPERTY_CHARGING_PHASES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryEnergy Energy content of the high-voltage battery.
             * @return The builder
             */
            public Builder setBatteryEnergy(Property<Energy> batteryEnergy) {
                Property property = batteryEnergy.setIdentifier(PROPERTY_BATTERY_ENERGY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryEnergyChargable Energy required until high-voltage battery is fully charged.
             * @return The builder
             */
            public Builder setBatteryEnergyChargable(Property<Energy> batteryEnergyChargable) {
                Property property = batteryEnergyChargable.setIdentifier(PROPERTY_BATTERY_ENERGY_CHARGABLE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingSingleImmediate Single instant charging function status.
             * @return The builder
             */
            public Builder setChargingSingleImmediate(Property<ActiveState> chargingSingleImmediate) {
                Property property = chargingSingleImmediate.setIdentifier(PROPERTY_CHARGING_SINGLE_IMMEDIATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingTimeDisplay Charging time displayed in the vehicle.
             * @return The builder
             */
            public Builder setChargingTimeDisplay(Property<ChargingTimeDisplay> chargingTimeDisplay) {
                Property property = chargingTimeDisplay.setIdentifier(PROPERTY_CHARGING_TIME_DISPLAY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param departureTimeDisplay Departure time displayed in the vehicle.
             * @return The builder
             */
            public Builder setDepartureTimeDisplay(Property<DepartureTimeDisplay> departureTimeDisplay) {
                Property property = departureTimeDisplay.setIdentifier(PROPERTY_DEPARTURE_TIME_DISPLAY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param restriction Charging limit and state
             * @return The builder
             */
            public Builder setRestriction(Property<ChargingRestriction> restriction) {
                Property property = restriction.setIdentifier(PROPERTY_RESTRICTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param limitStatus Indicates whether charging limit is active.
             * @return The builder
             */
            public Builder setLimitStatus(Property<ActiveState> limitStatus) {
                Property property = limitStatus.setIdentifier(PROPERTY_LIMIT_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param currentLimit Limit for the charging current.
             * @return The builder
             */
            public Builder setCurrentLimit(Property<ElectricCurrent> currentLimit) {
                Property property = currentLimit.setIdentifier(PROPERTY_CURRENT_LIMIT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param smartChargingOption Smart charging option being used to charge with.
             * @return The builder
             */
            public Builder setSmartChargingOption(Property<SmartChargingOption> smartChargingOption) {
                Property property = smartChargingOption.setIdentifier(PROPERTY_SMART_CHARGING_OPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param plugLockStatus Locking status of charging plug.
             * @return The builder
             */
            public Builder setPlugLockStatus(Property<LockState> plugLockStatus) {
                Property property = plugLockStatus.setIdentifier(PROPERTY_PLUG_LOCK_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param flapLockStatus Locking status of charging flap.
             * @return The builder
             */
            public Builder setFlapLockStatus(Property<LockState> flapLockStatus) {
                Property property = flapLockStatus.setIdentifier(PROPERTY_FLAP_LOCK_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param acousticLimit Acoustic limitation of charging process.
             * @return The builder
             */
            public Builder setAcousticLimit(Property<AcousticLimit> acousticLimit) {
                Property property = acousticLimit.setIdentifier(PROPERTY_ACOUSTIC_LIMIT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param minChargingCurrent Minimum charging current.
             * @return The builder
             */
            public Builder setMinChargingCurrent(Property<ElectricCurrent> minChargingCurrent) {
                Property property = minChargingCurrent.setIdentifier(PROPERTY_MIN_CHARGING_CURRENT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param estimatedRangeTarget Remaining electric range depending on target charging status.
             * @return The builder
             */
            public Builder setEstimatedRangeTarget(Property<Length> estimatedRangeTarget) {
                Property property = estimatedRangeTarget.setIdentifier(PROPERTY_ESTIMATED_RANGE_TARGET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param fullyChargedEndTimes Time and weekday when the vehicle will be fully charged.
             * @return The builder
             */
            public Builder setFullyChargedEndTimes(Property<WeekdayTime> fullyChargedEndTimes) {
                Property property = fullyChargedEndTimes.setIdentifier(PROPERTY_FULLY_CHARGED_END_TIMES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningScheduledTime Preconditioning scheduled departure time.
             * @return The builder
             */
            public Builder setPreconditioningScheduledTime(Property<Time> preconditioningScheduledTime) {
                Property property = preconditioningScheduledTime.setIdentifier(PROPERTY_PRECONDITIONING_SCHEDULED_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningRemainingTime Time until preconditioning is complete.
             * @return The builder
             */
            public Builder setPreconditioningRemainingTime(Property<Duration> preconditioningRemainingTime) {
                Property property = preconditioningRemainingTime.setIdentifier(PROPERTY_PRECONDITIONING_REMAINING_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryVoltage High-voltage battery electric potential difference (aka voltage).
             * @return The builder
             */
            public Builder setBatteryVoltage(Property<ElectricPotentialDifference> batteryVoltage) {
                Property property = batteryVoltage.setIdentifier(PROPERTY_BATTERY_VOLTAGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryTempretatureExtremes Current highest-lowest temperature inside the battery.
             * @return The builder
             * @deprecated fixed the name typo. Replaced by {@link #getBatteryTemperatureExtremes()}
             */
            @Deprecated
            public Builder setBatteryTempretatureExtremes(Property<TemperatureExtreme> batteryTempretatureExtremes) {
                Property property = batteryTempretatureExtremes.setIdentifier(PROPERTY_BATTERY_TEMPRETATURE_EXTREMES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryTemperatureControlDemand Current demand of HV battery temperature control system.
             * @return The builder
             */
            public Builder setBatteryTemperatureControlDemand(Property<BatteryTemperatureControlDemand> batteryTemperatureControlDemand) {
                Property property = batteryTemperatureControlDemand.setIdentifier(PROPERTY_BATTERY_TEMPERATURE_CONTROL_DEMAND);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingCurrent Charging electric current.
             * @return The builder
             */
            public Builder setChargingCurrent(Property<ElectricCurrent> chargingCurrent) {
                Property property = chargingCurrent.setIdentifier(PROPERTY_CHARGING_CURRENT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryStatus Battery state.
             * @return The builder
             */
            public Builder setBatteryStatus(Property<BatteryStatus> batteryStatus) {
                Property property = batteryStatus.setIdentifier(PROPERTY_BATTERY_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryLed State of LED for the battery.
             * @return The builder
             */
            public Builder setBatteryLed(Property<BatteryLed> batteryLed) {
                Property property = batteryLed.setIdentifier(PROPERTY_BATTERY_LED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryCoolingTemperature Battery cooling temperature.
             * @return The builder
             */
            public Builder setBatteryCoolingTemperature(Property<Temperature> batteryCoolingTemperature) {
                Property property = batteryCoolingTemperature.setIdentifier(PROPERTY_BATTERY_COOLING_TEMPERATURE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryTemperatureExtremes Current highest-lowest temperature inside the battery.
             * @return The builder
             */
            public Builder setBatteryTemperatureExtremes(Property<TemperatureExtreme> batteryTemperatureExtremes) {
                Property property = batteryTemperatureExtremes.setIdentifier(PROPERTY_BATTERY_TEMPERATURE_EXTREMES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param drivingModePHEV Indicates the current driving mode for Plug-In Hybrid Vehicle.
             * @return The builder
             */
            public Builder setDrivingModePHEV(Property<DrivingModePhev> drivingModePHEV) {
                Property property = drivingModePHEV.setIdentifier(PROPERTY_DRIVING_MODE_PHEV);
                addProperty(property);
                return this;
            }
            
            /**
             * @param batteryChargeType Battery charge type.
             * @return The builder
             */
            public Builder setBatteryChargeType(Property<BatteryChargeType> batteryChargeType) {
                Property property = batteryChargeType.setIdentifier(PROPERTY_BATTERY_CHARGE_TYPE);
                addProperty(property);
                return this;
            }
        }
    }

    public enum ChargeMode implements ByteEnum {
        IMMEDIATE((byte) 0x00),
        TIMER_BASED((byte) 0x01),
        INDUCTIVE((byte) 0x02),
        CONDUCTIVE((byte) 0x03),
        PUSH_BUTTON((byte) 0x04);
    
        public static ChargeMode fromByte(byte byteValue) throws CommandParseException {
            ChargeMode[] values = ChargeMode.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargeMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ChargeMode.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ChargeMode(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum PlugType implements ByteEnum {
        TYPE_1((byte) 0x00),
        TYPE_2((byte) 0x01),
        CCS((byte) 0x02),
        CHADEMO((byte) 0x03);
    
        public static PlugType fromByte(byte byteValue) throws CommandParseException {
            PlugType[] values = PlugType.values();
    
            for (int i = 0; i < values.length; i++) {
                PlugType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(PlugType.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        PlugType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum ChargingWindowChosen implements ByteEnum {
        NOT_CHOSEN((byte) 0x00),
        CHOSEN((byte) 0x01);
    
        public static ChargingWindowChosen fromByte(byte byteValue) throws CommandParseException {
            ChargingWindowChosen[] values = ChargingWindowChosen.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingWindowChosen state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ChargingWindowChosen.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ChargingWindowChosen(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum PluggedIn implements ByteEnum {
        DISCONNECTED((byte) 0x00),
        PLUGGED_IN((byte) 0x01),
        PLUGGED_IN_BOTH_SIDES((byte) 0x02);
    
        public static PluggedIn fromByte(byte byteValue) throws CommandParseException {
            PluggedIn[] values = PluggedIn.values();
    
            for (int i = 0; i < values.length; i++) {
                PluggedIn state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(PluggedIn.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        PluggedIn(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Status implements ByteEnum {
        NOT_CHARGING((byte) 0x00),
        CHARGING((byte) 0x01),
        CHARGING_COMPLETE((byte) 0x02),
        INITIALISING((byte) 0x03),
        CHARGING_PAUSED((byte) 0x04),
        CHARGING_ERROR((byte) 0x05),
        CABLE_UNPLUGGED((byte) 0x06),
        SLOW_CHARGING((byte) 0x07),
        FAST_CHARGING((byte) 0x08),
        DISCHARGING((byte) 0x09),
        FOREIGN_OBJECT_DETECTED((byte) 0x0a),
        CONDITIONING((byte) 0x0b),
        FLAP_OPEN((byte) 0x0c);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Status.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum CurrentType implements ByteEnum {
        ALTERNATING_CURRENT((byte) 0x00),
        DIRECT_CURRENT((byte) 0x01);
    
        public static CurrentType fromByte(byte byteValue) throws CommandParseException {
            CurrentType[] values = CurrentType.values();
    
            for (int i = 0; i < values.length; i++) {
                CurrentType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(CurrentType.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        CurrentType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum StarterBatteryState implements ByteEnum {
        /**
         * Vehicle engine will not start anymore, battery must be charged using an external system (battery charge is greater than 0%).
         */
        RED((byte) 0x00),
        /**
         * Battery partly discharged, battery should be charged by driving the vehicle to avoid loosing functionality (battery charge is greater than 40%).
         */
        YELLOW((byte) 0x01),
        /**
         * Ok, (battery charge is greater than 70%).
         */
        GREEN((byte) 0x02),
        /**
         * Battery is now in saving mode, remote commands are not possible anymore, battery should be charged by driving the vehicle.
         */
        ORANGE((byte) 0x03),
        /**
         * Battery partly discharged but ok.
         */
        GREEN_YELLOW((byte) 0x04);
    
        public static StarterBatteryState fromByte(byte byteValue) throws CommandParseException {
            StarterBatteryState[] values = StarterBatteryState.values();
    
            for (int i = 0; i < values.length; i++) {
                StarterBatteryState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(StarterBatteryState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        StarterBatteryState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum SmartChargingStatus implements ByteEnum {
        WALLBOX_IS_ACTIVE((byte) 0x00),
        /**
         * Smart Charge Communication is active
         */
        SCC_IS_ACTIVE((byte) 0x01),
        /**
         * On/Off-peak setting is active (charges when electricity is cheaper)
         */
        PEAK_SETTING_ACTIVE((byte) 0x02);
    
        public static SmartChargingStatus fromByte(byte byteValue) throws CommandParseException {
            SmartChargingStatus[] values = SmartChargingStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                SmartChargingStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(SmartChargingStatus.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        SmartChargingStatus(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum PreconditioningError implements ByteEnum {
        NO_CHANGE((byte) 0x00),
        /**
         * Preconditioning not possible because battery or fuel is low
         */
        NOT_POSSIBLE_LOW((byte) 0x01),
        /**
         * Preconditioning not possible because charging is not finished
         */
        NOT_POSSIBLE_FINISHED((byte) 0x02),
        AVAILABLE_AFTER_ENGINE_RESTART((byte) 0x03),
        GENERAL_ERROR((byte) 0x04);
    
        public static PreconditioningError fromByte(byte byteValue) throws CommandParseException {
            PreconditioningError[] values = PreconditioningError.values();
    
            for (int i = 0; i < values.length; i++) {
                PreconditioningError state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(PreconditioningError.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        PreconditioningError(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum ChargingEndReason implements ByteEnum {
        UNKNOWN((byte) 0x00),
        GOAL_REACHED((byte) 0x01),
        REQUESTED_BY_DRIVER((byte) 0x02),
        CONNECTOR_REMOVED((byte) 0x03),
        POWERGRID_FAILED((byte) 0x04),
        HV_SYSTEM_FAILURE((byte) 0x05),
        CHARGING_STATION_FAILURE((byte) 0x06),
        PARKING_LOCK_FAILED((byte) 0x07),
        NO_PARKING_LOCK((byte) 0x08),
        SIGNAL_INVALID((byte) 0x09);
    
        public static ChargingEndReason fromByte(byte byteValue) throws CommandParseException {
            ChargingEndReason[] values = ChargingEndReason.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingEndReason state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ChargingEndReason.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ChargingEndReason(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum ChargingPhases implements ByteEnum {
        NO_CHARGING((byte) 0x00),
        ONE((byte) 0x01),
        TWO((byte) 0x02),
        THREE((byte) 0x03);
    
        public static ChargingPhases fromByte(byte byteValue) throws CommandParseException {
            ChargingPhases[] values = ChargingPhases.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingPhases state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ChargingPhases.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ChargingPhases(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum ChargingTimeDisplay implements ByteEnum {
        NO_DISPLAY((byte) 0x00),
        DISPLAY_DURATION((byte) 0x01),
        NO_DISPLAY_DURATION((byte) 0x02);
    
        public static ChargingTimeDisplay fromByte(byte byteValue) throws CommandParseException {
            ChargingTimeDisplay[] values = ChargingTimeDisplay.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingTimeDisplay state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ChargingTimeDisplay.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ChargingTimeDisplay(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum DepartureTimeDisplay implements ByteEnum {
        NO_DISPLAY((byte) 0x00),
        REACHABLE((byte) 0x01),
        NOT_REACHABLE((byte) 0x02);
    
        public static DepartureTimeDisplay fromByte(byte byteValue) throws CommandParseException {
            DepartureTimeDisplay[] values = DepartureTimeDisplay.values();
    
            for (int i = 0; i < values.length; i++) {
                DepartureTimeDisplay state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(DepartureTimeDisplay.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        DepartureTimeDisplay(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum SmartChargingOption implements ByteEnum {
        PRICE_OPTIMIZED((byte) 0x00),
        RENEWABLE_ENERGY((byte) 0x01),
        CO2_OPTIMIZED((byte) 0x02);
    
        public static SmartChargingOption fromByte(byte byteValue) throws CommandParseException {
            SmartChargingOption[] values = SmartChargingOption.values();
    
            for (int i = 0; i < values.length; i++) {
                SmartChargingOption state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(SmartChargingOption.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        SmartChargingOption(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum AcousticLimit implements ByteEnum {
        NO_ACTION((byte) 0x00),
        AUTOMATIC((byte) 0x01),
        UNLIMITED((byte) 0x02),
        LIMITED((byte) 0x03);
    
        public static AcousticLimit fromByte(byte byteValue) throws CommandParseException {
            AcousticLimit[] values = AcousticLimit.values();
    
            for (int i = 0; i < values.length; i++) {
                AcousticLimit state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(AcousticLimit.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        AcousticLimit(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum BatteryTemperatureControlDemand implements ByteEnum {
        HIGH_COOLING((byte) 0x00),
        MEDIUM_COOLING((byte) 0x01),
        LOW_COOLING((byte) 0x02),
        NO_TEMPERATURE_REQUIREMENT((byte) 0x03),
        LOW_HEATING((byte) 0x04),
        MEDIUM_HEATING((byte) 0x05),
        HIGH_HEATING((byte) 0x06),
        CIRCULATION_REQUIREMENT((byte) 0x07);
    
        public static BatteryTemperatureControlDemand fromByte(byte byteValue) throws CommandParseException {
            BatteryTemperatureControlDemand[] values = BatteryTemperatureControlDemand.values();
    
            for (int i = 0; i < values.length; i++) {
                BatteryTemperatureControlDemand state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(BatteryTemperatureControlDemand.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        BatteryTemperatureControlDemand(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum BatteryStatus implements ByteEnum {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        BALANCING((byte) 0x02),
        EXTERNAL_LOAD((byte) 0x03),
        LOAD((byte) 0x04),
        ERROR((byte) 0x05),
        INITIALISING((byte) 0x06),
        CONDITIONING((byte) 0x07);
    
        public static BatteryStatus fromByte(byte byteValue) throws CommandParseException {
            BatteryStatus[] values = BatteryStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                BatteryStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(BatteryStatus.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        BatteryStatus(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum BatteryLed implements ByteEnum {
        NO_COLOUR((byte) 0x00),
        WHITE((byte) 0x01),
        YELLOW((byte) 0x02),
        GREEN((byte) 0x03),
        RED((byte) 0x04),
        YELLOW_PULSING((byte) 0x05),
        GREEN_PULSING((byte) 0x06),
        RED_PULSING((byte) 0x07),
        GREEN_RED_PULSING((byte) 0x08),
        GREEN_FLASHING((byte) 0x09),
        INITIALISING((byte) 0x0a),
        ERROR((byte) 0x0b);
    
        public static BatteryLed fromByte(byte byteValue) throws CommandParseException {
            BatteryLed[] values = BatteryLed.values();
    
            for (int i = 0; i < values.length; i++) {
                BatteryLed state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(BatteryLed.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        BatteryLed(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum BatteryChargeType implements ByteEnum {
        NO_CHARGE((byte) 0x00),
        NORMAL((byte) 0x01),
        ACCELERATED((byte) 0x02),
        FAST((byte) 0x03),
        QUICK((byte) 0x04),
        ULTRA_FAST((byte) 0x05),
        NOT_USED((byte) 0x06),
        VEHICLE_TO_HOME((byte) 0x07),
        VEHICLE_TO_GRID((byte) 0x08);
    
        public static BatteryChargeType fromByte(byte byteValue) throws CommandParseException {
            BatteryChargeType[] values = BatteryChargeType.values();
    
            for (int i = 0; i < values.length; i++) {
                BatteryChargeType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(BatteryChargeType.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        BatteryChargeType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}