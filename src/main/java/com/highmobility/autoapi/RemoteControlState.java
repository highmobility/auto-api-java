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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.PropertyInteger;

/**
 * The remote control state
 */
public class RemoteControlState extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.REMOTE_CONTROL;

    public static final byte IDENTIFIER_CONTROL_MODE = 0x01;
    public static final byte IDENTIFIER_ANGLE = 0x02;

    Property<ControlMode> controlMode = new Property(ControlMode.class, IDENTIFIER_CONTROL_MODE);
    PropertyInteger angle = new PropertyInteger(IDENTIFIER_ANGLE, true);

    /**
     * @return The control mode
     */
    public Property<ControlMode> getControlMode() {
        return controlMode;
    }

    /**
     * @return Wheel base angle in degrees
     */
    public PropertyInteger getAngle() {
        return angle;
    }

    RemoteControlState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CONTROL_MODE: return controlMode.update(p);
                    case IDENTIFIER_ANGLE: return angle.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum ControlMode implements ByteEnum {
        UNAVAILABLE((byte) 0x00),
        AVAILABLE((byte) 0x01),
        STARTED((byte) 0x02),
        FAILED_TO_START((byte) 0x03),
        ABORTED((byte) 0x04),
        ENDED((byte) 0x05);
    
        public static ControlMode fromByte(byte byteValue) throws CommandParseException {
            ControlMode[] values = ControlMode.values();
    
            for (int i = 0; i < values.length; i++) {
                ControlMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        ControlMode(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}