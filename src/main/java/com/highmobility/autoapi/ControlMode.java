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

import com.highmobility.autoapi.property.ControlModeValue;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Command sent from the car every time the remote control mode changes or when a Get Control
 * ControlMode is received. The new mode is included in the command and may be the result of both
 * user or car triggered action.
 */
public class ControlMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x01);

    ControlModeValue mode;
    Integer angle;

    /**
     * @return the angle
     */
    public Integer getAngle() {
        return angle;
    }

    /**
     * @return the control mode
     */
    public ControlModeValue getMode() {
        return mode;
    }

    public ControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];

            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    mode = ControlModeValue.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    angle = Property.getUnsignedInt(property.getValueBytes());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ControlMode(Builder builder) {
        super(builder);
        angle = builder.angle;
        mode = builder.mode;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private int angle;
        private ControlModeValue mode;

        public Builder() {
            super(TYPE);
        }

        public Builder setAngle(int angle) {
            this.angle = angle;
            addProperty(new IntegerProperty((byte) 0x01, angle, 2));
            return this;
        }

        public Builder setMode(ControlModeValue mode) {
            this.mode = mode;
            addProperty(mode);
            return this;
        }

        public ControlMode build() {
            return new ControlMode(this);
        }
    }
}