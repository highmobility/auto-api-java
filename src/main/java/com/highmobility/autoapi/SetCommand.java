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
import com.highmobility.value.Bytes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class SetCommand extends Command {
    ArrayList<Property> propertiesBuilder;

    SetCommand(Identifier identifier) {
        super(identifier, 3);

        set(0, identifier.getBytes());
        set(2, (byte) 0x01);

        type = Type.SET;
    }

    /**
     * Used in SetCommands, to create the bytes and base properties.
     */
    protected void addProperty(Property property) {
        if (property.getValueComponent() == null) return;
        if (propertiesBuilder == null) propertiesBuilder = new ArrayList();
        propertiesBuilder.add(property);
    }

    /**
     * Used in SetCommands, to create the bytes and base properties.
     */
    protected void addProperty(Property property, boolean createBytes) {
        if (property != null && property.getValueComponent() != null) addProperty(property);

        if (createBytes) {
            if (propertiesBuilder == null) propertiesBuilder = new ArrayList();
            findUniversalProperties(identifier, type, propertiesBuilder.toArray(new Property[0]),
                    true);
        }
    }

    protected void addProperties(List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            concat(properties.get(i));
        }
    }

    private static int getPropertiesLength(Property[] properties) {
        int length = 0;

        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            length += property.size();
        }

        return length;
    }

    SetCommand(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes[2] != 0x01) throw new CommandParseException();
    }

    public SetCommand(Builder builder) {
        super(builder.identifier, Type.SET, builder.propertiesBuilder.toArray(new Property[0]));
    }

    public static class Builder {
        private Identifier identifier;

        private Bytes nonce;
        private Bytes signature;
        private Calendar timestamp;

        protected ArrayList<Property> propertiesBuilder = new ArrayList<>();

        public Builder(Identifier identifier) {
            this.identifier = identifier;
        }

        public Builder addProperty(Property property) {
            propertiesBuilder.add(property);
            return this;
        }

        /**
         * @param nonce The nonce used for the signature.
         * @return The nonce.
         */
        public Builder setNonce(Bytes nonce) {
            this.nonce = nonce;
            addProperty(new Property(NONCE_IDENTIFIER, nonce));
            return this;
        }

        /**
         * @param signature The signature for the signed bytes(the whole command except the
         *                  signature property)
         * @return The builder.
         */
        public Builder setSignature(Bytes signature) {
            this.signature = signature;
            addProperty(new Property(SIGNATURE_IDENTIFIER,
                    signature));
            return this;
        }

        /**
         * @param timestamp The timestamp of when the data was transmitted from the car.
         * @return The builder.
         */
        public Builder setTimestamp(Calendar timestamp) {
            this.timestamp = timestamp;
            addProperty(new Property(TIMESTAMP_IDENTIFIER,
                    timestamp));
            return this;
        }

        protected SetCommand build() {
            return new SetCommand(this);
        }

        protected Property[] getProperties() {
            return propertiesBuilder.toArray(new Property[0]);
        }
    }
}
