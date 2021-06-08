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

class KLightConditionsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005401" + 
            "01000D01000A110040fb198000000000" +  // Outside illuminance is 111'000.0lux
            "02000D01000A11003fd0000000000000" // Inside illuminance is 0.25lux
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as LightConditions.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = LightConditions.State.Builder()
        builder.setOutsideLight(Property(Illuminance(111000.0, Illuminance.Unit.LUX)))
        builder.setInsideLight(Property(Illuminance(0.25, Illuminance.Unit.LUX)))
        testState(builder.build())
    }
    
    private fun testState(state: LightConditions.State) {
        assertTrue(state.outsideLight.value?.value == 111000.0)
        assertTrue(state.outsideLight.value?.unit == Illuminance.Unit.LUX)
        assertTrue(state.insideLight.value?.value == 0.25)
        assertTrue(state.insideLight.value?.unit == Illuminance.Unit.LUX)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetLightConditions() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "005400")
        val defaultGetter = LightConditions.GetLightConditions()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0054000102")
        val propertyGetter = LightConditions.GetLightConditions(0x01, 0x02)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102"))
    }
    
    @Test
    fun testGetLightConditionsAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "005402")
        val created = LightConditions.GetLightConditionsAvailability()
        assertTrue(created.identifier == Identifier.LIGHT_CONDITIONS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as LightConditions.GetLightConditionsAvailability
        assertTrue(resolved.identifier == Identifier.LIGHT_CONDITIONS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetLightConditionsAvailabilitySome() {
        val identifierBytes = Bytes("0102")
        val allBytes = Bytes(COMMAND_HEADER + "005402" + identifierBytes)
        val constructed = LightConditions.GetLightConditionsAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.LIGHT_CONDITIONS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = LightConditions.GetLightConditionsAvailability(0x01, 0x02)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as LightConditions.GetLightConditionsAvailability
        assertTrue(resolved.identifier == Identifier.LIGHT_CONDITIONS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}