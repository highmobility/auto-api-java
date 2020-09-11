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

class KTrunkTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002101" + 
            "01000401000100" +  // Trunk is unlocked
            "02000401000101" // Trunk is open
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Trunk.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Trunk.State.Builder()
        builder.setLock(Property(LockState.UNLOCKED))
        builder.setPosition(Property(Position.OPEN))
        testState(builder.build())
    }
    
    private fun testState(state: Trunk.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getLock().value == LockState.UNLOCKED)
        assertTrue(state.getPosition().value == Position.OPEN)
    }
    
    @Test fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002100")
        val defaultGetter = Trunk.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0021000102")
        val propertyGetter = Trunk.GetState(0x01, 0x02)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102"))
    }
    
    @Test fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002102")
        val created = Trunk.GetStateAvailability()
        assertTrue(created.identifier == Identifier.TRUNK)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Trunk.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.TRUNK)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102")
        val allBytes = Bytes(COMMAND_HEADER + "002102" + identifierBytes)
        val constructed = Trunk.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.TRUNK)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Trunk.GetStateAvailability(0x01, 0x02)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Trunk.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.TRUNK)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test fun controlTrunk() {
        val bytes = Bytes(COMMAND_HEADER + "002101" +
            "01000401000100" +
            "02000401000101")
    
        val constructed = Trunk.ControlTrunk(LockState.UNLOCKED, Position.OPEN)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Trunk.ControlTrunk
        assertTrue(resolved.getLock().value == LockState.UNLOCKED)
        assertTrue(resolved.getPosition().value == Position.OPEN)
        assertTrue(resolved == bytes)
    }
}