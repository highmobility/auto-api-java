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

class KEngineTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006901" + 
            "01000401000100" +  // Engine is off
            "02000401000101" +  // Automatic engine start-stop system is currently active
            "03000401000101" // Automatic start-stop system is enabled
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Engine.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Engine.State.Builder()
        builder.setStatus(Property(OnOffState.OFF))
        builder.setStartStopState(Property(ActiveState.ACTIVE))
        builder.setStartStopEnabled(Property(EnabledState.ENABLED))
        testState(builder.build())
    }
    
    private fun testState(state: Engine.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.status.value == OnOffState.OFF)
        assertTrue(state.startStopState.value == ActiveState.ACTIVE)
        assertTrue(state.startStopEnabled.value == EnabledState.ENABLED)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006900")
        val defaultGetter = Engine.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "006900010203")
        val propertyGetter = Engine.GetState(0x01, 0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006902")
        val created = Engine.GetStateAvailability()
        assertTrue(created.identifier == Identifier.ENGINE)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Engine.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.ENGINE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("010203")
        val allBytes = Bytes(COMMAND_HEADER + "006902" + identifierBytes)
        val constructed = Engine.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.ENGINE)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Engine.GetStateAvailability(0x01, 0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Engine.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.ENGINE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun turnEngineOnOff() {
        val bytes = Bytes(COMMAND_HEADER + "006901" +
            "01000401000100")
    
        val constructed = Engine.TurnEngineOnOff(OnOffState.OFF)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Engine.TurnEngineOnOff
        assertTrue(resolved.status.value == OnOffState.OFF)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun enableDisableStartStop() {
        val bytes = Bytes(COMMAND_HEADER + "006901" +
            "03000401000101")
    
        val constructed = Engine.EnableDisableStartStop(EnabledState.ENABLED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Engine.EnableDisableStartStop
        assertTrue(resolved.startStopEnabled.value == EnabledState.ENABLED)
        assertTrue(resolved == bytes)
    }
}