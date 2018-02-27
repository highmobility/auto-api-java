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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.ScreenLocation;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Hand over a video from smart device to car headunit to be shown in the car display. The emulator
 * supports HTML5 video player formats .mp4 and .webm.
 */
public class VideoHandover extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.VIDEO_HANDOVER, 0x00);

    /**
     * @param url            The video url
     * @param location       The screen location
     * @param startingSecond The starting second of the video
     * @throws IllegalArgumentException When both arguments are null
     */
    public VideoHandover(String url, Integer startingSecond, ScreenLocation location) {
        super(TYPE, getProperties(url, startingSecond, location));
    }

    VideoHandover(byte[] bytes) {
        super(bytes);
    }

    static HMProperty[] getProperties(String url, Integer startingSecond, ScreenLocation location) {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (url != null) propertiesBuilder.add(new StringProperty((byte) 0x01, url));
        if (startingSecond != null)
            propertiesBuilder.add(new IntegerProperty((byte) 0x02, startingSecond, 2));
        if (location != null) propertiesBuilder.add(location);

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }
}
