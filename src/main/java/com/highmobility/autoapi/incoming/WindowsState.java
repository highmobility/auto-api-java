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
import com.highmobility.autoapi.WindowState;

public class WindowsState extends IncomingCommand {
    /**
     *
     * @return The states for the windows.
     */
    public WindowState[] getWindowStates() {
        return windowStates;
    }

    WindowState[] windowStates;

    WindowsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 4) throw new CommandParseException();

        int stateCountPosition = 3;
        int stateCount = bytes[stateCountPosition];

        windowStates = new WindowState[stateCount];
        int statePosition = stateCountPosition + 1;

        for (int i = 0; i < stateCount; i++) {
            WindowState.Location location = WindowState.Location.fromByte(bytes[statePosition]);
            WindowState.Position position = WindowState.Position.fromByte(bytes[statePosition + 1]);

            WindowState state = new WindowState(location, position);
            windowStates[i] = state;

            statePosition+=2;
        }
    }
}
