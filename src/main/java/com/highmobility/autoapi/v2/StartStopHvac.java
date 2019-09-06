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

package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

/**
 * Start or stop the HVAC system to reach driver and passenger set temperatures. The car will use
 * cooling, defrosting and defogging as appropriate. The result is sent through the evented Climate
 * State message.
 */
public class StartStopHvac extends SetCommand {
    private static final byte IDENTIFIER = 0x01;

    private final Property<Boolean> start = new Property(Boolean.class, IDENTIFIER);

    /**
     * @return Whether HVAC should be started.
     */
    public Property<Boolean> start() {
        return start;
    }

    /**
     * @param start The HVAC state.
     */
    public StartStopHvac(Boolean start) {
        super(Identifier.CLIMATE);
        addProperty(this.start.update(start));
    }

    StartStopHvac(byte[] bytes) {
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

    @Override protected boolean propertiesExpected() {
        return true;
    }
}
