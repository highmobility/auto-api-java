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
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "002100")
        assertTrue(Trunk.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0021000102")
        val getter = Trunk.GetProperties(0x01, 0x02)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testControlTrunk() {
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