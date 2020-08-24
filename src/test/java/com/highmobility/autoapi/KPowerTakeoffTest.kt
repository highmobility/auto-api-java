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

class KPowerTakeoffTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006501" + 
            "01000401000101" +  // Power take-off is active
            "02000401000101" // Power take-off is engaged
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as PowerTakeoff.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = PowerTakeoff.State.Builder()
        builder.setStatus(Property(ActiveState.ACTIVE))
        builder.setEngaged(Property(PowerTakeoff.Engaged.ENGAGED))
        testState(builder.build())
    }
    
    private fun testState(state: PowerTakeoff.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getStatus().value == ActiveState.ACTIVE)
        assertTrue(state.getEngaged().value == PowerTakeoff.Engaged.ENGAGED)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "006500")
        assertTrue(PowerTakeoff.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0065000102")
        val getter = PowerTakeoff.GetProperties(0x01, 0x02)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testActivateDeactivatePowerTakeoff() {
        val bytes = Bytes(COMMAND_HEADER + "006501" + 
            "01000401000101")
    
        val constructed = PowerTakeoff.ActivateDeactivatePowerTakeoff(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as PowerTakeoff.ActivateDeactivatePowerTakeoff
        assertTrue(resolved.getStatus().value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
}