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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class HmkitVersion extends PropertyValueObject {
    public static final int SIZE = 3;

    Integer major;
    Integer minor;
    Integer patch;

    /**
     * @return HMKit version major number.
     */
    public Integer getMajor() {
        return major;
    }

    /**
     * @return HMKit version minor number.
     */
    public Integer getMinor() {
        return minor;
    }

    /**
     * @return HMKit version patch number.
     */
    public Integer getPatch() {
        return patch;
    }

    public HmkitVersion(Integer major, Integer minor, Integer patch) {
        super(3);
        update(major, minor, patch);
    }

    public HmkitVersion(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public HmkitVersion() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        major = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        minor = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        patch = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer major, Integer minor, Integer patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(major, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(minor, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(patch, 1));
    }

    public void update(HmkitVersion value) {
        update(value.major, value.minor, value.patch);
    }

    @Override public int getLength() {
        return 1 + 1 + 1;
    }
}