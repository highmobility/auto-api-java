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

class KRaceTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005701" + 
            "01000E01000B0001013feba5e353f7ced9" +  // Longitudinal acceleration is 0.864g
            "01000E01000B010101bfe8189374bc6a7f" +  // Lateral acceleration is -0.753g
            "01000E01000B0201013fe8189374bc6a7f" +  // Front lateral acceleration is 0.753g
            "01000E01000B030101bfeba5e353f7ced9" +  // Rear lateral acceleration is -0.864g
            "02000B0100083fc851eb851eb852" +  // Understeering is at 19%
            "03000B0100083fa999999999999a" +  // Oversteering is at 5%
            "04000B0100083fef5c28f5c28f5c" +  // Gas pedal position is at 98%
            "05000D01000A02004024000000000000" +  // Steering angle is 10° to right
            "06000D01000A15064034000000000000" +  // Brake pressure is 20.0bar
            "07000D01000A0301401aa3d70a3d70a4" +  // Yaw rate is 6.66°/s
            "08000D01000A0200400a666666666666" +  // Rear suspension steering is +3°
            "09000401000101" +  // ESP is active
            "0a00050100020001" +  // Front brake torque vectoring is active
            "0a00050100020100" +  // Rear brake torque vectoring is inactive
            "0b000401000104" +  // Gear is in drive
            "0c000401000104" +  // 4th gear is selected
            "0d000B0100083fbeb851eb851eb8" +  // Brake pedal position is at 12%
            "0e000401000101" +  // Brake pedal switch is active, brake lights active
            "0f000401000101" +  // Clutch pedal switch is active, clutch is fully depressed
            "10000401000101" +  // Accelerator pedal idle switch is active, pedal released
            "11000401000101" +  // Accelerator pedal kickdown switch is active, pedal fully depressed
            "12000401000101" // Vehicle is moving
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Race.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Race.State.Builder()
        builder.addAcceleration(Property(Acceleration(Acceleration.Direction.LONGITUDINAL, AccelerationUnit(0.864, AccelerationUnit.Unit.GRAVITY))))
        builder.addAcceleration(Property(Acceleration(Acceleration.Direction.LATERAL, AccelerationUnit(-0.753, AccelerationUnit.Unit.GRAVITY))))
        builder.addAcceleration(Property(Acceleration(Acceleration.Direction.FRONT_LATERAL, AccelerationUnit(0.753, AccelerationUnit.Unit.GRAVITY))))
        builder.addAcceleration(Property(Acceleration(Acceleration.Direction.REAR_LATERAL, AccelerationUnit(-0.864, AccelerationUnit.Unit.GRAVITY))))
        builder.setUndersteering(Property(0.19))
        builder.setOversteering(Property(0.05))
        builder.setGasPedalPosition(Property(0.98))
        builder.setSteeringAngle(Property(Angle(10.0, Angle.Unit.DEGREES)))
        builder.setBrakePressure(Property(Pressure(20.0, Pressure.Unit.BARS)))
        builder.setYawRate(Property(AngularVelocity(6.66, AngularVelocity.Unit.DEGREES_PER_SECOND)))
        builder.setRearSuspensionSteering(Property(Angle(3.3, Angle.Unit.DEGREES)))
        builder.setElectronicStabilityProgram(Property(ActiveState.ACTIVE))
        builder.addBrakeTorqueVectoring(Property(BrakeTorqueVectoring(Axle.FRONT, ActiveState.ACTIVE)))
        builder.addBrakeTorqueVectoring(Property(BrakeTorqueVectoring(Axle.REAR, ActiveState.INACTIVE)))
        builder.setGearMode(Property(Race.GearMode.DRIVE))
        builder.setSelectedGear(Property(4))
        builder.setBrakePedalPosition(Property(0.12))
        builder.setBrakePedalSwitch(Property(ActiveState.ACTIVE))
        builder.setClutchPedalSwitch(Property(ActiveState.ACTIVE))
        builder.setAcceleratorPedalIdleSwitch(Property(ActiveState.ACTIVE))
        builder.setAcceleratorPedalKickdownSwitch(Property(ActiveState.ACTIVE))
        builder.setVehicleMoving(Property(Race.VehicleMoving.MOVING))
        testState(builder.build())
    }
    
    private fun testState(state: Race.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getAccelerations()[0].value?.direction == Acceleration.Direction.LONGITUDINAL)
        assertTrue(state.getAccelerations()[0].value?.acceleration?.value == 0.864)
        assertTrue(state.getAccelerations()[0].value?.acceleration?.unit == AccelerationUnit.Unit.GRAVITY)
        assertTrue(state.getAccelerations()[1].value?.direction == Acceleration.Direction.LATERAL)
        assertTrue(state.getAccelerations()[1].value?.acceleration?.value == -0.753)
        assertTrue(state.getAccelerations()[1].value?.acceleration?.unit == AccelerationUnit.Unit.GRAVITY)
        assertTrue(state.getAccelerations()[2].value?.direction == Acceleration.Direction.FRONT_LATERAL)
        assertTrue(state.getAccelerations()[2].value?.acceleration?.value == 0.753)
        assertTrue(state.getAccelerations()[2].value?.acceleration?.unit == AccelerationUnit.Unit.GRAVITY)
        assertTrue(state.getAccelerations()[3].value?.direction == Acceleration.Direction.REAR_LATERAL)
        assertTrue(state.getAccelerations()[3].value?.acceleration?.value == -0.864)
        assertTrue(state.getAccelerations()[3].value?.acceleration?.unit == AccelerationUnit.Unit.GRAVITY)
        assertTrue(state.getUndersteering().value == 0.19)
        assertTrue(state.getOversteering().value == 0.05)
        assertTrue(state.getGasPedalPosition().value == 0.98)
        assertTrue(state.getSteeringAngle().value?.value == 10.0)
        assertTrue(state.getSteeringAngle().value?.unit == Angle.Unit.DEGREES)
        assertTrue(state.getBrakePressure().value?.value == 20.0)
        assertTrue(state.getBrakePressure().value?.unit == Pressure.Unit.BARS)
        assertTrue(state.getYawRate().value?.value == 6.66)
        assertTrue(state.getYawRate().value?.unit == AngularVelocity.Unit.DEGREES_PER_SECOND)
        assertTrue(state.getRearSuspensionSteering().value?.value == 3.3)
        assertTrue(state.getRearSuspensionSteering().value?.unit == Angle.Unit.DEGREES)
        assertTrue(state.getElectronicStabilityProgram().value == ActiveState.ACTIVE)
        assertTrue(state.getBrakeTorqueVectorings()[0].value?.axle == Axle.FRONT)
        assertTrue(state.getBrakeTorqueVectorings()[0].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getBrakeTorqueVectorings()[1].value?.axle == Axle.REAR)
        assertTrue(state.getBrakeTorqueVectorings()[1].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getGearMode().value == Race.GearMode.DRIVE)
        assertTrue(state.getSelectedGear().value == 4)
        assertTrue(state.getBrakePedalPosition().value == 0.12)
        assertTrue(state.getBrakePedalSwitch().value == ActiveState.ACTIVE)
        assertTrue(state.getClutchPedalSwitch().value == ActiveState.ACTIVE)
        assertTrue(state.getAcceleratorPedalIdleSwitch().value == ActiveState.ACTIVE)
        assertTrue(state.getAcceleratorPedalKickdownSwitch().value == ActiveState.ACTIVE)
        assertTrue(state.getVehicleMoving().value == Race.VehicleMoving.MOVING)
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "005700")
        val defaultGetter = Race.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0057000102030405060708090a0b0c0d0e0f101112")
        val propertyGetter = Race.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405060708090a0b0c0d0e0f101112"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "005702")
        val created = Race.GetStateAvailability()
        assertTrue(created.identifier == Identifier.RACE)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Race.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.RACE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102030405060708090a0b0c0d0e0f101112")
        val allBytes = Bytes(COMMAND_HEADER + "005702" + identifierBytes)
        val constructed = Race.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.RACE)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Race.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12)
        assertTrue(constructed == secondConstructed)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(allBytes) as Race.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.RACE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}