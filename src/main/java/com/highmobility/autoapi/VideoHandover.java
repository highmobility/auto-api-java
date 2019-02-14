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

import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyString;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ScreenLocation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Hand over a video from smart device to car headunit to be shown in the car display. The emulator
 * supports HTML5 video player formats .mp4 and .webm.
 */
public class VideoHandover extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VIDEO_HANDOVER, 0x00);

    public static final byte IDENTIFIER_URL = 0x01;
    public static final byte STARTING_SECOND_IDENTIFIER = 0x02;
    public static final byte IDENTIFIER_SCREEN_LOCATION = 0x03;

    private ObjectPropertyString url = new ObjectPropertyString(IDENTIFIER_URL);
    private ObjectPropertyInteger startingSecond =
            new ObjectPropertyInteger(STARTING_SECOND_IDENTIFIER, false);
    private ObjectProperty<ScreenLocation> location = new ObjectProperty<>(ScreenLocation.class,
            IDENTIFIER_SCREEN_LOCATION);

    /**
     * @return The video url.
     */
    public ObjectPropertyString getUrl() {
        return url;
    }

    /**
     * @return The starting second.
     */
    @Nullable public ObjectPropertyInteger getStartingSecond() {
        return startingSecond;
    }

    /**
     * @return The screen location.
     */
    @Nullable public ObjectProperty<ScreenLocation> getLocation() {
        return location;
    }

    /**
     * @param url            The video url.
     * @param location       The screen location.
     * @param startingSecond The starting second of the video.
     */
    public VideoHandover(String url, @Nullable Integer startingSecond,
                         @Nullable ScreenLocation location) {
        super(TYPE);

        List<Property> properties = new ArrayList<>();

        this.url.update(url);
        properties.add(this.url);

        if (startingSecond != null) {
            this.startingSecond.update(STARTING_SECOND_IDENTIFIER, false, 2, startingSecond);
            properties.add(this.startingSecond);
        }

        if (location != null) {
            this.location.update(location);
            properties.add(this.location);
        }

        createBytes(properties);
    }

    VideoHandover(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_URL:
                        return url.update(p);
                    case STARTING_SECOND_IDENTIFIER:
                        return startingSecond.update(p);
                    case IDENTIFIER_SCREEN_LOCATION:
                        return location.update(p);
                }
                return null;
            });
        }

    }

}
