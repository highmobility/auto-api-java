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
import com.highmobility.autoapi.value.measurement.Angle;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class OffRoadTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "005201" +
                    "010005010002000A" +
                    "02000B0100083FE0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        Offroad.State state = (Offroad.State) command;
        testState(state);
    }

    private void testState(Offroad.State state) {
        assertTrue(state.getRouteIncline().getValue().getValue() == 10d);
        assertTrue(state.getWheelSuspension().getValue() == .5d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test
    public void get() {
        String waitingForBytes = COMMAND_HEADER + "005200";
        String commandBytes = new Offroad.GetState().getHex();
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test
    public void build() {
        Offroad.State.Builder builder = new Offroad.State.Builder();
        // TODO: should be new Angle(10d, Angle.Unit.DEGREES))
        builder.setRouteIncline(new Property<>(new Angle(10d, Angle.Unit.DEGREES)));
        builder.setWheelSuspension(new Property<>(.5d));
        Offroad.State state = builder.build();
        testState(state);


        TestValue t = new TestValue(1d);
        setValue(t); // no compiler error, but setValue expects generic <Exception>
    }

    class TestValue<T> {
        TestValue(T value) { }
    }

    public void setValue(TestValue<Exception> value){

    }


}