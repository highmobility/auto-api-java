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
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import javax.annotation.Nullable;

/**
 * Control gas flap
 */
public class ControlGasFlap extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.FUELING;

    public static final byte IDENTIFIER_GAS_FLAP_LOCK = 0x02;
    public static final byte IDENTIFIER_GAS_FLAP_POSITION = 0x03;

    @Nullable Property<LockState> gasFlapLock = new Property(LockState.class, IDENTIFIER_GAS_FLAP_LOCK);
    @Nullable Property<Position> gasFlapPosition = new Property(Position.class, IDENTIFIER_GAS_FLAP_POSITION);

    /**
     * @return The gas flap lock
     */
    public @Nullable Property<LockState> getGasFlapLock() {
        return gasFlapLock;
    }
    
    /**
     * @return The gas flap position
     */
    public @Nullable Property<Position> getGasFlapPosition() {
        return gasFlapPosition;
    }
    
    /**
     * Control gas flap
     *
     * @param gasFlapLock The gas flap lock
     * @param gasFlapPosition The gas flap position
     */
    public ControlGasFlap(@Nullable LockState gasFlapLock, @Nullable Position gasFlapPosition) {
        super(IDENTIFIER);
    
        addProperty(this.gasFlapLock.update(gasFlapLock));
        addProperty(this.gasFlapPosition.update(gasFlapPosition), true);
        if (this.gasFlapLock.getValue() == null && this.gasFlapPosition.getValue() == null) throw new IllegalArgumentException();
    }

    ControlGasFlap(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_GAS_FLAP_LOCK: return gasFlapLock.update(p);
                    case IDENTIFIER_GAS_FLAP_POSITION: return gasFlapPosition.update(p);
                }
                return null;
            });
        }
        if (this.gasFlapLock.getValue() == null && this.gasFlapPosition.getValue() == null) throw new NoPropertiesException();
    }
}