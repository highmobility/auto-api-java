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
import com.highmobility.autoapi.property.WindowState;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an evented message that is sent from the car every time the windows state changes. This
 * message is also sent when a Get Windows State is received by the car. The new status is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class WindowsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x01);

    WindowState[] windowStates;

    /**
     * @return All of the window states
     */
    public WindowState[] getWindowStates() {
        return windowStates;
    }

    public WindowState getWindowState(WindowState.Position position) {
        for (int i = 0; i < windowStates.length; i++) {
            if (windowStates[i].getPosition() == position) return windowStates[i];
        }

        return null;
    }

    public WindowsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        List<WindowState> builder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    WindowState state = new WindowState(property.getValueBytes()[0], property
                            .getValueBytes()[1]);
                    builder.add(state);
                    break;
            }
        }

        windowStates = builder.toArray(new WindowState[builder.size()]);
    }
}