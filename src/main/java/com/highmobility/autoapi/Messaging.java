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
import javax.annotation.Nullable;

/**
 * The Messaging capability
 */
public class Messaging {
    public static final int IDENTIFIER = Identifier.MESSAGING;

    public static final byte PROPERTY_TEXT = 0x01;
    public static final byte PROPERTY_HANDLE = 0x02;

    /**
     * The messaging state
     */
    public static class State extends SetCommand {
        Property<String> text = new Property(String.class, PROPERTY_TEXT);
        Property<String> handle = new Property(String.class, PROPERTY_HANDLE);
    
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
     * Message received
     */
    public static class MessageReceived extends SetCommand {
        Property<String> text = new Property(String.class, PROPERTY_TEXT);
        Property<String> handle = new Property(String.class, PROPERTY_HANDLE);
    
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
}