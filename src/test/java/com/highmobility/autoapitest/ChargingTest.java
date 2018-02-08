package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChargeState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChargeState;
import com.highmobility.autoapi.SetChargeLimit;
import com.highmobility.autoapi.SetChargeMode;
import com.highmobility.autoapi.SetChargeTimer;
import com.highmobility.autoapi.StartStopCharging;
import com.highmobility.autoapi.property.ChargeMode;
import com.highmobility.autoapi.property.ChargeTimer;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChargingTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0023010100010202000200FF03000132040004bf19999a050004bf19999a06000443c8000007000443cd00000800015A090002003C0A0004000000000B000101"
                    + "0C0001000D00090212010A1020050000");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(ChargeState.TYPE));
        ChargeState state = (ChargeState) command;
        assertTrue(state.getChargingState() == ChargeState.ChargingState.CHARGING);
        assertTrue(state.getEstimatedRange() == 255f);
        assertTrue(state.getBatteryLevel() == .5f);
        assertTrue(state.getChargerVoltageAC() == 400f);
        assertTrue(state.getChargerVoltageDC() == 410f);
        assertTrue(state.getChargeLimit() == .9f);
        assertTrue(state.getTimeToCompleteCharge() == 60f);
        assertTrue(state.getChargeRate() == 0f);
        assertTrue(state.getBatteryCurrentAC() == -.6f);
        assertTrue(state.getBatteryCurrentDC() == -.6f);
        assertTrue(state.getChargePortState() == ChargeState.PortState.OPEN);

        assertTrue(state.getChargeMode() == ChargeMode.IMMEDIATE);
        assertTrue(state.getChargeTimer().getType() == ChargeTimer.Type.DEPARTURE_TIME);
        try {
            assertTrue(TestUtils.dateIsSame(state.getChargeTimer().getTime(), "2018-01-10T16" +
                        ":32:05"));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test public void get() {
        String waitingForBytes = "002300";
        String commandBytes = Bytes.hexFromBytes(new GetChargeState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setChargeLimit() {
        String waitingForBytes = "0023035A";
        String commandBytes = Bytes.hexFromBytes(new SetChargeLimit(.9f).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopCharging() {
        String waitingForBytes = "00230201";
        String commandBytes = Bytes.hexFromBytes(new StartStopCharging(true).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setChargeMode() {
        String waitingForBytes = "00230502";
        String commandBytes = Bytes.hexFromBytes(new SetChargeMode(ChargeMode.INDUCTIVE).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setChargeTimer() {
        byte[] waitingForBytes = Bytes.bytesFromHex("0023060D00090212010a1020050000");
        try {
            Calendar c = TestUtils.getCalendar("2018-01-10T16:32:05");
            byte[] commandBytes = new SetChargeTimer(ChargeTimer.Type.DEPARTURE_TIME, c).getBytes();
            assertTrue(Arrays.equals(waitingForBytes, commandBytes));
        } catch (ParseException e) {
            fail();
        }
    }
}
