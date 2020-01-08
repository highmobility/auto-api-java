/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoteControlTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "002701" +
                    "01000401000102" +
                    "0200050100020032");

    @Test
    public void controlMode() {
        RemoteControl.State state = (RemoteControl.State) CommandResolver.resolve(bytes);
        assertTrue(state.getAngle().getValue() == 50);
        assertTrue(state.getControlMode().getValue() == RemoteControl.ControlMode.STARTED);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002700");
        assertTrue(new RemoteControl.GetControlState().equals(waitingForBytes));
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof RemoteControl.GetControlState);
    }

    @Test public void startRemoteControl() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002701" +
                "01000401000102");
        RemoteControl.StartControl command = new RemoteControl.StartControl();
        assertTrue(command.equals(waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof RemoteControl.StartControl);
    }

    @Test public void stopRemoteControl() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002701" +
                "01000401000105");
        assertTrue(new RemoteControl.StopControl().equals(waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof RemoteControl.StopControl);
    }

    @Test public void controlCommand() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002701" +
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
