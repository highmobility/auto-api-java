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
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Messaging capability
 */
public class Messaging {
    public static final int IDENTIFIER = Identifier.MESSAGING;

    public static final byte PROPERTY_TEXT = 0x01;
    public static final byte PROPERTY_HANDLE = 0x02;

    /**
     * Message received
     */
    public static class MessageReceived extends SetCommand {
        Property<String> text = new Property<>(String.class, PROPERTY_TEXT);
        Property<String> handle = new Property<>(String.class, PROPERTY_HANDLE);
    
        /**
         * @return The text
         */
        public Property<String> getText() {
            return text;
        }
        
        /**
         * @return The handle
         */
        public Property<String> getHandle() {
            return handle;
        }
        
        /**
         * Message received
         *
         * @param text The text
         * @param handle The optional handle of message
         */
        public MessageReceived(String text, @Nullable String handle) {
            super(IDENTIFIER);
        
            addProperty(this.text.update(text));
            addProperty(this.handle.update(handle));
            createBytes();
        }
    
        MessageReceived(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_TEXT: return text.update(p);
                        case PROPERTY_HANDLE: return handle.update(p);
                    }
                    return null;
                });
            }
            if (this.text.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    /**
     * The messaging state
     */
    public static class State extends SetCommand {
        Property<String> text = new Property<>(String.class, PROPERTY_TEXT);
        Property<String> handle = new Property<>(String.class, PROPERTY_HANDLE);
    
        /**
         * @return The text
         */
        public Property<String> getText() {
            return text;
        }
    
        /**
         * @return The optional handle of message
         */
        public Property<String> getHandle() {
            return handle;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_TEXT: return text.update(p);
                        case PROPERTY_HANDLE: return handle.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            text = builder.text;
            handle = builder.handle;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<String> text;
            private Property<String> handle;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param text The text
             * @return The builder
             */
            public Builder setText(Property<String> text) {
                this.text = text.setIdentifier(PROPERTY_TEXT);
                addProperty(this.text);
                return this;
            }
            
            /**
             * @param handle The optional handle of message
             * @return The builder
             */
            public Builder setHandle(Property<String> handle) {
                this.handle = handle.setIdentifier(PROPERTY_HANDLE);
                addProperty(this.handle);
                return this;
            }
        }
    }

    /**
     * Get all messaging property availabilities
     */
    public static class GetAllAvailabilities extends GetAvailabilityCommand {
        public GetAllAvailabilities() {
            super(IDENTIFIER);
        }
    
        GetAllAvailabilities(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get specific messaging property availabilities.
     */
    public static class GetAvailabilities extends GetAvailabilityCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetAvailabilities(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetAvailabilities(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetAvailabilities(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }
}