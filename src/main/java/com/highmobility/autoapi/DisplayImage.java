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
 * Display image
 */
public class DisplayImage extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.GRAPHICS;

    public static final byte IDENTIFIER_IMAGE_URL = 0x01;

    Property<String> imageURL = new Property(String.class, IDENTIFIER_IMAGE_URL);

    /**
     * @return The image url
     */
    public Property<String> getImageURL() {
        return imageURL;
    }
    
    /**
     * Display image
     *
     * @param imageURL The image URL
     */
    public DisplayImage(String imageURL) {
        super(IDENTIFIER);
    
        addProperty(this.imageURL.update(imageURL), true);
    }

    DisplayImage(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_IMAGE_URL: return imageURL.update(p);
                }
                return null;
            });
        }
        if (this.imageURL.getValue() == null) 
            throw new NoPropertiesException();
    }
}