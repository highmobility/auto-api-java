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
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NaviDestinationTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "003101" +
                    "010013010010404A428F9F44D445402ACF562174C4CE" +
                    "0200090100064265726C696E");

    @Test
    public void state() {
        NaviDestination.State command = (NaviDestination.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(NaviDestination.State state) {
        assertTrue(state.getDestinationName().getValue().equals("Berlin"));
        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "003100";
        assertTrue(new NaviDestination.GetNaviDestination().equals(waitingForBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "003101" +
                "010013010010404A428F9F44D445402ACF562174C4CE" +
                "0200090100064265726c696e");

        Bytes commandBytes = new NaviDestination.SetNaviDestination(new Coordinates(52.520008, 13.404954),
                "Berlin");

        assertTrue(bytesTheSame(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        NaviDestination.SetNaviDestination command = (NaviDestination.SetNaviDestination) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDestinationName().getValue().equals("Berlin"));
        assertTrue(command.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(command.getCoordinates().getValue().getLongitude() == 13.404954);
    }

    @Test public void build() {
        NaviDestination.State.Builder builder = new NaviDestination.State.Builder();
        builder.setCoordinates(new Property(new Coordinates(52.520008, 13.404954)));
        builder.setDestinationName(new Property("Berlin"));
        NaviDestination.State state = builder.build();
        testState(state);
    }
}