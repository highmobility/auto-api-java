/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;

/**
 * Honk flash
 */
public class HonkFlash extends SetCommand {
    public static final Identifier identifier = Identifier.HONK_HORN_FLASH_LIGHTS;

    @Nullable PropertyInteger honkSeconds = new PropertyInteger(0x02, false);
    @Nullable PropertyInteger flashTimes = new PropertyInteger(0x03, false);

    /**
     * @return The honk seconds
     */
    public @Nullable PropertyInteger getHonkSeconds() {
        return honkSeconds;
    }
    
    /**
     * @return The flash times
     */
    public @Nullable PropertyInteger getFlashTimes() {
        return flashTimes;
    }
    
    /**
     * Honk flash
     *
     * @param honkSeconds Number of seconds to honk the horn
     * @param flashTimes Number of times to flash the lights
     */
    public HonkFlash(@Nullable Integer honkSeconds, @Nullable Integer flashTimes) {
        super(identifier);
    
        addProperty(this.honkSeconds.update(false, 1, honkSeconds));
        addProperty(this.flashTimes.update(false, 1, flashTimes), true);
        if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new IllegalArgumentException();
    }

    HonkFlash(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return honkSeconds.update(p);
                    case 0x03: return flashTimes.update(p);
                }
                return null;
            });
        }
        if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new NoPropertiesException();
    }
}