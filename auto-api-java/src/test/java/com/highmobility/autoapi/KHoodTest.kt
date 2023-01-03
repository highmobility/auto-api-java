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

class KHoodTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006701" + 
            "01000401000101" +  // Hood is open
            "02000401000101" +  // The hood is locked.
            "03000401000100" // Trunk lock is safely locked.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Hood.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Hood.State.Builder()
        builder.setPosition(Property(Hood.Position.OPEN))
        builder.setLock(Property(LockState.LOCKED))
        builder.setLockSafety(Property(LockSafety.SAFE))
        testState(builder.build())
    }
    
    private fun testState(state: Hood.State) {
        assertTrue(state.position.value == Hood.Position.OPEN)
        assertTrue(state.lock.value == LockState.LOCKED)
        assertTrue(state.lockSafety.value == LockSafety.SAFE)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006700")
        val defaultGetter = Hood.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "006700010203")
        val propertyGetter = Hood.GetState(0x01, 0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006702")
        val created = Hood.GetStateAvailability()
        assertTrue(created.identifier == Identifier.HOOD)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Hood.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.HOOD)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("010203")
        val allBytes = Bytes(COMMAND_HEADER + "006702" + identifierBytes)
        val constructed = Hood.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.HOOD)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Hood.GetStateAvailability(0x01, 0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Hood.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.HOOD)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}