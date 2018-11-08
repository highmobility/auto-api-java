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
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Honk the horn and/or flash the blinker lights. This can be done simultaneously or just one action
 * at the time. It is also possible to pass in how many times the lights should be flashed and how
 * many seconds the horn should be honked.
 */
public class HonkAndFlash extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x12);
    private static final int SECONDS_IDENTIFIER = 0x01;
    private static final int COUNT_IDENTIFIER = 0x02;

    private Integer lightFlashCount;
    private Integer seconds;

    /**
     * @return How many seconds the horn should be honked.
     */
    @Nullable public Integer getSeconds() {
        return seconds;
    }

    /**
     * @return How many times the lights should be flashed.
     */
    @Nullable public Integer getLightFlashCount() {
        return lightFlashCount;
    }

    /**
     * @param seconds         How many seconds the horn should be honked.
     * @param lightFlashCount How many times the lights should be flashed.
     */
    public HonkAndFlash(@Nullable Integer seconds, @Nullable Integer lightFlashCount) {
        super(TYPE, getProperties(seconds, lightFlashCount));
        this.seconds = seconds;
        this.lightFlashCount = lightFlashCount;
    }

    static HMProperty[] getProperties(Integer seconds, Integer lightFlashCount) {
        ArrayList<Property> properties = new ArrayList<>();

        if (seconds != null) {
            IntegerProperty prop = new IntegerProperty((byte) SECONDS_IDENTIFIER, seconds, 1);
            properties.add(prop);
        }

        if (lightFlashCount != null) {
            IntegerProperty prop = new IntegerProperty((byte) COUNT_IDENTIFIER, lightFlashCount, 1);
            properties.add(prop);
        }

        return properties.toArray(new Property[0]);
    }

    HonkAndFlash(byte[] bytes) {
        super(bytes);
        for (Property property : properties) {
            switch (property.getPropertyIdentifier()) {
                case SECONDS_IDENTIFIER:
                    seconds = Property.getUnsignedInt(property.getValueByte());
                    break;
                case COUNT_IDENTIFIER:
                    lightFlashCount = Property.getUnsignedInt(property.getValueByte());
                    break;
            }
        }
    }
}
