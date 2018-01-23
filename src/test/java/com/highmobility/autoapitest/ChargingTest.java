package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChargeState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChargeState;
import com.highmobility.autoapi.GetTheftAlarmState;
import com.highmobility.autoapi.SetChargeLimit;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.StartStopCharging;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.utils.Bytes;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ChargingTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0023010100010202000200FF03000132040004bf19999a050004bf19999a06000443c8000007000443cd00000800015A090002003C0A0004000000000B000101");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            Assert.fail("init failed");
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
}
