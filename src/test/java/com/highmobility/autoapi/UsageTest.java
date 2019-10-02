package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.DrivingModeActivationPeriod;
import com.highmobility.autoapi.value.DrivingModeEnergyConsumption;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class UsageTest extends BaseTest {
    Bytes bytes = new Bytes(
            "006801" +
                    "010005010002029A" +
                    "020005010002029A" +
                    "03000B0100083FE6666666666666" +
                    "04000B0100083FE6666666666666" +
                    "05000C010009013FE3333333333333" +
                    "05000C010009003FD999999999999A" +
                    "060008010005014204CCCD" +
                    "06000801000500425D999A" +
                    "07000701000442CA999A" +
                    "08000701000441B40000" +
                    "09000701000447BAC85A" +
                    "0A000B0100083FE6666666666666" +
                    "0B000701000440B5C28F" +
                    "0C000B0100083FE0000000000000" +
                    "0D000B0100080000016682059D50" +
                    "0E000701000440D00000" +
                    "0F000701000440F00000"

    );

    @Test public void state() {
        Command command = CommandResolver.resolve(bytes);
        UsageState state = (UsageState) command;
        testState(state);
    }

    private void testState(UsageState state) {
        assertTrue(state.getAverageWeeklyDistance().getValue() == 666);
        assertTrue(state.getAverageWeeklyDistance().getValue() == 666);
        assertTrue(state.getAverageWeeklyDistanceLongRun().getValue() == 666);
        assertTrue(state.getAccelerationEvaluation().getValue() == .7d);
        assertTrue(state.getDrivingStyleEvaluation().getValue() == .7d);
        assertTrue(state.getDrivingStyleEvaluation().getValue() == .7d);

        assertTrue(state.getDrivingModesActivationPeriods().length == 2);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.ECO).getValue().getPercentage() == .6d);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.REGULAR).getValue().getPercentage() ==
                .4d);

        assertTrue(state.getDrivingModesEnergyConsumptions().length == 2);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.ECO).getValue().getConsumption()
                == 33.2f);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.REGULAR).getValue()
                .getConsumption() == 55.4f);

        assertTrue(state.getLastTripEnergyConsumption().getValue() == 101.3f);
        assertTrue(state.getLastTripFuelConsumption().getValue() == 22.5f);
        assertTrue(state.getMileageAfterLastTrip().getValue() == 1203423322);
        assertTrue(state.getLastTripElectricPortion().getValue() == .7d);
        assertTrue(state.getLastTripAverageEnergyRecuperation().getValue() == 5.68f);
        assertTrue(state.getLastTripBatteryRemaining().getValue() == .5d);
        assertTrue(TestUtils.dateIsSame(state.getLastTripDate().getValue(), "2018-10-17T12:34:58"));
        assertTrue(state.getAverageFuelConsumption().getValue() == 6.5f);
        assertTrue(state.getCurrentFuelConsumption().getValue() == 7.5f);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        UsageState.Builder builder = new UsageState.Builder();

        builder.setAverageWeeklyDistance(new Property(666));
        builder.setAverageWeeklyDistanceLongRun(new Property(666));
        builder.setAccelerationEvaluation(new Property(.7d));
        builder.setDrivingStyleEvaluation(new Property(.7d));

        builder.addDrivingModesActivationPeriod(new Property(new DrivingModeActivationPeriod(DrivingMode.ECO, .6d)));
        builder.addDrivingModesActivationPeriod(new Property(new DrivingModeActivationPeriod(DrivingMode.REGULAR, .4d)));

        builder.addDrivingModesEnergyConsumption(new Property(new DrivingModeEnergyConsumption(DrivingMode.ECO, 33.2f)));
        builder.addDrivingModesEnergyConsumption(new Property(new DrivingModeEnergyConsumption(DrivingMode.REGULAR, 55.4f)));

        builder.setLastTripEnergyConsumption(new Property(101.3f));
        builder.setLastTripFuelConsumption(new Property(22.5f));
        builder.setMileageAfterLastTrip(new Property(1203423322));
        builder.setLastTripElectricPortion(new Property(.7d));
        builder.setLastTripAverageEnergyRecuperation(new Property(5.68f));
        builder.setLastTripBatteryRemaining(new Property(.5d));

        builder.setLastTripDate(new Property(TestUtils.getCalendar("2018-10-17T12:34:58")));
        builder.setAverageFuelConsumption(new Property(6.5f));
        builder.setCurrentFuelConsumption(new Property(7.5f));

        UsageState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006800");
        Bytes commandBytes = new GetUsage();
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}