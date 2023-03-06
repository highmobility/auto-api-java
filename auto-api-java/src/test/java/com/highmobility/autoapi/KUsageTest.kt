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
class KUsageTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006801" + 
            "01000D01000A12044084d4cccccccccd" +  // Average weekly distance is 666.6km
            "02000D01000A120440884d999999999a" +  // Average weekly distance, over long term, is 777.7km
            "03000B0100083fe6666666666666" +  // Acceleration is evaluated at 70%
            "04000B0100083fec28f5c28f5c29" +  // Driving style is evaluated at 88%
            "05000C010009003fc999999999999a" +  // Driving mode 'regular' is engaged 20% of the time
            "05000C010009013fd3333333333333" +  // Driving mode 'eco' is engaged 30% of the time
            "05000C010009023fb999999999999a" +  // Driving mode 'eco' is engaged 10% of the time
            "05000C010009033fb999999999999a" +  // Driving mode 'eco' is engaged 1% of the time
            "05000C010009043fd3333333333333" +  // Driving mode 'eco_plus' is engaged 30% of the time
            "05000C010009050000000000000000" +  // Driving mode 'eco' is engaged 0% of the time
            "06000E01000B000c044034333333333333" +  // Driving mode 'regular' consumed 20.2kWh of electric energy
            "06000E01000B010c04404099999999999a" +  // Driving mode 'eco' consumed 33.2kWh of electric energy
            "06000E01000B020c04404b266666666666" +  // Driving mode 'sport' consumed 54.3kWh of electric energy
            "06000E01000B030c044050333333333333" +  // Driving mode 'sport_plus' consumed 64.8kWh of electric energy
            "06000E01000B040c044032000000000000" +  // Driving mode 'eco_plus' consumed 18.0kWh of electric energy
            "06000E01000B050c044040d9999999999a" +  // Driving mode 'comfort' consumed 33.7kWh of electric energy
            "07000D01000A0c044059533333333333" +  // Last trip consumed 101.3kWh of electric energy
            "08000D01000A19024036800000000000" +  // Last trip consumed 22.5 L of fuel
            "09000D01000A120440f7590000000000" +  // Mileage is 95'632km after last trip
            "0a000B0100083fe6666666666666" +  // Electric postion of the last trip is 70%
            "0b000D01000A0d004016b851eb851eb8" +  // 5.68kWh/100km of electric energy was recuperated during last trip
            "0c000B0100083fe0000000000000" +  // Battery is at 50% after last trip
            "0d000B0100080000016682059d50" +  // Last trip happened at 17 October 2018 at 12:34:58 UTC
            "0e000D01000A0f00401a000000000000" +  // Average fuel consumption is 6.5 L per 100km
            "0f000D01000A0f00401e000000000000" +  // Current fuel consumption is 7.5 L per 100km
            "10000D01000A120440f7590000000000" +  // Odometer is 95'632km after last trip
            "11000B0100083fe6666666666666" +  // Safety driving score is evaluated at 70%
            "12000401000100" +  // Rapid acceleration is graded as excellent
            "13000401000101" +  // Rapid deceleration is graded as normal
            "14000401000100" +  // Late night driving is graded as excellent
            "1500170100141204409773851eb851ec07044024000000000000" +  // During the last 10.0 weeks the vehicle travelled 1500.88km
            "16000D01000A0d00402670a3d70a3d71" +  // Consumed 11.22kWh/100km since the start of a trip
            "17000D01000A0d004036547ae147ae14" +  // Consumed 22.33kWh/100km since a reset
            "18000D01000A120040fb198000000000" +  // Vehicle travelled 111000.0m using electricity during the last trip
            "19000D01000A1200410b198000000000" +  // Vehicle travelled 222000.0m using electricity since last reset
            "1a000D01000A07014053400000000000" +  // Vehicle travelled 77.0min using electricity during last trip
            "1b000D01000A07014056000000000000" +  // Vehicle travelled 88.0min using electricity since last reset
            "1c000D01000A0f00401599999999999a" +  // Consumed 5.4 L/100km during last trip
            "1d000D01000A0f004015333333333333" +  // Consumed 5.3 L/100km since reset
            "1e000D01000A1601404619999999999a" +  // Average speed was 44.2km/h during last trip
            "1f000D01000A1601404619999999999a" +  // Average speed was 44.2km/h since reset
            "20000D01000A120040fb198000000000" +  // Vehicle travelled 111000.0m using fuel during the last trip
            "21000D01000A1200410b198000000000" +  // Vehicle travelled 222000.0m using fuel since last reset
            "22000D01000A07014053400000000000" +  // Vehicle travelled 77.0min during last trip
            "23000D01000A07014056000000000000" +  // Vehicle travelled 88.0min since last reset
            "24000B0100083fe6666666666666" +  // Total eco-score rating is 70%
            "25000B0100083fe6666666666666" +  // Eco-score free-wheeling rating is 70%
            "26000B0100083fe6666666666666" +  // Eco-score constant is 70%
            "27000D01000A12043fe6666666666666" +  // Eco-score bonus range is 0.7km
            "28000E01000B011204407c833333333333" +  // Trip meter 1`s distance is 456.2km
            "28000E01000B02120440a372999999999a" +  // Trip meter 2`s distance is 2489.3km
            "29000D01000A0d00402670a3d70a3d71" +  // Average consumption is 11.22kWh/100km
            "2a000B0100083fe6666666666666" +  // Braking is evaluated at 70%
            "2b000D01000A16014053600000000000" +  // Average speed is 77.5km/h.
            "2c000D01000A140040a1f80000000000" +  // Recuperation energy is 2300.0W.
            "2d000F01000C000007064093480000000000" +  // Normal longitudinal acceleration lasted 1234.0ms.
            "2d000F01000C010107064093480000000000" // Positive outlier lateral acceleration lasted 1234.0ms.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Usage.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Usage.State.Builder()
        builder.setAverageWeeklyDistance(Property(Length(666.6, Length.Unit.KILOMETERS)))
        builder.setAverageWeeklyDistanceLongRun(Property(Length(777.7, Length.Unit.KILOMETERS)))
        builder.setAccelerationEvaluation(Property(0.7))
        builder.setDrivingStyleEvaluation(Property(0.88))
        builder.addDrivingModesActivationPeriod(Property(DrivingModeActivationPeriod(DrivingMode.REGULAR, 0.2)))
        builder.addDrivingModesActivationPeriod(Property(DrivingModeActivationPeriod(DrivingMode.ECO, 0.3)))
        builder.addDrivingModesActivationPeriod(Property(DrivingModeActivationPeriod(DrivingMode.SPORT, 0.1)))
        builder.addDrivingModesActivationPeriod(Property(DrivingModeActivationPeriod(DrivingMode.SPORT_PLUS, 0.1)))
        builder.addDrivingModesActivationPeriod(Property(DrivingModeActivationPeriod(DrivingMode.ECO_PLUS, 0.3)))
        builder.addDrivingModesActivationPeriod(Property(DrivingModeActivationPeriod(DrivingMode.COMFORT, 0.0)))
        builder.addDrivingModeEnergyConsumption(Property(DrivingModeEnergyConsumption(DrivingMode.REGULAR, Energy(20.2, Energy.Unit.KILOWATT_HOURS))))
        builder.addDrivingModeEnergyConsumption(Property(DrivingModeEnergyConsumption(DrivingMode.ECO, Energy(33.2, Energy.Unit.KILOWATT_HOURS))))
        builder.addDrivingModeEnergyConsumption(Property(DrivingModeEnergyConsumption(DrivingMode.SPORT, Energy(54.3, Energy.Unit.KILOWATT_HOURS))))
        builder.addDrivingModeEnergyConsumption(Property(DrivingModeEnergyConsumption(DrivingMode.SPORT_PLUS, Energy(64.8, Energy.Unit.KILOWATT_HOURS))))
        builder.addDrivingModeEnergyConsumption(Property(DrivingModeEnergyConsumption(DrivingMode.ECO_PLUS, Energy(18.0, Energy.Unit.KILOWATT_HOURS))))
        builder.addDrivingModeEnergyConsumption(Property(DrivingModeEnergyConsumption(DrivingMode.COMFORT, Energy(33.7, Energy.Unit.KILOWATT_HOURS))))
        builder.setLastTripEnergyConsumption(Property(Energy(101.3, Energy.Unit.KILOWATT_HOURS)))
        builder.setLastTripFuelConsumption(Property(Volume(22.5, Volume.Unit.LITERS)))
        builder.setMileageAfterLastTrip(Property(Length(95632.0, Length.Unit.KILOMETERS)))
        builder.setLastTripElectricPortion(Property(0.7))
        builder.setLastTripAverageEnergyRecuperation(Property(EnergyEfficiency(5.68, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)))
        builder.setLastTripBatteryRemaining(Property(0.5))
        builder.setLastTripDate(Property(getCalendar("2018-10-17T12:34:58.000Z")))
        builder.setAverageFuelConsumption(Property(FuelEfficiency(6.5, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)))
        builder.setCurrentFuelConsumption(Property(FuelEfficiency(7.5, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)))
        builder.setOdometerAfterLastTrip(Property(Length(95632.0, Length.Unit.KILOMETERS)))
        builder.setSafetyDrivingScore(Property(0.7))
        builder.setRapidAccelerationGrade(Property(Grade.EXCELLENT))
        builder.setRapidDecelerationGrade(Property(Grade.NORMAL))
        builder.setLateNightGrade(Property(Grade.EXCELLENT))
        builder.setDistanceOverTime(Property(DistanceOverTime(Length(1500.88, Length.Unit.KILOMETERS), Duration(10.0, Duration.Unit.WEEKS))))
        builder.setElectricConsumptionRateSinceStart(Property(EnergyEfficiency(11.22, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)))
        builder.setElectricConsumptionRateSinceReset(Property(EnergyEfficiency(22.33, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)))
        builder.setElectricDistanceLastTrip(Property(Length(111000.0, Length.Unit.METERS)))
        builder.setElectricDistanceSinceReset(Property(Length(222000.0, Length.Unit.METERS)))
        builder.setElectricDurationLastTrip(Property(Duration(77.0, Duration.Unit.MINUTES)))
        builder.setElectricDurationSinceReset(Property(Duration(88.0, Duration.Unit.MINUTES)))
        builder.setFuelConsumptionRateLastTrip(Property(FuelEfficiency(5.4, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)))
        builder.setFuelConsumptionRateSinceReset(Property(FuelEfficiency(5.3, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)))
        builder.setAverageSpeedLastTrip(Property(Speed(44.2, Speed.Unit.KILOMETERS_PER_HOUR)))
        builder.setAverageSpeedSinceReset(Property(Speed(44.2, Speed.Unit.KILOMETERS_PER_HOUR)))
        builder.setFuelDistanceLastTrip(Property(Length(111000.0, Length.Unit.METERS)))
        builder.setFuelDistanceSinceReset(Property(Length(222000.0, Length.Unit.METERS)))
        builder.setDrivingDurationLastTrip(Property(Duration(77.0, Duration.Unit.MINUTES)))
        builder.setDrivingDurationSinceReset(Property(Duration(88.0, Duration.Unit.MINUTES)))
        builder.setEcoScoreTotal(Property(0.7))
        builder.setEcoScoreFreeWheel(Property(0.7))
        builder.setEcoScoreConstant(Property(0.7))
        builder.setEcoScoreBonusRange(Property(Length(0.7, Length.Unit.KILOMETERS)))
        builder.addTripMeter(Property(TripMeter(1, Length(456.2, Length.Unit.KILOMETERS))))
        builder.addTripMeter(Property(TripMeter(2, Length(2489.3, Length.Unit.KILOMETERS))))
        builder.setElectricConsumptionAverage(Property(EnergyEfficiency(11.22, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)))
        builder.setBrakingEvaluation(Property(0.7))
        builder.setAverageSpeed(Property(Speed(77.5, Speed.Unit.KILOMETERS_PER_HOUR)))
        builder.setRecuperationPower(Property(Power(2300.0, Power.Unit.WATTS)))
        builder.addAccelerationDuration(Property(AccelerationDuration(AccelerationDuration.Direction.LONGITUDINAL, AccelerationDuration.Type.REGULAR, Duration(1234.0, Duration.Unit.MILLISECONDS))))
        builder.addAccelerationDuration(Property(AccelerationDuration(AccelerationDuration.Direction.LATERAL, AccelerationDuration.Type.POSITIVE_OUTLIER, Duration(1234.0, Duration.Unit.MILLISECONDS))))
        testState(builder.build())
    }
    
    private fun testState(state: Usage.State) {
        assertTrue(state.averageWeeklyDistance.value?.value == 666.6)
        assertTrue(state.averageWeeklyDistance.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.averageWeeklyDistanceLongRun.value?.value == 777.7)
        assertTrue(state.averageWeeklyDistanceLongRun.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.accelerationEvaluation.value == 0.7)
        assertTrue(state.drivingStyleEvaluation.value == 0.88)
        assertTrue(state.drivingModesActivationPeriods[0].value?.drivingMode == DrivingMode.REGULAR)
        assertTrue(state.drivingModesActivationPeriods[0].value?.period == 0.2)
        assertTrue(state.drivingModesActivationPeriods[1].value?.drivingMode == DrivingMode.ECO)
        assertTrue(state.drivingModesActivationPeriods[1].value?.period == 0.3)
        assertTrue(state.drivingModesActivationPeriods[2].value?.drivingMode == DrivingMode.SPORT)
        assertTrue(state.drivingModesActivationPeriods[2].value?.period == 0.1)
        assertTrue(state.drivingModesActivationPeriods[3].value?.drivingMode == DrivingMode.SPORT_PLUS)
        assertTrue(state.drivingModesActivationPeriods[3].value?.period == 0.1)
        assertTrue(state.drivingModesActivationPeriods[4].value?.drivingMode == DrivingMode.ECO_PLUS)
        assertTrue(state.drivingModesActivationPeriods[4].value?.period == 0.3)
        assertTrue(state.drivingModesActivationPeriods[5].value?.drivingMode == DrivingMode.COMFORT)
        assertTrue(state.drivingModesActivationPeriods[5].value?.period == 0.0)
        assertTrue(state.drivingModesEnergyConsumptions[0].value?.drivingMode == DrivingMode.REGULAR)
        assertTrue(state.drivingModesEnergyConsumptions[0].value?.consumption?.value == 20.2)
        assertTrue(state.drivingModesEnergyConsumptions[0].value?.consumption?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.drivingModesEnergyConsumptions[1].value?.drivingMode == DrivingMode.ECO)
        assertTrue(state.drivingModesEnergyConsumptions[1].value?.consumption?.value == 33.2)
        assertTrue(state.drivingModesEnergyConsumptions[1].value?.consumption?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.drivingModesEnergyConsumptions[2].value?.drivingMode == DrivingMode.SPORT)
        assertTrue(state.drivingModesEnergyConsumptions[2].value?.consumption?.value == 54.3)
        assertTrue(state.drivingModesEnergyConsumptions[2].value?.consumption?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.drivingModesEnergyConsumptions[3].value?.drivingMode == DrivingMode.SPORT_PLUS)
        assertTrue(state.drivingModesEnergyConsumptions[3].value?.consumption?.value == 64.8)
        assertTrue(state.drivingModesEnergyConsumptions[3].value?.consumption?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.drivingModesEnergyConsumptions[4].value?.drivingMode == DrivingMode.ECO_PLUS)
        assertTrue(state.drivingModesEnergyConsumptions[4].value?.consumption?.value == 18.0)
        assertTrue(state.drivingModesEnergyConsumptions[4].value?.consumption?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.drivingModesEnergyConsumptions[5].value?.drivingMode == DrivingMode.COMFORT)
        assertTrue(state.drivingModesEnergyConsumptions[5].value?.consumption?.value == 33.7)
        assertTrue(state.drivingModesEnergyConsumptions[5].value?.consumption?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.lastTripEnergyConsumption.value?.value == 101.3)
        assertTrue(state.lastTripEnergyConsumption.value?.unit == Energy.Unit.KILOWATT_HOURS)
        assertTrue(state.lastTripFuelConsumption.value?.value == 22.5)
        assertTrue(state.lastTripFuelConsumption.value?.unit == Volume.Unit.LITERS)
        assertTrue(state.mileageAfterLastTrip.value?.value == 95632.0)
        assertTrue(state.mileageAfterLastTrip.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.lastTripElectricPortion.value == 0.7)
        assertTrue(state.lastTripAverageEnergyRecuperation.value?.value == 5.68)
        assertTrue(state.lastTripAverageEnergyRecuperation.value?.unit == EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)
        assertTrue(state.lastTripBatteryRemaining.value == 0.5)
        assertTrue(dateIsSame(state.lastTripDate.value, "2018-10-17T12:34:58.000Z"))
        assertTrue(state.averageFuelConsumption.value?.value == 6.5)
        assertTrue(state.averageFuelConsumption.value?.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(state.currentFuelConsumption.value?.value == 7.5)
        assertTrue(state.currentFuelConsumption.value?.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(state.odometerAfterLastTrip.value?.value == 95632.0)
        assertTrue(state.odometerAfterLastTrip.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.safetyDrivingScore.value == 0.7)
        assertTrue(state.rapidAccelerationGrade.value == Grade.EXCELLENT)
        assertTrue(state.rapidDecelerationGrade.value == Grade.NORMAL)
        assertTrue(state.lateNightGrade.value == Grade.EXCELLENT)
        assertTrue(state.distanceOverTime.value?.distance?.value == 1500.88)
        assertTrue(state.distanceOverTime.value?.distance?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.distanceOverTime.value?.time?.value == 10.0)
        assertTrue(state.distanceOverTime.value?.time?.unit == Duration.Unit.WEEKS)
        assertTrue(state.electricConsumptionRateSinceStart.value?.value == 11.22)
        assertTrue(state.electricConsumptionRateSinceStart.value?.unit == EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)
        assertTrue(state.electricConsumptionRateSinceReset.value?.value == 22.33)
        assertTrue(state.electricConsumptionRateSinceReset.value?.unit == EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)
        assertTrue(state.electricDistanceLastTrip.value?.value == 111000.0)
        assertTrue(state.electricDistanceLastTrip.value?.unit == Length.Unit.METERS)
        assertTrue(state.electricDistanceSinceReset.value?.value == 222000.0)
        assertTrue(state.electricDistanceSinceReset.value?.unit == Length.Unit.METERS)
        assertTrue(state.electricDurationLastTrip.value?.value == 77.0)
        assertTrue(state.electricDurationLastTrip.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.electricDurationSinceReset.value?.value == 88.0)
        assertTrue(state.electricDurationSinceReset.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.fuelConsumptionRateLastTrip.value?.value == 5.4)
        assertTrue(state.fuelConsumptionRateLastTrip.value?.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(state.fuelConsumptionRateSinceReset.value?.value == 5.3)
        assertTrue(state.fuelConsumptionRateSinceReset.value?.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(state.averageSpeedLastTrip.value?.value == 44.2)
        assertTrue(state.averageSpeedLastTrip.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.averageSpeedSinceReset.value?.value == 44.2)
        assertTrue(state.averageSpeedSinceReset.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.fuelDistanceLastTrip.value?.value == 111000.0)
        assertTrue(state.fuelDistanceLastTrip.value?.unit == Length.Unit.METERS)
        assertTrue(state.fuelDistanceSinceReset.value?.value == 222000.0)
        assertTrue(state.fuelDistanceSinceReset.value?.unit == Length.Unit.METERS)
        assertTrue(state.drivingDurationLastTrip.value?.value == 77.0)
        assertTrue(state.drivingDurationLastTrip.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.drivingDurationSinceReset.value?.value == 88.0)
        assertTrue(state.drivingDurationSinceReset.value?.unit == Duration.Unit.MINUTES)
        assertTrue(state.ecoScoreTotal.value == 0.7)
        assertTrue(state.ecoScoreFreeWheel.value == 0.7)
        assertTrue(state.ecoScoreConstant.value == 0.7)
        assertTrue(state.ecoScoreBonusRange.value?.value == 0.7)
        assertTrue(state.ecoScoreBonusRange.value?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.tripMeters[0].value?.id == 1)
        assertTrue(state.tripMeters[0].value?.distance?.value == 456.2)
        assertTrue(state.tripMeters[0].value?.distance?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.tripMeters[1].value?.id == 2)
        assertTrue(state.tripMeters[1].value?.distance?.value == 2489.3)
        assertTrue(state.tripMeters[1].value?.distance?.unit == Length.Unit.KILOMETERS)
        assertTrue(state.electricConsumptionAverage.value?.value == 11.22)
        assertTrue(state.electricConsumptionAverage.value?.unit == EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)
        assertTrue(state.brakingEvaluation.value == 0.7)
        assertTrue(state.averageSpeed.value?.value == 77.5)
        assertTrue(state.averageSpeed.value?.unit == Speed.Unit.KILOMETERS_PER_HOUR)
        assertTrue(state.recuperationPower.value?.value == 2300.0)
        assertTrue(state.recuperationPower.value?.unit == Power.Unit.WATTS)
        assertTrue(state.accelerationDurations[0].value?.direction == AccelerationDuration.Direction.LONGITUDINAL)
        assertTrue(state.accelerationDurations[0].value?.type == AccelerationDuration.Type.REGULAR)
        assertTrue(state.accelerationDurations[0].value?.duration?.value == 1234.0)
        assertTrue(state.accelerationDurations[0].value?.duration?.unit == Duration.Unit.MILLISECONDS)
        assertTrue(state.accelerationDurations[1].value?.direction == AccelerationDuration.Direction.LATERAL)
        assertTrue(state.accelerationDurations[1].value?.type == AccelerationDuration.Type.POSITIVE_OUTLIER)
        assertTrue(state.accelerationDurations[1].value?.duration?.value == 1234.0)
        assertTrue(state.accelerationDurations[1].value?.duration?.unit == Duration.Unit.MILLISECONDS)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetUsage() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006800")
        val defaultGetter = Usage.GetUsage()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0068000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d")
        val propertyGetter = Usage.GetUsage(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d"))
    }
    
    @Test
    fun testGetUsageAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006802")
        val created = Usage.GetUsageAvailability()
        assertTrue(created.identifier == Identifier.USAGE)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as Usage.GetUsageAvailability
        assertTrue(resolved.identifier == Identifier.USAGE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetUsageAvailabilitySome() {
        val identifierBytes = Bytes("0102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d")
        val allBytes = Bytes(COMMAND_HEADER + "006802" + identifierBytes)
        val constructed = Usage.GetUsageAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.USAGE)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = Usage.GetUsageAvailability(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as Usage.GetUsageAvailability
        assertTrue(resolved.identifier == Identifier.USAGE)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}