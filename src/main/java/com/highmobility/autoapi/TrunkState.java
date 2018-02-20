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
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;

/**
 * This is an evented message that is sent from the car every time the trunk state changes. This
 * message is also sent when a Get Trunk State is received by the car. The new status is included in
 * the message payload and may be the result of user, device or car triggered action.
 */
public class TrunkState extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x01);

    TrunkLockState lockState;
    TrunkPosition position;

    /**
     * @return the current lock status of the trunk
     */
    public TrunkLockState getLockState() {
        return lockState;
    }

    /**
     * @return the current position of the trunk
     */
    public TrunkPosition getPosition() {
        return position;
    }

    public TrunkState(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    lockState = TrunkLockState.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    position = TrunkPosition.fromByte(property.getValueByte());
                    break;
            }
        }
    }

    private TrunkState(Builder builder) {
        super(builder);
        lockState = builder.lockState;
        position = builder.position;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private TrunkLockState lockState;
        private TrunkPosition position;

        public Builder() {
            super(TYPE);
        }

        public Builder setLockState(TrunkLockState lockState) {
            this.lockState = lockState;
            addProperty(lockState);
            return this;
        }

        public Builder setPosition(TrunkPosition position) {
            this.position = position;
            addProperty(position);
            return this;
        }

        public TrunkState build() {
            return new TrunkState(this);
        }
    }
}