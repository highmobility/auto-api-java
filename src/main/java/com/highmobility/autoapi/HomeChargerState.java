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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.PriceTariff;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The home charger state
 */
public class HomeChargerState extends SetCommand {
    public static final Identifier identifier = Identifier.HOME_CHARGER;

    Property<ChargingStatus> chargingStatus = new Property(ChargingStatus.class, 0x01);
    Property<AuthenticationMechanism> authenticationMechanism = new Property(AuthenticationMechanism.class, 0x02);
    Property<PlugType> plugType = new Property(PlugType.class, 0x03);
    Property<Float> chargingPowerKW = new Property(Float.class, 0x04);
    Property<ActiveState> solarCharging = new Property(ActiveState.class, 0x05);
    Property<EnabledState> wifiHotspotEnabled = new Property(EnabledState.class, 0x08);
    Property<String> wifiHotspotSSID = new Property(String.class, 0x09);
    Property<NetworkSecurity> wiFiHotspotSecurity = new Property(NetworkSecurity.class, 0x0a);
    Property<String> wiFiHotspotPassword = new Property(String.class, 0x0b);
    Property<AuthenticationState> authenticationState = new Property(AuthenticationState.class, 0x0d);
    Property<Float> chargeCurrentDC = new Property(Float.class, 0x0e);
    Property<Float> maximumChargeCurrent = new Property(Float.class, 0x0f);
    Property<Float> minimumChargeCurrent = new Property(Float.class, 0x10);
    Property<Coordinates> coordinates = new Property(Coordinates.class, 0x11);
    Property<PriceTariff>[] priceTariffs;

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
     * @return Charging power in kW
     */
    public Property<Float> getChargingPowerKW() {
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
     * @return The charge direct current
     */
    public Property<Float> getChargeCurrentDC() {
        return chargeCurrentDC;
    }

    /**
     * @return The maximum possible charge current
     */
    public Property<Float> getMaximumChargeCurrent() {
        return maximumChargeCurrent;
    }

    /**
     * @return The minimal possible charge current
     */
    public Property<Float> getMinimumChargeCurrent() {
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
    public Property<PriceTariff>[] getPriceTariffs() {
        return priceTariffs;
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

    HomeChargerState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> priceTariffsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return chargingStatus.update(p);
                    case 0x02: return authenticationMechanism.update(p);
                    case 0x03: return plugType.update(p);
                    case 0x04: return chargingPowerKW.update(p);
                    case 0x05: return solarCharging.update(p);
                    case 0x08: return wifiHotspotEnabled.update(p);
                    case 0x09: return wifiHotspotSSID.update(p);
                    case 0x0a: return wiFiHotspotSecurity.update(p);
                    case 0x0b: return wiFiHotspotPassword.update(p);
                    case 0x0d: return authenticationState.update(p);
                    case 0x0e: return chargeCurrentDC.update(p);
                    case 0x0f: return maximumChargeCurrent.update(p);
                    case 0x10: return minimumChargeCurrent.update(p);
                    case 0x11: return coordinates.update(p);
                    case 0x12:
                        Property<PriceTariff> priceTariff = new Property(PriceTariff.class, p);
                        priceTariffsBuilder.add(priceTariff);
                        return priceTariff;
                }

                return null;
            });
        }

        priceTariffs = priceTariffsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private HomeChargerState(Builder builder) {
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
        chargeCurrentDC = builder.chargeCurrentDC;
        maximumChargeCurrent = builder.maximumChargeCurrent;
        minimumChargeCurrent = builder.minimumChargeCurrent;
        coordinates = builder.coordinates;
        priceTariffs = builder.priceTariffs.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<ChargingStatus> chargingStatus;
        private Property<AuthenticationMechanism> authenticationMechanism;
        private Property<PlugType> plugType;
        private Property<Float> chargingPowerKW;
        private Property<ActiveState> solarCharging;
        private Property<EnabledState> wifiHotspotEnabled;
        private Property<String> wifiHotspotSSID;
        private Property<NetworkSecurity> wiFiHotspotSecurity;
        private Property<String> wiFiHotspotPassword;
        private Property<AuthenticationState> authenticationState;
        private Property<Float> chargeCurrentDC;
        private Property<Float> maximumChargeCurrent;
        private Property<Float> minimumChargeCurrent;
        private Property<Coordinates> coordinates;
        private List<Property> priceTariffs = new ArrayList<>();

        public Builder() {
            super(identifier);
        }

        public HomeChargerState build() {
            return new HomeChargerState(this);
        }

        /**
         * @param chargingStatus The charging status
         * @return The builder
         */
        public Builder setChargingStatus(Property<ChargingStatus> chargingStatus) {
            this.chargingStatus = chargingStatus.setIdentifier(0x01);
            addProperty(this.chargingStatus);
            return this;
        }
        
        /**
         * @param authenticationMechanism The authentication mechanism
         * @return The builder
         */
        public Builder setAuthenticationMechanism(Property<AuthenticationMechanism> authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism.setIdentifier(0x02);
            addProperty(this.authenticationMechanism);
            return this;
        }
        
        /**
         * @param plugType The plug type
         * @return The builder
         */
        public Builder setPlugType(Property<PlugType> plugType) {
            this.plugType = plugType.setIdentifier(0x03);
            addProperty(this.plugType);
            return this;
        }
        
        /**
         * @param chargingPowerKW Charging power in kW
         * @return The builder
         */
        public Builder setChargingPowerKW(Property<Float> chargingPowerKW) {
            this.chargingPowerKW = chargingPowerKW.setIdentifier(0x04);
            addProperty(this.chargingPowerKW);
            return this;
        }
        
        /**
         * @param solarCharging The solar charging
         * @return The builder
         */
        public Builder setSolarCharging(Property<ActiveState> solarCharging) {
            this.solarCharging = solarCharging.setIdentifier(0x05);
            addProperty(this.solarCharging);
            return this;
        }
        
        /**
         * @param wifiHotspotEnabled The wi fi hotspot enabled
         * @return The builder
         */
        public Builder setWifiHotspotEnabled(Property<EnabledState> wifiHotspotEnabled) {
            this.wifiHotspotEnabled = wifiHotspotEnabled.setIdentifier(0x08);
            addProperty(this.wifiHotspotEnabled);
            return this;
        }
        
        /**
         * @param wifiHotspotSSID The Wi-Fi Hotspot SSID
         * @return The builder
         */
        public Builder setWifiHotspotSSID(Property<String> wifiHotspotSSID) {
            this.wifiHotspotSSID = wifiHotspotSSID.setIdentifier(0x09);
            addProperty(this.wifiHotspotSSID);
            return this;
        }
        
        /**
         * @param wiFiHotspotSecurity The wi fi hotspot security
         * @return The builder
         */
        public Builder setWiFiHotspotSecurity(Property<NetworkSecurity> wiFiHotspotSecurity) {
            this.wiFiHotspotSecurity = wiFiHotspotSecurity.setIdentifier(0x0a);
            addProperty(this.wiFiHotspotSecurity);
            return this;
        }
        
        /**
         * @param wiFiHotspotPassword The Wi-Fi Hotspot password
         * @return The builder
         */
        public Builder setWiFiHotspotPassword(Property<String> wiFiHotspotPassword) {
            this.wiFiHotspotPassword = wiFiHotspotPassword.setIdentifier(0x0b);
            addProperty(this.wiFiHotspotPassword);
            return this;
        }
        
        /**
         * @param authenticationState The authentication state
         * @return The builder
         */
        public Builder setAuthenticationState(Property<AuthenticationState> authenticationState) {
            this.authenticationState = authenticationState.setIdentifier(0x0d);
            addProperty(this.authenticationState);
            return this;
        }
        
        /**
         * @param chargeCurrentDC The charge direct current
         * @return The builder
         */
        public Builder setChargeCurrentDC(Property<Float> chargeCurrentDC) {
            this.chargeCurrentDC = chargeCurrentDC.setIdentifier(0x0e);
            addProperty(this.chargeCurrentDC);
            return this;
        }
        
        /**
         * @param maximumChargeCurrent The maximum possible charge current
         * @return The builder
         */
        public Builder setMaximumChargeCurrent(Property<Float> maximumChargeCurrent) {
            this.maximumChargeCurrent = maximumChargeCurrent.setIdentifier(0x0f);
            addProperty(this.maximumChargeCurrent);
            return this;
        }
        
        /**
         * @param minimumChargeCurrent The minimal possible charge current
         * @return The builder
         */
        public Builder setMinimumChargeCurrent(Property<Float> minimumChargeCurrent) {
            this.minimumChargeCurrent = minimumChargeCurrent.setIdentifier(0x10);
            addProperty(this.minimumChargeCurrent);
            return this;
        }
        
        /**
         * @param coordinates The coordinates
         * @return The builder
         */
        public Builder setCoordinates(Property<Coordinates> coordinates) {
            this.coordinates = coordinates.setIdentifier(0x11);
            addProperty(this.coordinates);
            return this;
        }
        
        /**
         * Add an array of price tariffs.
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
         * Add a single price tariff.
         * 
         * @param priceTariff The price tariff
         * @return The builder
         */
        public Builder addPriceTariff(Property<PriceTariff> priceTariff) {
            priceTariff.setIdentifier(0x12);
            addProperty(priceTariff);
            priceTariffs.add(priceTariff);
            return this;
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        AuthenticationState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}