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

import com.highmobility.autoapi.property.CalendarProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.Bytes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Used for commands with properties. Can have 0 properties.
 */
public class CommandWithProperties extends Command {
    static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be null";
    private static final byte NONCE_IDENTIFIER = (byte) 0xA0;
    private static final byte SIGNATURE_IDENTIFIER = (byte) 0xA1;
    private static final byte TIMESTAMP_IDENTIFIER = (byte) 0xA2;

    Property[] properties;
    byte[] nonce;
    byte[] signature;
    Calendar timestamp;

    /**
     * @return The nonce for the signature.
     */
    public byte[] getNonce() {
        return nonce;
    }

    /**
     * @return The signature for the signed bytes(the whole command except the signature property).
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * @return Timestamp of when the data was transmitted from the car.
     */
    public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     * @return The bytes that are signed with the signature
     */
    public byte[] getSignedBytes() {
        return Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3);
    }

    /**
     * States are commands that describe some properties of the vehicle. They are usually returned
     * after the state changes, as a response for a get command or as a state in {@link
     * VehicleStatus#getStates()}.
     *
     * @return True if command is a state.
     */
    public boolean isState() {
        return false;
    }

    protected boolean propertiesExpected() {
        return isState() == false;
    }

    /**
     * @return All of the properties with raw values
     */
    public Property[] getProperties() {
        return properties;
    }

    public Property getProperty(byte identifier) {
        for (int i = 0; i < properties.length; i++) {
            Property prop = properties[i];
            if (prop.getPropertyIdentifier() == identifier) return prop;
        }

        return null;
    }

    public CommandWithProperties(byte[] bytes) {
        super(bytes);
        if (propertiesExpected() && bytes.length < 7)
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        ArrayList<Property> builder = new ArrayList<>();
        PropertyEnumeration enumeration = new PropertyEnumeration(bytes);

        while (enumeration.hasMoreElements()) {
            PropertyEnumeration.EnumeratedProperty propertyEnumeration = enumeration.nextElement();
            Property property = new Property(Arrays.copyOfRange(bytes, propertyEnumeration
                    .valueStart - 3, propertyEnumeration.valueStart + propertyEnumeration.size));
            builder.add(property);

            if (property.getPropertyIdentifier() == NONCE_IDENTIFIER) {
                if (propertyEnumeration.size != 9)
                    continue; // invalid nonce length, just ignore

                nonce = Arrays.copyOfRange(bytes, propertyEnumeration.valueStart,
                        propertyEnumeration.valueStart + propertyEnumeration.size);
            } else if (property.getPropertyIdentifier() == SIGNATURE_IDENTIFIER) {
                if (propertyEnumeration.size != 64) continue; // ignore invalid length
                signature = Arrays.copyOfRange(bytes, propertyEnumeration.valueStart,
                        propertyEnumeration.valueStart + propertyEnumeration.size);
            } else if (property.getPropertyIdentifier() == TIMESTAMP_IDENTIFIER) {
                timestamp = Property.getCalendar(bytes, propertyEnumeration.valueStart);
            }
        }

        properties = builder.toArray(new Property[builder.size()]);
    }

    CommandWithProperties(Type type, HMProperty[] properties) throws IllegalArgumentException {
        super(type);
        if (propertiesExpected() && (properties == null || properties.length == 0))
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        bytes = type.getIdentifierAndType();

        for (int i = 0; i < properties.length; i++) {
            HMProperty property = properties[i];
            byte[] propertyBytes = property.getPropertyBytes();
            bytes = Bytes.concatBytes(bytes, propertyBytes);

            if (property.getPropertyIdentifier() == NONCE_IDENTIFIER) {
                nonce = Arrays.copyOfRange(propertyBytes, 3, propertyBytes.length);
            } else if (property.getPropertyIdentifier() == SIGNATURE_IDENTIFIER) {
                signature = Arrays.copyOfRange(propertyBytes, 3, propertyBytes.length);
            } else if (property.getPropertyIdentifier() == TIMESTAMP_IDENTIFIER) {
                timestamp = Property.getCalendar(propertyBytes, 3);
            }
        }
    }

    CommandWithProperties(Builder builder) throws IllegalArgumentException {
        super(builder.type);

        this.nonce = builder.nonce;
        this.signature = builder.signature;
        this.timestamp = builder.timestamp;

        bytes = type.getIdentifierAndType();

        HMProperty[] properties = builder.getProperties();

        for (int i = 0; i < properties.length; i++) {
            HMProperty property = properties[i];
            byte[] propertyBytes = property.getPropertyBytes();
            bytes = Bytes.concatBytes(bytes, propertyBytes);
        }
    }

    public static class Builder {
        private Type type;
        private byte[] nonce;
        private byte[] signature;

        private Calendar timestamp;

        protected ArrayList<HMProperty> propertiesBuilder = new ArrayList<>();

        public Builder(Type type) {
            this.type = type;
        }

        public Builder addProperty(HMProperty property) {
            propertiesBuilder.add(property);
            return this;
        }

        /**
         * @param nonce The nonce used for the signature.
         * @return The nonce.
         */
        public Builder setNonce(byte[] nonce) {
            this.nonce = nonce;
            addProperty(new Property((byte) 0xA0, nonce));
            return this;
        }

        /**
         * @param signature The signature for the signed bytes(the whole command except the
         *                  signature property)
         * @return The builder.
         */
        public Builder setSignature(byte[] signature) {
            this.signature = signature;
            addProperty(new Property((byte) 0xA1, signature));
            return this;
        }

        /**
         * @param timestamp The timestamp of when the data was transmitted from the car.
         * @return The builder.
         */
        public Builder setTimestamp(Calendar timestamp) {
            this.timestamp = timestamp;
            addProperty(new CalendarProperty(TIMESTAMP_IDENTIFIER, timestamp));
            return this;
        }

        public CommandWithProperties build() {
            return new CommandWithProperties(this);
        }

        protected HMProperty[] getProperties() {
            return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
        }
    }
}
