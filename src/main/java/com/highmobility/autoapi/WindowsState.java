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
import java.util.Arrays;
import java.util.List;

/**
 * This is an evented message that is sent from the car every time the windows state changes. This
 * message is also sent when a Get Windows State is received by the car. The new status is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class WindowsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x01);

    WindowProperty[] windowProperties;

    /**
     * @return All of the window states
     */
    public WindowProperty[] getWindowProperties() {
        return windowProperties;
    }

    public WindowProperty getWindowProperty(WindowProperty.Position position) {
        for (int i = 0; i < windowProperties.length; i++) {
            if (windowProperties[i].getPosition() == position) return windowProperties[i];
        }

        return null;
    }

    public WindowsState(byte[] bytes) {
        super(bytes);

        List<WindowProperty> builder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    try {
                        WindowProperty state = new WindowProperty(property.getValueBytes()[0], property

                                .getValueBytes()[1]);
                        builder.add(state);
                    }
                    catch (CommandParseException e) {
                        // ignore
                    }
                    break;
            }
        }

        windowProperties = builder.toArray(new WindowProperty[builder.size()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private WindowsState(Builder builder) {
        super(builder);
        windowProperties = builder.windowProperties;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private WindowProperty[] windowProperties;

        public Builder() {
            super(TYPE);
        }

        public Builder setWindowProperties(WindowProperty[] windowProperties) {
            this.windowProperties = windowProperties;

            for (int i = 0; i < windowProperties.length; i++) {
                addProperty(windowProperties[i]);
            }

            return this;
        }

        public Builder addWindowProperty(WindowProperty windowProperty) {
            if (windowProperties == null) windowProperties = new WindowProperty[1];
            else windowProperties = Arrays.copyOf(windowProperties, windowProperties.length + 1);

            addProperty(windowProperty);
            windowProperties[windowProperties.length - 1] = windowProperty;

            return this;
        }

        public WindowsState build() {
            return new WindowsState(this);
        }
    }
}