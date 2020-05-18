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
import com.highmobility.autoapi.value.SupportedCapability;
import java.util.ArrayList;
import java.util.List;

/**
 * The Capabilities capability
 */
public class Capabilities {
    public static final int IDENTIFIER = Identifier.CAPABILITIES;

    public static final byte PROPERTY_CAPABILITIES = 0x01;

    /**
     * Get capabilities
     */
    public static class GetCapabilities extends GetCommand {
        public GetCapabilities() {
            super(State.class, IDENTIFIER);
        }
    
        GetCapabilities(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The capabilities state
     */
    public static class State extends SetCommand {
        Property<SupportedCapability>[] capabilities;
    
        /**
         * @return The capabilities
         */
        public Property<SupportedCapability>[] getCapabilities() {
            return capabilities;
        }
    
        /**
         * Get the capability support state.
         *
         * @param identifier The identifier of the Capability
         * @param propertyId   The property identifier
         * @return The supported state.
         */
        public boolean getSupported(Integer identifier, byte propertyId) {
            for (Property<SupportedCapability> capability : capabilities) {
                if (identifier.equals(capability.getValue().getCapabilityID())) {
                    for (Byte supportedPropertyID : capability.getValue().getSupportedPropertyIDs()) {
                        if (supportedPropertyID == propertyId) {
                            return true;
                        }
                    }
                }
            }
    
            return false;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> capabilitiesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CAPABILITIES:
                            Property<SupportedCapability> capability = new Property(SupportedCapability.class, p);
                            capabilitiesBuilder.add(capability);
                            return capability;
                    }
    
                    return null;
                });
            }
    
            capabilities = capabilitiesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            capabilities = builder.capabilities.toArray(new Property[0]);
        }
    
        public static final class Builder extends SetCommand.Builder {
            private List<Property> capabilities = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of capabilities.
             * 
             * @param capabilities The capabilities
             * @return The builder
             */
            public Builder setCapabilities(Property<SupportedCapability>[] capabilities) {
                this.capabilities.clear();
                for (int i = 0; i < capabilities.length; i++) {
                    addCapability(capabilities[i]);
                }
            
                return this;
            }
            /**
             * Add a single capability.
             * 
             * @param capability The capability
             * @return The builder
             */
            public Builder addCapability(Property<SupportedCapability> capability) {
                capability.setIdentifier(PROPERTY_CAPABILITIES);
                addProperty(capability);
                capabilities.add(capability);
                return this;
            }
        }
    }
}