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

import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

public class PropertyComponent extends Bytes {
    byte identifier;
    int length;
    Bytes valueBytes;

    PropertyComponent(Bytes value) {
        if (value.getLength() < 3) throw new ParseException();
        bytes = value.getByteArray();
        identifier = bytes[0];
        length = Property.getUnsignedInt(bytes, 1, 2);
        valueBytes = value;
    }

    PropertyComponent(byte identifier, Bytes value) {
        this(identifier, value.getLength());
        valueBytes = value;
        set(3, value);
    }

    // property header:010015 component header: 010012 value: 68747470733a2f2f676f6f676c652e636f6d
    // 01001B 010018 01001501001268747470733A2F2F676F6F676C652E636F6D
    PropertyComponent(byte identifier, int valueSize) {
        this.length = valueSize;
        this.identifier = identifier;
        bytes = new byte[3 + valueSize];
        // component identifier
        bytes[0] = identifier;
        // component length
        ByteUtils.setBytes(bytes, Property.intToBytes(valueSize, 2), 1); // value length
    }
}
