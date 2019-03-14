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
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.windows.WindowOpenPercentage;
import com.highmobility.autoapi.value.windows.WindowPosition;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent from the car every time the windows state changes or when a Get Windows State is
 * received. The new status is included in the command payload and may be the result of user, device
 * or car triggered action.
 */
public class WindowsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x01);

    private static final byte IDENTIFIER_WINDOW_OPEN_PERCENTAGES = 0x02;
    private static final byte IDENTIFIER_WINDOW_POSITION = 0x03;

    Property<WindowPosition>[] windowPositions;
    Property<WindowOpenPercentage>[] windowOpenPercentages;

    /**
     * @return The window positions.
     */
    public Property<WindowPosition>[] getWindowPositions() {
        return windowPositions;
    }

    /**
     * @param location The window location.
     * @return The window position.
     */
    @Nullable public Property<WindowPosition> getWindowPosition(Location location) {
        for (Property<WindowPosition> windowPosition : windowPositions) {
            if (windowPosition.getValue() != null && windowPosition.getValue().getLocation() == location)
                return windowPosition;
        }
        return null;
    }

    /**
     * @return The window open percentages.
     */
    public Property<WindowOpenPercentage>[] getWindowOpenPercentages() {
        return windowOpenPercentages;
    }

    /**
     * @param location The window location.
     * @return The window open percentage.
     */
    @Nullable public Property<WindowOpenPercentage> getWindowOpenPercentage(Location location) {
        for (Property<WindowOpenPercentage> windowOpenPercentage : windowOpenPercentages) {
            if (windowOpenPercentage.getValue() != null && windowOpenPercentage.getValue().getLocation() == location)
                return windowOpenPercentage;
        }
        return null;
    }

    WindowsState(byte[] bytes) {
        super(bytes);

        List<Property<WindowPosition>> positionBuilder = new ArrayList<>();
        List<Property<WindowOpenPercentage>> openBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WINDOW_OPEN_PERCENTAGES:
                        Property<WindowOpenPercentage> windowOpenPercentage =
                                new Property(WindowOpenPercentage.class, p);
                        openBuilder.add(windowOpenPercentage);
                        return windowOpenPercentage;
                    case IDENTIFIER_WINDOW_POSITION:
                        Property<WindowPosition> windowPosition =
                                new Property(WindowPosition.class, p);
                        positionBuilder.add(windowPosition);
                        return windowPosition;
                }

                return null;
            });
        }

        windowPositions = positionBuilder.toArray(new Property[0]);
        windowOpenPercentages = openBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private WindowsState(Builder builder) {
        super(builder);
        windowPositions = builder.windowPositions.toArray(new Property[0]);
        windowOpenPercentages = builder.windowOpenPercentages.toArray(new Property[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<Property<WindowPosition>> windowPositions = new ArrayList<>();
        List<Property<WindowOpenPercentage>> windowOpenPercentages = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param windowPositions The window positions.
         * @return The builder.
         */
        public Builder setWindowPositions(Property<WindowPosition>[] windowPositions) {
            this.windowPositions.clear();

            for (Property<WindowPosition> windowPosition : windowPositions) {
                addWindowPosition(windowPosition);
            }

            return this;
        }

        /**
         * @param windowOpenPercentages The window open percentages.
         * @return The builder.
         */
        public Builder setWindowOpenPercentages(Property<WindowOpenPercentage>[] windowOpenPercentages) {
            this.windowOpenPercentages.clear();

            for (Property<WindowOpenPercentage> windowOpenPercentage : windowOpenPercentages) {
                addWindowOpenPercentage(windowOpenPercentage);
            }

            return this;
        }

        /**
         * @param windowPosition A window position.
         * @return The builder.
         */
        public Builder addWindowPosition(Property<WindowPosition> windowPosition) {
            windowPosition.setIdentifier(IDENTIFIER_WINDOW_POSITION);
            addProperty(windowPosition);
            windowPositions.add(windowPosition);
            return this;
        }

        /**
         * @param windowOpenPercentage A window open percentage.
         * @return The builder.
         */
        public Builder addWindowOpenPercentage(Property<WindowOpenPercentage> windowOpenPercentage) {
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