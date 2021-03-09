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

import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KRemoteControlTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002701" + 
            "01000401000102" +  // Remote control is started
            "02000D01000A02004049000000000000" // Angle is 50.0°
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as RemoteControl.State)
    }
    
    private fun testState(state: RemoteControl.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.controlMode.value == RemoteControl.ControlMode.STARTED)
        assertTrue(state.angle.value?.value == 50.0)
        assertTrue(state.angle.value?.unit == Angle.Unit.DEGREES)
    }
    
    @Test
    fun testGetControlState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002700")
        val defaultGetter = RemoteControl.GetControlState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
    }
    
    @Test
    fun testGetControlStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002702")
        val created = RemoteControl.GetControlStateAvailability()
        assertTrue(created.identifier == Identifier.REMOTE_CONTROL)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as RemoteControl.GetControlStateAvailability
        assertTrue(resolved.identifier == Identifier.REMOTE_CONTROL)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun controlCommand() {
        val bytes = Bytes(COMMAND_HEADER + "002701" +
            "02000D01000A02004049000000000000" +
            "03000D01000A16014014000000000000")
    
        val constructed = RemoteControl.ControlCommand(Angle(50.0, Angle.Unit.DEGREES), Speed(5.0, Speed.Unit.KILOMETERS_PER_HOUR))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as RemoteControl.ControlCommand
        assertTrue(resolved.angle.value?.value == 50.0)
        assertTrue(resolved.angle.value?.unit == Angle.Unit.DEGREES)
        assertTrue(resolved.speed.value?.value == 5.0)
        assertTrue(resolved.speed.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun startControl() {
        val bytes = Bytes(COMMAND_HEADER + "002701" +
            "01000401000102")
    
        val constructed = RemoteControl.StartControl()
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as RemoteControl.StartControl
        assertTrue(resolved == bytes)
    }
    
    @Test fun invalidStartControlControlModeThrows() {
        val bytes = Bytes(COMMAND_HEADER + "002701" +
            "010004010001CD")
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        debugLogExpected(2) { 
            val resolved = CommandResolver.resolve(bytes)
            assertTrue(resolved is Command)
        }
    }
    
    @Test
    fun stopControl() {
        val bytes = Bytes(COMMAND_HEADER + "002701" +
            "01000401000105")
    
        val constructed = RemoteControl.StopControl()
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as RemoteControl.StopControl
        assertTrue(resolved == bytes)
    }
    
    @Test fun invalidStopControlControlModeThrows() {
        val bytes = Bytes(COMMAND_HEADER + "002701" +
            "010004010001CD")
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        debugLogExpected(2) { 
            val resolved = CommandResolver.resolve(bytes)
            assertTrue(resolved is Command)
        }
    }
}