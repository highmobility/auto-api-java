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

class KParkingBrakeTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005801" + 
            "01000401000101" // Parking brake is active
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as ParkingBrake.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = ParkingBrake.State.Builder()
        builder.setStatus(Property(ActiveState.ACTIVE))
        testState(builder.build())
    }
    
    private fun testState(state: ParkingBrake.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getStatus().value == ActiveState.ACTIVE)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "005800")
        assertTrue(ParkingBrake.GetState() == bytes)
    }
    
    @Test
    fun testSetParkingBrake() {
        val bytes = Bytes(COMMAND_HEADER + "005801" + 
            "01000401000101")
    
        val constructed = ParkingBrake.SetParkingBrake(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ParkingBrake.SetParkingBrake
        assertTrue(resolved.getStatus().value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
}