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
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002600")
        val defaultGetter = HonkHornFlashLights.GetFlashersState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0026000102030405")
        val propertyGetter = HonkHornFlashLights.GetFlashersState(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405"))
    }
    
    @Test
    fun testGetFlashersStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002602")
        val created = HonkHornFlashLights.GetFlashersStateAvailability()
        assertTrue(created.identifier == Identifier.HONK_HORN_FLASH_LIGHTS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as HonkHornFlashLights.GetFlashersStateAvailability
        assertTrue(resolved.identifier == Identifier.HONK_HORN_FLASH_LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetFlashersStateAvailabilitySome() {
        val identifierBytes = Bytes("0102030405")
        val allBytes = Bytes(COMMAND_HEADER + "002602" + identifierBytes)
        val constructed = HonkHornFlashLights.GetFlashersStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.HONK_HORN_FLASH_LIGHTS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = HonkHornFlashLights.GetFlashersStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as HonkHornFlashLights.GetFlashersStateAvailability
        assertTrue(resolved.identifier == Identifier.HONK_HORN_FLASH_LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun honkFlash() {
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
    fun activateDeactivateEmergencyFlasher() {
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