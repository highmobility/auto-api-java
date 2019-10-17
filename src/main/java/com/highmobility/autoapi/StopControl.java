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

import com.highmobility.autoapi.RemoteControlState.ControlMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * Stop control
 */
public class StopControl extends SetCommand {
    public static final int IDENTIFIER = Identifier.REMOTE_CONTROL;

    public static final byte IDENTIFIER_CONTROL_MODE = 0x01;

    Property<ControlMode> controlMode = new Property(ControlMode.class, IDENTIFIER_CONTROL_MODE);

    /**
     * Stop control
     */
    public StopControl() {
        super(IDENTIFIER);
    
        addProperty(controlMode.addValueComponent(new Bytes("05")), true);
    }

    StopControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CONTROL_MODE: controlMode.update(p);
                }
                return null;
            });
        }
        if ((controlMode.getValue() == null || controlMode.getValueComponent().getValueBytes().equals(new Bytes("05")) == false)) 
            throw new NoPropertiesException();
    }
}