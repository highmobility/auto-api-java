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
 * Control trunk
 */
public class ControlTrunk extends SetCommand {
    public static final int IDENTIFIER = Identifier.TRUNK;

    public static final byte IDENTIFIER_LOCK = 0x01;
    public static final byte IDENTIFIER_POSITION = 0x02;

    @Nullable Property<LockState> lock = new Property(LockState.class, IDENTIFIER_LOCK);
    @Nullable Property<Position> position = new Property(Position.class, IDENTIFIER_POSITION);

    /**
     * @return The lock
     */
    public @Nullable Property<LockState> getLock() {
        return lock;
    }
    
    /**
     * @return The position
     */
    public @Nullable Property<Position> getPosition() {
        return position;
    }
    
    /**
     * Control trunk
     *
     * @param lock The lock
     * @param position The position
     */
    public ControlTrunk(@Nullable LockState lock, @Nullable Position position) {
        super(IDENTIFIER);
    
        addProperty(this.lock.update(lock));
        addProperty(this.position.update(position), true);
        if (this.lock.getValue() == null && this.position.getValue() == null) throw new IllegalArgumentException();
    }

    ControlTrunk(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_LOCK: return lock.update(p);
                    case IDENTIFIER_POSITION: return position.update(p);
                }
                return null;
            });
        }
        if (this.lock.getValue() == null && this.position.getValue() == null) throw new NoPropertiesException();
    }
}