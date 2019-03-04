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
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyString;
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

    ObjectProperty<Charging> charging = new ObjectProperty<>(Charging.class, IDENTIFIER_CHARGING);
    ObjectProperty<AuthenticationMechanism> authenticationMechanism =
            new ObjectProperty<>(AuthenticationMechanism.class,
                    IDENTIFIER_AUTHENTICATION_MECHANISM);
    ObjectProperty<PlugType> plugType = new ObjectProperty<>(PlugType.class, IDENTIFIER_PLUG_TYPE);
    ObjectProperty<Float> chargingPower = new ObjectProperty<>(Float.class,
            IDENTIFIER_CHARGING_POWER);
    ObjectProperty<Boolean> solarChargingActive = new ObjectProperty<>(Boolean.class,
            IDENTIFIER_SOLAR_CHARGING_ACTIVE);
    ObjectProperty<Boolean> hotspotEnabled = new ObjectProperty<>(Boolean.class,
            IDENTIFIER_HOTSPOT_ENABLED);
    ObjectPropertyString hotspotSsid = new ObjectPropertyString(IDENTIFIER_HOTSPOT_SSID);
    ObjectProperty<NetworkSecurity> hotspotSecurity = new ObjectProperty<>(NetworkSecurity.class,
            IDENTIFIER_HOTSPOT_SECURITY);
    ObjectPropertyString hotspotPassword = new ObjectPropertyString(IDENTIFIER_HOTSPOT_PASSWORD);

    // level8
    ObjectProperty<Boolean> authenticated = new ObjectProperty<>(Boolean.class,
            IDENTIFIER_AUTHENTICATED);
    ObjectProperty<Float> chargeCurrentDC = new ObjectProperty<>(Float.class,
            IDENTIFIER_CHARGE_CURRENT_DC);
    ObjectProperty<Float> maximumChargeCurrent =
            new ObjectProperty<>(Float.class, IDENTIFIER_MAXIMUM_CHARGE_CURRENT);
    ObjectProperty<Float> minimumChargeCurrent =
            new ObjectProperty<>(Float.class, IDENTIFIER_MINIMUM_CHARGE_CURRENT);
    ObjectProperty<Coordinates> coordinates = new ObjectProperty<>(Coordinates.class,
            IDENTIFIER_COORDINATES);

    ObjectProperty<PriceTariff>[] priceTariffs;

    /**
     * @return The charging state.
     */
    @Nullable public ObjectProperty<Charging> getCharging() {
        return charging;
    }

    /**
     * @return The authentication mechanism.
     */
    @Nullable public ObjectProperty<AuthenticationMechanism> getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    /**
     * @return The plug type.
     */
    @Nullable public ObjectProperty<PlugType> getPlugType() {
        return plugType;
    }

    /**
     * @return Charging power in kW.
     */
    @Nullable public ObjectProperty<Float> getChargingPower() {
        return chargingPower;
    }

    /**
     * @return Solar charging state.
     */
    @Nullable public ObjectProperty<Boolean> isSolarChargingActive() {
        return solarChargingActive;
    }

    /**
     * @return The hotspot state.
     */
    @Nullable public ObjectProperty<Boolean> isHotspotEnabled() {
        return hotspotEnabled;
    }

    /**
     * @return The hotspot SSID.
     */
    @Nullable public ObjectPropertyString getHotspotSsid() {
        return hotspotSsid;
    }

    /**
     * @return The hotspot security.
     */
    @Nullable public ObjectProperty<NetworkSecurity> getHotspotSecurity() {
        return hotspotSecurity;
    }

    /**
     * @return The hotspot password.
     */
    @Nullable public ObjectPropertyString getHotspotPassword() {
        return hotspotPassword;
    }

    /**
     * @return The authentication state.
     */
    @Nullable public ObjectProperty<Boolean> isAuthenticated() {
        return authenticated;
    }

    /**
     * @return The charge current (DC).
     */
    @Nullable public ObjectProperty<Float> getChargeCurrent() {
        return chargeCurrentDC;
    }

    /**
     * @return The maximum possible charge current.
     */
    @Nullable public ObjectProperty<Float> getMaximumChargeCurrent() {
        return maximumChargeCurrent;
    }

    /**
     * @return The minimal possible charge current.
     */
    @Nullable public ObjectProperty<Float> getMinimumChargeCurrent() {
        return minimumChargeCurrent;
    }

    /**
     * @return The coordinates.
     */
    @Nullable public ObjectProperty<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return All of the price tariffs.
     */
    public ObjectProperty<PriceTariff>[] getPriceTariffs() {
        return priceTariffs;
    }

    /**
     * @param pricingType The pricing type.
     * @return Price tariff for the given pricing type.
     */
    @Nullable
    public ObjectProperty<PriceTariff> getPriceTariff(PriceTariff.PricingType pricingType) {
        if (priceTariffs != null) {
            for (ObjectProperty<PriceTariff> tariff : priceTariffs) {
                if (tariff.getValue() != null && tariff.getValue().getPricingType() == pricingType)
                    return tariff;
            }
        }

        return null;
    }

    public HomeChargerState(byte[] bytes) {
        super(bytes);
        ArrayList<ObjectProperty<PriceTariff>> tariffs = new ArrayList<>();

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
                        ObjectProperty<PriceTariff> t = new ObjectProperty<>(PriceTariff.class, p);
                        tariffs.add(t);
                        return t;
                }
                return null;
            });
        }

        priceTariffs = tariffs.toArray(new ObjectProperty[0]);
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
        priceTariffs = builder.priceTariffs.toArray(new ObjectProperty[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ObjectProperty<Charging> charging;
        private ObjectProperty<AuthenticationMechanism> authenticationMechanism;
        private ObjectProperty<PlugType> plugType;
        private ObjectProperty<Float> chargingPower;
        private ObjectProperty<Boolean> solarChargingActive;
        private ObjectProperty<Boolean> hotspotEnabled;
        private ObjectPropertyString hotspotSsid;
        private ObjectProperty<NetworkSecurity> hotspotSecurity;
        private ObjectPropertyString hotspotPassword;
        private List<ObjectProperty<PriceTariff>> priceTariffs = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param charging The charging state
         * @return The builder.
         */
        public Builder setCharging(ObjectProperty<Charging> charging) {
            this.charging = charging;
            addProperty(charging.setIdentifier(IDENTIFIER_CHARGING));
            return this;
        }

        /**
         * @param authenticationMechanism The authentication mechanism.
         * @return The builder.
         */
        public Builder setAuthenticationMechanism(ObjectProperty<AuthenticationMechanism> authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism;
            addProperty(authenticationMechanism.setIdentifier(IDENTIFIER_AUTHENTICATION_MECHANISM));
            return this;
        }

        /**
         * @param plugType The plug type.
         * @return The builder.
         */
        public Builder setPlugType(ObjectProperty<PlugType> plugType) {
            this.plugType = plugType;
            addProperty(plugType.setIdentifier(IDENTIFIER_PLUG_TYPE));
            return this;
        }

        /**
         * @param chargingPower The charging power in kW.
         * @return The builder.
         */
        public Builder setChargingPower(ObjectProperty<Float> chargingPower) {
            this.chargingPower = chargingPower;
            chargingPower.setIdentifier(IDENTIFIER_CHARGING_POWER);
            addProperty(chargingPower);
            return this;
        }

        /**
         * @param solarChargingActive The solar charging state.
         * @return The builder.
         */
        public Builder setSolarChargingActive(ObjectProperty<Boolean> solarChargingActive) {
            this.solarChargingActive = solarChargingActive;
            solarChargingActive.setIdentifier(IDENTIFIER_SOLAR_CHARGING_ACTIVE);
            addProperty(solarChargingActive);
            return this;
        }

        /**
         * @param hotspotEnabled The Wi-Fi hotspot state.
         * @return The builder.
         */
        public Builder setHotspotEnabled(ObjectProperty<Boolean> hotspotEnabled) {
            this.hotspotEnabled = hotspotEnabled;
            hotspotEnabled.setIdentifier(IDENTIFIER_HOTSPOT_ENABLED);
            addProperty(hotspotEnabled);
            return this;
        }

        /**
         * @param hotspotSsid The hotspot SSID.
         * @return The builder.
         */
        public Builder setHotspotSsid(ObjectPropertyString hotspotSsid) {
            this.hotspotSsid = hotspotSsid;
            addProperty(hotspotSsid.setIdentifier(IDENTIFIER_HOTSPOT_SSID));
            return this;
        }

        /**
         * @param hotspotSecurity The hotspot security.
         * @return The builder.
         */
        public Builder setHotspotSecurity(ObjectProperty<NetworkSecurity> hotspotSecurity) {
            this.hotspotSecurity = hotspotSecurity;
            addProperty(hotspotSecurity.setIdentifier(IDENTIFIER_HOTSPOT_SECURITY));
            return this;
        }

        /**
         * @param hotspotPassword The hotspot password.
         * @return The builder.
         */
        public Builder setHotspotPassword(ObjectPropertyString hotspotPassword) {
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
        public Builder setPriceTariffs(ObjectProperty<PriceTariff>[] priceTariffs) {
            this.priceTariffs.clear();
            for (ObjectProperty priceTariff : priceTariffs) {
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
        public Builder addPriceTariff(ObjectProperty<PriceTariff> priceTariff) {
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