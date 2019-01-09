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

import com.highmobility.value.Bytes;

import java.util.Calendar;

import javax.annotation.Nullable;

public class BooleanProperty extends Property {
    Boolean value;

    @Nullable public Boolean getValue() {
        return value;
    }

    public BooleanProperty(Boolean value) {
        this((byte) 0x00, value);
    }

    public BooleanProperty(@Nullable Boolean value, @Nullable Calendar timestamp,
                           @Nullable PropertyFailure failure) {
        this(value);
        this.timestamp = timestamp;
        this.failure = failure;
    }

    // TODO: this should be internal

    public BooleanProperty(byte identifier, Boolean value) {
        super(identifier, value == null ? 0 : 1);
        this.value = value;
        if (value != null) bytes[3] = Property.boolToByte(value);
    }

    public BooleanProperty(Bytes bytes) {
        super(bytes);
        if (getValueByte() != null) value = Property.getBool(getValueByte());
    }

    // TODO: ^^ should be internal

}
