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

public class IntegerProperty extends Property {
    public IntegerProperty(byte identifier, int value, int length) {
        super(identifier, length);

        if (length == 1) {
            bytes[3] = (byte) value;
        } else {
            ByteUtils.setBytes(bytes, intToBytes(value, length), 3);
        }
    }

    public IntegerProperty(int value) {
        this((byte) 0x00, value, 4); // TODO: 2019-01-03 when used in builders, builders need to
        // reset the length bytes (builder knows the length)
        // default is 4
    }
}
