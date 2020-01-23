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

public class RooftopTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "002501" +
            "01000B0100083ff0000000000000" +
            "02000B0100083fe0000000000000" +
            "03000401000101" +
            "04000401000102" +
            "05000401000101"
    );

    @Test
    public void state() {
        RooftopControl.State command = (RooftopControl.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "002500";
        String commandBytes = ByteUtils.hexFromBytes(new RooftopControl.GetRooftopState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlRooftop() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002501" +
                "01000B0100080000000000000000" +
                "02000B0100080000000000000000" +
                "03000401000100" +
                "04000401000102" +
                "05000401000101");

        Bytes commandBytes = new RooftopControl.ControlRooftop(0d, 0d,
                RooftopControl.ConvertibleRoofState.CLOSED,
                RooftopControl.SunroofTiltState.HALF_TILTED,
                RooftopControl.SunroofState.OPEN);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        RooftopControl.ControlRooftop command = (RooftopControl.ControlRooftop) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDimming().getValue() == 0d);
        assertTrue(command.getPosition().getValue() == 0d);
        assertTrue(command.getConvertibleRoofState().getValue() == RooftopControl.ConvertibleRoofState.CLOSED);
        assertTrue(command.getSunroofTiltState().getValue() == RooftopControl.SunroofTiltState.HALF_TILTED);
        assertTrue(command.getSunroofState().getValue() == RooftopControl.SunroofState.OPEN);
    }

    @Test public void stateBuilder() {
        RooftopControl.State.Builder builder = new RooftopControl.State.Builder();

        builder.setDimming(new Property(1d));
        builder.setPosition(new Property(.5d));
        builder.setConvertibleRoofState(new Property(RooftopControl.ConvertibleRoofState.OPEN));
        builder.setSunroofTiltState(new Property(RooftopControl.SunroofTiltState.HALF_TILTED));
        builder.setSunroofState(new Property(RooftopControl.SunroofState.OPEN));

        RooftopControl.State state = builder.build();
        testState(state);
    }

    private void testState(RooftopControl.State state) {
        assertTrue(bytesTheSame(state, bytes));
        assertTrue(state.getDimming().getValue() == 1d);
        assertTrue(state.getPosition().getValue() == .5d);
        assertTrue(state.getConvertibleRoofState().getValue() == RooftopControl.ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState().getValue() == RooftopControl.SunroofTiltState.HALF_TILTED);
        assertTrue(state.getSunroofState().getValue() == RooftopControl.SunroofState.OPEN);
        assertTrue(bytesTheSame(state, bytes));
    }
}
