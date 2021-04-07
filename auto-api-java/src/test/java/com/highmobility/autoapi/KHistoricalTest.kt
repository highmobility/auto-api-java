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

class KHistoricalTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "001201" + 
            "01002C0100290c0020010600040100010004000501000200010400050100020201a2000b010008000001598938e788" +  // Doors capability - front left and rear right door is open while locks are unlocked, recorded at 10. January 2017 at 16:32:05 GMT
            "0100430100400c0023010b0004010001010c00040100010018000d01000a140240418000000000001c000d01000a12044081580000000000a2000b010008000001598938e788" // Charging capability - charging port is open, charge mode is immediate, charging rate is 35.0kW and max range is 555.0km, recorded at 10. January 2017 at 16:32:05 GMT
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Historical.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Historical.State.Builder()
        builder.addState(Property(CommandResolver.resolve("0c0020010600040100010004000501000200010400050100020201a2000b010008000001598938e788")))
        builder.addState(Property(CommandResolver.resolve("0c0023010b0004010001010c00040100010018000d01000a140240418000000000001c000d01000a12044081580000000000a2000b010008000001598938e788")))
        testState(builder.build())
    }
    
    private fun testState(state: Historical.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.states[0].value == CommandResolver.resolve("0c0020010600040100010004000501000200010400050100020201a2000b010008000001598938e788"))
        assertTrue(state.states[1].value == CommandResolver.resolve("0c0023010b0004010001010c00040100010018000d01000a140240418000000000001c000d01000a12044081580000000000a2000b010008000001598938e788"))
    }
    
    @Test
    fun requestStates() {
        val bytes = Bytes(COMMAND_HEADER + "001201" +
            "0200050100020060" +
            "03000B0100080000016da6524300" +
            "04000B0100080000016d71e2c4f0")
    
        val constructed = Historical.RequestStates(0x0060, getCalendar("2019-10-07T13:04:32.000Z"), getCalendar("2019-09-27T08:42:30.000Z"))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Historical.RequestStates
        assertTrue(resolved.capabilityID.value == 0x0060)
        assertTrue(dateIsSame(resolved.startDate.value, "2019-10-07T13:04:32.000Z"))
        assertTrue(dateIsSame(resolved.endDate.value, "2019-09-27T08:42:30.000Z"))
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun getTrips() {
        val bytes = Bytes(COMMAND_HEADER + "001201" +
            "020005010002006a" +
            "03000B0100080000016da6524300" +
            "04000B0100080000016d71e2c4f0")
    
        val constructed = Historical.GetTrips(getCalendar("2019-10-07T13:04:32.000Z"), getCalendar("2019-09-27T08:42:30.000Z"))
        assertTrue(bytesTheSame(constructed, bytes))
    }
    
    @Test
    fun getChargingSessions() {
        val bytes = Bytes(COMMAND_HEADER + "001201" +
            "020005010002006d" +
            "03000B0100080000016da6524300" +
            "04000B0100080000016d71e2c4f0")
    
        val constructed = Historical.GetChargingSessions(getCalendar("2019-10-07T13:04:32.000Z"), getCalendar("2019-09-27T08:42:30.000Z"))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Historical.GetChargingSessions
        assertTrue(dateIsSame(resolved.startDate.value, "2019-10-07T13:04:32.000Z"))
        assertTrue(dateIsSame(resolved.endDate.value, "2019-09-27T08:42:30.000Z"))
        assertTrue(resolved == bytes)
    }
    
    @Test fun invalidGetChargingSessionsCapabilityIDThrows() {
        val bytes = Bytes(COMMAND_HEADER + "001201" +
            "02000501000200CD" +
            "03000B0100080000016da6524300" +
            "04000B0100080000016d71e2c4f0")
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        errorLogExpected { 
            val resolved = CommandResolver.resolve(bytes)
            assertTrue(resolved is Command)
        }
    }
}