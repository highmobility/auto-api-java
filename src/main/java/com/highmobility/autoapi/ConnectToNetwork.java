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
import com.highmobility.autoapi.value.NetworkSecurity;
import javax.annotation.Nullable;

/**
 * Connect to network
 */
public class ConnectToNetwork extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.WI_FI;

    public static final byte IDENTIFIER_NETWORK_SSID = 0x03;
    public static final byte IDENTIFIER_NETWORK_SECURITY = 0x04;
    public static final byte IDENTIFIER_PASSWORD = 0x05;

    Property<String> networkSSID = new Property(String.class, IDENTIFIER_NETWORK_SSID);
    Property<NetworkSecurity> networkSecurity = new Property(NetworkSecurity.class, IDENTIFIER_NETWORK_SECURITY);
    @Nullable Property<String> password = new Property(String.class, IDENTIFIER_PASSWORD);

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
    public @Nullable Property<String> getPassword() {
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
        addProperty(this.password.update(password), true);
    }

    ConnectToNetwork(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_NETWORK_SSID: return networkSSID.update(p);
                    case IDENTIFIER_NETWORK_SECURITY: return networkSecurity.update(p);
                    case IDENTIFIER_PASSWORD: return password.update(p);
                }
                return null;
            });
        }
        if (this.networkSSID.getValue() == null ||
            this.networkSecurity.getValue() == null) 
            throw new NoPropertiesException();
    }
}