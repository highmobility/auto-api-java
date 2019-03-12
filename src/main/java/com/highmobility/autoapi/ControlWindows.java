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

import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.windows.WindowPosition;

import java.util.ArrayList;

/**
 * Open or close the windows. Either one or all windows can be controlled with the same command. The
 * result is sent through the evented Windows State command.
 */
public class ControlWindows extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x12);
    private static final byte PROPERTY_IDENTIFIER = 0x02;

    private Property<WindowPosition>[] windowPositions;

    /**
     * @return The window positions.
     */
    public Property<WindowPosition>[] getWindowPositions() {
        return windowPositions;
    }

    /**
     * Get the window position for a window location.
     *
     * @param location The window location.
     * @return The window position.
     */
    public Property<WindowPosition> getWindowPosition(Location location) {
        for (int i = 0; i < windowPositions.length; i++) {
            Property<WindowPosition> prop = windowPositions[i];
            if (prop.getValue().getLocation() == location) return prop;
        }

        return null;
    }

    public ControlWindows(WindowPosition[] windowPositions) {
        super(TYPE);

        ArrayList<Property> builder = new ArrayList<>();

        for (WindowPosition windowPos : windowPositions) {
            Property prop = new Property(PROPERTY_IDENTIFIER, windowPos);
            builder.add(prop);
        }

        this.windowPositions = builder.toArray(new Property[0]);
        createBytes(builder);
    }

    ControlWindows(byte[] bytes) {
        super(bytes);
        ArrayList<Property<WindowPosition>> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case PROPERTY_IDENTIFIER:
                        Property prop = new Property(new WindowPosition(
                                Location.fromByte(p.getValueComponent().getValueBytes().get(0)),
                                Position.fromByte(p.getValueComponent().getValueBytes().get(1))));
                        builder.add(prop);
                        return prop;
                }
                return null;
            });
        }

        windowPositions = builder.toArray(new Property[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
