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

    private static final byte AUTO_HVAC_IDENTIFIER = 0x01;
    private static final byte DRIVER_TEMP_IDENTIFIER = 0x02;
    private static final byte PASSENGER_TEMP_IDENTIFIER = 0x03;

    private AutoHvacProperty autoHvacState;
    private Float driverTemperature;
    private Float passengerTemperature;

    /**
     * @return The Auto HVAC state.
     */
    public AutoHvacProperty getAutoHvacState() {
        return autoHvacState;
    }

    /**
     * @return The driver temperature.
     */
    public Float getDriverTemperature() {
        return driverTemperature;
    }

    /**
     * @return The passenger temperature.
     */
    public Float getPassengerTemperature() {
        return passengerTemperature;
    }

    /**
     * Create a set climate profile command. At least one parameter is expected not to be null.
     *
     * @param autoHvacState        The Auto HVAC state.
     * @param driverTemperature    The driver temperature.
     * @param passengerTemperature The driver temperature.
     * @throws IllegalArgumentException When all arguments are null
     */
    public SetClimateProfile(AutoHvacProperty autoHvacState,
                             Float driverTemperature,
                             Float passengerTemperature) {
        super(TYPE, getProperties(autoHvacState, driverTemperature, passengerTemperature));
        this.autoHvacState = autoHvacState;
        this.driverTemperature = driverTemperature;
        this.passengerTemperature = passengerTemperature;
    }

    static HMProperty[] getProperties(AutoHvacProperty autoHvacState,
                                      Float driverTemperature,
                                      Float passengerTemperature) {
        ArrayList<Property> properties = new ArrayList<>();

        if (autoHvacState != null) {
            autoHvacState.setIdentifier(AUTO_HVAC_IDENTIFIER);
            properties.add(autoHvacState);
        }

        if (driverTemperature != null) {
            FloatProperty prop = new FloatProperty(DRIVER_TEMP_IDENTIFIER, driverTemperature);
            properties.add(prop);
        }

        if (passengerTemperature != null) {
            FloatProperty prop = new FloatProperty(PASSENGER_TEMP_IDENTIFIER, passengerTemperature);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    public SetClimateProfile(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property[] properties = getProperties();

        for (int i = 0; i < properties.length; i++) {
            Property prop = properties[i];

            switch (prop.getPropertyIdentifier()) {
                case AUTO_HVAC_IDENTIFIER:
                    autoHvacState = new AutoHvacProperty(prop.getPropertyBytes());
                    break;
                case DRIVER_TEMP_IDENTIFIER:
                    driverTemperature = Property.getFloat(prop.getValueBytes());
                    break;
                case PASSENGER_TEMP_IDENTIFIER:
                    passengerTemperature = Property.getFloat(prop.getValueBytes());
                    break;
            }
        }
    }
}
