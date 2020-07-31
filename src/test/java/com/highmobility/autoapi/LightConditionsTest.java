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
import com.highmobility.autoapi.value.measurement.Illuminance;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class LightConditionsTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "005401" +
                    "01000D01000A110040fb198000000000" +
                    "02000D01000A11003fd0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == LightConditions.State.class);
        LightConditions.State state = (LightConditions.State) command;
        testState(state);
    }

    private void testState(LightConditions.State state) {
        assertTrue(state.getOutsideLight().getValue().getValue() == 111000d);
        assertTrue(state.getInsideLight().getValue().getValue() == .25d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test
    public void build() {
        LightConditions.State.Builder builder = new LightConditions.State.Builder();
        builder.setOutsideLight(new Property<>(new Illuminance(111000d, Illuminance.Unit.LUX)));
        builder.setInsideLight(new Property<>(new Illuminance(.25d, Illuminance.Unit.LUX)));
        LightConditions.State command = builder.build();
        testState(command);
    }

    @Test
    public void get() {
        String waitingForBytes = COMMAND_HEADER + "005400";
        String commandBytes =
                ByteUtils.hexFromBytes(new LightConditions.GetLightConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}