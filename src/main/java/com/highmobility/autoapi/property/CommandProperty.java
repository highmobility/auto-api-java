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

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * The possible control mode values.
 */
public class CommandProperty extends Property {
    CommandWithProperties value;

    @Nullable public CommandWithProperties getValue() {
        return value;
    }

    public CommandProperty() {
        super();
    }

    public CommandProperty(byte identifier) {
        super(identifier);
    }

    public CommandProperty(@Nullable CommandWithProperties value, @Nullable Calendar timestamp,
                           @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public CommandProperty(@Nullable CommandWithProperties value) {
        super((PropertyValue) value);
        this.value = value;

        if (value != null) {
            ByteUtils.setBytes(bytes, value.getByteArray(), 3);
            // TBODO: test for historical state
        }
    }

    public CommandProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);

        if (p.getValueLength() > 5) {
            Command command = CommandResolver.resolve(p.getValueBytes());
            if (command instanceof CommandWithProperties)
                value = (CommandWithProperties) command;
        }

        return this;
    }
}