package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTheftAlarmState;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TheftAlarmTest {
    Bytes bytes = new Bytes(
            "00460101000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        if (command == null) fail();

        assertTrue(command.is(TheftAlarmState.TYPE));
        TheftAlarmState state = (TheftAlarmState) command;
        testState(state);
    }

    private void testState(TheftAlarmState state) {
        assertTrue(state.getState().getValue() == TheftAlarmState.Value.ARMED);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
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
        testState(state);
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(() -> {
            assertTrue(CommandResolver.resolve(SetTheftAlarm.TYPE.getIdentifierAndType()).getClass() == Command.class);
        });
    }
}
