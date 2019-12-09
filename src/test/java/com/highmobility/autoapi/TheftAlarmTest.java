package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheftAlarmTest extends BaseTest {
    Bytes bytes = new Bytes(
            "00460101000401000101");

    @Test
    public void state() {
        TheftAlarm.State command = (TheftAlarm.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(TheftAlarm.State state) {
        assertTrue(state.getStatus().getValue() == TheftAlarm.Status.ARMED);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "004600";
        String commandBytes = ByteUtils.hexFromBytes(new TheftAlarm.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setAlarm() {
        Bytes waitingForBytes = new Bytes(
                "004601" +
                        "01000401000101");
        TheftAlarm.SetTheftAlarm command = new TheftAlarm.SetTheftAlarm(TheftAlarm.Status.ARMED);
        assertTrue(bytesTheSame(command, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        command = (TheftAlarm.SetTheftAlarm) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == TheftAlarm.Status.ARMED);
    }

    @Test public void build() {
        TheftAlarm.State.Builder builder = new TheftAlarm.State.Builder();
        builder.setStatus(new Property(TheftAlarm.Status.ARMED));
        TheftAlarm.State state = builder.build();
        testState(state);
    }
}
