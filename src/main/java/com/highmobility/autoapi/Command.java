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
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import javax.annotation.Nullable;

/**
 * Used for commands with properties. Can have 0 properties.
 */
public class Command extends Bytes {
    /*
    Properties logic:
    * Empty properties are created as fields in subclasses with correct identifier but 0x00 bytes
      and no components.

    * Empty property will be updated with a base property that has the components parsed. If
      the subclass knows the identifier, it copies the components and replaces the base property
      with itself.
     */
    public static Logger logger = LoggerFactory.getLogger(Command.class);
    private static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be " +
            "null";
    public static final byte NONCE_IDENTIFIER = (byte) 0xA0;
    public static final byte SIGNATURE_IDENTIFIER = (byte) 0xA1;
    public static final byte TIMESTAMP_IDENTIFIER = (byte) 0xA2;

    Integer type;
    Integer identifier;

    Property[] properties;
    Bytes nonce;
    Bytes signature;
    Calendar timestamp;

    public Command(Integer identifier, int size) {
        super(size);
        this.identifier = identifier;
    }

    protected Command() {
        super();
    }

    /**
     * @return The nonce for the signature.
     */
    @Nullable public Bytes getNonce() {
        return nonce;
    }

    /**
     * @return The signature for the signed bytes(the whole command except the signature property).
     */
    @Nullable public Bytes getSignature() {
        return signature;
    }

    /**
     * @return Timestamp of when the data was transmitted from the car.
     */
    @Nullable public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     * @return The bytes that are signed with the signature
     */
    public Bytes getSignedBytes() {
        return new Bytes(Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3 - 3));
    }

    /**
     * States are commands that describe some properties of the vehicle. They are usually returned
     * after the state changes, as a response for a get command or as a state in {@link
     * VehicleStatusState#getStates()}.
     * <p>
     * States can have 0 or more properties.
     *
     * @return True if command is a state.
     */
    public boolean isState() {
        return false;
    }

    protected boolean propertiesExpected() {
        return false;
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

    /**
     * @return The identifier of the command.
     */
    public Integer getIdentifier() {
        return identifier;
    }

    /**
     * @return The type of the command.
     */
    public Integer getType() {
        return type;
    }

    Command(Bytes bytes) {
        this(bytes.getByteArray());
    }

    Command(byte[] bytes) {
        super(bytes);
        setTypeAndBytes(bytes);

        if (propertiesExpected() && bytes.length < 7)
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        ArrayList<Property> builder = new ArrayList<>();
        PropertyEnumeration enumeration = new PropertyEnumeration(this.bytes);

        // create the base properties
        while (enumeration.hasMoreElements()) {
            PropertyEnumeration.EnumeratedProperty propertyEnumeration = enumeration.nextElement();
            if (propertyEnumeration.isValid(bytes.length)) {
                Property property = new Property(Arrays.copyOfRange(bytes, propertyEnumeration
                                .valueStart - 3,
                        propertyEnumeration.valueStart + propertyEnumeration.size));
                builder.add(property);
            }
        }

        // find universal properties
        findUniversalProperties(identifier, type, builder.toArray(new Property[0]));
    }

    Command(Integer identifier, Integer type, Property[] properties) {
        this.type = type;
        this.identifier = identifier;
        // here there are no timestamps. This constructor is called from setter commands only.
        findUniversalProperties(identifier, type, properties, true);
    }

    private void setTypeAndBytes(byte[] bytes) {
        if (bytes == null || bytes.length < 3) {
            byte firstByte = 0, secondByte = 0, thirdByte = 0;
            if (bytes != null) {
                if (bytes.length > 0) firstByte = bytes[0];
                if (bytes.length > 1) secondByte = bytes[1];
                if (bytes.length > 2) thirdByte = bytes[2];
            }

            identifier = Identifier.fromBytes(firstByte, secondByte);
            type = Type.fromByte(thirdByte);
        } else {
            identifier = Identifier.fromBytes(bytes);
            type = Type.fromByte(bytes[2]);
        }
    }

    protected void findUniversalProperties(Integer identifier, Integer type,
                                           Property[] properties) {
        findUniversalProperties(identifier, type, properties, false);
    }

    protected void findUniversalProperties(Integer identifier, Integer type, Property[] properties,
                                           boolean createBytes) {
        if (propertiesExpected() && (properties == null || properties.length == 0))
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        this.properties = properties;

        // if from builder, bytes need to be built
        byte[] identifierBytes = Identifier.toBytes(identifier);
        if (createBytes) bytes = new byte[]{
                identifierBytes[0], identifierBytes[1], Type.toByte(type)
        };

        for (int i = 0; i < properties.length; i++) {
            try {
                Property property = properties[i];
                if (createBytes) bytes = ByteUtils.concatBytes(bytes, property.getByteArray());

                if (property.getPropertyIdentifier() == NONCE_IDENTIFIER) {
                    if (property.getValueComponent().getValueBytes().getLength() != 9)
                        continue; // invalid nonce length, just ignore
                    nonce = property.getValueComponent().getValueBytes();
                } else if (property.getPropertyIdentifier() == SIGNATURE_IDENTIFIER) {
                    if (property.getValueComponent().getValueBytes().getLength() != 64)
                        continue; // ignore invalid length
                    signature = property.getValueComponent().getValueBytes();
                } else if (property.getPropertyIdentifier() == TIMESTAMP_IDENTIFIER) {
                    timestamp = Property.getCalendar(property.getValueComponent().getValueBytes());
                }
            } catch (Exception e) {
                // ignore if some universal property had invalid data. just keep the base one.
            }
        }

        // iterator is used by subclass
        propertyIterator = new PropertyIterator();
    }

    // Used to catch the property parsing exception, managing parsed properties in this class.
    protected PropertyIterator propertyIterator;

    protected class PropertyIterator implements Iterator<Property> {
        private int currentSize;
        private int propertiesReplaced = 0;

        PropertyIterator() {
            this.currentSize = Command.this.properties.length;
        }

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            boolean hasNext = currentIndex < currentSize && properties[currentIndex] != null;

            /*if (hasNext == false && propertiesExpected() && propertiesReplaced == 0) {
                // throw if propertiesExpected but returned 0 properties (child command
                // didn't find its property)
                throw new NoPropertiesException();
            }*/

            return hasNext;
        }

        @Override
        public Property next() {
            return properties[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void parseNext(PropertyIteration next) {
            Property nextProperty = next();

            try {
                Object parsedProperty = next.iterate(nextProperty);
                // failure and timestamp are separate properties and should be retained in base
                // properties array
                if (parsedProperty != null) {
                    // replace the base property with parsed one
                    properties[currentIndex - 1] = (Property) parsedProperty;
                    propertiesReplaced++;
                }
            } catch (Exception e) {
                nextProperty.printFailedToParse(e, null);
            }
        }
    }

    public interface PropertyIteration {
        /**
         * @param p The base property.
         * @return The parsed property or object, if matched to a field.
         * @throws Exception The property parse exception is caught internally. If a property
         *                   parsing fails, it is ignored and parsing continues with the next
         *                   property.
         */
        @Nullable
        Property iterate(@Nullable Property p) throws Exception;
    }
}
