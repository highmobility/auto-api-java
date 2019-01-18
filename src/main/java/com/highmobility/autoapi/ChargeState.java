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
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.charging.ChargePortState;
import com.highmobility.autoapi.property.charging.ChargingState;
import com.highmobility.autoapi.property.charging.ChargingTimer;
import com.highmobility.autoapi.property.charging.DepartureTime;
import com.highmobility.autoapi.property.charging.PlugType;
import com.highmobility.autoapi.property.charging.ReductionTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Charge State command is received by the car or when the car is plugged
 * in, disconnected, starts or stops charging, or when the charge limit is changed.
 */
public class ChargeState extends CommandWithProperties {
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

    IntegerProperty estimatedRange = new IntegerProperty(ESTIMATED_RANGE_IDENTIFIER, false);
    PercentageProperty batteryLevel = new PercentageProperty(BATTERY_LEVEL_IDENTIFIER);
    FloatProperty batteryCurrentAC = new FloatProperty(BATTERY_CURRENT_AC_IDENTIFIER);
    FloatProperty batteryCurrentDC = new FloatProperty(BATTERY_CURRENT_DC_IDENTIFIER);
    FloatProperty chargerVoltageAC = new FloatProperty(CHARGER_VOLTAGE_AC_IDENTIFIER);
    FloatProperty chargerVoltageDC = new FloatProperty(CHARGER_VOLTAGE_DC_IDENTIFIER);
    PercentageProperty chargeLimit = new PercentageProperty(CHARGE_LIMIT_IDENTIFIER);
    IntegerProperty timeToCompleteCharge = new IntegerProperty(TIME_TO_COMPLETE_CHARGE_IDENTIFIER
            , false);
    FloatProperty chargingRate = new FloatProperty(CHARGING_RATE_IDENTIFIER);
    ChargePortState chargePortState = new ChargePortState(CHARGE_PORT_STATE_IDENTIFIER);
    ChargeMode chargeMode = new ChargeMode(CHARGE_MODE_IDENTIFIER);
    FloatProperty maxChargingCurrent = new FloatProperty(MAX_CHARGING_CURRENT_IDENTIFIER);
    PlugType plugType = new PlugType(PLUG_TYPE_IDENTIFIER);
    BooleanProperty chargingWindowChosen = new BooleanProperty(CHARGING_WINDOW_CHOSEN_IDENTIFIER);
    DepartureTime[] departureTimes;
    ReductionTime[] reductionOfChargingCurrentTimes;
    FloatProperty batteryTemperature = new FloatProperty(BATTERY_TEMPERATURE_IDENTIFIER);
    ChargingTimer[] timers;
    BooleanProperty pluggedIn = new BooleanProperty(PLUGGED_IN_IDENTIFIER);
    ChargingState activeState = new ChargingState(ACTIVE_STATE_IDENTIFIER);

    /**
     * @return The estimated range in km.
     */
    @Nullable public IntegerProperty getEstimatedRange() {
        return estimatedRange;
    }

    /**
     * @return The battery level percentage.
     */
    @Nullable public PercentageProperty getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The battery current in AC.
     */
    @Nullable public FloatProperty getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     * @return The battery current in DC.
     */
    @Nullable public FloatProperty getBatteryCurrentDC() {
        return batteryCurrentDC;
    }

    /**
     * @return The Charger voltage in AC.
     */
    @Nullable public FloatProperty getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     * @return The Charger voltage in DC.
     */
    @Nullable public FloatProperty getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     * @return The Charge limit percentage.
     */
    @Nullable public PercentageProperty getChargeLimit() {
        return chargeLimit;
    }

    /**
     * @return The time to complete the charge in minutes.
     */
    @Nullable public IntegerProperty getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     * @return The charging rate in kW, when charging.
     */
    @Nullable public FloatProperty getChargingRate() {
        return chargingRate;
    }

    /**
     * @return The charge port state.
     */
    @Nullable public ChargePortState getChargePortState() {
        return chargePortState;
    }

    /**
     * @return The charge mode.
     */
    @Nullable public ChargeMode getChargeMode() {
        return chargeMode;
    }

