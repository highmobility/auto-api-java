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

/**
 * Created by root on 6/29/17.
 */

public class WindscreenDamagePosition {
    int windscreenSizeHorizontal;
    int windscreenSizeVertical;
    int damagePositionX;
    int damagePositionY;

    public int getWindscreenSizeHorizontal() {
        return windscreenSizeHorizontal;
    }

    public int getWindscreenSizeVertical() {
        return windscreenSizeVertical;
    }

    public int getDamagePositionX() {
        return damagePositionX;
    }

    public int getDamagePositionY() {
        return damagePositionY;
    }

    public WindscreenDamagePosition(int windscreenSizeHorizontal, int windscreenSizeVertical, int damagePositionX, int damagePositionY) {
        this.windscreenSizeHorizontal = windscreenSizeHorizontal;
        this.windscreenSizeVertical = windscreenSizeVertical;
        this.damagePositionX = damagePositionX;
        this.damagePositionY = damagePositionY;
    }

    public byte getSizeByte() {
        return (byte) (((windscreenSizeHorizontal & 0x0F) << 4) | (windscreenSizeVertical & 0x0F));
    }

    public byte getPositionByte() {
        return (byte) (((damagePositionX & 0x0F) << 4) | (damagePositionY & 0x0F));
    }
}
