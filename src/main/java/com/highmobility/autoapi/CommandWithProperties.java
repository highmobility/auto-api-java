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
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Used for commands with properties. Can have 0 properties.
 */
public class CommandWithProperties extends Command {
    /*
    Properties logic:
    * Empty properties are always created in subclasses with correct identifier but 0x00 bytes.
    These will not be replaced in base properties.

    * The empty property will be updated with property, timestamp and failure. If with the real
    property, then base property is replaced in base command properties array.

    * Otherwise timestamp and failure added as ivars.
     */
    private static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be " +
            "null";
    public static final byte NONCE_IDENTIFIER = (byte) 0xA0;
    public static final byte SIGNATURE_IDENTIFIER = (byte) 0xA1;
    public static final byte TIMESTAMP_IDENTIFIER = (byte) 0xA2;

    Property[] properties;
    Bytes nonce;
    Bytes signature;
    Calendar timestamp;

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

    // TODO: 2019-03-01 cleanup comments
//    /**
//     * @return Timestamps for specific properties of when they were recorded by the car.
//     */
//    public PropertyTimestamp[] getPropertyTimestamps() {
//        return propertyTimestamps;
//    }

//    /**
//     * Get property timestamps for a property identifier.
//     *
//     * @param identifier The property identifier the timestamps are returned for.
//     * @return An array of property timestamps.
//     */
//    public PropertyTimestamp[] getPropertyTimestamps(byte identifier) {
//        ArrayList<PropertyTimestamp> builder = new ArrayList<>();
//
//        for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
//            if (propertyTimestamp.getTimestampPropertyIdentifier() == identifier) {
//                builder.add(propertyTimestamp);
//            }
//        }
//
//        return builder.toArray(new PropertyTimestamp[0]);
//    }
//
//    /**
//     * Get the timestamp for a property. This compares the additional data of the timestamp
//     with the
//     * property data.
//     *
//     * @param property The property.
//     * @return The property timestamp.
//     */
//    @Nullable public PropertyTimestamp getPropertyTimestamp(Property property) {
//        // TODO: delete
//        for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
//            if (propertyTimestamp.getAdditionalData() != null &&
//                    propertyTimestamp.getAdditionalData().equals(property)) {
//                return propertyTimestamp;
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Get the timestamp for a subclass field. Use the getter values in the parsed Command
//     subclass
//     * as the parameter.
//     *
//     * @param property The property.
//     * @return The property timestamp.
//     */
//    @Nullable public PropertyTimestamp getPropertyTimestamp(Object property) {
//        // TODO: 2019-01-11 delete
//        if (linkedPropertyTimestamps == null) return null;
//        for (HashMap.Entry<Object, PropertyTimestamp> pair : linkedPropertyTimestamps.entrySet
//        ()) {
//            if (pair.getKey() == property) return pair.getValue();
//        }
//
//        return null;
//    }
//
//    /**
//     * @return Failures of properties.
//     */
//    public PropertyFailure[] getPropertyFailures() {
//        return propertyFailures;
//    }
//
//    /**
//     * Get the failure for a property identifier.
//     *
//     * @param identifier The property identifier of the failure.
//     * @return An array of property failures.
//     */
//    @Nullable public PropertyFailure getPropertyFailure(byte identifier) {
//        for (PropertyFailure propertyFailure : propertyFailures) {
//            if (propertyFailure.getFailedPropertyIdentifier() == identifier) return
//            propertyFailure;
//        }
//
//        return null;
//    }

    /**
     * @return The bytes that are signed with the signature
     */
    public Bytes getSignedBytes() {
        return new Bytes(Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3 - 3));
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

        // find universal properties
        findUniversalProperties(builder.toArray(new Property[0]), false);
    }

    CommandWithProperties(Type type, Property[] properties) {
        super(type);
        // here there are no timestamps. This constructor is called from setter commands only.
        findUniversalProperties(properties, true);
    }

    CommandWithProperties(Type type) {
        super(type);
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
//        propertiesIterator = new PropertiesIterator();
        propertiesIterator2 = new PropertiesIterator2();
    }

