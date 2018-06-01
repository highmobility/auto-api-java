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

import com.highmobility.autoapi.property.ChargeMode;
import com.highmobility.autoapi.property.ChargeTimer;
import com.highmobility.autoapi.property.ChargingState;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.PortState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    ChargingState chargingState;
    Integer estimatedRange;
    Float batteryLevel;
    Float batteryCurrentAC;
    Float batteryCurrentDC;

    Float chargerVoltageAC;
    Float chargerVoltageDC;

    Float chargeLimit;
    Integer timeToCompleteCharge;

    Float chargeRate;
    PortState chargePortState;

    ChargeMode chargeMode;
    ChargeTimer[] chargeTimers;

    /**
     * @return The charge state
     */
    public ChargingState getChargingState() {
        return chargingState;
    }

    /**
     * @return The estimated range in km
     */
    public Integer getEstimatedRange() {
        return estimatedRange;
    }

    /**
     * @return The battery level percentage
     */
    public Float getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The battery current in AC
     */
    public Float getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     * @return The battery current in DC
     */
    public Float getBatteryCurrentDC() {
        return batteryCurrentDC;
    }

    /**
     * @return The Charger voltage in AC
     */
    public Float getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     * @return The Charger voltage in DC
     */
    public Float getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     * @return The Charge limit percentage
     */
    public Float getChargeLimit() {
        return chargeLimit;
    }

    /**
     * @return The time to complete the charge in minutes
     */
    public Integer getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     * @return The charge rate in kW, when charging
     */
    public Float getChargeRate() {
        return chargeRate;
    }

    /**
     * @return The charge port state
     */
    public PortState getChargePortState() {
        return chargePortState;
    }

    /**
     * @return The charge mode
     */
    public ChargeMode getChargeMode() {
        return chargeMode;
    }

    /**
     * @return All of the charge timers
     */
    public ChargeTimer[] getChargeTimers() {
        return chargeTimers;
    }

    /**
     * Get the charge timer for the given type
     *
     * @param type The charge timer type
     * @return The charge timer
     */
    public ChargeTimer getChargeTimer(ChargeTimer.Type type) {
        if (chargeTimers != null) {
            for (ChargeTimer timer : chargeTimers) {
                if (timer.getType() == type) return timer;
            }
        }
        return null;
    }

    public ChargeState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            try {
                switch (property.getPropertyIdentifier()) {
                    case ChargingState.IDENTIFIER:
                        chargingState = ChargingState.fromByte(property.getValueByte());
                        break;
                    case ESTIMATED_RANGE_IDENTIFIER:
                        estimatedRange = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case BATTERY_LEVEL_IDENTIFIER:
                        batteryLevel = property.getValueByte() / 100f;
                        break;
                    case BATTERY_CURRENT_AC_IDENTIFIER:
                        batteryCurrentAC = Property.getFloat(property.getValueBytes());
                        break;
                    case BATTERY_CURRENT_DC_IDENTIFIER:
                        batteryCurrentDC = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGER_VOLTAGE_AC_IDENTIFIER:
                        chargerVoltageAC = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGER_VOLTAGE_DC_IDENTIFIER:
                        chargerVoltageDC = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGE_LIMIT_IDENTIFIER:
                        chargeLimit = property.getValueByte() / 100f;
                        break;
                    case TIME_TO_COMPLETE_CHARGE_IDENTIFIER:
                        timeToCompleteCharge = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case CHARGE_RATE_IDENTIFIER:
                        chargeRate = Property.getFloat(property.getValueBytes());
                        break;
                    case PortState.IDENTIFIER:
                        chargePortState = PortState.fromByte(property.getValueByte());
                        break;
                    case ChargeMode.IDENTIFIER:
                        chargeMode = ChargeMode.fromByte(property.getValueByte());
                        break;
                    case ChargeTimer.IDENTIFIER:
                        if (chargeTimers == null) chargeTimers = new ChargeTimer[1];
                        else chargeTimers = Arrays.copyOf(chargeTimers, chargeTimers.length + 1);
                        chargeTimers[chargeTimers.length - 1] = new ChargeTimer(property
                                .getPropertyBytes());
                        break;
                }
            } catch (Exception e) {
                logger.info("invalid " + ByteUtils.hexFromBytes(bytes));
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ChargeState(Builder builder) {
        super(builder);
        chargingState = builder.chargingState;
        estimatedRange = builder.estimatedRange;
        batteryLevel = builder.batteryLevel;
        batteryCurrentAC = builder.batteryCurrentAC;
        batteryCurrentDC = builder.batteryCurrentDC;
        chargerVoltageAC = builder.chargerVoltageAC;
        chargerVoltageDC = builder.chargerVoltageDC;
        chargeLimit = builder.chargeLimit;
        timeToCompleteCharge = builder.timeToCompleteCharge;
        chargeRate = builder.chargeRate;
        chargePortState = builder.chargePortState;
        chargeMode = builder.chargeMode;
        chargeTimers = builder.chargeTimers.toArray(new ChargeTimer[builder.chargeTimers.size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ChargingState chargingState;
        private Integer estimatedRange;
        private Float batteryLevel;
        private Float batteryCurrentAC;
        private Float batteryCurrentDC;
        private Float chargerVoltageAC;
        private Float chargerVoltageDC;
        private Float chargeLimit;
        private Integer timeToCompleteCharge;
        private Float chargeRate;
        private PortState chargePortState;
        private ChargeMode chargeMode;
        private List<ChargeTimer> chargeTimers = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param chargingState The charging state.
         * @return The builder.
         */
        public Builder setChargingState(ChargingState chargingState) {
            this.chargingState = chargingState;
            addProperty(chargingState);
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
        public Builder setBatteryLevel(Float batteryLevel) {
            this.batteryLevel = batteryLevel;
            addProperty(new PercentageProperty(BATTERY_LEVEL_IDENTIFIER, batteryLevel));
            return this;
        }

        /**
         * @param batteryCurrentAC The battery current in AC.
         * @return The builder.
         */
        public Builder setBatteryCurrentAC(Float batteryCurrentAC) {
            this.batteryCurrentAC = batteryCurrentAC;
            addProperty(new FloatProperty(BATTERY_CURRENT_AC_IDENTIFIER, batteryCurrentAC));
            return this;
        }

        /**
         * @param batteryCurrentDC The battery current in DC.
         * @return The builder.
         */
        public Builder setBatteryCurrentDC(Float batteryCurrentDC) {
            this.batteryCurrentDC = batteryCurrentDC;
            addProperty(new FloatProperty(BATTERY_CURRENT_DC_IDENTIFIER, batteryCurrentDC));
            return this;
        }

        /**
         * @param chargerVoltageAC The charger voltage in AC.
         * @return The builder.
         */
        public Builder setChargerVoltageAC(Float chargerVoltageAC) {
            this.chargerVoltageAC = chargerVoltageAC;
            addProperty(new FloatProperty(CHARGER_VOLTAGE_AC_IDENTIFIER, chargerVoltageAC));
            return this;
        }

        /**
         * @param chargerVoltageDC The charger voltage in DC.
         * @return The builder.
         */
        public Builder setChargerVoltageDC(Float chargerVoltageDC) {
            this.chargerVoltageDC = chargerVoltageDC;
            addProperty(new FloatProperty(CHARGER_VOLTAGE_DC_IDENTIFIER, chargerVoltageDC));
            return this;
        }

        /**
         * @param chargeLimit The charge limit percentage.
         * @return The builder.
         */
        public Builder setChargeLimit(Float chargeLimit) {
            this.chargeLimit = chargeLimit;
            addProperty(new PercentageProperty(CHARGE_LIMIT_IDENTIFIER, chargeLimit));
            return this;
        }

        /**
         * @param timeToCompleteCharge The time to complete the charge in minutes.
         * @return The builder.
         */
        public Builder setTimeToCompleteCharge(Integer timeToCompleteCharge) {
            this.timeToCompleteCharge = timeToCompleteCharge;
            addProperty(new IntegerProperty(TIME_TO_COMPLETE_CHARGE_IDENTIFIER,
                    timeToCompleteCharge, 2));
            return this;
        }

        /**
         * @param chargeRate The charge rate in kW, when charging.
         * @return The builder.
         */
        public Builder setChargeRate(Float chargeRate) {
            this.chargeRate = chargeRate;
            addProperty(new FloatProperty(CHARGE_RATE_IDENTIFIER, chargeRate));
            return this;
        }

        /**
         * @param chargePortState The charge port state.
         * @return The builder.
         */
        public Builder setChargePortState(PortState chargePortState) {
            this.chargePortState = chargePortState;
            addProperty(chargePortState);
            return this;
        }

        /**
         * @param chargeMode The charge mode.
         * @return The builder.
         */
        public Builder setChargeMode(ChargeMode chargeMode) {
            this.chargeMode = chargeMode;
            addProperty(chargeMode);
            return this;
        }

        /**
         * @param chargeTimers All of the charge timers.
         * @return The builder.
         */
        public Builder setChargeTimers(ChargeTimer[] chargeTimers) {
            this.chargeTimers = Arrays.asList(chargeTimers);

            for (int i = 0; i < chargeTimers.length; i++) {
                addProperty(chargeTimers[i]);
            }

            return this;
        }

        /**
         * Add a single charge timer.
         *
         * @param chargeTimer The charge timer
         * @return The builder.
         */
        public Builder addChargeTimer(ChargeTimer chargeTimer) {
            chargeTimers.add(chargeTimer);
            addProperty(chargeTimer);
            return this;
        }

        public ChargeState build() {
            return new ChargeState(this);
        }
    }
}