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

/**
 * This message is sent when a Get Firmware Version is received by the car.
 */
public class FirmwareVersion extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FIRMWARE_VERSION, 0x01);

    String carSDKVersion;
    String carSDKBuild;
    String applicationVersion;

    public String getCarSDKVersion() {
        return carSDKVersion;
    }

    public String getCarSDKBuild() {
        return carSDKBuild;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public FirmwareVersion(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    byte[] value = property.getValueBytes();
                    carSDKVersion = (int) value[0] + "." +
                            (int) value[1] + "." +
                            (int) value[2];
                    break;
                case 0x02:
                    carSDKBuild = Property.getString(property.getValueBytes());
                    break;
                case 0x03:
                    applicationVersion = Property.getString(property.getValueBytes());
                    break;
            }
        }
    }
}