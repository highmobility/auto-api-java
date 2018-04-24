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
import com.highmobility.utils.Bytes;

public class TireStateProperty extends Property {
    public static final byte identifier = (byte) 0x0A;
    Location location;
    Float pressure;
    Float temperature;
    Integer rpm;

    /**
     * @return The location of the tire.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The tire pressure in BAR.
     */
    public Float getPressure() {
        return pressure;
    }

    /**
     * @return Tire temperature in celsius.
     */
    public Float getTemperature() {
        return temperature;
    }

    /**
     * @return The RPM measured at this wheel.
     */
    public Integer getRpm() {
        return rpm;
    }

    public TireStateProperty(byte[] bytes, int startingFrom) throws CommandParseException {
        super(bytes);
        location = Location.fromByte(bytes[startingFrom]);
        pressure = Property.getFloat(bytes, startingFrom + 1);
        temperature = Property.getFloat(bytes, startingFrom + 5);
        rpm = Property.getUnsignedInt(bytes, startingFrom + 9, 2);
    }

    public TireStateProperty(Location location, float pressure, float temperature, int rpm) {
        super(identifier, getValue(location, pressure, temperature, rpm));
    }

    static byte[] getValue(Location location, float pressure, float temperature, int rpm) {
        byte[] value = new byte[11];
        value[0] = location.getByte();
        Bytes.setBytes(value, Property.floatToBytes(pressure), 1);
        Bytes.setBytes(value, Property.floatToBytes(temperature), 5);
        Bytes.setBytes(value, Property.intToBytes(rpm, 2), 9);
        return value;
    }

    /**
     * The tire location
     */
    public enum Location {
        FRONT_LEFT((byte) 0x00),
        FRONT_RIGHT((byte) 0x01),
        REAR_RIGHT((byte) 0x02),
        REAR_LEFT((byte) 0x03);

        public static Location fromByte(byte value) throws CommandParseException {
            Location[] values = Location.values();

            for (int i = 0; i < values.length; i++) {
                Location value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Location(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}