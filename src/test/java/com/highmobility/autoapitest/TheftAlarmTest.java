package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTheftAlarmState;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TheftAlarmTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "00460101000101");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        if (command == null) fail();

        assertTrue(command.is(TheftAlarmState.TYPE));
        TheftAlarmState state = (TheftAlarmState) command;
        assertTrue(state.getState() == TheftAlarmState.State.ARMED);
    }

    @Test public void get() {
        String waitingForBytes = "004600";
        String commandBytes = ByteUtils.hexFromBytes(new GetTheftAlarmState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setAlarm() {
        String waitingForBytes = "00461201000101";
        String commandBytes = ByteUtils.hexFromBytes(new SetTheftAlarm(TheftAlarmState.State
                .ARMED).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetTheftAlarm command = (SetTheftAlarm) CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.getState() == TheftAlarmState.State.ARMED);
    }

    @Test public void build() {
        TheftAlarmState.Builder builder = new TheftAlarmState.Builder();
        builder.setState(TheftAlarmState.State.ARMED);
        TheftAlarmState state = builder.build();
        assertTrue(Arrays.equals(state.getByteArray(), ByteUtils.bytesFromHex("00460101000101")));
        assertTrue(state.getState() == TheftAlarmState.State.ARMED);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("004601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((TheftAlarmState) state).getState() == null);
    }
}
