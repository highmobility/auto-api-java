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

/**
 * Attempt to start or stop the control mode of the car. The result is sent through the Control Mode
 * message.
 */
public class StartControlMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x12);
    private static final byte IDENTIFIER = 0x01;

    private Property<Boolean> start = new Property(Boolean.class, IDENTIFIER);

    /**
     * @return Whether to start the control mode.
     */
    public Property<Boolean> getStart() {
        return start;
    }

    /**
     * @param start The control mode state.
     */
    public StartControlMode(Boolean start) {
        super(TYPE);
        this.start.update(start);
        createBytes(this.start);
    }

    StartControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return start.update(p);
                }
                return null;
            });
        }
    }
}
