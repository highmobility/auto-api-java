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

class KSeatsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005601" + 
            "0200050100020001" +  // Person detected on the front-left seat
            "0200050100020100" +  // No person detected on the front-right seat
            "0200050100020200" +  // No person detected on the rear-right seat
            "0200050100020300" +  // No person detected on the rear-left seat
            "0200050100020400" +  // No person detected on the rear-center seat
            "0300050100020001" +  // Seatbelt fastened for the front-left seat
            "0300050100020100" +  // Seatbelt not fastened for the front-right seat
            "0300050100020200" +  // Seatbelt not fastened for the rear-right seat
            "0300050100020300" +  // Seatbelt not fastened for the rear-left seat
            "0300050100020400" // Seatbelt not fastened for the rear-center seat
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Seats.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Seats.State.Builder()
        builder.addPersonDetected(Property(PersonDetected(SeatLocation.FRONT_LEFT, Detected.DETECTED)))
        builder.addPersonDetected(Property(PersonDetected(SeatLocation.FRONT_RIGHT, Detected.NOT_DETECTED)))
        builder.addPersonDetected(Property(PersonDetected(SeatLocation.REAR_RIGHT, Detected.NOT_DETECTED)))
        builder.addPersonDetected(Property(PersonDetected(SeatLocation.REAR_LEFT, Detected.NOT_DETECTED)))
        builder.addPersonDetected(Property(PersonDetected(SeatLocation.REAR_CENTER, Detected.NOT_DETECTED)))
        builder.addSeatbeltState(Property(SeatbeltState(SeatLocation.FRONT_LEFT, SeatbeltState.FastenedState.FASTENED)))
        builder.addSeatbeltState(Property(SeatbeltState(SeatLocation.FRONT_RIGHT, SeatbeltState.FastenedState.NOT_FASTENED)))
        builder.addSeatbeltState(Property(SeatbeltState(SeatLocation.REAR_RIGHT, SeatbeltState.FastenedState.NOT_FASTENED)))
        builder.addSeatbeltState(Property(SeatbeltState(SeatLocation.REAR_LEFT, SeatbeltState.FastenedState.NOT_FASTENED)))
        builder.addSeatbeltState(Property(SeatbeltState(SeatLocation.REAR_CENTER, SeatbeltState.FastenedState.NOT_FASTENED)))
        testState(builder.build())
    }
    
    private fun testState(state: Seats.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getPersonsDetected()[0].value?.location == SeatLocation.FRONT_LEFT)
        assertTrue(state.getPersonsDetected()[0].value?.detected == Detected.DETECTED)
        assertTrue(state.getPersonsDetected()[1].value?.location == SeatLocation.FRONT_RIGHT)
        assertTrue(state.getPersonsDetected()[1].value?.detected == Detected.NOT_DETECTED)
        assertTrue(state.getPersonsDetected()[2].value?.location == SeatLocation.REAR_RIGHT)
        assertTrue(state.getPersonsDetected()[2].value?.detected == Detected.NOT_DETECTED)
        assertTrue(state.getPersonsDetected()[3].value?.location == SeatLocation.REAR_LEFT)
        assertTrue(state.getPersonsDetected()[3].value?.detected == Detected.NOT_DETECTED)
        assertTrue(state.getPersonsDetected()[4].value?.location == SeatLocation.REAR_CENTER)
        assertTrue(state.getPersonsDetected()[4].value?.detected == Detected.NOT_DETECTED)
        assertTrue(state.getSeatbeltsState()[0].value?.location == SeatLocation.FRONT_LEFT)
        assertTrue(state.getSeatbeltsState()[0].value?.fastenedState == SeatbeltState.FastenedState.FASTENED)
        assertTrue(state.getSeatbeltsState()[1].value?.location == SeatLocation.FRONT_RIGHT)
        assertTrue(state.getSeatbeltsState()[1].value?.fastenedState == SeatbeltState.FastenedState.NOT_FASTENED)
        assertTrue(state.getSeatbeltsState()[2].value?.location == SeatLocation.REAR_RIGHT)
        assertTrue(state.getSeatbeltsState()[2].value?.fastenedState == SeatbeltState.FastenedState.NOT_FASTENED)
        assertTrue(state.getSeatbeltsState()[3].value?.location == SeatLocation.REAR_LEFT)
        assertTrue(state.getSeatbeltsState()[3].value?.fastenedState == SeatbeltState.FastenedState.NOT_FASTENED)
        assertTrue(state.getSeatbeltsState()[4].value?.location == SeatLocation.REAR_CENTER)
        assertTrue(state.getSeatbeltsState()[4].value?.fastenedState == SeatbeltState.FastenedState.NOT_FASTENED)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "005600")
        val defaultGetter = Seats.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0056000203")
        val propertyGetter = Seats.GetState(0x02, 0x03)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0203"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "005602")
        val created = Seats.GetStateAvailability()
        assertTrue(created.identifier == Identifier.SEATS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Seats.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.SEATS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0203")
        val allBytes = Bytes(COMMAND_HEADER + "005602" + identifierBytes)
        val constructed = Seats.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.SEATS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Seats.GetStateAvailability(0x02, 0x03)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Seats.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.SEATS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}