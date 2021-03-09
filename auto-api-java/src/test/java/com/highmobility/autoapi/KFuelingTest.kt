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

class KFuelingTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "004001" + 
            "02000401000101" +  // Gas flap is locked
            "03000401000100" // Gas flap is closed
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Fueling.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Fueling.State.Builder()
        builder.setGasFlapLock(Property(LockState.LOCKED))
        builder.setGasFlapPosition(Property(Position.CLOSED))
        testState(builder.build())
    }
    
    private fun testState(state: Fueling.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.gasFlapLock.value == LockState.LOCKED)
        assertTrue(state.gasFlapPosition.value == Position.CLOSED)
    }
    
    @Test
    fun testGetGasFlapState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "004000")
        val defaultGetter = Fueling.GetGasFlapState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0040000203")
        val propertyGetter = Fueling.GetGasFlapState(0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0203"))
    }
    
    @Test
    fun testGetGasFlapStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "004002")
        val created = Fueling.GetGasFlapStateAvailability()
        assertTrue(created.identifier == Identifier.FUELING)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Fueling.GetGasFlapStateAvailability
        assertTrue(resolved.identifier == Identifier.FUELING)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetGasFlapStateAvailabilitySome() {
        val identifierBytes = Bytes("0203")
        val allBytes = Bytes(COMMAND_HEADER + "004002" + identifierBytes)
        val constructed = Fueling.GetGasFlapStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.FUELING)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Fueling.GetGasFlapStateAvailability(0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Fueling.GetGasFlapStateAvailability
        assertTrue(resolved.identifier == Identifier.FUELING)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun controlGasFlap() {
        val bytes = Bytes(COMMAND_HEADER + "004001" +
            "02000401000101" +
            "03000401000100")
    
        val constructed = Fueling.ControlGasFlap(LockState.LOCKED, Position.CLOSED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Fueling.ControlGasFlap
        assertTrue(resolved.gasFlapLock.value == LockState.LOCKED)
        assertTrue(resolved.gasFlapPosition.value == Position.CLOSED)
        assertTrue(resolved == bytes)
    }
}