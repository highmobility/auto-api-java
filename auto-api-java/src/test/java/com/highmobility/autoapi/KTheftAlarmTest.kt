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

class KTheftAlarmTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "004601" + 
            "01000401000101" +  // Theft alarm is armed
            "02000401000102" +  // Interior protection sensor is active
            "03000401000102" +  // Tow protection sensor is active
            "04000401000106" +  // Last warning is for the hood
            "05000B01000800000172bcd25b10" +  // Last trip happened at 16. June 2020 at 11:10:02 GMT
            "06000401000100" +  // Last event had a low impact
            "07000401000105" // Last event happened to rear right position
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as TheftAlarm.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = TheftAlarm.State.Builder()
        builder.setStatus(Property(TheftAlarm.Status.ARMED))
        builder.setInteriorProtectionStatus(Property(ActiveSelectedState.ACTIVE))
        builder.setTowProtectionStatus(Property(ActiveSelectedState.ACTIVE))
        builder.setLastWarningReason(Property(TheftAlarm.LastWarningReason.HOOD))
        builder.setLastEvent(Property(getCalendar("2020-06-16T11:10:02.000Z")))
        builder.setLastEventLevel(Property(TheftAlarm.LastEventLevel.LOW))
        builder.setEventType(Property(TheftAlarm.EventType.REAR_RIGHT))
        testState(builder.build())
    }
    
    private fun testState(state: TheftAlarm.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getStatus().value == TheftAlarm.Status.ARMED)
        assertTrue(state.getInteriorProtectionStatus().value == ActiveSelectedState.ACTIVE)
        assertTrue(state.getTowProtectionStatus().value == ActiveSelectedState.ACTIVE)
        assertTrue(state.getLastWarningReason().value == TheftAlarm.LastWarningReason.HOOD)
        assertTrue(dateIsSame(state.getLastEvent().value, "2020-06-16T11:10:02.000Z"))
        assertTrue(state.getLastEventLevel().value == TheftAlarm.LastEventLevel.LOW)
        assertTrue(state.getEventType().value == TheftAlarm.EventType.REAR_RIGHT)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "004600")
        val defaultGetter = TheftAlarm.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "00460001020304050607")
        val propertyGetter = TheftAlarm.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("01020304050607"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "004602")
        val created = TheftAlarm.GetStateAvailability()
        assertTrue(created.identifier == Identifier.THEFT_ALARM)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as TheftAlarm.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.THEFT_ALARM)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("01020304050607")
        val allBytes = Bytes(COMMAND_HEADER + "004602" + identifierBytes)
        val constructed = TheftAlarm.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.THEFT_ALARM)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = TheftAlarm.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as TheftAlarm.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.THEFT_ALARM)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun setTheftAlarm() {
        val bytes = Bytes(COMMAND_HEADER + "004601" +
            "01000401000101")
    
        val constructed = TheftAlarm.SetTheftAlarm(TheftAlarm.Status.ARMED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as TheftAlarm.SetTheftAlarm
        assertTrue(resolved.getStatus().value == TheftAlarm.Status.ARMED)
        assertTrue(resolved == bytes)
    }
}