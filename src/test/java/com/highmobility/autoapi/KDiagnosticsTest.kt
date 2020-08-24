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
            "0c000D01000A19023fe199999999999a" +  // 0.55L of AdBlue remaining
            "0d000D01000A12044097706666666666" +  // 1'500.1km driven since reset
            "0e000D01000A12044028cccccccccccd" +  // 12.4km driven since the engine start
            "0f000D01000A19024041c00000000000" +  // 35.5L of fuel remaining
            "10000401000101" +  // ABS is active
            "11000D01000A17014034000000000000" +  // Engine coolant temperature is 20°C
            "12000D01000A0702409772999999999a" +  // The engine has operated 1'500.65h in total
            "13000D01000A190240daf0c000000000" +  // The engine has consumend 27'587.0L of fuel over it's lifespan
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
            "1b000E01000B00170140440ccccccccccd" +  // Front left tire temperature is 40.1°C
            "1b000E01000B011701404419999999999a" +  // Front right tire temperature is 40.2°C
            "1b000E01000B0217014044266666666666" +  // Rear right tire temperature is 40.3°C
            "1b000E01000B0317014044333333333333" +  // Rear left tire temperature is 40.4°C
            "1b000E01000B0417014044400000000000" +  // Rear right outer tire temperature is 40.5°C
            "1b000E01000B05170140444ccccccccccd" +  // Rear left outer tire temperature is 40.6°C
            "1c000E01000B0003004087080000000000" +  // Front left wheel is doing 737.0RPM
            "1c000E01000B0103004087580000000000" +  // Front right wheel is doing 747.0RPM
            "1c000E01000B0203004088480000000000" +  // Rear right wheel is doing 777.0RPM
            "1c000E01000B0303004088980000000000" +  // Rear left wheel is doing 787.0RPM
            "1c000E01000B0403004088e80000000000" +  // Rear right outer wheel is doing 797.0RPM
            "1c000E01000B0503004089380000000000" +  // Rear left outer wheel is doing 807.0RPM
            "1d002201001F0200074331313136464100095244555f3231324652000750454e44494e4700" +  // Trouble code 'C1116FA' with ECU-ID 'RDU_212FR' occurred 2 times and is 'PENDING'
            "1d001F01001C020007433136334146410006445452323132000750454e44494e4701" +  // Trouble code 'C163AFA' with ECU-ID 'DTR212' occurred 2 times in body-system and is 'PENDING'
            "1e000D01000A120441024f8800000000" +  // Odometer is showing 150'001km
            "1f000D01000A1205401999999999999a" +  // Odometer is showing 6.4Mm – length of the Great Wall.
            "20000D01000A0702409772999999999a" +  // The engine has operated 1'500.65h in total
            "2100050100020000" +  // Front left tire pressure is normal
            "2100050100020101" +  // Front right tire pressure is low
            "2100050100020202" +  // Rear right tire pressure status alert
            "2100050100020300" +  // Rear left tire pressure is normal
            "22000401000100" +  // Brake lining wear pre-warning is inactive
            "23000B0100083fec28f5c28f5c29" +  // Engine oil life remaining is 88%
            "240024010021000531323349440018000a736f6d655f6572726f72000a736f6d655f76616c7565" +  // Trouble code '123ID' has a value 'some_value' for a key 'some_error'
            "24002D01002A0004314233430022000f696d706f7274616e745f6572726f72000f73797374656d206661756c74203332" +  // Trouble code '1B3C' has a value 'system fault 32' for a key 'important_error'
            "25000D01000A120440a1720000000000" +  // Diesel exhaust fluid is empty in 2233.0km
            "26000B0100083fc47ae147ae147b" // Diesel exhaust particulate filter soot level is 16%
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
        builder.setAdBlueLevel(Property(Volume(0.55, Volume.Unit.LITERS)))
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
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.FRONT_LEFT, Temperature(40.1, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.FRONT_RIGHT, Temperature(40.2, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_RIGHT, Temperature(40.3, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_LEFT, Temperature(40.4, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_RIGHT_OUTER, Temperature(40.5, Temperature.Unit.CELSIUS))))
        builder.addTireTemperature(Property(TireTemperature(LocationWheel.REAR_LEFT_OUTER, Temperature(40.6, Temperature.Unit.CELSIUS))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.FRONT_LEFT, AngularVelocity(737.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.FRONT_RIGHT, AngularVelocity(747.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_RIGHT, AngularVelocity(777.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_LEFT, AngularVelocity(787.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_RIGHT_OUTER, AngularVelocity(797.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addWheelRpm(Property(WheelRpm(LocationWheel.REAR_LEFT_OUTER, AngularVelocity(807.0, AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE))))
        builder.addTroubleCode(Property(TroubleCode(2, "C1116FA", "RDU_212FR", "PENDING", TroubleCode.System.UNKNOWN)))
        builder.addTroubleCode(Property(TroubleCode(2, "C163AFA", "DTR212", "PENDING", TroubleCode.System.BODY)))
        builder.setMileageMeters(Property(Length(150001.0, Length.Unit.KILOMETERS)))
        builder.setOdometer(Property(Length(6.4, Length.Unit.MEGAMETERS)))
        builder.setEngineTotalOperatingTime(Property(Duration(1500.65, Duration.Unit.HOURS)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.FRONT_LEFT, TirePressureStatus.Status.NORMAL)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.FRONT_RIGHT, TirePressureStatus.Status.LOW)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.REAR_RIGHT, TirePressureStatus.Status.ALERT)))
        builder.addTirePressureStatus(Property(TirePressureStatus(LocationWheel.REAR_LEFT, TirePressureStatus.Status.NORMAL)))
        builder.setBrakeLiningWearPreWarning(Property(ActiveState.INACTIVE))
        builder.setEngineOilLifeRemaining(Property(0.88))
        builder.addOemTroubleCodeValue(Property(OemTroubleCodeValue("123ID", KeyValue("some_error", "some_value"))))
        builder.addOemTroubleCodeValue(Property(OemTroubleCodeValue("1B3C", KeyValue("important_error", "system fault 32"))))
        builder.setDieselExhaustFluidRange(Property(Length(2233.0, Length.Unit.KILOMETERS)))
        builder.setDieselParticulateFilterSootLevel(Property(0.16))
        testState(builder.build())
    }
    
    private fun testState(state: Diagnostics.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getMileage().value?.value == 150000.0)
        assertTrue(state.getMileage().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getEngineOilTemperature().value?.value == 99.5)
        assertTrue(state.getEngineOilTemperature().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getSpeed().value?.value == 60.0)
        assertTrue(state.getSpeed().value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.getEngineRPM().value?.value == 2500.0)
        assertTrue(state.getEngineRPM().value?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getFuelLevel().value == 0.9)
        assertTrue(state.getEstimatedRange().value?.value == 265.0)
        assertTrue(state.getEstimatedRange().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getWasherFluidLevel().value == FluidLevel.FILLED)
        assertTrue(state.getBatteryVoltage().value?.value == 12.1)
        assertTrue(state.getBatteryVoltage().value?.unit == ElectricPotentialDifference.Unit.VOLTS)
        assertTrue(state.getAdBlueLevel().value?.value == 0.55)
        assertTrue(state.getAdBlueLevel().value?.unit == Volume.Unit.LITERS)
        assertTrue(state.getDistanceSinceReset().value?.value == 1500.1)
        assertTrue(state.getDistanceSinceReset().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getDistanceSinceStart().value?.value == 12.4)
        assertTrue(state.getDistanceSinceStart().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getFuelVolume().value?.value == 35.5)
        assertTrue(state.getFuelVolume().value?.unit == Volume.Unit.LITERS)
        assertTrue(state.getAntiLockBraking().value == ActiveState.ACTIVE)
        assertTrue(state.getEngineCoolantTemperature().value?.value == 20.0)
        assertTrue(state.getEngineCoolantTemperature().value?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getEngineTotalOperatingHours().value?.value == 1500.65)
        assertTrue(state.getEngineTotalOperatingHours().value?.unit == Duration.Unit.HOURS)
        assertTrue(state.getEngineTotalFuelConsumption().value?.value == 27587.0)
        assertTrue(state.getEngineTotalFuelConsumption().value?.unit == Volume.Unit.LITERS)
        assertTrue(state.getBrakeFluidLevel().value == FluidLevel.LOW)
        assertTrue(state.getEngineTorque().value == 0.2)
        assertTrue(state.getEngineLoad().value == 0.1)
        assertTrue(state.getWheelBasedSpeed().value?.value == 65.0)
        assertTrue(state.getWheelBasedSpeed().value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.getBatteryLevel().value == 0.56)
        assertTrue(state.getCheckControlMessages()[0].value?.id == 1)
        assertTrue(state.getCheckControlMessages()[0].value?.remainingTime?.value == 105592.0)
        assertTrue(state.getCheckControlMessages()[0].value?.remainingTime?.unit == Duration.Unit.MINUTES)
        assertTrue(state.getCheckControlMessages()[0].value?.text == "Check engine")
        assertTrue(state.getCheckControlMessages()[0].value?.status == "Alert")
        assertTrue(state.getTirePressures()[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.getTirePressures()[0].value?.pressure?.value == 2.31)
        assertTrue(state.getTirePressures()[0].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.getTirePressures()[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.getTirePressures()[1].value?.pressure?.value == 2.31)
        assertTrue(state.getTirePressures()[1].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.getTirePressures()[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.getTirePressures()[2].value?.pressure?.value == 2.24)
        assertTrue(state.getTirePressures()[2].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.getTirePressures()[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.getTirePressures()[3].value?.pressure?.value == 2.24)
        assertTrue(state.getTirePressures()[3].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.getTirePressures()[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.getTirePressures()[4].value?.pressure?.value == 2.25)
        assertTrue(state.getTirePressures()[4].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.getTirePressures()[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.getTirePressures()[5].value?.pressure?.value == 2.25)
        assertTrue(state.getTirePressures()[5].value?.pressure?.unit == Pressure.Unit.BARS)
        assertTrue(state.getTireTemperatures()[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.getTireTemperatures()[0].value?.temperature?.value == 40.1)
        assertTrue(state.getTireTemperatures()[0].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getTireTemperatures()[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.getTireTemperatures()[1].value?.temperature?.value == 40.2)
        assertTrue(state.getTireTemperatures()[1].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getTireTemperatures()[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.getTireTemperatures()[2].value?.temperature?.value == 40.3)
        assertTrue(state.getTireTemperatures()[2].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getTireTemperatures()[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.getTireTemperatures()[3].value?.temperature?.value == 40.4)
        assertTrue(state.getTireTemperatures()[3].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getTireTemperatures()[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.getTireTemperatures()[4].value?.temperature?.value == 40.5)
        assertTrue(state.getTireTemperatures()[4].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getTireTemperatures()[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.getTireTemperatures()[5].value?.temperature?.value == 40.6)
        assertTrue(state.getTireTemperatures()[5].value?.temperature?.unit == Temperature.Unit.CELSIUS)
        assertTrue(state.getWheelRPMs()[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.getWheelRPMs()[0].value?.rpm?.value == 737.0)
        assertTrue(state.getWheelRPMs()[0].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getWheelRPMs()[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.getWheelRPMs()[1].value?.rpm?.value == 747.0)
        assertTrue(state.getWheelRPMs()[1].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getWheelRPMs()[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.getWheelRPMs()[2].value?.rpm?.value == 777.0)
        assertTrue(state.getWheelRPMs()[2].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getWheelRPMs()[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.getWheelRPMs()[3].value?.rpm?.value == 787.0)
        assertTrue(state.getWheelRPMs()[3].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getWheelRPMs()[4].value?.location == LocationWheel.REAR_RIGHT_OUTER)
        assertTrue(state.getWheelRPMs()[4].value?.rpm?.value == 797.0)
        assertTrue(state.getWheelRPMs()[4].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getWheelRPMs()[5].value?.location == LocationWheel.REAR_LEFT_OUTER)
        assertTrue(state.getWheelRPMs()[5].value?.rpm?.value == 807.0)
        assertTrue(state.getWheelRPMs()[5].value?.rpm?.unit == AngularVelocity.Unit.REVOLUTIONS_PER_MINUTE)
        assertTrue(state.getTroubleCodes()[0].value?.occurrences == 2)
        assertTrue(state.getTroubleCodes()[0].value?.id == "C1116FA")
        assertTrue(state.getTroubleCodes()[0].value?.ecuID == "RDU_212FR")
        assertTrue(state.getTroubleCodes()[0].value?.status == "PENDING")
        assertTrue(state.getTroubleCodes()[0].value?.system == TroubleCode.System.UNKNOWN)
        assertTrue(state.getTroubleCodes()[1].value?.occurrences == 2)
        assertTrue(state.getTroubleCodes()[1].value?.id == "C163AFA")
        assertTrue(state.getTroubleCodes()[1].value?.ecuID == "DTR212")
        assertTrue(state.getTroubleCodes()[1].value?.status == "PENDING")
        assertTrue(state.getTroubleCodes()[1].value?.system == TroubleCode.System.BODY)
        assertTrue(state.getMileageMeters().value?.value == 150001.0)
        assertTrue(state.getMileageMeters().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getOdometer().value?.value == 6.4)
        assertTrue(state.getOdometer().value?.unit == Length.Unit.MEGAMETERS)
        assertTrue(state.getEngineTotalOperatingTime().value?.value == 1500.65)
        assertTrue(state.getEngineTotalOperatingTime().value?.unit == Duration.Unit.HOURS)
        assertTrue(state.getTirePressureStatuses()[0].value?.location == LocationWheel.FRONT_LEFT)
        assertTrue(state.getTirePressureStatuses()[0].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.getTirePressureStatuses()[1].value?.location == LocationWheel.FRONT_RIGHT)
        assertTrue(state.getTirePressureStatuses()[1].value?.status == TirePressureStatus.Status.LOW)
        assertTrue(state.getTirePressureStatuses()[2].value?.location == LocationWheel.REAR_RIGHT)
        assertTrue(state.getTirePressureStatuses()[2].value?.status == TirePressureStatus.Status.ALERT)
        assertTrue(state.getTirePressureStatuses()[3].value?.location == LocationWheel.REAR_LEFT)
        assertTrue(state.getTirePressureStatuses()[3].value?.status == TirePressureStatus.Status.NORMAL)
        assertTrue(state.getBrakeLiningWearPreWarning().value == ActiveState.INACTIVE)
        assertTrue(state.getEngineOilLifeRemaining().value == 0.88)
        assertTrue(state.getOemTroubleCodeValues()[0].value?.id == "123ID")
        assertTrue(state.getOemTroubleCodeValues()[0].value?.keyValue?.key == "some_error")
        assertTrue(state.getOemTroubleCodeValues()[0].value?.keyValue?.value == "some_value")
        assertTrue(state.getOemTroubleCodeValues()[1].value?.id == "1B3C")
        assertTrue(state.getOemTroubleCodeValues()[1].value?.keyValue?.key == "important_error")
        assertTrue(state.getOemTroubleCodeValues()[1].value?.keyValue?.value == "system fault 32")
        assertTrue(state.getDieselExhaustFluidRange().value?.value == 2233.0)
        assertTrue(state.getDieselExhaustFluidRange().value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.getDieselParticulateFilterSootLevel().value == 0.16)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "003300")
        assertTrue(Diagnostics.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "003300010203040506090b0c0d0e0f101112131415161718191a1b1c1d1e1f20212223242526")
        val getter = Diagnostics.GetProperties(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x09, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26)
        assertTrue(getter == bytes)
    }
}