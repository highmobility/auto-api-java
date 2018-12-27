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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the preferred temperature settings. The result is sent through the Climate State message.
 */
public class SetTemperatureSettings extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x17);
    private static final byte IDENTIFIER_DRIVER = 0x01;
    private static final byte IDENTIFIER_PASSENGER = 0x02;
    private static final byte IDENTIFIER_REAR = 0x03;

    Float driverTemperature, passengerTemperature, rearTemperature;

    /**
     * @return The driver temperature.
     */
    @Nullable public Float getDriverTemperature() {
        return driverTemperature;
    }

    /**
     * @return The passenger temperature.
     */
    @Nullable public Float getPassengerTemperature() {
        return passengerTemperature;
    }

    /**
     * @return The rear temperature.
     */
    @Nullable public Float getRearTemperature() {
        return rearTemperature;
    }

    /**
     * @param driverTemperature    The driver temperature.
     * @param passengerTemperature The passenger temperature.
     * @param rearTemperature      The rear temperature.
     */
    public SetTemperatureSettings(@Nullable Float driverTemperature, @Nullable Float
            passengerTemperature, @Nullable Float rearTemperature) {
        super(TYPE, getProperties(driverTemperature, passengerTemperature, rearTemperature));
        this.driverTemperature = driverTemperature;
        this.passengerTemperature = passengerTemperature;
        this.rearTemperature = rearTemperature;
    }

    static Property[] getProperties(Float driverTemperature, Float passengerTemperature, Float
            rearTemperature) {
        ArrayList<Property> builder = new ArrayList<>();
        if (driverTemperature != null) {
            builder.add(new FloatProperty(IDENTIFIER_DRIVER, driverTemperature));
        }

        if (passengerTemperature != null) {
            builder.add(new FloatProperty(IDENTIFIER_PASSENGER, passengerTemperature));
        }

        if (rearTemperature != null) {
            builder.add(new FloatProperty(IDENTIFIER_REAR, rearTemperature));
        }

        return builder.toArray(new Property[0]);
    }

    SetTemperatureSettings(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            if (property.getPropertyIdentifier() == IDENTIFIER_DRIVER) {
                this.driverTemperature = Property.getFloat(property.getValueBytes());
            } else if (property.getPropertyIdentifier() == IDENTIFIER_PASSENGER) {
                this.passengerTemperature = Property.getFloat(property.getValueBytes());
            } else if (property.getPropertyIdentifier() == IDENTIFIER_REAR) {
                this.rearTemperature = Property.getFloat(property.getValueBytes());
            }
        }
    }
}
