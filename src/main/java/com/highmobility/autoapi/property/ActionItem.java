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
import com.highmobility.utils.Bytes;

public class ActionItem implements HMProperty {
    byte[] bytes;
    int actionIdentifier;
    String name;

    /**
     *
     * @return The action item identifier
     */
    public int getActionIdentifier() {
        return actionIdentifier;
    }

    /**
     *
     * @return Name of the action item
     */
    public String getName() {
        return name;
    }

    public ActionItem(int actionIdentifier, String name) {
        this.actionIdentifier = actionIdentifier;
        this.name = name;

        bytes = new byte[]{ getPropertyIdentifier() };
        bytes = Bytes.concatBytes(bytes, Property.intToBytes(getPropertyLength(), 2));
        bytes = Bytes.concatBytes(bytes, (byte) getActionIdentifier());
        bytes = Bytes.concatBytes(bytes, Property.stringToBytes(name));
    }

    public ActionItem(byte[] bytes) throws CommandParseException {
        if (bytes.length < 5) throw new CommandParseException();
        this.bytes = bytes;

        actionIdentifier = bytes[3];
        name = Property.getString(bytes, 4, bytes.length - 4);
    }

    @Override public byte getPropertyIdentifier() {
        return 0x02;
    }

    @Override public int getPropertyLength() {
        return 1 + name.length();
    }

    @Override public byte[] getPropertyBytes()  {
        return bytes;
    }
}
