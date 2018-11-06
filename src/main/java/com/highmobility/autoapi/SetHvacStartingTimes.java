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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the HVAC (Heating, ventilation, and air conditioning) automated starting times. The result is
 * sent through the evented Climate State message with the new state.
 */
public class SetHvacStartingTimes extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x12);

    private static final byte AUTO_HVAC_IDENTIFIER = 0x01;

    private HvacStartingTime[] hvacStartingTimes;

    /**
     * @return The Auto HVAC state.
     */
    public HvacStartingTime[] getHvacStartingTimes() {
        return hvacStartingTimes;
    }

    /**
     * @return The Auto HVAC state.
     */
    @Nullable public HvacStartingTime getHvacStartingTime(HvacStartingTime.Weekday weekday) {
        for (HvacStartingTime hvacStartingTime : hvacStartingTimes) {
            if (hvacStartingTime.getWeekday() == weekday) return hvacStartingTime;
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
        super(TYPE, getProperties(hvacStartingTimes));
        this.hvacStartingTimes = hvacStartingTimes;
    }

    static HMProperty[] getProperties(HvacStartingTime[] hvacStartingTimes) {
        for (HvacStartingTime hvacStartingTime : hvacStartingTimes) {
            hvacStartingTime.setIdentifier(AUTO_HVAC_IDENTIFIER);
        }

        return hvacStartingTimes;
    }

    public SetHvacStartingTimes(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property[] properties = getProperties();
        ArrayList<HvacStartingTime> builder = new ArrayList<>();

        for (int i = 0; i < properties.length; i++) {
            Property prop = properties[i];

            switch (prop.getPropertyIdentifier()) {
                case AUTO_HVAC_IDENTIFIER:
                    builder.add(new HvacStartingTime(prop.getPropertyBytes()));
                    break;
            }
        }

        hvacStartingTimes = builder.toArray(new HvacStartingTime[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
