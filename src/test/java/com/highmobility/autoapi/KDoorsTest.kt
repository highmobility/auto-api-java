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
        assertTrue(state.getInsideLocks()[0].value?.location == Location.FRONT_LEFT)
        assertTrue(state.getInsideLocks()[0].value?.lockState == LockState.LOCKED)
        assertTrue(state.getInsideLocks()[1].value?.location == Location.FRONT_RIGHT)
        assertTrue(state.getInsideLocks()[1].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.getInsideLocks()[2].value?.location == Location.REAR_RIGHT)
        assertTrue(state.getInsideLocks()[2].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.getInsideLocks()[3].value?.location == Location.REAR_LEFT)
        assertTrue(state.getInsideLocks()[3].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.getLocks()[0].value?.location == Location.FRONT_LEFT)
        assertTrue(state.getLocks()[0].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.getLocks()[1].value?.location == Location.FRONT_RIGHT)
        assertTrue(state.getLocks()[1].value?.lockState == LockState.UNLOCKED)
        assertTrue(state.getLocks()[2].value?.location == Location.REAR_RIGHT)
        assertTrue(state.getLocks()[2].value?.lockState == LockState.LOCKED)
        assertTrue(state.getLocks()[3].value?.location == Location.REAR_LEFT)
        assertTrue(state.getLocks()[3].value?.lockState == LockState.LOCKED)
        assertTrue(state.getPositions()[0].value?.location == DoorPosition.Location.FRONT_LEFT)
        assertTrue(state.getPositions()[0].value?.position == Position.OPEN)
        assertTrue(state.getPositions()[1].value?.location == DoorPosition.Location.FRONT_RIGHT)
        assertTrue(state.getPositions()[1].value?.position == Position.CLOSED)
        assertTrue(state.getPositions()[2].value?.location == DoorPosition.Location.REAR_RIGHT)
        assertTrue(state.getPositions()[2].value?.position == Position.CLOSED)
        assertTrue(state.getPositions()[3].value?.location == DoorPosition.Location.REAR_LEFT)
        assertTrue(state.getPositions()[3].value?.position == Position.CLOSED)
        assertTrue(state.getPositions()[4].value?.location == DoorPosition.Location.ALL)
        assertTrue(state.getPositions()[4].value?.position == Position.CLOSED)
        assertTrue(state.getInsideLocksState().value == LockState.LOCKED)
        assertTrue(state.getLocksState().value == LockState.UNLOCKED)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "002000")
        assertTrue(Doors.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0020000203040506")
        val getter = Doors.GetProperties(0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(getter == bytes)
    }
    
    @Test fun lockUnlockDoors() {
        val bytes = Bytes(COMMAND_HEADER + "002001" +
            "06000401000100")
    
        val constructed = Doors.LockUnlockDoors(LockState.UNLOCKED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Doors.LockUnlockDoors
        assertTrue(resolved.getLocksState().value == LockState.UNLOCKED)
        assertTrue(resolved == bytes)
    }
}