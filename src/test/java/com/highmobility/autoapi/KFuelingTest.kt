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
        assertTrue(state.getGasFlapLock().value == LockState.LOCKED)
        assertTrue(state.getGasFlapPosition().value == Position.CLOSED)
    }
    
    @Test
    fun testGetGasFlapState() {
        val bytes = Bytes(COMMAND_HEADER + "004000")
        assertTrue(Fueling.GetGasFlapState() == bytes)
    }
    
    @Test
    fun testGetGasFlapProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0040000203")
        val getter = Fueling.GetGasFlapProperties(0x02, 0x03)
        assertTrue(getter == bytes)
    }
    
    @Test fun controlGasFlap() {
        val bytes = Bytes(COMMAND_HEADER + "004001" +
            "02000401000101" +
            "03000401000100")
    
        val constructed = Fueling.ControlGasFlap(LockState.LOCKED, Position.CLOSED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Fueling.ControlGasFlap
        assertTrue(resolved.getGasFlapLock().value == LockState.LOCKED)
        assertTrue(resolved.getGasFlapPosition().value == Position.CLOSED)
        assertTrue(resolved == bytes)
    }
}