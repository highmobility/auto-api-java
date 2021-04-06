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
            "0e000C0100090000064265726c696e" +  // City component value is 'Berlin'
            "0e000D01000A0100074765726d616e79" +  // Country component value is 'Germany'
            "0e00080100050200024445" +  // Country short component value is 'DE'
            "0e000C0100090300064265726c696e" +  // District component value is 'Berlin'
            "0e000B0100080400053130313137" +  // Postal code component value is '10117'
            "0e001A010017050014536b616c69747a65722053747261c39f65203638" +  // Street component value is 'Skalitzer Straße 68'
            "0e001101000E06000b4272616e64656e62757267" +  // Country component value is 'Brandenburg'
            "0e000D01000A0700074765726d616e79" +  // Other component value is 'Germany'
            "0f000C0100090000064265726c696e" +  // City component value is 'Berlin'
            "0f000D01000A0100074765726d616e79" +  // Country component value is 'Germany'
            "0f00080100050200024445" +  // Country short component value is 'DE'
            "0f000C0100090300064265726c696e" +  // District component value is 'Berlin'
            "0f000B0100080400053130313137" +  // Postal code component value is '10117'
            "0f001A010017050014536b616c69747a65722053747261c39f65203638" +  // Street component value is 'Skalitzer Straße 68'
            "0f001101000E06000b4272616e64656e62757267" +  // Country component value is 'Brandenburg'
            "0f000D01000A0700074765726d616e79" +  // Other component value is 'Germany'
            "10000401000101" +  // Driving event of harsh acceleration encountered
            "11000401000100" +  // Eco driving level is high
            "12000C010009000000000000000000" +  // Eco driving zero-threshold is set at 0.0
            "13000D01000A19024037666666666666" +  // Total fuel consumption during the trip was 23.4 l
            "14000D01000A19024004000000000000" +  // Since the last ignition the vehicle has consumed 2.5 l while ideling.
            "15000D01000A16014050d33333333333" // Maximum speed since last ignition on is 67.3km/h
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
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.CITY, "Berlin")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.COUNTRY, "Germany")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.COUNTRY_SHORT, "DE")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.DISTRICT, "Berlin")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.POSTAL_CODE, "10117")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.STREET, "Skalitzer Straße 68")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.STATE_PROVINCE, "Brandenburg")))
        builder.addStartAddressComponent(Property(AddressComponent(AddressComponent.Type.OTHER, "Germany")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.CITY, "Berlin")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.COUNTRY, "Germany")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.COUNTRY_SHORT, "DE")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.DISTRICT, "Berlin")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.POSTAL_CODE, "10117")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.STREET, "Skalitzer Straße 68")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.STATE_PROVINCE, "Brandenburg")))
        builder.addEndAddressComponent(Property(AddressComponent(AddressComponent.Type.OTHER, "Germany")))
        builder.setEvent(Property(Trips.Event.HARSH_ACCELERATION))
        builder.setEcoLevel(Property(Trips.EcoLevel.HIGH))
        builder.addThreshold(Property(EcoDrivingThreshold(EcoDrivingThreshold.Type.ZERO, 0.0)))
        builder.setTotalFuelConsumption(Property(Volume(23.4, Volume.Unit.LITERS)))
        builder.setTotalIdleFuelConsumption(Property(Volume(2.5, Volume.Unit.LITERS)))
        builder.setMaximumSpeed(Property(Speed(67.3, Speed.Unit.KILOMETERS_PER_HOUR)))
        testState(builder.build())
    }
    
    private fun testState(state: Trips.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.type.value == Trips.Type.SINGLE)
        assertTrue(state.driverName.value == "Hari Seldon")
        assertTrue(state.description.value == "To save humankind")
        assertTrue(dateIsSame(state.startTime.value, "2020-06-19T12:12:10.000Z"))
        assertTrue(dateIsSame(state.endTime.value, "2020-06-19T13:12:10.000Z"))
        assertTrue(state.startAddress.value == "Skalitzer Straße 68, 10997 Berlin, Germany")
        assertTrue(state.endAddress.value == "Skalitzer Straße 68, 10997 Berlin, Germany")
        assertTrue(state.startCoordinates.value?.latitude == 52.500276)
        assertTrue(state.startCoordinates.value?.longitude == 13.439719)
        assertTrue(state.endCoordinates.value?.latitude == 52.500276)
        assertTrue(state.endCoordinates.value?.longitude == 13.439719)
        assertTrue(state.startOdometer.value?.value == 10500.0)
        assertTrue(state.startOdometer.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.endOdometer.value?.value == 10543.0)
        assertTrue(state.endOdometer.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.averageFuelConsumption.value?.value == 7.34)
        assertTrue(state.averageFuelConsumption.value?.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(state.distance.value?.value == 43.0)
        assertTrue(state.distance.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.startAddressComponents[0].value?.type == AddressComponent.Type.CITY)
        assertTrue(state.startAddressComponents[0].value?.value == "Berlin")
        assertTrue(state.startAddressComponents[1].value?.type == AddressComponent.Type.COUNTRY)
        assertTrue(state.startAddressComponents[1].value?.value == "Germany")
        assertTrue(state.startAddressComponents[2].value?.type == AddressComponent.Type.COUNTRY_SHORT)
        assertTrue(state.startAddressComponents[2].value?.value == "DE")
        assertTrue(state.startAddressComponents[3].value?.type == AddressComponent.Type.DISTRICT)
        assertTrue(state.startAddressComponents[3].value?.value == "Berlin")
        assertTrue(state.startAddressComponents[4].value?.type == AddressComponent.Type.POSTAL_CODE)
        assertTrue(state.startAddressComponents[4].value?.value == "10117")
        assertTrue(state.startAddressComponents[5].value?.type == AddressComponent.Type.STREET)
        assertTrue(state.startAddressComponents[5].value?.value == "Skalitzer Straße 68")
        assertTrue(state.startAddressComponents[6].value?.type == AddressComponent.Type.STATE_PROVINCE)
        assertTrue(state.startAddressComponents[6].value?.value == "Brandenburg")
        assertTrue(state.startAddressComponents[7].value?.type == AddressComponent.Type.OTHER)
        assertTrue(state.startAddressComponents[7].value?.value == "Germany")
        assertTrue(state.endAddressComponents[0].value?.type == AddressComponent.Type.CITY)
        assertTrue(state.endAddressComponents[0].value?.value == "Berlin")
        assertTrue(state.endAddressComponents[1].value?.type == AddressComponent.Type.COUNTRY)
        assertTrue(state.endAddressComponents[1].value?.value == "Germany")
        assertTrue(state.endAddressComponents[2].value?.type == AddressComponent.Type.COUNTRY_SHORT)
        assertTrue(state.endAddressComponents[2].value?.value == "DE")
        assertTrue(state.endAddressComponents[3].value?.type == AddressComponent.Type.DISTRICT)
        assertTrue(state.endAddressComponents[3].value?.value == "Berlin")
        assertTrue(state.endAddressComponents[4].value?.type == AddressComponent.Type.POSTAL_CODE)
        assertTrue(state.endAddressComponents[4].value?.value == "10117")
        assertTrue(state.endAddressComponents[5].value?.type == AddressComponent.Type.STREET)
        assertTrue(state.endAddressComponents[5].value?.value == "Skalitzer Straße 68")
        assertTrue(state.endAddressComponents[6].value?.type == AddressComponent.Type.STATE_PROVINCE)
        assertTrue(state.endAddressComponents[6].value?.value == "Brandenburg")
        assertTrue(state.endAddressComponents[7].value?.type == AddressComponent.Type.OTHER)
        assertTrue(state.endAddressComponents[7].value?.value == "Germany")
        assertTrue(state.event.value == Trips.Event.HARSH_ACCELERATION)
        assertTrue(state.ecoLevel.value == Trips.EcoLevel.HIGH)
        assertTrue(state.thresholds[0].value?.type == EcoDrivingThreshold.Type.ZERO)
        assertTrue(state.thresholds[0].value?.value == 0.0)
        assertTrue(state.totalFuelConsumption.value?.value == 23.4)
        assertTrue(state.totalFuelConsumption.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.totalIdleFuelConsumption.value?.value == 2.5)
        assertTrue(state.totalIdleFuelConsumption.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.maximumSpeed.value?.value == 67.3)
        assertTrue(state.maximumSpeed.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
    }
}