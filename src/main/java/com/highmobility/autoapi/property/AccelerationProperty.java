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
import com.highmobility.utils.ByteUtils;

import java.util.Arrays;

public class AccelerationProperty extends Property {
    public static final byte IDENTIFIER = 0x01;
    public enum AccelerationType {
        LONGITUDINAL((byte)0x00),
        LATERAL((byte)0x01),
        FRONT_LATERAL((byte)0x02),
        REAR_LATERAL((byte)0x03);

        public static AccelerationType fromByte(byte byteValue) throws CommandParseException {
            AccelerationType[] values = AccelerationType.values();

            for (int i = 0; i < values.length; i++) {
                AccelerationType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        AccelerationType(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    AccelerationType accelerationType;
    float acceleration;

    /**
     *
     * @return The acceleration type
     */
    public AccelerationType getAccelerationType() {
        return accelerationType;
    }

    /**
     *
     * @return The acceleration in g-force
     */
    public float getAcceleration() {
        return acceleration;
    }

    public AccelerationProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 8) throw new CommandParseException();
        accelerationType = AccelerationType.fromByte(bytes[3]);
        acceleration = Property.getFloat(Arrays.copyOfRange(bytes, 4, 8));
    }

    public AccelerationProperty(AccelerationType type, float acceleration) {
        this(IDENTIFIER, type, acceleration);
    }

    public AccelerationProperty(byte identifier, AccelerationType type, float acceleration) {
        super(identifier, 5);
        bytes[3] = type.getByte();
        ByteUtils.setBytes(bytes, Property.floatToBytes(acceleration), 4);
    }
}