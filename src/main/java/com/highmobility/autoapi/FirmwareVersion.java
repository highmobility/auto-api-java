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
import com.highmobility.autoapi.property.StringProperty;

/**
 * Command sent when a Get Firmware Version is received by the car.
 */
public class FirmwareVersion extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FIRMWARE_VERSION, 0x01);

    private static final byte SDK_VERSION_IDENTIFIER = 0x01;
    private static final byte SDK_BUILD_IDENTIFIER = 0x02;
    private static final byte APP_VERSION_IDENTIFIER = 0x03;

    String carSDKVersion;
    String carSDKBuild;
    String applicationVersion;

    /**
     * @return The car SDK version.
     */
    public String getCarSDKVersion() {
        return carSDKVersion;
    }

    /**
     * @return The car SDK build.
     */
    public String getCarSDKBuild() {
        return carSDKBuild;
    }

    /**
     * @return The application version.
     */
    public String getApplicationVersion() {
        return applicationVersion;
    }

    public FirmwareVersion(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case SDK_VERSION_IDENTIFIER:
                    carSDKVersion = identifiersToString(property.getValueBytes());
                    break;
                case SDK_BUILD_IDENTIFIER:
                    carSDKBuild = Property.getString(property.getValueBytes());
                    break;
                case APP_VERSION_IDENTIFIER:
                    applicationVersion = Property.getString(property.getValueBytes());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private static String identifiersToString(byte[] identifiers) {
        return (int) identifiers[0] + "." +
                (int) identifiers[1] + "." +
                (int) identifiers[2];
    }

    private FirmwareVersion(Builder builder) {
        super(builder);
        carSDKVersion = builder.carSDKVersion;
        carSDKBuild = builder.carSDKBuild;
        applicationVersion = builder.applicationVersion;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private String carSDKVersion;
        private String carSDKBuild;
        private String applicationVersion;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param carSDKVersionIdentifiers The Car SDK version identifiers: major, minor and patch.
         *                                 For instance for version 1.15.33 the identifiers would be
         *                                 {1, 15, 33}.
         * @return The builder.
         */
        public Builder setCarSDKVersion(int[] carSDKVersionIdentifiers) {
            if (carSDKVersionIdentifiers.length != 3) throw new IllegalArgumentException();

            byte[] identifiers = new byte[3];
            for (int i = 0; i < carSDKVersionIdentifiers.length; i++) {
                identifiers[i] = (byte) carSDKVersionIdentifiers[i];
            }

            carSDKVersion = FirmwareVersion.identifiersToString(identifiers);
            addProperty(new Property(SDK_VERSION_IDENTIFIER, identifiers));
            return this;
        }

        /**
         * @param carSDKBuild The Car SDK build.
         * @return The builder.
         */
        public Builder setCarSDKBuild(String carSDKBuild) {
            this.carSDKBuild = carSDKBuild;
            addProperty(new StringProperty(SDK_BUILD_IDENTIFIER, carSDKBuild));
            return this;
        }

        /**
         * @param applicationVersion The application version.
         * @return The builder.
         */
        public Builder setApplicationVersion(String applicationVersion) {
            this.applicationVersion = applicationVersion;
            addProperty(new StringProperty(APP_VERSION_IDENTIFIER, applicationVersion));
            return this;
        }

        public FirmwareVersion build() {
            return new FirmwareVersion(this);
        }
    }
}