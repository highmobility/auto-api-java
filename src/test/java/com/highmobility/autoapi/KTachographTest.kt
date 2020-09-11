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
import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KTachographTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006401" + 
            "0100050100020102" +  // Driver nr 1 is working
            "0100050100020200" +  // Driver nr 2 is resting
            "0200050100020302" +  // Driver nr 3 has reached 4 hours
            "0200050100020405" +  // Driver nr 4 has reached 15 hours and 45 minutes
            "0300050100020601" +  // Driver nr 6 has a card present
            "0300050100020700" +  // Driver nr 7 does not have a card present
            "04000401000101" +  // Detected vehicle in motion
            "05000401000100" +  // Vehicle is not overspeeding
            "06000401000100" +  // Vehicle is moving forward
            "07000D01000A16014054000000000000" // Vehicle speed is 80.0km/h
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Tachograph.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Tachograph.State.Builder()
        builder.addDriverWorkingState(Property(DriverWorkingState(1, DriverWorkingState.WorkingState.WORKING)))
        builder.addDriverWorkingState(Property(DriverWorkingState(2, DriverWorkingState.WorkingState.RESTING)))
        builder.addDriversTimeState(Property(DriverTimeState(3, DriverTimeState.TimeState.FOUR_REACHED)))
        builder.addDriversTimeState(Property(DriverTimeState(4, DriverTimeState.TimeState.FIFTEEN_MIN_BEFORE_SIXTEEN)))
        builder.addDriversCardPresent(Property(DriverCardPresent(6, DriverCardPresent.CardPresent.PRESENT)))
        builder.addDriversCardPresent(Property(DriverCardPresent(7, DriverCardPresent.CardPresent.NOT_PRESENT)))
        builder.setVehicleMotion(Property(Detected.DETECTED))
        builder.setVehicleOverspeed(Property(Tachograph.VehicleOverspeed.NO_OVERSPEED))
        builder.setVehicleDirection(Property(Tachograph.VehicleDirection.FORWARD))
        builder.setVehicleSpeed(Property(Speed(80.0, Speed.Unit.KILOMETERS_PER_HOUR)))
        testState(builder.build())
    }
    
    private fun testState(state: Tachograph.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getDriversWorkingStates()[0].value?.driverNumber == 1)
        assertTrue(state.getDriversWorkingStates()[0].value?.workingState == DriverWorkingState.WorkingState.WORKING)
        assertTrue(state.getDriversWorkingStates()[1].value?.driverNumber == 2)
        assertTrue(state.getDriversWorkingStates()[1].value?.workingState == DriverWorkingState.WorkingState.RESTING)
        assertTrue(state.getDriversTimeStates()[0].value?.driverNumber == 3)
        assertTrue(state.getDriversTimeStates()[0].value?.timeState == DriverTimeState.TimeState.FOUR_REACHED)
        assertTrue(state.getDriversTimeStates()[1].value?.driverNumber == 4)
        assertTrue(state.getDriversTimeStates()[1].value?.timeState == DriverTimeState.TimeState.FIFTEEN_MIN_BEFORE_SIXTEEN)
        assertTrue(state.getDriversCardsPresent()[0].value?.driverNumber == 6)
        assertTrue(state.getDriversCardsPresent()[0].value?.cardPresent == DriverCardPresent.CardPresent.PRESENT)
        assertTrue(state.getDriversCardsPresent()[1].value?.driverNumber == 7)
        assertTrue(state.getDriversCardsPresent()[1].value?.cardPresent == DriverCardPresent.CardPresent.NOT_PRESENT)
        assertTrue(state.getVehicleMotion().value == Detected.DETECTED)
        assertTrue(state.getVehicleOverspeed().value == Tachograph.VehicleOverspeed.NO_OVERSPEED)
        assertTrue(state.getVehicleDirection().value == Tachograph.VehicleDirection.FORWARD)
        assertTrue(state.getVehicleSpeed().value?.value == 80.0)
        assertTrue(state.getVehicleSpeed().value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
    }
    
    @Test fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006400")
        val defaultGetter = Tachograph.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "00640001020304050607")
        val propertyGetter = Tachograph.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("01020304050607"))
    }
    
    @Test fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006402")
        val created = Tachograph.GetStateAvailability()
        assertTrue(created.identifier == Identifier.TACHOGRAPH)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Tachograph.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.TACHOGRAPH)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("01020304050607")
        val allBytes = Bytes(COMMAND_HEADER + "006402" + identifierBytes)
        val constructed = Tachograph.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.TACHOGRAPH)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Tachograph.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Tachograph.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.TACHOGRAPH)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}