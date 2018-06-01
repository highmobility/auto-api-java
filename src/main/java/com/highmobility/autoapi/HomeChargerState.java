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
import com.highmobility.autoapi.property.ChargeCurrentProperty;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.homecharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.homecharger.Charging;
import com.highmobility.autoapi.property.homecharger.PlugType;
import com.highmobility.autoapi.property.homecharger.PriceTariff;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.utils.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This command is sent when a Home Charger State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class HomeChargerState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x01);

    private static final byte CHARGING_POWER_IDENTIFIER = 0x04;
    private static final byte SOLAR_CHARGING_ACTIVE_IDENTIFIER = 0x05;
    private static final byte LOCATION_IDENTIFIER = 0x06;
    private static final byte HOTSPOT_ENABLED_IDENTIFIER = 0x08;
    private static final byte HOTSPOT_SSID_IDENTIFIER = 0x09;
    private static final byte HOTSPOT_SECURITY_IDENTIFIER = 0x0A;
    private static final byte HOTSPOT_PASSWORD_IDENTIFIER = 0x0B;

    Charging charging;
    AuthenticationMechanism authenticationMechanism;
    PlugType plugType;
    Float chargingPower;
    Boolean solarChargingActive;
    CoordinatesProperty location;
    ChargeCurrentProperty chargeCurrent;
    Boolean hotspotEnabled;
    String hotspotSsid;
    NetworkSecurity hotspotSecurity;
    String hotspotPassword;
    PriceTariff[] priceTariffs;

    /**
     * @return The charging state.
     */
    public Charging getCharging() {
        return charging;
    }

    /**
     * @return The authentication mechanism.
     */
    public AuthenticationMechanism getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    /**
     * @return The plug type.
     */
    public PlugType getPlugType() {
        return plugType;
    }

    /**
     * @return Charging power in kW.
     */
    public Float getChargingPower() {
        return chargingPower;
    }

    /**
     * @return Solar charging state.
     */
    public Boolean isSolarChargingActive() {
        return solarChargingActive;
    }

    /**
     * @return The location of the home charger.
     */
    public CoordinatesProperty getLocation() {
        return location;
    }

    /**
     * @return The charge current.
     */
    public ChargeCurrentProperty getChargeCurrent() {
        return chargeCurrent;
    }

    /**
     * @return The hotspot state.
     */
    public Boolean isHotspotEnabled() {
        return hotspotEnabled;
    }

    /**
     * @return The hotspot SSID.
     */
    public String getHotspotSsid() {
        return hotspotSsid;
    }

    /**
     * @return The hotspot security.
     */
    public NetworkSecurity getHotspotSecurity() {
        return hotspotSecurity;
    }

    /**
     * @return The hotspot password.
     */
    public String getHotspotPassword() {
        return hotspotPassword;
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
    public PriceTariff getPriceTariff(PriceTariff.PricingType pricingType) {
        if (priceTariffs != null) {
            for (PriceTariff tariff : priceTariffs) {
                if (tariff.getPricingType() == pricingType) return tariff;
            }
        }

        return null;
    }

    public HomeChargerState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            try {
                switch (property.getPropertyIdentifier()) {
                    case Charging.IDENTIFIER:
                        charging = Charging.fromByte(property.getValueByte());
                        break;
                    case AuthenticationMechanism.IDENTIFIER:
                        authenticationMechanism = AuthenticationMechanism.fromByte(property
                                .getValueByte());
                        break;
                    case PlugType.IDENTIFIER:
                        plugType = PlugType.fromByte(property.getValueByte());
                        break;
                    case CHARGING_POWER_IDENTIFIER:
                        chargingPower = Property.getFloat(property.getValueBytes());
                        break;
                    case SOLAR_CHARGING_ACTIVE_IDENTIFIER:
                        solarChargingActive = Property.getBool(property.getValueByte());
                        break;
                    case LOCATION_IDENTIFIER:
                        location = new CoordinatesProperty(property.getPropertyBytes());
                        break;
                    case ChargeCurrentProperty.IDENTIFIER:
                        chargeCurrent = new ChargeCurrentProperty(property.getPropertyBytes());
                        break;
                    case HOTSPOT_ENABLED_IDENTIFIER:
                        hotspotEnabled = Property.getBool(property.getValueByte());
                        break;
                    case HOTSPOT_SSID_IDENTIFIER:
                        hotspotSsid = Property.getString(property.getValueBytes());
                        break;
                    case HOTSPOT_SECURITY_IDENTIFIER:
                        hotspotSecurity = NetworkSecurity.fromByte(property.getValueByte());
                        break;
                    case HOTSPOT_PASSWORD_IDENTIFIER:
                        hotspotPassword = Property.getString(property.getValueBytes());
                        break;
                    case PriceTariff.IDENTIFIER:
                        if (getPriceTariffs() == null) priceTariffs = new PriceTariff[1];
                        else priceTariffs = Arrays.copyOf(priceTariffs, priceTariffs.length + 1);

                        priceTariffs[priceTariffs.length - 1] = new PriceTariff(property
                                .getPropertyBytes());
                        break;
                }
            } catch (Exception e) {
                logger.info("invalid property " + ByteUtils.hexFromBytes(property.getPropertyBytes()));
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private HomeChargerState(Builder builder) {
        super(builder);
        charging = builder.charging;
        authenticationMechanism = builder.authenticationMechanism;
        plugType = builder.plugType;
        chargingPower = builder.chargingPower;
        solarChargingActive = builder.solarChargingActive;
        location = builder.location;
        chargeCurrent = builder.chargeCurrent;
        hotspotEnabled = builder.hotspotEnabled;
        hotspotSsid = builder.hotspotSsid;
        hotspotSecurity = builder.hotspotSecurity;
        hotspotPassword = builder.hotspotPassword;
        priceTariffs = builder.priceTariffs.toArray(new PriceTariff[builder.priceTariffs.size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Charging charging;
        private AuthenticationMechanism authenticationMechanism;
        private PlugType plugType;
        private Float chargingPower;
        private Boolean solarChargingActive;
        private CoordinatesProperty location;
        private ChargeCurrentProperty chargeCurrent;
        private Boolean hotspotEnabled;
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
        public Builder setCharging(Charging charging) {
            this.charging = charging;
            addProperty(charging);
            return this;
        }

        /**
         * @param authenticationMechanism The authentication mechanism.
         * @return The builder.
         */
        public Builder setAuthenticationMechanism(AuthenticationMechanism authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism;
            addProperty(authenticationMechanism);
            return this;
        }

        /**
         * @param plugType The plug type.
         * @return The builder.
         */
        public Builder setPlugType(PlugType plugType) {
            this.plugType = plugType;
            addProperty(plugType);
            return this;
        }

        /**
         * @param chargingPower The charging power in kW.
         * @return The builder.
         */
        public Builder setChargingPower(Float chargingPower) {
            this.chargingPower = chargingPower;
            addProperty(new FloatProperty(CHARGING_POWER_IDENTIFIER, chargingPower));
            return this;
        }

        /**
         * @param solarChargingActive The solar charging state.
         * @return The builder.
         */
        public Builder setSolarChargingActive(Boolean solarChargingActive) {
            this.solarChargingActive = solarChargingActive;
            addProperty(new BooleanProperty(SOLAR_CHARGING_ACTIVE_IDENTIFIER, solarChargingActive));
            return this;
        }

        /**
         * @param location The location of the home charger.
         * @return The builder.
         */
        public Builder setLocation(CoordinatesProperty location) {
            location.setIdentifier(LOCATION_IDENTIFIER);
            this.location = location;
            addProperty(location);
            return this;
        }

        /**
         * @param chargeCurrent The charge current.
         * @return The builder.
         */
        public Builder setChargeCurrent(ChargeCurrentProperty chargeCurrent) {
            this.chargeCurrent = chargeCurrent;
            addProperty(chargeCurrent);
            return this;
        }

        /**
         * @param hotspotEnabled The Wi-Fi hotspot state.
         * @return The builder.
         */
        public Builder setHotspotEnabled(Boolean hotspotEnabled) {
            this.hotspotEnabled = hotspotEnabled;
            addProperty(new BooleanProperty(HOTSPOT_ENABLED_IDENTIFIER, hotspotEnabled));
            return this;
        }

        /**
         * @param hotspotSsid The hotspot SSID.
         * @return The builder.
         */
        public Builder setHotspotSsid(String hotspotSsid) {
            this.hotspotSsid = hotspotSsid;
            addProperty(new StringProperty(HOTSPOT_SSID_IDENTIFIER, hotspotSsid));
            return this;
        }

        /**
         * @param hotspotSecurity The hotspot security.
         * @return The builder.
         */
        public Builder setHotspotSecurity(NetworkSecurity hotspotSecurity) {
            this.hotspotSecurity = hotspotSecurity;
            hotspotSecurity.setIdentifier(HOTSPOT_SECURITY_IDENTIFIER);
            addProperty(hotspotSecurity);
            return this;
        }

        /**
         * @param hotspotPassword The hotspot password.
         * @return The builder.
         */
        public Builder setHotspotPassword(String hotspotPassword) {
            this.hotspotPassword = hotspotPassword;
            addProperty(new StringProperty(HOTSPOT_PASSWORD_IDENTIFIER, hotspotPassword));
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