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
import com.highmobility.autoapi.value.ActiveState;

/**
 * Activate deactivate start stop
 */
public class ActivateDeactivateStartStop extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.ENGINE_START_STOP;

    public static final byte IDENTIFIER_STATUS = 0x01;

    Property<ActiveState> status = new Property(ActiveState.class, IDENTIFIER_STATUS);

    /**
     * @return The status
     */
    public Property<ActiveState> getStatus() {
        return status;
    }
    
    /**
     * Activate deactivate start stop
     *
     * @param status The status
     */
    public ActivateDeactivateStartStop(ActiveState status) {
        super(IDENTIFIER);
    
        addProperty(this.status.update(status), true);
    }

    ActivateDeactivateStartStop(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATUS: return status.update(p);
                }
                return null;
            });
        }
        if (this.status.getValue() == null) 
            throw new NoPropertiesException();
    }
}