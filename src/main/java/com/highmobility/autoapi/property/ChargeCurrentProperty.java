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

public class ChargeCurrentProperty extends Property {
    float chargeCurrent;
    float maximumValue;
    float minimumValue;

    /**
     * @return The charge current (DC)
     */
    public float getChargeCurrent() {
        return chargeCurrent;
    }

    /**
     * @return The maximum possible value for the charge current
     */
    public float getMaximumValue() {
        return maximumValue;
    }

    /**
     * @return The minimum possible value for the charge current
     */
    public float getMinimumValue() {
        return minimumValue;
    }

    public ChargeCurrentProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        chargeCurrent = Property.getFloat(bytes, 3);
        maximumValue = Property.getFloat(bytes, 7);
        minimumValue = Property.getFloat(bytes, 11);
    }
}