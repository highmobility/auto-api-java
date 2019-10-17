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
import com.highmobility.autoapi.value.Coordinates;
import javax.annotation.Nullable;

/**
 * Set navi destination
 */
public class SetNaviDestination extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.NAVI_DESTINATION;

    public static final byte IDENTIFIER_COORDINATES = 0x01;
    public static final byte IDENTIFIER_DESTINATION_NAME = 0x02;

    Property<Coordinates> coordinates = new Property(Coordinates.class, IDENTIFIER_COORDINATES);
    @Nullable Property<String> destinationName = new Property(String.class, IDENTIFIER_DESTINATION_NAME);

    /**
     * @return The coordinates
     */
    public Property<Coordinates> getCoordinates() {
        return coordinates;
    }
    
    /**
     * @return The destination name
     */
    public @Nullable Property<String> getDestinationName() {
        return destinationName;
    }
    
    /**
     * Set navi destination
     *
     * @param coordinates The coordinates
     * @param destinationName Destination name
     */
    public SetNaviDestination(Coordinates coordinates, @Nullable String destinationName) {
        super(IDENTIFIER);
    
        addProperty(this.coordinates.update(coordinates));
        addProperty(this.destinationName.update(destinationName), true);
    }

    SetNaviDestination(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_COORDINATES: return coordinates.update(p);
                    case IDENTIFIER_DESTINATION_NAME: return destinationName.update(p);
                }
                return null;
            });
        }
        if (this.coordinates.getValue() == null) 
            throw new NoPropertiesException();
    }
}