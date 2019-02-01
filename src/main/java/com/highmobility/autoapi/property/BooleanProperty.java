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

import java.util.Calendar;

import javax.annotation.Nullable;

public class BooleanProperty extends Property {
    Boolean value;

    @Nullable public Boolean getValue() {
        return value;
    }

    public BooleanProperty(byte identifier) {
        super(identifier);
    }

    public BooleanProperty(Boolean value) {
        super(value == null ? 0 : 1);
        update(value);
    }

    public BooleanProperty(@Nullable Boolean value, @Nullable Calendar timestamp,
                           @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public BooleanProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) this.value = getBool(p.getValueByte());
        return this;
    }

    public BooleanProperty update(Boolean value) {
        this.value = value;
        if (value != null) {
            if (bytes.length < 4) bytes = baseBytes(getPropertyIdentifier(), 1);
            bytes[3] = boolToByte(value);
        }
        return this;
    }
}
