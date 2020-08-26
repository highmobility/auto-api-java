/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
        Property url = new Property<>(String.class, PROPERTY_URL);
    
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