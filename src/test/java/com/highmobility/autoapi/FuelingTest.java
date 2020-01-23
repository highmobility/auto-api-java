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
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FuelingTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "004001" +
            "02000401000101" +
            "03000401000100"
    );

    @Test
    public void state() {
        Command state = CommandResolver.resolve(bytes);
        testState((Fueling.State) state);
    }

    private void testState(Fueling.State state) {
        assertTrue(state.getGasFlapLock().getValue() == LockState.LOCKED);
        assertTrue(state.getGasFlapPosition().getValue() == Position.CLOSED);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Fueling.State.Builder builder = new Fueling.State.Builder();

        builder.setGasFlapLock(new Property(LockState.LOCKED));
        builder.setGasFlapPosition(new Property(Position.CLOSED));

        Fueling.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004000");
        byte[] bytes = new Fueling.GetGasFlapState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof Fueling.GetGasFlapState);
    }

    @Test public void control() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004001" +
                "02000401000100" +
                "03000401000101");

        byte[] bytes = new Fueling.ControlGasFlap(LockState.UNLOCKED, Position.OPEN).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Fueling.ControlGasFlap openCloseGasFlap =
                (Fueling.ControlGasFlap) CommandResolver.resolve(waitingForBytes);
        assertTrue(Arrays.equals(openCloseGasFlap.getByteArray(), waitingForBytes));
    }
}
