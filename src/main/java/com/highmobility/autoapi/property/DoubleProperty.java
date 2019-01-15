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
import com.highmobility.value.Bytes;

import java.util.Calendar;

import javax.annotation.Nullable;

public class DoubleProperty extends Property {
    Double value;

    public Double getValue() {
        return value;
    }

    public DoubleProperty(Double value) {
        this((byte) 0x00, value);
    }

    public DoubleProperty(@Nullable Double value, @Nullable Calendar timestamp,
                          @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public DoubleProperty(byte identifier, Double value) {
        super(identifier, value == null ? 0 : 8);
        this.value = value;
        if (value != null) ByteUtils.setBytes(bytes, doubleToBytes(value), 3);
    }

    public DoubleProperty(Bytes bytes) {
        super(bytes);
        value = Property.getDouble(getValueBytes());
    }
}
