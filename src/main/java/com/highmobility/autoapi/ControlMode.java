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
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

/**
 * Command sent from the car every time the remote control mode changes or when a Get Control
 * ControlMode is received. The new mode is included in the command and may be the result of both
 * user or car triggered action.
 */
public class ControlMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x01);
    private static final byte IDENTIFIER_MODE = 0x01;
    private static final byte IDENTIFIER_ANGLE = 0x02;

    ObjectProperty<Value> mode = new ObjectProperty<>(Value.class, IDENTIFIER_MODE);
    ObjectPropertyInteger angle = new ObjectPropertyInteger(IDENTIFIER_ANGLE, false);

    /**
     * @return the angle
     */
    public ObjectPropertyInteger getAngle() {
        return angle;
    }

    /**
     * @return the control mode
     */
    public ObjectProperty<Value> getMode() {
        return mode;
    }

    public ControlMode(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_MODE:
                        return mode.update(p);
                    case IDENTIFIER_ANGLE:
                        return angle.update(p);
                }

                return null;
            });
        }

    }

    @Override public boolean isState() {
        return true;
    }

    /**
     * The possible control mode values.
     */

    public enum Value implements PropertyValueSingleByte {
        UNAVAILABLE((byte) 0x00),
        AVAILABLE((byte) 0x01),
        STARTED((byte) 0x02),
        FAILED_TO_START((byte) 0x03),
        ABORTED((byte) 0x04),
        ENDED((byte) 0x05),
        UNSUPPORTED((byte) 0xFF);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] allValues = Value.values();

            for (int i = 0; i < allValues.length; i++) {
                Value value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Value(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        @Override public int getLength() {
            return 1;
        }
    }
}