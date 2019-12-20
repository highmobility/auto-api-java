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
 * The Graphics capability
 */
public class Graphics {
    public static final int IDENTIFIER = Identifier.GRAPHICS;

    public static final byte PROPERTY_IMAGE_URL = 0x01;

    /**
     * Display image
     */
    public static class DisplayImage extends SetCommand {
        Property<String> imageURL = new Property(String.class, PROPERTY_IMAGE_URL);
    
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
        
            addProperty(this.imageURL.update(imageURL));
            createBytes();
        }
    
        DisplayImage(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_IMAGE_URL: return imageURL.update(p);
                    }
                    return null;
                });
            }
            if (this.imageURL.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}