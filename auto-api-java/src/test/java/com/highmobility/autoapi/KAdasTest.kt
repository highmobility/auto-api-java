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

class KAdasTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006c01" + 
            "01000401000101" +  // ADAS is on
            "02000401000101" +  // Driver alertness warning system is active.
            "03000401000101" +  // Forward collision warning system is active.
            "04000401000101" +  // Blind spot warning is active.
            "05000401000100" +  // Blind spot warning system coverage is regular.
            "06000401000101" +  // Rear cross warning system is active.
            "07000401000101" +  // Automated parking brake is active.
            "08000401000101" +  // Lane keep assist system is turned on.
            "0900050100020000" +  // Left lane keeping assist is not actively controlling the wheels.
            "0900050100020101" +  // Right lane keeping assist is actively controlling the wheels.
            "0a0006010003000000" +  // Front park assist is inactive and not muted.
            "0a0006010003010100" +  // Rear park assist is active and not muted.
            "0b000401000101" +  // Blind spot warning system is turned on.
            "0c000401000101" // Launch control is active.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Adas.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Adas.State.Builder()
        builder.setStatus(Property(OnOffState.ON))
        builder.setAlertnessSystemStatus(Property(ActiveState.ACTIVE))
        builder.setForwardCollisionWarningSystem(Property(ActiveState.ACTIVE))
        builder.setBlindSpotWarningState(Property(ActiveState.ACTIVE))
        builder.setBlindSpotWarningSystemCoverage(Property(Adas.BlindSpotWarningSystemCoverage.REGULAR))
        builder.setRearCrossWarningSystem(Property(ActiveState.ACTIVE))
        builder.setAutomatedParkingBrake(Property(ActiveState.ACTIVE))
        builder.setLaneKeepAssistSystem(Property(OnOffState.ON))
        builder.addLaneKeepAssistsState(Property(LaneKeepAssistState(LaneKeepAssistState.Location.LEFT, ActiveState.INACTIVE)))
        builder.addLaneKeepAssistsState(Property(LaneKeepAssistState(LaneKeepAssistState.Location.RIGHT, ActiveState.ACTIVE)))
        builder.addParkAssist(Property(ParkAssist(LocationLongitudinal.FRONT, ActiveState.INACTIVE, Muted.NOT_MUTED)))
        builder.addParkAssist(Property(ParkAssist(LocationLongitudinal.REAR, ActiveState.ACTIVE, Muted.NOT_MUTED)))
        builder.setBlindSpotWarningSystem(Property(OnOffState.ON))
        builder.setLaunchControl(Property(ActiveState.ACTIVE))
        testState(builder.build())
    }
    
    private fun testState(state: Adas.State) {
        assertTrue(state.status.value == OnOffState.ON)
        assertTrue(state.alertnessSystemStatus.value == ActiveState.ACTIVE)
        assertTrue(state.forwardCollisionWarningSystem.value == ActiveState.ACTIVE)
        assertTrue(state.blindSpotWarningState.value == ActiveState.ACTIVE)
        assertTrue(state.blindSpotWarningSystemCoverage.value == Adas.BlindSpotWarningSystemCoverage.REGULAR)
        assertTrue(state.rearCrossWarningSystem.value == ActiveState.ACTIVE)
        assertTrue(state.automatedParkingBrake.value == ActiveState.ACTIVE)
        assertTrue(state.laneKeepAssistSystem.value == OnOffState.ON)
        assertTrue(state.laneKeepAssistsStates[0].value?.location == LaneKeepAssistState.Location.LEFT)
        assertTrue(state.laneKeepAssistsStates[0].value?.state == ActiveState.INACTIVE)
        assertTrue(state.laneKeepAssistsStates[1].value?.location == LaneKeepAssistState.Location.RIGHT)
        assertTrue(state.laneKeepAssistsStates[1].value?.state == ActiveState.ACTIVE)
        assertTrue(state.parkAssists[0].value?.location == LocationLongitudinal.FRONT)
        assertTrue(state.parkAssists[0].value?.alarm == ActiveState.INACTIVE)
        assertTrue(state.parkAssists[0].value?.muted == Muted.NOT_MUTED)
        assertTrue(state.parkAssists[1].value?.location == LocationLongitudinal.REAR)
        assertTrue(state.parkAssists[1].value?.alarm == ActiveState.ACTIVE)
        assertTrue(state.parkAssists[1].value?.muted == Muted.NOT_MUTED)
        assertTrue(state.blindSpotWarningSystem.value == OnOffState.ON)
        assertTrue(state.launchControl.value == ActiveState.ACTIVE)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006c00")
        val defaultGetter = Adas.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "006c000102030405060708090a0b0c")
        val propertyGetter = Adas.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405060708090a0b0c"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006c02")
        val created = Adas.GetStateAvailability()
        assertTrue(created.identifier == Identifier.ADAS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Adas.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.ADAS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102030405060708090a0b0c")
        val allBytes = Bytes(COMMAND_HEADER + "006c02" + identifierBytes)
        val constructed = Adas.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.ADAS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Adas.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Adas.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.ADAS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}