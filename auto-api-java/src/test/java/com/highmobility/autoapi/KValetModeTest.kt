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

class KValetModeTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002801" + 
            "01000401000101" // Valet mode is active
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as ValetMode.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = ValetMode.State.Builder()
        builder.setStatus(Property(ActiveState.ACTIVE))
        testState(builder.build())
    }
    
    private fun testState(state: ValetMode.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.status.value == ActiveState.ACTIVE)
    }
    
    @Test
    fun testGetValetMode() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002800")
        val defaultGetter = ValetMode.GetValetMode()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
    }
    
    @Test
    fun testGetValetModeAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002802")
        val created = ValetMode.GetValetModeAvailability()
        assertTrue(created.identifier == Identifier.VALET_MODE)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ValetMode.GetValetModeAvailability
        assertTrue(resolved.identifier == Identifier.VALET_MODE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun activateDeactivateValetMode() {
        val bytes = Bytes(COMMAND_HEADER + "002801" +
            "01000401000101")
    
        val constructed = ValetMode.ActivateDeactivateValetMode(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ValetMode.ActivateDeactivateValetMode
        assertTrue(resolved.status.value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
}