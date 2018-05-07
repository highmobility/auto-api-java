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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

/**
 * Command sent when a Wi-Fi State command is received by the car. The new state is included in the
 * command payload and may be the result of user, device or car triggered action.
 */
public class WifiState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x01);
    private static final byte ENABLED_IDENTIFIER = 0x01;
    private static final byte CONNECTED_IDENTIFIER = 0x02;
    public static final byte SSID_IDENTIFIER = 0x03;

    Boolean enabled;
    Boolean connected;
    String ssid;
    NetworkSecurity security;

    /**
     * @return Whether Wi-Fi is enabled.
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * @return Whether Wi-Fi is connected.
     */
    public Boolean isConnected() {
        return connected;
    }

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

    public WifiState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case ENABLED_IDENTIFIER:
                    enabled = Property.getBool(property.getValueByte());
                    break;
                case CONNECTED_IDENTIFIER:
                    connected = Property.getBool(property.getValueByte());
                    break;
                case SSID_IDENTIFIER:
                    ssid = Property.getString(property.getValueBytes());
                    break;
                case NetworkSecurity.IDENTIFIER:
                    security = NetworkSecurity.fromByte(property.getValueByte());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WifiState(Builder builder) {
        super(builder);
        enabled = builder.enabled;
        connected = builder.connected;
        ssid = builder.ssid;
        security = builder.security;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Boolean enabled;
        private Boolean connected;
        private String ssid;
        private NetworkSecurity security;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param enabled The Wi-Fi state.
         * @return The builder.
         */
        public Builder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            addProperty(new BooleanProperty(ENABLED_IDENTIFIER, enabled));
            return this;
        }

        /**
         * @param connected The connection state.
         * @return The builder.
         */
        public Builder setConnected(Boolean connected) {
            this.connected = connected;
            addProperty(new BooleanProperty(CONNECTED_IDENTIFIER, connected));
            return this;
        }

        /**
         * @param ssid The network SSID.
         * @return The builder.
         */
        public Builder setSsid(String ssid) {
            this.ssid = ssid;
            addProperty(new StringProperty(SSID_IDENTIFIER, ssid));
            return this;
        }

        /**
         * @param security The network security.
         * @return The builder.
         */
        public Builder setSecurity(NetworkSecurity security) {
            this.security = security;
            addProperty(security);
            return this;
        }

        public WifiState build() {
            return new WifiState(this);
        }
    }
}