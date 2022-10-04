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

class KVehicleLocationTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003001" + 
            "040013010010404a428f9f44d445402acf562174c4ce" +  // Vehicle coordinates are 52.520008:13.404954
            "05000D01000A0200402abd80c308feac" +  // Heading direction is 13.370123°
            "06000D01000A12004060b00000000000" +  // Vehicle altitude is 133.5m
            "07000D01000A1200407f400000000000" +  // Precision is 500m
            "08000401000101" +  // The GPS signal is from a real source
            "09000B0100083fe999999999999a" +  // GPS signal strength is 80%
            "0a0013010010404a428f5c28f5c3402acf4f0d844d01" // Vehicle fuzzy coordinates are 52.5200:13.4049
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as VehicleLocation.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = VehicleLocation.State.Builder()
        builder.setCoordinates(Property(Coordinates(52.520008, 13.404954)))
        builder.setHeading(Property(Angle(13.370123, Angle.Unit.DEGREES)))
        builder.setAltitude(Property(Length(133.5, Length.Unit.METERS)))
        builder.setPrecision(Property(Length(500.0, Length.Unit.METERS)))
        builder.setGpsSource(Property(VehicleLocation.GpsSource.REAL))
        builder.setGpsSignalStrength(Property(0.8))
        builder.setFuzzyCoordinates(Property(Coordinates(52.5200, 13.4049)))
        testState(builder.build())
    }
    
    private fun testState(state: VehicleLocation.State) {
        assertTrue(state.coordinates.value?.latitude == 52.520008)
        assertTrue(state.coordinates.value?.longitude == 13.404954)
        assertTrue(state.heading.value?.value == 13.370123)
        assertTrue(state.heading.value?.unit == Angle.Unit.DEGREES)
        assertTrue(state.altitude.value?.value == 133.5)
        assertTrue(state.altitude.value?.unit == Length.Unit.METERS)
        assertTrue(state.precision.value?.value == 500.0)
        assertTrue(state.precision.value?.unit == Length.Unit.METERS)
        assertTrue(state.gpsSource.value == VehicleLocation.GpsSource.REAL)
        assertTrue(state.gpsSignalStrength.value == 0.8)
        assertTrue(state.fuzzyCoordinates.value?.latitude == 52.5200)
        assertTrue(state.fuzzyCoordinates.value?.longitude == 13.4049)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetVehicleLocation() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "003000")
        val defaultGetter = VehicleLocation.GetVehicleLocation()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0030000405060708090a")
        val propertyGetter = VehicleLocation.GetVehicleLocation(0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0405060708090a"))
    }
    
    @Test
    fun testGetVehicleLocationAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "003002")
        val created = VehicleLocation.GetVehicleLocationAvailability()
        assertTrue(created.identifier == Identifier.VEHICLE_LOCATION)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as VehicleLocation.GetVehicleLocationAvailability
        assertTrue(resolved.identifier == Identifier.VEHICLE_LOCATION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetVehicleLocationAvailabilitySome() {
        val identifierBytes = Bytes("0405060708090a")
        val allBytes = Bytes(COMMAND_HEADER + "003002" + identifierBytes)
        val constructed = VehicleLocation.GetVehicleLocationAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.VEHICLE_LOCATION)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = VehicleLocation.GetVehicleLocationAvailability(0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as VehicleLocation.GetVehicleLocationAvailability
        assertTrue(resolved.identifier == Identifier.VEHICLE_LOCATION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}