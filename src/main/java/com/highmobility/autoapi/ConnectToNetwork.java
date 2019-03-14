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

import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.autoapi.property.Property;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Connect the car to a Wi-Fi network.
 */
public class ConnectToNetwork extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x02);
    private static final byte IDENTIFIER_SSID = 0x03;
    private static final byte IDENTIFIER_SECURITY = 0x04;
    private static final byte IDENTIFIER_PASSWORD = 0x05;

    private Property<String> ssid = new Property(String.class, IDENTIFIER_SSID);
    private Property<String> password = new Property(String.class, IDENTIFIER_PASSWORD);
    private Property<NetworkSecurity> security = new Property(NetworkSecurity.class
            , IDENTIFIER_SECURITY);

    /**
     * @return The network SSID.
     */
    public Property<String> getSsid() {
        return ssid;
    }

    /**
     * @return The network security.
     */
    public Property<NetworkSecurity> getSecurity() {
        return security;
    }

    /**
     * @return The network password.
     */
    @Nullable public Property<String> getPassword() {
        return password;
    }

    /**
     * Connect the car to a Wi-Fi network.
     *
     * @param ssid     The network SSID.
     * @param security Network security.
     * @param password The password.
     */
    public ConnectToNetwork(String ssid, NetworkSecurity security,
                            @Nullable String password) {
        super(TYPE);

        List<Property> propertiesBuilder = new ArrayList<>();

        this.ssid.update(ssid);
        propertiesBuilder.add(this.ssid);

        this.security.update(security);
        propertiesBuilder.add(this.security);

        if (password != null) {
            this.password.update(password);
            propertiesBuilder.add(this.password);
        }

        createBytes(propertiesBuilder);
    }

    ConnectToNetwork(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_SSID:
                        return ssid.update(p);
                    case IDENTIFIER_SECURITY:
                        return security.update(p);
                    case IDENTIFIER_PASSWORD:
                        return password.update(p);
                }
                return null;
            });
        }
    }
}