    /**
     * @return The maximum charging current.
     */
    @Nullable public FloatProperty getMaxChargingCurrent() {
        return maxChargingCurrent;
    }

    /**
     * @return The plug type.
     */
    @Nullable public PlugType getPlugType() {
        return plugType;
    }

    /**
     * @return Indication on whether charging window is chosen.
     */
    @Nullable public BooleanProperty getChargingWindowChosen() {
        return chargingWindowChosen;
    }

    /**
     * @return The departure times.
     */
    public DepartureTime[] getDepartureTimes() {
        return departureTimes;
    }

    /**
     * @return The reduction of charging current times.
     */
    public ReductionTime[] getReductionOfChargingCurrentTimes() {
        return reductionOfChargingCurrentTimes;
    }

    /**
     * @return The battery temperature in Celsius.
     */
    @Nullable public FloatProperty getBatteryTemperature() {
        return batteryTemperature;
    }

    /**
     * @return The charging timers.
     */
    public ChargingTimer[] getTimers() {
        return timers;
    }

    /**
     * Get the charge timer for the given type.
     *
     * @param type The charge timer type.
     * @return The charge timer.
     */
    @Nullable public ChargingTimer getTimer(ChargingTimer.Value.Type type) {
        if (timers != null) {
            for (ChargingTimer timer : timers) {
                if (timer.getValue() != null && timer.getValue().getType() == type) return timer;
            }
        }
        return null;
    }

    /**
     * @return The plugged in state.
     */
    @Nullable public BooleanProperty getPluggedIn() {
        return pluggedIn;
    }

    /**
     * @return The charging active state.
     */
    @Nullable
    public ChargingState getActiveState() {
        return activeState;
    }

    ChargeState(byte[] bytes) {
        super(bytes);

        ArrayList<DepartureTime> departureTimes = new ArrayList<>();
        ArrayList<ReductionTime> reductionOfChargingCurrentTimes = new ArrayList<>();
        ArrayList<ChargingTimer> timers = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
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
                        DepartureTime time = new DepartureTime(p);
                        departureTimes.add(time);
                        return time;
                    case REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER:
                        ReductionTime time2 = new ReductionTime(p);
                        reductionOfChargingCurrentTimes.add(time2);
                        return time2;
                    case BATTERY_TEMPERATURE_IDENTIFIER:
                        batteryTemperature.update(p);
                        return batteryTemperature;
                    case PLUGGED_IN_IDENTIFIER:
                        pluggedIn.update(p);
                        return pluggedIn;
                    case TIMER_IDENTIFIER:
                        ChargingTimer time3 = new ChargingTimer(p);
                        timers.add(time3);
                        return time3;
                }

