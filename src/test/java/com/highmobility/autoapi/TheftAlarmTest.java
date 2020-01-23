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
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheftAlarmTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "00460101000401000101");

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
        String waitingForBytes = COMMAND_HEADER + "004600";
        String commandBytes = ByteUtils.hexFromBytes(new TheftAlarm.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setAlarm() {
        Bytes waitingForBytes = new Bytes(
                COMMAND_HEADER + "004601" +
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
