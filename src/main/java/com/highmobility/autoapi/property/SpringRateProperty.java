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
import java.io.UnsupportedEncodingException;

public class SpringRateProperty extends Property {
    Axle axle;
    Integer springRate;
    Integer maximumPossibleRate;
    Integer minimumPossibleRate;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     *
     * @return The suspension spring rate in N/mm
     */
    public Integer getSpringRate() {
        return springRate;
    }

    /**
     *
     * @return The maximum possible value for the spring rate
     */
    public Integer getMaximumPossibleRate() {
        return maximumPossibleRate;
    }

    /**
     *
     * @return The minimum possible value for the spring rate
     */
    public Integer getMinimumPossibleRate() {
        return minimumPossibleRate;
    }

    public SpringRateProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        axle = Axle.fromByte(bytes[3]);
        springRate = Property.getUnsignedInt(bytes[4]);
        maximumPossibleRate = Property.getUnsignedInt(bytes[5]);
        minimumPossibleRate = Property.getUnsignedInt(bytes[6]);
    }

    public SpringRateProperty(byte identifier, Axle type, Integer springRate, Integer
            maximumPossibleRate, Integer minimumPossibleRate) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = type.getByte();
        bytes[4] = springRate.byteValue();
        bytes[5] = maximumPossibleRate.byteValue();
        bytes[6] = minimumPossibleRate.byteValue();
    }
}