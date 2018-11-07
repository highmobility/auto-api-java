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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Connect the car to a Wi-Fi network.
 */
public class ConnectToNetwork extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x02);
    public static final byte PASSWORD_IDENTIFIER = 0x05;

    private String ssid;
    private NetworkSecurity security;
    private String password;

    /**
     * @return The network SSID.
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * @return The network security.
     */
    public NetworkSecurity getSecurity() {
        return security;
    }

    /**
     * @return The network password.
     */
    @Nullable public String getPassword() {
        return password;
    }

    /**
     * Connect the car to a Wi-Fi network.
     *
     * @param ssid     The network SSID.
     * @param security Network security.
     * @param password The password.
     */
    public ConnectToNetwork(String ssid, NetworkSecurity security, @Nullable String password) {
        super(TYPE, getProperties(ssid, security, password));
        this.ssid = ssid;
        this.security = security;
        this.password = password;
    }

    static HMProperty[] getProperties(String ssid, NetworkSecurity security, String password) {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (ssid != null)
            propertiesBuilder.add(new StringProperty(WifiState.SSID_IDENTIFIER, ssid));
        if (security != null) {
            security.setIdentifier(WifiState.SECURITY_IDENTIFIER);
            propertiesBuilder.add(security);
        }
        if (password != null)
            propertiesBuilder.add(new StringProperty(PASSWORD_IDENTIFIER, password));

        return propertiesBuilder.toArray(new HMProperty[0]);
    }

    ConnectToNetwork(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (Property property : properties) {
            switch (property.getPropertyIdentifier()) {
                case WifiState.SSID_IDENTIFIER:
                    ssid = Property.getString(property.getValueBytes());
                    break;
                case WifiState.SECURITY_IDENTIFIER:
                    security = NetworkSecurity.fromByte(property.getValueByte());
                    break;
                case PASSWORD_IDENTIFIER:
                    password = Property.getString(property.getValueBytes());
                    break;
            }
        }
    }
}