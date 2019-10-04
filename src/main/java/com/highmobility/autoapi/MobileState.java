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
import com.highmobility.autoapi.value.ConnectionState;

/**
 * The mobile state
 */
public class MobileState extends SetCommand {
    public static final Identifier identifier = Identifier.MOBILE;

    Property<ConnectionState> connection = new Property(ConnectionState.class, 0x01);

    /**
     * @return The connection
     */
    public Property<ConnectionState> getConnection() {
        return connection;
    }

    MobileState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return connection.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private MobileState(Builder builder) {
        super(builder);

        connection = builder.connection;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<ConnectionState> connection;

        public Builder() {
            super(identifier);
        }

        public MobileState build() {
            return new MobileState(this);
        }

        /**
         * @param connection The connection
         * @return The builder
         */
        public Builder setConnection(Property<ConnectionState> connection) {
            this.connection = connection.setIdentifier(0x01);
            addProperty(this.connection);
            return this;
        }
    }
}