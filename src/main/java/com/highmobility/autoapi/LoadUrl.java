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
 * Load a URL in the head unit browser. A URL shortener can be used in other cases. Note that for the
 * car emulator the URL has to be for a secure site (HTTPS).
 */
public class LoadUrl extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.BROWSER, 0x00);

    /**
     * @param url The url that will be loaded to head unit
     * @throws UnsupportedEncodingException The url is not in UTF-8
     * @throws IllegalArgumentException        If the argument is not valid
     */
    public LoadUrl(String url) throws UnsupportedEncodingException {
        super(TYPE, StringProperty.getProperties(url, (byte) 0x01));
    }

    LoadUrl(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
