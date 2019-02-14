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
import com.highmobility.value.Bytes;

public class Coordinates extends PropertyValueObject {
    double latitude;
    double longitude;

    /**
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    public Coordinates(double latitude, double longitude) {
        super(16);
        update(latitude, longitude);
    }

    public Coordinates() {
        super(16);
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 16) throw new CommandParseException();
        latitude = Property.getDouble(getRange(0, 8));
        longitude = Property.getDouble(getRange(8, 16));
    }

    public void update(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        set(0, Property.doubleToBytes(latitude));
        set(8, Property.doubleToBytes(longitude));
    }

    public void update(Coordinates coordinates) {
        update(coordinates.latitude, coordinates.longitude);
    }
}