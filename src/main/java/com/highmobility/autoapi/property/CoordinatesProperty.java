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
import com.highmobility.utils.ByteUtils;

public class CoordinatesProperty extends Property {
    double latitude;
    double longitude;

    /**
     *
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    public CoordinatesProperty(byte[] bytes) {
        super(bytes);

        latitude = Property.getDouble(bytes, 3);
        longitude = Property.getDouble(bytes, 11);
    }
    
    public CoordinatesProperty(float latitude, float longitude) {
        this((byte) 0x00, latitude, longitude);
    }

    public CoordinatesProperty(byte identifier, double latitude, double longitude) {
        super(identifier, 16);
        ByteUtils.setBytes(bytes, Property.doubleToBytes(latitude), 3);
        ByteUtils.setBytes(bytes, Property.doubleToBytes(longitude), 11);
        this.latitude = latitude;
        this.longitude = longitude;
    }
}