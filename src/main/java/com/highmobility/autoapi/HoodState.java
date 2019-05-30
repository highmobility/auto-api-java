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
import com.highmobility.autoapi.value.Position;

/**
 * Command sent when a Get Hood State message is received by the car. The new state is included in
 * the message payload and may be the result of user, device or car triggered action.
 */
public class HoodState extends Command {
    public static final Type TYPE = new Type(Identifier.HOOD, 0x01);
    private static final byte IDENTIFIER_POSITION = 0x01;

    Property<Position> position = new Property(Position.class, IDENTIFIER_POSITION);

    /**
     * @return The hood position.
     */
    public Property<Position> getPosition() {
        return position;
    }

    HoodState(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER_POSITION) {
                    return position.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private HoodState(Builder builder) {
        super(builder);
        this.position = builder.position;
    }

    public static final class Builder extends Command.Builder {
        Property<Position> position;

        /**
         * @param position The hood position.
         * @return The builder.
         */
        public Builder setPosition(Property<Position> position) {
            addProperty(position.setIdentifier(IDENTIFIER_POSITION));
            this.position = position;
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public HoodState build() {
            return new HoodState(this);
        }
    }
}