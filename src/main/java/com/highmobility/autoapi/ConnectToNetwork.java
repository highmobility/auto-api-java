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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Connect the car to a Wi-Fi network.
 */
public class ConnectToNetwork extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x02);

    /**
     * Connect the car to a Wi-Fi network.
     *
     * @param ssid The network SSID formatted in UTF-8
     * @param security Network security
     * @param password The password formatted in UTF-8
     * @throws UnsupportedEncodingException When a string is in invalid format.
     */
    public ConnectToNetwork(String ssid, NetworkSecurity security, String password) throws
            UnsupportedEncodingException {
        super(TYPE, getProperties(ssid, security, password));
    }

    static HMProperty[] getProperties(String ssid, NetworkSecurity security, String password) throws UnsupportedEncodingException {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (ssid != null) propertiesBuilder.add(new StringProperty((byte) 0x03, ssid));
        if (security != null) propertiesBuilder.add(security);
        if (password != null) propertiesBuilder.add(new StringProperty((byte) 0x05, password));

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }

    ConnectToNetwork(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
