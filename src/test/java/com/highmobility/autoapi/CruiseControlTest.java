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

import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CruiseControlTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "006201" +
            "01000401000101" +
            "02000401000101" +
            "030005010002003D" +
            "04000401000100" +
            "050005010002003C"
    );

    @Test
    public void state() {
        CruiseControl.State state = (CruiseControl.State) CommandResolver.resolve(bytes);

        assertTrue(state.getCruiseControl().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getLimiter().getValue() == CruiseControl.Limiter.HIGHER_SPEED_REQUESTED);

        assertTrue(state.getTargetSpeed().getValue() == 61);
        assertTrue(state.getAdaptiveCruiseControl().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getAccTargetSpeed().getValue() == 60);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "006200";
        String commandBytes = ByteUtils.hexFromBytes(new CruiseControl.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "006201" +
                "01000401000101" +
                "030005010002003C");
        byte[] commandBytes =
                new CruiseControl.ActivateDeactivateCruiseControl(ActiveState.ACTIVE, 60)
                .getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void deactivate() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006201" +
                "01000401000100");
        Command commandBytes =
                new CruiseControl.ActivateDeactivateCruiseControl(ActiveState.INACTIVE, null);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));
    }
}