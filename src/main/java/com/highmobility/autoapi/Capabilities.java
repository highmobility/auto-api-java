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
            super(IDENTIFIER);
        }
    
        GetCapabilities(byte[] bytes) throws CommandParseException {
            super(bytes);
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