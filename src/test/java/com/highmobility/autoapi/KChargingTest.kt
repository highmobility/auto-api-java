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

class KChargingTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "02000D01000A1204407b01999999999a" +  // 432.1km estimated range
            "03000B0100083fe0000000000000" +  // Battery level 50%
            "04000D01000A0900bfe3333333333333" +  // Battery alternating current is -0.6A
            "05000D01000A0900bfe3333333333333" +  // Battery direct current is -0.6A
            "06000D01000A0a004079000000000000" +  // Charger voltage is 400.0V for alternating current
            "07000D01000A0a004079000000000000" +  // Charger voltage is 400.0V for direct current
            "08000B0100083feccccccccccccd" +  // Charge limit is set to 90%
            "09000D01000A0701404e000000000000" +  // Time to complete charge is 60.0 minutes
            "0a000D01000A1402400c000000000000" +  // Charging rate is 3.5kW
            "0b000401000101" +  // Charge port is open
            "0c000401000101" +  // Charging is 'timer based'
            "0e000D01000A09004039000000000000" +  // Maximum charging current is 25.0A
            "0f000401000101" +  // Electric plug type is 'Type 2'
            "10000401000100" +  // Charging window is not chosen
            "110006010003011020" +  // Departure time 16:32 is active
            "110006010003000b33" +  // Departure time 11:51 is inactive
            "130006010003001121" +  // Start reduction of charging at 17:33
            "130006010003010c34" +  // Stop reduction of charging current at 12:52
            "14000D01000A17014043333333333333" +  // The battery temperature is 38.4°C
            "15000C01000900000001598938e788" +  // Preferred start time is 10 January 2017 at 16:32:05 UTC
            "15000C0100090100000159893c9108" +  // Preferred end time is 10 January 2017 at 16:36:05 GMT
            "15000C0100090200000159893c9108" +  // Departure date is 10 January 2017 at 16:36:05 GMT
            "16000401000101" +  // The charger is plugged in
            "17000401000101" +  // The vehicle is charging
            "18000D01000A14024062c00000000000" +  // Charging rate is 150.0kW
            "19000D01000A0900bfe3333333333333" +  // Battery current is -0.6A
            "1a000D01000A0a004079000000000000" +  // Charger voltage is 400.0V
            "1b000401000100" +  // Alternating current is used
            "1c000D01000A12044081580000000000" +  // Maximum electric range is 555.0km
            "1d000401000102" +  // Starter battery status is green
            "1e000401000101" +  // Smart Charge Communication is active
            "1f000B0100083feccccccccccccd" +  // Battery level is expected to be 90% at time of departure
            "20000401000101" +  // Preconditioning is active for departure time
            "21000401000101" +  // Immediate preconditioning is active
            "22000401000101" +  // Preconditioning is enabled for departure
            "23000401000101" // Preconditioning not possible because battery or fuel is low
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Charging.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Charging.State.Builder()
        builder.setEstimatedRange(Property(Length(432.1, Length.Unit.KILOMETERS)))
        builder.setBatteryLevel(Property(0.5))
        builder.setBatteryCurrentAC(Property(ElectricCurrent(-0.6, ElectricCurrent.Unit.AMPERES)))
        builder.setBatteryCurrentDC(Property(ElectricCurrent(-0.6, ElectricCurrent.Unit.AMPERES)))
        builder.setChargerVoltageAC(Property(ElectricPotentialDifference(400.0, ElectricPotentialDifference.Unit.VOLTS)))
        builder.setChargerVoltageDC(Property(ElectricPotentialDifference(400.0, ElectricPotentialDifference.Unit.VOLTS)))
        builder.setChargeLimit(Property(0.9))
        builder.setTimeToCompleteCharge(Property(Duration(60.0, Duration.Unit.MINUTES)))
        builder.setChargingRateKW(Property(Power(3.5, Power.Unit.KILOWATTS)))
        builder.setChargePortState(Property(Position.OPEN))
        builder.setChargeMode(Property(Charging.ChargeMode.TIMER_BASED))
        builder.setMaxChargingCurrent(Property(ElectricCurrent(25.0, ElectricCurrent.Unit.AMPERES)))
        builder.setPlugType(Property(Charging.PlugType.TYPE_2))
        builder.setChargingWindowChosen(Property(Charging.ChargingWindowChosen.NOT_CHOSEN))
        builder.addDepartureTime(Property(DepartureTime(ActiveState.ACTIVE, Time(16, 32))))
        builder.addDepartureTime(Property(DepartureTime(ActiveState.INACTIVE, Time(11, 51))))
        builder.addReductionTime(Property(ReductionTime(StartStop.START, Time(17, 33))))
        builder.addReductionTime(Property(ReductionTime(StartStop.STOP, Time(12, 52))))
        builder.setBatteryTemperature(Property(Temperature(38.4, Temperature.Unit.CELSIUS)))
        builder.addTimer(Property(Timer(Timer.TimerType.PREFERRED_START_TIME, getCalendar("2017-01-10T16:32:05.000Z"))))
        builder.addTimer(Property(Timer(Timer.TimerType.PREFERRED_END_TIME, getCalendar("2017-01-10T16:36:05.000Z"))))
        builder.addTimer(Property(Timer(Timer.TimerType.DEPARTURE_DATE, getCalendar("2017-01-10T16:36:05.000Z"))))
        builder.setPluggedIn(Property(Charging.PluggedIn.PLUGGED_IN))
        builder.setStatus(Property(Charging.Status.CHARGING))
        builder.setChargingRate(Property(Power(150.0, Power.Unit.KILOWATTS)))
        builder.setBatteryCurrent(Property(ElectricCurrent(-0.6, ElectricCurrent.Unit.AMPERES)))
        builder.setChargerVoltage(Property(ElectricPotentialDifference(400.0, ElectricPotentialDifference.Unit.VOLTS)))
        builder.setCurrentType(Property(Charging.CurrentType.ALTERNATING_CURRENT))
        builder.setMaxRange(Property(Length(555.0, Length.Unit.KILOMETERS)))
        builder.setStarterBatteryState(Property(Charging.StarterBatteryState.GREEN))
        builder.setSmartChargingStatus(Property(Charging.SmartChargingStatus.SCC_IS_ACTIVE))
        builder.setBatteryLevelAtDeparture(Property(0.9))
        builder.setPreconditioningDepartureStatus(Property(ActiveState.ACTIVE))
        builder.setPreconditioningImmediateStatus(Property(ActiveState.ACTIVE))
        builder.setPreconditioningDepartureEnabled(Property(EnabledState.ENABLED))
        builder.setPreconditioningError(Property(Charging.PreconditioningError.NOT_POSSIBLE_LOW))
        testState(builder.build())
    }
    
    private fun testState(state: Charging.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getEstimatedRange().value?.value == 432.1)
        assertTrue(state.getEstimatedRange().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getBatteryLevel().value == 0.5)
        assertTrue(state.getBatteryCurrentAC().value?.value == -0.6)
        assertTrue(state.getBatteryCurrentAC().value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.getBatteryCurrentDC().value?.value == -0.6)
        assertTrue(state.getBatteryCurrentDC().value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.getChargerVoltageAC().value?.value == 400.0)
        assertTrue(state.getChargerVoltageAC().value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.getChargerVoltageDC().value?.value == 400.0)
        assertTrue(state.getChargerVoltageDC().value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.getChargeLimit().value == 0.9)
        assertTrue(state.getTimeToCompleteCharge().value?.value == 60.0)
        assertTrue(state.getTimeToCompleteCharge().value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.getChargingRateKW().value?.value == 3.5)
        assertTrue(state.getChargingRateKW().value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.getChargePortState().value == Position.OPEN)
        assertTrue(state.getChargeMode().value == Charging.ChargeMode.TIMER_BASED)
        assertTrue(state.getMaxChargingCurrent().value?.value == 25.0)
        assertTrue(state.getMaxChargingCurrent().value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.getPlugType().value == Charging.PlugType.TYPE_2)
        assertTrue(state.getChargingWindowChosen().value == Charging.ChargingWindowChosen.NOT_CHOSEN)
        assertTrue(state.getDepartureTimes()[0].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getDepartureTimes()[0].value?.time?.hour == 16)
        assertTrue(state.getDepartureTimes()[0].value?.time?.minute == 32)
        assertTrue(state.getDepartureTimes()[1].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDepartureTimes()[1].value?.time?.hour == 11)
        assertTrue(state.getDepartureTimes()[1].value?.time?.minute == 51)
        assertTrue(state.getReductionTimes()[0].value?.startStop == StartStop.START)
        assertTrue(state.getReductionTimes()[0].value?.time?.hour == 17)
        assertTrue(state.getReductionTimes()[0].value?.time?.minute == 33)
        assertTrue(state.getReductionTimes()[1].value?.startStop == StartStop.STOP)
        assertTrue(state.getReductionTimes()[1].value?.time?.hour == 12)
        assertTrue(state.getReductionTimes()[1].value?.time?.minute == 52)
        assertTrue(state.getBatteryTemperature().value?.value == 38.4)
        assertTrue(state.getBatteryTemperature().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getTimers()[0].value?.timerType == Timer.TimerType.PREFERRED_START_TIME)
        assertTrue(dateIsSame(state.getTimers()[0].value?.date, "2017-01-10T16:32:05.000Z"))
        assertTrue(state.getTimers()[1].value?.timerType == Timer.TimerType.PREFERRED_END_TIME)
        assertTrue(dateIsSame(state.getTimers()[1].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(state.getTimers()[2].value?.timerType == Timer.TimerType.DEPARTURE_DATE)
        assertTrue(dateIsSame(state.getTimers()[2].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(state.getPluggedIn().value == Charging.PluggedIn.PLUGGED_IN)
        assertTrue(state.getStatus().value == Charging.Status.CHARGING)
        assertTrue(state.getChargingRate().value?.value == 150.0)
        assertTrue(state.getChargingRate().value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.getBatteryCurrent().value?.value == -0.6)
        assertTrue(state.getBatteryCurrent().value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.getChargerVoltage().value?.value == 400.0)
        assertTrue(state.getChargerVoltage().value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.getCurrentType().value == Charging.CurrentType.ALTERNATING_CURRENT)
        assertTrue(state.getMaxRange().value?.value == 555.0)
        assertTrue(state.getMaxRange().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getStarterBatteryState().value == Charging.StarterBatteryState.GREEN)
        assertTrue(state.getSmartChargingStatus().value == Charging.SmartChargingStatus.SCC_IS_ACTIVE)
        assertTrue(state.getBatteryLevelAtDeparture().value == 0.9)
        assertTrue(state.getPreconditioningDepartureStatus().value == ActiveState.ACTIVE)
        assertTrue(state.getPreconditioningImmediateStatus().value == ActiveState.ACTIVE)
        assertTrue(state.getPreconditioningDepartureEnabled().value == EnabledState.ENABLED)
        assertTrue(state.getPreconditioningError().value == Charging.PreconditioningError.NOT_POSSIBLE_LOW)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "002300")
        assertTrue(Charging.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "00230002030405060708090a0b0c0e0f1011131415161718191a1b1c1d1e1f20212223")
        val getter = Charging.GetProperties(0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0e, 0x0f, 0x10, 0x11, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testStartStopCharging() {
        val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "17000401000101")
    
        val constructed = Charging.StartStopCharging(Charging.Status.CHARGING)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.StartStopCharging
        assertTrue(resolved.getStatus().value == Charging.Status.CHARGING)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testSetChargeLimit() {
        val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "08000B0100083feccccccccccccd")
    
        val constructed = Charging.SetChargeLimit(0.9)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetChargeLimit
        assertTrue(resolved.getChargeLimit().value == 0.9)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testOpenCloseChargingPort() {
        val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "0b000401000101")
    
        val constructed = Charging.OpenCloseChargingPort(Position.OPEN)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.OpenCloseChargingPort
        assertTrue(resolved.getChargePortState().value == Position.OPEN)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testSetChargeMode() {
        val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "0c000401000101")
    
        val constructed = Charging.SetChargeMode(Charging.ChargeMode.TIMER_BASED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetChargeMode
        assertTrue(resolved.getChargeMode().value == Charging.ChargeMode.TIMER_BASED)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testSetChargingTimers() {
        val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "15000C01000900000001598938e788" +
            "15000C0100090100000159893c9108" +
            "15000C0100090200000159893c9108")
    
        val constructed = Charging.SetChargingTimers(arrayOf(
            Timer(Timer.TimerType.PREFERRED_START_TIME, getCalendar("2017-01-10T16:32:05.000Z")), 
            Timer(Timer.TimerType.PREFERRED_END_TIME, getCalendar("2017-01-10T16:36:05.000Z")), 
            Timer(Timer.TimerType.DEPARTURE_DATE, getCalendar("2017-01-10T16:36:05.000Z"))))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetChargingTimers
        assertTrue(resolved.getTimers()[0].value?.timerType == Timer.TimerType.PREFERRED_START_TIME)
        assertTrue(dateIsSame(resolved.getTimers()[0].value?.date, "2017-01-10T16:32:05.000Z"))
        assertTrue(resolved.getTimers()[1].value?.timerType == Timer.TimerType.PREFERRED_END_TIME)
        assertTrue(dateIsSame(resolved.getTimers()[1].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(resolved.getTimers()[2].value?.timerType == Timer.TimerType.DEPARTURE_DATE)
        assertTrue(dateIsSame(resolved.getTimers()[2].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testSetReductionOfChargingCurrentTimes() {
        val bytes = Bytes(COMMAND_HEADER + "002301" + 
            "130006010003001121" +
            "130006010003010c34")
    
        val constructed = Charging.SetReductionOfChargingCurrentTimes(arrayOf(
            ReductionTime(StartStop.START, Time(17, 33)), 
            ReductionTime(StartStop.STOP, Time(12, 52))))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetReductionOfChargingCurrentTimes
        assertTrue(resolved.getReductionTimes()[0].value?.startStop == StartStop.START)
        assertTrue(resolved.getReductionTimes()[0].value?.time?.hour == 17)
        assertTrue(resolved.getReductionTimes()[0].value?.time?.minute == 33)
        assertTrue(resolved.getReductionTimes()[1].value?.startStop == StartStop.STOP)
        assertTrue(resolved.getReductionTimes()[1].value?.time?.hour == 12)
        assertTrue(resolved.getReductionTimes()[1].value?.time?.minute == 52)
        assertTrue(resolved == bytes)
    }
}