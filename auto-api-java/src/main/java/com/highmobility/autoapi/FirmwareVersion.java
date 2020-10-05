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
import com.highmobility.autoapi.value.HmkitVersion;
import com.highmobility.value.Bytes;

/**
 * The Firmware Version capability
 */
public class FirmwareVersion {
    public static final int IDENTIFIER = Identifier.FIRMWARE_VERSION;

    public static final byte PROPERTY_HMKIT_VERSION = 0x01;
    public static final byte PROPERTY_HMKIT_BUILD_NAME = 0x02;
    public static final byte PROPERTY_APPLICATION_VERSION = 0x03;

    /**
     * Get firmware version
     */
    public static class GetFirmwareVersion extends GetCommand<State> {
        /**
         * Get all Firmware Version properties
         */
        public GetFirmwareVersion() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Firmware Version properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetFirmwareVersion(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Firmware Version properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetFirmwareVersion(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetFirmwareVersion(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Firmware Version properties
     * 
     * @deprecated use {@link GetFirmwareVersion#GetFirmwareVersion(byte...)} instead
     */
    @Deprecated
    public static class GetFirmwareVersionProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetFirmwareVersionProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetFirmwareVersionProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetFirmwareVersionProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The firmware version state
     */
    public static class State extends SetCommand {
        Property<HmkitVersion> hmKitVersion = new Property<>(HmkitVersion.class, PROPERTY_HMKIT_VERSION);
        Property<String> hmKitBuildName = new Property<>(String.class, PROPERTY_HMKIT_BUILD_NAME);
        Property<String> applicationVersion = new Property<>(String.class, PROPERTY_APPLICATION_VERSION);
    
        /**
         * @return HMKit version
         */
        public Property<HmkitVersion> getHmKitVersion() {
            return hmKitVersion;
        }
    
        /**
         * @return HMKit version build name
         */
        public Property<String> getHmKitBuildName() {
            return hmKitBuildName;
        }
    
        /**
         * @return Application version
         */
        public Property<String> getApplicationVersion() {
            return applicationVersion;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_HMKIT_VERSION: return hmKitVersion.update(p);
                        case PROPERTY_HMKIT_BUILD_NAME: return hmKitBuildName.update(p);
                        case PROPERTY_APPLICATION_VERSION: return applicationVersion.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            hmKitVersion = builder.hmKitVersion;
            hmKitBuildName = builder.hmKitBuildName;
            applicationVersion = builder.applicationVersion;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<HmkitVersion> hmKitVersion;
            private Property<String> hmKitBuildName;
            private Property<String> applicationVersion;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param hmKitVersion HMKit version
             * @return The builder
             */
            public Builder setHmKitVersion(Property<HmkitVersion> hmKitVersion) {
                this.hmKitVersion = hmKitVersion.setIdentifier(PROPERTY_HMKIT_VERSION);
                addProperty(this.hmKitVersion);
                return this;
            }
            
            /**
             * @param hmKitBuildName HMKit version build name
             * @return The builder
             */
            public Builder setHmKitBuildName(Property<String> hmKitBuildName) {
                this.hmKitBuildName = hmKitBuildName.setIdentifier(PROPERTY_HMKIT_BUILD_NAME);
                addProperty(this.hmKitBuildName);
                return this;
            }
            
            /**
             * @param applicationVersion Application version
             * @return The builder
             */
            public Builder setApplicationVersion(Property<String> applicationVersion) {
                this.applicationVersion = applicationVersion.setIdentifier(PROPERTY_APPLICATION_VERSION);
                addProperty(this.applicationVersion);
                return this;
            }
        }
    }
}