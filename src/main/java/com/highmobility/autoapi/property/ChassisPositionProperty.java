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

public class ChassisPositionProperty extends Property {
    Integer position;
    Integer maximumPossibleRate;
    Integer minimumPossibleRate;

    /**
     * @return The chassis position in mm calculated from the lowest point
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @return The maximum possible value for the chassis position
     */
    public Integer getMaximumPossibleValue() {
        return maximumPossibleRate;
    }

    /**
     * @return The minimum value for the chassis position
     */
    public Integer getMinimumPossibleValue() {
        return minimumPossibleRate;
    }

    public ChassisPositionProperty(byte[] bytes) {
        super(bytes);
        position = Property.getSignedInt(bytes[3]);
        maximumPossibleRate = Property.getSignedInt(bytes[4]);
        minimumPossibleRate = Property.getSignedInt(bytes[5]);
    }

    public ChassisPositionProperty(byte identifier, Integer position, Integer
            maximumPossibleRate, Integer minimumPossibleRate) {
        super(identifier, 5);

        bytes[3] = position.byteValue();
        bytes[4] = maximumPossibleRate.byteValue();
        bytes[5] = minimumPossibleRate.byteValue();
    }
}