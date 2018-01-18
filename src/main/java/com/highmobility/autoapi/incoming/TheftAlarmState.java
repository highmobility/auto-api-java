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

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;


/**
 * This is an evented message that is sent from the car every time the theft alarm state changes.
 * This message is also sent when a Get Theft Alarm State message is received by the car.
 */
public class TheftAlarmState extends IncomingCommand {
    public enum State {
        NOT_ARMED, ARMED, TRIGGERED;

        public static State fromByte(byte value) {
            switch (value) {
                case 0x00:
                    return NOT_ARMED;
                case 0x01:
                    return ARMED;
                case 0x02:
                    return TRIGGERED;
            }

            return NOT_ARMED;
        }
    }

    State state;

    /**
     *
     * @return Theft alarm state
     */
    public State getState() {
        return state;
    }

    public TheftAlarmState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 4) throw new CommandParseException();

        state = State.fromByte(bytes[3]);
    }
}