// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.ActiveState;
import com.highmobility.autoapi.v2.value.EnabledState;
import com.highmobility.autoapi.v2.value.NetworkSecurity;
import com.highmobility.autoapi.v2.value.Coordinates;
import com.highmobility.autoapi.v2.value.PriceTariff;
import java.util.ArrayList;
import java.util.List;

public class HomeChargerState extends Command {
    Property<ChargingStatus> chargingStatus = new Property(ChargingStatus.class, 0x01);
    Property<AuthenticationMechanism> authenticationMechanism = new Property(AuthenticationMechanism.class, 0x02);
    Property<PlugType> plugType = new Property(PlugType.class, 0x03);
    Property<Float> chargingPowerKW = new Property(Float.class, 0x04);
    Property<ActiveState> solarCharging = new Property(ActiveState.class, 0x05);
    Property<EnabledState> hotspotEnabled = new Property(EnabledState.class, 0x08);
    Property<String> hotspotSSID = new Property(String.class, 0x09);
    Property<NetworkSecurity> wiFiHotspotSecurity = new Property(NetworkSecurity.class, 0x0a);
    Property<String> wiFiHotspotPassword = new Property(String.class, 0x0b);
    Property<AuthenticationState> authenticationState = new Property(AuthenticationState.class, 0x0d);
    Property<Float> chargeCurrentDC = new Property(Float.class, 0x0e);
    Property<Float> maximumChargeCurrent = new Property(Float.class, 0x0f);
    Property<Float> minimumChargeCurrent = new Property(Float.class, 0x10);
    Property<Coordinates> coordinates = new Property(Coordinates.class, 0x11);
    Property<PriceTariff> priceTariffs[];

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
     * @return Charging power in kW formatted in 4-bytes per IEEE 754
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
     * @return The hotspot enabled
     */
    public Property<EnabledState> getHotspotEnabled() {
        return hotspotEnabled;
    }

    /**
     * @return The Wi-Fi Hotspot SSID formatted in UTF-8
     */
    public Property<String> getHotspotSSID() {
        return hotspotSSID;
    }

    /**
     * @return The wi fi hotspot security
     */
    public Property<NetworkSecurity> getWiFiHotspotSecurity() {
        return wiFiHotspotSecurity;
    }

    /**
     * @return The Wi-Fi Hotspot password formatted in UTF-8
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
     * @return The charge current (DC) per IEEE 754
     */
    public Property<Float> getChargeCurrentDC() {
        return chargeCurrentDC;
    }

    /**
     * @return The maximum possible charge current per IEEE 754
     */
    public Property<Float> getMaximumChargeCurrent() {
        return maximumChargeCurrent;
    }

    /**
     * @return The minimal possible charge current per IEEE 754
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

    HomeChargerState(byte[] bytes) {
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
                    case 0x08: return hotspotEnabled.update(p);
                    case 0x09: return hotspotSSID.update(p);
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
        hotspotEnabled = builder.hotspotEnabled;
        hotspotSSID = builder.hotspotSSID;
        wiFiHotspotSecurity = builder.wiFiHotspotSecurity;
        wiFiHotspotPassword = builder.wiFiHotspotPassword;
        authenticationState = builder.authenticationState;
        chargeCurrentDC = builder.chargeCurrentDC;
        maximumChargeCurrent = builder.maximumChargeCurrent;
        minimumChargeCurrent = builder.minimumChargeCurrent;
        coordinates = builder.coordinates;
        priceTariffs = builder.priceTariffs.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        private Property<ChargingStatus> chargingStatus;
        private Property<AuthenticationMechanism> authenticationMechanism;
        private Property<PlugType> plugType;
        private Property<Float> chargingPowerKW;
        private Property<ActiveState> solarCharging;
        private Property<EnabledState> hotspotEnabled;
        private Property<String> hotspotSSID;
        private Property<NetworkSecurity> wiFiHotspotSecurity;
        private Property<String> wiFiHotspotPassword;
        private Property<AuthenticationState> authenticationState;
        private Property<Float> chargeCurrentDC;
        private Property<Float> maximumChargeCurrent;
        private Property<Float> minimumChargeCurrent;
        private Property<Coordinates> coordinates;
        private List<Property> priceTariffs = new ArrayList<>();

        public Builder() {
            super(Identifier.HOME_CHARGER);
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
            addProperty(chargingStatus);
            return this;
        }
        
        /**
         * @param authenticationMechanism The authentication mechanism
         * @return The builder
         */
        public Builder setAuthenticationMechanism(Property<AuthenticationMechanism> authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism.setIdentifier(0x02);
            addProperty(authenticationMechanism);
            return this;
        }
        
