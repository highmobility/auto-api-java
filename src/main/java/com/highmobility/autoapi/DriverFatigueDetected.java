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

import com.highmobility.autoapi.property.FatigueLevel;
import com.highmobility.autoapi.property.Property;

import javax.annotation.Nullable;

/**
 * This is an evented command that notifies about driver fatigue. Sent continuously when level 1 or higher.
 */
public class DriverFatigueDetected extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DRIVER_FATIGUE, 0x01);

    FatigueLevel fatigueLevel;

    /**
     * @return The driver fatigue level
     */
    @Nullable public FatigueLevel getFatigueLevel() {
        return fatigueLevel;
    }

    public DriverFatigueDetected(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property p = getProperty((byte) 0x01);
        if (p != null) fatigueLevel = FatigueLevel.fromByte(p.getValueByte());
    }

    @Override public boolean isState() {
        return true;
    }
}