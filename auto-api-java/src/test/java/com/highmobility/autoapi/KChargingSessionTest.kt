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

class KChargingSessionTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006d01" + 
            "01005201004F00064265726C696e000531303939370014536b616c69747a65722053747261c39f6520363800284869676820456e65726779204c6f7720507269636573204368617267696e672050726f7669646572" +  // Matching public charging point from 'High Energy Low Prices Charging Provider' is located at Skalitzer Straße 60, 10997 Berlin
            "01003D01003A000754616c6c696e6e0005313031333000074861726a752036001f46726565205769666920616e64204368617267696e672050726f7669646572" +  // Matching public charging point from 'Free Wifi and Charging Provider' is located at Harju 6, 10130 Tallinn
            "02000B0100083fd28f5c28f5c28f" +  // Displayed state of charge is 29.0%
            "03000B0100083fbeb851eb851eb8" +  // Displayed start state of charge is 12.0%
            "040024010021506c6561736520636865636b20746865206368617267696e672073746174696f6e" +  // Business error states 'Please check the charging station'
            "0400150100125265706561742074686520706c75672d696e" +  // Business error states 'Repeat the plug-in'
            "05001001000D4575726f70652f4265726c696e" +  // Charging session`s time zone is Europe - Berlin
            "06000B010008000001781bcbb94d" +  // Charging session started on 10. March 2021 at 10:57:57 CET
            "07000B010008000001781bc9fd3e" +  // Charging session ended on 10. March 2021 at 10:58:45 CET
            "08000D01000A070040c11e8000000000" +  // Total time the charging was active was 8765.0s in the session
            "09000D01000A0c04400c89374bc6a7f0" +  // Calculated amount of energy charged was 3.567Kwh during the session
            "0a000D01000A0c044002c28f5c28f5c3" +  // Energy charged in the last session was 2.345Kwh
            "0b000401000101" +  // Preconditioning is active
            "0c000D01000A120440a0040000000000" +  // Odometer is showing 2050.0km
            "0d002001001D0003455552400234eab76265223fe226809d495183400234eab7626522" +  // Charging costs are shown in 'EUR' with calculated costs of 2.2758383109, calculated savings 0.5672 and simulated charging cost of 2.2758383109
            "0e004E01004B00064265726c696e002b536b616c69747a65722053747261c39f652036382c203130393937204265726c696e2c204765726d616e790014536b616c69747a65722053747261c39f65203638" // Charging location was 'Skalitzer Straße 68, 10997 Berlin, Germany'
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as ChargingSession.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = ChargingSession.State.Builder()
        builder.addPublicChargingPoint(Property(ChargingPoint("Berlin", "10997", "Skalitzer Straße 68", "High Energy Low Prices Charging Provider")))
        builder.addPublicChargingPoint(Property(ChargingPoint("Tallinn", "10130", "Harju 6", "Free Wifi and Charging Provider")))
        builder.setDisplayedStateOfCharge(Property(0.29))
        builder.setDisplayedStartStateOfCharge(Property(0.12))
        builder.addBusinessError(Property("Please check the charging station"))
        builder.addBusinessError(Property("Repeat the plug-in"))
        builder.setTimeZone(Property("Europe/Berlin"))
        builder.setStartTime(Property(getCalendar("2021-03-10T11:00:39.373Z")))
        builder.setEndTime(Property(getCalendar("2021-03-10T10:58:45.694Z")))
        builder.setTotalChargingDuration(Property(Duration(8765.0, Duration.Unit.SECONDS)))
        builder.setCalculatedEnergyCharged(Property(Energy(3.567, Energy.Unit.KILOWATT_HOURS)))
        builder.setEnergyCharged(Property(Energy(2.345, Energy.Unit.KILOWATT_HOURS)))
        builder.setPreconditioningState(Property(ActiveState.ACTIVE))
        builder.setOdometer(Property(Length(2050.0, Length.Unit.KILOMETERS)))
        builder.setChargingCost(Property(ChargingCost("EUR", 2.2758383109, 0.5672, 2.2758383109)))
        builder.setLocation(Property(ChargingLocation("Berlin", "Skalitzer Straße 68, 10997 Berlin, Germany", "Skalitzer Straße 68")))
        testState(builder.build())
    }
    
    private fun testState(state: ChargingSession.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.publicChargingPoints[0].value?.city == "Berlin")
        assertTrue(state.publicChargingPoints[0].value?.postalCode == "10997")
        assertTrue(state.publicChargingPoints[0].value?.street == "Skalitzer Straße 68")
        assertTrue(state.publicChargingPoints[0].value?.provider == "High Energy Low Prices Charging Provider")
        assertTrue(state.publicChargingPoints[1].value?.city == "Tallinn")
        assertTrue(state.publicChargingPoints[1].value?.postalCode == "10130")
        assertTrue(state.publicChargingPoints[1].value?.street == "Harju 6")
        assertTrue(state.publicChargingPoints[1].value?.provider == "Free Wifi and Charging Provider")
        assertTrue(state.displayedStateOfCharge.value == 0.29)
        assertTrue(state.displayedStartStateOfCharge.value == 0.12)
        assertTrue(state.businessErrors[0].value == "Please check the charging station")
        assertTrue(state.businessErrors[1].value == "Repeat the plug-in")
        assertTrue(state.timeZone.value == "Europe/Berlin")
        assertTrue(dateIsSame(state.startTime.value, "2021-03-10T11:00:39.373Z"))
        assertTrue(dateIsSame(state.endTime.value, "2021-03-10T10:58:45.694Z"))
        assertTrue(state.totalChargingDuration.value?.value == 8765.0)
        assertTrue(state.totalChargingDuration.value?.unit == Duration.Unit.SECONDS)
        assertTrue(state.calculatedEnergyCharged.value?.value == 3.567)
        assertTrue(state.calculatedEnergyCharged.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.energyCharged.value?.value == 2.345)
        assertTrue(state.energyCharged.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.preconditioningState.value == ActiveState.ACTIVE)
        assertTrue(state.odometer.value?.value == 2050.0)
        assertTrue(state.odometer.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.chargingCost.value?.currency == "EUR")
        assertTrue(state.chargingCost.value?.calculatedChargingCost == 2.2758383109)
        assertTrue(state.chargingCost.value?.calculatedSavings == 0.5672)
        assertTrue(state.chargingCost.value?.simulatedImmediateChargingCost == 2.2758383109)
        assertTrue(state.location.value?.municipality == "Berlin")
        assertTrue(state.location.value?.formattedAddress == "Skalitzer Straße 68, 10997 Berlin, Germany")
        assertTrue(state.location.value?.streetAddress == "Skalitzer Straße 68")
    }
}