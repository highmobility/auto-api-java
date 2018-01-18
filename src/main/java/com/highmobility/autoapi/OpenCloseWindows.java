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
import com.highmobility.autoapi.property.WindowProperty;
import com.highmobility.autoapi.property.WindowState;

/**
 * Open or close the windows. Either one or all windows can be controlled with the same message. The
 * result is not received by the ack but instead sent through the evented Windows State message.
 */
public class OpenCloseWindows extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x02);

    WindowState[] windowStates;

    public WindowState[] getWindowStates() {
        return windowStates;
    }

    public OpenCloseWindows(WindowState[] windowStates) throws CommandParseException {
        super(TYPE, getProperties(windowStates));
        this.windowStates = windowStates;
    }

    OpenCloseWindows(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();
    }

    static Property[] getProperties(WindowState[] windowStates) throws CommandParseException {
        if (windowStates == null) throw new CommandParseException();
        WindowProperty[] properties = new WindowProperty[windowStates.length];

        for (int i = 0; i < windowStates.length; i++) {
            WindowState state = windowStates[i];
            WindowProperty property = new WindowProperty(state);
            properties[i] = property;
        }

        return properties;
    }
}
