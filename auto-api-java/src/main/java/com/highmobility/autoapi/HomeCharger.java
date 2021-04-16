/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.autoapi.value.PriceTariff;
import com.highmobility.autoapi.value.measurement.ElectricCurrent;
import com.highmobility.autoapi.value.measurement.Power;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Home Charger capability
 */
public class HomeCharger {
    public static final int IDENTIFIER = Identifier.HOME_CHARGER;

    public static final byte PROPERTY_CHARGING_STATUS = 0x01;
    public static final byte PROPERTY_AUTHENTICATION_MECHANISM = 0x02;
    public static final byte PROPERTY_PLUG_TYPE = 0x03;
    public static final byte PROPERTY_CHARGING_POWER_KW = 0x04;
    public static final byte PROPERTY_SOLAR_CHARGING = 0x05;
    public static final byte PROPERTY_WI_FI_HOTSPOT_ENABLED = 0x08;
    public static final byte PROPERTY_WI_FI_HOTSPOT_SSID = 0x09;
    public static final byte PROPERTY_WI_FI_HOTSPOT_SECURITY = 0x0a;
    public static final byte PROPERTY_WI_FI_HOTSPOT_PASSWORD = 0x0b;
    public static final byte PROPERTY_AUTHENTICATION_STATE = 0x0d;
    public static final byte PROPERTY_CHARGE_CURRENT = 0x0e;
    public static final byte PROPERTY_MAXIMUM_CHARGE_CURRENT = 0x0f;
    public static final byte PROPERTY_MINIMUM_CHARGE_CURRENT = 0x10;
    public static final byte PROPERTY_COORDINATES = 0x11;
    public static final byte PROPERTY_PRICE_TARIFFS = 0x12;
    public static final byte PROPERTY_CHARGING_POWER = 0x13;

    /**
     * Get Home Charger property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Home Charger property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Home Charger property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Home Charger property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Home Charger properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Home Charger properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Home Charger properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Home Charger properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Home Charger properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Set charge current
     */
    public static class SetChargeCurrent extends SetCommand {
        Property<ElectricCurrent> chargeCurrent = new Property<>(ElectricCurrent.class, PROPERTY_CHARGE_CURRENT);
    
        /**
         * @return The charge current
         */
        public Property<ElectricCurrent> getChargeCurrent() {
            return chargeCurrent;
        }
        
        /**
         * Set charge current
         * 
         * @param chargeCurrent The charge current
         */
        public SetChargeCurrent(ElectricCurrent chargeCurrent) {
            super(IDENTIFIER);
        
            addProperty(this.chargeCurrent.update(chargeCurrent));
            createBytes();
        }
    
        SetChargeCurrent(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_CHARGE_CURRENT) return chargeCurrent.update(p);
                    
