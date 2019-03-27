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

import com.highmobility.autoapi.property.Property;

/**
 * Display an image in the head unit by providing the image URL.
 */
public class DisplayImage extends Command {
    public static final Type TYPE = new Type(Identifier.GRAPHICS, 0x00);
    private static final byte IDENTIFIER = 0x01;

    Property<String> url = new Property(String.class, IDENTIFIER);

    /**
     * @return The url of the image that should be loaded to head unit.
     */
    public Property<String> getUrl() {
        return url;
    }

    /**
     * @param url The url of the image that should be loaded to head unit.
     */
    public DisplayImage(String url) {
        super(TYPE);
        this.url.update(url);
        createBytes(this.url);
    }

    DisplayImage(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return url.update(p);
                }
                return null;
            });
        }
    }

    @Override protected boolean propertiesExpected() {
        return true;
    }
}
