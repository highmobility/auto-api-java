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
import com.highmobility.autoapi.value.LockState;

/**
 * Lock unlock doors
 */
public class LockUnlockDoors extends SetCommand {
    public static final Identifier identifier = Identifier.DOORS;

    Property<LockState> insideLocksState = new Property(LockState.class, 0x05);

    /**
     * @return The inside locks state
     */
    public Property<LockState> getInsideLocksState() {
        return insideLocksState;
    }
    
    /**
     * Lock unlock doors
     *
     * @param insideLocksState Inside locks state for the whole car (combines all specific lock states if available)
     */
    public LockUnlockDoors(LockState insideLocksState) {
        super(identifier);
    
        addProperty(this.insideLocksState.update(insideLocksState), true);
    }

    LockUnlockDoors(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x05: return insideLocksState.update(p);
                }
                return null;
            });
        }
        if (this.insideLocksState.getValue() == null) 
            throw new NoPropertiesException();
    }
}