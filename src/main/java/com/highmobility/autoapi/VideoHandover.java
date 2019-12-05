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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;

/**
 * The Video Handover capability
 */
public class VideoHandover {
    public static final int IDENTIFIER = Identifier.VIDEO_HANDOVER;

    public static final byte PROPERTY_URL = 0x01;
    public static final byte PROPERTY_STARTING_SECOND = 0x02;
    public static final byte PROPERTY_SCREEN = 0x03;

    /**
     * Video handover command
     */
    public static class VideoHandoverCommand extends SetCommand {
        Property<String> url = new Property(String.class, PROPERTY_URL);
        PropertyInteger startingSecond = new PropertyInteger(PROPERTY_STARTING_SECOND, false);
        Property<Screen> screen = new Property(Screen.class, PROPERTY_SCREEN);
    
        /**
         * @return The url
         */
        public Property<String> getUrl() {
            return url;
        }
        
        /**
         * @return The starting second
         */
        public PropertyInteger getStartingSecond() {
            return startingSecond;
        }
        
        /**
         * @return The screen
         */
        public Property<Screen> getScreen() {
            return screen;
        }
        
        /**
         * Video handover command
         *
         * @param url URL string
         * @param startingSecond The starting second
         * @param screen The screen
         */
        public VideoHandoverCommand(String url, @Nullable Integer startingSecond, @Nullable Screen screen) {
            super(IDENTIFIER);
        
            addProperty(this.url.update(url));
            addProperty(this.startingSecond.update(false, 2, startingSecond));
            addProperty(this.screen.update(screen));
            createBytes();
        }
    
        VideoHandoverCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_URL: return url.update(p);
                        case PROPERTY_STARTING_SECOND: return startingSecond.update(p);
                        case PROPERTY_SCREEN: return screen.update(p);
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
    
            throw new CommandParseException();
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