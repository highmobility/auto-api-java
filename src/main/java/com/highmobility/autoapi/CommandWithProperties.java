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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.Bytes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * CommandWithProperties expects at least 1 property and no size for the whole command.
 * It is used for constructing commands with properties.
 */
public class CommandWithProperties extends Command {
    static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be null";

    Property[] properties;
    byte[] nonce;
    byte[] signature;

    /**
     *
     * @return The nonce for the signature
     */
    public byte[] getNonce() {
        return nonce;
    }

    /**
     *
     * @return The signature for the signed bytes(the whole command except the signature property)
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     *
     * @return The bytes that are signed with the signature
     */
    public byte[] getSignedBytes() {
        return Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3);
    }

    /**
     * @return All of the properties with raw values
     */
    public Property[] getProperties() {
        return properties;
    }

    public CommandWithProperties(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 7) throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        ArrayList<Property> builder = new ArrayList<>();
        PropertyEnumeration enumeration = new PropertyEnumeration(bytes);

        while (enumeration.hasMoreElements()) {
            PropertyEnumeration.EnumeratedProperty propertyEnumeration = enumeration.nextElement();
            Property property = new Property(Arrays.copyOfRange(bytes, propertyEnumeration
                    .valueStart - 3, propertyEnumeration.valueStart + propertyEnumeration.size));
            builder.add(property);

            if (property.getPropertyIdentifier() == (byte)0xA0) {
                // does not work
                if (propertyEnumeration.size != 9) {
                    throw new CommandParseException();
                }

                nonce = Arrays.copyOfRange(bytes, propertyEnumeration.valueStart,
                        propertyEnumeration.valueStart + propertyEnumeration.size);
            } else if (property.getPropertyIdentifier() == (byte)0xA1) {
                if (propertyEnumeration.size != 64) {
                    throw new CommandParseException();
                }

                signature = Arrays.copyOfRange(bytes, propertyEnumeration.valueStart,
                        propertyEnumeration.valueStart + propertyEnumeration.size);
            }
        }

        properties = builder.toArray(new Property[builder.size()]);
    }

    CommandWithProperties(Type type, HMProperty[] properties) throws IllegalArgumentException {
        super(type);

        if (properties == null || properties.length == 0) throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        bytes = new byte[] {
                type.getIdentifierAndType()[0],
                type.getIdentifierAndType()[1],
                type.getIdentifierAndType()[2]
        };

        for (int i = 0; i < properties.length; i++) {
            HMProperty property = properties[i];
            byte[] propertyBytes = property.getPropertyBytes();
            bytes = Bytes.concatBytes(bytes, propertyBytes);

            if (property.getPropertyIdentifier() == (byte)0xA0) {
                nonce = Arrays.copyOfRange(propertyBytes, 3, propertyBytes.length);
            }
            else if (property.getPropertyIdentifier() == (byte)0xA1) {
                signature = Arrays.copyOfRange(propertyBytes, 3, propertyBytes.length);
            }
        }
    }

    public CommandWithProperties(byte[] bytes, boolean internal)  {
        super(bytes, internal);
    }

    public static class Builder {
        private Type type;
        private byte[] nonce;
        private byte[] signature;
        protected ArrayList<HMProperty> propertiesBuilder = new ArrayList<>();

        public Builder(Type type) {
            this.type = type;
        }

        public Builder addProperty(HMProperty property) {
            propertiesBuilder.add(property);
            return this;
        }

        public Builder setNonce(byte[] nonce) {
            this.nonce = nonce;
            addProperty(new Property((byte)0xA0, nonce));
            return this;
        }

        public Builder setSignature(byte[] signature) {
            this.signature = signature;
            addProperty(new Property((byte)0xA1, signature));
            return this;
        }

//        public Builder setProperties(Property[] properties) {
//            this.propertiesBuilder = new ArrayList<>(properties.length);
//
//            for (int i = 0; i < properties.length; i++) {
//                propertiesBuilder.set(i, properties[i]);
//            }
//
//            return this;
//        }

        public CommandWithProperties build() {
            return new CommandWithProperties(type, getProperties());
        }

        protected HMProperty[] getProperties() {
            return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
        }
    }
}
