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
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KIgnitionTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003501" + 
            "01000401000100" +  // Ignition is off
            "02000401000101" +  // Accessories power is on
            "03000401000102" // Ignition state is in accessory
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Ignition.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Ignition.State.Builder()
        builder.setStatus(Property(OnOffState.OFF))
        builder.setAccessoriesStatus(Property(OnOffState.ON))
        builder.setState(Property(Ignition.IgnitionState.ACCESSORY))
        testState(builder.build())
    }
    
    private fun testState(state: Ignition.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getStatus().value == OnOffState.OFF)
        assertTrue(state.getAccessoriesStatus().value == OnOffState.ON)
        assertTrue(state.getState().value == Ignition.IgnitionState.ACCESSORY)
    }
    
    @Test fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "003500")
        val defaultGetter = Ignition.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "003500010203")
        val propertyGetter = Ignition.GetState(0x01, 0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203"))
    }
    
    @Test fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "003502")
        val created = Ignition.GetStateAvailability()
        assertTrue(created.identifier == Identifier.IGNITION)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Ignition.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.IGNITION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("010203")
        val allBytes = Bytes(COMMAND_HEADER + "003502" + identifierBytes)
        val constructed = Ignition.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.IGNITION)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Ignition.GetStateAvailability(0x01, 0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Ignition.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.IGNITION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test fun turnIgnitionOnOff() {
        val bytes = Bytes(COMMAND_HEADER + "003501" +
            "01000401000100")
    
        val constructed = Ignition.TurnIgnitionOnOff(OnOffState.OFF)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Ignition.TurnIgnitionOnOff
        assertTrue(resolved.getStatus().value == OnOffState.OFF)
        assertTrue(resolved == bytes)
    }
}