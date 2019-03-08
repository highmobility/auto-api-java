package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTheftAlarmState;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TheftAlarmTest {
    Bytes bytes = new Bytes(
            "00460101000401000101");

    @Test
    public void state() {

        Command command = CommandResolver.resolve(bytes);
        if (command == null) fail();

        assertTrue(command.is(TheftAlarmState.TYPE));
        TheftAlarmState state = (TheftAlarmState) command;
        assertTrue(state.getState().getValue() == TheftAlarmState.Value.ARMED);
    }

    @Test public void get() {
        String waitingForBytes = "004600";
        String commandBytes = ByteUtils.hexFromBytes(new GetTheftAlarmState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setAlarm() {
        Bytes waitingForBytes = new Bytes(
                "004612" +
                        "01000401000101");
        SetTheftAlarm command = new SetTheftAlarm(TheftAlarmState.Value.ARMED);
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        command = (SetTheftAlarm) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getState().getValue() == TheftAlarmState.Value.ARMED);
    }

    @Test public void build() {
        TheftAlarmState.Builder builder = new TheftAlarmState.Builder();
        builder.setState(new Property(TheftAlarmState.Value.ARMED));
        TheftAlarmState state = builder.build();
        assertTrue(state.equals(bytes));
        assertTrue(state.getState().getValue() == TheftAlarmState.Value.ARMED);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("004601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((TheftAlarmState) state).getState().getValue() == null);
    }
}