                return null;
            });
        }

        this.departureTimes = departureTimes.toArray(new DepartureTime[0]);
        this.reductionOfChargingCurrentTimes = reductionOfChargingCurrentTimes.toArray(new
                ReductionTime[0]);
        this.timers = timers.toArray(new ChargingTimer[0]);
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
        departureTimes = builder.departureTimes.toArray(new DepartureTime[0]);
        reductionOfChargingCurrentTimes = builder.reductionOfChargingCurrentTimes.toArray(new
                ReductionTime[0]);
        batteryTemperature = builder.batteryTemperature;
        timers = builder.timers.toArray(new ChargingTimer[0]);
        pluggedIn = builder.pluggedIn;
        activeState = builder.activeState;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private IntegerProperty estimatedRange;
        private PercentageProperty batteryLevel;
        private FloatProperty batteryCurrentAC;
        private FloatProperty batteryCurrentDC;

        private FloatProperty chargerVoltageAC;
        private FloatProperty chargerVoltageDC;

        private PercentageProperty chargeLimit;
        private IntegerProperty timeToCompleteCharge;

        private FloatProperty chargingRate;
        private ChargePortState chargePortState;

        private ChargeMode chargeMode;
        private FloatProperty maxChargingCurrent;
        private PlugType plugType;
        private BooleanProperty chargingWindowChosen;
        private List<DepartureTime> departureTimes = new ArrayList<>();

        private List<ReductionTime> reductionOfChargingCurrentTimes = new ArrayList<>();
        private FloatProperty batteryTemperature;
        private List<ChargingTimer> timers = new ArrayList<>();
        private BooleanProperty pluggedIn;
        private ChargingState activeState;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param activeState The charging state.
         * @return The builder.
         */
        public Builder setActiveState(ChargingState activeState) {
            this.activeState = activeState;
            activeState.setIdentifier(ACTIVE_STATE_IDENTIFIER);
            addProperty(activeState);
            return this;
        }

        /**
         * @param estimatedRange The estimated range in km.
         * @return The builder.
         */
        public Builder setEstimatedRange(IntegerProperty estimatedRange) {
            this.estimatedRange = estimatedRange;
            estimatedRange.setIdentifier(ESTIMATED_RANGE_IDENTIFIER, 2);
            addProperty(estimatedRange);
            return this;
        }

        /**
         * @param batteryLevel The battery level percentage.
         * @return The builder.
         */
        public Builder setBatteryLevel(PercentageProperty batteryLevel) {
            this.batteryLevel = batteryLevel;
            batteryLevel.setIdentifier(BATTERY_LEVEL_IDENTIFIER);
            addProperty(batteryLevel);
            return this;
        }

        /**
         * @param batteryCurrentAC The battery current in AC.
         * @return The builder.
         */
        public Builder setBatteryCurrentAC(FloatProperty batteryCurrentAC) {
            this.batteryCurrentAC = batteryCurrentAC;
            batteryCurrentAC.setIdentifier(BATTERY_CURRENT_AC_IDENTIFIER);
            addProperty(batteryCurrentAC);
            return this;
        }

        /**
         * @param batteryCurrentDC The battery current in DC.
         * @return The builder.
         */
        public Builder setBatteryCurrentDC(FloatProperty batteryCurrentDC) {
            this.batteryCurrentDC = batteryCurrentDC;
            batteryCurrentDC.setIdentifier(BATTERY_CURRENT_DC_IDENTIFIER);
            addProperty(batteryCurrentDC);
            return this;
        }

        /**
         * @param chargerVoltageAC The charger voltage in AC.
         * @return The builder.
         */
        public Builder setChargerVoltageAC(FloatProperty chargerVoltageAC) {
            this.chargerVoltageAC = chargerVoltageAC;
            chargerVoltageAC.setIdentifier(CHARGER_VOLTAGE_AC_IDENTIFIER);
            addProperty(chargerVoltageAC);
            return this;
        }

        /**
         * @param chargerVoltageDC The charger voltage in DC.
         * @return The builder.
         */
        public Builder setChargerVoltageDC(FloatProperty chargerVoltageDC) {
            this.chargerVoltageDC = chargerVoltageDC;
            chargerVoltageDC.setIdentifier(CHARGER_VOLTAGE_DC_IDENTIFIER);
            addProperty(chargerVoltageDC);
            return this;
        }

        /**
         * @param chargeLimit The charge limit percentage.
         * @return The builder.
         */
        public Builder setChargeLimit(PercentageProperty chargeLimit) {
            this.chargeLimit = chargeLimit;
            chargeLimit.setIdentifier(CHARGE_LIMIT_IDENTIFIER);
            addProperty(chargeLimit);
            return this;
        }

        /**
         * @param timeToCompleteCharge The time to complete the charge in minutes.
         * @return The builder.
         */
        public Builder setTimeToCompleteCharge(IntegerProperty timeToCompleteCharge) {
            this.timeToCompleteCharge = timeToCompleteCharge;
            timeToCompleteCharge.setIdentifier(TIME_TO_COMPLETE_CHARGE_IDENTIFIER, 1);
            addProperty(timeToCompleteCharge);
            return this;
        }

        /**
         * @param chargingRate The charge rate in kW, when charging.
         * @return The builder.
         */
        public Builder setChargingRate(FloatProperty chargingRate) {
            this.chargingRate = chargingRate;
            chargingRate.setIdentifier(CHARGING_RATE_IDENTIFIER);
            addProperty(chargingRate);
            return this;
        }

        /**
         * @param chargePortState The charge port state.
         * @return The builder.
         */
        public Builder setChargePortState(ChargePortState chargePortState) {
            this.chargePortState = chargePortState;
            chargePortState.setIdentifier(CHARGE_PORT_STATE_IDENTIFIER);
            addProperty(chargePortState);
            return this;
        }

        /**
         * @param chargeMode The charge mode.
         * @return The builder.
         */
        public Builder setChargeMode(ChargeMode chargeMode) {
            this.chargeMode = chargeMode;
            chargeMode.setIdentifier(CHARGE_MODE_IDENTIFIER);
            addProperty(chargeMode);
            return this;
        }

        /**
         * @param maxChargingCurrent The max charging current.
         * @return The builder.
         */
        public Builder setMaxChargingCurrent(FloatProperty maxChargingCurrent) {
            this.maxChargingCurrent = maxChargingCurrent;
            maxChargingCurrent.setIdentifier(MAX_CHARGING_CURRENT_IDENTIFIER);
            addProperty(maxChargingCurrent);
            return this;
        }

        /**
         * @param plugType The plug type.
         * @return The builder.
         */
        public Builder setPlugType(PlugType plugType) {
            this.plugType = plugType;
            plugType.setIdentifier(PLUG_TYPE_IDENTIFIER);
            addProperty(plugType);
            return this;
        }

        /**
         * @param chargingWindowChosen Charging window chosen state.
         * @return The builder.
         */
        public Builder setChargingWindowChosen(BooleanProperty chargingWindowChosen) {
            this.chargingWindowChosen = chargingWindowChosen;
            chargingWindowChosen.setIdentifier(CHARGING_WINDOW_CHOSEN_IDENTIFIER);
            addProperty(chargingWindowChosen);
            return this;
        }

        /**
         * @param departureTimes The departure times.
         * @return The builder.
         */
        public Builder setDepartureTimes(DepartureTime[] departureTimes) {
            this.departureTimes = Arrays.asList(departureTimes);

            for (int i = 0; i < departureTimes.length; i++) {
                addProperty(departureTimes[i]);
            }

            return this;
        }

        // TBODO: translate

        public Builder addDepartureTime(DepartureTime departureTime) {
            this.departureTimes.add(departureTime);
            addProperty(departureTime);
            return this;
        }

        public Builder setReductionOfChargingCurrentTimes(ReductionTime[]
                                                                  reductionOfChargingCurrentTimes) {
            this.reductionOfChargingCurrentTimes = Arrays.asList(reductionOfChargingCurrentTimes);

            for (int i = 0; i < reductionOfChargingCurrentTimes.length; i++) {
                ReductionTime time = reductionOfChargingCurrentTimes[i];
                time.setIdentifier(REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER);
                addProperty(time);
            }

            return this;
        }

        public Builder addReductionOfChargingCurrentTime(ReductionTime
                                                                 reductionOfChargingCurrentTime) {
            reductionOfChargingCurrentTime.setIdentifier
                    (REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER);
            this.reductionOfChargingCurrentTimes.add(reductionOfChargingCurrentTime);
            addProperty(reductionOfChargingCurrentTime);
            return this;

        }

        public Builder setBatteryTemperature(FloatProperty batteryTemperature) {
            this.batteryTemperature = batteryTemperature;
            batteryTemperature.setIdentifier(BATTERY_TEMPERATURE_IDENTIFIER);
            addProperty(batteryTemperature);
            return this;
        }

        /**
         * @param chargingTimers All of the charge timers.
         * @return The builder.
         */
        public Builder setTimers(ChargingTimer[] chargingTimers) {
            this.timers = Arrays.asList(chargingTimers);

            for (int i = 0; i < chargingTimers.length; i++) {
                ChargingTimer timer = chargingTimers[i];
                timer.setIdentifier(TIMER_IDENTIFIER);
                addProperty(chargingTimers[i]);
            }

            return this;
        }

        /**
         * Add a single charge timer.
         *
         * @param timer The charge timer.
         * @return The builder.
         */
        public Builder addTimer(ChargingTimer timer) {
            this.timers.add(timer);
            timer.setIdentifier(TIMER_IDENTIFIER);
            addProperty(timer);
            return this;
        }

        public Builder setPluggedIn(BooleanProperty pluggedIn) {
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