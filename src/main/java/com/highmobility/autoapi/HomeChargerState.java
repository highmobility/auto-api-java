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
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.autoapi.property.homecharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.homecharger.Charging;
import com.highmobility.autoapi.property.homecharger.PlugType;
import com.highmobility.autoapi.property.homecharger.PriceTariff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This command is sent when a Home Charger State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class HomeChargerState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x01);

    private static final byte IDENTIFIER_CHARGING = 0x01;
    private static final byte IDENTIFIER_PLUG_TYPE = 0x03;

    private static final byte IDENTIFIER_CHARGING_POWER = 0x04;
    private static final byte IDENTIFIER_SOLAR_CHARGING_ACTIVE = 0x05;
    private static final byte IDENTIFIER_HOTSPOT_ENABLED = 0x08;
    private static final byte IDENTIFIER_HOTSPOT_SSID = 0x09;
    private static final byte IDENTIFIER_HOTSPOT_SECURITY = 0x0A;
    private static final byte IDENTIFIER_HOTSPOT_PASSWORD = 0x0B;
    private static final byte IDENTIFIER_AUTHENTICATION_MECHANISM = 0x02;

    private static final byte IDENTIFIER_AUTHENTICATED = 0x0D;
    private static final byte IDENTIFIER_CHARGE_CURRENT_DC = 0x0E;
    private static final byte IDENTIFIER_MAXIMUM_CHARGE_CURRENT = 0x0F;
    private static final byte IDENTIFIER_MINIMUM_CHARGE_CURRENT = 0x10;
    private static final byte IDENTIFIER_COORDINATES = 0x11;
    private static final byte IDENTIFIER_PRICING_TARIFF = 0x12;

    Charging.Value charging;
    AuthenticationMechanism authenticationMechanism;
    PlugType plugType;
    FloatProperty chargingPower;
    BooleanProperty solarChargingActive;
    BooleanProperty hotspotEnabled;
    String hotspotSsid;
    NetworkSecurity hotspotSecurity;
    String hotspotPassword;

    // level8
    BooleanProperty authenticated;
    FloatProperty chargeCurrentDC;
    FloatProperty maximumChargeCurrent;
    FloatProperty minimumChargeCurrent;
    CoordinatesProperty coordinates;

    PriceTariff[] priceTariffs;

    /*Charging charging = new Charging(IDENTIFIER_CHARGING);
    AuthenticationMechanism authenticationMechanism = new AuthenticationMechanism
    (IDENTIFIER_AUTHENTICATION_MECHANISM);
    PlugType plugType = new PlugType(IDENTIFIER_PLUG_TYPE);
    FloatProperty chargingPower = new FloatProperty(IDENTIFIER_CHARGING_POWER);
    BooleanProperty solarChargingActive = new BooleanProperty(IDENTIFIER_SOLAR_CHARGING_ACTIVE);
    BooleanProperty hotspotEnabled = new BooleanProperty(IDENTIFIER_HOTSPOT_ENABLED);
    String hotspotSsid = new String(IDENTIFIER_HOTSPOT_SSID);
    NetworkSecurity hotspotSecurity = new NetworkSecurity(IDENTIFIER_HOTSPOT_SECURITY);
    String hotspotPassword = new String(IDENTIFIER_HOTSPOT_PASSWORD);

    // level8
    BooleanProperty authenticated = new BooleanProperty(IDENTIFIER_AUTHENTICATED);
    FloatProperty chargeCurrentDC = new FloatProperty(IDENTIFIER_CHARGE_CURRENT_DC);
    FloatProperty maximumChargeCurrent = new FloatProperty(IDENTIFIER_MAXIMUM_CHARGE_CURRENT);
    FloatProperty minimumChargeCurrent = new FloatProperty(IDENTIFIER_MINIMUM_CHARGE_CURRENT);
    CoordinatesProperty coordinates = new CoordinatesProperty(IDENTIFIER_COORDINATES);

    PriceTariff[] priceTariffs;*/

    /**
     * @return The charging state.
     */
    @Nullable public Charging.Value getCharging() {
        return charging;
    }

    /**
     * @return The authentication mechanism.
     */
    @Nullable public AuthenticationMechanism getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    /**
     * @return The plug type.
     */
    @Nullable public PlugType getPlugType() {
        return plugType;
    }

    /**
     * @return Charging power in kW.
     */
    @Nullable public FloatProperty getChargingPower() {
        return chargingPower;
    }

    /**
     * @return Solar charging state.
     */
    @Nullable public BooleanProperty isSolarChargingActive() {
        return solarChargingActive;
    }

    /**
     * @return The hotspot state.
     */
    @Nullable public BooleanProperty isHotspotEnabled() {
        return hotspotEnabled;
    }

    /**
     * @return The hotspot SSID.
     */
    @Nullable public String getHotspotSsid() {
        return hotspotSsid;
    }

    /**
     * @return The hotspot security.
     */
    @Nullable public NetworkSecurity getHotspotSecurity() {
        return hotspotSecurity;
    }

    /**
     * @return The hotspot password.
     */
    @Nullable public String getHotspotPassword() {
        return hotspotPassword;
    }

    /**
     * @return The authentication state.
     */
    @Nullable public BooleanProperty isAuthenticated() {
        return authenticated;
    }

    /**
     * @return The charge current (DC).
     */
    @Nullable public FloatProperty getChargeCurrent() {
        return chargeCurrentDC;
    }

    /**
     * @return The maximum possible charge current.
     */
    @Nullable public FloatProperty getMaximumChargeCurrent() {
        return maximumChargeCurrent;
    }

    /**
     * @return The minimal possible charge current.
     */
    @Nullable public FloatProperty getMinimumChargeCurrent() {
        return minimumChargeCurrent;
    }

    /**
     * @return The coordinates.
     */
    @Nullable public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     * @return All of the price tariffs.
     */
    public PriceTariff[] getPriceTariffs() {
        return priceTariffs;
    }

    /**
     * @param pricingType The pricing type.
     * @return Price tariff for the given pricing type.
     */
    @Nullable public PriceTariff getPriceTariff(PriceTariff.PricingType pricingType) {
        if (priceTariffs != null) {
            for (PriceTariff tariff : priceTariffs) {
                if (tariff.getPricingType() == pricingType) return tariff;
            }
        }

        return null;
    }

    public HomeChargerState(byte[] bytes) {
        super(bytes);
        ArrayList<PriceTariff> tariffs = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CHARGING:
                        charging = Charging.Value.fromByte(p.getValueByte());
                        return charging;
                    case IDENTIFIER_AUTHENTICATION_MECHANISM:
                        authenticationMechanism =
                                AuthenticationMechanism.fromByte(p.getValueByte());
                        return authenticationMechanism;
                    case IDENTIFIER_PLUG_TYPE:
                        plugType = PlugType.fromByte(p.getValueByte());
                        return plugType;
                    case IDENTIFIER_CHARGING_POWER:
                        chargingPower = new FloatProperty(p);
                        return chargingPower;
                    case IDENTIFIER_SOLAR_CHARGING_ACTIVE:
                        solarChargingActive = new BooleanProperty(p);
                        return solarChargingActive;
                    case IDENTIFIER_HOTSPOT_ENABLED:
                        hotspotEnabled = new BooleanProperty(p);
                        return hotspotEnabled;
                    case IDENTIFIER_HOTSPOT_SSID:
                        hotspotSsid = Property.getString(p.getValueBytes());
                        return hotspotSsid;
                    case IDENTIFIER_HOTSPOT_SECURITY:
                        hotspotSecurity = NetworkSecurity.fromByte(p.getValueByte());
                        return hotspotSecurity;
                    case IDENTIFIER_HOTSPOT_PASSWORD:
                        hotspotPassword = Property.getString(p.getValueBytes());
                        return hotspotPassword;
                    case IDENTIFIER_AUTHENTICATED:
                        authenticated = new BooleanProperty(p);
                        return authenticated;
                    case IDENTIFIER_CHARGE_CURRENT_DC:
                        chargeCurrentDC = new FloatProperty(p);
                        return chargeCurrentDC;
                    case IDENTIFIER_MAXIMUM_CHARGE_CURRENT:
                        maximumChargeCurrent = new FloatProperty(p);
                        return maximumChargeCurrent;
                    case IDENTIFIER_MINIMUM_CHARGE_CURRENT:
                        minimumChargeCurrent = new FloatProperty(p);
                        return minimumChargeCurrent;
                    case IDENTIFIER_COORDINATES:
                        coordinates = new CoordinatesProperty(p.getByteArray());
                        return coordinates;
                    case IDENTIFIER_PRICING_TARIFF:
                        PriceTariff t = new PriceTariff(p.getByteArray());
                        tariffs.add(t);
                        return t;
                }
                return null;
            });
        }

        priceTariffs = tariffs.toArray(new PriceTariff[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    // TBODO:
    private HomeChargerState(Builder builder) {
        super(builder);
        charging = builder.charging;
        authenticationMechanism = builder.authenticationMechanism;
        plugType = builder.plugType;
        chargingPower = builder.chargingPower;
        solarChargingActive = builder.solarChargingActive;
        hotspotEnabled = builder.hotspotEnabled;
        hotspotSsid = builder.hotspotSsid;
        hotspotSecurity = builder.hotspotSecurity;
        hotspotPassword = builder.hotspotPassword;
        priceTariffs = builder.priceTariffs.toArray(new PriceTariff[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Charging.Value charging;
        private AuthenticationMechanism authenticationMechanism;
        private PlugType plugType;
        private FloatProperty chargingPower;
        private BooleanProperty solarChargingActive;
        private BooleanProperty hotspotEnabled;
        private String hotspotSsid;
        private NetworkSecurity hotspotSecurity;
        private String hotspotPassword;
        private List<PriceTariff> priceTariffs = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param charging The charging state
         * @return The builder.
         */
        public Builder setCharging(Charging.Value charging) {
            this.charging = charging;
            addProperty(new Property(IDENTIFIER_CHARGING, charging.getByte()));
            return this;
        }

        /**
         * @param authenticationMechanism The authentication mechanism.
         * @return The builder.
         */
        public Builder setAuthenticationMechanism(AuthenticationMechanism authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism;
            addProperty(new Property(IDENTIFIER_AUTHENTICATION_MECHANISM,
                    authenticationMechanism.getByte()));
            return this;
        }

        /**
         * @param plugType The plug type.
         * @return The builder.
         */
        public Builder setPlugType(PlugType plugType) {
            this.plugType = plugType;
            addProperty(new Property(IDENTIFIER_PLUG_TYPE, plugType.getByte()));
            return this;
        }

        /**
         * @param chargingPower The charging power in kW.
         * @return The builder.
         */
        public Builder setChargingPower(FloatProperty chargingPower) {
            this.chargingPower = chargingPower;
            chargingPower.setIdentifier(IDENTIFIER_CHARGING_POWER);
            addProperty(chargingPower);
            return this;
        }

        /**
         * @param solarChargingActive The solar charging state.
         * @return The builder.
         */
        public Builder setSolarChargingActive(BooleanProperty solarChargingActive) {
            this.solarChargingActive = solarChargingActive;
            solarChargingActive.setIdentifier(IDENTIFIER_SOLAR_CHARGING_ACTIVE);
            addProperty(solarChargingActive);
            return this;
        }

        /**
         * @param hotspotEnabled The Wi-Fi hotspot state.
         * @return The builder.
         */
        public Builder setHotspotEnabled(BooleanProperty hotspotEnabled) {
            this.hotspotEnabled = hotspotEnabled;
            hotspotEnabled.setIdentifier(IDENTIFIER_HOTSPOT_ENABLED);
            addProperty(hotspotEnabled);
            return this;
        }

        /**
         * @param hotspotSsid The hotspot SSID.
         * @return The builder.
         */
        public Builder setHotspotSsid(String hotspotSsid) {
            this.hotspotSsid = hotspotSsid;
            addProperty(new StringProperty(IDENTIFIER_HOTSPOT_SSID, hotspotSsid));
            return this;
        }

        /**
         * @param hotspotSecurity The hotspot security.
         * @return The builder.
         */
        public Builder setHotspotSecurity(NetworkSecurity hotspotSecurity) {
            this.hotspotSecurity = hotspotSecurity;
            addProperty(new Property(IDENTIFIER_HOTSPOT_SECURITY, hotspotSecurity.getByte()));
            return this;
        }

        /**
         * @param hotspotPassword The hotspot password.
         * @return The builder.
         */
        public Builder setHotspotPassword(String hotspotPassword) {
            this.hotspotPassword = hotspotPassword;
            addProperty(new StringProperty(IDENTIFIER_HOTSPOT_PASSWORD, hotspotPassword));
            return this;
        }

        /**
         * @param priceTariffs The price tariffs.
         * @return The builder.
         */
        public Builder setPriceTariffs(PriceTariff[] priceTariffs) {
            this.priceTariffs = Arrays.asList(priceTariffs);
            for (PriceTariff priceTariff : priceTariffs) {
                addProperty(priceTariff);
            }
            return this;
        }

        /**
         * Add a single price tariff.
         *
         * @param priceTariff The price tariff.
         * @return The builder.
         */
        public Builder addPriceTariff(PriceTariff priceTariff) {
            priceTariffs.add(priceTariff);
            addProperty(priceTariff);
            return this;
        }

        public HomeChargerState build() {
            return new HomeChargerState(this);
        }
    }
}