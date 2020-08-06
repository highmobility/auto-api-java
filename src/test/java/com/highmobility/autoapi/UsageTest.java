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
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.*;
import com.highmobility.autoapi.value.measurement.*;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class UsageTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "006801" +
                    "01000D01000A12044084d4cccccccccd" + // Average weekly distance is 666.6km
                    "02000D01000A120440884d999999999a" + // Average weekly distance, over long term, is 777.7km
                    "03000B0100083FE6666666666666" +
                    "04000B0100083FE6666666666666" +
                    "05000C010009013FE3333333333333" +
                    "05000C010009003FD999999999999A" +
                    "06000E01000B000c044034333333333333" + // Driving mode 'regular' consumed 20.2kWh of electric energy
                    "06000E01000B010c04404099999999999a" + // Driving mode 'eco' consumed 33.2kWh of electric energy
                    "07000D01000A0c044059533333333333" + // Last trip consumed 101.3kWh of electric energy
                    "08000D01000A19024036800000000000" + // Last trip consumed 22.5L of fuel
                    "0A000B0100083FE6666666666666" +
                    "0B000D01000A0d004016b851eb851eb8" + // 5.68kWh/100km of electric energy was recuperated during last trip
                    "0C000B0100083FE0000000000000" +
                    "0D000B0100080000016682059D50" +
                    "0E000D01000A0f00401a000000000000" + // Average fuel consumption is 6.5L per 100km
                    "0F000D01000A0f00401e000000000000" + // Current fuel consumption is 7.5L per 100km
                    "10000D01000A120440f7590000000000" + // Odometer is 95'632km after last trip
                    "11000B0100083fe6666666666666" + // Safety driving score is evaluated at 70%
                    "12000401000100" + // Rapid acceleration is graded as excellent
                    "13000401000101" + // Rapid deceleration is graded as normal
                    "14000401000100" + // Late night driving is graded as excellent
                    "1500170100141204409773851eb851ec07044024000000000000" + // During the last 10.0 weeks the vehicle travelled 1500.88km
                    "16000D01000A0d00402670a3d70a3d71" + // Consumed 11.22kWh/100km since the start of a trip
                    "17000D01000A0d004036547ae147ae14" + // Consumed 22.33kWh/100km since a reset
                    "18000D01000A120040fb198000000000" + // Vehicle travelled 111000.0m using electricity during the last trip
                    "19000D01000A1200410b198000000000" + // Vehicle travelled 222000.0m using electricity since last reset
                    "1a000D01000A07014053400000000000" + // Vehicle travelled 77.0min using electricity during last trip
                    "1b000D01000A07014056000000000000" + // Vehicle travelled 88.0min using electricity since last reset
                    "1c000D01000A0f00401599999999999a" + // Consumed 5.4 L/100km during last trip
                    "1d000D01000A0f004015333333333333" + // Consumed 5.3 L/100km since reset
                    "1e000D01000A1601404619999999999a" + // Average speed was 44.2km/h during last trip
                    "1f000D01000A1601404619999999999a" + // Average speed was 44.2km/h since reset
                    "20000D01000A120040fb198000000000" + // Vehicle travelled 111000.0m using fuel during the last trip
                    "21000D01000A1200410b198000000000" + // Vehicle travelled 222000.0m using fuel since last reset
                    "22000D01000A07014053400000000000" + // Vehicle travelled 77.0min during last trip
                    "23000D01000A07014056000000000000" + // Vehicle travelled 88.0min since last reset
                    "24000B0100083fe6666666666666" + // Total eco-score rating is 70%
                    "25000B0100083fe6666666666666" + // Eco-score free-wheeling rating is 70%
                    "26000B0100083fe6666666666666" + // Eco-score constant is 70%
                    "27000D01000A12043fe6666666666666" // Eco-score bonus range is 0.7km
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        Usage.State state = (Usage.State) command;
        testState(state);
    }

    private void testState(Usage.State state) {
        assertTrue(state.getAverageWeeklyDistance().getValue().getValue() == 666.6d);
        assertTrue(state.getAverageWeeklyDistanceLongRun().getValue().getValue() == 777.7d);
        assertTrue(state.getAccelerationEvaluation().getValue() == .7d);
        assertTrue(state.getDrivingStyleEvaluation().getValue() == .7d);
        assertTrue(state.getDrivingStyleEvaluation().getValue() == .7d);

        assertTrue(state.getDrivingModesActivationPeriods().length == 2);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.ECO).getValue().getPeriod() == .6d);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.REGULAR).getValue().getPeriod() == .4d);

        assertTrue(state.getDrivingModesEnergyConsumptions().length == 2);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.ECO).getValue().getConsumption().getValue() == 33.2d);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.REGULAR).getValue().getConsumption().getValue() == 20.2d);

        assertTrue(state.getLastTripEnergyConsumption().getValue().getValue() == 101.3d);
        assertTrue(state.getLastTripFuelConsumption().getValue().getValue() == 22.5d);

        assertTrue(state.getLastTripElectricPortion().getValue() == .7d);
        assertTrue(state.getLastTripAverageEnergyRecuperation().getValue().getValue() == 5.68d);
        assertTrue(state.getLastTripBatteryRemaining().getValue() == .5d);
        assertTrue(TestUtils.dateIsSame(state.getLastTripDate().getValue(), "2018-10-17T12:34:58"));
        assertTrue(state.getAverageFuelConsumption().getValue().getValue() == 6.5d);
        assertTrue(state.getCurrentFuelConsumption().getValue().getValue() == 7.5d);

        assertTrue(state.getOdometerAfterLastTrip().getValue().getValue() == 95632d);
        assertTrue(state.getSafetyDrivingScore().getValue() == .7d);
        assertTrue(state.getRapidAccelerationGrade().getValue() == Grade.EXCELLENT);
        assertTrue(state.getRapidDecelerationGrade().getValue() == Grade.NORMAL);
        assertTrue(state.getLateNightGrade().getValue() == Grade.EXCELLENT);

        assertTrue(state.getDistanceOverTime().getValue().getDistance().getValue() == 1500.88d);
        assertTrue(state.getDistanceOverTime().getValue().getTime().getValue() == 10d);

        assertTrue(state.getElectricConsumptionRateSinceStart().getValue().getValue() == 11.22d);
        assertTrue(state.getElectricConsumptionRateSinceReset().getValue().getValue() == 22.33d);
        assertTrue(state.getElectricDistanceLastTrip().getValue().getValue() == 111000d);
        assertTrue(state.getElectricDistanceSinceReset().getValue().getValue() == 222000d);

        assertTrue(state.getDrivingDurationLastTrip().getValue().getValue() == 77d);
        assertTrue(state.getDrivingDurationSinceReset().getValue().getValue() == 88d);
        assertTrue(state.getFuelConsumptionRateLastTrip().getValue().getValue() == 5.4d);
        assertTrue(state.getFuelConsumptionRateSinceReset().getValue().getValue() == 5.3d);
        assertTrue(state.getAverageSpeedLastTrip().getValue().getValue() == 44.2d);

        assertTrue(state.getAverageSpeedSinceReset().getValue().getValue() == 44.2d);
        assertTrue(state.getFuelDistanceLastTrip().getValue().getValue() == 111000d);
        assertTrue(state.getFuelDistanceSinceReset().getValue().getValue() == 222000d);
        assertTrue(state.getDrivingDurationLastTrip().getValue().getValue() == 77d);
        assertTrue(state.getDrivingDurationSinceReset().getValue().getValue() == 88d);

        assertTrue(state.getEcoScoreTotal().getValue() == .7d);
        assertTrue(state.getEcoScoreFreeWheel().getValue() == .7d);
        assertTrue(state.getEcoScoreConstant().getValue() == .7d);
        assertTrue(state.getEcoScoreBonusRange().getValue().getValue() == .7d);

        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test
    public void build() {
        Usage.State.Builder builder = new Usage.State.Builder();

        builder.setAverageWeeklyDistance(new Property(new Length(666.6d, Length.Unit.KILOMETERS)));
        builder.setAverageWeeklyDistanceLongRun(new Property(new Length(777.7d, Length.Unit.KILOMETERS)));
        builder.setAccelerationEvaluation(new Property(.7d));
        builder.setDrivingStyleEvaluation(new Property(.7d));

        builder.addDrivingModesActivationPeriod(new Property(new DrivingModeActivationPeriod(DrivingMode.ECO, .6d)));
        builder.addDrivingModesActivationPeriod(new Property(new DrivingModeActivationPeriod(DrivingMode.REGULAR, .4d)));

        builder.addDrivingModeEnergyConsumption(new Property(
                new DrivingModeEnergyConsumption(DrivingMode.REGULAR, new Energy(20.2d, Energy.Unit.KILOWATT_HOURS))));

        builder.addDrivingModeEnergyConsumption(new Property(
                new DrivingModeEnergyConsumption(DrivingMode.ECO, new Energy(33.2d, Energy.Unit.KILOWATT_HOURS))));

        builder.setLastTripEnergyConsumption(new Property(new Energy(101.3d, Energy.Unit.KILOWATT_HOURS)));
        builder.setLastTripFuelConsumption(new Property(new Volume(22.5d, Volume.Unit.LITERS)));

        builder.setLastTripElectricPortion(new Property(.7d));
        builder.setLastTripAverageEnergyRecuperation(new Property(new EnergyEfficiency(5.68d, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)));
        builder.setLastTripBatteryRemaining(new Property(.5d));

        builder.setLastTripDate(new Property(TestUtils.getCalendar("2018-10-17T12:34:58")));
        builder.setAverageFuelConsumption(new Property(new FuelEfficiency(6.5d, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)));
        builder.setCurrentFuelConsumption(new Property(new FuelEfficiency(7.5d, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)));

        builder.setOdometerAfterLastTrip(new Property(new Length(95632d, Length.Unit.KILOMETERS)));
        builder.setSafetyDrivingScore(new Property(.7d));

        builder.setRapidAccelerationGrade(new Property(Grade.EXCELLENT));
        builder.setRapidDecelerationGrade(new Property(Grade.NORMAL));
        builder.setLateNightGrade(new Property(Grade.EXCELLENT));

        builder.setDistanceOverTime(new Property(
                new DistanceOverTime(new Length(1500.88d, Length.Unit.KILOMETERS), new Duration(10d, Duration.Unit.WEEKS))));

        builder.setElectricConsumptionRateSinceStart(new Property(new EnergyEfficiency(11.22d, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)));
        builder.setElectricConsumptionRateSinceReset(new Property(new EnergyEfficiency(22.33d, EnergyEfficiency.Unit.KWH_PER_100_KILOMETERS)));
        builder.setElectricDistanceLastTrip(new Property(new Length(111000d, Length.Unit.METERS)));
        builder.setElectricDistanceSinceReset(new Property(new Length(222000d, Length.Unit.METERS)));

        builder.setElectricDurationLastTrip(new Property<>(new Duration(77d, Duration.Unit.MINUTES)));
        builder.setElectricDurationSinceReset(new Property<>(new Duration(88d, Duration.Unit.MINUTES)));

        builder.setFuelConsumptionRateLastTrip(new Property(new FuelEfficiency(5.4d, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)));
        builder.setFuelConsumptionRateSinceReset(new Property(new FuelEfficiency(5.3d, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)));
        builder.setAverageSpeedLastTrip(new Property(new Speed(44.2d, Speed.Unit.KILOMETERS_PER_HOUR)));

        builder.setAverageSpeedSinceReset(new Property(new Speed(44.2d, Speed.Unit.KILOMETERS_PER_HOUR)));
        builder.setFuelDistanceLastTrip(new Property(new Length(111000d, Length.Unit.METERS)));
        builder.setFuelDistanceSinceReset(new Property(new Length(222000d, Length.Unit.METERS)));

        builder.setDrivingDurationLastTrip(new Property(new Duration(77d, Duration.Unit.MINUTES)));
        builder.setDrivingDurationSinceReset(new Property(new Duration(88d, Duration.Unit.MINUTES)));

        builder.setEcoScoreTotal(new Property(.7d));
        builder.setEcoScoreFreeWheel(new Property(.7d));
        builder.setEcoScoreConstant(new Property(.7d));
        builder.setEcoScoreBonusRange(new Property(new Length(.7d, Length.Unit.KILOMETERS)));

        Usage.State state = builder.build();
        testState(state);
    }

    @Test
    public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006800");
        Bytes commandBytes = new Usage.GetUsage();
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}