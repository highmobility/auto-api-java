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

class KTripsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006a01" + 
            "01000401000100" +  // Trip had a single stop
            "02000E01000B486172692053656c646f6e" +  // Driver name is 'Hari Seldon'
            "030014010011546f20736176652068756d616e6b696e64" +  // Description of the trip is 'To save humankind'
            "04000B01000800000172cc7e5190" +  // Trip started at 19 June 2020 at 12:12:10 GMT
            "05000B01000800000172ccb54010" +  // Trip ended at 19 June 2020 at 13:12:10 GMT
            "06002E01002B536b616c69747a65722053747261c39f652036382c203130393937204265726c696e2c204765726d616e79" +  // Trip started from Skalitzer Straße 68, 10997 Berlin, Germany
            "07002E01002B536b616c69747a65722053747261c39f652036382c203130393937204265726c696e2c204765726d616e79" +  // Trip ended at Skalitzer Straße 68, 10997 Berlin, Germany
            "080013010010404a40090b417ca2402ae122d948dc12" +  // Trip started from 52.500276:13.439719
            "090013010010404a40090b417ca2402ae122d948dc12" +  // Trip ended at 52.500276:13.439719
            "0a000D01000A120440c4820000000000" +  // At the start of the trip the odometer was showing 10500.0km
            "0b000D01000A120440c4978000000000" +  // At the start of the trip the odometer was showing 10543.0km
            "0c000D01000A0f00401d5c28f5c28f5c" +  // Average fuel consumption during the trip was 7.34 l/100km
            "0d000D01000A12044045800000000000" +  // Distance of trip was 43.0km
            "0e001A010017050014536b616c69747a65722053747261c39f65203638" +  // Street component value is 'Skalitzer Straße 68'
            "0e000C0100090000064265726c696e" +  // City component value is 'Berlin'
            "0e000D01000A0100074765726d616e79" +  // Country component value is 'Germany'
            "0f001A010017050014536b616c69747a65722053747261c39f65203638" +  // Street component value is 'Skalitzer Straße 68'
            "0f000C0100090000064265726c696e" +  // City component value is 'Berlin'
            "0f000D01000A0100074765726d616e79" // Country component value is 'Germany'
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Trips.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Trips.State.Builder()
        builder.setType(Property(Trips.Type.SINGLE))
        builder.setDriverName(Property("Hari Seldon"))
        builder.setDescription(Property("To save humankind"))
        builder.setStartTime(Property(getCalendar("2020-06-19T12:12:10.000Z")))
        builder.setEndTime(Property(getCalendar("2020-06-19T13:12:10.000Z")))
        builder.setStartAddress(Property("Skalitzer Straße 68, 10997 Berlin, Germany"))
        builder.setEndAddress(Property("Skalitzer Straße 68, 10997 Berlin, Germany"))
        builder.setStartCoordinates(Property(Coordinates(52.500276, 13.439719)))
        builder.setEndCoordinates(Property(Coordinates(52.500276, 13.439719)))
        builder.setStartOdometer(Property(Length(10500.0, Length.Unit.KILOMETERS)))
        builder.setEndOdometer(Property(Length(10543.0, Length.Unit.KILOMETERS)))
        builder.setAverageFuelConsumption(Property(FuelEfficiency(7.34, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)))
        builder.setDistance(Property(Length(43.0, Length.Unit.KILOMETERS)))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.STREET, "Skalitzer Straße 68")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.CITY, "Berlin")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.COUNTRY, "Germany")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.STREET, "Skalitzer Straße 68")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.CITY, "Berlin")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.COUNTRY, "Germany")))
        testState(builder.build())
    }
    
    private fun testState(state: Trips.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getType().value == Trips.Type.SINGLE)
        assertTrue(state.getDriverName().value == "Hari Seldon")
        assertTrue(state.getDescription().value == "To save humankind")
        assertTrue(dateIsSame(state.getStartTime().value, "2020-06-19T12:12:10.000Z"))
        assertTrue(dateIsSame(state.getEndTime().value, "2020-06-19T13:12:10.000Z"))
        assertTrue(state.getStartAddress().value == "Skalitzer Straße 68, 10997 Berlin, Germany")
        assertTrue(state.getEndAddress().value == "Skalitzer Straße 68, 10997 Berlin, Germany")
        assertTrue(state.getStartCoordinates().value?.latitude == 52.500276)
        assertTrue(state.getStartCoordinates().value?.longitude == 13.439719)
        assertTrue(state.getEndCoordinates().value?.latitude == 52.500276)
        assertTrue(state.getEndCoordinates().value?.longitude == 13.439719)
        assertTrue(state.getStartOdometer().value?.value == 10500.0)
        assertTrue(state.getStartOdometer().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getEndOdometer().value?.value == 10543.0)
        assertTrue(state.getEndOdometer().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getAverageFuelConsumption().value?.value == 7.34)
        assertTrue(state.getAverageFuelConsumption().value?.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(state.getDistance().value?.value == 43.0)
        assertTrue(state.getDistance().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getStartAddressComponents()[0].value?.type == AddressComponent.Type.STREET)
        assertTrue(state.getStartAddressComponents()[0].value?.value == "Skalitzer Straße 68")
        assertTrue(state.getStartAddressComponents()[1].value?.type == AddressComponent.Type.CITY)
        assertTrue(state.getStartAddressComponents()[1].value?.value == "Berlin")
        assertTrue(state.getStartAddressComponents()[2].value?.type == AddressComponent.Type.COUNTRY)
        assertTrue(state.getStartAddressComponents()[2].value?.value == "Germany")
        assertTrue(state.getEndAddressComponents()[0].value?.type == AddressComponent.Type.STREET)
        assertTrue(state.getEndAddressComponents()[0].value?.value == "Skalitzer Straße 68")
        assertTrue(state.getEndAddressComponents()[1].value?.type == AddressComponent.Type.CITY)
        assertTrue(state.getEndAddressComponents()[1].value?.value == "Berlin")
        assertTrue(state.getEndAddressComponents()[2].value?.type == AddressComponent.Type.COUNTRY)
        assertTrue(state.getEndAddressComponents()[2].value?.value == "Germany")
    }
    
}