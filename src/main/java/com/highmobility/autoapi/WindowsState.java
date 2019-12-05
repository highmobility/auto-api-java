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
import com.highmobility.autoapi.value.WindowOpenPercentage;
import com.highmobility.autoapi.value.WindowPosition;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.WindowLocation;
import java.util.ArrayList;
import java.util.List;

/**
 * The windows state
 */
public class WindowsState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.WINDOWS;

    public static final byte IDENTIFIER_OPEN_PERCENTAGES = 0x02;
    public static final byte IDENTIFIER_POSITIONS = 0x03;

    Property<WindowOpenPercentage>[] openPercentages;
    Property<WindowPosition>[] positions;

    /**
     * @return The open percentages
     */
    public Property<WindowOpenPercentage>[] getOpenPercentages() {
        return openPercentages;
    }

    /**
     * @return The positions
     */
    public Property<WindowPosition>[] getPositions() {
        return positions;
    }

    /**
     * @param location The window location.
     * @return The window position.
     */
    @Nullable public Property<WindowPosition> getPosition(WindowLocation location) {
        for (Property<WindowPosition> windowPosition : positions) {
            if (windowPosition.getValue() != null && windowPosition.getValue().getWindowLocation() == location)
                return windowPosition;
        }
        return null;
    }

    /**
     * @param location The window location.
     * @return The window open percentage.
     */
    @Nullable public Property<WindowOpenPercentage> getOpenPercentage(WindowLocation location) {
        for (Property<WindowOpenPercentage> windowOpenPercentage : openPercentages) {
            if (windowOpenPercentage.getValue() != null && windowOpenPercentage.getValue().getWindowLocation() == location)
                return windowOpenPercentage;
        }
        return null;
    }

    WindowsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> openPercentagesBuilder = new ArrayList<>();
        ArrayList<Property> positionsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_OPEN_PERCENTAGES:
                        Property<WindowOpenPercentage> openPercentage = new Property(WindowOpenPercentage.class, p);
                        openPercentagesBuilder.add(openPercentage);
                        return openPercentage;
                    case IDENTIFIER_POSITIONS:
                        Property<WindowPosition> position = new Property(WindowPosition.class, p);
                        positionsBuilder.add(position);
                        return position;
                }

                return null;
            });
        }

        openPercentages = openPercentagesBuilder.toArray(new Property[0]);
        positions = positionsBuilder.toArray(new Property[0]);
    }

    private WindowsState(Builder builder) {
        super(builder);

        openPercentages = builder.openPercentages.toArray(new Property[0]);
        positions = builder.positions.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> openPercentages = new ArrayList<>();
        private List<Property> positions = new ArrayList<>();

        public Builder() {
            super(IDENTIFIER);
        }

        public WindowsState build() {
            return new WindowsState(this);
        }

        /**
         * Add an array of open percentages.
         * 
         * @param openPercentages The open percentages
         * @return The builder
         */
        public Builder setOpenPercentages(Property<WindowOpenPercentage>[] openPercentages) {
            this.openPercentages.clear();
            for (int i = 0; i < openPercentages.length; i++) {
                addOpenPercentage(openPercentages[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single open percentage.
         * 
         * @param openPercentage The open percentage
         * @return The builder
         */
        public Builder addOpenPercentage(Property<WindowOpenPercentage> openPercentage) {
            openPercentage.setIdentifier(IDENTIFIER_OPEN_PERCENTAGES);
            addProperty(openPercentage);
            openPercentages.add(openPercentage);
            return this;
        }
        
        /**
         * Add an array of positions.
         * 
         * @param positions The positions
         * @return The builder
         */
        public Builder setPositions(Property<WindowPosition>[] positions) {
            this.positions.clear();
            for (int i = 0; i < positions.length; i++) {
                addPosition(positions[i]);
            }
        
            return this;
        }
        /**
         * Add a single position.
         * 
         * @param position The position
         * @return The builder
         */
        public Builder addPosition(Property<WindowPosition> position) {
            position.setIdentifier(IDENTIFIER_POSITIONS);
            addProperty(position);
            positions.add(position);
            return this;
        }
    }
}