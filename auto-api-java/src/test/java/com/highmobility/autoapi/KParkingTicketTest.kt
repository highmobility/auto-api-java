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

class KParkingTicketTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "004701" + 
            "01000401000100" +  // Parking ticket has ended
            "02001101000E4265726c696e205061726b696e67" +  // Operator name is 'Berlin Parking'
            "03000D01000A36343839414234323333" +  // Operator ticket ID is '6489AB4233'
            "04000B0100080000015989dfca30" +  // Parking ticket started at 10 January 2017 at 19:34:22 GMT
            "05000B0100080000016dab1a8528" // Parking ticket ended at 8. October 2019 at 11:21:45 GMT
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as ParkingTicket.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = ParkingTicket.State.Builder()
        builder.setStatus(Property(ParkingTicket.Status.ENDED))
        builder.setOperatorName(Property("Berlin Parking"))
        builder.setOperatorTicketID(Property("6489AB4233"))
        builder.setTicketStartTime(Property(getCalendar("2017-01-10T19:34:22.000Z")))
        builder.setTicketEndTime(Property(getCalendar("2019-10-08T11:21:45.000Z")))
        testState(builder.build())
    }
    
    private fun testState(state: ParkingTicket.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.status.value == ParkingTicket.Status.ENDED)
        assertTrue(state.operatorName.value == "Berlin Parking")
        assertTrue(state.operatorTicketID.value == "6489AB4233")
        assertTrue(dateIsSame(state.ticketStartTime.value, "2017-01-10T19:34:22.000Z"))
        assertTrue(dateIsSame(state.ticketEndTime.value, "2019-10-08T11:21:45.000Z"))
    }
    
    @Test
    fun testGetParkingTicket() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "004700")
        val defaultGetter = ParkingTicket.GetParkingTicket()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0047000102030405")
        val propertyGetter = ParkingTicket.GetParkingTicket(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405"))
    }
    
    @Test
    fun testGetParkingTicketAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "004702")
        val created = ParkingTicket.GetParkingTicketAvailability()
        assertTrue(created.identifier == Identifier.PARKING_TICKET)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ParkingTicket.GetParkingTicketAvailability
        assertTrue(resolved.identifier == Identifier.PARKING_TICKET)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetParkingTicketAvailabilitySome() {
        val identifierBytes = Bytes("0102030405")
        val allBytes = Bytes(COMMAND_HEADER + "004702" + identifierBytes)
        val constructed = ParkingTicket.GetParkingTicketAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.PARKING_TICKET)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = ParkingTicket.GetParkingTicketAvailability(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as ParkingTicket.GetParkingTicketAvailability
        assertTrue(resolved.identifier == Identifier.PARKING_TICKET)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun startParking() {
        val bytes = Bytes(COMMAND_HEADER + "004701" +
            "01000401000101" +
            "02001101000E4265726c696e205061726b696e67" +
            "03000D01000A36343839414234323333" +
            "04000B0100080000015989dfca30" +
            "05000B0100080000016dab1a8528")
    
        val constructed = ParkingTicket.StartParking("Berlin Parking", "6489AB4233", getCalendar("2017-01-10T19:34:22.000Z"), getCalendar("2019-10-08T11:21:45.000Z"))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ParkingTicket.StartParking
        assertTrue(resolved.operatorName.value == "Berlin Parking")
        assertTrue(resolved.operatorTicketID.value == "6489AB4233")
        assertTrue(dateIsSame(resolved.ticketStartTime.value, "2017-01-10T19:34:22.000Z"))
        assertTrue(dateIsSame(resolved.ticketEndTime.value, "2019-10-08T11:21:45.000Z"))
        assertTrue(resolved == bytes)
    }
    
    @Test fun invalidStartParkingStatusThrows() {
        val bytes = Bytes(COMMAND_HEADER + "004701" +
            "010004010001CD" +
            "02001101000E4265726c696e205061726b696e67" +
            "03000D01000A36343839414234323333" +
            "04000B0100080000015989dfca30" +
            "05000B0100080000016dab1a8528")
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        debugLogExpected(2) { 
            val resolved = CommandResolver.resolve(bytes)
            assertTrue(resolved is Command)
        }
    }
    
    @Test
    fun endParking() {
        val bytes = Bytes(COMMAND_HEADER + "004701" +
            "01000401000100")
    
        val constructed = ParkingTicket.EndParking()
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ParkingTicket.EndParking
        assertTrue(resolved == bytes)
    }
    
    @Test fun invalidEndParkingStatusThrows() {
        val bytes = Bytes(COMMAND_HEADER + "004701" +
            "010004010001CD")
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        debugLogExpected(2) { 
            val resolved = CommandResolver.resolve(bytes)
            assertTrue(resolved is Command)
        }
    }
}