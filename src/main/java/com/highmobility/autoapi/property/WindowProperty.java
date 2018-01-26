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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class WindowProperty extends Property {
    WindowState state;

    public WindowState getState() {
        return state;
    }

    public WindowProperty(byte positionByte, byte stateByte) throws CommandParseException {
        this(new WindowState(WindowState.Position.fromByte(positionByte), WindowState.State.fromByte(stateByte)));
    }

    public WindowProperty(WindowState state) throws CommandParseException {
        super((byte) 0x01, 2);
        this.state = state;
        bytes[3] = state.getPosition().getByte();
        bytes[4] = state.getState().getByte();
    }

    public WindowProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        WindowState.Position position = WindowState.Position.fromByte(bytes[3]);
        WindowState.State windowState = WindowState.State.fromByte(bytes[4]);
        this.state = new WindowState(position, windowState);
    }
}