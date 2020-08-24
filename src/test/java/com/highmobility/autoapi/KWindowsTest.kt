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
        assertTrue(state.getOpenPercentages()[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(state.getOpenPercentages()[0].value?.openPercentage == 0.2)
        assertTrue(state.getOpenPercentages()[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(state.getOpenPercentages()[1].value?.openPercentage == 0.5)
        assertTrue(state.getOpenPercentages()[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(state.getOpenPercentages()[2].value?.openPercentage == 0.5)
        assertTrue(state.getOpenPercentages()[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(state.getOpenPercentages()[3].value?.openPercentage == 0.1)
        assertTrue(state.getOpenPercentages()[4].value?.location == WindowLocation.HATCH)
        assertTrue(state.getOpenPercentages()[4].value?.openPercentage == 0.18)
        assertTrue(state.getPositions()[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(state.getPositions()[0].value?.position == WindowPosition.Position.OPEN)
        assertTrue(state.getPositions()[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(state.getPositions()[1].value?.position == WindowPosition.Position.OPEN)
        assertTrue(state.getPositions()[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(state.getPositions()[2].value?.position == WindowPosition.Position.CLOSED)
        assertTrue(state.getPositions()[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(state.getPositions()[3].value?.position == WindowPosition.Position.OPEN)
        assertTrue(state.getPositions()[4].value?.location == WindowLocation.HATCH)
        assertTrue(state.getPositions()[4].value?.position == WindowPosition.Position.OPEN)
    }
    
    @Test
    fun testGetWindows() {
        val bytes = Bytes(COMMAND_HEADER + "004500")
        assertTrue(Windows.GetWindows() == bytes)
    }
    
    @Test
    fun testGetWindowsProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0045000203")
        val getter = Windows.GetWindowsProperties(0x02, 0x03)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testControlWindows() {
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
    
        val constructed = Windows.ControlWindows(arrayOf(
            WindowOpenPercentage(WindowLocation.FRONT_LEFT, 0.2), 
            WindowOpenPercentage(WindowLocation.FRONT_RIGHT, 0.5), 
            WindowOpenPercentage(WindowLocation.REAR_RIGHT, 0.5), 
            WindowOpenPercentage(WindowLocation.REAR_LEFT, 0.1), 
            WindowOpenPercentage(WindowLocation.HATCH, 0.18)), 
            arrayOf(
            WindowPosition(WindowLocation.FRONT_LEFT, WindowPosition.Position.OPEN), 
            WindowPosition(WindowLocation.FRONT_RIGHT, WindowPosition.Position.OPEN), 
            WindowPosition(WindowLocation.REAR_RIGHT, WindowPosition.Position.CLOSED), 
            WindowPosition(WindowLocation.REAR_LEFT, WindowPosition.Position.OPEN), 
            WindowPosition(WindowLocation.HATCH, WindowPosition.Position.OPEN)))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Windows.ControlWindows
        assertTrue(resolved.getOpenPercentages()[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(resolved.getOpenPercentages()[0].value?.openPercentage == 0.2)
        assertTrue(resolved.getOpenPercentages()[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(resolved.getOpenPercentages()[1].value?.openPercentage == 0.5)
        assertTrue(resolved.getOpenPercentages()[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(resolved.getOpenPercentages()[2].value?.openPercentage == 0.5)
        assertTrue(resolved.getOpenPercentages()[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(resolved.getOpenPercentages()[3].value?.openPercentage == 0.1)
        assertTrue(resolved.getOpenPercentages()[4].value?.location == WindowLocation.HATCH)
        assertTrue(resolved.getOpenPercentages()[4].value?.openPercentage == 0.18)
        assertTrue(resolved.getPositions()[0].value?.location == WindowLocation.FRONT_LEFT)
        assertTrue(resolved.getPositions()[0].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved.getPositions()[1].value?.location == WindowLocation.FRONT_RIGHT)
        assertTrue(resolved.getPositions()[1].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved.getPositions()[2].value?.location == WindowLocation.REAR_RIGHT)
        assertTrue(resolved.getPositions()[2].value?.position == WindowPosition.Position.CLOSED)
        assertTrue(resolved.getPositions()[3].value?.location == WindowLocation.REAR_LEFT)
        assertTrue(resolved.getPositions()[3].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved.getPositions()[4].value?.location == WindowLocation.HATCH)
        assertTrue(resolved.getPositions()[4].value?.position == WindowPosition.Position.OPEN)
        assertTrue(resolved == bytes)
    }
}