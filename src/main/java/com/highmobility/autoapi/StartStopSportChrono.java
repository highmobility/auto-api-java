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
import com.highmobility.autoapi.value.StartStop;

/**
 * Start/stop the sports chronometer. The result is sent through the Chassis Settings message.
 */
public class StartStopSportChrono extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x13);
    private static final byte PROPERTY_IDENTIFIER = 0x01;
    Property<StartStop> startStop = new Property(StartStop.class, PROPERTY_IDENTIFIER);

    /**
     * @return The chronometer command.
     */
    public Property<StartStop> getStartStop() {
        return startStop;
    }

    /**
     * @param startStop The chronometer command.
     */
    public StartStopSportChrono(StartStop startStop) {
        super(TYPE);
        this.startStop.update(startStop);
        createBytes(this.startStop);
    }

    StartStopSportChrono(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case PROPERTY_IDENTIFIER:
                        return startStop.update(p);
                }
                return null;
            });
        }
    }
}
