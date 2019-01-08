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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ScreenLocation;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Hand over a video from smart device to car headunit to be shown in the car display. The emulator
 * supports HTML5 video player formats .mp4 and .webm.
 */
public class VideoHandover extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VIDEO_HANDOVER, 0x00);

    public static final byte URL_IDENTIFIER = 0x01;
    public static final byte STARTING_SECOND_IDENTIFIER = 0x02;
    public static final byte IDENTIFIER_SCREEN_LOCATION = 0x03;

    private String url;
    private Integer startingSecond;
    private ScreenLocation location;

    /**
     * @return The video url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return The starting second.
     */
    @Nullable public Integer getStartingSecond() {
        return startingSecond;
    }

    /**
     * @return The screen location.
     */
    @Nullable public ScreenLocation getLocation() {
        return location;
    }

    /**
     * @param url            The video url.
     * @param location       The screen location.
     * @param startingSecond The starting second of the video.
     */
    public VideoHandover(String url, @Nullable Integer startingSecond,
                         @Nullable ScreenLocation location) {
        super(TYPE, getProperties(url, startingSecond, location));
        this.url = url;
        this.startingSecond = startingSecond;
        this.location = location;
    }

    VideoHandover(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (Property p : properties) {
            switch (p.getPropertyIdentifier()) {
                case URL_IDENTIFIER:
                    url = Property.getString(p.getValueBytes());
                    break;
                case STARTING_SECOND_IDENTIFIER:
                    startingSecond = Property.getUnsignedInt(p.getValueBytes());
                    break;
                case IDENTIFIER_SCREEN_LOCATION:
                    location = ScreenLocation.fromByte(p.getValueByte());
                    break;
            }
        }
    }

    static Property[] getProperties(String url, Integer startingSecond, ScreenLocation location) {
        List<Property> propertiesBuilder = new ArrayList<>();

        if (url != null) propertiesBuilder.add(new StringProperty(URL_IDENTIFIER, url));
        if (startingSecond != null) {
            propertiesBuilder.add(new IntegerProperty(STARTING_SECOND_IDENTIFIER, startingSecond,
                    2));
        }
        if (location != null)
            propertiesBuilder.add(new Property(IDENTIFIER_SCREEN_LOCATION, location.getByte()));
        return propertiesBuilder.toArray(new Property[0]);
    }
}
