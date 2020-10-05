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

class KNaviDestinationTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003101" + 
            "010013010010404a428f9f44d445402acf562174c4ce" +  // Coordinates are 52.520008:13.404954
            "0200090100064265726c696e" +  // Destination name is 'Berlin'
            "0300040100010e" +  // 14 available POI data slots
            "0400040100011e" +  // Maximum number of POI data slots is 30
            "05000D01000A07024004cccccccccccd" +  // Remaining time to destination is 2.6h
            "06000D01000A12044094e40000000000" // Remaining distance to destination is 1337.0km
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as NaviDestination.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = NaviDestination.State.Builder()
        builder.setCoordinates(Property(Coordinates(52.520008, 13.404954)))
        builder.setDestinationName(Property("Berlin"))
        builder.setDataSlotsFree(Property(14))
        builder.setDataSlotsMax(Property(30))
        builder.setArrivalDuration(Property(Duration(2.6, Duration.Unit.HOURS)))
        builder.setDistanceToDestination(Property(Length(1337.0, Length.Unit.KILOMETERS)))
        testState(builder.build())
    }
    
    private fun testState(state: NaviDestination.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getCoordinates().value?.latitude == 52.520008)
        assertTrue(state.getCoordinates().value?.longitude == 13.404954)
        assertTrue(state.getDestinationName().value == "Berlin")
        assertTrue(state.getDataSlotsFree().value == 14)
        assertTrue(state.getDataSlotsMax().value == 30)
        assertTrue(state.getArrivalDuration().value?.value == 2.6)
        assertTrue(state.getArrivalDuration().value?.unit == Duration.Unit.HOURS)
        assertTrue(state.getDistanceToDestination().value?.value == 1337.0)
        assertTrue(state.getDistanceToDestination().value?.unit == Length.Unit.KILOMETERS)
    }
    
    @Test
    fun testGetNaviDestination() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "003100")
        val defaultGetter = NaviDestination.GetNaviDestination()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "003100010203040506")
        val propertyGetter = NaviDestination.GetNaviDestination(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203040506"))
    }
    
    @Test
    fun testGetNaviDestinationAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "003102")
        val created = NaviDestination.GetNaviDestinationAvailability()
        assertTrue(created.identifier == Identifier.NAVI_DESTINATION)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as NaviDestination.GetNaviDestinationAvailability
        assertTrue(resolved.identifier == Identifier.NAVI_DESTINATION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetNaviDestinationAvailabilitySome() {
        val identifierBytes = Bytes("010203040506")
        val allBytes = Bytes(COMMAND_HEADER + "003102" + identifierBytes)
        val constructed = NaviDestination.GetNaviDestinationAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.NAVI_DESTINATION)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = NaviDestination.GetNaviDestinationAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as NaviDestination.GetNaviDestinationAvailability
        assertTrue(resolved.identifier == Identifier.NAVI_DESTINATION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun setNaviDestination() {
        val bytes = Bytes(COMMAND_HEADER + "003101" +
            "010013010010404a428f9f44d445402acf562174c4ce" +
            "0200090100064265726c696e")
    
        val constructed = NaviDestination.SetNaviDestination(Coordinates(52.520008, 13.404954), "Berlin")
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as NaviDestination.SetNaviDestination
        assertTrue(resolved.getCoordinates().value?.latitude == 52.520008)
        assertTrue(resolved.getCoordinates().value?.longitude == 13.404954)
        assertTrue(resolved.getDestinationName().value == "Berlin")
        assertTrue(resolved == bytes)
    }
}