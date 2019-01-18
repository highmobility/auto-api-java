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

/**
 * {@link #getValue()} is float 0-1. {@link #getValueByte()} is 0-100 int.
 */
public class PercentageProperty extends Property {
    Float value;

    /**
     * @return Percentage between 0 and 1.
     */
    @Nullable public Float getValue() {
        return value;
    }

    public PercentageProperty(Float value) {
        this((byte) 0x00, value);
    }

    public PercentageProperty(@Nullable Float value, @Nullable Calendar timestamp,
                              @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public PercentageProperty(byte identifier, Float value) {
        super(identifier, value == null ? 0 : 1);
        this.value = value;
        if (value != null) bytes[3] = floatToIntPercentageByte(value);
    }

    public PercentageProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) this.value = getUnsignedInt(p.getValueByte()) / 100f;
        return this;
    }

    public PercentageProperty(byte identifier) {
        super(identifier);
    }
}