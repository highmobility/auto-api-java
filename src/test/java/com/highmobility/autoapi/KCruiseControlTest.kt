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
import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KCruiseControlTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006201" + 
            "01000401000101" +  // Cruise control is active
            "02000401000101" +  // Higher speed requested by the limiter
            "03000D01000A1601404e800000000000" +  // The target speed is set to 61.0km/h
            "04000401000100" +  // Adaptive Cruise Control is inactive
            "05000D01000A16014050c00000000000" // The Adaptive Cruise Control target speed is set to 67.0km/h
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as CruiseControl.State)
    }
    
    private fun testState(state: CruiseControl.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getCruiseControl().value == ActiveState.ACTIVE)
        assertTrue(state.getLimiter().value == CruiseControl.Limiter.HIGHER_SPEED_REQUESTED)
        assertTrue(state.getTargetSpeed().value?.value == 61.0)
        assertTrue(state.getTargetSpeed().value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.getAdaptiveCruiseControl().value == ActiveState.INACTIVE)
        assertTrue(state.getAccTargetSpeed().value?.value == 67.0)
        assertTrue(state.getAccTargetSpeed().value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "006200")
        assertTrue(CruiseControl.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0062000102030405")
        val getter = CruiseControl.GetProperties(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(getter == bytes)
    }
    
    @Test fun activateDeactivateCruiseControl() {
        val bytes = Bytes(COMMAND_HEADER + "006201" +
            "01000401000101" +
            "03000D01000A1601404e800000000000")
    
        val constructed = CruiseControl.ActivateDeactivateCruiseControl(ActiveState.ACTIVE, Speed(61.0, Speed.Unit.KILOMETERS_PER_HOUR))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as CruiseControl.ActivateDeactivateCruiseControl
        assertTrue(resolved.getCruiseControl().value == ActiveState.ACTIVE)
        assertTrue(resolved.getTargetSpeed().value?.value == 61.0)
        assertTrue(resolved.getTargetSpeed().value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(resolved == bytes)
    }
}