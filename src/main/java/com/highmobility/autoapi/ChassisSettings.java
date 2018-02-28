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

import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.ChassisPositionProperty;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.SpringRateProperty;

import java.util.ArrayList;

/**
 * This message is sent when a Get Chassis Settings is received by the car.
 */
public class ChassisSettings extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x01);

    DrivingMode drivingMode;
    Boolean sportChronoActive;
    SpringRateProperty[] springRates;
    ChassisPositionProperty chassisPosition;

    /**
     *
     * @return The driving mode
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     *
     * @return Boolean indicating whether the sport chronometer is active
     */
    public Boolean isSportChronoActive() {
        return sportChronoActive;
    }

    /**
     *
     * @param axle The spring rate's axle.
     * @return The spring rate for the given axle. Null if does not exists.
     */
    public SpringRateProperty getSpringRate(Axle axle) {
        for (int i = 0; i < springRates.length; i++) {
            SpringRateProperty property = springRates[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     *
     * @return The chassis position
     */
    public ChassisPositionProperty getChassisPosition() {
        return chassisPosition;
    }

    public ChassisSettings(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<SpringRateProperty> springRateProperties = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    drivingMode = DrivingMode.fromByte(property.getValueByte());
                break;
                case 0x02:
                    sportChronoActive = Property.getBool(property.getValueByte());
                break;
                case 0x03:
                    springRateProperties.add(new SpringRateProperty(property.getPropertyBytes()));
                break;
                case 0x04:
                    chassisPosition = new ChassisPositionProperty(property.getPropertyBytes());
                break;
            }
        }

        springRates = springRateProperties.toArray(new SpringRateProperty[springRateProperties.size()]);
    }
}