//    private void addTimestamp(Property property, int index,
//                              ArrayList<PropertyTimestamp> propertyTimestamps) {
//        PropertyTimestamp timestamp = new PropertyTimestamp(property.getByteArray());
//        properties[index] = timestamp;
//        propertyTimestamps.add(timestamp);
//
//        // match the timestamp to a property
//
//        // find if there are multiple properties with this identifier
//        ArrayList<Property> propertiesForTimestamps = new ArrayList<>();
//        for (int i = 0; i < properties.length; i++) {
//            Property p = properties[i];
//            if (p.isUniversalProperty() == false &&
//                    p.getPropertyIdentifier() == timestamp.getTimestampPropertyIdentifier()) {
//                propertiesForTimestamps.add(p);
//            }
//        }
//
//        // if single property, add the timestamp
//        if (propertiesForTimestamps.size() == 1) {
//            propertiesForTimestamps.get(0).setPropertyTimestamp(timestamp);
//        } else if (propertiesForTimestamps.size() > 1 && timestamp.getAdditionalData() != null) {
//            // if multiple properties, check the additional data
//            for (int i = 0; i < propertiesForTimestamps.size(); i++) {
//                Property p = propertiesForTimestamps.get(i);
//                if (p.equals(timestamp.getAdditionalData())) {
//                    p.setPropertyTimestamp(timestamp);
//                    break;
//                }
//            }
//        }
//    }
//
//    private void addFailure(Property property, int index,
//                            ArrayList<PropertyFailure> propertyFailures) throws
//                            CommandParseException {
//        PropertyFailure failure = new PropertyFailure(property.getByteArray());
//        properties[index] = failure;
//        propertyFailures.add(failure);
//    }

    protected void createBytes(List<Property> properties) {
        bytes = type.getIdentifierAndType();

        if (propertiesExpected() && properties.size() == 0) throw new IllegalArgumentException();

        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            // TODO: 2019-03-08 could alloc 1x before the loop
            bytes = ByteUtils.concatBytes(bytes, property.getByteArray());
        }
    }

    protected void createBytes(Property property) {
        bytes = type.getIdentifierAndType();
        bytes = ByteUtils.concatBytes(bytes, property.getByteArray());
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
            addProperty(new Property(SIGNATURE_IDENTIFIER, signature));
            return this;
        }

        /**
         * @param timestamp The timestamp of when the data was transmitted from the car.
         * @return The builder.
         */
        public Builder setTimestamp(Calendar timestamp) {
            this.timestamp = timestamp;
            addProperty(new Property(TIMESTAMP_IDENTIFIER, timestamp));
            return this;
        }

        public CommandWithProperties build() {
            return new CommandWithProperties(this);
        }

        protected Property[] getProperties() {
            return propertiesBuilder.toArray(new Property[0]);
        }
    }

    /*// TODO: 2019-01-11 delete v1 iterator
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

            while (nextProperty.isUniversalProperty()) {
                nextProperty = next();
            }

            try {
                Object parsedProperty = next.iterate(nextProperty);
                if (parsedProperty != null) {
                    if (parsedProperty instanceof Property) {
                        // replace the base property with parsed one
                        Property castParsedProperty = (Property) parsedProperty;
                        properties[currentIndex - 1] = castParsedProperty;
                    }

                    // try to match a the property timestamp to the the property
//                    for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
//                        if (propertyTimestamp.getAdditionalData().equals(nextProperty)) {
//                            // TODO: delete
//                            if (linkedPropertyTimestamps == null)
//                                linkedPropertyTimestamps = new HashMap<>();
//                            linkedPropertyTimestamps.put(parsedProperty, propertyTimestamp);
//                        }
//                    }
                }
            } catch (Exception e) {
                nextProperty.printFailedToParse(e);
            }
        }
    }*/

    public interface PropertyIteration {
        /**
         * @param p The base property.
         * @return A parsed property or object, if did parse.
         * @throws Exception Any exception.
         */
        @Nullable Object iterate(Property p) throws Exception;
    }

    // Used to catch the property parsing exception, managing parsed properties in this class.
    protected PropertiesIterator2 propertiesIterator2;
    // TODO: 2019-02-07 throw if propertiesExpected but returned 0 properties (child command
    //  didnt find its property)

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

//            while (nextProperty instanceof PropertyTimestamp && hasNext()) {
//                nextProperty = next();
//            }

//            if (nextProperty instanceof PropertyFailure) {
//                // just create temp property with propertiesIterator2. So the fake
//                // empty property is not added to properties array.
//                // create empty property with the identifier.
//                try {
//                    PropertyFailure failure = (PropertyFailure) nextProperty;
//                    Property fakeProperty = new Property(failure.getFailedPropertyIdentifier());
//                    fakeProperty.setPropertyFailure(failure);
//                    next.iterate(fakeProperty);
//                } catch (Exception e) {
//                    nextProperty.printFailedToParse(e);
//                }
//                return;
//            }

            try {
                Object parsedProperty = next.iterate(nextProperty);
                // failure and timestamp are separate properties and should be retained in base
                // properties array
                if (parsedProperty != null) {
                    // replace the base property with parsed one
                    properties[currentIndex - 1] = (Property) parsedProperty;

//                    // TODO: 2019-01-11 delete
//                    // try to match a the property timestamp to the the property
//                    for (PropertyTimestamp propertyTimestamp : propertyTimestamps) {
//                        if (propertyTimestamp.getAdditionalData().equals(nextProperty)) {
//                            if (linkedPropertyTimestamps == null)
//                                linkedPropertyTimestamps = new HashMap<>();
//                            linkedPropertyTimestamps.put(parsedProperty, propertyTimestamp);
//                        }
//                    }
                }
            } catch (Exception e) {
                nextProperty.printFailedToParse(e);
            }
        }
    }

    public interface PropertyIteration2 {
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
