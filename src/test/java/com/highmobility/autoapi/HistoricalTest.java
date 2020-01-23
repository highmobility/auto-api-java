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
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class HistoricalTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "001201" +
            "01001D01001A" + // >> length 19 + 3 + 3
            COMMAND_HEADER + "0020010300050100020000" + // 1 outside lock front left unlocked
            "A2000B01000800000160E1560840"
    );

    @Test
    public void state() {
        Historical.State states = (Historical.State) CommandResolver.resolve(bytes);
        testState(states);
    }

    private void testState(Historical.State state) {
        assertTrue(state.getStates().length == 1);
        Doors.State lockState = (Doors.State) state.getStates()[0].getValue();

        assertTrue(lockState.getLocks()[0].getValue().getLocation() == Location.FRONT_LEFT);
        assertTrue(lockState.getLocks()[0].getValue().getLockState() == LockState.UNLOCKED);

        assertTrue(TestUtils.dateIsSame(lockState.getTimestamp(), "2018-01-10T18:30:00"));
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Lock outsideLock = new Lock(Location.FRONT_LEFT, LockState.UNLOCKED);
        Calendar timestamp = TestUtils.getCalendar("2018-01-10T18:30:00");

        Doors.State.Builder doorsBuilder = new Doors.State.Builder();
        doorsBuilder.addLock(new Property(outsideLock));
        doorsBuilder.setTimestamp(timestamp);
        Doors.State doorsS = doorsBuilder.build();

        Historical.State.Builder historicalBuilder = new Historical.State.Builder();
        historicalBuilder.addState(new Property(doorsS));

        Historical.State historicalStates = historicalBuilder.build();
        testState(historicalStates);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "001201" +
                "0200050100020020" +
                "03000B01000800000160E0EA1388" +
                "04000B01000800000160E1560840"
        );

        Command command = new Historical.RequestStates(
                Identifier.DOORS,
                TestUtils.getCalendar("2018-01-10T16:32:05"),
                TestUtils.getCalendar("2018-01-10T18:30:00")
        );

        assertTrue(bytesTheSame(command, waitingForBytes));
    }
}