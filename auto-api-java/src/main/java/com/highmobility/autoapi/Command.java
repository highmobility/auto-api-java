/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Brand;
import com.highmobility.value.Bytes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import static com.highmobility.autoapi.AutoApiLogger.getLogger;

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
    private static final String INVALID_VERSION_EXCEPTION = "Invalid AutoAPI version. This " +
            "package supports level %d.";

    public static final byte NONCE_IDENTIFIER = (byte) 0xA0;
    public static final byte SIGNATURE_IDENTIFIER = (byte) 0xA1;
    public static final byte TIMESTAMP_IDENTIFIER = (byte) 0xA2;
    public static final byte VIN_IDENTIFIER = (byte) 0xA3;
    public static final byte BRAND_IDENTIFIER = (byte) 0xA4;

    static final byte AUTO_API_VERSION = 0x0C;
    static final int HEADER_LENGTH = 1;
    static final int COMMAND_TYPE_POSITION = HEADER_LENGTH + 2;

    int type;
    int identifier;
    int autoApiVersion;

    Property[] properties;
    Bytes nonce;
    Bytes signature;
    Calendar timestamp;
    String vin;
    Brand brand;

    /**
     * @return The Auto API version of the command.
     */
    public int getAutoApiVersion() {
        return autoApiVersion;
    }

    /**
     * @return The identifier of the command.
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * @return The type of the command.
     * @see Type
     */
    public int getCommandType() {
        return type;
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
     * @return The unique Vehicle Identification Number
     */
    @Nullable public String getVin() {
        return vin;
    }

    /**
     * @return The vehicle brand
     */
    @Nullable public Brand getBrand() {
        return brand;
    }

    /**
     * @return The bytes that are signed with the signature
     */
    public Bytes getSignedBytes() {
        return new Bytes(Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3 - 3));
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

    protected Command() {
        super();
        this.autoApiVersion = AUTO_API_VERSION;
    }

    // only called from CommandResolver
    Command(byte[] bytes) {
        super(bytes);

        if (bytes[0] != AUTO_API_VERSION)
            getLogger().error(String.format(INVALID_VERSION_EXCEPTION, (int) AUTO_API_VERSION));

        setTypeAndBytes(bytes);

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

        findUniversalProperties(identifier, type, builder.toArray(new Property[0]));
    }

    Command(Integer identifier, Integer type, Property[] properties) {
        this.autoApiVersion = AUTO_API_VERSION;
        this.type = type;
        this.identifier = identifier;
        // here there are no timestamps. This constructor is called from setter commands only.
        findUniversalProperties(identifier, type, properties, true);
    }

    /**
     * @param sizeAfterType The command size after the header and command type.
     */
    Command(Integer identifier, int type, int sizeAfterType) {
        this.autoApiVersion = AUTO_API_VERSION;
        this.type = type;
        this.identifier = identifier;
        createBytes(sizeAfterType);
    }

    private void setTypeAndBytes(byte[] bytes) {
        byte versionByte = 0, firstByte = 0, secondByte = 0, thirdByte = 0;

        if (bytes != null) {
            if (bytes.length > 0) versionByte = bytes[0];
            if (bytes.length > 1) firstByte = bytes[1];
            if (bytes.length > 2) secondByte = bytes[2];
            if (bytes.length > 3) thirdByte = bytes[3];
        }

        identifier = Identifier.fromBytes(firstByte, secondByte);
        type = Type.fromByte(thirdByte);
        autoApiVersion = versionByte;
    }

    protected void findUniversalProperties(Integer identifier, Integer type, Property[] properties) {
        findUniversalProperties(identifier, type, properties, false);
    }

    protected void findUniversalProperties(Integer identifier, Integer type, Property[] properties,
                                           boolean createBytes) {
        this.properties = properties;

        // if from builder, bytes need to be built
        int propertyPosition = COMMAND_TYPE_POSITION + 1;
        if (createBytes) createBytes(getPropertiesSize(properties));

        for (int i = 0; i < properties.length; i++) {
            try {
                Property property = properties[i];

                if (createBytes) {
                    set(propertyPosition, property);
                    propertyPosition += property.size();
                }

                switch (property.getPropertyIdentifier()) {
                    case NONCE_IDENTIFIER: {
                        if (property.getValueComponent().getValueBytes().getLength() != 9)
                            continue; // invalid nonce length, just ignore
                        nonce = property.getValueComponent().getValueBytes();
                    }
                    case SIGNATURE_IDENTIFIER: {
                        if (property.getValueComponent().getValueBytes().getLength() != 64)
                            continue; // ignore invalid length
                        signature = property.getValueComponent().getValueBytes();
                    }
                    case TIMESTAMP_IDENTIFIER: {
                        timestamp = Property.getCalendar(property.getValueComponent().getValueBytes());
                    }
                    case VIN_IDENTIFIER: {
                        vin = Property.getString(property.getValueComponent().getValueBytes());
                    }
                    case BRAND_IDENTIFIER: {
                        brand = Brand.Companion.fromInt(property.getValueComponent().getValueByte());
                    }
                }
            } catch (Exception e) {
                // ignore if some universal property had invalid data. just keep the base one.
            }
        }

        // iterator is used by subclass
        propertyIterator = new PropertyIterator();
    }


    private void createBytes(int sizeAfterType) {
        bytes = new byte[4 + sizeAfterType];
        set(0, AUTO_API_VERSION);
        set(1, Identifier.toBytes(identifier));
        set(3, Type.toByte(type));
    }

    private int getPropertiesSize(Property[] properties) {
        int size = 0;
        for (int i = 0; i < properties.length; i++) size += properties[i].size();
        return size;
    }

    // Used to catch the property parsing exception, managing parsed properties in this class.
    protected PropertyIterator propertyIterator;

    protected class PropertyIterator implements Iterator<Property> {
        private final int currentSize;
        private int propertiesReplaced = 0;

        PropertyIterator() {
            this.currentSize = Command.this.properties.length;
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
