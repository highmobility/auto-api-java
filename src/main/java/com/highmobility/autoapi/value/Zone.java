/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Zone extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer horizontal;
    Integer vertical;

    /**
     * @return The horizontal.
     */
    public Integer getHorizontal() {
        return horizontal;
    }

    /**
     * @return The vertical.
     */
    public Integer getVertical() {
        return vertical;
    }

    public Zone(Integer horizontal, Integer vertical) {
        super(2);
        update(horizontal, vertical);
    }

    public Zone(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Zone() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        horizontal = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        vertical = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer horizontal, Integer vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(horizontal, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(vertical, 1));
    }

    public void update(Zone value) {
        update(value.horizontal, value.vertical);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}