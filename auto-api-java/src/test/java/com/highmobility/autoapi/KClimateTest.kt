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

class KClimateTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002401" + 
            "01000D01000A1701403319999999999a" +  // Inside temperature is 19.1°C
            "02000D01000A17014028666666666666" +  // Outside temperature is 12.2°C
            "03000D01000A17014035800000000000" +  // Driver temperature setting is 21.5°C
            "04000D01000A17014035b33333333333" +  // Passenger temperature setting is 21.7°C
            "05000401000101" +  // HVAC is active
            "06000401000100" +  // Defogging is inactive
            "07000401000100" +  // Defrosting is inactive
            "08000401000100" +  // Ionising is inactive
            "09000D01000A17014035333333333333" +  // Defrosting temperature setting is 21.2°C
            "0b0006010003001000" +  // HVAC is started on monday at 16:00
            "0b0006010003011000" +  // HVAC is started on tuesday at 16:00
            "0b0006010003021000" +  // HVAC is started on wednesday at 16:00
            "0b0006010003031000" +  // HVAC is started on thursday at 16:00
            "0b0006010003041000" +  // HVAC is started on friday at 16:00
            "0b000601000305121e" +  // HVAC is started on saturday at 18:30
            "0b000601000306131f" +  // HVAC is started on sunday at 19:31
            "0b0006010003071000" +  // HVAC is automatic
            "0c000D01000A1701403599999999999a" // Rear temperature setting is 21.6°C
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Climate.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Climate.State.Builder()
        builder.setInsideTemperature(Property(Temperature(19.1, Temperature.Unit.CELSIUS)))
        builder.setOutsideTemperature(Property(Temperature(12.2, Temperature.Unit.CELSIUS)))
        builder.setDriverTemperatureSetting(Property(Temperature(21.5, Temperature.Unit.CELSIUS)))
        builder.setPassengerTemperatureSetting(Property(Temperature(21.7, Temperature.Unit.CELSIUS)))
        builder.setHvacState(Property(ActiveState.ACTIVE))
        builder.setDefoggingState(Property(ActiveState.INACTIVE))
        builder.setDefrostingState(Property(ActiveState.INACTIVE))
        builder.setIonisingState(Property(ActiveState.INACTIVE))
        builder.setDefrostingTemperatureSetting(Property(Temperature(21.2, Temperature.Unit.CELSIUS)))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.MONDAY, Time(16, 0))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.TUESDAY, Time(16, 0))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.WEDNESDAY, Time(16, 0))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.THURSDAY, Time(16, 0))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.FRIDAY, Time(16, 0))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.SATURDAY, Time(18, 30))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.SUNDAY, Time(19, 31))))
        builder.addHvacWeekdayStartingTime(Property(HvacWeekdayStartingTime(Weekday.AUTOMATIC, Time(16, 0))))
        builder.setRearTemperatureSetting(Property(Temperature(21.6, Temperature.Unit.CELSIUS)))
        testState(builder.build())
    }
    
    private fun testState(state: Climate.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.insideTemperature.value?.value == 19.1)
        assertTrue(state.insideTemperature.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.outsideTemperature.value?.value == 12.2)
        assertTrue(state.outsideTemperature.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.driverTemperatureSetting.value?.value == 21.5)
        assertTrue(state.driverTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.passengerTemperatureSetting.value?.value == 21.7)
        assertTrue(state.passengerTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.hvacState.value == ActiveState.ACTIVE)
        assertTrue(state.defoggingState.value == ActiveState.INACTIVE)
        assertTrue(state.defrostingState.value == ActiveState.INACTIVE)
        assertTrue(state.ionisingState.value == ActiveState.INACTIVE)
        assertTrue(state.defrostingTemperatureSetting.value?.value == 21.2)
        assertTrue(state.defrostingTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.hvacWeekdayStartingTimes[0].value?.weekday == Weekday.MONDAY)
        assertTrue(state.hvacWeekdayStartingTimes[0].value?.time?.hour == 16)
        assertTrue(state.hvacWeekdayStartingTimes[0].value?.time?.minute == 0)
        assertTrue(state.hvacWeekdayStartingTimes[1].value?.weekday == Weekday.TUESDAY)
        assertTrue(state.hvacWeekdayStartingTimes[1].value?.time?.hour == 16)
        assertTrue(state.hvacWeekdayStartingTimes[1].value?.time?.minute == 0)
        assertTrue(state.hvacWeekdayStartingTimes[2].value?.weekday == Weekday.WEDNESDAY)
        assertTrue(state.hvacWeekdayStartingTimes[2].value?.time?.hour == 16)
        assertTrue(state.hvacWeekdayStartingTimes[2].value?.time?.minute == 0)
        assertTrue(state.hvacWeekdayStartingTimes[3].value?.weekday == Weekday.THURSDAY)
        assertTrue(state.hvacWeekdayStartingTimes[3].value?.time?.hour == 16)
        assertTrue(state.hvacWeekdayStartingTimes[3].value?.time?.minute == 0)
        assertTrue(state.hvacWeekdayStartingTimes[4].value?.weekday == Weekday.FRIDAY)
        assertTrue(state.hvacWeekdayStartingTimes[4].value?.time?.hour == 16)
        assertTrue(state.hvacWeekdayStartingTimes[4].value?.time?.minute == 0)
        assertTrue(state.hvacWeekdayStartingTimes[5].value?.weekday == Weekday.SATURDAY)
        assertTrue(state.hvacWeekdayStartingTimes[5].value?.time?.hour == 18)
        assertTrue(state.hvacWeekdayStartingTimes[5].value?.time?.minute == 30)
        assertTrue(state.hvacWeekdayStartingTimes[6].value?.weekday == Weekday.SUNDAY)
        assertTrue(state.hvacWeekdayStartingTimes[6].value?.time?.hour == 19)
        assertTrue(state.hvacWeekdayStartingTimes[6].value?.time?.minute == 31)
        assertTrue(state.hvacWeekdayStartingTimes[7].value?.weekday == Weekday.AUTOMATIC)
        assertTrue(state.hvacWeekdayStartingTimes[7].value?.time?.hour == 16)
        assertTrue(state.hvacWeekdayStartingTimes[7].value?.time?.minute == 0)
        assertTrue(state.rearTemperatureSetting.value?.value == 21.6)
        assertTrue(state.rearTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002400")
        val defaultGetter = Climate.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0024000102030405060708090b0c")
        val propertyGetter = Climate.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0b, 0x0c)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405060708090b0c"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002402")
        val created = Climate.GetStateAvailability()
        assertTrue(created.identifier == Identifier.CLIMATE)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.CLIMATE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102030405060708090b0c")
        val allBytes = Bytes(COMMAND_HEADER + "002402" + identifierBytes)
        val constructed = Climate.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.CLIMATE)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Climate.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0b, 0x0c)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Climate.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.CLIMATE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun changeStartingTimes() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "0b0006010003001000" +
            "0b0006010003011000" +
            "0b0006010003021000" +
            "0b0006010003031000" +
            "0b0006010003041000" +
            "0b000601000305121e" +
            "0b000601000306131f" +
            "0b0006010003071000")
    
        val constructed = Climate.ChangeStartingTimes(arrayListOf(
            HvacWeekdayStartingTime(Weekday.MONDAY, Time(16, 0)), 
            HvacWeekdayStartingTime(Weekday.TUESDAY, Time(16, 0)), 
            HvacWeekdayStartingTime(Weekday.WEDNESDAY, Time(16, 0)), 
            HvacWeekdayStartingTime(Weekday.THURSDAY, Time(16, 0)), 
            HvacWeekdayStartingTime(Weekday.FRIDAY, Time(16, 0)), 
            HvacWeekdayStartingTime(Weekday.SATURDAY, Time(18, 30)), 
            HvacWeekdayStartingTime(Weekday.SUNDAY, Time(19, 31)), 
            HvacWeekdayStartingTime(Weekday.AUTOMATIC, Time(16, 0))))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.ChangeStartingTimes
        assertTrue(resolved.hvacWeekdayStartingTimes[0].value?.weekday == Weekday.MONDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[0].value?.time?.hour == 16)
        assertTrue(resolved.hvacWeekdayStartingTimes[0].value?.time?.minute == 0)
        assertTrue(resolved.hvacWeekdayStartingTimes[1].value?.weekday == Weekday.TUESDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[1].value?.time?.hour == 16)
        assertTrue(resolved.hvacWeekdayStartingTimes[1].value?.time?.minute == 0)
        assertTrue(resolved.hvacWeekdayStartingTimes[2].value?.weekday == Weekday.WEDNESDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[2].value?.time?.hour == 16)
        assertTrue(resolved.hvacWeekdayStartingTimes[2].value?.time?.minute == 0)
        assertTrue(resolved.hvacWeekdayStartingTimes[3].value?.weekday == Weekday.THURSDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[3].value?.time?.hour == 16)
        assertTrue(resolved.hvacWeekdayStartingTimes[3].value?.time?.minute == 0)
        assertTrue(resolved.hvacWeekdayStartingTimes[4].value?.weekday == Weekday.FRIDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[4].value?.time?.hour == 16)
        assertTrue(resolved.hvacWeekdayStartingTimes[4].value?.time?.minute == 0)
        assertTrue(resolved.hvacWeekdayStartingTimes[5].value?.weekday == Weekday.SATURDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[5].value?.time?.hour == 18)
        assertTrue(resolved.hvacWeekdayStartingTimes[5].value?.time?.minute == 30)
        assertTrue(resolved.hvacWeekdayStartingTimes[6].value?.weekday == Weekday.SUNDAY)
        assertTrue(resolved.hvacWeekdayStartingTimes[6].value?.time?.hour == 19)
        assertTrue(resolved.hvacWeekdayStartingTimes[6].value?.time?.minute == 31)
        assertTrue(resolved.hvacWeekdayStartingTimes[7].value?.weekday == Weekday.AUTOMATIC)
        assertTrue(resolved.hvacWeekdayStartingTimes[7].value?.time?.hour == 16)
        assertTrue(resolved.hvacWeekdayStartingTimes[7].value?.time?.minute == 0)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun startStopHvac() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "05000401000101")
    
        val constructed = Climate.StartStopHvac(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopHvac
        assertTrue(resolved.hvacState.value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun startStopDefogging() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "06000401000100")
    
        val constructed = Climate.StartStopDefogging(ActiveState.INACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopDefogging
        assertTrue(resolved.defoggingState.value == ActiveState.INACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun startStopDefrosting() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "07000401000100")
    
        val constructed = Climate.StartStopDefrosting(ActiveState.INACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopDefrosting
        assertTrue(resolved.defrostingState.value == ActiveState.INACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun startStopIonising() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "08000401000100")
    
        val constructed = Climate.StartStopIonising(ActiveState.INACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopIonising
        assertTrue(resolved.ionisingState.value == ActiveState.INACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setTemperatureSettings() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "03000D01000A17014035800000000000" +
            "04000D01000A17014035b33333333333" +
            "0c000D01000A1701403599999999999a")
    
        val constructed = Climate.SetTemperatureSettings(Temperature(21.5, Temperature.Unit.CELSIUS), Temperature(21.7, Temperature.Unit.CELSIUS), Temperature(21.6, Temperature.Unit.CELSIUS))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.SetTemperatureSettings
        assertTrue(resolved.driverTemperatureSetting.value?.value == 21.5)
        assertTrue(resolved.driverTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(resolved.passengerTemperatureSetting.value?.value == 21.7)
        assertTrue(resolved.passengerTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(resolved.rearTemperatureSetting.value?.value == 21.6)
        assertTrue(resolved.rearTemperatureSetting.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(resolved == bytes)
    }
}