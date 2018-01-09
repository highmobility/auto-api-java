package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DiagnosticsState;
import com.highmobility.autoapi.GetDiagnosticsState;
import com.highmobility.autoapi.TireState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DiagnosticsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0033010100030249F00200020063030002003C04000209C40500015A0600020109070004410c000008000440c66666090001010A000B004013d70a4220000002EA0A000B014013d70a4220000002EA0A000B024013d70a4220000002EA0A000B034013d70a4220000002EA0B0004414000000C00043F000000");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == DiagnosticsState.class);
        DiagnosticsState state = (DiagnosticsState)command;

        assertTrue(state.getMileage() == 150000);
        assertTrue(state.getOilTemperature() == 99);
        assertTrue(state.getSpeed() == 60);
        assertTrue(state.getRpm() == 2500);
        assertTrue(state.getRange() == 265);
        assertTrue(state.getFuelLevel() == .9f);
        assertTrue(state.getWasherFluidLevel() == DiagnosticsState.WasherFluidLevel.FULL);

        assertTrue(state.getTireStates().size() == 4);
        boolean leftExists = false, rightExist = false, rearLeftExists = false, rearRightExists = false;

        for (TireState tireState : state.getTireStates()) {
            switch (tireState.getLocation()) {
                case FRONT_LEFT:
                    leftExists = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
                case FRONT_RIGHT:
                    rightExist = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
                case REAR_RIGHT:
                    rearRightExists = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
                case REAR_LEFT:
                    rearLeftExists = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
            }
        }

        assertTrue(leftExists == true);
        assertTrue(rightExist == true);
        assertTrue(rearRightExists == true);
        assertTrue(rearLeftExists == true);

        assertTrue(state.getNonce() == null);
        assertTrue(state.getSignature() == null);
    }

    @Test public void get() {
        String waitingForBytes = "003300";
        String commandBytes = Bytes.hexFromBytes(new GetDiagnosticsState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

//    @Test public void getDiagnosticsCommand() throws Exception {
//        String expectedResult = "00330B0249F00063003C09C45A01";
//
//        assertTrue(Arrays.equals(Bytes.bytesFromHex(
//                expectedResult),
//                getDiagnostics().getAllBytes()));
//    }
//
//    private DiagnosticsTest getDiagnostics() {
//        DiagnosticsTest send = new DiagnosticsTest(
//                150000,
//                99,
//                60,
//                2500,
//                .9f,
//                DiagnosticsState.WasherFluidLevel.FULL
//        );
//
//        return send;
//    }
}
