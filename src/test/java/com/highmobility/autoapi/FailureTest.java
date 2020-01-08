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

public class FailureTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "000201" +
            "0100050100020021" +
            "02000401000101" + // set
            "03000401000101" + // reason
            "04000C01000954727920616761696E" + // description
            "05000401000101" // property ids
    );

    @Test
    public void failure() {
        Command command = CommandResolver.resolve(bytes);
        testState((FailureMessage.State) command);
    }

    private void testState(FailureMessage.State state) {
        assertTrue(state.getFailedMessageType().getValue() == Type.SET);
        assertTrue(state.getFailureReason().getValue() == FailureMessage.FailureReason.UNAUTHORISED);
        assertTrue(state.getFailureDescription().getValue().equals("Try again"));
        assertTrue(state.getFailedPropertyIDs().getValue().equals("01"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        FailureMessage.State.Builder builder = new FailureMessage.State.Builder();
        builder.setFailedMessageID(new Property(Identifier.TRUNK));

        builder.setFailedMessageType(new Property(Type.SET));

        builder.setFailureReason(new Property(FailureMessage.FailureReason.UNAUTHORISED));
        builder.setFailureDescription(new Property("Try again"));

        builder.setFailedPropertyIDs(new Property(new Bytes("01")));

        FailureMessage.State failure = builder.build();
        testState(failure);
    }
}
