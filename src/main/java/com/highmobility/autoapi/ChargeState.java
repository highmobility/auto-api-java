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
import com.highmobility.autoapi.property.ChargeMode;
import com.highmobility.autoapi.property.ChargePortState;
import com.highmobility.autoapi.property.ChargingState;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
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
    private static final byte CHARGE_RATE_IDENTIFIER = 0x0A;
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

    private static final byte IDENTIFIER_STATE = 0x17;

    Integer estimatedRange;
    PercentageProperty batteryLevel;
    FloatProperty batteryCurrentAC;
    FloatProperty batteryCurrentDC;

    FloatProperty chargerVoltageAC;
    FloatProperty chargerVoltageDC;

    PercentageProperty chargeLimit;
    Integer timeToCompleteCharge;

    FloatProperty chargingRate;
    ChargePortState chargeChargePortState;

    ChargeMode chargeMode;

    FloatProperty maxChargingCurrent;
    PlugType plugType;
    Boolean chargingWindowChosen;
    DepartureTime[] departureTimes;

    ReductionTime[] reductionOfChargingCurrentTimes;
    FloatProperty batteryTemperature;
    ChargingTimer[] timers;
    Boolean pluggedIn;
    ChargingState activeState;

    /**
     * @return The estimated range in km.
     */
    @Nullable public Integer getEstimatedRange() {
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
    @Nullable public Integer getTimeToCompleteCharge() {
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
    @Nullable public ChargePortState getChargeChargePortState() {
        return chargeChargePortState;
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
    @Nullable public Boolean getChargingWindowChosen() {
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
    @Nullable public ChargingTimer getTimer(ChargingTimer.Type type) {
        if (timers != null) {
            for (ChargingTimer timer : timers) {
                if (timer.getType() == type) return timer;
            }
        }
        return null;
    }

    /**
     * @return The plugged in state.
     */
    @Nullable public Boolean getPluggedIn() {
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

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATE:
                        activeState = ChargingState.fromByte(p.getValueByte());
                        return activeState;
                    case ESTIMATED_RANGE_IDENTIFIER:
                        estimatedRange = Property.getUnsignedInt(p.getValueBytes());
                        return estimatedRange;
                    case BATTERY_LEVEL_IDENTIFIER:
                        batteryLevel = new PercentageProperty(p);
                        return batteryLevel;
                    case BATTERY_CURRENT_AC_IDENTIFIER:
                        batteryCurrentAC = new FloatProperty(p);
                        return batteryCurrentAC;
                    case BATTERY_CURRENT_DC_IDENTIFIER:
                        batteryCurrentDC = new FloatProperty(p);
                        return batteryCurrentDC;
                    case CHARGER_VOLTAGE_AC_IDENTIFIER:
                        chargerVoltageAC = new FloatProperty(p);
                        return chargerVoltageAC;
                    case CHARGER_VOLTAGE_DC_IDENTIFIER:
                        chargerVoltageDC = new FloatProperty(p);
                        return chargerVoltageDC;
                    case CHARGE_LIMIT_IDENTIFIER:
                        chargeLimit = new PercentageProperty(p);
                        return chargeLimit;
                    case TIME_TO_COMPLETE_CHARGE_IDENTIFIER:
                        timeToCompleteCharge = Property.getUnsignedInt(p.getValueBytes());
                        return timeToCompleteCharge;
                    case CHARGE_RATE_IDENTIFIER:
                        chargingRate = new FloatProperty(p);
                        return chargingRate;
                    case CHARGE_PORT_STATE_IDENTIFIER:
                        chargeChargePortState = ChargePortState.fromByte(p.getValueByte());
                        return chargeChargePortState;
                    case CHARGE_MODE_IDENTIFIER:
                        chargeMode = ChargeMode.fromByte(p.getValueByte());
                        return chargeMode;
                    case MAX_CHARGING_CURRENT_IDENTIFIER:
                        maxChargingCurrent = new FloatProperty(p);
                        return maxChargingCurrent;
                    case PLUG_TYPE_IDENTIFIER:
                        plugType = PlugType.fromByte(p.getValueByte());
                        return plugType;
                    case CHARGING_WINDOW_CHOSEN_IDENTIFIER:
                        chargingWindowChosen = Property.getBool(p.getValueByte());
                        return chargingWindowChosen;
                    case DEPARTURE_TIMES_IDENTIFIER:
                        DepartureTime time = new DepartureTime(p.getByteArray());
                        departureTimes.add(time);
                        return time;
                    case REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER:
                        ReductionTime time2 = new ReductionTime(p.getByteArray());
                        reductionOfChargingCurrentTimes.add(time2);
                        return time2;
                    case BATTERY_TEMPERATURE_IDENTIFIER:
                        batteryTemperature = new FloatProperty(p);
                        return batteryTemperature;
                    case PLUGGED_IN_IDENTIFIER:
                        pluggedIn = Property.getBool(p.getValueByte());
                        return pluggedIn;
                    case TIMER_IDENTIFIER:
                        ChargingTimer timer = new ChargingTimer(p.getByteArray());
                        timers.add(timer);
                        return timer;
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
        chargeChargePortState = builder.chargePortState;
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
        private Integer estimatedRange;
        private PercentageProperty batteryLevel;
        private FloatProperty batteryCurrentAC;
        private FloatProperty batteryCurrentDC;

        private FloatProperty chargerVoltageAC;
        private FloatProperty chargerVoltageDC;

        private PercentageProperty chargeLimit;
        private Integer timeToCompleteCharge;

        private FloatProperty chargingRate;
        private ChargePortState chargePortState;

        private ChargeMode chargeMode;
        private FloatProperty maxChargingCurrent;
        private PlugType plugType;
        private Boolean chargingWindowChosen;
        private List<DepartureTime> departureTimes = new ArrayList<>();

        private List<ReductionTime> reductionOfChargingCurrentTimes = new ArrayList<>();
        private FloatProperty batteryTemperature;
        private List<ChargingTimer> timers = new ArrayList<>();
        private Boolean pluggedIn;
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
            addProperty(new Property(IDENTIFIER_STATE, activeState.getByte()));
            return this;
        }

        /**
         * @param estimatedRange The estimated range in km.
         * @return The builder.
         */
        public Builder setEstimatedRange(Integer estimatedRange) {
            this.estimatedRange = estimatedRange;
            addProperty(new IntegerProperty(ESTIMATED_RANGE_IDENTIFIER, estimatedRange, 2));
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
        public Builder setTimeToCompleteCharge(Integer timeToCompleteCharge) {
            this.timeToCompleteCharge = timeToCompleteCharge;
            addProperty(new IntegerProperty(TIME_TO_COMPLETE_CHARGE_IDENTIFIER,
                    timeToCompleteCharge, 1));
            return this;
        }

        /**
         * @param chargingRate The charge rate in kW, when charging.
         * @return The builder.
         */
        public Builder setChargingRate(FloatProperty chargingRate) {
            this.chargingRate = chargingRate;
            chargingRate.setIdentifier(CHARGE_RATE_IDENTIFIER);
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
            addProperty(new Property(CHARGE_PORT_STATE_IDENTIFIER, chargePortState.getByte()));
            return this;
        }

        /**
         * @param chargeMode The charge mode.
         * @return The builder.
         */
        public Builder setChargeMode(ChargeMode chargeMode) {
            this.chargeMode = chargeMode;
            addProperty(new Property(CHARGE_MODE_IDENTIFIER, chargeMode.getByte()));
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
            addProperty(new Property(PLUG_TYPE_IDENTIFIER, plugType.getByte()));
            return this;
        }

        /**
         * @param chargingWindowChosen Charging window chosen state.
         * @return The builder.
         */
        public Builder setChargingWindowChosen(Boolean chargingWindowChosen) {
            this.chargingWindowChosen = chargingWindowChosen;
            addProperty(new BooleanProperty(CHARGING_WINDOW_CHOSEN_IDENTIFIER,
                    chargingWindowChosen));
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

        public Builder setPluggedIn(Boolean pluggedIn) {
            this.pluggedIn = pluggedIn;
            addProperty(new BooleanProperty(PLUGGED_IN_IDENTIFIER, pluggedIn));
            return this;
        }

        public ChargeState build() {
            return new ChargeState(this);
        }
    }
}