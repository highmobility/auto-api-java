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
 * Activate or deactivate power take-off. The result is sent through the Power Take-Off State
 * message.
 */
public class ActivateDeactivatePowerTakeoff extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.POWER_TAKE_OFF, 0x02);
    Boolean activate;

    /**
     * @return Whether power take-off should be activated.
     */
    public Boolean activate() {
        return activate;
    }

    public ActivateDeactivatePowerTakeoff(boolean activate) {
        super(TYPE.addProperty(new Property<>(activate).setIdentifier((byte) 0x01)));
        this.activate = activate;
    }

    ActivateDeactivatePowerTakeoff(byte[] bytes) {
        super(bytes);
        for (Property property : properties) {
            if (property.getPropertyIdentifier() == 0x01)
                activate = Property.getBool(property.getValueByte());
        }
    }
}
