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
    public static class GetFirmwareVersion extends GetCommand {
        public GetFirmwareVersion() {
            super(IDENTIFIER);
        }
    
        GetFirmwareVersion(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }
    
    /**
     * Get specific firmware version properties
     */
    public static class GetFirmwareVersionProperties extends GetCommand {
        Bytes propertyIdentifiers;
    
        /**
         * @return The property identifiers.
         */
        public Bytes getPropertyIdentifiers() {
            return propertyIdentifiers;
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetFirmwareVersionProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetFirmwareVersionProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The firmware version state
     */
    public static class State extends SetCommand {
        Property<HmkitVersion> hmKitVersion = new Property(HmkitVersion.class, PROPERTY_HMKIT_VERSION);
        Property<String> hmKitBuildName = new Property(String.class, PROPERTY_HMKIT_BUILD_NAME);
        Property<String> applicationVersion = new Property(String.class, PROPERTY_APPLICATION_VERSION);
    
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