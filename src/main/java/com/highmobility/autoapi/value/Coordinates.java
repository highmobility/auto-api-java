/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Coordinates extends PropertyValueObject {
    public static final int SIZE = 16;

    Double latitude;
    Double longitude;

    /**
     * @return Latitude.
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return Longitude.
     */
    public Double getLongitude() {
        return longitude;
    }

    public Coordinates(Double latitude, Double longitude) {
        super(16);
        update(latitude, longitude);
    }

    public Coordinates(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Coordinates() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 16) throw new CommandParseException();

        int bytePosition = 0;
        latitude = Property.getDouble(bytes, bytePosition);
        bytePosition += 8;

        longitude = Property.getDouble(bytes, bytePosition);
    }

    public void update(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.doubleToBytes(latitude));
        bytePosition += 8;

        set(bytePosition, Property.doubleToBytes(longitude));
    }

    public void update(Coordinates value) {
        update(value.latitude, value.longitude);
    }

    @Override public int getLength() {
        return 8 + 8;
    }
}