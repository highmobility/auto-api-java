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

import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyString;

import javax.annotation.Nullable;

/**
 * Command sent when a Wi-Fi State command is received by the car. The new state is included in the
 * command payload and may be the result of user, device or car triggered action.
 */
public class WifiState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x01);
    private static final byte IDENTIFIER_ENABLED = 0x01;
    private static final byte IDENTIFIER_CONNECTED = 0x02;
    public static final byte IDENTIFIER_SSID = 0x03;
    public static final byte IDENTIFIER_SECURITY = 0x04;

    ObjectProperty<Boolean> enabled = new ObjectProperty<>(Boolean.class, IDENTIFIER_ENABLED);
    ObjectProperty<Boolean> connected = new ObjectProperty<>(Boolean.class, IDENTIFIER_CONNECTED);
    ObjectPropertyString ssid = new ObjectPropertyString(IDENTIFIER_SSID);
    ObjectProperty<NetworkSecurity> security = new ObjectProperty(NetworkSecurity.class, IDENTIFIER_SECURITY);

    /**
     * @return Whether Wi-Fi is enabled.
     */
    @Nullable public ObjectProperty<Boolean> isEnabled() {
        return enabled;
    }

    /**
     * @return Whether Wi-Fi is connected.
     */
    @Nullable public ObjectProperty<Boolean> isConnected() {
        return connected;
    }

    /**
     * @return The network SSID.
     */
    @Nullable public ObjectPropertyString getSsid() {
        return ssid;
    }

    /**
     * @return The network security.
     */
    @Nullable public ObjectProperty<NetworkSecurity> getSecurity() {
        return security;
    }

    WifiState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ENABLED:
                        return enabled.update(p);
                    case IDENTIFIER_CONNECTED:
                        return connected.update(p);
                    case IDENTIFIER_SSID:
                        return ssid.update(p);
                    case IDENTIFIER_SECURITY:
                        return security.update(p);
                }

                return null;
            });
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
        private ObjectProperty<Boolean> enabled;
        private ObjectProperty<Boolean> connected;
        private ObjectPropertyString ssid;
        private ObjectProperty<NetworkSecurity> security;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param enabled The Wi-Fi state.
         * @return The builder.
         */
        public Builder setEnabled(ObjectProperty<Boolean> enabled) {
            this.enabled = enabled;
            enabled.setIdentifier(IDENTIFIER_ENABLED);
            addProperty(enabled);
            return this;
        }

        /**
         * @param connected The connection state.
         * @return The builder.
         */
        public Builder setConnected(ObjectProperty<Boolean> connected) {
            this.connected = connected;
            connected.setIdentifier(IDENTIFIER_CONNECTED);
            addProperty(connected);
            return this;
        }

        /**
         * @param ssid The network SSID.
         * @return The builder.
         */
        public Builder setSsid(ObjectPropertyString ssid) {
            this.ssid = ssid;
            ssid.setIdentifier(IDENTIFIER_SSID);
            addProperty(ssid);
            return this;
        }

        /**
         * @param security The network security.
         * @return The builder.
         */
        public Builder setSecurity(ObjectProperty<NetworkSecurity> security) {
            this.security = security;
            addProperty(security.setIdentifier(IDENTIFIER_SECURITY));
            return this;
        }

        public WifiState build() {
            return new WifiState(this);
        }
    }
}