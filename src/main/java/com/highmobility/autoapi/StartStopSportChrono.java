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

/**
 * Start/stop the sports chronometer. The result is sent through the Chassis Settings message.
 */
public class StartStopSportChrono extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x03);

    Command command;

    /**
     * @return The chronometer command.
     */
    public Command getCommand() {
        return command;
    }

    /**
     * @param command The chronometer command.
     */
    public StartStopSportChrono(Command command) {
        super(TYPE.addByte(command.getByte()));
        this.command = command;
    }

    StartStopSportChrono(byte[] bytes) throws CommandParseException {
        super(bytes);
        this.command = Command.fromByte(bytes[3]);
    }

    public enum Command {
        START((byte) 0x00),
        STOP((byte) 0x01),
        RESET((byte) 0x02);

        public static Command fromByte(byte byteValue) throws CommandParseException {
            Command[] values = Command.values();

            for (int i = 0; i < values.length; i++) {
                Command state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Command(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}