        /**
         * @param plugType The plug type
         * @return The builder
         */
        public Builder setPlugType(Property<PlugType> plugType) {
            this.plugType = plugType.setIdentifier(0x03);
            addProperty(plugType);
            return this;
        }
        
        /**
         * @param chargingPowerKW Charging power in kW formatted in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setChargingPowerKW(Property<Float> chargingPowerKW) {
            this.chargingPowerKW = chargingPowerKW.setIdentifier(0x04);
            addProperty(chargingPowerKW);
            return this;
        }
        
        /**
         * @param solarCharging The solar charging
         * @return The builder
         */
        public Builder setSolarCharging(Property<ActiveState> solarCharging) {
            this.solarCharging = solarCharging.setIdentifier(0x05);
            addProperty(solarCharging);
            return this;
        }
        
        /**
         * @param hotspotEnabled The hotspot enabled
         * @return The builder
         */
        public Builder setHotspotEnabled(Property<EnabledState> hotspotEnabled) {
            this.hotspotEnabled = hotspotEnabled.setIdentifier(0x08);
            addProperty(hotspotEnabled);
            return this;
        }
        
        /**
         * @param hotspotSSID The Wi-Fi Hotspot SSID formatted in UTF-8
         * @return The builder
         */
        public Builder setHotspotSSID(Property<String> hotspotSSID) {
            this.hotspotSSID = hotspotSSID.setIdentifier(0x09);
            addProperty(hotspotSSID);
            return this;
        }
        
        /**
         * @param wiFiHotspotSecurity The wi fi hotspot security
         * @return The builder
         */
        public Builder setWiFiHotspotSecurity(Property<NetworkSecurity> wiFiHotspotSecurity) {
            this.wiFiHotspotSecurity = wiFiHotspotSecurity.setIdentifier(0x0a);
            addProperty(wiFiHotspotSecurity);
            return this;
        }
        
        /**
         * @param wiFiHotspotPassword The Wi-Fi Hotspot password formatted in UTF-8
         * @return The builder
         */
        public Builder setWiFiHotspotPassword(Property<String> wiFiHotspotPassword) {
            this.wiFiHotspotPassword = wiFiHotspotPassword.setIdentifier(0x0b);
            addProperty(wiFiHotspotPassword);
            return this;
        }
        
        /**
         * @param authenticationState The authentication state
         * @return The builder
         */
        public Builder setAuthenticationState(Property<AuthenticationState> authenticationState) {
            this.authenticationState = authenticationState.setIdentifier(0x0d);
            addProperty(authenticationState);
            return this;
        }
        
        /**
         * @param chargeCurrentDC The charge current (DC) per IEEE 754
         * @return The builder
         */
        public Builder setChargeCurrentDC(Property<Float> chargeCurrentDC) {
            this.chargeCurrentDC = chargeCurrentDC.setIdentifier(0x0e);
            addProperty(chargeCurrentDC);
            return this;
        }
        
        /**
         * @param maximumChargeCurrent The maximum possible charge current per IEEE 754
         * @return The builder
         */
        public Builder setMaximumChargeCurrent(Property<Float> maximumChargeCurrent) {
            this.maximumChargeCurrent = maximumChargeCurrent.setIdentifier(0x0f);
            addProperty(maximumChargeCurrent);
            return this;
        }
        
        /**
         * @param minimumChargeCurrent The minimal possible charge current per IEEE 754
         * @return The builder
         */
        public Builder setMinimumChargeCurrent(Property<Float> minimumChargeCurrent) {
            this.minimumChargeCurrent = minimumChargeCurrent.setIdentifier(0x10);
            addProperty(minimumChargeCurrent);
            return this;
        }
        
        /**
         * @param coordinates The coordinates
         * @return The builder
         */
        public Builder setCoordinates(Property<Coordinates> coordinates) {
            this.coordinates = coordinates.setIdentifier(0x11);
            addProperty(coordinates);
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

    public enum ChargingStatus {
        DISCONNECTED((byte)0x00),
        PLUGGED_IN((byte)0x01),
        CHARGING((byte)0x02);
    
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
    
        public byte getByte() {
            return value;
        }
    }

    public enum AuthenticationMechanism {
        PIN((byte)0x00),
        APP((byte)0x01);
    
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
    
        public byte getByte() {
            return value;
        }
    }

    public enum PlugType {
        TYPE_1((byte)0x00),
        TYPE_2((byte)0x01),
        CCS((byte)0x02),
        CHADEMO((byte)0x03);
    
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
    
        public byte getByte() {
            return value;
        }
    }

    public enum AuthenticationState {
        UNAUTHENTICATED((byte)0x00),
        AUTHENTICATED((byte)0x01);
    
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
    
        public byte getByte() {
            return value;
        }
    }
}