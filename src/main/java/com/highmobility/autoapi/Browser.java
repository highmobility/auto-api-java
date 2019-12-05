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
 * The Browser capability
 */
public class Browser {
    public static final int IDENTIFIER = Identifier.BROWSER;

    public static final byte PROPERTY_URL = 0x01;

    /**
     * Load url
     */
    public static class LoadUrl extends SetCommand {
        Property<String> url = new Property(String.class, PROPERTY_URL);
    
        /**
         * @return The url
         */
        public Property<String> getUrl() {
            return url;
        }
        
        /**
         * Load url
         *
         * @param url The URL
         */
        public LoadUrl(String url) {
            super(IDENTIFIER);
        
            addProperty(this.url.update(url));
            createBytes();
        }
    
        LoadUrl(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_URL: return url.update(p);
                    }
                    return null;
                });
            }
            if (this.url.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}