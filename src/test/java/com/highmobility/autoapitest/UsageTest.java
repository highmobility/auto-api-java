package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetUsage;
import com.highmobility.autoapi.Usage;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class UsageTest {
    Bytes bytes = new Bytes(
            "006801" +
                    "010002029A" +
                    "020002029A" +
                    "03000146" +
                    "04000146" +
                    "050002013C" +
                    "0500020028" +
                    "060005014204CCCD" +
                    "06000500425D999A" +
                    "07000442CA999A" +
                    "08000441B40000" +
                    "97000447BAC85A" +
                    "0A000146" +
                    "0B000440B5C28F" +
                    "0C000132" +
                    "0D0008120A110A2132003C" +
                    "0E000440D00000" +
                    "0F000440F00000"
    );

    @Test public void state() throws ParseException {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == Usage.class);
        Usage state = (Usage) command;

        assertTrue(state.getAverageWeeklyDistance().getValue() == 666);
        assertTrue(state.getAverageWeeklyDistance().getValue() == 666);
        assertTrue(state.getAverageWeeklyDistanceLongTerm().getValue() == 666);
        assertTrue(state.getAccelerationEvaluation().getValue() == .7f);
        assertTrue(state.getDrivingStyleEvaluation().getValue() == .7f);
        assertTrue(state.getDrivingStyleEvaluation().getValue() == .7f);

        assertTrue(state.getDrivingModeActivationPeriods().length == 2);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.ECO).getPercentage() == .6f);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.REGULAR).getPercentage() ==
                .4f);

        assertTrue(state.getDrivingModeEnergyConsumptions().length == 2);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.ECO).getEnergyConsumption()
                == 33.2f);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.REGULAR)
                .getEnergyConsumption() == 55.4f);

        assertTrue(state.getLastTripEnergyConsumption().getValue() == 101.3f);
        assertTrue(state.getLastTripFuelConsumption().getValue() == 22.5f);
        assertTrue(state.getMileageAfterLastTrip().getValue() == 95632.7f);
        assertTrue(state.getLastTripElectricPortion().getValue() == .7f);
        assertTrue(state.getLastTripAverageEnergyRecuperation().getValue() == 5.68f);
        assertTrue(state.getLastTripBatteryRemaining().getValue() == .5f);
        assertTrue(TestUtils.dateIsSame(state.getLastTripDate(), "2018-10-17T10:33:50+0100"));
        assertTrue(state.getAverageFuelConsumption().getValue() == 6.5f);
        assertTrue(state.getCurrentFuelConsumption().getValue() == 7.5f);
    }

    @Test public void build() {
        // TBODO:
        /*Usage.Builder builder = new Usage.Builder();
        Usage state = builder.build();
        assertTrue(state.equals(bytes));*/
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006800");
        Bytes commandBytes = new GetUsage();
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("006801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((Usage) state).getAverageFuelConsumption() == null);
    }
}