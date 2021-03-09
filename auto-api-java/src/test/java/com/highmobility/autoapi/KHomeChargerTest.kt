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
package com.highmobility.autoapi

import com.highmobility.autoapi.property.Property
import com.highmobility.autoapi.value.*
import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

@Suppress("DEPRECATION")
class KHomeChargerTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006001" + 
            "01000401000102" +  // Charging is active
            "02000401000101" +  // Authentication mechanism is an app
            "03000401000101" +  // Plug type is 'Type 2'
            "04000D01000A14024027000000000000" +  // Charging power is 11.5kW
            "05000401000101" +  // Solar charging is active
            "08000401000101" +  // WiFi hotspot is enabled
            "09000F01000C436861726765722037363132" +  // WiFi hotspot SSID is 'Charger 7612'
            "0a000401000103" +  // WiFi hotspot security uses the WPA2-Personal algorithm
            "0b000D01000A5a57337641524e554265" +  // WiFi hotspot password is 'ZW3vARNUBe'
            "0d000401000101" +  // Is authenticated to the charger
            "0e000D01000A09003fe0000000000000" +  // Charger current is 0.5A
            "0f000D01000A09003ff0000000000000" +  // Maximum charger current is 1.0A
            "10000D01000A09003fb999999999999a" +  // Minimum charger current is 0.1A
            "110013010010404a428f9f44d445402acf562174c4ce" +  // Charger is located at 52.520008:13.404954
            "12001101000E0040120000000000000003455552" +  // Charger starting fee tariff is 4.5€
            "12001101000E013fd33333333333330003455552" +  // Charger per minute fee tariff is 0.3€
            "120014010011023fd33333333333330006526970706c65" +  // Charger per kWh tariff is 0.3RPL
            "13000D01000A14024075e00000000000" // Charging power is 350.0kW
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as HomeCharger.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = HomeCharger.State.Builder()
        builder.setChargingStatus(Property(HomeCharger.ChargingStatus.CHARGING))
        builder.setAuthenticationMechanism(Property(HomeCharger.AuthenticationMechanism.APP))
        builder.setPlugType(Property(HomeCharger.PlugType.TYPE_2))
        builder.setChargingPowerKW(Property(Power(11.5, Power.Unit.KILOWATTS)))
        builder.setSolarCharging(Property(ActiveState.ACTIVE))
        builder.setWifiHotspotEnabled(Property(EnabledState.ENABLED))
        builder.setWifiHotspotSSID(Property("Charger 7612"))
        builder.setWiFiHotspotSecurity(Property(NetworkSecurity.WPA2_PERSONAL))
        builder.setWiFiHotspotPassword(Property("ZW3vARNUBe"))
        builder.setAuthenticationState(Property(HomeCharger.AuthenticationState.AUTHENTICATED))
        builder.setChargeCurrent(Property(ElectricCurrent(0.5, ElectricCurrent.Unit.AMPERES)))
        builder.setMaximumChargeCurrent(Property(ElectricCurrent(1.0, ElectricCurrent.Unit.AMPERES)))
        builder.setMinimumChargeCurrent(Property(ElectricCurrent(0.1, ElectricCurrent.Unit.AMPERES)))
        builder.setCoordinates(Property(Coordinates(52.520008, 13.404954)))
        builder.addPriceTariff(Property(PriceTariff(PriceTariff.PricingType.STARTING_FEE, 4.5, "EUR")))
        builder.addPriceTariff(Property(PriceTariff(PriceTariff.PricingType.PER_MINUTE, 0.3, "EUR")))
        builder.addPriceTariff(Property(PriceTariff(PriceTariff.PricingType.PER_KWH, 0.3, "Ripple")))
        builder.setChargingPower(Property(Power(350.0, Power.Unit.KILOWATTS)))
        testState(builder.build())
    }
    
    private fun testState(state: HomeCharger.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.chargingStatus.value == HomeCharger.ChargingStatus.CHARGING)
        assertTrue(state.authenticationMechanism.value == HomeCharger.AuthenticationMechanism.APP)
        assertTrue(state.plugType.value == HomeCharger.PlugType.TYPE_2)
        assertTrue(state.chargingPowerKW.value?.value == 11.5)
        assertTrue(state.chargingPowerKW.value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.solarCharging.value == ActiveState.ACTIVE)
        assertTrue(state.wifiHotspotEnabled.value == EnabledState.ENABLED)
        assertTrue(state.wifiHotspotSSID.value == "Charger 7612")
        assertTrue(state.wiFiHotspotSecurity.value == NetworkSecurity.WPA2_PERSONAL)
        assertTrue(state.wiFiHotspotPassword.value == "ZW3vARNUBe")
        assertTrue(state.authenticationState.value == HomeCharger.AuthenticationState.AUTHENTICATED)
        assertTrue(state.chargeCurrent.value?.value == 0.5)
        assertTrue(state.chargeCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.maximumChargeCurrent.value?.value == 1.0)
        assertTrue(state.maximumChargeCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.minimumChargeCurrent.value?.value == 0.1)
        assertTrue(state.minimumChargeCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.coordinates.value?.latitude == 52.520008)
        assertTrue(state.coordinates.value?.longitude == 13.404954)
        assertTrue(state.priceTariffs[0].value?.pricingType == PriceTariff.PricingType.STARTING_FEE)
        assertTrue(state.priceTariffs[0].value?.price == 4.5)
        assertTrue(state.priceTariffs[0].value?.currency == "EUR")
        assertTrue(state.priceTariffs[1].value?.pricingType == PriceTariff.PricingType.PER_MINUTE)
        assertTrue(state.priceTariffs[1].value?.price == 0.3)
        assertTrue(state.priceTariffs[1].value?.currency == "EUR")
        assertTrue(state.priceTariffs[2].value?.pricingType == PriceTariff.PricingType.PER_KWH)
        assertTrue(state.priceTariffs[2].value?.price == 0.3)
        assertTrue(state.priceTariffs[2].value?.currency == "Ripple")
        assertTrue(state.chargingPower.value?.value == 350.0)
        assertTrue(state.chargingPower.value?.unit == Power.Unit.KILOWATTS)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006000")
        val defaultGetter = HomeCharger.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "006000010203040508090a0b0d0e0f10111213")
        val propertyGetter = HomeCharger.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x08, 0x09, 0x0a, 0x0b, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203040508090a0b0d0e0f10111213"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006002")
        val created = HomeCharger.GetStateAvailability()
        assertTrue(created.identifier == Identifier.HOME_CHARGER)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HomeCharger.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.HOME_CHARGER)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("010203040508090a0b0d0e0f10111213")
        val allBytes = Bytes(COMMAND_HEADER + "006002" + identifierBytes)
        val constructed = HomeCharger.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.HOME_CHARGER)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = HomeCharger.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x08, 0x09, 0x0a, 0x0b, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as HomeCharger.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.HOME_CHARGER)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun setChargeCurrent() {
        val bytes = Bytes(COMMAND_HEADER + "006001" +
            "0e000D01000A09003fe0000000000000")
    
        val constructed = HomeCharger.SetChargeCurrent(ElectricCurrent(0.5, ElectricCurrent.Unit.AMPERES))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HomeCharger.SetChargeCurrent
        assertTrue(resolved.chargeCurrent.value?.value == 0.5)
        assertTrue(resolved.chargeCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setPriceTariffs() {
        val bytes = Bytes(COMMAND_HEADER + "006001" +
            "12001101000E0040120000000000000003455552" +
            "12001101000E013fd33333333333330003455552" +
            "120014010011023fd33333333333330006526970706c65")
    
        val constructed = HomeCharger.SetPriceTariffs(arrayListOf(
                PriceTariff(PriceTariff.PricingType.STARTING_FEE, 4.5, "EUR"), 
                PriceTariff(PriceTariff.PricingType.PER_MINUTE, 0.3, "EUR"), 
                PriceTariff(PriceTariff.PricingType.PER_KWH, 0.3, "Ripple"))
            )
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HomeCharger.SetPriceTariffs
        assertTrue(resolved.priceTariffs[0].value?.pricingType == PriceTariff.PricingType.STARTING_FEE)
        assertTrue(resolved.priceTariffs[0].value?.price == 4.5)
        assertTrue(resolved.priceTariffs[0].value?.currency == "EUR")
        assertTrue(resolved.priceTariffs[1].value?.pricingType == PriceTariff.PricingType.PER_MINUTE)
        assertTrue(resolved.priceTariffs[1].value?.price == 0.3)
        assertTrue(resolved.priceTariffs[1].value?.currency == "EUR")
        assertTrue(resolved.priceTariffs[2].value?.pricingType == PriceTariff.PricingType.PER_KWH)
        assertTrue(resolved.priceTariffs[2].value?.price == 0.3)
        assertTrue(resolved.priceTariffs[2].value?.currency == "Ripple")
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun activateDeactivateSolarCharging() {
        val bytes = Bytes(COMMAND_HEADER + "006001" +
            "05000401000101")
    
        val constructed = HomeCharger.ActivateDeactivateSolarCharging(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HomeCharger.ActivateDeactivateSolarCharging
        assertTrue(resolved.solarCharging.value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun enableDisableWiFiHotspot() {
        val bytes = Bytes(COMMAND_HEADER + "006001" +
            "08000401000101")
    
        val constructed = HomeCharger.EnableDisableWiFiHotspot(EnabledState.ENABLED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HomeCharger.EnableDisableWiFiHotspot
        assertTrue(resolved.wifiHotspotEnabled.value == EnabledState.ENABLED)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun authenticateExpire() {
        val bytes = Bytes(COMMAND_HEADER + "006001" +
            "0d000401000101")
    
        val constructed = HomeCharger.AuthenticateExpire(HomeCharger.AuthenticationState.AUTHENTICATED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HomeCharger.AuthenticateExpire
        assertTrue(resolved.authenticationState.value == HomeCharger.AuthenticationState.AUTHENTICATED)
        assertTrue(resolved == bytes)
    }
}