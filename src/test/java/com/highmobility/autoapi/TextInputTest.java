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

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextInputTest extends BaseTest {
    @Test public void textInputTest() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004401" +
                "010006010003796573");
        byte[] bytes = new TextInput.TextInputCommand("yes").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004401" +
                "01000401000179");
        byte[] bytes = new TextInput.TextInputCommand("y").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "004401" +
                "010003010000");
        TextInput.TextInputCommand ti = new TextInput.TextInputCommand("");
        assertTrue(TestUtils.bytesTheSame(ti, waitingForBytes));
    }

    @Test public void resolve() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004401" +
                "010006010003796573");
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof TextInput.TextInputCommand);
        assertTrue(((TextInput.TextInputCommand) command).getText().getValue().equals("yes"));
    }
}
