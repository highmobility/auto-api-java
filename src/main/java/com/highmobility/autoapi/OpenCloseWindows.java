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

import java.util.ArrayList;

/**
 * Open or close the windows. Either one or all windows can be controlled with the same command. The
 * result is sent through the evented Windows State command.
 */
public class OpenCloseWindows extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x02);

    private WindowProperty[] windowProperties;

    /**
     * @return The window properties.
     */
    public WindowProperty[] getWindowProperties() {
        return windowProperties;
    }

    /**
     * Get the window property for a window position.
     *
     * @param position The window position.
     * @return The window property.
     */
    public WindowProperty getWindowProperty(WindowProperty.Position position) {
        for (int i = 0; i < windowProperties.length; i++) {
            WindowProperty prop = windowProperties[i];
            if (prop.getPosition() == position) return prop;
        }

        return null;
    }

    public OpenCloseWindows(WindowProperty[] windowProperties) {
        super(TYPE, windowProperties);
        this.windowProperties = windowProperties;
    }

    OpenCloseWindows(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList builder = new ArrayList();

        for (Property property : properties) {
            if (property.getPropertyIdentifier() == 0x01)
                builder.add(new WindowProperty(
                        WindowProperty.Position.fromByte(property.getPropertyBytes()[3]),
                        WindowProperty.State.fromByte(property.getPropertyBytes()[4])));
        }

        windowProperties = (WindowProperty[]) builder.toArray(new WindowProperty[builder.size()]);
    }
}
