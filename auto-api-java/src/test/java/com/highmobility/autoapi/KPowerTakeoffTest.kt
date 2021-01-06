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
        assertTrue(state.status.value == ActiveState.ACTIVE)
        assertTrue(state.engaged.value == PowerTakeoff.Engaged.ENGAGED)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006500")
        val defaultGetter = PowerTakeoff.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0065000102")
        val propertyGetter = PowerTakeoff.GetState(0x01, 0x02)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006502")
        val created = PowerTakeoff.GetStateAvailability()
        assertTrue(created.identifier == Identifier.POWER_TAKEOFF)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as PowerTakeoff.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.POWER_TAKEOFF)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102")
        val allBytes = Bytes(COMMAND_HEADER + "006502" + identifierBytes)
        val constructed = PowerTakeoff.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.POWER_TAKEOFF)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = PowerTakeoff.GetStateAvailability(0x01, 0x02)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as PowerTakeoff.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.POWER_TAKEOFF)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun activateDeactivatePowerTakeoff() {
        val bytes = Bytes(COMMAND_HEADER + "006501" +
            "01000401000101")
    
        val constructed = PowerTakeoff.ActivateDeactivatePowerTakeoff(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as PowerTakeoff.ActivateDeactivatePowerTakeoff
        assertTrue(resolved.status.value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
}