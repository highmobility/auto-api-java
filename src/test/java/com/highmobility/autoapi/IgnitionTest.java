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
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class IgnitionTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "003501" +
                    "01000401000101" +
                    "02000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Ignition.State) command);
    }

    private void testState(Ignition.State state) {
        assertTrue(state.getStatus().getValue() == OnOffState.ON);
        assertTrue(state.getAccessoriesStatus().getValue() == OnOffState.ON);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "003500";
        String commandBytes = ByteUtils.hexFromBytes(new Ignition.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "003501" +
                "01000401000101");
        Bytes commandBytes = new Ignition.TurnIgnitionOnOff(OnOffState.ON);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Ignition.TurnIgnitionOnOff incoming =
                (Ignition.TurnIgnitionOnOff) CommandResolver.resolve(waitingForBytes);
        assertTrue(incoming.getStatus().getValue() == OnOffState.ON);
    }

    @Test public void build() {
        Ignition.State.Builder builder = new Ignition.State.Builder();
        builder.setStatus(new Property(OnOffState.ON));
        builder.setAccessoriesStatus(new Property(OnOffState.ON));

        Ignition.State state = builder.build();
        testState(state);
    }
}