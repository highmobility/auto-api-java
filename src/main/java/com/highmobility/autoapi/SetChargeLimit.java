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
 * Set the charge limit, to which point the car will charge itself. The result is sent through the
 * evented Charge State command.
 */
public class SetChargeLimit extends Command {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x03);

    float percentage;

    /**
     * @return The charge limit percentage.
     */
    public float getChargeLimit() {
        return percentage;
    }

    /**
     * Get the set charge limit command bytes.
     *
     * @param percentage The charge limit percentage.
     */
    public SetChargeLimit(float percentage) throws IllegalArgumentException {
        super(Identifier.CHARGING.getBytesWithType(TYPE, (byte) (percentage * 100)));
        this.percentage = percentage;
    }

    SetChargeLimit(byte[] bytes) {
        super(bytes);
        this.percentage = Property.getUnsignedInt(bytes[3]) / 100f;
    }
}
