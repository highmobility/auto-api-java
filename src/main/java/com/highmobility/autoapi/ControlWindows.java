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
import java.util.ArrayList;
import javax.annotation.Nullable;

/**
 * Control windows
 */
public class ControlWindows extends SetCommand {
    public static final Identifier identifier = Identifier.WINDOWS;

    @Nullable Property<WindowOpenPercentage>[] openPercentages;
    @Nullable Property<WindowPosition>[] positions;

    /**
     * @return The open percentages
     */
    public @Nullable Property<WindowOpenPercentage>[] getOpenPercentages() {
        return openPercentages;
    }
    
    /**
     * @return The positions
     */
    public @Nullable Property<WindowPosition>[] getPositions() {
        return positions;
    }
    
    /**
     * Control windows
     *
     * @param openPercentages The open percentages
     * @param positions The positions
     */
    public ControlWindows(@Nullable WindowOpenPercentage[] openPercentages, @Nullable WindowPosition[] positions) {
        super(identifier);
    
        ArrayList<Property> openPercentagesBuilder = new ArrayList<>();
        if (openPercentages != null) {
            for (WindowOpenPercentage openPercentage : openPercentages) {
                Property prop = new Property(0x02, openPercentage);
                openPercentagesBuilder.add(prop);
                addProperty(prop);
            }
        }
        this.openPercentages = openPercentagesBuilder.toArray(new Property[0]);
        
        ArrayList<Property> positionsBuilder = new ArrayList<>();
        if (positions != null) {
            for (WindowPosition position : positions) {
                Property prop = new Property(0x03, position);
                positionsBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.positions = positionsBuilder.toArray(new Property[0]);
        if (this.openPercentages.length == 0 && this.positions.length == 0) throw new IllegalArgumentException();
    }

    ControlWindows(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<WindowOpenPercentage>> openPercentagesBuilder = new ArrayList<>();
        ArrayList<Property<WindowPosition>> positionsBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: {
                        Property openPercentage = new Property(WindowOpenPercentage.class, p);
                        openPercentagesBuilder.add(openPercentage);
                        return openPercentage;
                    }
                    case 0x03: {
                        Property position = new Property(WindowPosition.class, p);
                        positionsBuilder.add(position);
                        return position;
                    }
                }
                return null;
            });
        }
    
        openPercentages = openPercentagesBuilder.toArray(new Property[0]);
        positions = positionsBuilder.toArray(new Property[0]);
        if (this.openPercentages.length == 0 && this.positions.length == 0) throw new NoPropertiesException();
    }
}