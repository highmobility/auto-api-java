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

import com.highmobility.autoapi.property.ObjectPropertyInteger;
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
    private static final byte IDENTIFIER_SECONDS = 0x01;
    private static final byte IDENTIFIER_COUNT = 0x02;

    private ObjectPropertyInteger lightFlashCount = new ObjectPropertyInteger(IDENTIFIER_COUNT,
            false);
    private ObjectPropertyInteger seconds = new ObjectPropertyInteger(IDENTIFIER_SECONDS, false);

    /**
     * @return How many seconds the horn should be honked.
     */
    @Nullable public Integer getSeconds() {
        return seconds.getValue();
    }

    /**
     * @return How many times the lights should be flashed.
     */
    @Nullable public Integer getLightFlashCount() {
        return lightFlashCount.getValue();
    }

    /**
     * @param seconds         How many seconds the horn should be honked.
     * @param lightFlashCount How many times the lights should be flashed.
     */
    public HonkAndFlash(@Nullable Integer seconds, @Nullable Integer lightFlashCount) {
        super(TYPE);

        ArrayList<Property> properties = new ArrayList<>();

        if (seconds != null) {
            this.seconds = new ObjectPropertyInteger((byte) IDENTIFIER_SECONDS, false, 1, seconds);
            properties.add(this.seconds);
        }

        if (lightFlashCount != null) {
            this.lightFlashCount = new ObjectPropertyInteger((byte) IDENTIFIER_COUNT, false,
                    1, lightFlashCount);
            properties.add(this.lightFlashCount);
        }

        createBytes(properties);
    }

    HonkAndFlash(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_SECONDS:
                        return seconds.update(p);
                    case IDENTIFIER_COUNT:
                        return lightFlashCount.update(p);
                }
                return null;
            });
        }
    }
}
