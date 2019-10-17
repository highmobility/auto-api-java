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
import javax.annotation.Nullable;

/**
 * Control command
 */
public class ControlCommand extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.REMOTE_CONTROL;

    public static final byte IDENTIFIER_ANGLE = 0x02;
    public static final byte IDENTIFIER_SPEED = 0x03;

    @Nullable PropertyInteger angle = new PropertyInteger(IDENTIFIER_ANGLE, true);
    @Nullable PropertyInteger speed = new PropertyInteger(IDENTIFIER_SPEED, true);

    /**
     * @return The angle
     */
    public @Nullable PropertyInteger getAngle() {
        return angle;
    }
    
    /**
     * @return The speed
     */
    public @Nullable PropertyInteger getSpeed() {
        return speed;
    }
    
    /**
     * Control command
     *
     * @param angle Wheel base angle in degrees
     * @param speed Speed in km/h
     */
    public ControlCommand(@Nullable Integer angle, @Nullable Integer speed) {
        super(IDENTIFIER);
    
        addProperty(this.angle.update(true, 2, angle));
        addProperty(this.speed.update(true, 1, speed), true);
        if (this.angle.getValue() == null && this.speed.getValue() == null) throw new IllegalArgumentException();
    }

    ControlCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ANGLE: return angle.update(p);
                    case IDENTIFIER_SPEED: return speed.update(p);
                }
                return null;
            });
        }
        if (this.angle.getValue() == null && this.speed.getValue() == null) throw new NoPropertiesException();
    }
}