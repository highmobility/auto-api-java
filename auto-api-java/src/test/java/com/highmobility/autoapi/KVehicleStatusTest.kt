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
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KVehicleStatusTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "001101" + 
            "99002C0100290d0020010600040100010004000501000200010400050100020201a2000b010008000001598938e788" +  // Doors capability - front left and rear right door is open while locks are unlocked, recorded at 10. January 2017 at 16:32:05 GMT
            "9900430100400d0023010b0004010001010c00040100010018000d01000a140240418000000000001c000d01000a12044081580000000000a2000b010008000001598938e788" // Charging capability - charging port is open, charge mode is immediate, charging rate is 35.0kW and max range is 555.0km, recorded at 10. January 2017 at 16:32:05 GMT
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as VehicleStatus.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = VehicleStatus.State.Builder()
        builder.addState(Property(CommandResolver.resolve("0d0020010600040100010004000501000200010400050100020201a2000b010008000001598938e788")))
        builder.addState(Property(CommandResolver.resolve("0d0023010b0004010001010c00040100010018000d01000a140240418000000000001c000d01000a12044081580000000000a2000b010008000001598938e788")))
        testState(builder.build())
    }
    
    private fun testState(state: VehicleStatus.State) {
        assertTrue(state.states[0].value == CommandResolver.resolve("0d0020010600040100010004000501000200010400050100020201a2000b010008000001598938e788"))
        assertTrue(state.states[1].value == CommandResolver.resolve("0d0023010b0004010001010c00040100010018000d01000a140240418000000000001c000d01000a12044081580000000000a2000b010008000001598938e788"))
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetVehicleStatus() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "001100")
        val defaultGetter = VehicleStatus.GetVehicleStatus()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
    }
}