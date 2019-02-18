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

package com.highmobility.autoapi.property;

public class WindscreenDamageZone extends Property {
    public static final byte IDENTIFIER = 0x05;

    int damageZoneX;
    int damageZoneY;

    public WindscreenDamageZone(byte valueByte) {
        super(IDENTIFIER, 1);
        damageZoneX = valueByte >> 4;
        damageZoneY = valueByte & 0x0F;
        setByteValue();
    }

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
        super(IDENTIFIER, 1);
        this.damageZoneX = damageZoneX;
        this.damageZoneY = damageZoneY;
        setByteValue();
    }

    private void setByteValue() {
        bytes[6] = (byte) (((damageZoneX & 0x0F) << 4) | (damageZoneY & 0x0F));
    }

    @Override public byte getPropertyIdentifier() {
        return IDENTIFIER;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), bytes[6]);
    }
}
