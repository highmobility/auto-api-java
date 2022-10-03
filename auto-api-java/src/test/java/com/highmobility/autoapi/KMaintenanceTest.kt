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
class KMaintenanceTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003401" + 
            "01000D01000A0703407f500000000000" +  // 501.0 days until next service
            "02000D01000A120440acc20000000000" +  // 3'681km until next service
            "03000401000103" +  // Condition Based Service reports count is 3
            "04000D01000A07054014000000000000" +  // 5 months until exhaust inspection
            "05000401000102" +  // Teleservice is available
            "06000D01000A120440b3880000000000" +  // Service distance threshold is 5000.0km
            "07000D01000A07044010000000000000" +  // Service time threshold is 4 weeks
            "08000B010008000001674058f130" +  // Automatic teleservice call date is at 23 November 2018 at 11:33:50 UTC
            "09000B010008000001674024c1d0" +  // Teleservice battery call date is at 23 November 2018 at 10:36:50 GMT
            "0a000B01000800000166a15d20d8" +  // Next inspection date is at 23 October 2018 at 14:38:47 GMT
            "0b004401004107e305000300000b4272616b6520666c756964002c4e657874206368616e676520617420737065636966696564206461746520617420746865206c61746573742e" +  // Next latest brake fluid change date is 2019 May in a CBS with ID 3 and status 'ok'
            "0c000B010008000001677c63d280" +  // Brake fluid change date is at 5 December 2018 at 03:22:56 GMT
            "0d000D01000A0703407f500000000000" +  // 501.0 days until next service
            "0e000D01000A120440acc20000000000" +  // 3'681km until next service
            "0f000D01000A07054014000000000000" +  // 5 months until exhaust inspection
            "10000B010008000001677c63d280" +  // Last eCall happened at 5 December 2018 at 03:22:56 GMT
            "11000D01000A120440806ccccccccccd" +  // Distance to the next oil service is 525.6km
            "12000D01000A07034050b33333333333" +  // Time to the next oil service is 66.8 days
            "13000D01000A12044097710000000000" +  // Brake fluid remaining distance is 1500.25km
            "14000401000100" +  // Brake fluid status is 'ok'
            "16000C010009010000017fda4373c0" +  // Front brake has a service date at 30 March 2022 at 09:58:48 GMT
            "16000C010009010000017fda4373c0" +  // Rear brake has a service date at 30 March 2022 at 09:58:48 GMT
            "17000E01000B0012044097710000000000" +  // Front brake's remaining distance to service is 1500.25km
            "17000E01000B0112044097710000000000" +  // Rear brake's remaining distance to service is 1500.25km
            "1800050100020000" +  // Front brake's service status is 'ok'
            "1800050100020100" +  // Rear brake's service status is 'ok'
            "19000B0100080000017ff961a380" +  // Next drive-in inspection date is at 5 April 2022 at 11:00:00 GMT
            "1a000401000100" +  // Drive-in inspection service status is 'ok'
            "1b000B0100080000017fdabd2800" +  // Next oil service is at 30 March 2022 at 12:11:44 GMT
            "1c000D01000A12044097710000000000" +  // Distance until the next inspection is 1500.25km
            "1d000B0100080000017fdabd2800" +  // Next legally required inspection date is at 30 March 2022 at 12:11:44 GMT
            "1e000401000100" +  // No current service requirements.
            "1f000B0100080000017fdabd2800" +  // Date of the earliest service is at 30 March 2022 at 12:11:44 GMT
            "20000401000100" +  // Vehicle inspection service status is 'ok'.
            "21000D01000A120440acc20000000000" +  // 3'681km until next drive-in inspection
            "22000B0100080000017fe45286dd" +  // Vehicle check is on 1. April 2022 at 11:51:28 EEST.
            "23000401000100" +  // Vehicle check service status is 'ok'.
            "24000D01000A1204409519999999999a" // The distance to next vehicle check is 1350.4km.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Maintenance.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Maintenance.State.Builder()
        builder.setDaysToNextService(Property(Duration(501.0, Duration.Unit.DAYS)))
        builder.setKilometersToNextService(Property(Length(3681.0, Length.Unit.KILOMETERS)))
        builder.setCbsReportsCount(Property(3))
        builder.setMonthsToExhaustInspection(Property(Duration(5.0, Duration.Unit.MONTHS)))
        builder.setTeleserviceAvailability(Property(Maintenance.TeleserviceAvailability.SUCCESSFUL))
        builder.setServiceDistanceThreshold(Property(Length(5000.0, Length.Unit.KILOMETERS)))
        builder.setServiceTimeThreshold(Property(Duration(4.0, Duration.Unit.WEEKS)))
        builder.setAutomaticTeleserviceCallDate(Property(getCalendar("2018-11-23T11:33:50.000Z")))
        builder.setTeleserviceBatteryCallDate(Property(getCalendar("2018-11-23T10:36:50.000Z")))
        builder.setNextInspectionDate(Property(getCalendar("2018-10-23T14:38:47.000Z")))
        builder.addConditionBasedService(Property(ConditionBasedService(2019, 5, 3, ConditionBasedService.DueStatus.OK, "Brake fluid", "Next change at specified date at the latest.")))
        builder.setBrakeFluidChangeDate(Property(getCalendar("2018-12-05T03:22:56.000Z")))
        builder.setTimeToNextService(Property(Duration(501.0, Duration.Unit.DAYS)))
        builder.setDistanceToNextService(Property(Length(3681.0, Length.Unit.KILOMETERS)))
        builder.setTimeToExhaustInspection(Property(Duration(5.0, Duration.Unit.MONTHS)))
        builder.setLastECall(Property(getCalendar("2018-12-05T03:22:56.000Z")))
        builder.setDistanceToNextOilService(Property(Length(525.6, Length.Unit.KILOMETERS)))
        builder.setTimeToNextOilService(Property(Duration(66.8, Duration.Unit.DAYS)))
        builder.setBrakeFluidRemainingDistance(Property(Length(1500.25, Length.Unit.KILOMETERS)))
        builder.setBrakeFluidStatus(Property(ServiceStatus.OK))
        builder.addBrakeServiceDueDate(Property(BrakeServiceDueDate(Axle.FRONT, getCalendar("2022-03-30T09:58:48.000Z"))))
        builder.addBrakeServiceDueDate(Property(BrakeServiceDueDate(Axle.REAR, getCalendar("2022-03-30T09:58:48.000Z"))))
        builder.addBrakeServiceRemainingDistance(Property(BrakeServiceRemainingDistance(Axle.FRONT, Length(1500.25, Length.Unit.KILOMETERS))))
        builder.addBrakeServiceRemainingDistance(Property(BrakeServiceRemainingDistance(Axle.REAR, Length(1500.25, Length.Unit.KILOMETERS))))
        builder.addBrakeServiceStatus(Property(BrakeServiceStatus(Axle.FRONT, ServiceStatus.OK)))
        builder.addBrakeServiceStatus(Property(BrakeServiceStatus(Axle.REAR, ServiceStatus.OK)))
        builder.setDriveInInspectionDate(Property(getCalendar("2022-04-05T11:00:00.000Z")))
        builder.setDriveInInspectionStatus(Property(ServiceStatus.OK))
        builder.setNextOilServiceDate(Property(getCalendar("2022-03-30T12:11:44.000Z")))
        builder.setNextInspectionDistanceTo(Property(Length(1500.25, Length.Unit.KILOMETERS)))
        builder.setLegalInspectionDate(Property(getCalendar("2022-03-30T12:11:44.000Z")))
        builder.setServiceStatus(Property(ServiceStatus.OK))
        builder.setServiceDate(Property(getCalendar("2022-03-30T12:11:44.000Z")))
        builder.setInspectionStatus(Property(ServiceStatus.OK))
        builder.setDriveInInspectionDistanceTo(Property(Length(3681.0, Length.Unit.KILOMETERS)))
        builder.setVehicleCheckDate(Property(getCalendar("2022-04-01T08:51:28Z")))
        builder.setVehicleCheckStatus(Property(ServiceStatus.OK))
        builder.setVehicleCheckDistanceTo(Property(Length(1350.4, Length.Unit.KILOMETERS)))
        testState(builder.build())
    }
    
    private fun testState(state: Maintenance.State) {
        assertTrue(state.daysToNextService.value?.value == 501.0)
        assertTrue(state.daysToNextService.value?.unit == Duration.Unit.DAYS)
        assertTrue(state.kilometersToNextService.value?.value == 3681.0)
        assertTrue(state.kilometersToNextService.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.cbsReportsCount.value == 3)
        assertTrue(state.monthsToExhaustInspection.value?.value == 5.0)
        assertTrue(state.monthsToExhaustInspection.value?.unit == Duration.Unit.MONTHS)
        assertTrue(state.teleserviceAvailability.value == Maintenance.TeleserviceAvailability.SUCCESSFUL)
        assertTrue(state.serviceDistanceThreshold.value?.value == 5000.0)
        assertTrue(state.serviceDistanceThreshold.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.serviceTimeThreshold.value?.value == 4.0)
        assertTrue(state.serviceTimeThreshold.value?.unit == Duration.Unit.WEEKS)
        assertTrue(dateIsSame(state.automaticTeleserviceCallDate.value, "2018-11-23T11:33:50.000Z"))
        assertTrue(dateIsSame(state.teleserviceBatteryCallDate.value, "2018-11-23T10:36:50.000Z"))
        assertTrue(dateIsSame(state.nextInspectionDate.value, "2018-10-23T14:38:47.000Z"))
        assertTrue(state.conditionBasedServices[0].value?.year == 2019)
        assertTrue(state.conditionBasedServices[0].value?.month == 5)
        assertTrue(state.conditionBasedServices[0].value?.id == 3)
        assertTrue(state.conditionBasedServices[0].value?.dueStatus == ConditionBasedService.DueStatus.OK)
        assertTrue(state.conditionBasedServices[0].value?.text == "Brake fluid")
        assertTrue(state.conditionBasedServices[0].value?.description == "Next change at specified date at the latest.")
        assertTrue(dateIsSame(state.brakeFluidChangeDate.value, "2018-12-05T03:22:56.000Z"))
        assertTrue(state.timeToNextService.value?.value == 501.0)
        assertTrue(state.timeToNextService.value?.unit == Duration.Unit.DAYS)
        assertTrue(state.distanceToNextService.value?.value == 3681.0)
        assertTrue(state.distanceToNextService.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.timeToExhaustInspection.value?.value == 5.0)
        assertTrue(state.timeToExhaustInspection.value?.unit == Duration.Unit.MONTHS)
        assertTrue(dateIsSame(state.lastECall.value, "2018-12-05T03:22:56.000Z"))
        assertTrue(state.distanceToNextOilService.value?.value == 525.6)
        assertTrue(state.distanceToNextOilService.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.timeToNextOilService.value?.value == 66.8)
        assertTrue(state.timeToNextOilService.value?.unit == Duration.Unit.DAYS)
        assertTrue(state.brakeFluidRemainingDistance.value?.value == 1500.25)
        assertTrue(state.brakeFluidRemainingDistance.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.brakeFluidStatus.value == ServiceStatus.OK)
        assertTrue(state.brakesServiceDueDates[0].value?.axle == Axle.FRONT)
        assertTrue(dateIsSame(state.brakesServiceDueDates[0].value?.dueDate, "2022-03-30T09:58:48.000Z"))
        assertTrue(state.brakesServiceDueDates[1].value?.axle == Axle.REAR)
        assertTrue(dateIsSame(state.brakesServiceDueDates[1].value?.dueDate, "2022-03-30T09:58:48.000Z"))
        assertTrue(state.brakesServiceRemainingDistances[0].value?.axle == Axle.FRONT)
        assertTrue(state.brakesServiceRemainingDistances[0].value?.distance?.value == 1500.25)
        assertTrue(state.brakesServiceRemainingDistances[0].value?.distance?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.brakesServiceRemainingDistances[1].value?.axle == Axle.REAR)
        assertTrue(state.brakesServiceRemainingDistances[1].value?.distance?.value == 1500.25)
        assertTrue(state.brakesServiceRemainingDistances[1].value?.distance?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.brakesServiceStatuses[0].value?.axle == Axle.FRONT)
        assertTrue(state.brakesServiceStatuses[0].value?.status == ServiceStatus.OK)
        assertTrue(state.brakesServiceStatuses[1].value?.axle == Axle.REAR)
        assertTrue(state.brakesServiceStatuses[1].value?.status == ServiceStatus.OK)
        assertTrue(dateIsSame(state.driveInInspectionDate.value, "2022-04-05T11:00:00.000Z"))
        assertTrue(state.driveInInspectionStatus.value == ServiceStatus.OK)
        assertTrue(dateIsSame(state.nextOilServiceDate.value, "2022-03-30T12:11:44.000Z"))
        assertTrue(state.nextInspectionDistanceTo.value?.value == 1500.25)
        assertTrue(state.nextInspectionDistanceTo.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(dateIsSame(state.legalInspectionDate.value, "2022-03-30T12:11:44.000Z"))
        assertTrue(state.serviceStatus.value == ServiceStatus.OK)
        assertTrue(dateIsSame(state.serviceDate.value, "2022-03-30T12:11:44.000Z"))
        assertTrue(state.inspectionStatus.value == ServiceStatus.OK)
        assertTrue(state.driveInInspectionDistanceTo.value?.value == 3681.0)
        assertTrue(state.driveInInspectionDistanceTo.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(dateIsSame(state.vehicleCheckDate.value, "2022-04-01T08:51:28Z"))
        assertTrue(state.vehicleCheckStatus.value == ServiceStatus.OK)
        assertTrue(state.vehicleCheckDistanceTo.value?.value == 1350.4)
        assertTrue(state.vehicleCheckDistanceTo.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetState() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "003400")
        val defaultGetter = Maintenance.GetState()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0034000102030405060708090a0b0c0d0e0f1011121314161718191a1b1c1d1e1f2021222324")
        val propertyGetter = Maintenance.GetState(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405060708090a0b0c0d0e0f1011121314161718191a1b1c1d1e1f2021222324"))
    }
    
    @Test
    fun testGetStateAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "003402")
        val created = Maintenance.GetStateAvailability()
        assertTrue(created.identifier == Identifier.MAINTENANCE)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Maintenance.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.MAINTENANCE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetStateAvailabilitySome() {
        val identifierBytes = Bytes("0102030405060708090a0b0c0d0e0f1011121314161718191a1b1c1d1e1f2021222324")
        val allBytes = Bytes(COMMAND_HEADER + "003402" + identifierBytes)
        val constructed = Maintenance.GetStateAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.MAINTENANCE)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Maintenance.GetStateAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Maintenance.GetStateAvailability
        assertTrue(resolved.identifier == Identifier.MAINTENANCE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}