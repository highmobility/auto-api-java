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
import com.highmobility.autoapi.property.PropertyValueSingleByte;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Flashers State command is received by the car.
 */
public class FlashersState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x01);
    private static final byte STATE_IDENTIFIER = 0x01;
    Property<Value> state = new Property<>(Value.class, STATE_IDENTIFIER);

    /**
     * @return The flashers state.
     */
    @Nullable public Property<Value> getState() {
        return state;
    }

    FlashersState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == STATE_IDENTIFIER) {
                    return state.update(p);
                }
                return null;

            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private FlashersState(Builder builder) {
        super(builder);
        state = builder.state;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<Value> state;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param state The flashers state.
         * @return The builder.
         */
        public Builder setState(Property<Value> state) {
            this.state = state;
            addProperty(state.setIdentifier(STATE_IDENTIFIER));
            return this;
        }

        public FlashersState build() {
            return new FlashersState(this);
        }
    }

    public enum Value implements PropertyValueSingleByte {
        INACTIVE((byte) 0x00),
        EMERGENCY_ACTIVE((byte) 0x01),
        LEFT_ACTIVE((byte) 0x02),
        RIGHT_ACTIVE((byte) 0x03);

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
    }
}
