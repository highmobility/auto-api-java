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

class KLightsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003601" + 
            "01000401000102" +  // Front exterior lights are active with full beam
            "02000401000101" +  // Rear exterior lights are active
            "040006010003ff0000" +  // Ambient light is red
            "05000401000100" +  // Reverse light is inactive
            "06000401000100" +  // Emergency brake light is inactive
            "0700050100020000" +  // Front fog lights are inactive
            "0700050100020101" +  // Rear fog lights are active
            "0800050100020001" +  // Front left reading lamp is active
            "0800050100020101" +  // Front right reading lamp is active
            "0800050100020200" +  // Rear right reading lamp is inactive
            "0800050100020300" +  // Rear left reading lamp is inactive
            "0900050100020000" +  // Front interior lights are inactive
            "0900050100020101" +  // Rear reading lights are active
            "0a000401000102" // Rotary light switch position parking light right
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Lights.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Lights.State.Builder()
        builder.setFrontExteriorLight(Property(Lights.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM))
        builder.setRearExteriorLight(Property(ActiveState.ACTIVE))
        builder.setAmbientLightColour(Property(RgbColour(255, 0, 0)))
        builder.setReverseLight(Property(ActiveState.INACTIVE))
        builder.setEmergencyBrakeLight(Property(ActiveState.INACTIVE))
        builder.addFogLight(Property(Light(LocationLongitudinal.FRONT, ActiveState.INACTIVE)))
        builder.addFogLight(Property(Light(LocationLongitudinal.REAR, ActiveState.ACTIVE)))
        builder.addReadingLamp(Property(ReadingLamp(Location.FRONT_LEFT, ActiveState.ACTIVE)))
        builder.addReadingLamp(Property(ReadingLamp(Location.FRONT_RIGHT, ActiveState.ACTIVE)))
        builder.addReadingLamp(Property(ReadingLamp(Location.REAR_RIGHT, ActiveState.INACTIVE)))
        builder.addReadingLamp(Property(ReadingLamp(Location.REAR_LEFT, ActiveState.INACTIVE)))
        builder.addInteriorLight(Property(Light(LocationLongitudinal.FRONT, ActiveState.INACTIVE)))
        builder.addInteriorLight(Property(Light(LocationLongitudinal.REAR, ActiveState.ACTIVE)))
        builder.setSwitchPosition(Property(Lights.SwitchPosition.PARKING_LIGHT_RIGHT))
        testState(builder.build())
    }
    
    private fun testState(state: Lights.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getFrontExteriorLight().value == Lights.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM)
        assertTrue(state.getRearExteriorLight().value == ActiveState.ACTIVE)
        assertTrue(state.getAmbientLightColour().value?.red == 255)
        assertTrue(state.getAmbientLightColour().value?.green == 0)
        assertTrue(state.getAmbientLightColour().value?.blue == 0)
        assertTrue(state.getReverseLight().value == ActiveState.INACTIVE)
        assertTrue(state.getEmergencyBrakeLight().value == ActiveState.INACTIVE)
        assertTrue(state.getFogLights()[0].value?.location == LocationLongitudinal.FRONT)
        assertTrue(state.getFogLights()[0].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getFogLights()[1].value?.location == LocationLongitudinal.REAR)
        assertTrue(state.getFogLights()[1].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getReadingLamps()[0].value?.location == Location.FRONT_LEFT)
        assertTrue(state.getReadingLamps()[0].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getReadingLamps()[1].value?.location == Location.FRONT_RIGHT)
        assertTrue(state.getReadingLamps()[1].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getReadingLamps()[2].value?.location == Location.REAR_RIGHT)
        assertTrue(state.getReadingLamps()[2].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getReadingLamps()[3].value?.location == Location.REAR_LEFT)
        assertTrue(state.getReadingLamps()[3].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getInteriorLights()[0].value?.location == LocationLongitudinal.FRONT)
        assertTrue(state.getInteriorLights()[0].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getInteriorLights()[1].value?.location == LocationLongitudinal.REAR)
        assertTrue(state.getInteriorLights()[1].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getSwitchPosition().value == Lights.SwitchPosition.PARKING_LIGHT_RIGHT)
    }
    
    @Test fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "003600")
        val defaultGetter = Lights.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "00360001020405060708090a")
        val propertyGetter = Lights.GetState(0x01, 0x02, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("01020405060708090a"))
    }
    
    @Test fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "003602")
        val created = Lights.GetStateAvailability()
        assertTrue(created.identifier == Identifier.LIGHTS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Lights.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("01020405060708090a")
        val allBytes = Bytes(COMMAND_HEADER + "003602" + identifierBytes)
        val constructed = Lights.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.LIGHTS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Lights.GetStateAvailability(0x01, 0x02, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Lights.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test fun controlLights() {
        val bytes = Bytes(COMMAND_HEADER + "003601" +
            "01000401000102" +
            "02000401000101" +
            "040006010003ff0000" +
            "0700050100020000" +
            "0700050100020101" +
            "0800050100020001" +
            "0800050100020101" +
            "0800050100020200" +
            "0800050100020300" +
            "0900050100020000" +
            "0900050100020101")
    
        val constructed = Lights.ControlLights(Lights.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM, ActiveState.ACTIVE, RgbColour(255, 0, 0), 
            arrayListOf(
            Light(LocationLongitudinal.FRONT, ActiveState.INACTIVE), 
            Light(LocationLongitudinal.REAR, ActiveState.ACTIVE)), 
            arrayListOf(
            ReadingLamp(Location.FRONT_LEFT, ActiveState.ACTIVE), 
            ReadingLamp(Location.FRONT_RIGHT, ActiveState.ACTIVE), 
            ReadingLamp(Location.REAR_RIGHT, ActiveState.INACTIVE), 
            ReadingLamp(Location.REAR_LEFT, ActiveState.INACTIVE)), 
            arrayListOf(
            Light(LocationLongitudinal.FRONT, ActiveState.INACTIVE), 
            Light(LocationLongitudinal.REAR, ActiveState.ACTIVE)))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Lights.ControlLights
        assertTrue(resolved.getFrontExteriorLight().value == Lights.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM)
        assertTrue(resolved.getRearExteriorLight().value == ActiveState.ACTIVE)
        assertTrue(resolved.getAmbientLightColour().value?.red == 255)
        assertTrue(resolved.getAmbientLightColour().value?.green == 0)
        assertTrue(resolved.getAmbientLightColour().value?.blue == 0)
        assertTrue(resolved.getFogLights()[0].value?.location == LocationLongitudinal.FRONT)
        assertTrue(resolved.getFogLights()[0].value?.state == ActiveState.INACTIVE)
        assertTrue(resolved.getFogLights()[1].value?.location == LocationLongitudinal.REAR)
        assertTrue(resolved.getFogLights()[1].value?.state == ActiveState.ACTIVE)
        assertTrue(resolved.getReadingLamps()[0].value?.location == Location.FRONT_LEFT)
        assertTrue(resolved.getReadingLamps()[0].value?.state == ActiveState.ACTIVE)
        assertTrue(resolved.getReadingLamps()[1].value?.location == Location.FRONT_RIGHT)
        assertTrue(resolved.getReadingLamps()[1].value?.state == ActiveState.ACTIVE)
        assertTrue(resolved.getReadingLamps()[2].value?.location == Location.REAR_RIGHT)
        assertTrue(resolved.getReadingLamps()[2].value?.state == ActiveState.INACTIVE)
        assertTrue(resolved.getReadingLamps()[3].value?.location == Location.REAR_LEFT)
        assertTrue(resolved.getReadingLamps()[3].value?.state == ActiveState.INACTIVE)
        assertTrue(resolved.getInteriorLights()[0].value?.location == LocationLongitudinal.FRONT)
        assertTrue(resolved.getInteriorLights()[0].value?.state == ActiveState.INACTIVE)
        assertTrue(resolved.getInteriorLights()[1].value?.location == LocationLongitudinal.REAR)
        assertTrue(resolved.getInteriorLights()[1].value?.state == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
}