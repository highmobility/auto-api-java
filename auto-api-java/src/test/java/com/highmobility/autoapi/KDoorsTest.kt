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

class KDoorsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002001" + 
            "0200050100020001" +  // Front left door is locked inside
            "0200050100020100" +  // Front right door is unlocked inside
            "0200050100020200" +  // Rear right door is unlocked inside
            "0200050100020300" +  // Rear left door is unlocked inside
            "0300050100020000" +  // Front left door is unlocked
            "0300050100020100" +  // Front right door is unlocked
            "0300050100020201" +  // Rear right door is locked
            "0300050100020301" +  // Rear left door is locked
            "0400050100020001" +  // Front left door is open
            "0400050100020100" +  // Front right door is closed
            "0400050100020200" +  // Rear right door is closed
            "0400050100020300" +  // Rear left door is closed
            "0400050100020500" +  // All doors are closed
            "05000401000101" +  // Doors are locked inside
            "06000401000100" // Doors are unlocked
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Doors.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Doors.State.Builder()
        builder.addInsideLock(Property(Lock(Location.FRONT_LEFT, LockState.LOCKED)))
        builder.addInsideLock(Property(Lock(Location.FRONT_RIGHT, LockState.UNLOCKED)))
        builder.addInsideLock(Property(Lock(Location.REAR_RIGHT, LockState.UNLOCKED)))
        builder.addInsideLock(Property(Lock(Location.REAR_LEFT, LockState.UNLOCKED)))
        builder.addLock(Property(Lock(Location.FRONT_LEFT, LockState.UNLOCKED)))
        builder.addLock(Property(Lock(Location.FRONT_RIGHT, LockState.UNLOCKED)))
        builder.addLock(Property(Lock(Location.REAR_RIGHT, LockState.LOCKED)))
        builder.addLock(Property(Lock(Location.REAR_LEFT, LockState.LOCKED)))
        builder.addPosition(Property(DoorPosition(DoorPosition.Location.FRONT_LEFT, Position.OPEN)))
        builder.addPosition(Property(DoorPosition(DoorPosition.Location.FRONT_RIGHT, Position.CLOSED)))
        builder.addPosition(Property(DoorPosition(DoorPosition.Location.REAR_RIGHT, Position.CLOSED)))
        builder.addPosition(Property(DoorPosition(DoorPosition.Location.REAR_LEFT, Position.CLOSED)))
        builder.addPosition(Property(DoorPosition(DoorPosition.Location.ALL, Position.CLOSED)))
        builder.setInsideLocksState(Property(LockState.LOCKED))
        builder.setLocksState(Property(LockState.UNLOCKED))
        testState(builder.build())
    }
    
    private fun testState(state: Doors.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.insideLocks[0].value?.location == Location.FRONT_LEFT)
        assertTrue(state.insideLocks[0].value?.lockState == LockState.LOCKED)
        assertTrue(state.insideLocks[1].value?.location == Location.FRONT_RIGHT)
        assertTrue(state.insideLocks[1].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.insideLocks[2].value?.location == Location.REAR_RIGHT)
        assertTrue(state.insideLocks[2].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.insideLocks[3].value?.location == Location.REAR_LEFT)
        assertTrue(state.insideLocks[3].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.locks[0].value?.location == Location.FRONT_LEFT)
        assertTrue(state.locks[0].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.locks[1].value?.location == Location.FRONT_RIGHT)
        assertTrue(state.locks[1].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.locks[2].value?.location == Location.REAR_RIGHT)
        assertTrue(state.locks[2].value?.lockState == LockState.LOCKED)
        assertTrue(state.locks[3].value?.location == Location.REAR_LEFT)
        assertTrue(state.locks[3].value?.lockState == LockState.LOCKED)
        assertTrue(state.positions[0].value?.location == DoorPosition.Location.FRONT_LEFT)
        assertTrue(state.positions[0].value?.position == Position.OPEN)
        assertTrue(state.positions[1].value?.location == DoorPosition.Location.FRONT_RIGHT)
        assertTrue(state.positions[1].value?.position == Position.CLOSED)
        assertTrue(state.positions[2].value?.location == DoorPosition.Location.REAR_RIGHT)
        assertTrue(state.positions[2].value?.position == Position.CLOSED)
        assertTrue(state.positions[3].value?.location == DoorPosition.Location.REAR_LEFT)
        assertTrue(state.positions[3].value?.position == Position.CLOSED)
        assertTrue(state.positions[4].value?.location == DoorPosition.Location.ALL)
        assertTrue(state.positions[4].value?.position == Position.CLOSED)
        assertTrue(state.insideLocksState.value == LockState.LOCKED)
        assertTrue(state.locksState.value == LockState.UNLOCKED)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002000")
        val defaultGetter = Doors.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0020000203040506")
        val propertyGetter = Doors.GetState(0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0203040506"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002002")
        val created = Doors.GetStateAvailability()
        assertTrue(created.identifier == Identifier.DOORS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Doors.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.DOORS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0203040506")
        val allBytes = Bytes(COMMAND_HEADER + "002002" + identifierBytes)
        val constructed = Doors.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.DOORS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Doors.GetStateAvailability(0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Doors.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.DOORS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun lockUnlockDoors() {
        val bytes = Bytes(COMMAND_HEADER + "002001" +
            "06000401000100")
    
        val constructed = Doors.LockUnlockDoors(LockState.UNLOCKED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Doors.LockUnlockDoors
        assertTrue(resolved.locksState.value == LockState.UNLOCKED)
        assertTrue(resolved == bytes)
    }
}