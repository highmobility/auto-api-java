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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "002101" +
                    "01000401000100" +
                    "02000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == Trunk.State.class);
        Trunk.State state = (Trunk.State) command;
        testState(state);
    }

    private void testState(Trunk.State state) {
        assertTrue(state.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Trunk.State.Builder builder = new Trunk.State.Builder();
        builder.setLock(new Property(LockState.UNLOCKED));
        builder.setPosition(new Property(Position.OPEN));
        testState(builder.build());
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002100");
        Bytes commandBytes = new Trunk.GetState();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof Trunk.GetState);
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002101" +
                "01000401000100" +
                "02000401000101");
        Command commandBytes = new Trunk.ControlTrunk(LockState.UNLOCKED, Position.OPEN);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof Trunk.ControlTrunk);
        Trunk.ControlTrunk state = (Trunk.ControlTrunk) command;
        assertTrue(state.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
    }
}