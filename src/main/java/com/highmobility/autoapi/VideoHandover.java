package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.ScreenLocation;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hand over a video from smart device to car headunit to be shown in the car display. The emulator
 * supports HTML5 video player formats .mp4 and .webm.
 */
public class VideoHandover extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VIDEO_HANDOVER, 0x00);

    /**
     * @param url The video url
     * @param location The screen location
     * @param startingSecond The starting second of the video
     * @throws UnsupportedEncodingException The url is not in UTF-8
     * @throws IllegalArgumentException When both arguments are null
     */
    public VideoHandover(String url, Integer startingSecond, ScreenLocation location) throws
            UnsupportedEncodingException {
        super(TYPE, getProperties(url, startingSecond, location));
    }

    VideoHandover(byte[] bytes) throws CommandParseException {
        super(bytes);
    }

    static HMProperty[] getProperties(String url, Integer startingSecond, ScreenLocation location) throws
            UnsupportedEncodingException {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (url != null) propertiesBuilder.add(new StringProperty((byte) 0x01, url));
        if (startingSecond != null) propertiesBuilder.add(new IntProperty((byte) 0x02, startingSecond, 2));
        if (location != null) propertiesBuilder.add(location);

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }
}
