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
import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KOffroadTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005201" + 
            "01000D01000A02004024333333333333" +  // Route incline is 10°
            "02000B0100083fe0000000000000" // Wheel suspension level is 50%
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Offroad.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Offroad.State.Builder()
        builder.setRouteIncline(Property(Angle(10.1, Angle.Unit.DEGREES)))
        builder.setWheelSuspension(Property(0.5))
        testState(builder.build())
    }
    
    private fun testState(state: Offroad.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getRouteIncline().value?.value == 10.1)
        assertTrue(state.getRouteIncline().value?.unit == Angle.Unit.DEGREES)
        assertTrue(state.getWheelSuspension().value == 0.5)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "005200")
        assertTrue(Offroad.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0052000102")
        val getter = Offroad.GetProperties(0x01, 0x02)
        assertTrue(getter == bytes)
    }
}