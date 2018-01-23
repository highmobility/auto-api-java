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

/**
 * This message is sent when a Get Charge State message is received by the car. It is also sent
 * when the car is plugged in, disconnected, starts or stops charging, or when the charge limit
 * is changed.
 */
public class ChargeState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x01);

    /**
     * The possible charge states
     */
    public enum ChargingState {
        DISCONNECTED((byte)0x00),
        PLUGGED_IN((byte)0x01),
        CHARGING((byte)0x02),
        CHARGING_COMPLETE((byte)0x03);

        public static ChargingState fromByte(byte byteValue) throws CommandParseException {
            ChargingState[] values = ChargingState.values();

            for (int i = 0; i < values.length; i++) {
                ChargingState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        ChargingState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }


    /**
     * The possible charge port states
     */
    public enum PortState {
        CLOSED, OPEN, UNAVAILABLE;

        public static PortState fromByte(byte value) throws CommandParseException {
            switch (value) {
                case 0x00: return CLOSED;
                case 0x01: return OPEN;
                case (byte)0xFF: return UNAVAILABLE;
            }

            throw new CommandParseException();
        }
    }

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

    /**
     *
     * @return The Charge State
     */
    public ChargingState getChargingState() {
        return chargingState;
    }

    /**
     *
     * @return Estimated range in km
     */
    public Integer getEstimatedRange() {
        return estimatedRange;
    }

    /**
     *
     * @return battery level percentage
     */
    public Float getBatteryLevel() {
        return batteryLevel;
    }

    /**
     *
     * @return Battery current in AC
     */
    public Float getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     *
     * @return Battery current in DC
     */
    public Float getBatteryCurrentDC() {
        return batteryCurrentDC;
    }


    /**
     *
     * @return Charger voltage in AC
     */
    public Float getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     *
     * @return Charger voltage in DC
     */
    public Float getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     *
     * @return Charge limit percentage
     */
    public Float getChargeLimit() {
        return chargeLimit;
    }

    /**
     *
     * @return The time to complete the charge in minutes
     */
    public Integer getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     *
     * @return Charge rate in kW represented as 4-bytes per IEEE 754, when charging
     */
    public Float getChargeRate() {
        return chargeRate;
    }

    /**
     *
     * @return Charge Port State
     */
    public PortState getChargePortState() {
        return chargePortState;
    }

    public ChargeState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    chargingState = ChargingState.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    estimatedRange = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x03:
                    batteryLevel = property.getValueByte() / 100f;
                    break;
                case 0x04:
                    batteryCurrentAC = Property.getFloat(property.getValueBytes());
                    break;
                case 0x05:
                    batteryCurrentDC = Property.getFloat(property.getValueBytes());
                    break;
                case 0x06:
                    chargerVoltageAC = Property.getFloat(property.getValueBytes());
                    break;
                case 0x07:
                    chargerVoltageDC = Property.getFloat(property.getValueBytes());
                    break;
                case 0x08:
                    chargeLimit = property.getValueByte() / 100f;
                    break;
                case 0x09:
                    timeToCompleteCharge = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x0A:
                    chargeRate = Property.getFloat(property.getValueBytes());
                    break;
                case 0x0B:
                    chargePortState = PortState.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}