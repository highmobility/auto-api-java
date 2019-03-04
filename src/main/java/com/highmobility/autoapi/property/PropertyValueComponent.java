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

import javax.annotation.Nullable;

public class PropertyValueComponent<T> extends PropertyComponent {
    private static final byte IDENTIFIER = 0x01;

    Bytes valueBytes = new Bytes();
    @Nullable protected T value;

    @Nullable public T getValue() {
        return value;
    }

    public Bytes getValueBytes() {
        return valueBytes;
    }

    public PropertyValueComponent(Bytes value) throws CommandParseException {
        super(value);
        if (length > 0) {
            this.valueBytes = value.getRange(3, value.getLength());
        }
    }

    // TODO: 2019-02-28  
}
