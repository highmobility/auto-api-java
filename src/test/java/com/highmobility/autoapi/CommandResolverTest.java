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

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandResolverTest extends BaseTest {
    @Test public void logsInvalidProperties() {
        setRuntime(CommandResolver.RunTime.JAVA);

        // It tries to match 3 to commands, but in all of these the property parsing will fail
        TestUtils.errorLogExpected(3, () -> {
            Bytes invalidEndParking = new Bytes(
                    COMMAND_HEADER + "004701" +
                            "01000401000104"
            );
            Command command = CommandResolver.resolve(invalidEndParking);
            assertTrue(command.getClass() == ParkingTicket.State.class);
            assertTrue(command.getProperties().length == 1);
        });
    }

    @Test public void handlesIncorrectAutoApiVersion() {
        // if hoodState bytes are correct the State will be returned. Only error is logged.
        Bytes hoodBytes = new Bytes("020B" + "006701" + "01000401000101");
        TestUtils.errorLogExpected(1, () -> CommandResolver.resolve(hoodBytes));

        // For unknown VSS bytes the bytes are just returned in Command instance
        Bytes randomBytes = new Bytes("020B" + "676767" + "676767676767676767");
        TestUtils.errorLogExpected(1, () -> {
            Command command = CommandResolver.resolve(randomBytes);
            assertTrue(command instanceof Command);
            assertTrue(command.equals(randomBytes));
        });
    }
}
