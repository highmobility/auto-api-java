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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Notify the car that a message has been received. Depending on the car system, it will display or
 * read it loud to the driver.
 */
public class MessageReceived extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x00);

    /**
     *
     * @param handle The message handle
     * @param message The message
     * @throws UnsupportedEncodingException When strings are of invalid format
     * @throws IllegalArgumentException When all parameters are null
     */
    public MessageReceived(String handle, String message) throws UnsupportedEncodingException,
            IllegalArgumentException {
        super(TYPE, getProperties(handle, message));
    }

    static HMProperty[] getProperties(String handle, String message) throws
            UnsupportedEncodingException {
        ArrayList<HMProperty> properties = new ArrayList<>();

        if (handle != null) {
            StringProperty prop = new StringProperty((byte) 0x01, handle);
            properties.add(prop);
        }

        if (message != null) {
            StringProperty prop = new StringProperty((byte) 0x02, message);
            properties.add(prop);
        }

        return (properties.toArray(new HMProperty[properties.size()]));
    }

    public MessageReceived(Property[] properties) throws CommandParseException,
            IllegalArgumentException {
        super(TYPE, properties);
    }

    MessageReceived(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
