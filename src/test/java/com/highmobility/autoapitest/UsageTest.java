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
                    "010005010002029A" +
                    "020005010002029A" +
                    "03000B0100083FE6666666666666" +
                    "04000B0100083FE6666666666666" +
                    "05000C010009013FE3333333333333" +
                    "05000C010009003FD999999999999A" +
                    "060008010002014204CCCD" +
                    "06000801000200425D999A" +
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

    @Test public void state() throws ParseException {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == Usage.class);
        Usage state = (Usage) command;

        assertTrue(state.getAverageWeeklyDistance() == 666);
        assertTrue(state.getAverageWeeklyDistance() == 666);
        assertTrue(state.getAverageWeeklyDistanceLongTerm() == 666);
        assertTrue(state.getAccelerationEvaluation() == .7d);
        assertTrue(state.getDrivingStyleEvaluation() == .7d);
        assertTrue(state.getDrivingStyleEvaluation() == .7d);

        assertTrue(state.getDrivingModeActivationPeriods().length == 2);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.ECO).getPercentage() == .6d);
        assertTrue(state.getDrivingModeActivationPeriod(DrivingMode.REGULAR).getPercentage() ==
                .4d);

        assertTrue(state.getDrivingModeEnergyConsumptions().length == 2);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.ECO).getEnergyConsumption()
                == 33.2f);
        assertTrue(state.getDrivingModeEnergyConsumption(DrivingMode.REGULAR)
                .getEnergyConsumption() == 55.4f);

        assertTrue(state.getLastTripEnergyConsumption() == 101.3f);
        assertTrue(state.getLastTripFuelConsumption() == 22.5f);
        assertTrue(state.getMileageAfterLastTrip() == 95632.7f);
        assertTrue(state.getLastTripElectricPortion() == .7d);
        assertTrue(state.getLastTripAverageEnergyRecuperation() == 5.68f);
        assertTrue(state.getLastTripBatteryRemaining() == .5f);
        assertTrue(TestUtils.dateIsSame(state.getLastTripDate(), "2018-10-17T12:34:58"));
        assertTrue(state.getAverageFuelConsumption() == 6.5f);
        assertTrue(state.getCurrentFuelConsumption() == 7.5f);
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