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

public class CoordinatesProperty extends Property {
    float latitude;
    float longitude;

    /**
     *
     * @return The latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude
     */
    public float getLongitude() {
        return longitude;
    }

    public CoordinatesProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        latitude = Property.getFloat(bytes, 3);
        longitude = Property.getFloat(bytes, 7);
    }

    public CoordinatesProperty(byte identifier, Axle type, Integer springRate, Integer
            maximumPossibleRate, Integer minimumPossibleRate) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = type.getByte();
        bytes[4] = springRate.byteValue();
        bytes[5] = maximumPossibleRate.byteValue();
        bytes[6] = minimumPossibleRate.byteValue();
    }
}