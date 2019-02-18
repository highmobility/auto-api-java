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

public class SpringRateProperty extends Property {
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

    public SpringRateProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 8) throw new CommandParseException();
        axle = Axle.fromByte(bytes[6]);
        springRate = Property.getUnsignedInt(bytes[7]);
    }

    public SpringRateProperty(Axle axle, Integer springRate) {
        this((byte) 0x00, axle, springRate);
    }

    public SpringRateProperty(byte identifier, Axle axle, Integer springRate) {
        super(identifier, 2);
        bytes[6] = axle.getByte();
        bytes[7] = springRate.byteValue();
    }
}