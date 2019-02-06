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

import java.util.Calendar;

import javax.annotation.Nullable;

public class ObjectPropertyPercentage extends ObjectProperty<Integer> {
    private static final String INVALID_RANGE_EXCEPTION = "Percentage in invalid range";

    public ObjectPropertyPercentage(@Nullable Integer value, @Nullable Calendar timestamp,
                                    @Nullable PropertyFailure failure) {
        super(value, timestamp, failure);

        // TODO: 2019-02-06 should verify in ctors if is 0-100. add test as well
        checkValue(value);
    }

    public ObjectPropertyPercentage(@Nullable Integer value) {
        super(value);
        checkValue(value);
    }

    public ObjectPropertyPercentage(byte identifier, Integer value) {
        super(identifier, value);
        checkValue(value);
    }

    public ObjectPropertyPercentage(byte identifier) {
        super(Integer.class, identifier);
    }

    public ObjectPropertyPercentage(Property property) throws CommandParseException {
        super(Integer.class, property);
    }

    @Override protected Bytes getBytes(Integer value) {
        checkValue(value);
        return new Bytes(new byte[]{value.byteValue()});
    }

    @Override public ObjectProperty update(Property p) throws CommandParseException {
        super.update(p);

        Byte value = p.getValueByte();

        if (value != null) this.value = ensureRange(value);

        return this;
    }

    private boolean inRange(int value) {
        return (value >= 0) && (value <= 100);
    }

    private int ensureRange(int value) {
        return Math.min(Math.max(value, 0), 100);
    }

    private void checkValue(@Nullable Integer value) {
        if (value != null && inRange(value) == false) throw new IllegalArgumentException("INVALID_RANGE_EXCEPTION");
    }
}
