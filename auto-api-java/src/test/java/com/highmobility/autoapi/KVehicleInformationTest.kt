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
class KVehicleInformationTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "001401" + 
            "02000401000101" +  // Powertrain is all electric
            "030009010006547970652058" +  // Model name is 'Type X'
            "040009010006537065656479" +  // Name of the vehicle is 'Speedy'
            "050009010006414243313233" +  // Licence plate number is 'ABC123'
            "06000B0100085061636b6167652b" +  // Sales designation is 'Package+'
            "07000501000207e3" +  // Vehicle manufacturing year is 2019
            "08000F01000C4573746f72696c20426c6175" +  // Colour is named 'Estoril Blau'
            "09000D01000A1402406b800000000000" +  // Vehicle has 220.0kW of power
            "0a000401000105" +  // Vehicle has 5 doors
            "0b000401000105" +  // Vehicle has 5 seats
            "0c000D01000A19024004000000000000" +  // Engine volume is 2.5 L
            "0d000D01000A1800406ea00000000000" +  // Engine maximum torque is 245.0Nm
            "0e000401000101" +  // Vehicle has an automatic gearbox
            "0f000401000100" +  // Vehicle displays values in kilometers
            "10000401000100" +  // Driver seat is located on the left
            "11001201000F5061726b696e672073656e736f7273" +  // Parking sensors are equipped (installed)
            "1100130100104175746f6d6174696320776970657273" +  // Automatic wipers are equipped (installed)
            "13000D01000A1402406b800000000000" +  // Vehicle has 220kW of power
            "14000B0100086573746f6e69616e" +  // Headunit is in estonian language
            "15000401000101" +  // Headunit is using a 24h timeformat
            "16000401000101" +  // Vehicle has rear-wheel drive
            "17000401000106" // Secondary powertrain`s type is petrol.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as VehicleInformation.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = VehicleInformation.State.Builder()
        builder.setPowertrain(Property(EngineType.ALL_ELECTRIC))
        builder.setModelName(Property("Type X"))
        builder.setName(Property("Speedy"))
        builder.setLicensePlate(Property("ABC123"))
        builder.setSalesDesignation(Property("Package+"))
        builder.setModelYear(Property(2019))
        builder.setColourName(Property("Estoril Blau"))
        builder.setPowerInKW(Property(Power(220.0, Power.Unit.KILOWATTS)))
        builder.setNumberOfDoors(Property(5))
        builder.setNumberOfSeats(Property(5))
        builder.setEngineVolume(Property(Volume(2.5, Volume.Unit.LITERS)))
        builder.setEngineMaxTorque(Property(Torque(245.0, Torque.Unit.NEWTON_METERS)))
        builder.setGearbox(Property(VehicleInformation.Gearbox.AUTOMATIC))
        builder.setDisplayUnit(Property(VehicleInformation.DisplayUnit.KM))
        builder.setDriverSeatLocation(Property(VehicleInformation.DriverSeatLocation.LEFT))
        builder.addEquipment(Property("Parking sensors"))
        builder.addEquipment(Property("Automatic wipers"))
        builder.setPower(Property(Power(220.0, Power.Unit.KILOWATTS)))
        builder.setLanguage(Property("estonian"))
        builder.setTimeformat(Property(VehicleInformation.Timeformat.TWENTY_FOUR_H))
        builder.setDrive(Property(VehicleInformation.Drive.RWD))
        builder.setPowertrainSecondary(Property(EngineType.PETROL))
        testState(builder.build())
    }
    
    private fun testState(state: VehicleInformation.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.powertrain.value == EngineType.ALL_ELECTRIC)
        assertTrue(state.modelName.value == "Type X")
        assertTrue(state.name.value == "Speedy")
        assertTrue(state.licensePlate.value == "ABC123")
        assertTrue(state.salesDesignation.value == "Package+")
        assertTrue(state.modelYear.value == 2019)
        assertTrue(state.colourName.value == "Estoril Blau")
        assertTrue(state.powerInKW.value?.value == 220.0)
        assertTrue(state.powerInKW.value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.numberOfDoors.value == 5)
        assertTrue(state.numberOfSeats.value == 5)
        assertTrue(state.engineVolume.value?.value == 2.5)
        assertTrue(state.engineVolume.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.engineMaxTorque.value?.value == 245.0)
        assertTrue(state.engineMaxTorque.value?.unit == Torque.Unit.NEWTON_METERS)
        assertTrue(state.gearbox.value == VehicleInformation.Gearbox.AUTOMATIC)
        assertTrue(state.displayUnit.value == VehicleInformation.DisplayUnit.KM)
        assertTrue(state.driverSeatLocation.value == VehicleInformation.DriverSeatLocation.LEFT)
        assertTrue(state.equipments[0].value == "Parking sensors")
        assertTrue(state.equipments[1].value == "Automatic wipers")
        assertTrue(state.power.value?.value == 220.0)
        assertTrue(state.power.value?.unit == Power.Unit.KILOWATTS)
        assertTrue(state.language.value == "estonian")
        assertTrue(state.timeformat.value == VehicleInformation.Timeformat.TWENTY_FOUR_H)
        assertTrue(state.drive.value == VehicleInformation.Drive.RWD)
        assertTrue(state.powertrainSecondary.value == EngineType.PETROL)
    }
    
    @Test
    fun testGetVehicleInformation() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "001400")
        val defaultGetter = VehicleInformation.GetVehicleInformation()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "00140002030405060708090a0b0c0d0e0f10111314151617")
        val propertyGetter = VehicleInformation.GetVehicleInformation(0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x13, 0x14, 0x15, 0x16, 0x17)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("02030405060708090a0b0c0d0e0f10111314151617"))
    }
}