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
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

import javax.annotation.Nullable;

public class CoordinatesProperty extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public CoordinatesProperty(byte identifier) {
        super(identifier);
    }

    public CoordinatesProperty(@Nullable Value value, @Nullable Calendar timestamp,
                               @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public CoordinatesProperty(Value value) {
        super(value);

        this.value = value;

        if (value != null) {
            ByteUtils.setBytes(bytes, Property.doubleToBytes(value.latitude), 3);
            ByteUtils.setBytes(bytes, Property.doubleToBytes(value.longitude), 11);
        }
    }

    public CoordinatesProperty(double latitude, double longitude) {
        this(new Value(latitude, longitude));
    }

    public CoordinatesProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 16) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
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

        public Value(Property bytes) {
            latitude = Property.getDouble(bytes, 3);
            longitude = Property.getDouble(bytes, 11);
        }

        public Value(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override public int getLength() {
            return 16;
        }
    }

}