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

package com.highmobility.autoapi.property.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

import java.util.Calendar;

import javax.annotation.Nullable;

public class FrontExteriorLightState extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public FrontExteriorLightState(byte identifier) {
        super(identifier);
    }

    public FrontExteriorLightState(@Nullable Value value, @Nullable Calendar timestamp,
                                   @Nullable PropertyFailure failure) {
        super(value, timestamp, failure);
        update(value);
    }

    public FrontExteriorLightState(Value value) {
        super(value);
        update(value);
    }

    public FrontExteriorLightState(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        ignoreInvalidByteSizeException(() -> value = value.fromByte(p.get(3)));
        return this;
    }

    public Property update(Value value) {
        super.update(value);
        this.value = value;
        return this;
    }

    public enum Value implements PropertyValueSingleByte {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        ACTIVE_FULL_BEAM((byte) 0x02),
        ACTIVE_DAYLIGHT_RUNNING_LAMPS((byte) 0x03),
        AUTOMATIC((byte) 0x04);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value capability = values[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Value(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        @Override public int getLength() {
            return 1;
        }
    }
}