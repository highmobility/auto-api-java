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
 * Forget a network that the car has previously connected to.
 */
public class ForgetNetwork extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x03);

    private String ssid;

    /**
     * @return The network SSID.
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * Forget a network that the car has previously connected to.
     *
     * @param ssid The network name.
     */
    public ForgetNetwork(String ssid) {
        super(TYPE, StringProperty.getProperties(ssid, WifiState.IDENTIFIER_SSID));
        this.ssid = ssid;
    }

    ForgetNetwork(byte[] bytes) {
        super(bytes);
        for (Property property : properties) {
            if (property.getPropertyIdentifier() == WifiState.IDENTIFIER_SSID) {
                ssid = Property.getString(property.getValueBytes());
            }
        }
    }
}
