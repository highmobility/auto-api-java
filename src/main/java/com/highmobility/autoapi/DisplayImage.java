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

/**
 * Display an image in the head unit by providing the image URL.
 */
public class DisplayImage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.GRAPHICS, 0x00);

    String url;

    /**
     * @return The url of the image that should be loaded to head unit.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url of the image that should be loaded to head unit.
     * @throws IllegalArgumentException If the argument is not valid
     */
    public DisplayImage(String url) {
        super(TYPE, StringProperty.getProperties(url, (byte) 0x01));
        this.url = url;
    }

    DisplayImage(byte[] bytes) {
        super(bytes);
        this.url = Property.getString(bytes, 6, bytes.length - 6);
    }
}
