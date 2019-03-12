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

import com.highmobility.autoapi.value.HvacStartingTime;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Weekday;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the HVAC (Heating, ventilation, and air conditioning) automated starting times. The result is
 * sent through the evented Climate State message with the new state.
 */
public class SetHvacStartingTimes extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x12);

    private static final byte AUTO_HVAC_IDENTIFIER = 0x01;

    private Property<HvacStartingTime>[] hvacStartingTimes;

    /**
     * @return The Auto HVAC state.
     */
    public Property<HvacStartingTime>[] getHvacStartingTimes() {
        return hvacStartingTimes;
    }

    /**
     * @param weekday Weekday of the HVAC starting time.
     * @return The Auto HVAC state.
     */
    @Nullable public Property<HvacStartingTime> getHvacStartingTime(Weekday weekday) {
        for (Property<HvacStartingTime> hvacStartingTime : hvacStartingTimes) {
            if (hvacStartingTime.getValue() != null && hvacStartingTime.getValue().getWeekday() == weekday)
                return hvacStartingTime;
        }
        return null;
    }

    /**
     * Set the HVAC (Heating, ventilation, and air conditioning) automated starting times. At least
     * one parameter is expected not to be null.
     *
     * @param hvacStartingTimes The Auto HVAC starting times.
     */
    public SetHvacStartingTimes(HvacStartingTime[] hvacStartingTimes) {
        super(TYPE);

        ArrayList<Property> builder = new ArrayList<>();

        for (HvacStartingTime time : hvacStartingTimes) {
            Property<HvacStartingTime> prop = new Property<>(AUTO_HVAC_IDENTIFIER,
                    time);
            builder.add(prop);
        }

        this.hvacStartingTimes = builder.toArray(new Property[0]);
        createBytes(builder);
    }

    SetHvacStartingTimes(byte[] bytes) {
        super(bytes);

        ArrayList<Property> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case AUTO_HVAC_IDENTIFIER:
                        Property prop = new Property<>(HvacStartingTime.class, p);
                        builder.add(prop);
                        return prop;
                }
                return null;
            });
        }

        hvacStartingTimes = builder.toArray(new Property[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
