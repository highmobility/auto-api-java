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
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KRooftopControlTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002501" + 
            "01000B0100083ff0000000000000" +  // Rooftop is opaque (100%)
            "02000B0100083fe0000000000000" +  // Rooftop is half-open (50%)
            "03000401000101" +  // Convertible roof is open
            "04000401000102" +  // Sunroof is half-tilted
            "05000401000101" +  // Sunroof is open
            "06000401000100" // Sunroof had no rain event
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as RooftopControl.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = RooftopControl.State.Builder()
        builder.setDimming(Property(1.0))
        builder.setPosition(Property(0.5))
        builder.setConvertibleRoofState(Property(RooftopControl.ConvertibleRoofState.OPEN))
        builder.setSunroofTiltState(Property(RooftopControl.SunroofTiltState.HALF_TILTED))
        builder.setSunroofState(Property(RooftopControl.SunroofState.OPEN))
        builder.setSunroofRainEvent(Property(RooftopControl.SunroofRainEvent.NO_EVENT))
        testState(builder.build())
    }
    
    private fun testState(state: RooftopControl.State) {
        assertTrue(state.dimming.value == 1.0)
        assertTrue(state.position.value == 0.5)
        assertTrue(state.convertibleRoofState.value == RooftopControl.ConvertibleRoofState.OPEN)
        assertTrue(state.sunroofTiltState.value == RooftopControl.SunroofTiltState.HALF_TILTED)
        assertTrue(state.sunroofState.value == RooftopControl.SunroofState.OPEN)
        assertTrue(state.sunroofRainEvent.value == RooftopControl.SunroofRainEvent.NO_EVENT)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetRooftopState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002500")
        val defaultGetter = RooftopControl.GetRooftopState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "002500010203040506")
        val propertyGetter = RooftopControl.GetRooftopState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203040506"))
    }
    
    @Test
    fun testGetRooftopStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002502")
        val created = RooftopControl.GetRooftopStateAvailability()
        assertTrue(created.identifier == Identifier.ROOFTOP_CONTROL)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as RooftopControl.GetRooftopStateAvailability
        assertTrue(resolved.identifier == Identifier.ROOFTOP_CONTROL)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetRooftopStateAvailabilitySome() {
        val identifierBytes = Bytes("010203040506")
        val allBytes = Bytes(COMMAND_HEADER + "002502" + identifierBytes)
        val constructed = RooftopControl.GetRooftopStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.ROOFTOP_CONTROL)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = RooftopControl.GetRooftopStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as RooftopControl.GetRooftopStateAvailability
        assertTrue(resolved.identifier == Identifier.ROOFTOP_CONTROL)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun controlRooftop() {
        val bytes = Bytes(COMMAND_HEADER + "002501" +
            "01000B0100083ff0000000000000" +
            "02000B0100083fe0000000000000" +
            "03000401000101" +
            "04000401000102" +
            "05000401000101")
    
        val constructed = RooftopControl.ControlRooftop(1.0, 0.5, RooftopControl.ConvertibleRoofState.OPEN, RooftopControl.SunroofTiltState.HALF_TILTED, RooftopControl.SunroofState.OPEN)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as RooftopControl.ControlRooftop
        assertTrue(resolved.dimming.value == 1.0)
        assertTrue(resolved.position.value == 0.5)
        assertTrue(resolved.convertibleRoofState.value == RooftopControl.ConvertibleRoofState.OPEN)
        assertTrue(resolved.sunroofTiltState.value == RooftopControl.SunroofTiltState.HALF_TILTED)
        assertTrue(resolved.sunroofState.value == RooftopControl.SunroofState.OPEN)
        assertTrue(resolved == bytes)
    }
}