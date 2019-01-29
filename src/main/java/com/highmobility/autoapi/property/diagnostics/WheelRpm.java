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

public class WheelRpm extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public WheelRpm(byte identifier) {
        super(identifier);
    }

    public WheelRpm(@Nullable Value value, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public WheelRpm(Value value) {
        this((byte) 0x00, value);
    }

    public WheelRpm(byte identifier, Value value) {
        super(identifier, value);

        this.value = value;

        if (value != null) {
            this.bytes[3] = value.tireLocation.getByte();
            ByteUtils.setBytes(bytes, Property.intToBytes(value.rpm, 2), 4);
        }
    }

    public WheelRpm(TireLocation tireLocation, int rpm) {
        this((byte) 0x00, tireLocation, rpm);
    }

    public WheelRpm(byte identifier, TireLocation tireLocation, int rpm) {
        this(identifier, new Value(tireLocation, rpm));
    }

    public WheelRpm(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 3) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
        TireLocation tireLocation;
        int rpm;

        /**
         * @return The wheel location.
         */
        public TireLocation getTireLocation() {
            return tireLocation;
        }

        /**
         * @return The wheel's RPM.
         */
        public int getRpm() {
            return rpm;
        }

        public Value(TireLocation tireLocation, int rpm) {
            this.tireLocation = tireLocation;
            this.rpm = rpm;
        }

        public Value(Property bytes) throws CommandParseException {
            this.tireLocation = TireLocation.fromByte(bytes.get(3));
            this.rpm = Property.getUnsignedInt(bytes, 4, 2);
        }

        @Override public int getLength() {
            return 3;
        }
    }

}
