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

import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KKeyfobPositionTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "004801" + 
            "01000401000105" // Keyfob is inside the vehicle
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as KeyfobPosition.State)
    }
    
    private fun testState(state: KeyfobPosition.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getLocation().value == KeyfobPosition.Location.INSIDE_CAR)
    }
    
    @Test
    fun testGetKeyfobPosition() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "004800")
        val defaultGetter = KeyfobPosition.GetKeyfobPosition()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
    }
    
    @Test
    fun testGetKeyfobPositionAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "004802")
        val created = KeyfobPosition.GetKeyfobPositionAvailability()
        assertTrue(created.identifier == Identifier.KEYFOB_POSITION)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as KeyfobPosition.GetKeyfobPositionAvailability
        assertTrue(resolved.identifier == Identifier.KEYFOB_POSITION)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
}