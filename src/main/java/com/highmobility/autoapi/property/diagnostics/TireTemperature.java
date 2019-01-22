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

package com.highmobility.autoapi.property.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValue;
import com.highmobility.autoapi.property.value.TireLocation;
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

import javax.annotation.Nullable;

public class TireTemperature extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }


    public TireTemperature(byte identifier) {
        super(identifier);
    }

    public TireTemperature(@Nullable Value value, @Nullable Calendar timestamp,
                              @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public TireTemperature(Value value) {
        this((byte) 0x00, value);
    }

    public TireTemperature(byte identifier, Value value) {
        super(identifier, value == null ? 0 : value.getLength());

        this.value = value;

        if (value != null) {
            bytes[3] = value.tireLocation.getByte();
            ByteUtils.setBytes(bytes, Property.floatToBytes(value.temperature), 4);
        }
    }

    public TireTemperature(TireLocation tireLocation, float temperature) {
        this((byte) 0x00, tireLocation, temperature);
    }

    public TireTemperature(byte identifier, TireLocation tireLocation, float temperature) {
        this(identifier, new Value(tireLocation, temperature));
    }

    public TireTemperature(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 5) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
        TireLocation tireLocation;
        float temperature;

        /**
         * @return The tire location.
         */
        public TireLocation getTireLocation() {
            return tireLocation;
        }

        /**
         * @return The tire pressure.
         */
        public float getTemperature() {
            return temperature;
        }

        public Value(TireLocation tireLocation, float temperature) {
            this.tireLocation = tireLocation;
            this.temperature = temperature;
        }

        public Value(Property bytes) throws CommandParseException {
            if (bytes.getLength() < 8) throw new CommandParseException();
            this.tireLocation = TireLocation.fromByte(bytes.get(3));
            this.temperature = Property.getFloat(bytes, 4);
        }

        @Override public int getLength() {
            return 5;
        }
    }
}
