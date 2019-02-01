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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

import javax.annotation.Nullable;

/**
 * Activate or deactivate cruise control. The result is sent through the Cruise Control State
 * message.
 */
public class ActivateDeactivateCruiseControl extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CRUISE_CONTROL, 0x12);
    Boolean activate;
    Integer speed;

    /**
     * @return Whether cruise control should be activated.
     */
    public boolean activate() {
        return activate;
    }

    /**
     * @return The target speed in km/h.
     */
    @Nullable public Integer getSpeed() {
        return speed;
    }

    /**
     * Activate or deactivate cruise control. The result is sent through the Cruise Control State
     * message.
     *
     * @param activate The cruise control activation state.
     * @param speed    The speed. Nullable if cruise control inactive.
     */
    public ActivateDeactivateCruiseControl(boolean activate, @Nullable Integer speed) {
        super(TYPE, getProperties(activate, speed));
        this.activate = activate;
        this.speed = speed;
    }

    static Property[] getProperties(boolean activate, Integer speed) {
        Property[] properties = new Property[speed == null ? 1 : 2];

        properties[0] = new BooleanProperty(activate).setIdentifier((byte) 0x01);
        if (speed != null) properties[1] = new IntegerProperty((byte) 0x02, speed, 2);

        return properties;
    }

    ActivateDeactivateCruiseControl(byte[] bytes) {
        super(bytes);

        // no telematics
    }
}
