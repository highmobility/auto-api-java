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

import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Brand;
import com.highmobility.value.Bytes;

import java.util.ArrayList;
import java.util.Calendar;

public class SetCommand extends Command {
    ArrayList<Property> propertiesBuilder;

    SetCommand(Integer identifier) {
        this.identifier = identifier;
        type = Type.SET;
        // bytes are created later with createBytes()
    }

    /**
     * Add a property to the command. It is used in SetCommands, to create the bytes and properties
     * array.
     *
     * @param property The property.
     */
    protected void addProperty(Property property) {
        if (property == null || property.getValueComponent() == null) return;
        if (propertiesBuilder == null) propertiesBuilder = new ArrayList<>();
        propertiesBuilder.add(property);
    }

    protected void createBytes() {
        if (propertiesBuilder == null) propertiesBuilder = new ArrayList<>();
        findUniversalProperties(identifier, type, propertiesBuilder.toArray(new Property[0]),
                true);
    }

    protected String mandatoryPropertyErrorMessage(Class clazz) {
        return "Mandatory property does not exists for " + clazz.getSimpleName();
    }

    protected String optionalPropertyErrorMessage(Class clazz) {
        return "Optional property does not exists for " + clazz.getSimpleName();
    }

    SetCommand(byte[] bytes) throws ParseException {
        super(bytes);
        if (bytes[Command.COMMAND_TYPE_POSITION] != Type.SET) throw new ParseException();
    }

    public SetCommand(Builder builder) {
        super(builder.identifier, Type.SET, builder.getPropertiesArray());
    }

    /**
     * Use this builder to build an undefined capability.
     */
    public static final class BaseBuilder extends Builder<BaseBuilder> {
        public BaseBuilder(Integer identifier) {
            super(identifier);
        }
    }

    public static class Builder<Child> {
        private final Integer identifier;

        private ArrayList<Property> propertiesBuilder = new ArrayList<>();

        public Builder(Integer identifier) {
            this.identifier = identifier;
        }

        public Child addProperty(Property property) {
            propertiesBuilder.add(property);
            return (Child) this;
        }

        /**
         * @param nonce The nonce used for the signature.
         * @return The nonce.
         */
        public Child setNonce(Bytes nonce) {
            addProperty(new Property<>(NONCE_IDENTIFIER, nonce));
            return (Child) this;
        }

        /**
         * @param signature The signature for the signed bytes(the whole command except the
         *                  signature property)
         * @return The builder.
         */
        public Child setSignature(Bytes signature) {
            addProperty(new Property<>(SIGNATURE_IDENTIFIER, signature));
            return (Child) this;
        }

        /**
         * @param timestamp The timestamp of when the data was transmitted from the car.
         * @return The builder.
         */
        public Child setTimestamp(Calendar timestamp) {
            addProperty(new Property<>(TIMESTAMP_IDENTIFIER, timestamp));
            return (Child) this;
        }

        /**
         * @param vin The car vin.
         * @return The builder.
         */
        public Child setVin(String vin) {
            addProperty(new Property<>(VIN_IDENTIFIER, vin));
            return (Child) this;
        }

        /**
         * @param brand The car brand.
         * @return The builder.
         */
        public Child setBrand(Brand brand) {
            addProperty(new Property<>(BRAND_IDENTIFIER, brand));
            return (Child) this;
        }

        public SetCommand build() {
            return new SetCommand(this);
        }

        public Property[] getProperties() {
            return propertiesBuilder.toArray(new Property[0]);
        }

        private Property[] getPropertiesArray() {
            return propertiesBuilder.toArray(new Property[0]);
        }
    }
}
