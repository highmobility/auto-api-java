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
import com.highmobility.autoapi.property.windows.WindowLocation;
import com.highmobility.autoapi.property.windows.WindowOpenPercentage;
import com.highmobility.autoapi.property.windows.WindowPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Command sent from the car every time the windows state changes or when a Get Windows State is
 * received. The new status is included in the command payload and may be the result of user, device
 * or car triggered action.
 */
public class WindowsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x01);

    private static final byte IDENTIFIER_WINDOW_OPEN_PERCENTAGES = 0x02;
    private static final byte IDENTIFIER_WINDOW_POSITION = 0x03;

    WindowPosition[] windowPositions;
    WindowOpenPercentage[] windowOpenPercentages;

    /**
     * @return The window positions.
     */
    public WindowPosition[] getWindowPositions() {
        return windowPositions;
    }

    /**
     * @param location The window location.
     * @return The window position.
     */
    public WindowPosition getWindowPosition(WindowLocation location) {
        for (WindowPosition windowPosition : windowPositions) {
            if (windowPosition.getLocation() == location) return windowPosition;
        }
        return null;
    }

    /**
     * @return The window open percentages.
     */
    public WindowOpenPercentage[] getWindowOpenPercentages() {
        return windowOpenPercentages;
    }

    /**
     * @param location The window location.
     * @return The window open percentage.
     */
    public WindowOpenPercentage getWindowOpenPercentage(WindowLocation location) {
        for (WindowOpenPercentage windowOpenPercentage : windowOpenPercentages) {
            if (windowOpenPercentage.getLocation() == location) return windowOpenPercentage;
        }
        return null;
    }

    public WindowsState(byte[] bytes) {
        super(bytes);

        List<WindowPosition> positionBuilder = new ArrayList<>();
        List<WindowOpenPercentage> openBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            try {
                // if one property parsing fails, just dont add it
                switch (property.getPropertyIdentifier()) {
                    case IDENTIFIER_WINDOW_OPEN_PERCENTAGES:
                        openBuilder.add(new WindowOpenPercentage(property.getPropertyBytes()));
                        break;
                    case IDENTIFIER_WINDOW_POSITION:
                        positionBuilder.add(new WindowPosition(property.getPropertyBytes()));
                        break;
                }
            } catch (CommandParseException e) {
                property.printFailedToParse();
            }
        }

        windowPositions = positionBuilder.toArray(new WindowPosition[0]);
        windowOpenPercentages = openBuilder.toArray(new WindowOpenPercentage[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private WindowsState(Builder builder) {
        super(builder);
        windowPositions = builder.windowPositions.toArray(new WindowPosition[0]);
        windowOpenPercentages = builder.windowOpenPercentages.toArray(new WindowOpenPercentage[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<WindowPosition> windowPositions = new ArrayList<>();
        List<WindowOpenPercentage> windowOpenPercentages = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param windowPositions
         * @return The builder.
         */
        public Builder setWindowPositions(WindowPosition[] windowPositions) {
            this.windowPositions.clear();

            for (WindowPosition windowPosition : windowPositions) {
                addWindowPosition(windowPosition);
            }

            return this;
        }

        /**
         * @param windowOpenPercentages
         * @return The builder.
         */
        public Builder setWindowOpenPercentages(WindowOpenPercentage[] windowOpenPercentages) {
            this.windowOpenPercentages.clear();

            for (WindowOpenPercentage windowOpenPercentage : windowOpenPercentages) {
                addWindowOpenPercentage(windowOpenPercentage);
            }

            return this;
        }

        /**
         * @param windowPosition
         * @return The builder.
         */
        public Builder addWindowPosition(WindowPosition windowPosition) {
            windowPosition.setIdentifier(IDENTIFIER_WINDOW_POSITION);
            addProperty(windowPosition);
            windowPositions.add(windowPosition);
            return this;
        }

        /**
         * @param windowOpenPercentage
         * @return The builder.
         */
        public Builder addWindowOpenPercentage(WindowOpenPercentage windowOpenPercentage) {
            windowOpenPercentage.setIdentifier(IDENTIFIER_WINDOW_OPEN_PERCENTAGES);
            addProperty(windowOpenPercentage);
            windowOpenPercentages.add(windowOpenPercentage);
            return this;
        }

        public WindowsState build() {
            return new WindowsState(this);
        }
    }
}