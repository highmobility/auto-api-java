package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoteControlTest extends BaseTest {
    Bytes bytes = new Bytes(
            "002701" +
                    "01000401000102" +
                    "0200050100020032");

    @Test
    public void controlMode() {
        RemoteControl.State state = (RemoteControl.State) CommandResolver.resolve(bytes);
        assertTrue(state.getAngle().getValue() == 50);
        assertTrue(state.getControlMode().getValue() == RemoteControl.ControlMode.STARTED);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("002700");
        assertTrue(new GetControlState().equals(waitingForBytes));
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetControlState);
    }

    @Test public void startRemoteControl() {
        Bytes waitingForBytes = new Bytes("002701" +
                "01000401000102");
        RemoteControl.StartControl command = new RemoteControl.StartControl();
        assertTrue(command.equals(waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof RemoteControl.StartControl);
    }

    @Test public void stopRemoteControl() {
        Bytes waitingForBytes = new Bytes("002701" +
                "01000401000105");
        assertTrue(new RemoteControl.StopControl().equals(waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof RemoteControl.StopControl);
    }

    @Test public void controlCommand() {
        Bytes waitingForBytes = new Bytes("002701" +
                "0200050100020003" +
                "03000401000132");
        RemoteControl.ControlCommand command = new RemoteControl.ControlCommand(3, 50);
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        command = (RemoteControl.ControlCommand) CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof RemoteControl.ControlCommand);
        assertTrue(command.getAngle().getValue() == 3);
        assertTrue(command.getSpeed().getValue() == 50);
    }
}
