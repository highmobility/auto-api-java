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
import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Power Take-Off State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class PowerTakeOffState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.POWER_TAKE_OFF, 0x01);
    private static final byte ACTIVE_IDENTIFIER = 0x01;
    private static final byte ENGAGED_IDENTIFIER = 0x02;

    Boolean active;
    Boolean engaged;

    /**
     * @return Whether the power take-off is active.
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * @return Whether at least one Power Take-Off drive is engaged.
     */
    public Boolean isEngaged() {
        return engaged;
    }

    PowerTakeOffState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case ACTIVE_IDENTIFIER:
                    active = Property.getBool(property.getValueByte());
                    break;
                case ENGAGED_IDENTIFIER:
                    engaged = Property.getBool(property.getValueByte());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private PowerTakeOffState(Builder builder) {
        super(builder);
        active = builder.active;
        engaged = builder.engaged;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private boolean active;
        private boolean engaged;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param active Whether the power take-off is active.
         * @return The builder.
         */
        public Builder setIsActive(boolean active) {
            this.active = active;
            addProperty(new BooleanProperty(ACTIVE_IDENTIFIER, active));
            return this;
        }

        /**
         * @param engaged Whether the power take-off is engaged.
         * @return The builder.
         */
        public Builder setIsEngaged(boolean engaged) {
            this.engaged = engaged;
            addProperty(new BooleanProperty(ENGAGED_IDENTIFIER, engaged));
            return this;
        }

        public PowerTakeOffState build() {
            return new PowerTakeOffState(this);
        }
    }
}
