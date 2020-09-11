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

class KFirmwareVersionTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "000301" + 
            "010006010003010f21" +  // HMKit version is 1.15.33
            "02000F01000C6274737461636b2d75617274" +  // Build name is 'btstack-uart'
            "03000C01000976312e352d70726f64" // Application version is 'v1.5-prod'
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as FirmwareVersion.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = FirmwareVersion.State.Builder()
        builder.setHmKitVersion(Property(HmkitVersion(1, 15, 33)))
        builder.setHmKitBuildName(Property("btstack-uart"))
        builder.setApplicationVersion(Property("v1.5-prod"))
        testState(builder.build())
    }
    
    private fun testState(state: FirmwareVersion.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getHmKitVersion().value?.major == 1)
        assertTrue(state.getHmKitVersion().value?.minor == 15)
        assertTrue(state.getHmKitVersion().value?.patch == 33)
        assertTrue(state.getHmKitBuildName().value == "btstack-uart")
        assertTrue(state.getApplicationVersion().value == "v1.5-prod")
    }
    
    @Test fun testGetFirmwareVersion() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "000300")
        val defaultGetter = FirmwareVersion.GetFirmwareVersion()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "000300010203")
        val propertyGetter = FirmwareVersion.GetFirmwareVersion(0x01, 0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203"))
    }
    
    @Test fun testGetFirmwareVersionAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "000302")
        val created = FirmwareVersion.GetFirmwareVersionAvailability()
        assertTrue(created.identifier == Identifier.FIRMWARE_VERSION)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as FirmwareVersion.GetFirmwareVersionAvailability
        assertTrue(resolved.identifier == Identifier.FIRMWARE_VERSION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test fun testGetFirmwareVersionAvailabilitySome() {
        val identifierBytes = Bytes("010203")
        val allBytes = Bytes(COMMAND_HEADER + "000302" + identifierBytes)
        val constructed = FirmwareVersion.GetFirmwareVersionAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.FIRMWARE_VERSION)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = FirmwareVersion.GetFirmwareVersionAvailability(0x01, 0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as FirmwareVersion.GetFirmwareVersionAvailability
        assertTrue(resolved.identifier == Identifier.FIRMWARE_VERSION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}