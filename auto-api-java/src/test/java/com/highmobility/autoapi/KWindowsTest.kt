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

class KWindowsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "004501" + 
            "02000C010009003fc999999999999a" +  // Front left window is 20% open
            "02000C010009013fe0000000000000" +  // Front right window is 50% open
            "02000C010009023fe0000000000000" +  // Rear right window is 50% open
            "02000C010009033fb999999999999a" +  // Rear left window is 10% open
            "02000C010009043fc70a3d70a3d70a" +  // Hatch is 18% open
            "0300050100020001" +  // Front left window is open
            "0300050100020101" +  // Front right window is open
            "0300050100020200" +  // Rear right window is closed
            "0300050100020301" +  // Rear left window is open
            "0300050100020401" // Hatch is open
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Windows.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Windows.State.Builder()
        builder.addOpenPercentage(Property(WindowOpenPercentage(WindowLocation.FRONT_LEFT, 0.2)))
        builder.addOpenPercentage(Property(WindowOpenPercentage(WindowLocation.FRONT_RIGHT, 0.5)))
        builder.addOpenPercentage(Property(WindowOpenPercentage(WindowLocation.REAR_RIGHT, 0.5)))
        builder.addOpenPercentage(Property(WindowOpenPercentage(WindowLocation.REAR_LEFT, 0.1)))
        builder.addOpenPercentage(Property(WindowOpenPercentage(WindowLocation.HATCH, 0.18)))
        builder.addPosition(Property(WindowPosition(WindowLocation.FRONT_LEFT, WindowPosition.Position.OPEN)))
        builder.addPosition(Property(WindowPosition(WindowLocation.FRONT_RIGHT, WindowPosition.Position.OPEN)))
        builder.addPosition(Property(WindowPosition(WindowLocation.REAR_RIGHT, WindowPosition.Position.CLOSED)))
        builder.addPosition(Property(WindowPosition(WindowLocation.REAR_LEFT, WindowPosition.Position.OPEN)))
        builder.addPosition(Property(WindowPosition(WindowLocation.HATCH, WindowPosition.Position.OPEN)))
        testState(builder.build())
    }
    
    private fun testState(state: Windows.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.openPercentages[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(state.openPercentages[0].value?.openPercentage == 0.2)
        assertTrue(state.openPercentages[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(state.openPercentages[1].value?.openPercentage == 0.5)
        assertTrue(state.openPercentages[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(state.openPercentages[2].value?.openPercentage == 0.5)
        assertTrue(state.openPercentages[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(state.openPercentages[3].value?.openPercentage == 0.1)
        assertTrue(state.openPercentages[4].value?.location == WindowLocation.HATCH)
        assertTrue(state.openPercentages[4].value?.openPercentage == 0.18)
        assertTrue(state.positions[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(state.positions[0].value?.position == WindowPosition.Position.OPEN)
        assertTrue(state.positions[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(state.positions[1].value?.position == WindowPosition.Position.OPEN)
        assertTrue(state.positions[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(state.positions[2].value?.position == WindowPosition.Position.CLOSED)
        assertTrue(state.positions[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(state.positions[3].value?.position == WindowPosition.Position.OPEN)
        assertTrue(state.positions[4].value?.location == WindowLocation.HATCH)
        assertTrue(state.positions[4].value?.position == WindowPosition.Position.OPEN)
    }
    
    @Test
    fun testGetWindows() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "004500")
        val defaultGetter = Windows.GetWindows()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0045000203")
        val propertyGetter = Windows.GetWindows(0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0203"))
    }
    
    @Test
    fun testGetWindowsAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "004502")
        val created = Windows.GetWindowsAvailability()
        assertTrue(created.identifier == Identifier.WINDOWS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Windows.GetWindowsAvailability
        assertTrue(resolved.identifier == Identifier.WINDOWS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetWindowsAvailabilitySome() {
        val identifierBytes = Bytes("0203")
        val allBytes = Bytes(COMMAND_HEADER + "004502" + identifierBytes)
        val constructed = Windows.GetWindowsAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.WINDOWS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Windows.GetWindowsAvailability(0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Windows.GetWindowsAvailability
        assertTrue(resolved.identifier == Identifier.WINDOWS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun controlWindows() {
        val bytes = Bytes(COMMAND_HEADER + "004501" +
            "02000C010009003fc999999999999a" +
            "02000C010009013fe0000000000000" +
            "02000C010009023fe0000000000000" +
            "02000C010009033fb999999999999a" +
            "02000C010009043fc70a3d70a3d70a" +
            "0300050100020001" +
            "0300050100020101" +
            "0300050100020200" +
            "0300050100020301" +
            "0300050100020401")
    
        val constructed = Windows.ControlWindows(arrayListOf(
                WindowOpenPercentage(WindowLocation.FRONT_LEFT, 0.2), 
                WindowOpenPercentage(WindowLocation.FRONT_RIGHT, 0.5), 
                WindowOpenPercentage(WindowLocation.REAR_RIGHT, 0.5), 
                WindowOpenPercentage(WindowLocation.REAR_LEFT, 0.1), 
                WindowOpenPercentage(WindowLocation.HATCH, 0.18))
            , 
            arrayListOf(
                WindowPosition(WindowLocation.FRONT_LEFT, WindowPosition.Position.OPEN), 
                WindowPosition(WindowLocation.FRONT_RIGHT, WindowPosition.Position.OPEN), 
                WindowPosition(WindowLocation.REAR_RIGHT, WindowPosition.Position.CLOSED), 
                WindowPosition(WindowLocation.REAR_LEFT, WindowPosition.Position.OPEN), 
                WindowPosition(WindowLocation.HATCH, WindowPosition.Position.OPEN))
            )
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Windows.ControlWindows
        assertTrue(resolved.openPercentages[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(resolved.openPercentages[0].value?.openPercentage == 0.2)
        assertTrue(resolved.openPercentages[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(resolved.openPercentages[1].value?.openPercentage == 0.5)
        assertTrue(resolved.openPercentages[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(resolved.openPercentages[2].value?.openPercentage == 0.5)
        assertTrue(resolved.openPercentages[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(resolved.openPercentages[3].value?.openPercentage == 0.1)
        assertTrue(resolved.openPercentages[4].value?.location == WindowLocation.HATCH)
        assertTrue(resolved.openPercentages[4].value?.openPercentage == 0.18)
        assertTrue(resolved.positions[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(resolved.positions[0].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved.positions[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(resolved.positions[1].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved.positions[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(resolved.positions[2].value?.position == WindowPosition.Position.CLOSED)
        assertTrue(resolved.positions[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(resolved.positions[3].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved.positions[4].value?.location == WindowLocation.HATCH)
        assertTrue(resolved.positions[4].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved == bytes)
    }
}