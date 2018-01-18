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

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Created by root on 6/28/17.
 */
public class DiagnosticsState extends IncomingCommand {
    public enum WasherFluidLevel {
        LOW((byte)0x00),
        FULL((byte)0x01);

        public static WasherFluidLevel fromByte(byte value) throws CommandParseException {
            WasherFluidLevel[] values = WasherFluidLevel.values();

            for (int i = 0; i < values.length; i++) {
                WasherFluidLevel capability = values[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        WasherFluidLevel(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    int mileage;
    int oilTemperature;
    int speed;
    int rpm;
    float fuelLevel;
    WasherFluidLevel washerFluidLevel;

    Float frontRightTirePressure;
    Float frontLeftTirePressure;
    Float rearRightTirePressure;
    Float rearLeftTirePressure;

    /**
     *
     * @return The car mileage (odometer) in km
     */
    public int getMileage() {
        return mileage;
    }

    /**
     *
     * @return Engine oil temperature in Celsius, whereas can be negative
     */
    public int getOilTemperature() {
        return oilTemperature;
    }

    /**
     *
     * @return The car speed in km/h, whereas can be negative
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @return RPM of the Engine
     */
    public int getRpm() {
        return rpm;
    }

    /**
     *
     * @return Fuel level percentage between 0-100
     */
    public float getFuelLevel() {
        return fuelLevel;
    }

    /**
     *
     * @return Washer fluid level
     */
    public WasherFluidLevel getWasherFluidLevel() {
        return washerFluidLevel;
    }

    /**
     *
     * @return The front right tire pressure in BAR
     *          null if there is no info
     */
    public Float getFrontRightTirePressure() {
        return frontRightTirePressure;
    }

    /**
     *
     * @return The front left tire pressure in BAR
     *          null if there is no info
     */
    public Float getFrontLeftTirePressure() {
        return frontLeftTirePressure;
    }

    /**
     *
     * @return The rear right tire pressure in BAR
     *          null if there is no info
     */
    public Float getRearRightTirePressure() {
        return rearRightTirePressure;
    }

    /**
     *
     * @return The rear left tire pressure in BAR
     *          null if there is no info
     */
    public Float getRearLeftTirePressure() {
        return rearLeftTirePressure;
    }

    public DiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 13) throw new CommandParseException();

        mileage = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 3, 3 + 3));
        oilTemperature = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 6, 6 + 2));
        speed = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 8, 8 + 2));
        rpm = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 10, 10 + 2));
        fuelLevel = (int)bytes[12] / 100f;
        if (bytes[13] == 0x00) washerFluidLevel = WasherFluidLevel.LOW;
        else washerFluidLevel = WasherFluidLevel.FULL;

        int numberOfTires = bytes[14];
        int position = 15;

        for (int i = 0; i < numberOfTires; i++) {
            byte location = bytes[position];
            Float pressure = Property.getFloat(Arrays.copyOfRange(bytes, position + 1, position + 1 + 4));

            if (location == 0x00) {
                frontLeftTirePressure = pressure;
            }
            else if (location == 0x01) {
                frontRightTirePressure = pressure;
            }
            else if (location == 0x02) {
                rearRightTirePressure = pressure;
            }
            else if (location == 0x03) {
                rearLeftTirePressure = pressure;
            }

            position += 5;
        }
    }
}
