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
 * Video handover
 */
public class VideoHandover extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.VIDEO_HANDOVER;

    public static final byte IDENTIFIER_URL = 0x01;
    public static final byte IDENTIFIER_STARTING_SECOND = 0x02;
    public static final byte IDENTIFIER_SCREEN = 0x03;

    Property<String> url = new Property(String.class, IDENTIFIER_URL);
    @Nullable PropertyInteger startingSecond = new PropertyInteger(IDENTIFIER_STARTING_SECOND, false);
    @Nullable Property<Screen> screen = new Property(Screen.class, IDENTIFIER_SCREEN);

    /**
     * @return The url
     */
    public Property<String> getUrl() {
        return url;
    }
    
    /**
     * @return The starting second
     */
    public @Nullable PropertyInteger getStartingSecond() {
        return startingSecond;
    }
    
    /**
     * @return The screen
     */
    public @Nullable Property<Screen> getScreen() {
        return screen;
    }
    
    /**
     * Video handover
     *
     * @param url URL string
     * @param startingSecond The starting second
     * @param screen The screen
     */
    public VideoHandover(String url, @Nullable Integer startingSecond, @Nullable Screen screen) {
        super(IDENTIFIER);
    
        addProperty(this.url.update(url));
        addProperty(this.startingSecond.update(false, 2, startingSecond));
        addProperty(this.screen.update(screen), true);
    }

    VideoHandover(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_URL: return url.update(p);
                    case IDENTIFIER_STARTING_SECOND: return startingSecond.update(p);
                    case IDENTIFIER_SCREEN: return screen.update(p);
                }
                return null;
            });
        }
        if (this.url.getValue() == null) 
            throw new NoPropertiesException();
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