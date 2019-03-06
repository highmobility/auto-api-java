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

import com.highmobility.autoapi.property.Coordinates;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.Property;

import com.highmobility.autoapi.property.homecharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.homecharger.Charging;
import com.highmobility.autoapi.property.homecharger.PlugType;
import com.highmobility.autoapi.property.homecharger.PriceTariff;

import java.util.ArrayList;
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
    private static final byte IDENTIFIER_PRICE_TARIFF = 0x12;

    Property<Charging> charging = new Property<>(Charging.class, IDENTIFIER_CHARGING);
    Property<AuthenticationMechanism> authenticationMechanism =
            new Property<>(AuthenticationMechanism.class,
                    IDENTIFIER_AUTHENTICATION_MECHANISM);
    Property<PlugType> plugType = new Property<>(PlugType.class, IDENTIFIER_PLUG_TYPE);
    Property<Float> chargingPower = new Property<>(Float.class,
            IDENTIFIER_CHARGING_POWER);
    Property<Boolean> solarChargingActive = new Property<>(Boolean.class,
            IDENTIFIER_SOLAR_CHARGING_ACTIVE);
    Property<Boolean> hotspotEnabled = new Property<>(Boolean.class,
            IDENTIFIER_HOTSPOT_ENABLED);
    Property<String> hotspotSsid = new Property(String.class, IDENTIFIER_HOTSPOT_SSID);
    Property<NetworkSecurity> hotspotSecurity = new Property<>(NetworkSecurity.class,
            IDENTIFIER_HOTSPOT_SECURITY);
    Property<String> hotspotPassword = new Property(String.class, IDENTIFIER_HOTSPOT_PASSWORD);

    // level8
    Property<Boolean> authenticated = new Property<>(Boolean.class,
            IDENTIFIER_AUTHENTICATED);
    Property<Float> chargeCurrentDC = new Property<>(Float.class,
            IDENTIFIER_CHARGE_CURRENT_DC);
    Property<Float> maximumChargeCurrent =
            new Property<>(Float.class, IDENTIFIER_MAXIMUM_CHARGE_CURRENT);
    Property<Float> minimumChargeCurrent =
            new Property<>(Float.class, IDENTIFIER_MINIMUM_CHARGE_CURRENT);
    Property<Coordinates> coordinates = new Property<>(Coordinates.class,
            IDENTIFIER_COORDINATES);

    Property<PriceTariff>[] priceTariffs;

    /**
     * @return The charging state.
     */
    @Nullable public Property<Charging> getCharging() {
        return charging;
    }

    /**
     * @return The authentication mechanism.
     */
    @Nullable public Property<AuthenticationMechanism> getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    /**
     * @return The plug type.
     */
    @Nullable public Property<PlugType> getPlugType() {
        return plugType;
    }

    /**
     * @return Charging power in kW.
     */
    @Nullable public Property<Float> getChargingPower() {
        return chargingPower;
    }

    /**
     * @return Solar charging state.
     */
    @Nullable public Property<Boolean> isSolarChargingActive() {
        return solarChargingActive;
    }

    /**
     * @return The hotspot state.
     */
    @Nullable public Property<Boolean> isHotspotEnabled() {
        return hotspotEnabled;
    }

    /**
     * @return The hotspot SSID.
     */
    @Nullable public Property<String> getHotspotSsid() {
        return hotspotSsid;
    }

    /**
     * @return The hotspot security.
     */
    @Nullable public Property<NetworkSecurity> getHotspotSecurity() {
        return hotspotSecurity;
    }

    /**
     * @return The hotspot password.
     */
    @Nullable public Property<String> getHotspotPassword() {
        return hotspotPassword;
    }

    /**
     * @return The authentication state.
     */
    @Nullable public Property<Boolean> isAuthenticated() {
        return authenticated;
    }

    /**
     * @return The charge current (DC).
     */
    @Nullable public Property<Float> getChargeCurrent() {
        return chargeCurrentDC;
    }

    /**
     * @return The maximum possible charge current.
     */
    @Nullable public Property<Float> getMaximumChargeCurrent() {
        return maximumChargeCurrent;
    }

    /**
     * @return The minimal possible charge current.
     */
    @Nullable public Property<Float> getMinimumChargeCurrent() {
        return minimumChargeCurrent;
    }

    /**
     * @return The coordinates.
     */
    @Nullable public Property<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return All of the price tariffs.
     */
    public Property<PriceTariff>[] getPriceTariffs() {
        return priceTariffs;
    }

    /**
     * @param pricingType The pricing type.
     * @return Price tariff for the given pricing type.
     */
    @Nullable
    public Property<PriceTariff> getPriceTariff(PriceTariff.PricingType pricingType) {
        if (priceTariffs != null) {
            for (Property<PriceTariff> tariff : priceTariffs) {
                if (tariff.getValue() != null && tariff.getValue().getPricingType() == pricingType)
                    return tariff;
            }
        }

        return null;
    }

    public HomeChargerState(byte[] bytes) {
        super(bytes);
        ArrayList<Property<PriceTariff>> tariffs = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CHARGING:
                        return charging.update(p);
                    case IDENTIFIER_AUTHENTICATION_MECHANISM:
                        return authenticationMechanism.update(p);
                    case IDENTIFIER_PLUG_TYPE:
                        return plugType.update(p);
                    case IDENTIFIER_CHARGING_POWER:
                        return chargingPower.update(p);
                    case IDENTIFIER_SOLAR_CHARGING_ACTIVE:
                        return solarChargingActive.update(p);
                    case IDENTIFIER_HOTSPOT_ENABLED:
                        return hotspotEnabled.update(p);
                    case IDENTIFIER_HOTSPOT_SSID:
                        return hotspotSsid.update(p);
                    case IDENTIFIER_HOTSPOT_SECURITY:
                        return hotspotSecurity.update(p);
                    case IDENTIFIER_HOTSPOT_PASSWORD:
                        return hotspotPassword.update(p);
                    case IDENTIFIER_AUTHENTICATED:
                        return authenticated.update(p);
                    case IDENTIFIER_CHARGE_CURRENT_DC:
                        return chargeCurrentDC.update(p);
                    case IDENTIFIER_MAXIMUM_CHARGE_CURRENT:
                        return maximumChargeCurrent.update(p);
                    case IDENTIFIER_MINIMUM_CHARGE_CURRENT:
                        return minimumChargeCurrent.update(p);
                    case IDENTIFIER_COORDINATES:
                        return coordinates.update(p);
                    case IDENTIFIER_PRICE_TARIFF:
                        Property<PriceTariff> t = new Property<>(PriceTariff.class, p);
                        tariffs.add(t);
                        return t;
                }
                return null;
            });
        }

        priceTariffs = tariffs.toArray(new Property[0]);
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
        priceTariffs = builder.priceTariffs.toArray(new Property[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<Charging> charging;
        private Property<AuthenticationMechanism> authenticationMechanism;
        private Property<PlugType> plugType;
        private Property<Float> chargingPower;
        private Property<Boolean> solarChargingActive;
        private Property<Boolean> hotspotEnabled;
        private Property<String> hotspotSsid;
        private Property<NetworkSecurity> hotspotSecurity;
        private Property<String> hotspotPassword;
        private List<Property<PriceTariff>> priceTariffs = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param charging The charging state
         * @return The builder.
         */
        public Builder setCharging(Property<Charging> charging) {
            this.charging = charging;
            addProperty(charging.setIdentifier(IDENTIFIER_CHARGING));
            return this;
        }

        /**
         * @param authenticationMechanism The authentication mechanism.
         * @return The builder.
         */
        public Builder setAuthenticationMechanism(Property<AuthenticationMechanism> authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism;
            addProperty(authenticationMechanism.setIdentifier(IDENTIFIER_AUTHENTICATION_MECHANISM));
            return this;
        }

        /**
         * @param plugType The plug type.
         * @return The builder.
         */
        public Builder setPlugType(Property<PlugType> plugType) {
            this.plugType = plugType;
            addProperty(plugType.setIdentifier(IDENTIFIER_PLUG_TYPE));
            return this;
        }

        /**
         * @param chargingPower The charging power in kW.
         * @return The builder.
         */
        public Builder setChargingPower(Property<Float> chargingPower) {
            this.chargingPower = chargingPower;
            chargingPower.setIdentifier(IDENTIFIER_CHARGING_POWER);
            addProperty(chargingPower);
            return this;
        }

        /**
         * @param solarChargingActive The solar charging state.
         * @return The builder.
         */
        public Builder setSolarChargingActive(Property<Boolean> solarChargingActive) {
            this.solarChargingActive = solarChargingActive;
            solarChargingActive.setIdentifier(IDENTIFIER_SOLAR_CHARGING_ACTIVE);
            addProperty(solarChargingActive);
            return this;
        }

        /**
         * @param hotspotEnabled The Wi-Fi hotspot state.
         * @return The builder.
         */
        public Builder setHotspotEnabled(Property<Boolean> hotspotEnabled) {
            this.hotspotEnabled = hotspotEnabled;
            hotspotEnabled.setIdentifier(IDENTIFIER_HOTSPOT_ENABLED);
            addProperty(hotspotEnabled);
            return this;
        }

        /**
         * @param hotspotSsid The hotspot SSID.
         * @return The builder.
         */
        public Builder setHotspotSsid(Property<String> hotspotSsid) {
            this.hotspotSsid = hotspotSsid;
            addProperty(hotspotSsid.setIdentifier(IDENTIFIER_HOTSPOT_SSID));
            return this;
        }

        /**
         * @param hotspotSecurity The hotspot security.
         * @return The builder.
         */
        public Builder setHotspotSecurity(Property<NetworkSecurity> hotspotSecurity) {
            this.hotspotSecurity = hotspotSecurity;
            addProperty(hotspotSecurity.setIdentifier(IDENTIFIER_HOTSPOT_SECURITY));
            return this;
        }

        /**
         * @param hotspotPassword The hotspot password.
         * @return The builder.
         */
        public Builder setHotspotPassword(Property<String> hotspotPassword) {
            this.hotspotPassword = hotspotPassword;
            addProperty(hotspotPassword.setIdentifier(IDENTIFIER_HOTSPOT_PASSWORD));
            return this;
        }

        /**
         * Set the price tariffs.
         *
         * @param priceTariffs The price tariffs.
         * @return The builder.
         */
        public Builder setPriceTariffs(Property<PriceTariff>[] priceTariffs) {
            this.priceTariffs.clear();
            for (Property priceTariff : priceTariffs) {
                addPriceTariff(priceTariff);
            }
            return this;
        }

        /**
         * Add a single price tariff.
         *
         * @param priceTariff The price tariff.
         * @return The builder.
         */
        public Builder addPriceTariff(Property<PriceTariff> priceTariff) {
            priceTariffs.add(priceTariff);
            priceTariff.setIdentifier(IDENTIFIER_PRICE_TARIFF);
            addProperty(priceTariff);
            return this;
        }

        public HomeChargerState build() {
            return new HomeChargerState(this);
        }
    }
}