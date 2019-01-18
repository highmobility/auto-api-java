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
import com.highmobility.autoapi.property.value.TireLocation;
import com.highmobility.utils.ByteUtils;

import javax.annotation.Nullable;

public class TirePressure extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public TirePressure(TireLocation tireLocation, float pressure) {
        super((byte) 0x00, 5);
        value = new Value(tireLocation, pressure);
        bytes[3] = tireLocation.getByte();
        ByteUtils.setBytes(bytes, Property.floatToBytes(pressure), 4);
    }

    public TirePressure(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 2) value = new Value(p);
        return this;
    }

    public class Value {
        TireLocation tireLocation;
        float pressure;

        /**
         * @return The tire location
         */
        public TireLocation getTireLocation() {
            return tireLocation;
        }

        /**
         * @return The tire pressure.
         */
        public float getPressure() {
            return pressure;
        }

        public Value(TireLocation tireLocation, float pressure) {
            this.tireLocation = tireLocation;
            this.pressure = pressure;

        }

        public Value(Property bytes) throws CommandParseException {
            this.tireLocation = TireLocation.fromByte(bytes.get(3));
            this.pressure = Property.getFloat(bytes, 4);
        }
    }
}