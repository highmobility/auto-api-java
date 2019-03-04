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
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

public class PropertyComponent extends Bytes {
    byte identifier;
    int length;

    PropertyComponent(Bytes value) throws CommandParseException {
        if (value.getLength() < 3) throw new CommandParseException();
        bytes = value.getByteArray();
        identifier = bytes[0];
        length = Property.getUnsignedInt(bytes, 0, 2);
    }

    PropertyComponent(byte identifier, int length) {
        this.length = length;
        this.identifier = identifier;
        bytes = new byte[3 + length];
        bytes[0] = identifier;
        ByteUtils.setBytes(bytes, Property.intToBytes(CalendarProperty.CALENDAR_SIZE, 2), 1);
    }
}
