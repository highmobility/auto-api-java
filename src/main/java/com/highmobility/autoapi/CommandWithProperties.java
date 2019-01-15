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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyTimestamp;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Used for commands with properties. Can have 0 properties.
 */
public class CommandWithProperties extends Command {
    /*
    Properties logic:
    * Empty properties are always created in child classes with correct identifier but 0x00 bytes.
    These will not be replaced in base properties.

    * The empty property will be updated with property, timestamp and failure. If with the real
    property, then base property is replaced in base command properties array.

    * Otherwise timestamp and failure added as ivars.
     */
    private static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be " +
            "null";
    private static final byte NONCE_IDENTIFIER = (byte) 0xA0;
    private static final byte SIGNATURE_IDENTIFIER = (byte) 0xA1;
    private static final byte TIMESTAMP_IDENTIFIER = (byte) 0xA2;

    Property[] properties;
    Bytes nonce;
    Bytes signature;
    Calendar timestamp;

    PropertyTimestamp[] propertyTimestamps;
    PropertyFailure[] propertyFailures;

    private HashMap<Object, PropertyTimestamp> linkedPropertyTimestamps;

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
     * @return Timestamps for specific properties of when they were recorded by the car.
     */
    public PropertyTimestamp[] getPropertyTimestamps() {
        return propertyTimestamps;
    }

    /**
     * Get property timestamps for a property identifier.
     *
     * @param identifier The property identifier the timestamps are returned for.
     * @return An array of property timestamps.
     */
    public PropertyTimestamp[] getPropertyTimestamps(byte identifier) {
        ArrayList<PropertyTimestamp> builder = new ArrayList<>();

        for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
            if (propertyTimestamp.getTimestampPropertyIdentifier() == identifier) {
                builder.add(propertyTimestamp);
            }
        }

