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
 * Manually start or stop defogging. The result is sent through the evented Climate State message.
 */
public class StartStopDefogging extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x14);
    private static final byte IDENTIFIER = 0x01;

    private final Property<Boolean> start = new Property(Boolean.class, IDENTIFIER);

    /**
     * @return Whether defogging should be started.
     */
    public Property<Boolean> start() {
        return start;
    }

    /**
     * @param start The defogging state.
     */
    public StartStopDefogging(Boolean start) {
        super(TYPE);
        this.start.update(start);
        createBytes(this.start);
    }

    StartStopDefogging(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return start.update(p);
                }
                return null;
            });
        }
    }
}
