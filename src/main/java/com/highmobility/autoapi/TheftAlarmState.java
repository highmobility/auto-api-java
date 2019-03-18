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

import javax.annotation.Nullable;

/**
 * Command sent from the car every time the theft alarm state changes or when a Get Theft Alarm
 * State command is received.
 */
public class TheftAlarmState extends Command {
    public static final Type TYPE = new Type(Identifier.THEFT_ALARM, 0x01);
    private static final byte IDENTIFIER = 0x01;

    Property<Value> state = new Property(Value.class, IDENTIFIER);

    /**
     * @return Theft alarm state.
     */
    public Property<Value> getState() {
        return state;
    }

    TheftAlarmState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER) {
                    return state.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum Value implements ByteEnum {
        NOT_ARMED((byte) 0x00),
        ARMED((byte) 0x01),
        TRIGGERED((byte) 0x02);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value value1 = values[i];
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

    private TheftAlarmState(Builder builder) {
        super(builder);
        state = builder.state;
    }

    public static final class Builder extends Command.Builder {
        private Property<Value> state;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param state The theft alarm state.
         * @return The builder.
         */
        public Builder setState(Property<Value> state) {
            this.state = state;
            addProperty(state.setIdentifier(IDENTIFIER));
            return this;
        }

        public TheftAlarmState build() {
            return new TheftAlarmState(this);
        }
    }
}