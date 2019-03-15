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

import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * To be sent every time the controls for the car wants to be changed or once a second if the
 * controls remain the same. If the car does not receive the command every second it will stop the
 * control mode.
 */
public class ControlCommand extends Command {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x04);
    private static final byte IDENTIFIER_SPEED = 0x01;
    private static final byte IDENTIFIER_ANGLE = 0x02;

    PropertyInteger speed = new PropertyInteger(IDENTIFIER_SPEED, true);
    PropertyInteger angle = new PropertyInteger(IDENTIFIER_ANGLE, true);

    /**
     * @return The speed in km/h, can range between -5 to 5 whereas a negative speed will reverse
     * the car.
     */
    public Property<Integer> getSpeed() {
        return speed;
    }

    /**
     * @return The angle of the car
     */
    public Property<Integer> getAngle() {
        return angle;
    }

    /**
     * @param speed Speed in km/h, can range between -5 to 5 whereas a negative speed will reverse
     *              the car.
     * @param angle angle of the car.
     * @throws IllegalArgumentException When all arguments are null or invalid
     */
    public ControlCommand(@Nullable Integer speed, @Nullable Integer angle) {
        super(TYPE);
        List<Property> properties = new ArrayList<>();

        if (speed != null) {
            if (speed > 5 || speed < -5) throw new IllegalArgumentException();

            this.speed.update(true, 1, speed);
            properties.add(this.speed);
        }

        if (angle != null) {
            this.angle.update(true, 2, angle);
            properties.add(this.angle);
        }

        createBytes(properties);
    }

    ControlCommand(byte[] bytes) {
        super(bytes);

        // Not used in telematics.

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_SPEED:
                        return speed.update(p);
                    case IDENTIFIER_ANGLE:
                        return angle.update(p);
                }
                
                return null;
            });
        }
    }
}
