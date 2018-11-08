package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlCommand;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetControlMode;
import com.highmobility.autoapi.StartControlMode;
import com.highmobility.autoapi.property.ControlModeValue;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class RemoteControlTest {
    @Test
    public void controlMode() {
        Bytes bytes = new Bytes(
                "002701" +
                        "01000102" +
                        "0200020032");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(ControlMode.TYPE));
        ControlMode state = (ControlMode) command;
        assertTrue(state.getAngle() == 50);
        assertTrue(state.getMode() == ControlModeValue.STARTED);
    }

    @Test public void get() {
        String waitingForBytes = "002700";

        assertTrue(new GetControlMode().equals(waitingForBytes));
        Command command = CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command instanceof GetControlMode);
    }

    @Test public void startControlMode() {
        String waitingForBytes = "00271201000101";
        assertTrue(new StartControlMode(true).equals(waitingForBytes));
        Command command = CommandResolver.resolveHex(waitingForBytes);
        assertTrue(((StartControlMode) command).getStart() == true);
    }

    @Test public void stopControlMode() {
        String waitingForBytes = "00271201000100";
        assertTrue(new StartControlMode(false).equals(waitingForBytes));
        Command command = CommandResolver.resolveHex(waitingForBytes);
        assertTrue(((StartControlMode) command).getStart() == false);
    }

    @Test public void controlCommand() {
        String waitingForBytes = "002704010001030200020032";

        assertTrue(new ControlCommand(3, 50).equals(waitingForBytes));

        Command command = CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command instanceof ControlCommand);
        ControlCommand state = (ControlCommand) command;

        assertTrue(state.getAngle() == 50);
        assertTrue(state.getSpeed() == 3);
    }

    @Test public void state0Properties() {
        String bytes = "002701";
        Command state = CommandResolver.resolveHex(bytes);
        assertTrue(((ControlMode) state).getAngle() == null);
    }
}
