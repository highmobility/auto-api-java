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
import com.highmobility.utils.Bytes;

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

    public CoordinatesProperty(byte[] bytes) {
        super(bytes);

        latitude = Property.getFloat(bytes, 3);
        longitude = Property.getFloat(bytes, 7);
    }
    
    public CoordinatesProperty(float latitude, float longitude) {
        this((byte) 0x00, latitude, longitude);
    }

    public CoordinatesProperty(byte identifier, float latitude, float longitude) {
        super(identifier, 8);
        Bytes.setBytes(bytes, Property.floatToBytes(latitude), 3);
        Bytes.setBytes(bytes, Property.floatToBytes(longitude), 7);
        this.latitude = latitude;
        this.longitude = longitude;
    }
}