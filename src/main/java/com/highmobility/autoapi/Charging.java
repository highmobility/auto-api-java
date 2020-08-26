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
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.ElectricCurrent;
import com.highmobility.autoapi.value.measurement.ElectricPotentialDifference;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.Power;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.DepartureTime;
import com.highmobility.autoapi.value.ReductionTime;
import com.highmobility.autoapi.value.measurement.Temperature;
import com.highmobility.autoapi.value.Timer;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.EnabledState;
import java.util.ArrayList;
import java.util.List;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

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

    /**
     * Get all charging properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific charging properties
     */
    public static class GetProperties extends GetCommand {
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
     * The charging state
     */
    public static class State extends SetCommand {
        Property<Length> estimatedRange = new Property(Length.class, PROPERTY_ESTIMATED_RANGE);
        Property<Double> batteryLevel = new Property(Double.class, PROPERTY_BATTERY_LEVEL);
        Property<ElectricCurrent> batteryCurrentAC = new Property(ElectricCurrent.class, PROPERTY_BATTERY_CURRENT_AC);
        Property<ElectricCurrent> batteryCurrentDC = new Property(ElectricCurrent.class, PROPERTY_BATTERY_CURRENT_DC);
        Property<ElectricPotentialDifference> chargerVoltageAC = new Property(ElectricPotentialDifference.class, PROPERTY_CHARGER_VOLTAGE_AC);
        Property<ElectricPotentialDifference> chargerVoltageDC = new Property(ElectricPotentialDifference.class, PROPERTY_CHARGER_VOLTAGE_DC);
        Property<Double> chargeLimit = new Property(Double.class, PROPERTY_CHARGE_LIMIT);
        Property<Duration> timeToCompleteCharge = new Property(Duration.class, PROPERTY_TIME_TO_COMPLETE_CHARGE);
        Property<Power> chargingRateKW = new Property(Power.class, PROPERTY_CHARGING_RATE_KW);
        Property<Position> chargePortState = new Property(Position.class, PROPERTY_CHARGE_PORT_STATE);
        Property<ChargeMode> chargeMode = new Property(ChargeMode.class, PROPERTY_CHARGE_MODE);
        Property<ElectricCurrent> maxChargingCurrent = new Property(ElectricCurrent.class, PROPERTY_MAX_CHARGING_CURRENT);
        Property<PlugType> plugType = new Property(PlugType.class, PROPERTY_PLUG_TYPE);
        Property<ChargingWindowChosen> chargingWindowChosen = new Property(ChargingWindowChosen.class, PROPERTY_CHARGING_WINDOW_CHOSEN);
        Property<DepartureTime>[] departureTimes;
        Property<ReductionTime>[] reductionTimes;
        Property<Temperature> batteryTemperature = new Property(Temperature.class, PROPERTY_BATTERY_TEMPERATURE);
        Property<Timer>[] timers;
        Property<PluggedIn> pluggedIn = new Property(PluggedIn.class, PROPERTY_PLUGGED_IN);
        Property<Status> status = new Property(Status.class, PROPERTY_STATUS);
        Property<Power> chargingRate = new Property(Power.class, PROPERTY_CHARGING_RATE);
        Property<ElectricCurrent> batteryCurrent = new Property(ElectricCurrent.class, PROPERTY_BATTERY_CURRENT);
        Property<ElectricPotentialDifference> chargerVoltage = new Property(ElectricPotentialDifference.class, PROPERTY_CHARGER_VOLTAGE);
        Property<CurrentType> currentType = new Property(CurrentType.class, PROPERTY_CURRENT_TYPE);
        Property<Length> maxRange = new Property(Length.class, PROPERTY_MAX_RANGE);
        Property<StarterBatteryState> starterBatteryState = new Property(StarterBatteryState.class, PROPERTY_STARTER_BATTERY_STATE);
        Property<SmartChargingStatus> smartChargingStatus = new Property(SmartChargingStatus.class, PROPERTY_SMART_CHARGING_STATUS);
        Property<Double> batteryLevelAtDeparture = new Property(Double.class, PROPERTY_BATTERY_LEVEL_AT_DEPARTURE);
        Property<ActiveState> preconditioningDepartureStatus = new Property(ActiveState.class, PROPERTY_PRECONDITIONING_DEPARTURE_STATUS);
        Property<ActiveState> preconditioningImmediateStatus = new Property(ActiveState.class, PROPERTY_PRECONDITIONING_IMMEDIATE_STATUS);
        Property<EnabledState> preconditioningDepartureEnabled = new Property(EnabledState.class, PROPERTY_PRECONDITIONING_DEPARTURE_ENABLED);
        Property<PreconditioningError> preconditioningError = new Property(PreconditioningError.class, PROPERTY_PRECONDITIONING_ERROR);
    
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
         */
        public Property<ElectricCurrent> getBatteryCurrentAC() {
            return batteryCurrentAC;
        }
    
        /**
         * @return Battery direct current
         */
        public Property<ElectricCurrent> getBatteryCurrentDC() {
            return batteryCurrentDC;
        }
    
        /**
         * @return Charger voltage for alternating current
         */
        public Property<ElectricPotentialDifference> getChargerVoltageAC() {
            return chargerVoltageAC;
        }
    
        /**
         * @return Charger voltage for direct current
         */
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
         */
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
        public Property<DepartureTime>[] getDepartureTimes() {
            return departureTimes;
        }
    
        /**
         * @return The reduction times
         */
        public Property<ReductionTime>[] getReductionTimes() {
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
        public Property<Timer>[] getTimers() {
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
         * @return Battery current
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
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> departureTimesBuilder = new ArrayList<>();
            ArrayList<Property> reductionTimesBuilder = new ArrayList<>();
            ArrayList<Property> timersBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
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
                            Property<DepartureTime> departureTime = new Property(DepartureTime.class, p);
                            departureTimesBuilder.add(departureTime);
                            return departureTime;
                        case PROPERTY_REDUCTION_TIMES:
                            Property<ReductionTime> reductionTime = new Property(ReductionTime.class, p);
                            reductionTimesBuilder.add(reductionTime);
                            return reductionTime;
                        case PROPERTY_BATTERY_TEMPERATURE: return batteryTemperature.update(p);
                        case PROPERTY_TIMERS:
                            Property<Timer> timer = new Property(Timer.class, p);
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
                    }
    
                    return null;
                });
            }
    
            departureTimes = departureTimesBuilder.toArray(new Property[0]);
            reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
            timers = timersBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            estimatedRange = builder.estimatedRange;
            batteryLevel = builder.batteryLevel;
            batteryCurrentAC = builder.batteryCurrentAC;
            batteryCurrentDC = builder.batteryCurrentDC;
            chargerVoltageAC = builder.chargerVoltageAC;
            chargerVoltageDC = builder.chargerVoltageDC;
            chargeLimit = builder.chargeLimit;
            timeToCompleteCharge = builder.timeToCompleteCharge;
            chargingRateKW = builder.chargingRateKW;
            chargePortState = builder.chargePortState;
            chargeMode = builder.chargeMode;
            maxChargingCurrent = builder.maxChargingCurrent;
            plugType = builder.plugType;
            chargingWindowChosen = builder.chargingWindowChosen;
            departureTimes = builder.departureTimes.toArray(new Property[0]);
            reductionTimes = builder.reductionTimes.toArray(new Property[0]);
            batteryTemperature = builder.batteryTemperature;
            timers = builder.timers.toArray(new Property[0]);
            pluggedIn = builder.pluggedIn;
            status = builder.status;
            chargingRate = builder.chargingRate;
            batteryCurrent = builder.batteryCurrent;
            chargerVoltage = builder.chargerVoltage;
            currentType = builder.currentType;
            maxRange = builder.maxRange;
            starterBatteryState = builder.starterBatteryState;
            smartChargingStatus = builder.smartChargingStatus;
            batteryLevelAtDeparture = builder.batteryLevelAtDeparture;
            preconditioningDepartureStatus = builder.preconditioningDepartureStatus;
            preconditioningImmediateStatus = builder.preconditioningImmediateStatus;
            preconditioningDepartureEnabled = builder.preconditioningDepartureEnabled;
            preconditioningError = builder.preconditioningError;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Length> estimatedRange;
            private Property<Double> batteryLevel;
            private Property<ElectricCurrent> batteryCurrentAC;
            private Property<ElectricCurrent> batteryCurrentDC;
            private Property<ElectricPotentialDifference> chargerVoltageAC;
            private Property<ElectricPotentialDifference> chargerVoltageDC;
            private Property<Double> chargeLimit;
            private Property<Duration> timeToCompleteCharge;
            private Property<Power> chargingRateKW;
            private Property<Position> chargePortState;
            private Property<ChargeMode> chargeMode;
            private Property<ElectricCurrent> maxChargingCurrent;
            private Property<PlugType> plugType;
            private Property<ChargingWindowChosen> chargingWindowChosen;
            private List<Property> departureTimes = new ArrayList<>();
            private List<Property> reductionTimes = new ArrayList<>();
            private Property<Temperature> batteryTemperature;
            private List<Property> timers = new ArrayList<>();
            private Property<PluggedIn> pluggedIn;
            private Property<Status> status;
            private Property<Power> chargingRate;
            private Property<ElectricCurrent> batteryCurrent;
            private Property<ElectricPotentialDifference> chargerVoltage;
            private Property<CurrentType> currentType;
            private Property<Length> maxRange;
            private Property<StarterBatteryState> starterBatteryState;
            private Property<SmartChargingStatus> smartChargingStatus;
            private Property<Double> batteryLevelAtDeparture;
            private Property<ActiveState> preconditioningDepartureStatus;
            private Property<ActiveState> preconditioningImmediateStatus;
            private Property<EnabledState> preconditioningDepartureEnabled;
            private Property<PreconditioningError> preconditioningError;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param estimatedRange Estimated range
             * @return The builder
             */
            public Builder setEstimatedRange(Property<Length> estimatedRange) {
                this.estimatedRange = estimatedRange.setIdentifier(PROPERTY_ESTIMATED_RANGE);
                addProperty(this.estimatedRange);
                return this;
            }
            
            /**
             * @param batteryLevel Battery level percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setBatteryLevel(Property<Double> batteryLevel) {
                this.batteryLevel = batteryLevel.setIdentifier(PROPERTY_BATTERY_LEVEL);
                addProperty(this.batteryLevel);
                return this;
            }
            
            /**
             * @param batteryCurrentAC Battery alternating current
             * @return The builder
             */
            public Builder setBatteryCurrentAC(Property<ElectricCurrent> batteryCurrentAC) {
                this.batteryCurrentAC = batteryCurrentAC.setIdentifier(PROPERTY_BATTERY_CURRENT_AC);
                addProperty(this.batteryCurrentAC);
                return this;
            }
            
            /**
             * @param batteryCurrentDC Battery direct current
             * @return The builder
             */
            public Builder setBatteryCurrentDC(Property<ElectricCurrent> batteryCurrentDC) {
                this.batteryCurrentDC = batteryCurrentDC.setIdentifier(PROPERTY_BATTERY_CURRENT_DC);
                addProperty(this.batteryCurrentDC);
                return this;
            }
            
            /**
             * @param chargerVoltageAC Charger voltage for alternating current
             * @return The builder
             */
            public Builder setChargerVoltageAC(Property<ElectricPotentialDifference> chargerVoltageAC) {
                this.chargerVoltageAC = chargerVoltageAC.setIdentifier(PROPERTY_CHARGER_VOLTAGE_AC);
                addProperty(this.chargerVoltageAC);
                return this;
            }
            
            /**
             * @param chargerVoltageDC Charger voltage for direct current
             * @return The builder
             */
            public Builder setChargerVoltageDC(Property<ElectricPotentialDifference> chargerVoltageDC) {
                this.chargerVoltageDC = chargerVoltageDC.setIdentifier(PROPERTY_CHARGER_VOLTAGE_DC);
                addProperty(this.chargerVoltageDC);
                return this;
            }
            
            /**
             * @param chargeLimit Charge limit percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setChargeLimit(Property<Double> chargeLimit) {
                this.chargeLimit = chargeLimit.setIdentifier(PROPERTY_CHARGE_LIMIT);
                addProperty(this.chargeLimit);
                return this;
            }
            
            /**
             * @param timeToCompleteCharge Time until charging completed
             * @return The builder
             */
            public Builder setTimeToCompleteCharge(Property<Duration> timeToCompleteCharge) {
                this.timeToCompleteCharge = timeToCompleteCharge.setIdentifier(PROPERTY_TIME_TO_COMPLETE_CHARGE);
                addProperty(this.timeToCompleteCharge);
                return this;
            }
            
            /**
             * @param chargingRateKW Charging rate
             * @return The builder
             */
            public Builder setChargingRateKW(Property<Power> chargingRateKW) {
                this.chargingRateKW = chargingRateKW.setIdentifier(PROPERTY_CHARGING_RATE_KW);
                addProperty(this.chargingRateKW);
                return this;
            }
            
            /**
             * @param chargePortState The charge port state
             * @return The builder
             */
            public Builder setChargePortState(Property<Position> chargePortState) {
                this.chargePortState = chargePortState.setIdentifier(PROPERTY_CHARGE_PORT_STATE);
                addProperty(this.chargePortState);
                return this;
            }
            
            /**
             * @param chargeMode The charge mode
             * @return The builder
             */
            public Builder setChargeMode(Property<ChargeMode> chargeMode) {
                this.chargeMode = chargeMode.setIdentifier(PROPERTY_CHARGE_MODE);
                addProperty(this.chargeMode);
                return this;
            }
            
            /**
             * @param maxChargingCurrent Maximum charging current
             * @return The builder
             */
            public Builder setMaxChargingCurrent(Property<ElectricCurrent> maxChargingCurrent) {
                this.maxChargingCurrent = maxChargingCurrent.setIdentifier(PROPERTY_MAX_CHARGING_CURRENT);
                addProperty(this.maxChargingCurrent);
                return this;
            }
            
            /**
             * @param plugType The plug type
             * @return The builder
             */
            public Builder setPlugType(Property<PlugType> plugType) {
                this.plugType = plugType.setIdentifier(PROPERTY_PLUG_TYPE);
                addProperty(this.plugType);
                return this;
            }
            
            /**
             * @param chargingWindowChosen The charging window chosen
             * @return The builder
             */
            public Builder setChargingWindowChosen(Property<ChargingWindowChosen> chargingWindowChosen) {
                this.chargingWindowChosen = chargingWindowChosen.setIdentifier(PROPERTY_CHARGING_WINDOW_CHOSEN);
                addProperty(this.chargingWindowChosen);
                return this;
            }
            
            /**
             * Add an array of departure times.
             * 
             * @param departureTimes The departure times
             * @return The builder
             */
            public Builder setDepartureTimes(Property<DepartureTime>[] departureTimes) {
                this.departureTimes.clear();
                for (int i = 0; i < departureTimes.length; i++) {
                    addDepartureTime(departureTimes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single departure time.
             * 
             * @param departureTime The departure time
             * @return The builder
             */
            public Builder addDepartureTime(Property<DepartureTime> departureTime) {
                departureTime.setIdentifier(PROPERTY_DEPARTURE_TIMES);
                addProperty(departureTime);
                departureTimes.add(departureTime);
                return this;
            }
            
            /**
             * Add an array of reduction times.
             * 
             * @param reductionTimes The reduction times
             * @return The builder
             */
            public Builder setReductionTimes(Property<ReductionTime>[] reductionTimes) {
                this.reductionTimes.clear();
                for (int i = 0; i < reductionTimes.length; i++) {
                    addReductionTime(reductionTimes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single reduction time.
             * 
             * @param reductionTime The reduction time
             * @return The builder
             */
            public Builder addReductionTime(Property<ReductionTime> reductionTime) {
                reductionTime.setIdentifier(PROPERTY_REDUCTION_TIMES);
                addProperty(reductionTime);
                reductionTimes.add(reductionTime);
                return this;
            }
            
            /**
             * @param batteryTemperature Battery temperature
             * @return The builder
             */
            public Builder setBatteryTemperature(Property<Temperature> batteryTemperature) {
                this.batteryTemperature = batteryTemperature.setIdentifier(PROPERTY_BATTERY_TEMPERATURE);
                addProperty(this.batteryTemperature);
                return this;
            }
            
            /**
             * Add an array of timers.
             * 
             * @param timers The timers
             * @return The builder
             */
            public Builder setTimers(Property<Timer>[] timers) {
                this.timers.clear();
                for (int i = 0; i < timers.length; i++) {
                    addTimer(timers[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single timer.
             * 
             * @param timer The timer
             * @return The builder
             */
            public Builder addTimer(Property<Timer> timer) {
                timer.setIdentifier(PROPERTY_TIMERS);
                addProperty(timer);
                timers.add(timer);
                return this;
            }
            
            /**
             * @param pluggedIn The plugged in
             * @return The builder
             */
            public Builder setPluggedIn(Property<PluggedIn> pluggedIn) {
                this.pluggedIn = pluggedIn.setIdentifier(PROPERTY_PLUGGED_IN);
                addProperty(this.pluggedIn);
                return this;
            }
            
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<Status> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param chargingRate Charge rate when charging
             * @return The builder
             */
            public Builder setChargingRate(Property<Power> chargingRate) {
                this.chargingRate = chargingRate.setIdentifier(PROPERTY_CHARGING_RATE);
                addProperty(this.chargingRate);
                return this;
            }
            
            /**
             * @param batteryCurrent Battery current
             * @return The builder
             */
            public Builder setBatteryCurrent(Property<ElectricCurrent> batteryCurrent) {
                this.batteryCurrent = batteryCurrent.setIdentifier(PROPERTY_BATTERY_CURRENT);
                addProperty(this.batteryCurrent);
                return this;
            }
            
            /**
             * @param chargerVoltage Charger voltage
             * @return The builder
             */
            public Builder setChargerVoltage(Property<ElectricPotentialDifference> chargerVoltage) {
                this.chargerVoltage = chargerVoltage.setIdentifier(PROPERTY_CHARGER_VOLTAGE);
                addProperty(this.chargerVoltage);
                return this;
            }
            
            /**
             * @param currentType Type of current in use
             * @return The builder
             */
            public Builder setCurrentType(Property<CurrentType> currentType) {
                this.currentType = currentType.setIdentifier(PROPERTY_CURRENT_TYPE);
                addProperty(this.currentType);
                return this;
            }
            
            /**
             * @param maxRange Maximum electric range with 100% of battery
             * @return The builder
             */
            public Builder setMaxRange(Property<Length> maxRange) {
                this.maxRange = maxRange.setIdentifier(PROPERTY_MAX_RANGE);
                addProperty(this.maxRange);
                return this;
            }
            
            /**
             * @param starterBatteryState State of the starter battery
             * @return The builder
             */
            public Builder setStarterBatteryState(Property<StarterBatteryState> starterBatteryState) {
                this.starterBatteryState = starterBatteryState.setIdentifier(PROPERTY_STARTER_BATTERY_STATE);
                addProperty(this.starterBatteryState);
                return this;
            }
            
            /**
             * @param smartChargingStatus Status of optimized/intelligent charging
             * @return The builder
             */
            public Builder setSmartChargingStatus(Property<SmartChargingStatus> smartChargingStatus) {
                this.smartChargingStatus = smartChargingStatus.setIdentifier(PROPERTY_SMART_CHARGING_STATUS);
                addProperty(this.smartChargingStatus);
                return this;
            }
            
            /**
             * @param batteryLevelAtDeparture Battery charge level expected at time of departure
             * @return The builder
             */
            public Builder setBatteryLevelAtDeparture(Property<Double> batteryLevelAtDeparture) {
                this.batteryLevelAtDeparture = batteryLevelAtDeparture.setIdentifier(PROPERTY_BATTERY_LEVEL_AT_DEPARTURE);
                addProperty(this.batteryLevelAtDeparture);
                return this;
            }
            
            /**
             * @param preconditioningDepartureStatus Status of preconditioning at departure time
             * @return The builder
             */
            public Builder setPreconditioningDepartureStatus(Property<ActiveState> preconditioningDepartureStatus) {
                this.preconditioningDepartureStatus = preconditioningDepartureStatus.setIdentifier(PROPERTY_PRECONDITIONING_DEPARTURE_STATUS);
                addProperty(this.preconditioningDepartureStatus);
                return this;
            }
            
            /**
             * @param preconditioningImmediateStatus Status of immediate preconditioning
             * @return The builder
             */
            public Builder setPreconditioningImmediateStatus(Property<ActiveState> preconditioningImmediateStatus) {
                this.preconditioningImmediateStatus = preconditioningImmediateStatus.setIdentifier(PROPERTY_PRECONDITIONING_IMMEDIATE_STATUS);
                addProperty(this.preconditioningImmediateStatus);
                return this;
            }
            
            /**
             * @param preconditioningDepartureEnabled Preconditioning activation status at departure
             * @return The builder
             */
            public Builder setPreconditioningDepartureEnabled(Property<EnabledState> preconditioningDepartureEnabled) {
                this.preconditioningDepartureEnabled = preconditioningDepartureEnabled.setIdentifier(PROPERTY_PRECONDITIONING_DEPARTURE_ENABLED);
                addProperty(this.preconditioningDepartureEnabled);
                return this;
            }
            
            /**
             * @param preconditioningError Preconditioning error if one is encountered
             * @return The builder
             */
            public Builder setPreconditioningError(Property<PreconditioningError> preconditioningError) {
                this.preconditioningError = preconditioningError.setIdentifier(PROPERTY_PRECONDITIONING_ERROR);
                addProperty(this.preconditioningError);
                return this;
            }
        }
    }

    /**
     * Start stop charging
     */
    public static class StartStopCharging extends SetCommand {
        Property<Status> status = new Property(Status.class, PROPERTY_STATUS);
    
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
                status == Status.FOREIGN_OBJECT_DETECTED) throw new IllegalArgumentException();
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        StartStopCharging(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                    }
                    return null;
                });
            }
            if (this.status.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set charge limit
     */
    public static class SetChargeLimit extends SetCommand {
        Property<Double> chargeLimit = new Property(Double.class, PROPERTY_CHARGE_LIMIT);
    
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
    
        SetChargeLimit(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CHARGE_LIMIT: return chargeLimit.update(p);
                    }
                    return null;
                });
            }
            if (this.chargeLimit.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Open close charging port
     */
    public static class OpenCloseChargingPort extends SetCommand {
        Property<Position> chargePortState = new Property(Position.class, PROPERTY_CHARGE_PORT_STATE);
    
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
    
        OpenCloseChargingPort(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CHARGE_PORT_STATE: return chargePortState.update(p);
                    }
                    return null;
                });
            }
            if (this.chargePortState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set charge mode
     */
    public static class SetChargeMode extends SetCommand {
        Property<ChargeMode> chargeMode = new Property(ChargeMode.class, PROPERTY_CHARGE_MODE);
    
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
        
            if (chargeMode == ChargeMode.INDUCTIVE) throw new IllegalArgumentException();
        
            addProperty(this.chargeMode.update(chargeMode));
            createBytes();
        }
    
        SetChargeMode(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CHARGE_MODE: return chargeMode.update(p);
                    }
                    return null;
                });
            }
            if (this.chargeMode.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set charging timers
     */
    public static class SetChargingTimers extends SetCommand {
        Property<Timer>[] timers;
    
        /**
         * @return The timers
         */
        public Property<Timer>[] getTimers() {
            return timers;
        }
        
        /**
         * Set charging timers
         *
         * @param timers The timers
         */
        public SetChargingTimers(Timer[] timers) {
            super(IDENTIFIER);
        
            ArrayList<Property> timersBuilder = new ArrayList<>();
            if (timers != null) {
                for (Timer timer : timers) {
                    Property prop = new Property(0x15, timer);
                    timersBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.timers = timersBuilder.toArray(new Property[0]);
            createBytes();
        }
    
        SetChargingTimers(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            ArrayList<Property<Timer>> timersBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_TIMERS: {
                            Property timer = new Property(Timer.class, p);
                            timersBuilder.add(timer);
                            return timer;
                        }
                    }
                    return null;
                });
            }
        
            timers = timersBuilder.toArray(new Property[0]);
            if (this.timers.length == 0) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Set reduction of charging current times
     */
    public static class SetReductionOfChargingCurrentTimes extends SetCommand {
        Property<ReductionTime>[] reductionTimes;
    
        /**
         * @return The reduction times
         */
        public Property<ReductionTime>[] getReductionTimes() {
            return reductionTimes;
        }
        
        /**
         * Set reduction of charging current times
         *
         * @param reductionTimes The reduction times
         */
        public SetReductionOfChargingCurrentTimes(ReductionTime[] reductionTimes) {
            super(IDENTIFIER);
        
            ArrayList<Property> reductionTimesBuilder = new ArrayList<>();
            if (reductionTimes != null) {
                for (ReductionTime reductionTime : reductionTimes) {
                    Property prop = new Property(0x13, reductionTime);
                    reductionTimesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
            createBytes();
        }
    
        SetReductionOfChargingCurrentTimes(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            ArrayList<Property<ReductionTime>> reductionTimesBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_REDUCTION_TIMES: {
                            Property reductionTime = new Property(ReductionTime.class, p);
                            reductionTimesBuilder.add(reductionTime);
                            return reductionTime;
                        }
                    }
                    return null;
                });
            }
        
            reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
            if (this.reductionTimes.length == 0) 
                throw new NoPropertiesException();
        }
    }

    public enum ChargeMode implements ByteEnum {
        IMMEDIATE((byte) 0x00),
        TIMER_BASED((byte) 0x01),
        INDUCTIVE((byte) 0x02);
    
        public static ChargeMode fromByte(byte byteValue) throws CommandParseException {
            ChargeMode[] values = ChargeMode.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargeMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum ChargeMode does not contain " + hexFromByte(byteValue));
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
    
            throw new CommandParseException("Enum PlugType does not contain " + hexFromByte(byteValue));
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
    
            throw new CommandParseException("Enum ChargingWindowChosen does not contain " + hexFromByte(byteValue));
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
        PLUGGED_IN((byte) 0x01);
    
        public static PluggedIn fromByte(byte byteValue) throws CommandParseException {
            PluggedIn[] values = PluggedIn.values();
    
            for (int i = 0; i < values.length; i++) {
                PluggedIn state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum PluggedIn does not contain " + hexFromByte(byteValue));
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
        FOREIGN_OBJECT_DETECTED((byte) 0x0a);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum Status does not contain " + hexFromByte(byteValue));
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
    
            throw new CommandParseException("Enum CurrentType does not contain " + hexFromByte(byteValue));
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
         * Battery charge is greater than 0%
         */
        RED((byte) 0x00),
        /**
         * Battery charge is greater than 40%
         */
        YELLOW((byte) 0x01),
        /**
         * Battery charge is greater than 70%
         */
        GREEN((byte) 0x02);
    
        public static StarterBatteryState fromByte(byte byteValue) throws CommandParseException {
            StarterBatteryState[] values = StarterBatteryState.values();
    
            for (int i = 0; i < values.length; i++) {
                StarterBatteryState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum StarterBatteryState does not contain " + hexFromByte(byteValue));
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
        INACTIVE((byte) 0x02);
    
        public static SmartChargingStatus fromByte(byte byteValue) throws CommandParseException {
            SmartChargingStatus[] values = SmartChargingStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                SmartChargingStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum SmartChargingStatus does not contain " + hexFromByte(byteValue));
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
    
            throw new CommandParseException("Enum PreconditioningError does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        PreconditioningError(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}