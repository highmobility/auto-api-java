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
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HoodTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "006701" + "01000401000101");

    @Test
    public void state() {
        Command state = CommandResolver.resolve(bytes);
        testState((Hood.State) state);
    }

    private void testState(Hood.State state) {
        assertTrue(state.getPosition().getValue() == Hood.Position.OPEN);
        assertTrue(state.equals(bytes));
    }

    @Test public void build() {
        Hood.State.Builder builder = new Hood.State.Builder();
        builder.setPosition(new Property(Hood.Position.OPEN));
        Hood.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006700");
        Bytes bytes = new Hood.GetState();

        assertTrue(waitingForBytes.equals(bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof Hood.GetState);
    }
}
