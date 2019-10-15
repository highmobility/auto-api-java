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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.DepartureTime;
import com.highmobility.autoapi.value.ReductionTime;
import com.highmobility.autoapi.value.Timer;
import java.util.ArrayList;
import java.util.List;

/**
 * The charging state
 */
public class ChargingState extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.CHARGING;

    public static final byte IDENTIFIER_ESTIMATED_RANGE = 0x02;
    public static final byte IDENTIFIER_BATTERY_LEVEL = 0x03;
    public static final byte IDENTIFIER_BATTERY_CURRENT_AC = 0x04;
    public static final byte IDENTIFIER_BATTERY_CURRENT_DC = 0x05;
    public static final byte IDENTIFIER_CHARGER_VOLTAGE_AC = 0x06;
    public static final byte IDENTIFIER_CHARGER_VOLTAGE_DC = 0x07;
    public static final byte IDENTIFIER_CHARGE_LIMIT = 0x08;
    public static final byte IDENTIFIER_TIME_TO_COMPLETE_CHARGE = 0x09;
    public static final byte IDENTIFIER_CHARGING_RATE_KW = 0x0a;
    public static final byte IDENTIFIER_CHARGE_PORT_STATE = 0x0b;
    public static final byte IDENTIFIER_CHARGE_MODE = 0x0c;
    public static final byte IDENTIFIER_MAX_CHARGING_CURRENT = 0x0e;
    public static final byte IDENTIFIER_PLUG_TYPE = 0x0f;
    public static final byte IDENTIFIER_CHARGING_WINDOW_CHOSEN = 0x10;
    public static final byte IDENTIFIER_DEPARTURE_TIMES = 0x11;
    public static final byte IDENTIFIER_REDUCTION_TIMES = 0x13;
    public static final byte IDENTIFIER_BATTERY_TEMPERATURE = 0x14;
    public static final byte IDENTIFIER_TIMERS = 0x15;
    public static final byte IDENTIFIER_PLUGGED_IN = 0x16;
    public static final byte IDENTIFIER_STATUS = 0x17;

    PropertyInteger estimatedRange = new PropertyInteger(IDENTIFIER_ESTIMATED_RANGE, false);
    Property<Double> batteryLevel = new Property(Double.class, IDENTIFIER_BATTERY_LEVEL);
    Property<Float> batteryCurrentAC = new Property(Float.class, IDENTIFIER_BATTERY_CURRENT_AC);
    Property<Float> batteryCurrentDC = new Property(Float.class, IDENTIFIER_BATTERY_CURRENT_DC);
    Property<Float> chargerVoltageAC = new Property(Float.class, IDENTIFIER_CHARGER_VOLTAGE_AC);
    Property<Float> chargerVoltageDC = new Property(Float.class, IDENTIFIER_CHARGER_VOLTAGE_DC);
    Property<Double> chargeLimit = new Property(Double.class, IDENTIFIER_CHARGE_LIMIT);
    PropertyInteger timeToCompleteCharge = new PropertyInteger(IDENTIFIER_TIME_TO_COMPLETE_CHARGE, false);
    Property<Float> chargingRateKW = new Property(Float.class, IDENTIFIER_CHARGING_RATE_KW);
    Property<Position> chargePortState = new Property(Position.class, IDENTIFIER_CHARGE_PORT_STATE);
    Property<ChargeMode> chargeMode = new Property(ChargeMode.class, IDENTIFIER_CHARGE_MODE);
    Property<Float> maxChargingCurrent = new Property(Float.class, IDENTIFIER_MAX_CHARGING_CURRENT);
    Property<PlugType> plugType = new Property(PlugType.class, IDENTIFIER_PLUG_TYPE);
    Property<ChargingWindowChosen> chargingWindowChosen = new Property(ChargingWindowChosen.class, IDENTIFIER_CHARGING_WINDOW_CHOSEN);
    Property<DepartureTime>[] departureTimes;
    Property<ReductionTime>[] reductionTimes;
    Property<Float> batteryTemperature = new Property(Float.class, IDENTIFIER_BATTERY_TEMPERATURE);
    Property<Timer>[] timers;
    Property<PluggedIn> pluggedIn = new Property(PluggedIn.class, IDENTIFIER_PLUGGED_IN);
    Property<Status> status = new Property(Status.class, IDENTIFIER_STATUS);

    /**
     * @return Estimated range in km
     */
    public PropertyInteger getEstimatedRange() {
        return estimatedRange;
    }

    /**
     * @return Battery level percentage between 0.0-1.0
     */
    public Property<Double> getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return Battery active current
     */
    public Property<Float> getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     * @return Battery direct current
     */
    public Property<Float> getBatteryCurrentDC() {
        return batteryCurrentDC;
    }

    /**
     * @return Charger voltage
     */
    public Property<Float> getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     * @return Charger voltage
     */
    public Property<Float> getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     * @return Charge limit percentage between 0.0-1.0
     */
    public Property<Double> getChargeLimit() {
        return chargeLimit;
    }

    /**
     * @return Time until charging completed in minutes
     */
    public PropertyInteger getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     * @return Charge rate in kW when charging
     */
    public Property<Float> getChargingRateKW() {
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
    public Property<Float> getMaxChargingCurrent() {
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
     * @return Battery temperature in Celsius
     */
    public Property<Float> getBatteryTemperature() {
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

    ChargingState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> departureTimesBuilder = new ArrayList<>();
        ArrayList<Property> reductionTimesBuilder = new ArrayList<>();
        ArrayList<Property> timersBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ESTIMATED_RANGE: return estimatedRange.update(p);
                    case IDENTIFIER_BATTERY_LEVEL: return batteryLevel.update(p);
                    case IDENTIFIER_BATTERY_CURRENT_AC: return batteryCurrentAC.update(p);
                    case IDENTIFIER_BATTERY_CURRENT_DC: return batteryCurrentDC.update(p);
                    case IDENTIFIER_CHARGER_VOLTAGE_AC: return chargerVoltageAC.update(p);
                    case IDENTIFIER_CHARGER_VOLTAGE_DC: return chargerVoltageDC.update(p);
                    case IDENTIFIER_CHARGE_LIMIT: return chargeLimit.update(p);
                    case IDENTIFIER_TIME_TO_COMPLETE_CHARGE: return timeToCompleteCharge.update(p);
                    case IDENTIFIER_CHARGING_RATE_KW: return chargingRateKW.update(p);
                    case IDENTIFIER_CHARGE_PORT_STATE: return chargePortState.update(p);
                    case IDENTIFIER_CHARGE_MODE: return chargeMode.update(p);
                    case IDENTIFIER_MAX_CHARGING_CURRENT: return maxChargingCurrent.update(p);
                    case IDENTIFIER_PLUG_TYPE: return plugType.update(p);
                    case IDENTIFIER_CHARGING_WINDOW_CHOSEN: return chargingWindowChosen.update(p);
                    case IDENTIFIER_DEPARTURE_TIMES:
                        Property<DepartureTime> departureTime = new Property(DepartureTime.class, p);
                        departureTimesBuilder.add(departureTime);
                        return departureTime;
                    case IDENTIFIER_REDUCTION_TIMES:
                        Property<ReductionTime> reductionTime = new Property(ReductionTime.class, p);
                        reductionTimesBuilder.add(reductionTime);
                        return reductionTime;
                    case IDENTIFIER_BATTERY_TEMPERATURE: return batteryTemperature.update(p);
                    case IDENTIFIER_TIMERS:
                        Property<Timer> timer = new Property(Timer.class, p);
                        timersBuilder.add(timer);
                        return timer;
                    case IDENTIFIER_PLUGGED_IN: return pluggedIn.update(p);
                    case IDENTIFIER_STATUS: return status.update(p);
                }

                return null;
            });
        }

        departureTimes = departureTimesBuilder.toArray(new Property[0]);
        reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
        timers = timersBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChargingState(Builder builder) {
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
    }

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger estimatedRange;
        private Property<Double> batteryLevel;
        private Property<Float> batteryCurrentAC;
        private Property<Float> batteryCurrentDC;
        private Property<Float> chargerVoltageAC;
        private Property<Float> chargerVoltageDC;
        private Property<Double> chargeLimit;
        private PropertyInteger timeToCompleteCharge;
        private Property<Float> chargingRateKW;
        private Property<Position> chargePortState;
        private Property<ChargeMode> chargeMode;
        private Property<Float> maxChargingCurrent;
        private Property<PlugType> plugType;
        private Property<ChargingWindowChosen> chargingWindowChosen;
        private List<Property> departureTimes = new ArrayList<>();
        private List<Property> reductionTimes = new ArrayList<>();
        private Property<Float> batteryTemperature;
        private List<Property> timers = new ArrayList<>();
        private Property<PluggedIn> pluggedIn;
        private Property<Status> status;

        public Builder() {
            super(IDENTIFIER);
        }

        public ChargingState build() {
            return new ChargingState(this);
        }

        /**
         * @param estimatedRange Estimated range in km
         * @return The builder
         */
        public Builder setEstimatedRange(Property<Integer> estimatedRange) {
            this.estimatedRange = new PropertyInteger(IDENTIFIER_ESTIMATED_RANGE, false, 2, estimatedRange);
            addProperty(this.estimatedRange);
            return this;
        }
        
        /**
         * @param batteryLevel Battery level percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setBatteryLevel(Property<Double> batteryLevel) {
            this.batteryLevel = batteryLevel.setIdentifier(IDENTIFIER_BATTERY_LEVEL);
            addProperty(this.batteryLevel);
            return this;
        }
        
        /**
         * @param batteryCurrentAC Battery active current
         * @return The builder
         */
        public Builder setBatteryCurrentAC(Property<Float> batteryCurrentAC) {
            this.batteryCurrentAC = batteryCurrentAC.setIdentifier(IDENTIFIER_BATTERY_CURRENT_AC);
            addProperty(this.batteryCurrentAC);
            return this;
        }
        
        /**
         * @param batteryCurrentDC Battery direct current
         * @return The builder
         */
        public Builder setBatteryCurrentDC(Property<Float> batteryCurrentDC) {
            this.batteryCurrentDC = batteryCurrentDC.setIdentifier(IDENTIFIER_BATTERY_CURRENT_DC);
            addProperty(this.batteryCurrentDC);
            return this;
        }
        
        /**
         * @param chargerVoltageAC Charger voltage
         * @return The builder
         */
        public Builder setChargerVoltageAC(Property<Float> chargerVoltageAC) {
            this.chargerVoltageAC = chargerVoltageAC.setIdentifier(IDENTIFIER_CHARGER_VOLTAGE_AC);
            addProperty(this.chargerVoltageAC);
            return this;
        }
        
        /**
         * @param chargerVoltageDC Charger voltage
         * @return The builder
         */
        public Builder setChargerVoltageDC(Property<Float> chargerVoltageDC) {
            this.chargerVoltageDC = chargerVoltageDC.setIdentifier(IDENTIFIER_CHARGER_VOLTAGE_DC);
            addProperty(this.chargerVoltageDC);
            return this;
        }
        
        /**
         * @param chargeLimit Charge limit percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setChargeLimit(Property<Double> chargeLimit) {
            this.chargeLimit = chargeLimit.setIdentifier(IDENTIFIER_CHARGE_LIMIT);
            addProperty(this.chargeLimit);
            return this;
        }
        
        /**
         * @param timeToCompleteCharge Time until charging completed in minutes
         * @return The builder
         */
        public Builder setTimeToCompleteCharge(Property<Integer> timeToCompleteCharge) {
            this.timeToCompleteCharge = new PropertyInteger(IDENTIFIER_TIME_TO_COMPLETE_CHARGE, false, 1, timeToCompleteCharge);
            addProperty(this.timeToCompleteCharge);
            return this;
        }
        
        /**
         * @param chargingRateKW Charge rate in kW when charging
         * @return The builder
         */
        public Builder setChargingRateKW(Property<Float> chargingRateKW) {
            this.chargingRateKW = chargingRateKW.setIdentifier(IDENTIFIER_CHARGING_RATE_KW);
            addProperty(this.chargingRateKW);
            return this;
        }
        
        /**
         * @param chargePortState The charge port state
         * @return The builder
         */
        public Builder setChargePortState(Property<Position> chargePortState) {
            this.chargePortState = chargePortState.setIdentifier(IDENTIFIER_CHARGE_PORT_STATE);
            addProperty(this.chargePortState);
            return this;
        }
        
        /**
         * @param chargeMode The charge mode
         * @return The builder
         */
        public Builder setChargeMode(Property<ChargeMode> chargeMode) {
            this.chargeMode = chargeMode.setIdentifier(IDENTIFIER_CHARGE_MODE);
            addProperty(this.chargeMode);
            return this;
        }
        
        /**
         * @param maxChargingCurrent Maximum charging current
         * @return The builder
         */
        public Builder setMaxChargingCurrent(Property<Float> maxChargingCurrent) {
            this.maxChargingCurrent = maxChargingCurrent.setIdentifier(IDENTIFIER_MAX_CHARGING_CURRENT);
            addProperty(this.maxChargingCurrent);
            return this;
        }
        
        /**
         * @param plugType The plug type
         * @return The builder
         */
        public Builder setPlugType(Property<PlugType> plugType) {
            this.plugType = plugType.setIdentifier(IDENTIFIER_PLUG_TYPE);
            addProperty(this.plugType);
            return this;
        }
        
        /**
         * @param chargingWindowChosen The charging window chosen
         * @return The builder
         */
        public Builder setChargingWindowChosen(Property<ChargingWindowChosen> chargingWindowChosen) {
            this.chargingWindowChosen = chargingWindowChosen.setIdentifier(IDENTIFIER_CHARGING_WINDOW_CHOSEN);
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
            departureTime.setIdentifier(IDENTIFIER_DEPARTURE_TIMES);
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
            reductionTime.setIdentifier(IDENTIFIER_REDUCTION_TIMES);
            addProperty(reductionTime);
            reductionTimes.add(reductionTime);
            return this;
        }
        
        /**
         * @param batteryTemperature Battery temperature in Celsius
         * @return The builder
         */
        public Builder setBatteryTemperature(Property<Float> batteryTemperature) {
            this.batteryTemperature = batteryTemperature.setIdentifier(IDENTIFIER_BATTERY_TEMPERATURE);
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
            timer.setIdentifier(IDENTIFIER_TIMERS);
            addProperty(timer);
            timers.add(timer);
            return this;
        }
        
        /**
         * @param pluggedIn The plugged in
         * @return The builder
         */
        public Builder setPluggedIn(Property<PluggedIn> pluggedIn) {
            this.pluggedIn = pluggedIn.setIdentifier(IDENTIFIER_PLUGGED_IN);
            addProperty(this.pluggedIn);
            return this;
        }
        
        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<Status> status) {
            this.status = status.setIdentifier(IDENTIFIER_STATUS);
            addProperty(this.status);
            return this;
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
        CHARGING_ERROR((byte) 0x05);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}