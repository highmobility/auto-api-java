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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * To be sent every time the controls for the car wants to be changed or once a second if the
 * controls remain the same. If the car does not receive the command every second it will stop the
 * control mode.
 */
public class ControlCommand extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x04);
    private static final byte speedIdentifier = 0x01;
    private static final byte angleIdentifier = 0x02;

    Integer speed;
    Integer angle;

    /**
     * @return The speed in km/h, can range between -5 to 5 whereas a negative speed will reverse
     * the car.
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * @return The angle of the car
     */
    public Integer getAngle() {
        return angle;
    }

    /**
     * @param speed Speed in km/h, can range between -5 to 5 whereas a negative speed will reverse
     *              the car.
     * @param angle angle of the car.
     * @throws IllegalArgumentException When all arguments are null or invalid
     */
    public ControlCommand(Integer speed, Integer angle) {
        super(TYPE, getProperties(speed, angle));
        this.speed = speed;
        this.angle = angle;
    }

    static Property[] getProperties(Integer speed, Integer angle) {
        List<Property> properties = new ArrayList<>();

        if (speed != null) {
            if (speed > 5 || speed < -5) throw new IllegalArgumentException();

            IntegerProperty prop = new IntegerProperty(speedIdentifier, speed, 1);
            properties.add(prop);
        }

        if (angle != null) {
            IntegerProperty prop = new IntegerProperty(angleIdentifier, angle, 2);
            properties.add(prop);
        }

        return properties.toArray(new Property[0]);
    }

    ControlCommand(byte[] bytes) {
        super(bytes);

        // Not used in telematics.

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case speedIdentifier:
                    speed = Property.getSignedInt(property.getValueByte());
                    break;
                case angleIdentifier:
                    angle = Property.getSignedInt(property.getValueBytes());
                    break;
            }
        }
    }
}
