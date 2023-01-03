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

class KWindscreenTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "004201" + 
            "01000401000102" +  // Windscreen wipers are set to automatic mode
            "02000401000103" +  // Wipers are on highest intensity, indicating heavy rain
            "03000401000101" +  // Windscreen detected an impact, but no damage
            "0400050100020403" +  // Windscreen is divided into a matrix 4 columns horizontally by 3 rows vertically
            "0500050100020102" +  // Damage is detected in the 1st column from left and the 2nd row from the top
            "06000401000101" +  // Windscreen does not need replacement
            "07000B0100083fee666666666666" +  // Damage detected with 95% confidence
            "08000B010008000001598938e788" // Windscreen damage detected at 10 January 2017 at 16:32:05 UTC
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Windscreen.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Windscreen.State.Builder()
        builder.setWipersStatus(Property(Windscreen.WipersStatus.AUTOMATIC))
        builder.setWipersIntensity(Property(Windscreen.WipersIntensity.LEVEL_3))
        builder.setWindscreenDamage(Property(Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED))
        builder.setWindscreenZoneMatrix(Property(Zone(4, 3)))
        builder.setWindscreenDamageZone(Property(Zone(1, 2)))
        builder.setWindscreenNeedsReplacement(Property(Windscreen.WindscreenNeedsReplacement.NO_REPLACEMENT_NEEDED))
        builder.setWindscreenDamageConfidence(Property(0.95))
        builder.setWindscreenDamageDetectionTime(Property(getCalendar("2017-01-10T16:32:05.000Z")))
        testState(builder.build())
    }
    
    private fun testState(state: Windscreen.State) {
        assertTrue(state.wipersStatus.value == Windscreen.WipersStatus.AUTOMATIC)
        assertTrue(state.wipersIntensity.value == Windscreen.WipersIntensity.LEVEL_3)
        assertTrue(state.windscreenDamage.value == Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED)
        assertTrue(state.windscreenZoneMatrix.value?.horizontal == 4)
        assertTrue(state.windscreenZoneMatrix.value?.vertical == 3)
        assertTrue(state.windscreenDamageZone.value?.horizontal == 1)
        assertTrue(state.windscreenDamageZone.value?.vertical == 2)
        assertTrue(state.windscreenNeedsReplacement.value == Windscreen.WindscreenNeedsReplacement.NO_REPLACEMENT_NEEDED)
        assertTrue(state.windscreenDamageConfidence.value == 0.95)
        assertTrue(dateIsSame(state.windscreenDamageDetectionTime.value, "2017-01-10T16:32:05.000Z"))
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "004200")
        val defaultGetter = Windscreen.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0042000102030405060708")
        val propertyGetter = Windscreen.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405060708"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "004202")
        val created = Windscreen.GetStateAvailability()
        assertTrue(created.identifier == Identifier.WINDSCREEN)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Windscreen.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.WINDSCREEN)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102030405060708")
        val allBytes = Bytes(COMMAND_HEADER + "004202" + identifierBytes)
        val constructed = Windscreen.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.WINDSCREEN)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Windscreen.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Windscreen.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.WINDSCREEN)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun setWindscreenDamage() {
        val bytes = Bytes(COMMAND_HEADER + "004201" +
            "03000401000101" +
            "0500050100020102")
    
        val constructed = Windscreen.SetWindscreenDamage(Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED, Zone(1, 2))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Windscreen.SetWindscreenDamage
        assertTrue(resolved.windscreenDamage.value == Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED)
        assertTrue(resolved.windscreenDamageZone.value?.horizontal == 1)
        assertTrue(resolved.windscreenDamageZone.value?.vertical == 2)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setWindscreenReplacementNeeded() {
        val bytes = Bytes(COMMAND_HEADER + "004201" +
            "06000401000101")
    
        val constructed = Windscreen.SetWindscreenReplacementNeeded(Windscreen.WindscreenNeedsReplacement.NO_REPLACEMENT_NEEDED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Windscreen.SetWindscreenReplacementNeeded
        assertTrue(resolved.windscreenNeedsReplacement.value == Windscreen.WindscreenNeedsReplacement.NO_REPLACEMENT_NEEDED)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun controlWipers() {
        val bytes = Bytes(COMMAND_HEADER + "004201" +
            "01000401000102" +
            "02000401000103")
    
        val constructed = Windscreen.ControlWipers(Windscreen.WipersStatus.AUTOMATIC, Windscreen.WipersIntensity.LEVEL_3)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Windscreen.ControlWipers
        assertTrue(resolved.wipersStatus.value == Windscreen.WipersStatus.AUTOMATIC)
        assertTrue(resolved.wipersIntensity.value == Windscreen.WipersIntensity.LEVEL_3)
        assertTrue(resolved == bytes)
    }
}