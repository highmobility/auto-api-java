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

import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;

/**
 * This message is sent when a Get Navi Destination message is received by the car.
 */
public class NaviDestination extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x01);

    private CoordinatesProperty coordinates;
    private String name;

    /**
     * @return The coordinates
     */
    public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    public NaviDestination(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    coordinates = new CoordinatesProperty(property.getPropertyBytes());
                    break;
                case 0x02:
                    try {
                        name = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
            }
        }
    }
}