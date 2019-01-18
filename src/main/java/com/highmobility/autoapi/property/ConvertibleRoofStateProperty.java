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
import com.highmobility.autoapi.property.value.rooftop.ConvertibleRoofState;

import java.util.Calendar;

import javax.annotation.Nullable;

public class ConvertibleRoofStateProperty extends Property {
    ConvertibleRoofState convertibleRoofState;

    public ConvertibleRoofState getValue() {
        return convertibleRoofState;
    }

    public ConvertibleRoofStateProperty(ConvertibleRoofState convertibleRoofState) {
        this((byte) 0x00, convertibleRoofState);
    }

    public ConvertibleRoofStateProperty(@Nullable ConvertibleRoofState convertibleRoofStateProperty, @Nullable Calendar timestamp,
                                        @Nullable PropertyFailure failure) {
        this(convertibleRoofStateProperty);
        setTimestampFailure(timestamp, failure);
    }

    public ConvertibleRoofStateProperty(byte identifier) {
        super(identifier);
    }

    public ConvertibleRoofStateProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    public ConvertibleRoofStateProperty(byte identifier,
                                        ConvertibleRoofState convertibleRoofState) {
        super(identifier, convertibleRoofState == null ? 0 : 1);
        this.convertibleRoofState = convertibleRoofState;
        if (convertibleRoofState != null) bytes[3] = convertibleRoofState.getByte();
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) convertibleRoofState = ConvertibleRoofState.fromByte(p.get(3));
        return this;
    }

    // TBODO: ctors

}

