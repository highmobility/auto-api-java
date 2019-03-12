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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Acceleration extends PropertyValueObject {

    AccelerationType accelerationType;
    float acceleration;

    /**
     * @return The acceleration type
     */
    public AccelerationType getAccelerationType() {
        return accelerationType;
    }

    /**
     * @return The acceleration in g-force
     */
    public float getAcceleration() {
        return acceleration;
    }

    public Acceleration(AccelerationType type, float acceleration) {
        super(2);
        update(type, acceleration);
    }

    public Acceleration() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes bytes) throws CommandParseException {
        super.update(bytes);
        if (getLength() < 5) throw new CommandParseException();
        accelerationType = AccelerationType.fromByte(get(0));
        acceleration = Property.getFloat(this, 1);
    }

    public void update(AccelerationType type, float acceleration) {
        this.accelerationType = type;
        this.acceleration = acceleration;
        bytes = new byte[5];

        set(0, accelerationType.getByte());
        set(1, Property.floatToBytes(acceleration));
    }

    public void update(Acceleration value) {
        update(value.accelerationType, value.acceleration);
    }

    public enum AccelerationType {
        LONGITUDINAL((byte) 0x00),
        LATERAL((byte) 0x01),
        FRONT_LATERAL((byte) 0x02),
        REAR_LATERAL((byte) 0x03);

        static AccelerationType fromByte(byte byteValue) throws CommandParseException {
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
}