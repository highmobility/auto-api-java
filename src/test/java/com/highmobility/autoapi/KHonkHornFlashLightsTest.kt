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

class KHonkHornFlashLightsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002601" + 
            "01000401000102" // Left flasher is active
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as HonkHornFlashLights.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = HonkHornFlashLights.State.Builder()
        builder.setFlashers(Property(HonkHornFlashLights.Flashers.LEFT_FLASHER_ACTIVE))
        testState(builder.build())
    }
    
    private fun testState(state: HonkHornFlashLights.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getFlashers().value == HonkHornFlashLights.Flashers.LEFT_FLASHER_ACTIVE)
    }
    
    @Test
    fun testGetFlashersState() {
        val bytes = Bytes(COMMAND_HEADER + "002600")
        assertTrue(HonkHornFlashLights.GetFlashersState() == bytes)
    }
    
    @Test
    fun testGetFlashersProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0026000102030405")
        val getter = HonkHornFlashLights.GetFlashersProperties(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testHonkFlash() {
        val bytes = Bytes(COMMAND_HEADER + "002601" + 
            "03000401000105" +
            "05000D01000A07004000000000000000")
    
        val constructed = HonkHornFlashLights.HonkFlash(5, Duration(2.0, Duration.Unit.SECONDS))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HonkHornFlashLights.HonkFlash
        assertTrue(resolved.getFlashTimes().value == 5)
        assertTrue(resolved.getHonkTime().value?.value == 2.0)
        assertTrue(resolved.getHonkTime().value?.unit == Duration.Unit.SECONDS)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testActivateDeactivateEmergencyFlasher() {
        val bytes = Bytes(COMMAND_HEADER + "002601" + 
            "04000401000101")
    
        val constructed = HonkHornFlashLights.ActivateDeactivateEmergencyFlasher(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HonkHornFlashLights.ActivateDeactivateEmergencyFlasher
        assertTrue(resolved.getEmergencyFlashersState().value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
}