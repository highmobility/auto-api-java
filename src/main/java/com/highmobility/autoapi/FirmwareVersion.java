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

import com.highmobility.autoapi.property.IntArrayProperty;
import com.highmobility.autoapi.property.StringProperty;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Firmware Version is received by the car.
 */
public class FirmwareVersion extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FIRMWARE_VERSION, 0x01);

    private static final byte IDENTIFIER_SDK_VERSION = 0x01;
    private static final byte IDENTIFIER_SDK_BUILD = 0x02;
    private static final byte IDENTIFIER_APP_VERSION = 0x03;

    IntArrayProperty carSDKVersion = new IntArrayProperty(IDENTIFIER_SDK_VERSION);
    StringProperty carSDKBuild = new StringProperty(IDENTIFIER_SDK_BUILD);
    StringProperty applicationVersion = new StringProperty(IDENTIFIER_APP_VERSION);

    /**
     * @return The car SDK version.
     */
    @Nullable public IntArrayProperty getCarSDKVersion() {
        return carSDKVersion;
    }

    /**
     * @return The car SDK build.
     */
    @Nullable public StringProperty getCarSDKBuild() {
        return carSDKBuild;
    }

    /**
     * @return The application version.
     */
    @Nullable public StringProperty getApplicationVersion() {
        return applicationVersion;
    }

    FirmwareVersion(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_SDK_VERSION:
                        return carSDKVersion.update(p);
                    case IDENTIFIER_SDK_BUILD:
                        return carSDKBuild.update(p);
                    case IDENTIFIER_APP_VERSION:
                        return applicationVersion.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private FirmwareVersion(Builder builder) {
        super(builder);
        carSDKVersion = builder.carSdkVersion;
        carSDKBuild = builder.carSDKBuild;
        applicationVersion = builder.applicationVersion;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private IntArrayProperty carSdkVersion;
        private StringProperty carSDKBuild;
        private StringProperty applicationVersion;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param carSdkVersion The Car SDK version. Version is in format: "[major,minor,patch]"
         * @return The builder.
         */
        public Builder setCarSdkVersion(IntArrayProperty carSdkVersion) throws IllegalArgumentException {
            if (carSdkVersion.getValueLength() != 3) throw new IllegalArgumentException();
            this.carSdkVersion = carSdkVersion;
            addProperty(carSdkVersion.setIdentifier(IDENTIFIER_SDK_VERSION));
            return this;
        }

        /**
         * @param carSDKBuild The Car SDK build.
         * @return The builder.
         */
        public Builder setCarSDKBuild(StringProperty carSDKBuild) {
            this.carSDKBuild = carSDKBuild;
            addProperty(carSDKBuild.setIdentifier(IDENTIFIER_SDK_BUILD));
            return this;
        }

        /**
         * @param applicationVersion The application version.
         * @return The builder.
         */
        public Builder setApplicationVersion(StringProperty applicationVersion) {
            this.applicationVersion = applicationVersion;
            addProperty(applicationVersion.setIdentifier(IDENTIFIER_APP_VERSION));
            return this;
        }

        public FirmwareVersion build() {
            return new FirmwareVersion(this);
        }
    }
}