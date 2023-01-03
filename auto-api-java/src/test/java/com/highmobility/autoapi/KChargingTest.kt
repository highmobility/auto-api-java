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

@Suppress("DEPRECATION")
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
            "23000401000101" +  // Preconditioning not possible because battery or fuel is low
            "24000D01000A0c044051800000000000" +  // Battery capacity is 70.0kWh
            "25000D01000A1402405e000000000000" +  // Auxiliary power is 120.0kW.
            "26000401000100" +  // Charging complete lock status is 'inactive'.
            "27000D01000A0c044058c00000000000" +  // Maximum available battery capacity is 99.0kWh
            "28000401000101" +  // Charging ended because 'goal_reached'.
            "29000401000101" +  // Charging has 'one' phase.
            "2a000D01000A0c04405de00000000000" +  // Current energy content of the high-voltage battery is 119.5kWh.
            "2b000D01000A0c04403ec00000000000" +  // Energy required to fully charge the battery is 30.75kWh.
            "2c000401000101" +  // Single instant charging function is 'active'.
            "2d000401000101" +  // Charging duration is displayed in the vehicle.
            "2e000401000101" +  // Departure time display is showing 'reachable'.
            "2f00050100020100" +  // Charging restriction is 'active' and set to 'max'.
            "30000401000100" +  // Charging limit is 'inactive'.
            "31000D01000A09004059000000000000" +  // Charging current limit is 100.0A.
            "32000401000101" +  // Smart charging option is set to 'renewable_energy'.
            "33000401000100" +  // Charging plug is 'unlocked'.
            "34000401000101" +  // Charging flap is 'locked'.
            "35000401000101" +  // Charging process acoustic limitation is 'automatic'.
            "36000D01000A09004014000000000000" +  // Minimum charging current is 5.0A.
            "37000D01000A12044072c00000000000" +  // Remaining electric range target is 300.0km.
            "38000601000300051e" +  // Vehicle will be fully charged on monday at 05:30.
            "390005010002051e" +  // Preconditioning scheduled departure time is at 05:30.
            "3a000D01000A0701405b800000000000" +  // Preconditioning is completed in 110.0min.
            "3b000D01000A0a00405e000000000000" +  // HV battery voltage is 120.0V.
            "3c000E01000B0017014055400000000000" +  // Battery highest temperature is 85.0C.
            "3d000401000100" +  // Battery temperature control system demands high cooling.
            "3e000D01000A090040091eb851eb851f" +  // Charging current is 3.14A.
            "3f000401000101" +  // Battery is in an active state.
            "40000401000100" +  // State of battery LED is no colour.
            "41000D01000A1701404fb33333333333" +  // Battery cooling temperature is 63.4C.
            "42000E01000B0017014055400000000000" // Battery highest temperature is 85.0C.
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
        builder.setBatteryCapacity(Property(Energy(70.0, Energy.Unit.KILOWATT_HOURS)))
        builder.setAuxiliaryPower(Property(Power(120.0, Power.Unit.KILOWATTS)))
        builder.setChargingCompleteLock(Property(ActiveState.INACTIVE))
        builder.setBatteryMaxAvailable(Property(Energy(99.0, Energy.Unit.KILOWATT_HOURS)))
        builder.setChargingEndReason(Property(Charging.ChargingEndReason.GOAL_REACHED))
        builder.setChargingPhases(Property(Charging.ChargingPhases.ONE))
        builder.setBatteryEnergy(Property(Energy(119.5, Energy.Unit.KILOWATT_HOURS)))
        builder.setBatteryEnergyChargable(Property(Energy(30.75, Energy.Unit.KILOWATT_HOURS)))
        builder.setChargingSingleImmediate(Property(ActiveState.ACTIVE))
        builder.setChargingTimeDisplay(Property(Charging.ChargingTimeDisplay.DISPLAY_DURATION))
        builder.setDepartureTimeDisplay(Property(Charging.DepartureTimeDisplay.REACHABLE))
        builder.setRestriction(Property(ChargingRestriction(ActiveState.ACTIVE, ChargingRestriction.Limit.MAX)))
        builder.setLimitStatus(Property(ActiveState.INACTIVE))
        builder.setCurrentLimit(Property(ElectricCurrent(100.0, ElectricCurrent.Unit.AMPERES)))
        builder.setSmartChargingOption(Property(Charging.SmartChargingOption.RENEWABLE_ENERGY))
        builder.setPlugLockStatus(Property(LockState.UNLOCKED))
        builder.setFlapLockStatus(Property(LockState.LOCKED))
        builder.setAcousticLimit(Property(Charging.AcousticLimit.AUTOMATIC))
        builder.setMinChargingCurrent(Property(ElectricCurrent(5.0, ElectricCurrent.Unit.AMPERES)))
        builder.setEstimatedRangeTarget(Property(Length(300.0, Length.Unit.KILOMETERS)))
        builder.setFullyChargedEndTimes(Property(WeekdayTime(Weekday.MONDAY, Time(5, 30))))
        builder.setPreconditioningScheduledTime(Property(Time(5, 30)))
        builder.setPreconditioningRemainingTime(Property(Duration(110.0, Duration.Unit.MINUTES)))
        builder.setBatteryVoltage(Property(ElectricPotentialDifference(120.0, ElectricPotentialDifference.Unit.VOLTS)))
        builder.setBatteryTempretatureExtremes(Property(TemperatureExtreme(TemperatureExtreme.Extreme.HIGHEST, Temperature(85.0, Temperature.Unit.CELSIUS))))
        builder.setBatteryTemperatureControlDemand(Property(Charging.BatteryTemperatureControlDemand.HIGH_COOLING))
        builder.setChargingCurrent(Property(ElectricCurrent(3.14, ElectricCurrent.Unit.AMPERES)))
        builder.setBatteryStatus(Property(Charging.BatteryStatus.ACTIVE))
        builder.setBatteryLed(Property(Charging.BatteryLed.NO_COLOUR))
        builder.setBatteryCoolingTemperature(Property(Temperature(63.4, Temperature.Unit.CELSIUS)))
        builder.setBatteryTemperatureExtremes(Property(TemperatureExtreme(TemperatureExtreme.Extreme.HIGHEST, Temperature(85.0, Temperature.Unit.CELSIUS))))
        testState(builder.build())
    }
    
    private fun testState(state: Charging.State) {
        assertTrue(state.estimatedRange.value?.value == 432.1)
        assertTrue(state.estimatedRange.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.batteryLevel.value == 0.5)
        assertTrue(state.batteryCurrentAC.value?.value == -0.6)
        assertTrue(state.batteryCurrentAC.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.batteryCurrentDC.value?.value == -0.6)
        assertTrue(state.batteryCurrentDC.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.chargerVoltageAC.value?.value == 400.0)
        assertTrue(state.chargerVoltageAC.value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.chargerVoltageDC.value?.value == 400.0)
        assertTrue(state.chargerVoltageDC.value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.chargeLimit.value == 0.9)
        assertTrue(state.timeToCompleteCharge.value?.value == 60.0)
        assertTrue(state.timeToCompleteCharge.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.chargingRateKW.value?.value == 3.5)
        assertTrue(state.chargingRateKW.value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.chargePortState.value == Position.OPEN)
        assertTrue(state.chargeMode.value == Charging.ChargeMode.TIMER_BASED)
        assertTrue(state.maxChargingCurrent.value?.value == 25.0)
        assertTrue(state.maxChargingCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.plugType.value == Charging.PlugType.TYPE_2)
        assertTrue(state.chargingWindowChosen.value == Charging.ChargingWindowChosen.NOT_CHOSEN)
        assertTrue(state.departureTimes[0].value?.state == ActiveState.ACTIVE)
        assertTrue(state.departureTimes[0].value?.time?.hour == 16)
        assertTrue(state.departureTimes[0].value?.time?.minute == 32)
        assertTrue(state.departureTimes[1].value?.state == ActiveState.INACTIVE)
        assertTrue(state.departureTimes[1].value?.time?.hour == 11)
        assertTrue(state.departureTimes[1].value?.time?.minute == 51)
        assertTrue(state.reductionTimes[0].value?.startStop == StartStop.START)
        assertTrue(state.reductionTimes[0].value?.time?.hour == 17)
        assertTrue(state.reductionTimes[0].value?.time?.minute == 33)
        assertTrue(state.reductionTimes[1].value?.startStop == StartStop.STOP)
        assertTrue(state.reductionTimes[1].value?.time?.hour == 12)
        assertTrue(state.reductionTimes[1].value?.time?.minute == 52)
        assertTrue(state.batteryTemperature.value?.value == 38.4)
        assertTrue(state.batteryTemperature.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.timers[0].value?.timerType == Timer.TimerType.PREFERRED_START_TIME)
        assertTrue(dateIsSame(state.timers[0].value?.date, "2017-01-10T16:32:05.000Z"))
        assertTrue(state.timers[1].value?.timerType == Timer.TimerType.PREFERRED_END_TIME)
        assertTrue(dateIsSame(state.timers[1].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(state.timers[2].value?.timerType == Timer.TimerType.DEPARTURE_DATE)
        assertTrue(dateIsSame(state.timers[2].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(state.pluggedIn.value == Charging.PluggedIn.PLUGGED_IN)
        assertTrue(state.status.value == Charging.Status.CHARGING)
        assertTrue(state.chargingRate.value?.value == 150.0)
        assertTrue(state.chargingRate.value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.batteryCurrent.value?.value == -0.6)
        assertTrue(state.batteryCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.chargerVoltage.value?.value == 400.0)
        assertTrue(state.chargerVoltage.value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.currentType.value == Charging.CurrentType.ALTERNATING_CURRENT)
        assertTrue(state.maxRange.value?.value == 555.0)
        assertTrue(state.maxRange.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.starterBatteryState.value == Charging.StarterBatteryState.GREEN)
        assertTrue(state.smartChargingStatus.value == Charging.SmartChargingStatus.SCC_IS_ACTIVE)
        assertTrue(state.batteryLevelAtDeparture.value == 0.9)
        assertTrue(state.preconditioningDepartureStatus.value == ActiveState.ACTIVE)
        assertTrue(state.preconditioningImmediateStatus.value == ActiveState.ACTIVE)
        assertTrue(state.preconditioningDepartureEnabled.value == EnabledState.ENABLED)
        assertTrue(state.preconditioningError.value == Charging.PreconditioningError.NOT_POSSIBLE_LOW)
        assertTrue(state.batteryCapacity.value?.value == 70.0)
        assertTrue(state.batteryCapacity.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.auxiliaryPower.value?.value == 120.0)
        assertTrue(state.auxiliaryPower.value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.chargingCompleteLock.value == ActiveState.INACTIVE)
        assertTrue(state.batteryMaxAvailable.value?.value == 99.0)
        assertTrue(state.batteryMaxAvailable.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.chargingEndReason.value == Charging.ChargingEndReason.GOAL_REACHED)
        assertTrue(state.chargingPhases.value == Charging.ChargingPhases.ONE)
        assertTrue(state.batteryEnergy.value?.value == 119.5)
        assertTrue(state.batteryEnergy.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.batteryEnergyChargable.value?.value == 30.75)
        assertTrue(state.batteryEnergyChargable.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.chargingSingleImmediate.value == ActiveState.ACTIVE)
        assertTrue(state.chargingTimeDisplay.value == Charging.ChargingTimeDisplay.DISPLAY_DURATION)
        assertTrue(state.departureTimeDisplay.value == Charging.DepartureTimeDisplay.REACHABLE)
        assertTrue(state.restriction.value?.active == ActiveState.ACTIVE)
        assertTrue(state.restriction.value?.limit == ChargingRestriction.Limit.MAX)
        assertTrue(state.limitStatus.value == ActiveState.INACTIVE)
        assertTrue(state.currentLimit.value?.value == 100.0)
        assertTrue(state.currentLimit.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.smartChargingOption.value == Charging.SmartChargingOption.RENEWABLE_ENERGY)
        assertTrue(state.plugLockStatus.value == LockState.UNLOCKED)
        assertTrue(state.flapLockStatus.value == LockState.LOCKED)
        assertTrue(state.acousticLimit.value == Charging.AcousticLimit.AUTOMATIC)
        assertTrue(state.minChargingCurrent.value?.value == 5.0)
        assertTrue(state.minChargingCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.estimatedRangeTarget.value?.value == 300.0)
        assertTrue(state.estimatedRangeTarget.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.fullyChargedEndTimes.value?.weekday == Weekday.MONDAY)
        assertTrue(state.fullyChargedEndTimes.value?.time?.hour == 5)
        assertTrue(state.fullyChargedEndTimes.value?.time?.minute == 30)
        assertTrue(state.preconditioningScheduledTime.value?.hour == 5)
        assertTrue(state.preconditioningScheduledTime.value?.minute == 30)
        assertTrue(state.preconditioningRemainingTime.value?.value == 110.0)
        assertTrue(state.preconditioningRemainingTime.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.batteryVoltage.value?.value == 120.0)
        assertTrue(state.batteryVoltage.value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.batteryTempretatureExtremes.value?.extreme == TemperatureExtreme.Extreme.HIGHEST)
        assertTrue(state.batteryTempretatureExtremes.value?.temperature?.value == 85.0)
        assertTrue(state.batteryTempretatureExtremes.value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.batteryTemperatureControlDemand.value == Charging.BatteryTemperatureControlDemand.HIGH_COOLING)
        assertTrue(state.chargingCurrent.value?.value == 3.14)
        assertTrue(state.chargingCurrent.value?.unit == ElectricCurrent.Unit.AMPERES)
        assertTrue(state.batteryStatus.value == Charging.BatteryStatus.ACTIVE)
        assertTrue(state.batteryLed.value == Charging.BatteryLed.NO_COLOUR)
        assertTrue(state.batteryCoolingTemperature.value?.value == 63.4)
        assertTrue(state.batteryCoolingTemperature.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.batteryTemperatureExtremes.value?.extreme == TemperatureExtreme.Extreme.HIGHEST)
        assertTrue(state.batteryTemperatureExtremes.value?.temperature?.value == 85.0)
        assertTrue(state.batteryTemperatureExtremes.value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "002300")
        val defaultGetter = Charging.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "00230002030405060708090a0b0c0e0f1011131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142")
        val propertyGetter = Charging.GetState(0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0e, 0x0f, 0x10, 0x11, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f, 0x40, 0x41, 0x42)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("02030405060708090a0b0c0e0f1011131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "002302")
        val created = Charging.GetStateAvailability()
        assertTrue(created.identifier == Identifier.CHARGING)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.CHARGING)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("02030405060708090a0b0c0e0f1011131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142")
        val allBytes = Bytes(COMMAND_HEADER + "002302" + identifierBytes)
        val constructed = Charging.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.CHARGING)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Charging.GetStateAvailability(0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0e, 0x0f, 0x10, 0x11, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f, 0x40, 0x41, 0x42)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Charging.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.CHARGING)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun startStopCharging() {
        val bytes = Bytes(COMMAND_HEADER + "002301" +
            "17000401000101")
    
        val constructed = Charging.StartStopCharging(Charging.Status.CHARGING)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.StartStopCharging
        assertTrue(resolved.status.value == Charging.Status.CHARGING)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setChargeLimit() {
        val bytes = Bytes(COMMAND_HEADER + "002301" +
            "08000B0100083feccccccccccccd")
    
        val constructed = Charging.SetChargeLimit(0.9)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetChargeLimit
        assertTrue(resolved.chargeLimit.value == 0.9)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun openCloseChargingPort() {
        val bytes = Bytes(COMMAND_HEADER + "002301" +
            "0b000401000101")
    
        val constructed = Charging.OpenCloseChargingPort(Position.OPEN)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.OpenCloseChargingPort
        assertTrue(resolved.chargePortState.value == Position.OPEN)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setChargeMode() {
        val bytes = Bytes(COMMAND_HEADER + "002301" +
            "0c000401000101")
    
        val constructed = Charging.SetChargeMode(Charging.ChargeMode.TIMER_BASED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetChargeMode
        assertTrue(resolved.chargeMode.value == Charging.ChargeMode.TIMER_BASED)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setChargingTimers() {
        val bytes = Bytes(COMMAND_HEADER + "002301" +
            "15000C01000900000001598938e788" +
            "15000C0100090100000159893c9108" +
            "15000C0100090200000159893c9108")
    
        val constructed = Charging.SetChargingTimers(arrayListOf(
                Timer(Timer.TimerType.PREFERRED_START_TIME, getCalendar("2017-01-10T16:32:05.000Z")), 
                Timer(Timer.TimerType.PREFERRED_END_TIME, getCalendar("2017-01-10T16:36:05.000Z")), 
                Timer(Timer.TimerType.DEPARTURE_DATE, getCalendar("2017-01-10T16:36:05.000Z")))
            )
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetChargingTimers
        assertTrue(resolved.timers[0].value?.timerType == Timer.TimerType.PREFERRED_START_TIME)
        assertTrue(dateIsSame(resolved.timers[0].value?.date, "2017-01-10T16:32:05.000Z"))
        assertTrue(resolved.timers[1].value?.timerType == Timer.TimerType.PREFERRED_END_TIME)
        assertTrue(dateIsSame(resolved.timers[1].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(resolved.timers[2].value?.timerType == Timer.TimerType.DEPARTURE_DATE)
        assertTrue(dateIsSame(resolved.timers[2].value?.date, "2017-01-10T16:36:05.000Z"))
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setReductionOfChargingCurrentTimes() {
        val bytes = Bytes(COMMAND_HEADER + "002301" +
            "130006010003001121" +
            "130006010003010c34")
    
        val constructed = Charging.SetReductionOfChargingCurrentTimes(arrayListOf(
                ReductionTime(StartStop.START, Time(17, 33)), 
                ReductionTime(StartStop.STOP, Time(12, 52)))
            )
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Charging.SetReductionOfChargingCurrentTimes
        assertTrue(resolved.reductionTimes[0].value?.startStop == StartStop.START)
        assertTrue(resolved.reductionTimes[0].value?.time?.hour == 17)
        assertTrue(resolved.reductionTimes[0].value?.time?.minute == 33)
        assertTrue(resolved.reductionTimes[1].value?.startStop == StartStop.STOP)
        assertTrue(resolved.reductionTimes[1].value?.time?.hour == 12)
        assertTrue(resolved.reductionTimes[1].value?.time?.minute == 52)
        assertTrue(resolved == bytes)
    }
}