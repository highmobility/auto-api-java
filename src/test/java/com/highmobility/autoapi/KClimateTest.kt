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
        assertTrue(state.getInsideTemperature().value?.value == 19.1)
        assertTrue(state.getInsideTemperature().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getOutsideTemperature().value?.value == 12.2)
        assertTrue(state.getOutsideTemperature().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getDriverTemperatureSetting().value?.value == 21.5)
        assertTrue(state.getDriverTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getPassengerTemperatureSetting().value?.value == 21.7)
        assertTrue(state.getPassengerTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getHvacState().value == ActiveState.ACTIVE)
        assertTrue(state.getDefoggingState().value == ActiveState.INACTIVE)
        assertTrue(state.getDefrostingState().value == ActiveState.INACTIVE)
        assertTrue(state.getIonisingState().value == ActiveState.INACTIVE)
        assertTrue(state.getDefrostingTemperatureSetting().value?.value == 21.2)
        assertTrue(state.getDefrostingTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getHvacWeekdayStartingTimes()[0].value?.weekday == Weekday.MONDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[0].value?.time?.hour == 16)
        assertTrue(state.getHvacWeekdayStartingTimes()[0].value?.time?.minute == 0)
        assertTrue(state.getHvacWeekdayStartingTimes()[1].value?.weekday == Weekday.TUESDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[1].value?.time?.hour == 16)
        assertTrue(state.getHvacWeekdayStartingTimes()[1].value?.time?.minute == 0)
        assertTrue(state.getHvacWeekdayStartingTimes()[2].value?.weekday == Weekday.WEDNESDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[2].value?.time?.hour == 16)
        assertTrue(state.getHvacWeekdayStartingTimes()[2].value?.time?.minute == 0)
        assertTrue(state.getHvacWeekdayStartingTimes()[3].value?.weekday == Weekday.THURSDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[3].value?.time?.hour == 16)
        assertTrue(state.getHvacWeekdayStartingTimes()[3].value?.time?.minute == 0)
        assertTrue(state.getHvacWeekdayStartingTimes()[4].value?.weekday == Weekday.FRIDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[4].value?.time?.hour == 16)
        assertTrue(state.getHvacWeekdayStartingTimes()[4].value?.time?.minute == 0)
        assertTrue(state.getHvacWeekdayStartingTimes()[5].value?.weekday == Weekday.SATURDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[5].value?.time?.hour == 18)
        assertTrue(state.getHvacWeekdayStartingTimes()[5].value?.time?.minute == 30)
        assertTrue(state.getHvacWeekdayStartingTimes()[6].value?.weekday == Weekday.SUNDAY)
        assertTrue(state.getHvacWeekdayStartingTimes()[6].value?.time?.hour == 19)
        assertTrue(state.getHvacWeekdayStartingTimes()[6].value?.time?.minute == 31)
        assertTrue(state.getHvacWeekdayStartingTimes()[7].value?.weekday == Weekday.AUTOMATIC)
        assertTrue(state.getHvacWeekdayStartingTimes()[7].value?.time?.hour == 16)
        assertTrue(state.getHvacWeekdayStartingTimes()[7].value?.time?.minute == 0)
        assertTrue(state.getRearTemperatureSetting().value?.value == 21.6)
        assertTrue(state.getRearTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "002400")
        assertTrue(Climate.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0024000102030405060708090b0c")
        val getter = Climate.GetProperties(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0b, 0x0c)
        assertTrue(getter == bytes)
    }
    
    @Test fun changeStartingTimes() {
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
        assertTrue(resolved.getHvacWeekdayStartingTimes()[0].value?.weekday == Weekday.MONDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[0].value?.time?.hour == 16)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[0].value?.time?.minute == 0)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[1].value?.weekday == Weekday.TUESDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[1].value?.time?.hour == 16)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[1].value?.time?.minute == 0)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[2].value?.weekday == Weekday.WEDNESDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[2].value?.time?.hour == 16)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[2].value?.time?.minute == 0)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[3].value?.weekday == Weekday.THURSDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[3].value?.time?.hour == 16)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[3].value?.time?.minute == 0)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[4].value?.weekday == Weekday.FRIDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[4].value?.time?.hour == 16)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[4].value?.time?.minute == 0)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[5].value?.weekday == Weekday.SATURDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[5].value?.time?.hour == 18)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[5].value?.time?.minute == 30)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[6].value?.weekday == Weekday.SUNDAY)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[6].value?.time?.hour == 19)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[6].value?.time?.minute == 31)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[7].value?.weekday == Weekday.AUTOMATIC)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[7].value?.time?.hour == 16)
        assertTrue(resolved.getHvacWeekdayStartingTimes()[7].value?.time?.minute == 0)
        assertTrue(resolved == bytes)
    }
    
    @Test fun startStopHvac() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "05000401000101")
    
        val constructed = Climate.StartStopHvac(ActiveState.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopHvac
        assertTrue(resolved.getHvacState().value == ActiveState.ACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test fun startStopDefogging() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "06000401000100")
    
        val constructed = Climate.StartStopDefogging(ActiveState.INACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopDefogging
        assertTrue(resolved.getDefoggingState().value == ActiveState.INACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test fun startStopDefrosting() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "07000401000100")
    
        val constructed = Climate.StartStopDefrosting(ActiveState.INACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopDefrosting
        assertTrue(resolved.getDefrostingState().value == ActiveState.INACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test fun startStopIonising() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "08000401000100")
    
        val constructed = Climate.StartStopIonising(ActiveState.INACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.StartStopIonising
        assertTrue(resolved.getIonisingState().value == ActiveState.INACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test fun setTemperatureSettings() {
        val bytes = Bytes(COMMAND_HEADER + "002401" +
            "03000D01000A17014035800000000000" +
            "04000D01000A17014035b33333333333" +
            "0c000D01000A1701403599999999999a")
    
        val constructed = Climate.SetTemperatureSettings(Temperature(21.5, Temperature.Unit.CELSIUS), Temperature(21.7, Temperature.Unit.CELSIUS), Temperature(21.6, Temperature.Unit.CELSIUS))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Climate.SetTemperatureSettings
        assertTrue(resolved.getDriverTemperatureSetting().value?.value == 21.5)
        assertTrue(resolved.getDriverTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(resolved.getPassengerTemperatureSetting().value?.value == 21.7)
        assertTrue(resolved.getPassengerTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(resolved.getRearTemperatureSetting().value?.value == 21.6)
        assertTrue(resolved.getRearTemperatureSetting().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(resolved == bytes)
    }
}