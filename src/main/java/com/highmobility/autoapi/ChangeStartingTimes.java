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
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import java.util.ArrayList;

/**
 * Change starting times
 */
public class ChangeStartingTimes extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.CLIMATE;

    public static final byte IDENTIFIER_HVAC_WEEKDAY_STARTING_TIMES = 0x0b;

    Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes;

    /**
     * @return The hvac weekday starting times
     */
    public Property<HvacWeekdayStartingTime>[] getHvacWeekdayStartingTimes() {
        return hvacWeekdayStartingTimes;
    }
    
    /**
     * Change starting times
     *
     * @param hvacWeekdayStartingTimes The hvac weekday starting times
     */
    public ChangeStartingTimes(HvacWeekdayStartingTime[] hvacWeekdayStartingTimes) {
        super(IDENTIFIER);
    
        ArrayList<Property> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
        if (hvacWeekdayStartingTimes != null) {
            for (HvacWeekdayStartingTime hvacWeekdayStartingTime : hvacWeekdayStartingTimes) {
                Property prop = new Property(0x0b, hvacWeekdayStartingTime);
                hvacWeekdayStartingTimesBuilder.add(prop);
                addProperty(prop);
            }
        }
        this.hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
        createBytes();
    }

    ChangeStartingTimes(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<HvacWeekdayStartingTime>> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_HVAC_WEEKDAY_STARTING_TIMES: {
                        Property hvacWeekdayStartingTime = new Property(HvacWeekdayStartingTime.class, p);
                        hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                        return hvacWeekdayStartingTime;
                    }
                }
                return null;
            });
        }
    
        hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
        if (this.hvacWeekdayStartingTimes.length == 0) 
            throw new NoPropertiesException();
    }
}