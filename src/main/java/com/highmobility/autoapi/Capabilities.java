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
import com.highmobility.autoapi.value.Webhook;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

/**
 * The Capabilities capability
 */
public class Capabilities {
    public static final int IDENTIFIER = Identifier.CAPABILITIES;

    public static final byte PROPERTY_CAPABILITIES = 0x01;
    public static final byte PROPERTY_WEBHOOKS = 0x02;

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
     * Get specific capabilities properties
     */
    public static class GetCapabilitiesProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetCapabilitiesProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetCapabilitiesProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetCapabilitiesProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The capabilities state
     */
    public static class State extends SetCommand {
        Property<SupportedCapability>[] capabilities;
        Property<Webhook>[] webhooks;
    
        /**
         * @return The capabilities
         */
        public Property<SupportedCapability>[] getCapabilities() {
            return capabilities;
        }
    
        /**
         * @return The webhooks
         */
        public Property<Webhook>[] getWebhooks() {
            return webhooks;
        }
    
        /**
         * Get the capability support state.
         *
         * @param identifier The identifier of the Capability
         * @param propertyID   The property identifier
         * @return The supported state.
         */
        public boolean getSupported(Integer identifier, byte propertyID) {
            for (Property<SupportedCapability> capability : capabilities) {
                if (identifier.equals(capability.getValue().getCapabilityID())) {
                    for (Byte supportedPropertyID : capability.getValue().getSupportedPropertyIDs()) {
                        if (supportedPropertyID == propertyID) {
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
            ArrayList<Property> webhooksBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CAPABILITIES:
                            Property<SupportedCapability> capability = new Property(SupportedCapability.class, p);
                            capabilitiesBuilder.add(capability);
                            return capability;
                        case PROPERTY_WEBHOOKS:
                            Property<Webhook> webhook = new Property(Webhook.class, p);
                            webhooksBuilder.add(webhook);
                            return webhook;
                    }
    
                    return null;
                });
            }
    
            capabilities = capabilitiesBuilder.toArray(new Property[0]);
            webhooks = webhooksBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            capabilities = builder.capabilities.toArray(new Property[0]);
            webhooks = builder.webhooks.toArray(new Property[0]);
        }
    
        public static final class Builder extends SetCommand.Builder {
            private List<Property> capabilities = new ArrayList<>();
            private List<Property> webhooks = new ArrayList<>();
    
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
            
            /**
             * Add an array of webhooks.
             * 
             * @param webhooks The webhooks
             * @return The builder
             */
            public Builder setWebhooks(Property<Webhook>[] webhooks) {
                this.webhooks.clear();
                for (int i = 0; i < webhooks.length; i++) {
                    addWebhook(webhooks[i]);
                }
            
                return this;
            }
            /**
             * Add a single webhook.
             * 
             * @param webhook The webhook
             * @return The builder
             */
            public Builder addWebhook(Property<Webhook> webhook) {
                webhook.setIdentifier(PROPERTY_WEBHOOKS);
                addProperty(webhook);
                webhooks.add(webhook);
                return this;
            }
        }
    }
}