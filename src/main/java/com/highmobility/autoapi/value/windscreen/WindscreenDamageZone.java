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

package com.highmobility.autoapi.value.windscreen;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class WindscreenDamageZone extends PropertyValueObject {
    int damageZoneX;
    int damageZoneY;


    /**
     * @return The horizontal position of the zone in a matrix seen from the inside of the car,
     * starting from index 1
     */
    public int getDamageZoneX() {
        return damageZoneX;
    }

    /**
     * @return The vertical position of the zone from the bottom, starting from index 1
     */
    public int getDamageZoneY() {
        return damageZoneY;
    }

    public WindscreenDamageZone(int damageZoneX, int damageZoneY) {
        super(1);
        update(damageZoneX, damageZoneY);
    }

    public WindscreenDamageZone() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 1) throw new CommandParseException();

        damageZoneX = get(0) >> 4;
        damageZoneY = get(0) & 0x0F;
    }

    public void update(int damageZoneX, int damageZoneY) {
        this.damageZoneX = damageZoneX;
        this.damageZoneY = damageZoneY;
        bytes = new byte[1];
        set(0, (byte) (((damageZoneX & 0x0F) << 4) | (damageZoneY & 0x0F)));

    }

    public void update(WindscreenDamageZone value) {
        update(value.damageZoneX, value.damageZoneY);
    }
}
