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

import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;

/**
 * Send a keystroke or entire sentences as input to the car head unit. This can act as an alternative
 * to the input devices that the car is equipped with.
 */
public class TextInput extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TEXT_INPUT, 0x00);

    /**
     * @param text The text
     * @throws UnsupportedEncodingException The url is not in UTF-8
     * @throws IllegalArgumentException        If the argument is not valid
     */
    public TextInput(String text) throws UnsupportedEncodingException {
        super(TYPE, StringProperty.getProperties(text));
    }

    TextInput(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}