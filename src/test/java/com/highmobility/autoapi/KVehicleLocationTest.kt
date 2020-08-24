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
            "07000D01000A1200407f400000000000" // Precision is 500m
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
        testState(builder.build())
    }
    
    private fun testState(state: VehicleLocation.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getCoordinates().value?.latitude == 52.520008)
        assertTrue(state.getCoordinates().value?.longitude == 13.404954)
        assertTrue(state.getHeading().value?.value == 13.370123)
        assertTrue(state.getHeading().value?.unit == Angle.Unit.DEGREES)
        assertTrue(state.getAltitude().value?.value == 133.5)
        assertTrue(state.getAltitude().value?.unit == Length.Unit.METERS)
        assertTrue(state.getPrecision().value?.value == 500.0)
        assertTrue(state.getPrecision().value?.unit == Length.Unit.METERS)
    }
    
    @Test
    fun testGetVehicleLocation() {
        val bytes = Bytes(COMMAND_HEADER + "003000")
        assertTrue(VehicleLocation.GetVehicleLocation() == bytes)
    }
    
    @Test
    fun testGetVehicleLocationProperties() {
        val bytes = Bytes(COMMAND_HEADER + "00300004050607")
        val getter = VehicleLocation.GetVehicleLocationProperties(0x04, 0x05, 0x06, 0x07)
        assertTrue(getter == bytes)
    }
}