        return builder.toArray(new PropertyTimestamp[0]);
    }

    /**
     * Get the timestamp for a property. This compares the additional data of the timestamp with the
     * property data.
     *
     * @param property The property.
     * @return The property timestamp.
     */
    @Nullable public PropertyTimestamp getPropertyTimestamp(Property property) {
        // TODO: delete
        for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
            if (propertyTimestamp.getAdditionalData() != null &&
                    propertyTimestamp.getAdditionalData().equals(property)) {
                return propertyTimestamp;
            }
        }

        return null;
    }

    /**
     * Get the timestamp for a subclass field. Use the getter values in the parsed Command subclass
     * as the parameter.
     *
     * @param property The property.
     * @return The property timestamp.
     */
    @Nullable public PropertyTimestamp getPropertyTimestamp(Object property) {
        // TODO: 2019-01-11 delete
        if (linkedPropertyTimestamps == null) return null;
        for (HashMap.Entry<Object, PropertyTimestamp> pair : linkedPropertyTimestamps.entrySet()) {
            if (pair.getKey() == property) return pair.getValue();
        }

        return null;
    }

    /**
     * @return Failures of properties.
     */
    public PropertyFailure[] getPropertyFailures() {
        return propertyFailures;
    }

    /**
     * Get the failure for a property identifier.
     *
     * @param identifier The property identifier of the failure.
     * @return An array of property failures.
     */
    @Nullable public PropertyFailure getPropertyFailure(byte identifier) {
        for (PropertyFailure propertyFailure : propertyFailures) {
            if (propertyFailure.getFailedPropertyIdentifier() == identifier) return propertyFailure;
        }

        return null;
    }

    /**
     * @return The bytes that are signed with the signature
     */
    public Bytes getSignedBytes() {
        return new Bytes(Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3));
    }

    /**
     * States are commands that describe some properties of the vehicle. They are usually returned
     * after the state changes, as a response for a get command or as a state in {@link
     * VehicleStatus#getStates()}.
     * <p>
     * States can have 0 or more properties.
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

    CommandWithProperties(Type type, byte[] bytes) {
        this(bytes);
        this.type = type;
    }

    CommandWithProperties(byte[] bytes) {
        super(bytes);
        if (propertiesExpected() && bytes.length < 7)
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        ArrayList<Property> builder = new ArrayList<>();
        PropertyEnumeration enumeration = new PropertyEnumeration(bytes);

        // create the base properties
        while (enumeration.hasMoreElements()) {
            PropertyEnumeration.EnumeratedProperty propertyEnumeration = enumeration.nextElement();
            Property property = new Property(Arrays.copyOfRange(bytes, propertyEnumeration
                    .valueStart - 3, propertyEnumeration.valueStart + propertyEnumeration.size));
            builder.add(property);
        }

        builder.sort(new Property.SortForParsing());

        // find universal properties
        findUniversalProperties(builder.toArray(new Property[0]), false);
    }

    CommandWithProperties(Type type, Property[] properties) {
        super(type);
        // here there are no timestamps. This constructor is called from setter commands only.
        findUniversalProperties(properties, true);
    }

    CommandWithProperties(Type type, List<Property> properties) {
        this(type, properties.toArray(new Property[0]));
    }

    void findUniversalProperties(Property[] properties, boolean createBytes) {
        if (propertiesExpected() && (properties == null || properties.length == 0))
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        this.properties = properties;

        // if from builder, bytes need to be built
        if (createBytes) bytes = type.getIdentifierAndType();

        ArrayList<PropertyTimestamp> propertyTimestamps = new ArrayList<>();
        ArrayList<PropertyFailure> propertyFailures = new ArrayList<>();

        for (int i = 0; i < properties.length; i++) {
            try {
                Property property = properties[i];
                if (createBytes) bytes = ByteUtils.concatBytes(bytes, property.getByteArray());

                if (property.getPropertyIdentifier() == NONCE_IDENTIFIER) {
                    if (property.getValueLength() != 9)
                        continue; // invalid nonce length, just ignore
                    nonce = new Bytes(property.getValueBytes());
                } else if (property.getPropertyIdentifier() == SIGNATURE_IDENTIFIER) {
                    if (property.getValueLength() != 64) continue; // ignore invalid length
                    signature = new Bytes(property.getValueBytes());
                } else if (property.getPropertyIdentifier() == TIMESTAMP_IDENTIFIER) {
                    timestamp = Property.getCalendar(property.getValueBytes());
                } else if (property.getPropertyIdentifier() == PropertyTimestamp.IDENTIFIER) {
                    addTimestamp(property, i, propertyTimestamps);
                } else if (property.getPropertyIdentifier() == PropertyFailure.IDENTIFIER) {
                    addFailure(property, i, propertyFailures);
                }
            } catch (Exception e) {
                // ignore if some universal property had invalid data. just keep the base one.
            }
        }

        this.propertyFailures = propertyFailures.toArray(new PropertyFailure[0]);
        this.propertyTimestamps = propertyTimestamps.toArray(new PropertyTimestamp[0]);

        propertiesIterator = new PropertiesIterator();
        propertiesIterator2 = new PropertiesIterator2();
    }

    private void addTimestamp(Property property, int index,
                              ArrayList<PropertyTimestamp> propertyTimestamps) {
        PropertyTimestamp timestamp = new PropertyTimestamp(property.getByteArray());
        properties[index] = timestamp;
        propertyTimestamps.add(timestamp);
    }

    private void addFailure(Property property, int index,
                            ArrayList<PropertyFailure> propertyFailures) throws CommandParseException {
        PropertyFailure failure = new PropertyFailure(property.getByteArray());
        properties[index] = failure;
        propertyFailures.add(failure);
    }

    CommandWithProperties(Builder builder) throws IllegalArgumentException {
        super(builder.type);
        findUniversalProperties(builder.getProperties(), true);
    }

    public static class Builder {
        private Type type;
        private Bytes nonce;
        private Bytes signature;
        private Calendar timestamp;
        protected ArrayList<Property> propertiesBuilder = new ArrayList<>();

        public Builder(Type type) {
            this.type = type;
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
            addProperty(new Property((byte) 0xA0, nonce));
            return this;
        }

        /**
         * @param signature The signature for the signed bytes(the whole command except the
         *                  signature property)
         * @return The builder.
         */
        public Builder setSignature(Bytes signature) {
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

        protected Property[] getProperties() {
            return propertiesBuilder.toArray(new Property[0]);
        }
    }

    // TODO: 2019-01-11 delete v1 iterator
    // Used to catch the property parsing exception, managing parsed properties in this class.
    protected PropertiesIterator propertiesIterator;

    protected class PropertiesIterator implements Iterator<Property> {
        private int currentSize;

        PropertiesIterator() {
            this.currentSize = CommandWithProperties.this.properties.length;
        }

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < currentSize && properties[currentIndex] != null;
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
                if (parsedProperty != null) {
                    if (parsedProperty instanceof Property) {
                        // replace the base property with parsed one
                        Property castParsedProperty = (Property) parsedProperty;
                        properties[currentIndex - 1] = castParsedProperty;
                    }

                    // try to match a the property timestamp to the the property
                    for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
                        if (propertyTimestamp.getAdditionalData().equals(nextProperty)) {
                            // TODO: delete
                            if (linkedPropertyTimestamps == null)
                                linkedPropertyTimestamps = new HashMap<>();
                            linkedPropertyTimestamps.put(parsedProperty, propertyTimestamp);
                        }
                    }
                }
            } catch (Exception e) {
                nextProperty.printFailedToParse(e);
            }
        }
    }

    public interface PropertyIteration {
        /**
         * @param p The base property.
         * @return A parsed property or object, if did parse.
         * @throws Exception
         */
        @Nullable Object iterate(Property p) throws Exception;
    }

    // Used to catch the property parsing exception, managing parsed properties in this class.
    protected PropertiesIterator2 propertiesIterator2;

    protected class PropertiesIterator2 implements Iterator<Property> {
        private int currentSize;

        PropertiesIterator2() {
            this.currentSize = CommandWithProperties.this.properties.length;
        }

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < currentSize && properties[currentIndex] != null;
        }

        @Override
        public Property next() {
            return properties[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void parseNext(PropertyIteration2 next) {
            Property nextProperty = next();

            byte propertyIdentifier;
            PropertyFailure failure = null;
            PropertyTimestamp timestamp = null;

            if (nextProperty instanceof PropertyFailure) {
                failure = (PropertyFailure) nextProperty;
                propertyIdentifier = failure.getFailedPropertyIdentifier();
                nextProperty = null;
            } else if (nextProperty instanceof PropertyTimestamp) {
                timestamp = (PropertyTimestamp) nextProperty;
                propertyIdentifier = timestamp.getTimestampPropertyIdentifier();
                nextProperty = null;
            } else {
                propertyIdentifier = nextProperty.getPropertyIdentifier();
            }

            try {
                Object parsedProperty = next.iterate(propertyIdentifier, nextProperty, failure,
                        timestamp);
                // failure and timestamp are separate properties and should be retained in base
                // properties array
                if (parsedProperty != null && failure == null && timestamp == null) {
                    // replace the base property with parsed one
                    properties[currentIndex - 1] = (Property) parsedProperty;

                    // TODO: 2019-01-11 is this necessary??
                    // try to match a the property timestamp to the the property
                    for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
                        if (propertyTimestamp.getAdditionalData().equals(nextProperty)) {
                            if (linkedPropertyTimestamps == null)
                                linkedPropertyTimestamps = new HashMap<>();
                            linkedPropertyTimestamps.put(parsedProperty, propertyTimestamp);
                        }
                    }
                }
            } catch (Exception e) {
                nextProperty.printFailedToParse(e);
            }
        }
    }

    public interface PropertyIteration2 {
        /**
         * This could have either the property, property timestamp or property failure. It is used
         * to add timestamp and failure to the property.
         *
         * @param p The base property.
         * @return A parsed property or object, if did parse.
         * @throws Exception
         */
        @Nullable
        Object iterate(byte propertyIdentifier, @Nullable Property p,
                       @Nullable PropertyFailure failure, @Nullable PropertyTimestamp timestamp) throws Exception;
    }
}
