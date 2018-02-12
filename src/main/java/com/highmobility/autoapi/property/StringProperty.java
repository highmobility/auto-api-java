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

import com.highmobility.utils.Bytes;
import java.io.UnsupportedEncodingException;

public class StringProperty extends Property {
    public static final String CHARSET = "UTF-8";

    public StringProperty(byte identifier, String value) throws UnsupportedEncodingException {
        super(identifier, value.length());
        byte[] stringBytes = value.getBytes(CHARSET);
        Bytes.setBytes(bytes, stringBytes, 3);
    }

    public static HMProperty[] getProperties(String value, byte identifier) throws
            UnsupportedEncodingException {
        HMProperty[] properties = null;

        if (value != null) {
            properties = new HMProperty[1];
            properties[0] = new StringProperty(identifier, value);
        }

        return properties;
    }
}