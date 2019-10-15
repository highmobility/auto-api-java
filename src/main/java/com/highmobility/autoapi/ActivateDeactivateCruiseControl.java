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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.ActiveState;
import javax.annotation.Nullable;

/**
 * Activate deactivate cruise control
 */
public class ActivateDeactivateCruiseControl extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.CRUISE_CONTROL;

    public static final byte IDENTIFIER_CRUISE_CONTROL = 0x01;
    public static final byte IDENTIFIER_TARGET_SPEED = 0x03;

    Property<ActiveState> cruiseControl = new Property(ActiveState.class, IDENTIFIER_CRUISE_CONTROL);
    @Nullable PropertyInteger targetSpeed = new PropertyInteger(IDENTIFIER_TARGET_SPEED, true);

    /**
     * @return The cruise control
     */
    public Property<ActiveState> getCruiseControl() {
        return cruiseControl;
    }
    
    /**
     * @return The target speed
     */
    public @Nullable PropertyInteger getTargetSpeed() {
        return targetSpeed;
    }
    
    /**
     * Activate deactivate cruise control
     *
     * @param cruiseControl The cruise control
     * @param targetSpeed The target speed in km/h
     */
    public ActivateDeactivateCruiseControl(ActiveState cruiseControl, @Nullable Integer targetSpeed) {
        super(IDENTIFIER);
    
        addProperty(this.cruiseControl.update(cruiseControl));
        addProperty(this.targetSpeed.update(true, 2, targetSpeed), true);
    }

    ActivateDeactivateCruiseControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CRUISE_CONTROL: return cruiseControl.update(p);
                    case IDENTIFIER_TARGET_SPEED: return targetSpeed.update(p);
                }
                return null;
            });
        }
        if (this.cruiseControl.getValue() == null) 
            throw new NoPropertiesException();
    }
}