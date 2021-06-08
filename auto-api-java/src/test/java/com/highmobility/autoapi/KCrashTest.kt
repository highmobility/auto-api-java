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

class KCrashTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006b01" + 
            "010006010003000101" +  // High severity front crash incident needs repairs
            "010006010003010201" +  // Medium severity lateral crash incident needs repairs
            "010006010003020302" +  // Low severity rear crash incident does not need repairs
            "02000401000101" +  // Crash type is non-pedestrian (i.e. a another vehicle)
            "03000401000101" +  // Crash did not tip over the vehicle
            "04000401000101" +  // Automatic eCall is enabled
            "05000401000102" +  // Crash severity is 2
            "06000401000107" // Impact zone is front driver side
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Crash.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Crash.State.Builder()
        builder.addIncident(Property(CrashIncident(CrashIncident.Location.FRONT, CrashIncident.Severity.HIGH, CrashIncident.Repairs.NEEDED)))
        builder.addIncident(Property(CrashIncident(CrashIncident.Location.LATERAL, CrashIncident.Severity.MEDIUM, CrashIncident.Repairs.NEEDED)))
        builder.addIncident(Property(CrashIncident(CrashIncident.Location.REAR, CrashIncident.Severity.LOW, CrashIncident.Repairs.NOT_NEEDED)))
        builder.setType(Property(Crash.Type.NON_PEDESTRIAN))
        builder.setTippedState(Property(Crash.TippedState.NOT_TIPPED))
        builder.setAutomaticECall(Property(EnabledState.ENABLED))
        builder.setSeverity(Property(2))
        builder.setImpactZone(Property(Crash.ImpactZone.FRONT_DRIVER_SIDE))
        testState(builder.build())
    }
    
    private fun testState(state: Crash.State) {
        assertTrue(state.incidents[0].value?.location == CrashIncident.Location.FRONT)
        assertTrue(state.incidents[0].value?.severity == CrashIncident.Severity.HIGH)
        assertTrue(state.incidents[0].value?.repairs == CrashIncident.Repairs.NEEDED)
        assertTrue(state.incidents[1].value?.location == CrashIncident.Location.LATERAL)
        assertTrue(state.incidents[1].value?.severity == CrashIncident.Severity.MEDIUM)
        assertTrue(state.incidents[1].value?.repairs == CrashIncident.Repairs.NEEDED)
        assertTrue(state.incidents[2].value?.location == CrashIncident.Location.REAR)
        assertTrue(state.incidents[2].value?.severity == CrashIncident.Severity.LOW)
        assertTrue(state.incidents[2].value?.repairs == CrashIncident.Repairs.NOT_NEEDED)
        assertTrue(state.type.value == Crash.Type.NON_PEDESTRIAN)
        assertTrue(state.tippedState.value == Crash.TippedState.NOT_TIPPED)
        assertTrue(state.automaticECall.value == EnabledState.ENABLED)
        assertTrue(state.severity.value == 2)
        assertTrue(state.impactZone.value == Crash.ImpactZone.FRONT_DRIVER_SIDE)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006b00")
        val defaultGetter = Crash.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "006b00010203040506")
        val propertyGetter = Crash.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203040506"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006b02")
        val created = Crash.GetStateAvailability()
        assertTrue(created.identifier == Identifier.CRASH)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Crash.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.CRASH)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("010203040506")
        val allBytes = Bytes(COMMAND_HEADER + "006b02" + identifierBytes)
        val constructed = Crash.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.CRASH)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Crash.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Crash.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.CRASH)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}