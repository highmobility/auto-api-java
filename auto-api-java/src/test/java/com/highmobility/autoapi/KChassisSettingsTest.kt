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

class KChassisSettingsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005301" + 
            "01000401000101" +  // Driving mode is set to ECO
            "02000401000101" +  // Sport Chrono is active
            "05000E01000B0018014035000000000000" +  // Front axle spring rate is 21.0N/mm
            "05000E01000B0118014037000000000000" +  // Rear axle spring rate is 23.0N/mm
            "06000E01000B0018014042800000000000" +  // Front axle maximum spring rate is 37.0N/mm
            "06000E01000B0118014043800000000000" +  // Rear axle maximum spring rate is 39.0N/mm
            "07000E01000B0018014030000000000000" +  // Front axle minimum spring rate is 16.0N/mm
            "07000E01000B0118014032000000000000" +  // Rear axle minimum spring rate is 18.0N/mm
            "08000D01000A12014039666666666666" +  // Current chassis position is 25.4mm
            "09000D01000A1201404bc00000000000" +  // Maximum chassis position is 55.5mm
            "0a000D01000A1201c03c666666666666" // Minimum chassis position is -28.4mm
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as ChassisSettings.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = ChassisSettings.State.Builder()
        builder.setDrivingMode(Property(DrivingMode.ECO))
        builder.setSportChrono(Property(ChassisSettings.SportChrono.ACTIVE))
        builder.addCurrentSpringRate(Property(SpringRate(Axle.FRONT, Torque(21.0, Torque.Unit.NEWTON_MILLIMETERS))))
        builder.addCurrentSpringRate(Property(SpringRate(Axle.REAR, Torque(23.0, Torque.Unit.NEWTON_MILLIMETERS))))
        builder.addMaximumSpringRate(Property(SpringRate(Axle.FRONT, Torque(37.0, Torque.Unit.NEWTON_MILLIMETERS))))
        builder.addMaximumSpringRate(Property(SpringRate(Axle.REAR, Torque(39.0, Torque.Unit.NEWTON_MILLIMETERS))))
        builder.addMinimumSpringRate(Property(SpringRate(Axle.FRONT, Torque(16.0, Torque.Unit.NEWTON_MILLIMETERS))))
        builder.addMinimumSpringRate(Property(SpringRate(Axle.REAR, Torque(18.0, Torque.Unit.NEWTON_MILLIMETERS))))
        builder.setCurrentChassisPosition(Property(Length(25.4, Length.Unit.MILLIMETERS)))
        builder.setMaximumChassisPosition(Property(Length(55.5, Length.Unit.MILLIMETERS)))
        builder.setMinimumChassisPosition(Property(Length(-28.4, Length.Unit.MILLIMETERS)))
        testState(builder.build())
    }
    
    private fun testState(state: ChassisSettings.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.drivingMode.value == DrivingMode.ECO)
        assertTrue(state.sportChrono.value == ChassisSettings.SportChrono.ACTIVE)
        assertTrue(state.currentSpringRates[0].value?.axle == Axle.FRONT)
        assertTrue(state.currentSpringRates[0].value?.springRate?.value == 21.0)
        assertTrue(state.currentSpringRates[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.currentSpringRates[1].value?.axle == Axle.REAR)
        assertTrue(state.currentSpringRates[1].value?.springRate?.value == 23.0)
        assertTrue(state.currentSpringRates[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.maximumSpringRates[0].value?.axle == Axle.FRONT)
        assertTrue(state.maximumSpringRates[0].value?.springRate?.value == 37.0)
        assertTrue(state.maximumSpringRates[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.maximumSpringRates[1].value?.axle == Axle.REAR)
        assertTrue(state.maximumSpringRates[1].value?.springRate?.value == 39.0)
        assertTrue(state.maximumSpringRates[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.minimumSpringRates[0].value?.axle == Axle.FRONT)
        assertTrue(state.minimumSpringRates[0].value?.springRate?.value == 16.0)
        assertTrue(state.minimumSpringRates[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.minimumSpringRates[1].value?.axle == Axle.REAR)
        assertTrue(state.minimumSpringRates[1].value?.springRate?.value == 18.0)
        assertTrue(state.minimumSpringRates[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.currentChassisPosition.value?.value == 25.4)
        assertTrue(state.currentChassisPosition.value?.unit == Length.Unit.MILLIMETERS)
        assertTrue(state.maximumChassisPosition.value?.value == 55.5)
        assertTrue(state.maximumChassisPosition.value?.unit == Length.Unit.MILLIMETERS)
        assertTrue(state.minimumChassisPosition.value?.value == -28.4)
        assertTrue(state.minimumChassisPosition.value?.unit == Length.Unit.MILLIMETERS)
    }
    
    @Test
    fun testGetChassisSettings() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "005300")
        val defaultGetter = ChassisSettings.GetChassisSettings()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "005300010205060708090a")
        val propertyGetter = ChassisSettings.GetChassisSettings(0x01, 0x02, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("010205060708090a"))
    }
    
    @Test
    fun testGetChassisSettingsAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "005302")
        val created = ChassisSettings.GetChassisSettingsAvailability()
        assertTrue(created.identifier == Identifier.CHASSIS_SETTINGS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.GetChassisSettingsAvailability
        assertTrue(resolved.identifier == Identifier.CHASSIS_SETTINGS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetChassisSettingsAvailabilitySome() {
        val identifierBytes = Bytes("010205060708090a")
        val allBytes = Bytes(COMMAND_HEADER + "005302" + identifierBytes)
        val constructed = ChassisSettings.GetChassisSettingsAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.CHASSIS_SETTINGS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = ChassisSettings.GetChassisSettingsAvailability(0x01, 0x02, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as ChassisSettings.GetChassisSettingsAvailability
        assertTrue(resolved.identifier == Identifier.CHASSIS_SETTINGS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
    
    @Test
    fun setDrivingMode() {
        val bytes = Bytes(COMMAND_HEADER + "005301" +
            "01000401000101")
    
        val constructed = ChassisSettings.SetDrivingMode(DrivingMode.ECO)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.SetDrivingMode
        assertTrue(resolved.drivingMode.value == DrivingMode.ECO)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun startStopSportsChrono() {
        val bytes = Bytes(COMMAND_HEADER + "005301" +
            "02000401000101")
    
        val constructed = ChassisSettings.StartStopSportsChrono(ChassisSettings.SportChrono.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.StartStopSportsChrono
        assertTrue(resolved.sportChrono.value == ChassisSettings.SportChrono.ACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setSpringRates() {
        val bytes = Bytes(COMMAND_HEADER + "005301" +
            "05000E01000B0018014035000000000000" +
            "05000E01000B0118014037000000000000")
    
        val constructed = ChassisSettings.SetSpringRates(arrayListOf(
                SpringRate(Axle.FRONT, Torque(21.0, Torque.Unit.NEWTON_MILLIMETERS)), 
                SpringRate(Axle.REAR, Torque(23.0, Torque.Unit.NEWTON_MILLIMETERS)))
            )
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.SetSpringRates
        assertTrue(resolved.currentSpringRates[0].value?.axle == Axle.FRONT)
        assertTrue(resolved.currentSpringRates[0].value?.springRate?.value == 21.0)
        assertTrue(resolved.currentSpringRates[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(resolved.currentSpringRates[1].value?.axle == Axle.REAR)
        assertTrue(resolved.currentSpringRates[1].value?.springRate?.value == 23.0)
        assertTrue(resolved.currentSpringRates[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun setChassisPosition() {
        val bytes = Bytes(COMMAND_HEADER + "005301" +
            "08000D01000A12014039666666666666")
    
        val constructed = ChassisSettings.SetChassisPosition(Length(25.4, Length.Unit.MILLIMETERS))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.SetChassisPosition
        assertTrue(resolved.currentChassisPosition.value?.value == 25.4)
        assertTrue(resolved.currentChassisPosition.value?.unit == Length.Unit.MILLIMETERS)
        assertTrue(resolved == bytes)
    }
}