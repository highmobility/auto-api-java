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

package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.autoapi.property.Property;
import java.util.Enumeration;

class PropertyEnumeration implements Enumeration {
    private int cursor;
    private final byte[] bytes;

    public PropertyEnumeration(byte[] bytes) {
        this(bytes, 3);
    }

    public PropertyEnumeration(byte[] bytes, int cursor) {
        this.bytes = bytes;
        this.cursor = cursor;
    }

    @Override public boolean hasMoreElements() {
        return cursor + 3 <= bytes.length;
    }

    @Override public EnumeratedProperty nextElement() {
        byte propertyIdentifier = bytes[cursor];
        int propertyStart = cursor + 3;
        int propertySize = 0;

        try {
            propertySize = Property.getUnsignedInt(bytes, cursor + 1, 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cursor += 3 + propertySize;
        return new EnumeratedProperty(propertyIdentifier, propertySize, propertyStart);
    }

    class EnumeratedProperty {
        byte identifier;
        int size;
        int valueStart;

        public EnumeratedProperty(byte identifier, int size, int valueStart) {
            this.identifier = identifier;
            this.size = size;
            this.valueStart = valueStart;
        }

        public boolean isValid(int totalCommandLength) {
            return valueStart + size <= totalCommandLength;
        }
    }
}
