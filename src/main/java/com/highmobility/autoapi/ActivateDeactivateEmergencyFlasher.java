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

import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.Property;

/**
 * This activates or deactivates the emergency flashers of the car, typically the blinkers to alarm
 * other drivers.
 */
public class ActivateDeactivateEmergencyFlasher extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x13);
    private static final byte PROPERTY_IDENTIFIER = 0x01;
    boolean activate;

    /**
     * @return Whether flasher should be activated.
     */
    public boolean activate() {
        return activate;
    }

    public ActivateDeactivateEmergencyFlasher(boolean activate) {
        super(TYPE.addProperty(new ObjectProperty<>(activate).setIdentifier(PROPERTY_IDENTIFIER)));
        this.activate = activate;
    }

    ActivateDeactivateEmergencyFlasher(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property property = getProperty(PROPERTY_IDENTIFIER);
        if (property == null) throw new CommandParseException();
        activate = Property.getBool(bytes[6]);
    }
}
