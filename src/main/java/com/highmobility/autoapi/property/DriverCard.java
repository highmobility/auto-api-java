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

public class DriverCard extends Property {
    public static final byte IDENTIFIER = 0x03;

    int driverNumber;
    boolean present;

    public int getDriverNumber() {
        return driverNumber;
    }

    public boolean isPresent() {
        return present;
    }

    public DriverCard(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        driverNumber = Property.getUnsignedInt(bytes[3]);
        present = Property.getBool(bytes[4]);
    }

    public DriverCard(int driverNumber, boolean present) throws
            IllegalArgumentException {
        this(IDENTIFIER, driverNumber, present);
    }

    public DriverCard(byte identifier, int driverNumber, boolean present) throws
            IllegalArgumentException {
        super(identifier, 2);

        bytes[3] = (byte) driverNumber;
        bytes[4] = Property.boolToByte(present);
    }
}