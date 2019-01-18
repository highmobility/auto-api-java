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
import com.highmobility.autoapi.property.value.Position;

import java.util.Calendar;

import javax.annotation.Nullable;

public class PositionProperty extends Property {
    Position position;

    @Nullable public Position getValue() {
        return position;
    }

    public PositionProperty(Position position) {
        this((byte) 0x00, position);
    }

    public PositionProperty(@Nullable Position position, @Nullable Calendar timestamp,
                            @Nullable PropertyFailure failure) {
        this(position);
        setTimestampFailure(timestamp, failure);
    }

    public PositionProperty() {
        super(unknownBytes);
    }

    public PositionProperty(byte identifier, Position position) {
        super(identifier, position == null ? 0 : 1);
        this.position = position;
        if (position != null) bytes[3] = position.getByte();
    }

    public PositionProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) position = Position.fromByte(p.get(3));
        return this;
    }

    public PositionProperty(byte identifier) {
        super(identifier);
    }

}