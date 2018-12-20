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
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.autoapi.property.value.Position;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Open or close the gas flap of the car.
 */
public class ControlGasFlap extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FUELING, 0x12);

    private static final byte LOCK_IDENTIFIER = 0x02;
    private static final byte POSITION_IDENTIFIER = 0x03;

    /**
     * Control the gas flap.
     *
     * @param lock     The lock state.
     * @param position The position.
     */
    public ControlGasFlap(@Nullable Lock lock, @Nullable Position position) {
        super(TYPE, getProperties(lock, position));
    }

    static Property[] getProperties(@Nullable Lock lock, @Nullable Position position) {
        ArrayList<Property> propertyList = new ArrayList<>();
        if (lock != null) {
            propertyList.add(new Property(LOCK_IDENTIFIER, lock.getByte()));
        }

        if (position != null) {
            propertyList.add(new Property(POSITION_IDENTIFIER, position.getByte()));
        }

        return propertyList.toArray(new Property[0]);
    }

    ControlGasFlap(byte[] bytes) {
        super(bytes);
    }
}