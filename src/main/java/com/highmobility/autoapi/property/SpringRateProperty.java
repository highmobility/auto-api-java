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
import com.highmobility.autoapi.property.value.Axle;

import javax.annotation.Nullable;

public class SpringRateProperty extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public SpringRateProperty(Axle axle, Integer springRate) {
        this((byte) 0x00, axle, springRate);
    }

    public SpringRateProperty(byte identifier, Axle axle, Integer springRate) {
        super(identifier, 2);
        bytes[3] = axle.getByte();
        bytes[4] = springRate.byteValue();
    }

    public SpringRateProperty(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) value = new Value(p);
        return super.update(p, failure, timestamp, propertyInArray);
    }

    public static class Value {
        Axle axle;
        Integer springRate;

        /**
         * @return The axle.
         */
        public Axle getAxle() {
            return axle;
        }

        /**
         * @return The suspension spring rate in N/mm
         */
        public Integer getSpringRate() {
            return springRate;
        }

        public Value(Property bytes) throws CommandParseException {
            axle = Axle.fromByte(bytes.get(3));
            springRate = Property.getUnsignedInt(bytes.get(4));
        }

        public Value(Axle axle, Integer springRate) {
            this.axle = axle;
            this.springRate = springRate;
        }
    }
}