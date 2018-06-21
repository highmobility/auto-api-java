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

import com.highmobility.autoapi.property.Property;

/**
 * Set the chassis position. The result is sent through the Chassis Settings command.
 */
public class SetChassisPosition extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x05);

    int position;

    /**
     * @return The chassis position in mm calculated from the lowest point.
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position The chassis position in mm calculated from the lowest point
     */
    public SetChassisPosition(int position) {
        super(TYPE.addByte(Property.intToBytes(position, 1)[0]));
        this.position = position;
    }

    SetChassisPosition(byte[] bytes) {
        super(bytes);
        this.position = Property.getUnsignedInt(bytes[3]);
    }
}
