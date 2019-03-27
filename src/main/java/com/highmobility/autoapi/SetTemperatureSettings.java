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

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the preferred temperature settings. The result is sent through the Climate State message.
 */
public class SetTemperatureSettings extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x17);
    private static final byte IDENTIFIER_DRIVER_TEMPERATURE = 0x01;
    private static final byte IDENTIFIER_PASSENGER_TEMPERATURE = 0x02;
    private static final byte IDENTIFIER_REAR_TEMPERATURE = 0x03;

    Property<Float> driverTemperature = new Property(Float.class, IDENTIFIER_DRIVER_TEMPERATURE);
    Property<Float> passengerTemperature =
            new Property(Float.class, IDENTIFIER_PASSENGER_TEMPERATURE);
    Property<Float> rearTemperature = new Property(Float.class, IDENTIFIER_REAR_TEMPERATURE);

    /**
     * @return The driver temperature.
     */
    @Nullable public Float getDriverTemperature() {
        return driverTemperature.getValue();
    }

    /**
     * @return The passenger temperature.
     */
    @Nullable public Float getPassengerTemperature() {
        return passengerTemperature.getValue();
    }

    /**
     * @return The rear temperature.
     */
    @Nullable public Float getRearTemperature() {
        return rearTemperature.getValue();
    }

    /**
     * @param driverTemperature    The driver temperature.
     * @param passengerTemperature The passenger temperature.
     * @param rearTemperature      The rear temperature.
     */
    public SetTemperatureSettings(@Nullable Float driverTemperature, @Nullable Float
            passengerTemperature, @Nullable Float rearTemperature) {
        super(TYPE);

        ArrayList<Property> builder = new ArrayList<>();
        if (driverTemperature != null) {
            this.driverTemperature.update(driverTemperature);
            builder.add(this.driverTemperature);
        }

        if (passengerTemperature != null) {
            this.passengerTemperature.update(passengerTemperature);
            builder.add(this.passengerTemperature);
        }

        if (rearTemperature != null) {
            this.rearTemperature.update(rearTemperature);
            builder.add(this.rearTemperature);
        }

        createBytes(builder);
    }

    SetTemperatureSettings(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DRIVER_TEMPERATURE:
                        return driverTemperature.update(p);
                    case IDENTIFIER_PASSENGER_TEMPERATURE:
                        return passengerTemperature.update(p);
                    case IDENTIFIER_REAR_TEMPERATURE:
                        return rearTemperature.update(p);
                }

                return null;
            });
        }
    }
}
