package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheftAlarmTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "00460101000401000101");

    @Test
    public void state() {
        TheftAlarmState command = (TheftAlarmState) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(TheftAlarmState state) {
        assertTrue(state.getStatus().getValue() == TheftAlarmState.Status.ARMED);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "004600";
        String commandBytes = ByteUtils.hexFromBytes(new GetTheftAlarmState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setAlarm() {
        Bytes waitingForBytes = new Bytes(
                COMMAND_HEADER + "004601" +
                        "01000401000101");
        SetTheftAlarm command = new SetTheftAlarm(TheftAlarmState.Status.ARMED);
        assertTrue(bytesTheSame(command, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        command = (SetTheftAlarm) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == TheftAlarmState.Status.ARMED);
    }

    @Test public void build() {
        TheftAlarmState.Builder builder = new TheftAlarmState.Builder();
        builder.setStatus(new Property(TheftAlarmState.Status.ARMED));
        TheftAlarmState state = builder.build();
        testState(state);
    }
}
