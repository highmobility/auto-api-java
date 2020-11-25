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
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class DashboardLight extends PropertyValueObject {
    public static final int SIZE = 3;

    Name name;
    OnOffState state;

    /**
     * @return The name.
     */
    public Name getName() {
        return name;
    }

    /**
     * @return The state.
     */
    public OnOffState getState() {
        return state;
    }

    public DashboardLight(Name name, OnOffState state) {
        super(0);

        this.name = name;
        this.state = state;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, name.getByte());
        bytePosition += 1;

        set(bytePosition, state.getByte());
    }

    public DashboardLight(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        name = Name.fromByte(get(bytePosition));
        bytePosition += 1;

        state = OnOffState.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum Name implements ByteEnum {
        HIGH_BEAM((byte) 0x00),
        LOW_BEAM((byte) 0x01),
        HAZARD_WARNING((byte) 0x02),
        BRAKE_FAILURE((byte) 0x03),
        HATCH_OPEN((byte) 0x04),
        FUEL_LEVEL((byte) 0x05),
        ENGINE_COOLANT_TEMPERATURE((byte) 0x06),
        BATTERY_CHARGING_CONDITION((byte) 0x07),
        ENGINE_OIL((byte) 0x08),
        POSITION_LIGHTS((byte) 0x09),
        FRONT_FOG_LIGHT((byte) 0x0a),
        REAR_FOG_LIGHT((byte) 0x0b),
        PARK_HEATING((byte) 0x0c),
        ENGINE_INDICATOR((byte) 0x0d),
        SERVICE_CALL((byte) 0x0e),
        TRANSMISSION_FLUID_TEMPERATURE((byte) 0x0f),
        TRANSMISSION_FAILURE((byte) 0x10),
        ANTI_LOCK_BRAKE_FAILURE((byte) 0x11),
        WORN_BRAKE_LININGS((byte) 0x12),
        WINDSCREEN_WASHER_FLUID((byte) 0x13),
        TIRE_FAILURE((byte) 0x14),
        ENGINE_OIL_LEVEL((byte) 0x15),
        ENGINE_COOLANT_LEVEL((byte) 0x16),
        STEERING_FAILURE((byte) 0x17),
        ESC_INDICATION((byte) 0x18),
        BRAKE_LIGHTS((byte) 0x19),
        ADBLUE_LEVEL((byte) 0x1a),
        FUEL_FILTER_DIFF_PRESSURE((byte) 0x1b),
        SEAT_BELT((byte) 0x1c),
        ADVANCED_BRAKING((byte) 0x1d),
        ACC((byte) 0x1e),
        TRAILER_CONNECTED((byte) 0x1f),
        AIRBAG((byte) 0x20),
        ESC_SWITCHED_OFF((byte) 0x21),
        LANE_DEPARTURE_WARNING_OFF((byte) 0x22),
        AIR_FILTER_MINDER((byte) 0x23),
        AIR_SUSPENSION_RIDE_CONTROL_FAULT((byte) 0x24),
        ALL_WHEEL_DRIVE_DISABLED((byte) 0x25),
        ANTI_THEFT((byte) 0x26),
        BLIND_SPOT_DETECTION((byte) 0x27),
        CHARGE_SYSTEM_FAULT((byte) 0x28),
        CHECK_FUEL_CAP((byte) 0x29),
        CHECK_FUEL_FILL_INLET((byte) 0x2a),
        CHECK_FUEL_FILTER((byte) 0x2b),
        DC_TEMP_WARNING((byte) 0x2c),
        DC_WARNING_STATUS((byte) 0x2d),
        DIESEL_ENGINE_IDLE_SHUTDOWN((byte) 0x2e),
        DIESEL_ENGINE_WARNING((byte) 0x2f),
        DIESEL_EXHAUST_FLUID_SYSTEM_FAULT((byte) 0x30),
        DIESEL_EXHAUST_OVER_TEMP((byte) 0x31),
        DIESEL_EXHAUST_FLUID_QUALITY((byte) 0x32),
        DIESEL_FILTER_REGENERATION((byte) 0x33),
        DIESEL_PARTICULATE_FILTER((byte) 0x34),
        DIESEL_PRE_HEAT((byte) 0x35),
        ELECTRIC_TRAILER_BRAKE_CONNECTION((byte) 0x36),
        EV_BATTERY_CELL_MAX_VOLT_WARNING((byte) 0x37),
        EV_BATTERY_CELL_MIN_VOLT_WARNING((byte) 0x38),
        EV_BATTERY_CHARGE_ENERGY_STORAGE_WARNING((byte) 0x39),
        EV_BATTERY_HIGH_LEVEL_WARNING((byte) 0x3a),
        EV_BATTERY_HIGH_TEMPERATURE_WARNING((byte) 0x3b),
        EV_BATTERY_INSULATION_RESIST_WARNING((byte) 0x3c),
        EV_BATTERY_JUMP_LEVEL_WARNING((byte) 0x3d),
        EV_BATTERY_LOW_LEVEL_WARNING((byte) 0x3e),
        EV_BATTERY_MAX_VOLT_VEH_ENERGY_WARNING((byte) 0x3f),
        EV_BATTERY_MIN_VOLT_VEH_ENERGY_WARNING((byte) 0x40),
        EV_BATTERY_OVER_CHARGE_WARNING((byte) 0x41),
        EV_BATTERY_POOR_CELL_WARNING((byte) 0x42),
        EV_BATTERY_TEMP_DIFF_WARNING((byte) 0x43),
        FORWARD_COLLISION_WARNING((byte) 0x44),
        FUEL_DOOR_OPEN((byte) 0x45),
        HILL_DESCENT_CONTROL_FAULT((byte) 0x46),
        HILL_START_ASSIST_WARNING((byte) 0x47),
        HV_INTERLOCKING_STATUS_WARNING((byte) 0x48),
        LIGHTING_SYSTEM_FAILURE((byte) 0x49),
        MALFUNCTION_INDICATOR((byte) 0x4a),
        MOTOR_CONTROLLER_TEMP_WARNING((byte) 0x4b),
        PARK_AID_MALFUNCTION((byte) 0x4c),
        PASSIVE_ENTRY_PASSIVE_START((byte) 0x4d),
        POWERTRAIN_MALFUNCTION((byte) 0x4e),
        RESTRAINTS_INDICATOR_WARNING((byte) 0x4f),
        START_STOP_ENGINE_WARNING((byte) 0x50),
        TRACTION_CONTROL_DISABLED((byte) 0x51),
        TRACTION_CONTROL_ACTIVE((byte) 0x52),
        TRACTION_MOTOR_TEMP_WARNING((byte) 0x53),
        TIRE_PRESSURE_MONITOR_SYSTEM_WARNING((byte) 0x54),
        WATER_IN_FUEL((byte) 0x55),
        TIRE_WARNING_FRONT_RIGHT((byte) 0x56),
        TIRE_WARNING_FRONT_LEFT((byte) 0x57),
        TIRE_WARNING_REAR_RIGHT((byte) 0x58),
        TIRE_WARNING_REAR_LEFT((byte) 0x59),
        TIRE_WARNING_SYSTEM_ERROR((byte) 0x5a),
        BATTERY_LOW_WARNING((byte) 0x5b),
        BRAKE_FLUID_WARNING((byte) 0x5c);
    
        public static Name fromByte(byte byteValue) throws CommandParseException {
            Name[] values = Name.values();
    
            for (int i = 0; i < values.length; i++) {
                Name state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("DashboardLight.Name does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Name(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}