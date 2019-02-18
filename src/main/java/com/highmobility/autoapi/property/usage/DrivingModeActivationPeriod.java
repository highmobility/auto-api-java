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

package com.highmobility.autoapi.property.usage;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.Property;

public class DrivingModeActivationPeriod extends Property {
    public static final byte IDENTIFIER = 0x05;
    DrivingMode drivingMode;
    Float percentage;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return The activation period.
     */
    public float getPercentage() {
        return percentage;
    }

    public DrivingModeActivationPeriod(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 8) throw new CommandParseException();
        drivingMode = DrivingMode.fromByte(bytes[6]);
        percentage = Property.getPercentage(bytes[7]);
    }

    public DrivingModeActivationPeriod(DrivingMode type, float percentage) {
        this(IDENTIFIER, type, percentage);
    }

    DrivingModeActivationPeriod(byte identifier, DrivingMode type, float percentage) {
        super(identifier, 2);
        bytes[6] = type.getByte();
        bytes[7] = Property.floatToIntPercentageByte(percentage);
    }
}