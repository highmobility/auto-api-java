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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class DashboardLight extends Property {
    public static final byte IDENTIFIER = 0x01;

    Type type;
    State state;

    /**
     * @return The dashboard light type.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The state of the dashboard light.
     */
    public State getState() {
        return state;
    }

    public DashboardLight(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        type = Type.fromByte(bytes[6]);
        state = State.fromByte(bytes[7]);
    }

    public DashboardLight(Type type, State state) {
        this(IDENTIFIER, type, state);
    }

    public DashboardLight(byte identifier, Type type, State state) {
        super(identifier, 2);
        bytes[6] = type.getByte();
        bytes[7] = state.getByte();
        this.type = type;
        this.state = state;
    }

    public enum Type {
        HIGH_BEAM_MAIN_BEAM((byte) 0x00),
        LOW_BEAM_DIPPED_BEAM((byte) 0x01),
        HAZARD_WARNING((byte) 0x02),
        BRAKE_FAILURE_BRAKE_SYSTEM_MALFUNCTION((byte) 0x03),
        HATCH_OPEN((byte) 0x04),
        FUEL_LEVEL((byte) 0x05),
        ENGINE_COOLANT_TEMPERATURE((byte) 0x06),
        BATTERY_CHARGING_CONDITION((byte) 0x07),
        ENGINE_OIL((byte) 0x08),
        POSITION_LIGHTS_SIDE_LIGHTS((byte) 0x09),
        FRONT_FOG_LIGHT((byte) 0x0A),
        REAR_FOG_LIGHT((byte) 0x0B),
        PARK_HEATING((byte) 0x0C),
        ENGINE_INDICATOR((byte) 0x0D),
        SERVICE_CALL_FOR_MAINTENANCE((byte) 0x0E),
        TRANSMISSION_FLUID_TEMPERATURE((byte) 0x0F),
        TRANSMISSION_FAILURE_MALFUNCTION((byte) 0x10),
        ANTI_LOCK_BRAKE_SYSTEM_FAILURE((byte) 0x11),
        WORN_BRAKE_LININGS((byte) 0x12),
        WINDSCREEN_WASHER_FLUID_WINDSHIELD_WASHER_FLUID((byte) 0x13),
        TIRE_FAILURE_MALFUNCTION((byte) 0x14),
        ENGINE_OIL_LEVEL((byte) 0x15),
        ENGINE_COOLANT_LEVEL((byte) 0x16),
        STEERING_FAILURE((byte) 0x17),
        ELECTRONIC_SPEED_CONTROLLER_INDICATION((byte) 0x18),
        BRAKE_LIGHTS((byte) 0x19),
        AD_BLUE_LEVEL((byte) 0x1A),
        FUEL_FILTER_DIFFERENTIAL_PRESSURE((byte) 0x1B),
        SEAT_BELT((byte) 0x1C),
        ADVANCED_EMERGENCY_BRAKING_SYSTEM((byte) 0x1D),
        AUTONOMOUS_CRUISE_CONTROL((byte) 0x1E),
        TRAILER_CONNECTED((byte) 0x1F),
        AIRBAG((byte) 0x20),
        ESC_SWITCHED_OFF((byte) 0x21),
        LANE_DEPARTURE_WARNING_SWITCHED_OFF((byte) 0x22);

        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();

            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Type(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    public enum State {
        INACTIVE((byte) 0x00),
        INFO((byte) 0x01),
        YELLOW((byte) 0x02),
        RED((byte) 0x03);

        public static State fromByte(byte byteValue) throws CommandParseException {
            State[] values = State.values();

            for (int i = 0; i < values.length; i++) {
                State state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        State(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}