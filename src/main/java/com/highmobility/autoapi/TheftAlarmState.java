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

/**
 * This is an evented message that is sent from the car every time the theft alarm state changes.
 * This message is also sent when a Get Theft Alarm State message is received by the car.
 */
public class TheftAlarmState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.THEFT_ALARM, 0x01);

    public enum State {
        NOT_ARMED((byte) 0x00), ARMED((byte) 0x01), TRIGGERED((byte) 0x02);

        public static State fromByte(byte value) throws CommandParseException {
            State[] values = State.values();

            for (int i = 0; i < values.length; i++) {
                State value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        State(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    State state;

    /**
     * @return Theft alarm state
     */
    public State getState() {
        return state;
    }

    public TheftAlarmState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    state = State.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}