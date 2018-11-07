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
import com.highmobility.autoapi.property.value.Position;
import com.highmobility.autoapi.property.windows.WindowLocation;
import com.highmobility.autoapi.property.windows.WindowPosition;

import java.util.ArrayList;

/**
 * Open or close the windows. Either one or all windows can be controlled with the same command. The
 * result is sent through the evented Windows State command.
 */
public class ControlWindows extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x12);
    private static final byte PROPERTY_IDENTIFIER = 0x02;
    private WindowPosition[] windowPositions;

    /**
     * @return The window positions.
     */
    public WindowPosition[] getWindowPositions() {
        return windowPositions;
    }

    /**
     * Get the window position for a window location.
     *
     * @param location The window location.
     * @return The window position.
     */
    public WindowPosition getWindowPosition(WindowLocation location) {
        for (int i = 0; i < windowPositions.length; i++) {
            WindowPosition prop = windowPositions[i];
            if (prop.getLocation() == location) return prop;
        }

        return null;
    }

    public ControlWindows(WindowPosition[] windowPositions) {
        super(TYPE, updatePositions(windowPositions));
        this.windowPositions = windowPositions;
    }

    static WindowPosition[] updatePositions(WindowPosition[] windowPositions) {
        for (WindowPosition windowPosition : windowPositions) {
            windowPosition.setIdentifier(PROPERTY_IDENTIFIER);
        }

        return windowPositions;
    }

    ControlWindows(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<WindowPosition> builder = new ArrayList<>();

        for (Property property : properties) {
            if (property.getPropertyIdentifier() == PROPERTY_IDENTIFIER)
                builder.add(new WindowPosition(
                        WindowLocation.fromByte(property.getPropertyBytes()[3]),
                        Position.fromByte(property.getPropertyBytes()[4])
                ));
        }

        windowPositions = builder.toArray(new WindowPosition[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
