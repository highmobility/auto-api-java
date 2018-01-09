package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

/**
 * Honk the horn and/or flash the blinker lights. This can be done simultaneously or just one action
 * at the time. It is also possible to pass in how many times the lights should be flashed and how
 * many seconds the horn should be honked.
 */
public class HonkAndFlash extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x02);

    /**
     *
     * @param seconds How many seconds the horn should be honked
     * @param lightFlashCount How many times the lights should be flashed
     * @throws IllegalArgumentException If both arguments are null
     */
    public HonkAndFlash(Integer seconds, Integer lightFlashCount) {
        super(TYPE, getProperties(seconds, lightFlashCount));
    }

    static HMProperty[] getProperties(Integer seconds, Integer lightFlashCount) {
        ArrayList<Property> properties = new ArrayList<>();

        if (seconds != null) {
            IntProperty prop = new IntProperty((byte) 0x01, seconds, 1);
            properties.add(prop);
        }

        if (lightFlashCount != null) {
            IntProperty prop = new IntProperty((byte) 0x02, lightFlashCount, 1);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    HonkAndFlash(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
