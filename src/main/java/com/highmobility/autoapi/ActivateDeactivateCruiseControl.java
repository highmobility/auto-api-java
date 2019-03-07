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
import com.highmobility.autoapi.property.PropertyInteger;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Activate or deactivate cruise control. The result is sent through the Cruise Control State
 * message.
 */
public class ActivateDeactivateCruiseControl extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CRUISE_CONTROL, 0x12);
    private static final byte IDENTIFIER_ACTIVATE = 0x01;
    private static final byte IDENTIFIER_SPEED = 0x02;

    Property<Boolean> activate = new Property(Boolean.class, IDENTIFIER_ACTIVATE);
    PropertyInteger speed = new PropertyInteger(IDENTIFIER_SPEED, false);

    /**
     * @return Whether cruise control should be activated.
     */
    public Property<Boolean> activate() {
        return activate;
    }

    /**
     * @return The target speed in km/h.
     */
    @Nullable public Integer getSpeed() {
        return speed.getValue();
    }

    /**
     * Activate or deactivate cruise control. The result is sent through the Cruise Control State
     * message.
     *
     * @param activate The cruise control activation state.
     * @param speed    The speed. Nullable if cruise control inactive.
     */
    public ActivateDeactivateCruiseControl(Boolean activate, @Nullable Integer speed) {
        super(TYPE);
        ArrayList<Property> properties = new ArrayList<>();

        properties.add(new Property<>(activate).setIdentifier(IDENTIFIER_ACTIVATE));
        this.activate.update(activate);

        if (speed != null) {
            this.speed.update(IDENTIFIER_SPEED, false, 2, speed);
            properties.add(this.speed);
        }


        createBytes(properties);
    }

    ActivateDeactivateCruiseControl(byte[] bytes) {
        super(bytes);

        // no telematics
    }
}
