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
import com.highmobility.autoapi.value.Timer;
import java.util.ArrayList;

/**
 * Set charging timers
 */
public class SetChargingTimers extends SetCommand {
    public static final Identifier identifier = Identifier.CHARGING;

    Property<Timer>[] timers;

    /**
     * @return The timers
     */
    public Property<Timer>[] getTimers() {
        return timers;
    }
    
    /**
     * Set charging timers
     *
     * @param timers The timers
     */
    public SetChargingTimers(Timer[] timers) {
        super(identifier);
    
        ArrayList<Property> timersBuilder = new ArrayList<>();
        if (timers != null) {
            for (Timer timer : timers) {
                Property prop = new Property(0x15, timer);
                timersBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.timers = timersBuilder.toArray(new Property[0]);
    }

    SetChargingTimers(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<Timer>> timersBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x15: {
                        Property timer = new Property(Timer.class, p);
                        timersBuilder.add(timer);
                        return timer;
                    }
                }
                return null;
            });
        }
    
        timers = timersBuilder.toArray(new Property[0]);
        if (this.timers.length == 0) 
            throw new NoPropertiesException();
    }
}