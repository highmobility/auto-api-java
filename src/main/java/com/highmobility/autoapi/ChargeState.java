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
import com.highmobility.autoapi.value.charging.ChargeMode;
import com.highmobility.autoapi.value.charging.ChargePortState;
import com.highmobility.autoapi.value.charging.ChargingState;
import com.highmobility.autoapi.value.charging.ChargingTimer;
import com.highmobility.autoapi.value.charging.DepartureTime;
import com.highmobility.autoapi.value.charging.PlugType;
import com.highmobility.autoapi.value.charging.ReductionTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Charge State command is received by the car or when the car is plugged
 * in, disconnected, starts or stops charging, or when the charge limit is changed.
 */
public class ChargeState extends Command {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x01);

    private static final byte ESTIMATED_RANGE_IDENTIFIER = 0x02;
    private static final byte BATTERY_LEVEL_IDENTIFIER = 0x03;
    private static final byte BATTERY_CURRENT_AC_IDENTIFIER = 0x04;
    private static final byte BATTERY_CURRENT_DC_IDENTIFIER = 0x05;
    private static final byte CHARGER_VOLTAGE_AC_IDENTIFIER = 0x06;
    private static final byte CHARGER_VOLTAGE_DC_IDENTIFIER = 0x07;
    private static final byte CHARGE_LIMIT_IDENTIFIER = 0x08;
    private static final byte TIME_TO_COMPLETE_CHARGE_IDENTIFIER = 0x09;
    private static final byte CHARGING_RATE_IDENTIFIER = 0x0A;
    private static final byte CHARGE_PORT_STATE_IDENTIFIER = 0x0B;
    private static final byte CHARGE_MODE_IDENTIFIER = 0x0C;

    private static final byte MAX_CHARGING_CURRENT_IDENTIFIER = 0x0E;
    private static final byte PLUG_TYPE_IDENTIFIER = 0x0F;
    private static final byte CHARGING_WINDOW_CHOSEN_IDENTIFIER = 0x10;
    private static final byte DEPARTURE_TIMES_IDENTIFIER = 0x11;
    private static final byte REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER = 0x13;
    private static final byte BATTERY_TEMPERATURE_IDENTIFIER = 0x14;
    private static final byte TIMER_IDENTIFIER = 0x15;
    private static final byte PLUGGED_IN_IDENTIFIER = 0x16;

    private static final byte ACTIVE_STATE_IDENTIFIER = 0x17;

    PropertyInteger estimatedRange = new PropertyInteger(ESTIMATED_RANGE_IDENTIFIER,
            false);
    Property<Double> batteryLevel = new Property(Double.class, BATTERY_LEVEL_IDENTIFIER);
    Property<Float> batteryCurrentAC = new Property(Float.class, BATTERY_CURRENT_AC_IDENTIFIER);
    Property<Float> batteryCurrentDC = new Property(Float.class, BATTERY_CURRENT_DC_IDENTIFIER);
    Property<Float> chargerVoltageAC = new Property(Float.class, CHARGER_VOLTAGE_AC_IDENTIFIER);
    Property<Float> chargerVoltageDC = new Property(Float.class, CHARGER_VOLTAGE_DC_IDENTIFIER);
    Property<Double> chargeLimit = new Property(Double.class, CHARGE_LIMIT_IDENTIFIER);
    PropertyInteger timeToCompleteCharge = new PropertyInteger(TIME_TO_COMPLETE_CHARGE_IDENTIFIER
            , false);
    Property<Float> chargingRate = new Property(Float.class, CHARGING_RATE_IDENTIFIER);
    Property<ChargePortState> chargePortState = new Property(ChargePortState.class,
            CHARGE_PORT_STATE_IDENTIFIER);
    Property<ChargeMode> chargeMode = new Property(ChargeMode.class, CHARGE_MODE_IDENTIFIER);
    Property<Float> maxChargingCurrent = new Property(Float.class, MAX_CHARGING_CURRENT_IDENTIFIER);
    Property<PlugType> plugType = new Property(PlugType.class, PLUG_TYPE_IDENTIFIER);
    Property<Boolean> chargingWindowChosen = new Property(Boolean.class,
            CHARGING_WINDOW_CHOSEN_IDENTIFIER);
    Property<DepartureTime>[] departureTimes;
    Property<ReductionTime>[] reductionOfChargingCurrentTimes;
    Property<Float> batteryTemperature = new Property(Float.class, BATTERY_TEMPERATURE_IDENTIFIER);
    Property<ChargingTimer>[] timers;
    Property<Boolean> pluggedIn = new Property(Boolean.class, PLUGGED_IN_IDENTIFIER);
    Property<ChargingState> activeState = new Property(ChargingState.class,
            ACTIVE_STATE_IDENTIFIER);

    /**
     * @return The estimated range in km.
     */
    public Property<Integer> getEstimatedRange() {
        return estimatedRange;
    }

    /**
     * @return The battery level percentage.
     */
    public Property<Double> getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The battery current in AC.
     */
    public Property<Float> getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     * @return The battery current in DC.
     */
    public Property<Float> getBatteryCurrentDC() {
        return batteryCurrentDC;
    }

    /**
     * @return The Charger voltage in AC.
     */
    public Property<Float> getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     * @return The Charger voltage in DC.
     */
    public Property<Float> getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     * @return The Charge limit percentage.
     */
    public Property<Double> getChargeLimit() {
        return chargeLimit;
    }

    /**
     * @return The time to complete the charge in minutes.
     */
    public Property<Integer> getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     * @return The charging rate in kW, when charging.
     */
    public Property<Float> getChargingRate() {
        return chargingRate;
    }

    /**
     * @return The charge port state.
     */
    public Property<ChargePortState> getChargePortState() {
        return chargePortState;
    }

    /**
     * @return The charge mode.
     */
    public Property<ChargeMode> getChargeMode() {
        return chargeMode;
    }

    /**
     * @return The maximum charging current.
     */
    public Property<Float> getMaxChargingCurrent() {
        return maxChargingCurrent;
    }

    /**
     * @return The plug type.
     */
    public Property<PlugType> getPlugType() {
        return plugType;
    }

    /**
     * @return Indication on whether charging window is chosen.
     */
    public Property<Boolean> getChargingWindowChosen() {
        return chargingWindowChosen;
    }

    /**
     * @return The departure times.
     */
    public Property<DepartureTime>[] getDepartureTimes() {
        return departureTimes;
    }

    /**
     * @return The reduction of charging current times.
     */
    public Property<ReductionTime>[] getReductionOfChargingCurrentTimes() {
        return reductionOfChargingCurrentTimes;
    }

    /**
     * @return The battery temperature in Celsius.
     */
    public Property<Float> getBatteryTemperature() {
        return batteryTemperature;
    }

    /**
     * @return The charging timers.
     */
    public Property<ChargingTimer>[] getTimers() {
        return timers;
    }

    /**
     * Get the charge timer for the given type.
     *
     * @param type The charge timer type.
     * @return The charge timer.
     */
    @Nullable public Property<ChargingTimer> getTimer(ChargingTimer.Type type) {
        if (timers != null) {
            for (Property<ChargingTimer> timer : timers) {
                if (timer.getValue() != null && timer.getValue().getType() == type)
                    return timer;
            }
        }
        return null;
    }

    /**
     * @return The plugged in state.
     */
    public Property<Boolean> getPluggedIn() {
        return pluggedIn;
    }

    /**
     * @return The charging active state.
     */
    public Property<ChargingState> getActiveState() {
        return activeState;
    }

    ChargeState(byte[] bytes) {
        super(bytes);

        ArrayList<Property<DepartureTime>> departureTimes = new ArrayList<>();
        ArrayList<Property<ReductionTime>> reductionOfChargingCurrentTimes =
                new ArrayList<>();
        ArrayList<Property<ChargingTimer>> timers = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case ACTIVE_STATE_IDENTIFIER:
                        return activeState.update(p);
                    case ESTIMATED_RANGE_IDENTIFIER:
                        return estimatedRange.update(p);
                    case BATTERY_LEVEL_IDENTIFIER:
                        return batteryLevel.update(p);
                    case BATTERY_CURRENT_AC_IDENTIFIER:
                        return batteryCurrentAC.update(p);
                    case BATTERY_CURRENT_DC_IDENTIFIER:
                        return batteryCurrentDC.update(p);
                    case CHARGER_VOLTAGE_AC_IDENTIFIER:
                        return chargerVoltageAC.update(p);
                    case CHARGER_VOLTAGE_DC_IDENTIFIER:
                        return chargerVoltageDC.update(p);
                    case CHARGE_LIMIT_IDENTIFIER:
                        return chargeLimit.update(p);
                    case TIME_TO_COMPLETE_CHARGE_IDENTIFIER:
                        return timeToCompleteCharge.update(p);
                    case CHARGING_RATE_IDENTIFIER:
                        return chargingRate.update(p);
                    case CHARGE_PORT_STATE_IDENTIFIER:
                        return chargePortState.update(p);
                    case CHARGE_MODE_IDENTIFIER:
                        return chargeMode.update(p);
                    case MAX_CHARGING_CURRENT_IDENTIFIER:
                        return maxChargingCurrent.update(p);
                    case PLUG_TYPE_IDENTIFIER:
                        return plugType.update(p);
                    case CHARGING_WINDOW_CHOSEN_IDENTIFIER:
                        return chargingWindowChosen.update(p);
                    case DEPARTURE_TIMES_IDENTIFIER:
                        Property time = new Property(DepartureTime.class, p);
                        departureTimes.add(time);
                        return time;
                    case REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER:
                        Property time2 = new Property(ReductionTime.class, p);
                        reductionOfChargingCurrentTimes.add(time2);
                        return time2;
                    case BATTERY_TEMPERATURE_IDENTIFIER:
                        batteryTemperature.update(p);
                        return batteryTemperature;
                    case PLUGGED_IN_IDENTIFIER:
                        pluggedIn.update(p);
                        return pluggedIn;
                    case TIMER_IDENTIFIER:
                        Property time3 = new Property(ChargingTimer.class, p);
                        timers.add(time3);
                        return time3;
                }

                return null;
            });
        }

        this.departureTimes = departureTimes.toArray(new Property[0]);
        this.reductionOfChargingCurrentTimes =
                reductionOfChargingCurrentTimes.toArray(new Property[0]);
        this.timers = timers.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChargeState(Builder builder) {
        super(builder);
        estimatedRange = builder.estimatedRange;
        batteryLevel = builder.batteryLevel;
        batteryCurrentAC = builder.batteryCurrentAC;
        batteryCurrentDC = builder.batteryCurrentDC;
        chargerVoltageAC = builder.chargerVoltageAC;
        chargerVoltageDC = builder.chargerVoltageDC;
        chargeLimit = builder.chargeLimit;
        timeToCompleteCharge = builder.timeToCompleteCharge;
        chargingRate = builder.chargingRate;
        chargePortState = builder.chargePortState;
        chargeMode = builder.chargeMode;
        maxChargingCurrent = builder.maxChargingCurrent;
        plugType = builder.plugType;
        chargingWindowChosen = builder.chargingWindowChosen;
        departureTimes = builder.departureTimes.toArray(new Property[0]);
        reductionOfChargingCurrentTimes = builder.reductionOfChargingCurrentTimes.toArray(new
                Property[0]);
        batteryTemperature = builder.batteryTemperature;
        timers = builder.timers.toArray(new Property[0]);
        pluggedIn = builder.pluggedIn;
        activeState = builder.activeState;
    }

    public static final class Builder extends Command.Builder {
        private PropertyInteger estimatedRange;
        private Property<Double> batteryLevel;
        private Property<Float> batteryCurrentAC;
        private Property<Float> batteryCurrentDC;
        private Property<Float> chargerVoltageAC;
        private Property<Float> chargerVoltageDC;

        private Property<Double> chargeLimit;
        private PropertyInteger timeToCompleteCharge;

        private Property<Float> chargingRate;
        private Property<ChargePortState> chargePortState;

        private Property<ChargeMode> chargeMode;
        private Property<Float> maxChargingCurrent;
        private Property<PlugType> plugType;
        private Property<Boolean> chargingWindowChosen;
        private List<Property<DepartureTime>> departureTimes = new ArrayList<>();

        private List<Property<ReductionTime>> reductionOfChargingCurrentTimes = new ArrayList<>();
        private Property<Float> batteryTemperature;
        private List<Property<ChargingTimer>> timers = new ArrayList<>();
        private Property<Boolean> pluggedIn;
        private Property<ChargingState> activeState;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param activeState The charging state.
         * @return The builder.
         */
        public Builder setActiveState(Property<ChargingState> activeState) {
            this.activeState = activeState;
            activeState.setIdentifier(ACTIVE_STATE_IDENTIFIER);
            addProperty(activeState);
            return this;
        }

        /**
         * @param estimatedRange The estimated range in km.
         * @return The builder.
         */
        public Builder setEstimatedRange(Property<Integer> estimatedRange) {
            this.estimatedRange = new PropertyInteger(ESTIMATED_RANGE_IDENTIFIER, false, 2,
                    estimatedRange);
            addProperty(this.estimatedRange);
            return this;
        }

        /**
         * @param batteryLevel The battery level percentage.
         * @return The builder.
         */
        public Builder setBatteryLevel(Property<Double> batteryLevel) {
            this.batteryLevel = batteryLevel;
            batteryLevel.setIdentifier(BATTERY_LEVEL_IDENTIFIER);
            addProperty(batteryLevel);
            return this;
        }

        /**
         * @param batteryCurrentAC The battery current in AC.
         * @return The builder.
         */
        public Builder setBatteryCurrentAC(Property<Float> batteryCurrentAC) {
            this.batteryCurrentAC = batteryCurrentAC;
            batteryCurrentAC.setIdentifier(BATTERY_CURRENT_AC_IDENTIFIER);
            addProperty(batteryCurrentAC);
            return this;
        }

        /**
         * @param batteryCurrentDC The battery current in DC.
         * @return The builder.
         */
        public Builder setBatteryCurrentDC(Property<Float> batteryCurrentDC) {
            this.batteryCurrentDC = batteryCurrentDC;
            batteryCurrentDC.setIdentifier(BATTERY_CURRENT_DC_IDENTIFIER);
            addProperty(batteryCurrentDC);
            return this;
        }

        /**
         * @param chargerVoltageAC The charger voltage in AC.
         * @return The builder.
         */
        public Builder setChargerVoltageAC(Property<Float> chargerVoltageAC) {
            this.chargerVoltageAC = chargerVoltageAC;
            chargerVoltageAC.setIdentifier(CHARGER_VOLTAGE_AC_IDENTIFIER);
            addProperty(chargerVoltageAC);
            return this;
        }

        /**
         * @param chargerVoltageDC The charger voltage in DC.
         * @return The builder.
         */
        public Builder setChargerVoltageDC(Property<Float> chargerVoltageDC) {
            this.chargerVoltageDC = chargerVoltageDC;
            chargerVoltageDC.setIdentifier(CHARGER_VOLTAGE_DC_IDENTIFIER);
            addProperty(chargerVoltageDC);
            return this;
        }

        /**
         * @param chargeLimit The charge limit percentage.
         * @return The builder.
         */
        public Builder setChargeLimit(Property<Double> chargeLimit) {
            this.chargeLimit = chargeLimit;
            chargeLimit.setIdentifier(CHARGE_LIMIT_IDENTIFIER);
            addProperty(chargeLimit);
            return this;
        }

        /**
         * @param timeToCompleteCharge The time to complete the charge in minutes.
         * @return The builder.
         */
        public Builder setTimeToCompleteCharge(Property<Integer> timeToCompleteCharge) {
            this.timeToCompleteCharge = new PropertyInteger(TIME_TO_COMPLETE_CHARGE_IDENTIFIER,
                    false, 1, timeToCompleteCharge);
            addProperty(this.timeToCompleteCharge);
            return this;
        }

        /**
         * @param chargingRate The charge rate in kW, when charging.
         * @return The builder.
         */
        public Builder setChargingRate(Property<Float> chargingRate) {
            this.chargingRate = chargingRate;
            chargingRate.setIdentifier(CHARGING_RATE_IDENTIFIER);
            addProperty(chargingRate);
            return this;
        }

        /**
         * @param chargePortState The charge port state.
         * @return The builder.
         */
        public Builder setChargePortState
        (Property<ChargePortState> chargePortState) {
            this.chargePortState = chargePortState;
            chargePortState.setIdentifier(CHARGE_PORT_STATE_IDENTIFIER);
            addProperty(chargePortState);
            return this;
        }

        /**
         * @param chargeMode The charge mode.
         * @return The builder.
         */
        public Builder setChargeMode(Property<ChargeMode> chargeMode) {
            this.chargeMode = chargeMode;
            chargeMode.setIdentifier(CHARGE_MODE_IDENTIFIER);
            addProperty(chargeMode);
            return this;
        }

        /**
         * @param maxChargingCurrent The max charging current.
         * @return The builder.
         */
        public Builder setMaxChargingCurrent(Property<Float> maxChargingCurrent) {
            this.maxChargingCurrent = maxChargingCurrent;
            maxChargingCurrent.setIdentifier(MAX_CHARGING_CURRENT_IDENTIFIER);
            addProperty(maxChargingCurrent);
            return this;
        }

        /**
         * @param plugType The plug type.
         * @return The builder.
         */
        public Builder setPlugType(Property<PlugType> plugType) {
            this.plugType = plugType;
            plugType.setIdentifier(PLUG_TYPE_IDENTIFIER);
            addProperty(plugType);
            return this;
        }

        /**
         * @param chargingWindowChosen Charging window chosen state.
         * @return The builder.
         */
        public Builder setChargingWindowChosen
        (Property<Boolean> chargingWindowChosen) {
            this.chargingWindowChosen = chargingWindowChosen;
            chargingWindowChosen.setIdentifier(CHARGING_WINDOW_CHOSEN_IDENTIFIER);
            addProperty(chargingWindowChosen);
            return this;
        }

        /**
         * @param departureTimes The departure times.
         * @return The builder.
         */
        public Builder setDepartureTimes(Property<DepartureTime>[] departureTimes) {
            this.departureTimes = Arrays.asList(departureTimes);

            for (int i = 0; i < departureTimes.length; i++) {
                addProperty(departureTimes[i]);
            }

            return this;
        }

        // TBODO: translate

        public Builder addDepartureTime(Property<DepartureTime> departureTime) {
            departureTime.setIdentifier(DEPARTURE_TIMES_IDENTIFIER);
            this.departureTimes.add(departureTime);
            addProperty(departureTime);
            return this;
        }

        public Builder setReductionOfChargingCurrentTimes(Property<ReductionTime>[]
                                                                  reductionOfChargingCurrentTimes) {
            this.reductionOfChargingCurrentTimes.clear();

            for (int i = 0; i < reductionOfChargingCurrentTimes.length; i++) {
                addReductionOfChargingCurrentTime(reductionOfChargingCurrentTimes[i]);
            }

            return this;
        }

        public Builder addReductionOfChargingCurrentTime(Property<ReductionTime>
                                                                 reductionOfChargingCurrentTime) {
            reductionOfChargingCurrentTime.setIdentifier
                    (REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER);
            this.reductionOfChargingCurrentTimes.add(reductionOfChargingCurrentTime);
            addProperty(reductionOfChargingCurrentTime);
            return this;

        }

        public Builder setBatteryTemperature(Property<Float> batteryTemperature) {
            this.batteryTemperature = batteryTemperature;
            batteryTemperature.setIdentifier(BATTERY_TEMPERATURE_IDENTIFIER);
            addProperty(batteryTemperature);
            return this;
        }

        /**
         * @param chargingTimers All of the charge timers.
         * @return The builder.
         */
        public Builder setTimers(Property<ChargingTimer>[] chargingTimers) {
            this.timers.clear();

            for (int i = 0; i < chargingTimers.length; i++) {
                addTimer(chargingTimers[i]);
            }

            return this;
        }

        /**
         * Add a single charge timer.
         *
         * @param timer The charge timer.
         * @return The builder.
         */
        public Builder addTimer(Property<ChargingTimer> timer) {
            this.timers.add(timer);
            timer.setIdentifier(TIMER_IDENTIFIER);
            addProperty(timer);
            return this;
        }

        public Builder setPluggedIn(Property<Boolean> pluggedIn) {
            this.pluggedIn = pluggedIn;
            pluggedIn.setIdentifier(PLUGGED_IN_IDENTIFIER);
            addProperty(pluggedIn);
            return this;
        }

        public ChargeState build() {
            return new ChargeState(this);
        }
    }
}