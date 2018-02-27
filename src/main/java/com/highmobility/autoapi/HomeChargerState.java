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

import com.highmobility.autoapi.property.ChargeCurrentProperty;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.HomeCharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.HomeCharger.Charging;
import com.highmobility.autoapi.property.HomeCharger.PlugType;
import com.highmobility.autoapi.property.HomeCharger.PriceTariff;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.Property;

import java.util.Arrays;

/**
 * This message is sent when a Home Charger State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class HomeChargerState extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x01);

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
     * @return Charging state
     */
    public Charging getCharging() {
        return charging;
    }

    /**
     * @return The Authentication mechanism
     */
    public AuthenticationMechanism getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    /**
     * @return The Plug type
     */
    public PlugType getPlugType() {
        return plugType;
    }

    /**
     * @return Charging power in kW formatted in 4-bytes per IEEE 754
     */
    public Float getChargingPower() {
        return chargingPower;
    }

    /**
     * @return Solar charging state
     */
    public Boolean isSolarChargingActive() {
        return solarChargingActive;
    }

    /**
     * @return The location of the home charger
     */
    public CoordinatesProperty getLocation() {
        return location;
    }

    /**
     * @return The charge current
     */
    public ChargeCurrentProperty getChargeCurrent() {
        return chargeCurrent;
    }

    /**
     * @return The hotspot state
     */
    public Boolean isHotspotEnabled() {
        return hotspotEnabled;
    }

    /**
     * @return The hotspot SSID
     */
    public String getHotspotSsid() {
        return hotspotSsid;
    }

    /**
     * @return The hotspot security
     */
    public NetworkSecurity getHotspotSecurity() {
        return hotspotSecurity;
    }

    /**
     * @return The Wi-Fi Hotspot password
     */
    public String getHotspotPassword() {
        return hotspotPassword;
    }

    /**
     * @return All of the price tariffs
     */
    public PriceTariff[] getPriceTariffs() {
        return priceTariffs;
    }

    /**
     * @param pricingType The pricing type
     * @return Price tariff for the given pricing type
     */
    public PriceTariff getPriceTariff(PriceTariff.PricingType pricingType) {
        if (priceTariffs != null) {
            for (PriceTariff tariff : priceTariffs) {
                if (tariff.getPricingType() == pricingType) return tariff;
            }
        }

        return null;
    }

    public HomeChargerState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    charging = Charging.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    authenticationMechanism = AuthenticationMechanism.fromByte(property
                            .getValueByte());
                    break;
                case 0x03:
                    plugType = PlugType.fromByte(property.getValueByte());
                    break;
                case 0x04:
                    chargingPower = Property.getFloat(property.getValueBytes());
                    break;
                case 0x05:
                    solarChargingActive = Property.getBool(property.getValueByte());
                    break;
                case 0x06:
                    location = new CoordinatesProperty(property.getPropertyBytes());
                    break;
                case 0x07:
                    chargeCurrent = new ChargeCurrentProperty(property.getPropertyBytes());
                    break;
                case 0x08:
                    hotspotEnabled = Property.getBool(property.getValueByte());
                    break;
                case 0x09:
                    hotspotSsid = Property.getString(property.getValueBytes());
                    break;
                case 0x0A:
                    hotspotSecurity = NetworkSecurity.fromByte(property.getValueByte());
                    break;
                case 0x0B:
                    hotspotPassword = Property.getString(property.getValueBytes());
                    break;
                case 0x0C:
                    if (getPriceTariffs() == null) priceTariffs = new PriceTariff[1];
                    else priceTariffs = Arrays.copyOf(priceTariffs, priceTariffs.length + 1);

                    priceTariffs[priceTariffs.length - 1] = new PriceTariff(property
                            .getPropertyBytes());
                    break;
            }
        }
    }
}