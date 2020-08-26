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
        assertTrue(state.getDrivingMode().value == DrivingMode.ECO)
        assertTrue(state.getSportChrono().value == ChassisSettings.SportChrono.ACTIVE)
        assertTrue(state.getCurrentSpringRates()[0].value?.axle == Axle.FRONT)
        assertTrue(state.getCurrentSpringRates()[0].value?.springRate?.value == 21.0)
        assertTrue(state.getCurrentSpringRates()[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.getCurrentSpringRates()[1].value?.axle == Axle.REAR)
        assertTrue(state.getCurrentSpringRates()[1].value?.springRate?.value == 23.0)
        assertTrue(state.getCurrentSpringRates()[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.getMaximumSpringRates()[0].value?.axle == Axle.FRONT)
        assertTrue(state.getMaximumSpringRates()[0].value?.springRate?.value == 37.0)
        assertTrue(state.getMaximumSpringRates()[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.getMaximumSpringRates()[1].value?.axle == Axle.REAR)
        assertTrue(state.getMaximumSpringRates()[1].value?.springRate?.value == 39.0)
        assertTrue(state.getMaximumSpringRates()[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.getMinimumSpringRates()[0].value?.axle == Axle.FRONT)
        assertTrue(state.getMinimumSpringRates()[0].value?.springRate?.value == 16.0)
        assertTrue(state.getMinimumSpringRates()[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.getMinimumSpringRates()[1].value?.axle == Axle.REAR)
        assertTrue(state.getMinimumSpringRates()[1].value?.springRate?.value == 18.0)
        assertTrue(state.getMinimumSpringRates()[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(state.getCurrentChassisPosition().value?.value == 25.4)
        assertTrue(state.getCurrentChassisPosition().value?.unit == Length.Unit.MILLIMETERS)
        assertTrue(state.getMaximumChassisPosition().value?.value == 55.5)
        assertTrue(state.getMaximumChassisPosition().value?.unit == Length.Unit.MILLIMETERS)
        assertTrue(state.getMinimumChassisPosition().value?.value == -28.4)
        assertTrue(state.getMinimumChassisPosition().value?.unit == Length.Unit.MILLIMETERS)
    }
    
    @Test
    fun testGetChassisSettings() {
        val bytes = Bytes(COMMAND_HEADER + "005300")
        assertTrue(ChassisSettings.GetChassisSettings() == bytes)
    }
    
    @Test
    fun testGetChassisSettingsProperties() {
        val bytes = Bytes(COMMAND_HEADER + "005300010205060708090a")
        val getter = ChassisSettings.GetChassisSettingsProperties(0x01, 0x02, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testSetDrivingMode() {
        val bytes = Bytes(COMMAND_HEADER + "005301" + 
            "01000401000101")
    
        val constructed = ChassisSettings.SetDrivingMode(DrivingMode.ECO)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.SetDrivingMode
        assertTrue(resolved.getDrivingMode().value == DrivingMode.ECO)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testStartStopSportsChrono() {
        val bytes = Bytes(COMMAND_HEADER + "005301" + 
            "02000401000101")
    
        val constructed = ChassisSettings.StartStopSportsChrono(ChassisSettings.SportChrono.ACTIVE)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.StartStopSportsChrono
        assertTrue(resolved.getSportChrono().value == ChassisSettings.SportChrono.ACTIVE)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testSetSpringRates() {
        val bytes = Bytes(COMMAND_HEADER + "005301" + 
            "05000E01000B0018014035000000000000" +
            "05000E01000B0118014037000000000000")
    
        val constructed = ChassisSettings.SetSpringRates(arrayListOf(
            SpringRate(Axle.FRONT, Torque(21.0, Torque.Unit.NEWTON_MILLIMETERS)), 
            SpringRate(Axle.REAR, Torque(23.0, Torque.Unit.NEWTON_MILLIMETERS))))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.SetSpringRates
        assertTrue(resolved.getCurrentSpringRates()[0].value?.axle == Axle.FRONT)
        assertTrue(resolved.getCurrentSpringRates()[0].value?.springRate?.value == 21.0)
        assertTrue(resolved.getCurrentSpringRates()[0].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(resolved.getCurrentSpringRates()[1].value?.axle == Axle.REAR)
        assertTrue(resolved.getCurrentSpringRates()[1].value?.springRate?.value == 23.0)
        assertTrue(resolved.getCurrentSpringRates()[1].value?.springRate?.unit == Torque.Unit.NEWTON_MILLIMETERS)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testSetChassisPosition() {
        val bytes = Bytes(COMMAND_HEADER + "005301" + 
            "08000D01000A12014039666666666666")
    
        val constructed = ChassisSettings.SetChassisPosition(Length(25.4, Length.Unit.MILLIMETERS))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as ChassisSettings.SetChassisPosition
        assertTrue(resolved.getCurrentChassisPosition().value?.value == 25.4)
        assertTrue(resolved.getCurrentChassisPosition().value?.unit == Length.Unit.MILLIMETERS)
        assertTrue(resolved == bytes)
    }
}