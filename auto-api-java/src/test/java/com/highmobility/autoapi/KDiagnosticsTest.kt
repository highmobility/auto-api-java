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
class KDiagnosticsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003301" + 
            "01000D01000A120441024f8000000000" +  // Odometer is showing 150'000.0km
            "02000D01000A17014058e00000000000" +  // Engine oil temperature is 99.5°C
            "03000D01000A1601404e000000000000" +  // Vehicle speed is 60.0km/h
            "04000D01000A030040a3880000000000" +  // Engine RPM is 2500.0
            "05000B0100083feccccccccccccd" +  // Fuel level is at 90%
            "06000D01000A12044070900000000000" +  // Estimated range is 256.0km
            "09000401000101" +  // Washer fluid is filled
            "0b000D01000A0a004028333333333333" +  // Battery voltage is 12.1V
            "0c000B0100083feccccccccccccd" +  // AdBlue level is at 90%
            "0d000D01000A12044097706666666666" +  // 1'500.1km driven since reset
            "0e000D01000A12044028cccccccccccd" +  // 12.4km driven since the engine start
            "0f000D01000A19024041c00000000000" +  // 35.5 L of fuel remaining
            "10000401000101" +  // ABS is active
            "11000D01000A17014034000000000000" +  // Engine coolant temperature is 20°C
            "12000D01000A0702409772999999999a" +  // The engine has operated 1'500.65h in total
            "13000D01000A190240daf0c000000000" +  // The engine has consumend 27'587.0 L of fuel over it's lifespan
            "14000401000100" +  // Brake fluid is low
            "15000B0100083fc999999999999a" +  // Engine torque is currently at 20%
            "16000B0100083fb999999999999a" +  // Engine load is currently at 10%
            "17000D01000A16014050400000000000" +  // Wheel based speed is 65.0km/h
            "18000B0100083fe1eb851eb851ec" +  // Battery level is at 56%
            "1900240100210001070140f9c78000000000000c436865636b20656e67696e650005416c657274" +  // 105'592 minutes remaining for Check Engine Alert control message with ID 1
            "1a000E01000B00150640027ae147ae147b" +  // Front left tire pressure is 2.31BAR
            "1a000E01000B01150640027ae147ae147b" +  // Front right tire pressure is 2.31BAR
            "1a000E01000B0215064001eb851eb851ec" +  // Rear right tire pressure is 2.24BAR
            "1a000E01000B0315064001eb851eb851ec" +  // Rear left tire pressure is 2.24BAR
            "1a000E01000B0415064002000000000000" +  // Rear right outer tire pressure is 2.25BAR
            "1a000E01000B0515064002000000000000" +  // Rear left outer tire pressure is 2.25BAR
            "1a000E01000B0615064002000000000000" +  // Spare tire pressure is 2.25BAR
            "1b000E01000B00170140440ccccccccccd" +  // Front left tire temperature is 40.1°C
            "1b000E01000B011701404419999999999a" +  // Front right tire temperature is 40.2°C
            "1b000E01000B0217014044266666666666" +  // Rear right tire temperature is 40.3°C
            "1b000E01000B0317014044333333333333" +  // Rear left tire temperature is 40.4°C
            "1b000E01000B0417014044400000000000" +  // Rear right outer tire temperature is 40.5°C
            "1b000E01000B05170140444ccccccccccd" +  // Rear left outer tire temperature is 40.6°C
            "1b000E01000B0617014024666666666666" +  // Spare tire temperature is 10.2°C
            "1c000E01000B0003004087080000000000" +  // Front left wheel is doing 737.0RPM
            "1c000E01000B0103004087580000000000" +  // Front right wheel is doing 747.0RPM
            "1c000E01000B0203004088480000000000" +  // Rear right wheel is doing 777.0RPM
            "1c000E01000B0303004088980000000000" +  // Rear left wheel is doing 787.0RPM
            "1c000E01000B0403004088e80000000000" +  // Rear right outer wheel is doing 797.0RPM
            "1c000E01000B0503004089380000000000" +  // Rear left outer wheel is doing 807.0RPM
            "1c000E01000B0603000000000000000000" +  // Spare wheel is doing 0.0RPM
            "1d002201001F0200074331313136464100095244555f3231324652000750454e44494e4700" +  // Trouble code 'C1116FA' with ECU-ID 'RDU_212FR' occurred 2 times and is 'PENDING'
            "1d001F01001C020007433136334146410006445452323132000750454e44494e4701" +  // Trouble code 'C163AFA' with ECU-ID 'DTR212' occurred 2 times in body-system and is 'PENDING'
            "1e000D01000A120441024f8800000000" +  // Odometer is showing 150'001km
            "1f000D01000A120440a0040000000000" +  // Odometer is showing 2050.0km
            "20000D01000A0702409772999999999a" +  // The engine has operated 1'500.65h in total
            "2100050100020000" +  // Front left tire pressure is normal
            "2100050100020101" +  // Front right tire pressure is low
            "2100050100020202" +  // Rear right tire pressure status alert
            "2100050100020300" +  // Rear left tire pressure is normal
            "2100050100020400" +  // Rear right outer tire pressure is normal
            "2100050100020500" +  // Rear left outer tire pressure is normal
            "2100050100020600" +  // Spare tire pressure is normal
            "22000401000100" +  // Brake lining wear pre-warning is inactive
            "23000B0100083fec28f5c28f5c29" +  // Engine oil life remaining is 88%
            "240024010021000531323349440018000a736f6d655f6572726f72000a736f6d655f76616c7565" +  // Trouble code '123ID' has a value 'some_value' for a key 'some_error'
            "24002D01002A0004314233430022000f696d706f7274616e745f6572726f72000f73797374656d206661756c74203332" +  // Trouble code '1B3C' has a value 'system fault 32' for a key 'important_error'
            "25000D01000A120440a1720000000000" +  // Diesel exhaust fluid is empty in 2233.0km
            "26000B0100083fc47ae147ae147b" +  // Diesel exhaust particulate filter soot level is 16%
            "27001C01001900063830314331300002313600034341530006414354495645" +  // Confirmed trouble code '801C10' with ECU address '16' and variante name "CAS" is 'ACTIVE'
            "27001C01001900064435324334340002343800034341530006414354495645" +  // Confirmed trouble code 'D52C44' with ECU address '48' and variante name "CAS" is 'ACTIVE'
            "280006010003000100" +  // Diesel 'exhaust filter' is in 'unknown' status and unknown cleaning state
            "280006010003010100" +  // Diesel 'exhaust filter' is in 'normal operation' status unknown cleaning state
            "280006010003020100" +  // Diesel 'exhaust filter' is in 'overloaded' status and unknown cleaning state
            "280006010003030100" +  // Diesel 'exhaust filter' is in 'at_limit' status and unknown cleaning state
            "280006010003040100" +  // Diesel 'exhaust filter' is in 'over_limit' status and unknown cleaning state
            "2a000D01000A0702406ab1eb851eb852" +  // The engine has operated 213.56h in idle
            "2b000D01000A1902400c000000000000" +  // Engine oil tank is filled by 3.5 l
            "2c000B0100083fe999999999999a" +  // Engile oil level is at 80%
            "2d000D01000A12044070900000000000" +  // Estimated secondary powertrain`s range is 256.0km
            "2e000401000100" +  // Fuel level has been measured
            "2f000E01000B00150640027ae147ae147b" +  // Front left tire pressure target is 2.31BAR
            "2f000E01000B01150640027ae147ae147b" +  // Front right tire pressure target is 2.31BAR
            "2f000E01000B0215064001eb851eb851ec" +  // Rear right tire pressure target is 2.24BAR
            "2f000E01000B0315064001eb851eb851ec" +  // Rear left tire pressure target is 2.24BAR
            "2f000E01000B0415064002000000000000" +  // Rear right outer tire target pressure is 2.25BAR
            "2f000E01000B0515064002000000000000" +  // Rear left outer tire target pressure is 2.25BAR
            "2f000E01000B0615064002000000000000" +  // Spear tire target pressure is 2.25BAR
            "30000E01000B0015063fb999999999999a" +  // Front left tire pressure difference is 0.1BAR
            "30000E01000B0115063fb999999999999a" +  // Front right tire pressure difference is 0.1BAR
            "30000E01000B0215063fb999999999999a" +  // Rear right tire pressure difference is 0.1BAR
            "30000E01000B0315063fb999999999999a" +  // Rear left tire pressure difference is 0.1BAR
            "30000E01000B0415063fb999999999999a" +  // Rear right outer tire pressure difference is 0.1BAR
            "30000E01000B0515063fb999999999999a" +  // Rear left outer tire pressure difference is 0.1BAR
            "30000E01000B0615063fb999999999999a" +  // Spare tire pressure difference is 0.1BAR
            "31000D01000A0701402e000000000000" +  // The backup battery has 15min remaining.
            "32000401000104" +  // Engine coolant level is high
            "33000401000103" +  // Engile oil level is at normal
            "34000401000101" +  // Engine oil pressure is normal
            "35000D01000A0702407f500000000000" +  // 501.0 engine hours until next service
            "36000401000100" +  // Low voltage battery charge level is ok
            "37000401000100" +  // Engine oil service status is 'ok'
            "38000401000101" // Passenger airbag is `active`
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Diagnostics.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Diagnostics.State.Builder()
        builder.setMileage(Property(Length(150000.0, Length.Unit.KILOMETERS)))
        builder.setEngineOilTemperature(Property(Temperature(99.5, Temperature.Unit.CELSIUS)))
        builder.setSpeed(Property(Speed(60.0, Speed.Unit.KILOMETERS_PER_HOUR)))
        builder.setEngineRPM(Property(AngularVelocity(2500.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)))
        builder.setFuelLevel(Property(0.9))
        builder.setEstimatedRange(Property(Length(265.0, Length.Unit.KILOMETERS)))
        builder.setWasherFluidLevel(Property(FluidLevel.FILLED))
        builder.setBatteryVoltage(Property(ElectricPotentialDifference(12.1, ElectricPotentialDifference.Unit.VOLTS)))
        builder.setAdBlueLevel(Property(0.9))
        builder.setDistanceSinceReset(Property(Length(1500.1, Length.Unit.KILOMETERS)))
        builder.setDistanceSinceStart(Property(Length(12.4, Length.Unit.KILOMETERS)))
        builder.setFuelVolume(Property(Volume(35.5, Volume.Unit.LITERS)))
        builder.setAntiLockBraking(Property(ActiveState.ACTIVE))
        builder.setEngineCoolantTemperature(Property(Temperature(20.0, Temperature.Unit.CELSIUS)))
        builder.setEngineTotalOperatingHours(Property(Duration(1500.65, Duration.Unit.HOURS)))
        builder.setEngineTotalFuelConsumption(Property(Volume(27587.0, Volume.Unit.LITERS)))
        builder.setBrakeFluidLevel(Property(FluidLevel.LOW))
        builder.setEngineTorque(Property(0.2))
        builder.setEngineLoad(Property(0.1))
        builder.setWheelBasedSpeed(Property(Speed(65.0, Speed.Unit.KILOMETERS_PER_HOUR)))
        builder.setBatteryLevel(Property(0.56))
        builder.addCheckControlMessage(Property(CheckControlMessage(1, Duration(105592.0, Duration.Unit.MINUTES), "Check engine", "Alert")))
        builder.addTirePressure(Property(TirePressure(LocationWheel.FRONT_LEFT, Pressure(2.31, Pressure.Unit.BARS))))
        builder.addTirePressure(Property(TirePressure(LocationWheel.FRONT_RIGHT, Pressure(2.31, Pressure.Unit.BARS))))
        builder.addTirePressure(Property(TirePressure(LocationWheel.REAR_RIGHT, Pressure(2.24, Pressure.Unit.BARS))))
        builder.addTirePressure(Property(TirePressure(LocationWheel.REAR_LEFT, Pressure(2.24, Pressure.Unit.BARS))))
        builder.addTirePressure(Property(TirePressure(LocationWheel.REAR_RIGHT_OUTER, Pressure(2.25, Pressure.Unit.BARS))))
        builder.addTirePressure(Property(TirePressure(LocationWheel.REAR_LEFT_OUTER, Pressure(2.25, Pressure.Unit.BARS))))
        builder.addTirePressure(Property(TirePressure(LocationWheel.SPARE, Pressure(2.25, Pressure.Unit.BARS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.FRONT_LEFT, Temperature(40.1, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.FRONT_RIGHT, Temperature(40.2, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_RIGHT, Temperature(40.3, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_LEFT, Temperature(40.4, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_RIGHT_OUTER, Temperature(40.5, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_LEFT_OUTER, Temperature(40.6, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.SPARE, Temperature(10.2, Temperature.Unit.CELSIUS))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.FRONT_LEFT, AngularVelocity(737.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.FRONT_RIGHT, AngularVelocity(747.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_RIGHT, AngularVelocity(777.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_LEFT, AngularVelocity(787.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_RIGHT_OUTER, AngularVelocity(797.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_LEFT_OUTER, AngularVelocity(807.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.SPARE, AngularVelocity(0.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addTroubleCode(Property(TroubleCode(2, "C1116FA", "RDU_212FR", "PENDING", TroubleCode.System.UNKNOWN)))
        builder.addTroubleCode(Property(TroubleCode(2, "C163AFA", "DTR212", "PENDING", TroubleCode.System.BODY)))
        builder.setMileageMeters(Property(Length(150001.0, Length.Unit.KILOMETERS)))
        builder.setOdometer(Property(Length(2050.0, Length.Unit.KILOMETERS)))
        builder.setEngineTotalOperatingTime(Property(Duration(1500.65, Duration.Unit.HOURS)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.FRONT_LEFT, TirePressureStatus.Status.NORMAL)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.FRONT_RIGHT, TirePressureStatus.Status.LOW)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.REAR_RIGHT, TirePressureStatus.Status.ALERT)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.REAR_LEFT, TirePressureStatus.Status.NORMAL)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.REAR_RIGHT_OUTER, TirePressureStatus.Status.NORMAL)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.REAR_LEFT_OUTER, TirePressureStatus.Status.NORMAL)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.SPARE, TirePressureStatus.Status.NORMAL)))
        builder.setBrakeLiningWearPreWarning(Property(ActiveState.INACTIVE))
        builder.setEngineOilLifeRemaining(Property(0.88))
        builder.addEngineTroubleCodeValue(Property(OemTroubleCodeValue("123ID", KeyValue("some_error", "some_value"))))
        builder.addEngineTroubleCodeValue(Property(OemTroubleCodeValue("1B3C", KeyValue("important_error", "system fault 32"))))
        builder.setDieselExhaustFluidRange(Property(Length(2233.0, Length.Unit.KILOMETERS)))
        builder.setDieselParticulateFilterSootLevel(Property(0.16))
        builder.addConfirmedTroubleCode(Property(ConfirmedTroubleCode("801C10", "16", "CAS", "ACTIVE")))
        builder.addConfirmedTroubleCode(Property(ConfirmedTroubleCode("D52C44", "48", "CAS", "ACTIVE")))
        builder.addDieselExhaustFilterStatu(Property(DieselExhaustFilterStatus(DieselExhaustFilterStatus.Status.UNKNOWN, DieselExhaustFilterStatus.Component.EXHAUST_FILTER, DieselExhaustFilterStatus.Cleaning.UNKNOWN)))
        builder.addDieselExhaustFilterStatu(Property(DieselExhaustFilterStatus(DieselExhaustFilterStatus.Status.NORMAL_OPERATION, DieselExhaustFilterStatus.Component.EXHAUST_FILTER, DieselExhaustFilterStatus.Cleaning.UNKNOWN)))
        builder.addDieselExhaustFilterStatu(Property(DieselExhaustFilterStatus(DieselExhaustFilterStatus.Status.OVERLOADED, DieselExhaustFilterStatus.Component.EXHAUST_FILTER, DieselExhaustFilterStatus.Cleaning.UNKNOWN)))
        builder.addDieselExhaustFilterStatu(Property(DieselExhaustFilterStatus(DieselExhaustFilterStatus.Status.AT_LIMIT, DieselExhaustFilterStatus.Component.EXHAUST_FILTER, DieselExhaustFilterStatus.Cleaning.UNKNOWN)))
        builder.addDieselExhaustFilterStatu(Property(DieselExhaustFilterStatus(DieselExhaustFilterStatus.Status.OVER_LIMIT, DieselExhaustFilterStatus.Component.EXHAUST_FILTER, DieselExhaustFilterStatus.Cleaning.UNKNOWN)))
        builder.setEngineTotalIdleOperatingTime(Property(Duration(213.56, Duration.Unit.HOURS)))
        builder.setEngineOilAmount(Property(Volume(3.5, Volume.Unit.LITERS)))
        builder.setEngineOilLevel(Property(0.8))
        builder.setEstimatedSecondaryPowertrainRange(Property(Length(265.0, Length.Unit.KILOMETERS)))
        builder.setFuelLevelAccuracy(Property(Diagnostics.FuelLevelAccuracy.MEASURED))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.FRONT_LEFT, Pressure(2.31, Pressure.Unit.BARS))))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.FRONT_RIGHT, Pressure(2.31, Pressure.Unit.BARS))))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.REAR_RIGHT, Pressure(2.24, Pressure.Unit.BARS))))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.REAR_LEFT, Pressure(2.24, Pressure.Unit.BARS))))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.REAR_RIGHT_OUTER, Pressure(2.25, Pressure.Unit.BARS))))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.REAR_LEFT_OUTER, Pressure(2.25, Pressure.Unit.BARS))))
        builder.addTirePressureTarget(Property(TirePressure(LocationWheel.SPARE, Pressure(2.25, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.FRONT_LEFT, Pressure(0.1, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.FRONT_RIGHT, Pressure(0.1, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.REAR_RIGHT, Pressure(0.1, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.REAR_LEFT, Pressure(0.1, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.REAR_RIGHT_OUTER, Pressure(0.1, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.REAR_LEFT_OUTER, Pressure(0.1, Pressure.Unit.BARS))))
        builder.addTirePressureDifference(Property(TirePressure(LocationWheel.SPARE, Pressure(0.1, Pressure.Unit.BARS))))
        builder.setBackupBatteryRemainingTime(Property(Duration(15.0, Duration.Unit.MINUTES)))
        builder.setEngineCoolantFluidLevel(Property(FluidLevel.HIGH))
        builder.setEngineOilFluidLevel(Property(FluidLevel.NORMAL))
        builder.setEngineOilPressureLevel(Property(Diagnostics.EngineOilPressureLevel.NORMAL))
        builder.setEngineTimeToNextService(Property(Duration(501.0, Duration.Unit.HOURS)))
        builder.setLowVoltageBatteryChargeLevel(Property(Diagnostics.LowVoltageBatteryChargeLevel.OK))
        builder.setEngineOilServiceStatus(Property(ServiceStatus.OK))
        builder.setPassengerAirbagStatus(Property(ActiveState.ACTIVE))
        testState(builder.build())
    }
    
    private fun testState(state: Diagnostics.State) {
        assertTrue(state.mileage.value?.value == 150000.0)
        assertTrue(state.mileage.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.engineOilTemperature.value?.value == 99.5)
        assertTrue(state.engineOilTemperature.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.speed.value?.value == 60.0)
        assertTrue(state.speed.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.engineRPM.value?.value == 2500.0)
        assertTrue(state.engineRPM.value?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.fuelLevel.value == 0.9)
        assertTrue(state.estimatedRange.value?.value == 265.0)
        assertTrue(state.estimatedRange.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.washerFluidLevel.value == FluidLevel.FILLED)
        assertTrue(state.batteryVoltage.value?.value == 12.1)
        assertTrue(state.batteryVoltage.value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.adBlueLevel.value == 0.9)
        assertTrue(state.distanceSinceReset.value?.value == 1500.1)
        assertTrue(state.distanceSinceReset.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.distanceSinceStart.value?.value == 12.4)
        assertTrue(state.distanceSinceStart.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.fuelVolume.value?.value == 35.5)
        assertTrue(state.fuelVolume.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.antiLockBraking.value == ActiveState.ACTIVE)
        assertTrue(state.engineCoolantTemperature.value?.value == 20.0)
        assertTrue(state.engineCoolantTemperature.value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.engineTotalOperatingHours.value?.value == 1500.65)
        assertTrue(state.engineTotalOperatingHours.value?.unit == Duration.Unit.HOURS)
        assertTrue(state.engineTotalFuelConsumption.value?.value == 27587.0)
        assertTrue(state.engineTotalFuelConsumption.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.brakeFluidLevel.value == FluidLevel.LOW)
        assertTrue(state.engineTorque.value == 0.2)
        assertTrue(state.engineLoad.value == 0.1)
        assertTrue(state.wheelBasedSpeed.value?.value == 65.0)
        assertTrue(state.wheelBasedSpeed.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.batteryLevel.value == 0.56)
        assertTrue(state.checkControlMessages[0].value?.id == 1)
        assertTrue(state.checkControlMessages[0].value?.remainingTime?.value == 105592.0)
        assertTrue(state.checkControlMessages[0].value?.remainingTime?.unit == Duration.Unit.MINUTES)
        assertTrue(state.checkControlMessages[0].value?.text == "Check engine")
        assertTrue(state.checkControlMessages[0].value?.status == "Alert")
        assertTrue(state.tirePressures[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.tirePressures[0].value?.pressure?.value == 2.31)
        assertTrue(state.tirePressures[0].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressures[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.tirePressures[1].value?.pressure?.value == 2.31)
        assertTrue(state.tirePressures[1].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressures[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.tirePressures[2].value?.pressure?.value == 2.24)
        assertTrue(state.tirePressures[2].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressures[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.tirePressures[3].value?.pressure?.value == 2.24)
        assertTrue(state.tirePressures[3].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressures[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.tirePressures[4].value?.pressure?.value == 2.25)
        assertTrue(state.tirePressures[4].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressures[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.tirePressures[5].value?.pressure?.value == 2.25)
        assertTrue(state.tirePressures[5].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressures[6].value?.location == LocationWheel.SPARE)
        assertTrue(state.tirePressures[6].value?.pressure?.value == 2.25)
        assertTrue(state.tirePressures[6].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tireTemperatures[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.tireTemperatures[0].value?.temperature?.value == 40.1)
        assertTrue(state.tireTemperatures[0].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.tireTemperatures[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.tireTemperatures[1].value?.temperature?.value == 40.2)
        assertTrue(state.tireTemperatures[1].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.tireTemperatures[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.tireTemperatures[2].value?.temperature?.value == 40.3)
        assertTrue(state.tireTemperatures[2].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.tireTemperatures[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.tireTemperatures[3].value?.temperature?.value == 40.4)
        assertTrue(state.tireTemperatures[3].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.tireTemperatures[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.tireTemperatures[4].value?.temperature?.value == 40.5)
        assertTrue(state.tireTemperatures[4].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.tireTemperatures[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.tireTemperatures[5].value?.temperature?.value == 40.6)
        assertTrue(state.tireTemperatures[5].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.tireTemperatures[6].value?.location == LocationWheel.SPARE)
        assertTrue(state.tireTemperatures[6].value?.temperature?.value == 10.2)
        assertTrue(state.tireTemperatures[6].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.wheelRPMs[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.wheelRPMs[0].value?.rpm?.value == 737.0)
        assertTrue(state.wheelRPMs[0].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.wheelRPMs[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.wheelRPMs[1].value?.rpm?.value == 747.0)
        assertTrue(state.wheelRPMs[1].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.wheelRPMs[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.wheelRPMs[2].value?.rpm?.value == 777.0)
        assertTrue(state.wheelRPMs[2].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.wheelRPMs[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.wheelRPMs[3].value?.rpm?.value == 787.0)
        assertTrue(state.wheelRPMs[3].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.wheelRPMs[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.wheelRPMs[4].value?.rpm?.value == 797.0)
        assertTrue(state.wheelRPMs[4].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.wheelRPMs[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.wheelRPMs[5].value?.rpm?.value == 807.0)
        assertTrue(state.wheelRPMs[5].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.wheelRPMs[6].value?.location == LocationWheel.SPARE)
        assertTrue(state.wheelRPMs[6].value?.rpm?.value == 0.0)
        assertTrue(state.wheelRPMs[6].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.troubleCodes[0].value?.occurrences == 2)
        assertTrue(state.troubleCodes[0].value?.id == "C1116FA")
        assertTrue(state.troubleCodes[0].value?.ecuID == "RDU_212FR")
        assertTrue(state.troubleCodes[0].value?.status == "PENDING")
        assertTrue(state.troubleCodes[0].value?.system == TroubleCode.System.UNKNOWN)
        assertTrue(state.troubleCodes[1].value?.occurrences == 2)
        assertTrue(state.troubleCodes[1].value?.id == "C163AFA")
        assertTrue(state.troubleCodes[1].value?.ecuID == "DTR212")
        assertTrue(state.troubleCodes[1].value?.status == "PENDING")
        assertTrue(state.troubleCodes[1].value?.system == TroubleCode.System.BODY)
        assertTrue(state.mileageMeters.value?.value == 150001.0)
        assertTrue(state.mileageMeters.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.odometer.value?.value == 2050.0)
        assertTrue(state.odometer.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.engineTotalOperatingTime.value?.value == 1500.65)
        assertTrue(state.engineTotalOperatingTime.value?.unit == Duration.Unit.HOURS)
        assertTrue(state.tirePressureStatuses[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.tirePressureStatuses[0].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.tirePressureStatuses[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.tirePressureStatuses[1].value?.status == TirePressureStatus.Status.LOW)
        assertTrue(state.tirePressureStatuses[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.tirePressureStatuses[2].value?.status == TirePressureStatus.Status.ALERT)
        assertTrue(state.tirePressureStatuses[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.tirePressureStatuses[3].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.tirePressureStatuses[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.tirePressureStatuses[4].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.tirePressureStatuses[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.tirePressureStatuses[5].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.tirePressureStatuses[6].value?.location == LocationWheel.SPARE)
        assertTrue(state.tirePressureStatuses[6].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.brakeLiningWearPreWarning.value == ActiveState.INACTIVE)
        assertTrue(state.engineOilLifeRemaining.value == 0.88)
        assertTrue(state.oemTroubleCodeValues[0].value?.id == "123ID")
        assertTrue(state.oemTroubleCodeValues[0].value?.keyValue?.key == "some_error")
        assertTrue(state.oemTroubleCodeValues[0].value?.keyValue?.value == "some_value")
        assertTrue(state.oemTroubleCodeValues[1].value?.id == "1B3C")
        assertTrue(state.oemTroubleCodeValues[1].value?.keyValue?.key == "important_error")
        assertTrue(state.oemTroubleCodeValues[1].value?.keyValue?.value == "system fault 32")
        assertTrue(state.dieselExhaustFluidRange.value?.value == 2233.0)
        assertTrue(state.dieselExhaustFluidRange.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.dieselParticulateFilterSootLevel.value == 0.16)
        assertTrue(state.confirmedTroubleCodes[0].value?.id == "801C10")
        assertTrue(state.confirmedTroubleCodes[0].value?.ecuAddress == "16")
        assertTrue(state.confirmedTroubleCodes[0].value?.ecuVariantName == "CAS")
        assertTrue(state.confirmedTroubleCodes[0].value?.status == "ACTIVE")
        assertTrue(state.confirmedTroubleCodes[1].value?.id == "D52C44")
        assertTrue(state.confirmedTroubleCodes[1].value?.ecuAddress == "48")
        assertTrue(state.confirmedTroubleCodes[1].value?.ecuVariantName == "CAS")
        assertTrue(state.confirmedTroubleCodes[1].value?.status == "ACTIVE")
        assertTrue(state.dieselExhaustFilterStatus[0].value?.status == DieselExhaustFilterStatus.Status.UNKNOWN)
        assertTrue(state.dieselExhaustFilterStatus[0].value?.component == DieselExhaustFilterStatus.Component.EXHAUST_FILTER)
        assertTrue(state.dieselExhaustFilterStatus[0].value?.cleaning == DieselExhaustFilterStatus.Cleaning.UNKNOWN)
        assertTrue(state.dieselExhaustFilterStatus[1].value?.status == DieselExhaustFilterStatus.Status.NORMAL_OPERATION)
        assertTrue(state.dieselExhaustFilterStatus[1].value?.component == DieselExhaustFilterStatus.Component.EXHAUST_FILTER)
        assertTrue(state.dieselExhaustFilterStatus[1].value?.cleaning == DieselExhaustFilterStatus.Cleaning.UNKNOWN)
        assertTrue(state.dieselExhaustFilterStatus[2].value?.status == DieselExhaustFilterStatus.Status.OVERLOADED)
        assertTrue(state.dieselExhaustFilterStatus[2].value?.component == DieselExhaustFilterStatus.Component.EXHAUST_FILTER)
        assertTrue(state.dieselExhaustFilterStatus[2].value?.cleaning == DieselExhaustFilterStatus.Cleaning.UNKNOWN)
        assertTrue(state.dieselExhaustFilterStatus[3].value?.status == DieselExhaustFilterStatus.Status.AT_LIMIT)
        assertTrue(state.dieselExhaustFilterStatus[3].value?.component == DieselExhaustFilterStatus.Component.EXHAUST_FILTER)
        assertTrue(state.dieselExhaustFilterStatus[3].value?.cleaning == DieselExhaustFilterStatus.Cleaning.UNKNOWN)
        assertTrue(state.dieselExhaustFilterStatus[4].value?.status == DieselExhaustFilterStatus.Status.OVER_LIMIT)
        assertTrue(state.dieselExhaustFilterStatus[4].value?.component == DieselExhaustFilterStatus.Component.EXHAUST_FILTER)
        assertTrue(state.dieselExhaustFilterStatus[4].value?.cleaning == DieselExhaustFilterStatus.Cleaning.UNKNOWN)
        assertTrue(state.engineTotalIdleOperatingTime.value?.value == 213.56)
        assertTrue(state.engineTotalIdleOperatingTime.value?.unit == Duration.Unit.HOURS)
        assertTrue(state.engineOilAmount.value?.value == 3.5)
        assertTrue(state.engineOilAmount.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.engineOilLevel.value == 0.8)
        assertTrue(state.estimatedSecondaryPowertrainRange.value?.value == 265.0)
        assertTrue(state.estimatedSecondaryPowertrainRange.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.fuelLevelAccuracy.value == Diagnostics.FuelLevelAccuracy.MEASURED)
        assertTrue(state.tirePressuresTargets[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.tirePressuresTargets[0].value?.pressure?.value == 2.31)
        assertTrue(state.tirePressuresTargets[0].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresTargets[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.tirePressuresTargets[1].value?.pressure?.value == 2.31)
        assertTrue(state.tirePressuresTargets[1].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresTargets[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.tirePressuresTargets[2].value?.pressure?.value == 2.24)
        assertTrue(state.tirePressuresTargets[2].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresTargets[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.tirePressuresTargets[3].value?.pressure?.value == 2.24)
        assertTrue(state.tirePressuresTargets[3].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresTargets[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.tirePressuresTargets[4].value?.pressure?.value == 2.25)
        assertTrue(state.tirePressuresTargets[4].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresTargets[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.tirePressuresTargets[5].value?.pressure?.value == 2.25)
        assertTrue(state.tirePressuresTargets[5].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresTargets[6].value?.location == LocationWheel.SPARE)
        assertTrue(state.tirePressuresTargets[6].value?.pressure?.value == 2.25)
        assertTrue(state.tirePressuresTargets[6].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.tirePressuresDifferences[0].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[0].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.tirePressuresDifferences[1].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[1].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.tirePressuresDifferences[2].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[2].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.tirePressuresDifferences[3].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[3].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.tirePressuresDifferences[4].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[4].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.tirePressuresDifferences[5].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[5].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.tirePressuresDifferences[6].value?.location == LocationWheel.SPARE)
        assertTrue(state.tirePressuresDifferences[6].value?.pressure?.value == 0.1)
        assertTrue(state.tirePressuresDifferences[6].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.backupBatteryRemainingTime.value?.value == 15.0)
        assertTrue(state.backupBatteryRemainingTime.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.engineCoolantFluidLevel.value == FluidLevel.HIGH)
        assertTrue(state.engineOilFluidLevel.value == FluidLevel.NORMAL)
        assertTrue(state.engineOilPressureLevel.value == Diagnostics.EngineOilPressureLevel.NORMAL)
        assertTrue(state.engineTimeToNextService.value?.value == 501.0)
        assertTrue(state.engineTimeToNextService.value?.unit == Duration.Unit.HOURS)
        assertTrue(state.lowVoltageBatteryChargeLevel.value == Diagnostics.LowVoltageBatteryChargeLevel.OK)
        assertTrue(state.engineOilServiceStatus.value == ServiceStatus.OK)
        assertTrue(state.passengerAirbagStatus.value == ActiveState.ACTIVE)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "003300")
        val defaultGetter = Diagnostics.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "003300010203040506090b0c0d0e0f101112131415161718191a1b1c1d1e1f2021222324252627282a2b2c2d2e2f303132333435363738")
        val propertyGetter = Diagnostics.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x09, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010203040506090b0c0d0e0f101112131415161718191a1b1c1d1e1f2021222324252627282a2b2c2d2e2f303132333435363738"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "003302")
        val created = Diagnostics.GetStateAvailability()
        assertTrue(created.identifier == Identifier.DIAGNOSTICS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Diagnostics.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.DIAGNOSTICS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("010203040506090b0c0d0e0f101112131415161718191a1b1c1d1e1f2021222324252627282a2b2c2d2e2f303132333435363738")
        val allBytes = Bytes(COMMAND_HEADER + "003302" + identifierBytes)
        val constructed = Diagnostics.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.DIAGNOSTICS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Diagnostics.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x09, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Diagnostics.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.DIAGNOSTICS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}