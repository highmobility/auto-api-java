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

package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Time;

public class DepartureTime extends Property {
    private static final byte IDENTIFIER = 0x11;
    boolean active;
    Time time;

    /**
     * @return The activation state.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return The departure time.
     */
    public Time getTime() {
        return time;
    }

    public DepartureTime(boolean active, int time) {
        super(IDENTIFIER, 3);
        bytes[3] = Property.boolToByte(active);
        // TODO: 12/10/2018  
    }

    public DepartureTime(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 6) throw new CommandParseException();
        // TODO: 12/10/2018
    }
}
