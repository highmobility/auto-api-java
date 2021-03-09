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
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Wi-Fi capability
 */
public class WiFi {
    public static final int IDENTIFIER = Identifier.WI_FI;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_NETWORK_CONNECTED = 0x02;
    public static final byte PROPERTY_NETWORK_SSID = 0x03;
    public static final byte PROPERTY_NETWORK_SECURITY = 0x04;
    public static final byte PROPERTY_PASSWORD = 0x05;

    /**
     * Get Wi-Fi property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Wi-Fi property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Wi-Fi property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Wi-Fi property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Wi-Fi properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Wi-Fi properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Wi-Fi properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Wi-Fi properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Wi-Fi properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Connect to network
     */
    public static class ConnectToNetwork extends SetCommand {
        Property<String> networkSSID = new Property<>(String.class, PROPERTY_NETWORK_SSID);
        Property<NetworkSecurity> networkSecurity = new Property<>(NetworkSecurity.class, PROPERTY_NETWORK_SECURITY);
        Property<String> password = new Property<>(String.class, PROPERTY_PASSWORD);
    
        /**
         * @return The network ssid
         */
        public Property<String> getNetworkSSID() {
            return networkSSID;
        }
        
        /**
         * @return The network security
         */
        public Property<NetworkSecurity> getNetworkSecurity() {
            return networkSecurity;
        }
        
        /**
         * @return The password
         */
        public Property<String> getPassword() {
            return password;
        }
        
        /**
         * Connect to network
         * 
         * @param networkSSID The network SSID
         * @param networkSecurity The network security
         * @param password The network password
         */
        public ConnectToNetwork(String networkSSID, NetworkSecurity networkSecurity, @Nullable String password) {
            super(IDENTIFIER);
        
            addProperty(this.networkSSID.update(networkSSID));
            addProperty(this.networkSecurity.update(networkSecurity));
            addProperty(this.password.update(password));
            createBytes();
        }
    
        ConnectToNetwork(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_NETWORK_SSID: return networkSSID.update(p);
                        case PROPERTY_NETWORK_SECURITY: return networkSecurity.update(p);
                        case PROPERTY_PASSWORD: return password.update(p);
                    }
                    return null;
                });
            }
            if (this.networkSSID.getValue() == null ||
                this.networkSecurity.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    /**
     * Forget network
     */
    public static class ForgetNetwork extends SetCommand {
        Property<String> networkSSID = new Property<>(String.class, PROPERTY_NETWORK_SSID);
    
        /**
         * @return The network ssid
         */
        public Property<String> getNetworkSSID() {
            return networkSSID;
        }
        
        /**
         * Forget network
         * 
         * @param networkSSID The network SSID
         */
        public ForgetNetwork(String networkSSID) {
            super(IDENTIFIER);
        
            addProperty(this.networkSSID.update(networkSSID));
            createBytes();
        }
    
        ForgetNetwork(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_NETWORK_SSID) return networkSSID.update(p);
                    return null;
                });
            }
            if (this.networkSSID.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    /**
     * Enable disable wi fi
     */
    public static class EnableDisableWiFi extends SetCommand {
        Property<EnabledState> status = new Property<>(EnabledState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<EnabledState> getStatus() {
            return status;
        }
        
        /**
         * Enable disable wi fi
         * 
         * @param status The status
         */
        public EnableDisableWiFi(EnabledState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        EnableDisableWiFi(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    return null;
                });
            }
            if (this.status.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    /**
     * The wi fi state
     */
    public static class State extends SetCommand {
        Property<EnabledState> status = new Property<>(EnabledState.class, PROPERTY_STATUS);
        Property<ConnectionState> networkConnected = new Property<>(ConnectionState.class, PROPERTY_NETWORK_CONNECTED);
        Property<String> networkSSID = new Property<>(String.class, PROPERTY_NETWORK_SSID);
        Property<NetworkSecurity> networkSecurity = new Property<>(NetworkSecurity.class, PROPERTY_NETWORK_SECURITY);
    
        /**
         * @return The status
         */
        public Property<EnabledState> getStatus() {
            return status;
        }
    
        /**
         * @return The network connected
         */
        public Property<ConnectionState> getNetworkConnected() {
            return networkConnected;
        }
    
        /**
         * @return The network SSID
         */
        public Property<String> getNetworkSSID() {
            return networkSSID;
        }
    
        /**
         * @return The network security
         */
        public Property<NetworkSecurity> getNetworkSecurity() {
            return networkSecurity;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_NETWORK_CONNECTED: return networkConnected.update(p);
                        case PROPERTY_NETWORK_SSID: return networkSSID.update(p);
                        case PROPERTY_NETWORK_SECURITY: return networkSecurity.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            networkConnected = builder.networkConnected;
            networkSSID = builder.networkSSID;
            networkSecurity = builder.networkSecurity;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<EnabledState> status;
            private Property<ConnectionState> networkConnected;
            private Property<String> networkSSID;
            private Property<NetworkSecurity> networkSecurity;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<EnabledState> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param networkConnected The network connected
             * @return The builder
             */
            public Builder setNetworkConnected(Property<ConnectionState> networkConnected) {
                this.networkConnected = networkConnected.setIdentifier(PROPERTY_NETWORK_CONNECTED);
                addProperty(this.networkConnected);
                return this;
            }
            
            /**
             * @param networkSSID The network SSID
             * @return The builder
             */
            public Builder setNetworkSSID(Property<String> networkSSID) {
                this.networkSSID = networkSSID.setIdentifier(PROPERTY_NETWORK_SSID);
                addProperty(this.networkSSID);
                return this;
            }
            
            /**
             * @param networkSecurity The network security
             * @return The builder
             */
            public Builder setNetworkSecurity(Property<NetworkSecurity> networkSecurity) {
                this.networkSecurity = networkSecurity.setIdentifier(PROPERTY_NETWORK_SECURITY);
                addProperty(this.networkSecurity);
                return this;
            }
        }
    }
}