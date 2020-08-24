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
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.property.ByteEnum;
import javax.annotation.Nullable;

import static com.highmobility.utils.ByteUtils.hexFromByte;

/**
 * The Video Handover capability
 */
public class VideoHandover {
    public static final int IDENTIFIER = Identifier.VIDEO_HANDOVER;

    public static final byte PROPERTY_URL = 0x01;
    public static final byte PROPERTY_STARTING_SECOND = 0x02;
    public static final byte PROPERTY_SCREEN = 0x03;
    public static final byte PROPERTY_STARTING_TIME = 0x04;

    /**
     * Video handover command
     */
    public static class VideoHandoverCommand extends SetCommand {
        Property<String> url = new Property(String.class, PROPERTY_URL);
        Property<Screen> screen = new Property(Screen.class, PROPERTY_SCREEN);
        Property<Duration> startingTime = new Property(Duration.class, PROPERTY_STARTING_TIME);
    
        /**
         * @return The url
         */
        public Property<String> getUrl() {
            return url;
        }
        
        /**
         * @return The screen
         */
        public Property<Screen> getScreen() {
            return screen;
        }
        
        /**
         * @return The starting time
         */
        public Property<Duration> getStartingTime() {
            return startingTime;
        }
        
        /**
         * Video handover command
         *
         * @param url URL string
         * @param screen The screen
         * @param startingTime Start the video from the given time
         */
        public VideoHandoverCommand(String url, @Nullable Screen screen, @Nullable Duration startingTime) {
            super(IDENTIFIER);
        
            addProperty(this.url.update(url));
            addProperty(this.screen.update(screen));
            addProperty(this.startingTime.update(startingTime));
            createBytes();
        }
    
        VideoHandoverCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_URL: return url.update(p);
                        case PROPERTY_SCREEN: return screen.update(p);
                        case PROPERTY_STARTING_TIME: return startingTime.update(p);
                    }
                    return null;
                });
            }
            if (this.url.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    public enum Screen implements ByteEnum {
        FRONT((byte) 0x00),
        REAR((byte) 0x01);
    
        public static Screen fromByte(byte byteValue) throws CommandParseException {
            Screen[] values = Screen.values();
    
            for (int i = 0; i < values.length; i++) {
                Screen state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum Screen does not contain " + hexFromByte(byteValue));
        }
    
        private byte value;
    
        Screen(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}