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
import com.highmobility.autoapi.property.value.Axle;

public class BrakeTorqueVectoringProperty extends Property {
    public static final byte IDENTIFIER = 0x0A;
    Axle axle;
    boolean active;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     * @return Whether brake torque vectoring is active or not.
     */
    public boolean isActive() {
        return active;
    }

    public BrakeTorqueVectoringProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        axle = Axle.fromByte(bytes[6]);
        active = Property.getBool(bytes[7]);
    }

    public BrakeTorqueVectoringProperty(Axle axle, boolean active) {
        this(IDENTIFIER, axle, active);
    }

    BrakeTorqueVectoringProperty(byte identifier, Axle axle, boolean active) {
        super(identifier, 2);
        bytes[6] = axle.getByte();
        bytes[7] = Property.boolToByte(active);
    }
}