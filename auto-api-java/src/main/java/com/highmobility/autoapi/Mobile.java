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
import com.highmobility.autoapi.value.ConnectionState;

/**
 * The Mobile capability
 */
public class Mobile {
    public static final int IDENTIFIER = Identifier.MOBILE;

    public static final byte PROPERTY_CONNECTION = 0x01;

    /**
     * Get Mobile property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Mobile property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Mobile properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Mobile properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The mobile state
     */
    public static class State extends SetCommand {
        Property<ConnectionState> connection = new Property<>(ConnectionState.class, PROPERTY_CONNECTION);
    
        /**
         * @return The connection
         */
        public Property<ConnectionState> getConnection() {
            return connection;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CONNECTION: return connection.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            connection = builder.connection;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<ConnectionState> connection;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param connection The connection
             * @return The builder
             */
            public Builder setConnection(Property<ConnectionState> connection) {
                this.connection = connection.setIdentifier(PROPERTY_CONNECTION);
                addProperty(this.connection);
                return this;
            }
        }
    }
}