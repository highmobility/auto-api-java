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
import com.highmobility.utils.ByteUtils;

/**
 * Activate or deactivate cruise control. The result is sent through the Cruise Control State
 * message.
 */
public class ActivateDeactivateCruiseControl extends Command {
    public static final Type TYPE = new Type(Identifier.CRUISE_CONTROL, 0x02);
    boolean activate;
    float speed;

    /**
     * @return Whether cruise control should be activated.
     */
    public Boolean activate() {
        return activate;
    }

    /**
     * @return The target speed in km/h.
     */
    public Float speed() {
        return speed;
    }

    public ActivateDeactivateCruiseControl(boolean activate, int speed) {
        super(getBytes(activate, speed));
        this.activate = activate;
        this.speed = speed;
    }

    static byte[] getBytes(boolean activate, int speed) {
        byte[] bytes = new byte[3 + 4 + 5];

        BooleanProperty activeProp = new BooleanProperty((byte) 0x01, activate);
        IntegerProperty speedProp = new IntegerProperty((byte) 0x02, speed, 2);

        ByteUtils.setBytes(bytes, TYPE.getIdentifierAndType(), 0);
        ByteUtils.setBytes(bytes, activeProp.getPropertyBytes(), 3);
        ByteUtils.setBytes(bytes, speedProp.getPropertyBytes(), 7);

        return bytes;
    }

    ActivateDeactivateCruiseControl(byte[] bytes) {
        super(bytes);

        // no telematics
    }
}
