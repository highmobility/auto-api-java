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

import com.highmobility.autoapi.CommandParseException;

public class ColorProperty extends Property {
    public ColorProperty(int[] ambientColor) {
        this((byte) 0x00, ambientColor);
    }

    public int[] getAmbientColor() {
        return ambientColor;
    }

    int[] ambientColor;

    public ColorProperty(byte identifier, int[] ambientColor) {
        super(identifier, 3);

        if (ambientColor.length != 4 && ambientColor.length != 3) {
            // maybe later will add alpha as well
            throw new IllegalArgumentException("Need rgb or rgba color values");
        }

        bytes[3] = (byte) ambientColor[0];
        bytes[4] = (byte) ambientColor[1];
        bytes[5] = (byte) ambientColor[2];

        this.ambientColor = ambientColor;
    }

    public ColorProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 6) throw new CommandParseException();
        ambientColor = new int[4];
        for (int i = 0; i < 3; i++) {
            ambientColor[i] = Property.getUnsignedInt(bytes[i + 3]);
        }

        if (getSize() == 3) ambientColor[3] = 255;
    }
}
