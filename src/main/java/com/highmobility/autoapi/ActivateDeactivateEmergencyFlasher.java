/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
import com.highmobility.autoapi.value.ActiveState;

/**
 * Activate deactivate emergency flasher
 */
public class ActivateDeactivateEmergencyFlasher extends SetCommand {
    public static final Identifier identifier = Identifier.HONK_HORN_FLASH_LIGHTS;

    Property<ActiveState> emergencyFlashersState = new Property(ActiveState.class, 0x04);

    /**
     * @return The emergency flashers state
     */
    public Property<ActiveState> getEmergencyFlashersState() {
        return emergencyFlashersState;
    }
    
    /**
     * Activate deactivate emergency flasher
     *
     * @param emergencyFlashersState The emergency flashers state
     */
    public ActivateDeactivateEmergencyFlasher(ActiveState emergencyFlashersState) {
        super(identifier);
    
        addProperty(this.emergencyFlashersState.update(emergencyFlashersState), true);
    }

    ActivateDeactivateEmergencyFlasher(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x04: return emergencyFlashersState.update(p);
                }
                return null;
            });
        }
        if (this.emergencyFlashersState.getValue() == null) 
            throw new NoPropertiesException();
    }
}