                    return null;
                });
            }
            if (this.chargeCurrent.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set price tariffs
     */
    public static class SetPriceTariffs extends SetCommand {
        List<Property<PriceTariff>> priceTariffs;
    
        /**
         * @return The price tariffs
         */
        public List<Property<PriceTariff>> getPriceTariffs() {
            return priceTariffs;
        }
        
        /**
         * Set price tariffs
         * 
         * @param priceTariffs The price tariffs
         */
        public SetPriceTariffs(List<PriceTariff> priceTariffs) {
            super(IDENTIFIER);
        
            final ArrayList<Property<PriceTariff>> priceTariffsBuilder = new ArrayList<>();
            if (priceTariffs != null) {
                for (PriceTariff priceTariff : priceTariffs) {
                    Property<PriceTariff> prop = new Property<>(0x12, priceTariff);
                    priceTariffsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.priceTariffs = priceTariffsBuilder;
            createBytes();
        }
    
        SetPriceTariffs(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
        
            final ArrayList<Property<PriceTariff>> priceTariffsBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_PRICE_TARIFFS) {
                        Property<PriceTariff> priceTariff = new Property<>(PriceTariff.class, p);
                        priceTariffsBuilder.add(priceTariff);
                        return priceTariff;
                    }
                    
                    return null;
                });
            }
        
            priceTariffs = priceTariffsBuilder;
            if (this.priceTariffs.size() == 0) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Activate deactivate solar charging
     */
    public static class ActivateDeactivateSolarCharging extends SetCommand {
        Property<ActiveState> solarCharging = new Property<>(ActiveState.class, PROPERTY_SOLAR_CHARGING);
    
        /**
         * @return The solar charging
         */
        public Property<ActiveState> getSolarCharging() {
            return solarCharging;
        }
        
        /**
         * Activate deactivate solar charging
         * 
         * @param solarCharging The solar charging
         */
        public ActivateDeactivateSolarCharging(ActiveState solarCharging) {
            super(IDENTIFIER);
        
            addProperty(this.solarCharging.update(solarCharging));
            createBytes();
        }
    
        ActivateDeactivateSolarCharging(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_SOLAR_CHARGING) return solarCharging.update(p);
                    
                    return null;
                });
            }
            if (this.solarCharging.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Enable disable wi fi hotspot
     */
    public static class EnableDisableWiFiHotspot extends SetCommand {
        Property<EnabledState> wifiHotspotEnabled = new Property<>(EnabledState.class, PROPERTY_WI_FI_HOTSPOT_ENABLED);
    
        /**
         * @return The wi fi hotspot enabled
         */
        public Property<EnabledState> getWifiHotspotEnabled() {
            return wifiHotspotEnabled;
        }
        
        /**
         * Enable disable wi fi hotspot
         * 
         * @param wifiHotspotEnabled The wi fi hotspot enabled
         */
        public EnableDisableWiFiHotspot(EnabledState wifiHotspotEnabled) {
            super(IDENTIFIER);
        
            addProperty(this.wifiHotspotEnabled.update(wifiHotspotEnabled));
            createBytes();
        }
    
        EnableDisableWiFiHotspot(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_WI_FI_HOTSPOT_ENABLED) return wifiHotspotEnabled.update(p);
                    
                    return null;
                });
            }
            if (this.wifiHotspotEnabled.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Authenticate expire
     */
    public static class AuthenticateExpire extends SetCommand {
        Property<AuthenticationState> authenticationState = new Property<>(AuthenticationState.class, PROPERTY_AUTHENTICATION_STATE);
    
        /**
         * @return The authentication state
         */
        public Property<AuthenticationState> getAuthenticationState() {
            return authenticationState;
        }
        
        /**
         * Authenticate expire
         * 
         * @param authenticationState The authentication state
         */
        public AuthenticateExpire(AuthenticationState authenticationState) {
            super(IDENTIFIER);
        
            addProperty(this.authenticationState.update(authenticationState));
            createBytes();
        }
    
        AuthenticateExpire(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_AUTHENTICATION_STATE) return authenticationState.update(p);
                    
                    return null;
                });
            }
            if (this.authenticationState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The home charger state
     */
    public static class State extends SetCommand {
        Property<ChargingStatus> chargingStatus = new Property<>(ChargingStatus.class, PROPERTY_CHARGING_STATUS);
        Property<AuthenticationMechanism> authenticationMechanism = new Property<>(AuthenticationMechanism.class, PROPERTY_AUTHENTICATION_MECHANISM);
        Property<PlugType> plugType = new Property<>(PlugType.class, PROPERTY_PLUG_TYPE);
        Property<Power> chargingPowerKW = new Property<>(Power.class, PROPERTY_CHARGING_POWER_KW);
        Property<ActiveState> solarCharging = new Property<>(ActiveState.class, PROPERTY_SOLAR_CHARGING);
        Property<EnabledState> wifiHotspotEnabled = new Property<>(EnabledState.class, PROPERTY_WI_FI_HOTSPOT_ENABLED);
        Property<String> wifiHotspotSSID = new Property<>(String.class, PROPERTY_WI_FI_HOTSPOT_SSID);
        Property<NetworkSecurity> wiFiHotspotSecurity = new Property<>(NetworkSecurity.class, PROPERTY_WI_FI_HOTSPOT_SECURITY);
        Property<String> wiFiHotspotPassword = new Property<>(String.class, PROPERTY_WI_FI_HOTSPOT_PASSWORD);
        Property<AuthenticationState> authenticationState = new Property<>(AuthenticationState.class, PROPERTY_AUTHENTICATION_STATE);
        Property<ElectricCurrent> chargeCurrent = new Property<>(ElectricCurrent.class, PROPERTY_CHARGE_CURRENT);
        Property<ElectricCurrent> maximumChargeCurrent = new Property<>(ElectricCurrent.class, PROPERTY_MAXIMUM_CHARGE_CURRENT);
        Property<ElectricCurrent> minimumChargeCurrent = new Property<>(ElectricCurrent.class, PROPERTY_MINIMUM_CHARGE_CURRENT);
        Property<Coordinates> coordinates = new Property<>(Coordinates.class, PROPERTY_COORDINATES);
        List<Property<PriceTariff>> priceTariffs;
        Property<Power> chargingPower = new Property<>(Power.class, PROPERTY_CHARGING_POWER);
    
        /**
         * @return The charging status
         */
        public Property<ChargingStatus> getChargingStatus() {
            return chargingStatus;
        }
    
        /**
         * @return The authentication mechanism
         */
        public Property<AuthenticationMechanism> getAuthenticationMechanism() {
            return authenticationMechanism;
        }
    
        /**
         * @return The plug type
         */
        public Property<PlugType> getPlugType() {
            return plugType;
        }
    
        /**
         * @return Charging power
         * @deprecated removed the unit from the name. Replaced by {@link #getChargingPower()}
         */
        @Deprecated
        public Property<Power> getChargingPowerKW() {
            return chargingPowerKW;
        }
    
        /**
         * @return The solar charging
         */
        public Property<ActiveState> getSolarCharging() {
            return solarCharging;
        }
    
        /**
         * @return The wi fi hotspot enabled
         */
        public Property<EnabledState> getWifiHotspotEnabled() {
            return wifiHotspotEnabled;
        }
    
        /**
         * @return The Wi-Fi Hotspot SSID
         */
        public Property<String> getWifiHotspotSSID() {
            return wifiHotspotSSID;
        }
    
        /**
         * @return The wi fi hotspot security
         */
        public Property<NetworkSecurity> getWiFiHotspotSecurity() {
            return wiFiHotspotSecurity;
        }
    
        /**
         * @return The Wi-Fi Hotspot password
         */
        public Property<String> getWiFiHotspotPassword() {
            return wiFiHotspotPassword;
        }
    
        /**
         * @return The authentication state
         */
        public Property<AuthenticationState> getAuthenticationState() {
            return authenticationState;
        }
    
        /**
         * @return The charge current
         */
        public Property<ElectricCurrent> getChargeCurrent() {
            return chargeCurrent;
        }
    
        /**
         * @return The maximum possible charge current
         */
        public Property<ElectricCurrent> getMaximumChargeCurrent() {
            return maximumChargeCurrent;
        }
    
        /**
         * @return The minimal possible charge current
         */
        public Property<ElectricCurrent> getMinimumChargeCurrent() {
            return minimumChargeCurrent;
        }
    
        /**
         * @return The coordinates
         */
        public Property<Coordinates> getCoordinates() {
            return coordinates;
        }
    
        /**
         * @return The price tariffs
         */
        public List<Property<PriceTariff>> getPriceTariffs() {
            return priceTariffs;
        }
    
        /**
         * @return Charging power output from the charger
         */
        public Property<Power> getChargingPower() {
            return chargingPower;
        }
    
        /**
         * @param pricingType The pricing type.
         * @return Price tariff for the given pricing type.
         */
        @Nullable public Property<PriceTariff> getPriceTariff(PriceTariff.PricingType pricingType) {
            if (priceTariffs != null) {
                for (Property<PriceTariff> tariff : priceTariffs) {
                    if (tariff.getValue() != null && tariff.getValue().getPricingType() == pricingType)
                        return tariff;
                }
            }
    
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
    
            final ArrayList<Property<PriceTariff>> priceTariffsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CHARGING_STATUS: return chargingStatus.update(p);
                        case PROPERTY_AUTHENTICATION_MECHANISM: return authenticationMechanism.update(p);
                        case PROPERTY_PLUG_TYPE: return plugType.update(p);
                        case PROPERTY_CHARGING_POWER_KW: return chargingPowerKW.update(p);
                        case PROPERTY_SOLAR_CHARGING: return solarCharging.update(p);
                        case PROPERTY_WI_FI_HOTSPOT_ENABLED: return wifiHotspotEnabled.update(p);
                        case PROPERTY_WI_FI_HOTSPOT_SSID: return wifiHotspotSSID.update(p);
                        case PROPERTY_WI_FI_HOTSPOT_SECURITY: return wiFiHotspotSecurity.update(p);
                        case PROPERTY_WI_FI_HOTSPOT_PASSWORD: return wiFiHotspotPassword.update(p);
                        case PROPERTY_AUTHENTICATION_STATE: return authenticationState.update(p);
                        case PROPERTY_CHARGE_CURRENT: return chargeCurrent.update(p);
                        case PROPERTY_MAXIMUM_CHARGE_CURRENT: return maximumChargeCurrent.update(p);
                        case PROPERTY_MINIMUM_CHARGE_CURRENT: return minimumChargeCurrent.update(p);
                        case PROPERTY_COORDINATES: return coordinates.update(p);
                        case PROPERTY_PRICE_TARIFFS:
                            Property<PriceTariff> priceTariff = new Property<>(PriceTariff.class, p);
                            priceTariffsBuilder.add(priceTariff);
                            return priceTariff;
                        case PROPERTY_CHARGING_POWER: return chargingPower.update(p);
                    }
    
                    return null;
                });
            }
    
            priceTariffs = priceTariffsBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            chargingStatus = builder.chargingStatus;
            authenticationMechanism = builder.authenticationMechanism;
            plugType = builder.plugType;
            chargingPowerKW = builder.chargingPowerKW;
            solarCharging = builder.solarCharging;
            wifiHotspotEnabled = builder.wifiHotspotEnabled;
            wifiHotspotSSID = builder.wifiHotspotSSID;
            wiFiHotspotSecurity = builder.wiFiHotspotSecurity;
            wiFiHotspotPassword = builder.wiFiHotspotPassword;
            authenticationState = builder.authenticationState;
            chargeCurrent = builder.chargeCurrent;
            maximumChargeCurrent = builder.maximumChargeCurrent;
            minimumChargeCurrent = builder.minimumChargeCurrent;
            coordinates = builder.coordinates;
            priceTariffs = builder.priceTariffs;
            chargingPower = builder.chargingPower;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<ChargingStatus> chargingStatus;
            private Property<AuthenticationMechanism> authenticationMechanism;
            private Property<PlugType> plugType;
            private Property<Power> chargingPowerKW;
            private Property<ActiveState> solarCharging;
            private Property<EnabledState> wifiHotspotEnabled;
            private Property<String> wifiHotspotSSID;
            private Property<NetworkSecurity> wiFiHotspotSecurity;
            private Property<String> wiFiHotspotPassword;
            private Property<AuthenticationState> authenticationState;
            private Property<ElectricCurrent> chargeCurrent;
            private Property<ElectricCurrent> maximumChargeCurrent;
            private Property<ElectricCurrent> minimumChargeCurrent;
            private Property<Coordinates> coordinates;
            private final List<Property<PriceTariff>> priceTariffs = new ArrayList<>();
            private Property<Power> chargingPower;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param chargingStatus The charging status
             * @return The builder
             */
            public Builder setChargingStatus(Property<ChargingStatus> chargingStatus) {
                this.chargingStatus = chargingStatus.setIdentifier(PROPERTY_CHARGING_STATUS);
                addProperty(this.chargingStatus);
                return this;
            }
            
            /**
             * @param authenticationMechanism The authentication mechanism
             * @return The builder
             */
            public Builder setAuthenticationMechanism(Property<AuthenticationMechanism> authenticationMechanism) {
                this.authenticationMechanism = authenticationMechanism.setIdentifier(PROPERTY_AUTHENTICATION_MECHANISM);
                addProperty(this.authenticationMechanism);
                return this;
            }
            
            /**
             * @param plugType The plug type
             * @return The builder
             */
            public Builder setPlugType(Property<PlugType> plugType) {
                this.plugType = plugType.setIdentifier(PROPERTY_PLUG_TYPE);
                addProperty(this.plugType);
                return this;
            }
            
            /**
             * @param chargingPowerKW Charging power
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getChargingPower()}
             */
            @Deprecated
            public Builder setChargingPowerKW(Property<Power> chargingPowerKW) {
                this.chargingPowerKW = chargingPowerKW.setIdentifier(PROPERTY_CHARGING_POWER_KW);
                addProperty(this.chargingPowerKW);
                return this;
            }
            
            /**
             * @param solarCharging The solar charging
             * @return The builder
             */
            public Builder setSolarCharging(Property<ActiveState> solarCharging) {
                this.solarCharging = solarCharging.setIdentifier(PROPERTY_SOLAR_CHARGING);
                addProperty(this.solarCharging);
                return this;
            }
            
            /**
             * @param wifiHotspotEnabled The wi fi hotspot enabled
             * @return The builder
             */
            public Builder setWifiHotspotEnabled(Property<EnabledState> wifiHotspotEnabled) {
                this.wifiHotspotEnabled = wifiHotspotEnabled.setIdentifier(PROPERTY_WI_FI_HOTSPOT_ENABLED);
                addProperty(this.wifiHotspotEnabled);
                return this;
            }
            
            /**
             * @param wifiHotspotSSID The Wi-Fi Hotspot SSID
             * @return The builder
             */
            public Builder setWifiHotspotSSID(Property<String> wifiHotspotSSID) {
                this.wifiHotspotSSID = wifiHotspotSSID.setIdentifier(PROPERTY_WI_FI_HOTSPOT_SSID);
                addProperty(this.wifiHotspotSSID);
                return this;
            }
            
            /**
             * @param wiFiHotspotSecurity The wi fi hotspot security
             * @return The builder
             */
            public Builder setWiFiHotspotSecurity(Property<NetworkSecurity> wiFiHotspotSecurity) {
                this.wiFiHotspotSecurity = wiFiHotspotSecurity.setIdentifier(PROPERTY_WI_FI_HOTSPOT_SECURITY);
                addProperty(this.wiFiHotspotSecurity);
                return this;
            }
            
            /**
             * @param wiFiHotspotPassword The Wi-Fi Hotspot password
             * @return The builder
             */
            public Builder setWiFiHotspotPassword(Property<String> wiFiHotspotPassword) {
                this.wiFiHotspotPassword = wiFiHotspotPassword.setIdentifier(PROPERTY_WI_FI_HOTSPOT_PASSWORD);
                addProperty(this.wiFiHotspotPassword);
                return this;
            }
            
            /**
             * @param authenticationState The authentication state
             * @return The builder
             */
            public Builder setAuthenticationState(Property<AuthenticationState> authenticationState) {
                this.authenticationState = authenticationState.setIdentifier(PROPERTY_AUTHENTICATION_STATE);
                addProperty(this.authenticationState);
                return this;
            }
            
            /**
             * @param chargeCurrent The charge current
             * @return The builder
             */
            public Builder setChargeCurrent(Property<ElectricCurrent> chargeCurrent) {
                this.chargeCurrent = chargeCurrent.setIdentifier(PROPERTY_CHARGE_CURRENT);
                addProperty(this.chargeCurrent);
                return this;
            }
            
            /**
             * @param maximumChargeCurrent The maximum possible charge current
             * @return The builder
             */
            public Builder setMaximumChargeCurrent(Property<ElectricCurrent> maximumChargeCurrent) {
                this.maximumChargeCurrent = maximumChargeCurrent.setIdentifier(PROPERTY_MAXIMUM_CHARGE_CURRENT);
                addProperty(this.maximumChargeCurrent);
                return this;
            }
            
            /**
             * @param minimumChargeCurrent The minimal possible charge current
             * @return The builder
             */
            public Builder setMinimumChargeCurrent(Property<ElectricCurrent> minimumChargeCurrent) {
                this.minimumChargeCurrent = minimumChargeCurrent.setIdentifier(PROPERTY_MINIMUM_CHARGE_CURRENT);
                addProperty(this.minimumChargeCurrent);
                return this;
            }
            
            /**
             * @param coordinates The coordinates
             * @return The builder
             */
            public Builder setCoordinates(Property<Coordinates> coordinates) {
                this.coordinates = coordinates.setIdentifier(PROPERTY_COORDINATES);
                addProperty(this.coordinates);
                return this;
            }
            
            /**
             * Add an array of price tariffs
             * 
             * @param priceTariffs The price tariffs
             * @return The builder
             */
            public Builder setPriceTariffs(Property<PriceTariff>[] priceTariffs) {
                this.priceTariffs.clear();
                for (int i = 0; i < priceTariffs.length; i++) {
                    addPriceTariff(priceTariffs[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single price tariff
             * 
             * @param priceTariff The price tariff
             * @return The builder
             */
            public Builder addPriceTariff(Property<PriceTariff> priceTariff) {
                priceTariff.setIdentifier(PROPERTY_PRICE_TARIFFS);
                addProperty(priceTariff);
                priceTariffs.add(priceTariff);
                return this;
            }
            
            /**
             * @param chargingPower Charging power output from the charger
             * @return The builder
             */
            public Builder setChargingPower(Property<Power> chargingPower) {
                this.chargingPower = chargingPower.setIdentifier(PROPERTY_CHARGING_POWER);
                addProperty(this.chargingPower);
                return this;
            }
        }
    }

    public enum ChargingStatus implements ByteEnum {
        DISCONNECTED((byte) 0x00),
        PLUGGED_IN((byte) 0x01),
        CHARGING((byte) 0x02);
    
        public static ChargingStatus fromByte(byte byteValue) throws CommandParseException {
            ChargingStatus[] values = ChargingStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ChargingStatus.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ChargingStatus(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum AuthenticationMechanism implements ByteEnum {
        PIN((byte) 0x00),
        APP((byte) 0x01);
    
        public static AuthenticationMechanism fromByte(byte byteValue) throws CommandParseException {
            AuthenticationMechanism[] values = AuthenticationMechanism.values();
    
            for (int i = 0; i < values.length; i++) {
                AuthenticationMechanism state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(AuthenticationMechanism.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        AuthenticationMechanism(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum PlugType implements ByteEnum {
        TYPE_1((byte) 0x00),
        TYPE_2((byte) 0x01),
        CCS((byte) 0x02),
        CHADEMO((byte) 0x03);
    
        public static PlugType fromByte(byte byteValue) throws CommandParseException {
            PlugType[] values = PlugType.values();
    
            for (int i = 0; i < values.length; i++) {
                PlugType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(PlugType.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        PlugType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum AuthenticationState implements ByteEnum {
        UNAUTHENTICATED((byte) 0x00),
        AUTHENTICATED((byte) 0x01);
    
        public static AuthenticationState fromByte(byte byteValue) throws CommandParseException {
            AuthenticationState[] values = AuthenticationState.values();
    
            for (int i = 0; i < values.length; i++) {
                AuthenticationState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(AuthenticationState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        AuthenticationState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}