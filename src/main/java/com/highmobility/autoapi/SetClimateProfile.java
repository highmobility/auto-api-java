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

import com.highmobility.autoapi.property.AutoHvacProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

/**
 * Set the climate profile. The result is sent through the evented Climate State command with the
 * new state.
 */
public class SetClimateProfile extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x02);

    /**
     * Create a set climate profile command. At least one parameter is expected not to be null.
     *
     * @param autoHvacState        The auto hvac state.
     * @param driverTemperature    The driver temperature.
     * @param passengerTemperature The passenger temperature.
     * @throws IllegalArgumentException When all arguments are null
     */
    public SetClimateProfile(AutoHvacProperty autoHvacState,
                             Float driverTemperature,
                             Float passengerTemperature) {
        super(TYPE, getProperties(autoHvacState, driverTemperature, passengerTemperature));
    }

    static HMProperty[] getProperties(AutoHvacProperty autoHvacState,
                                      Float driverTemperature,
                                      Float passengerTemperature) {
        ArrayList<Property> properties = new ArrayList<>();

        if (autoHvacState != null) {
            autoHvacState.setIdentifier((byte) 0x01);
            properties.add(autoHvacState);
        }

        if (driverTemperature != null) {
            FloatProperty prop = new FloatProperty((byte) 0x02, driverTemperature);
            properties.add(prop);
        }

        if (passengerTemperature != null) {
            FloatProperty prop = new FloatProperty((byte) 0x03, passengerTemperature);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }
}
