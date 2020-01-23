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
import com.highmobility.autoapi.value.SupportedCapability;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CapabilitiesTest extends BaseTest {
    Bytes bytes = new Bytes
            (COMMAND_HEADER + "001001" +
                    "01000C010009002000050203040506" +
                    "01000A01000700230003020811");

    @Test
    public void capabilities() {
        Command command = CommandResolver.resolve(bytes);
        testState((Capabilities.State) command);
    }

    private void testState(Capabilities.State state) {
        // l11. now capabilities are defined property by property

        // doors
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x02));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x03));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x04));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x05));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x06));

        // trunk
        assertTrue(state.getSupported(Charging.IDENTIFIER, Charging.PROPERTY_ESTIMATED_RANGE));
        assertTrue(state.getSupported(Identifier.CHARGING, (byte) 0x08));
        assertTrue(state.getSupported(Identifier.CHARGING, (byte) 0x11));

        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void getCapabilities() {
        byte[] bytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "001000");
        byte[] commandBytes = new Capabilities.GetCapabilities().getByteArray();
        assertTrue(Arrays.equals(bytes, commandBytes));
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof Capabilities.GetCapabilities);
    }

    @Test public void build() {
        Capabilities.State.Builder builder = new Capabilities.State.Builder();

        builder.addCapability(new Property(new SupportedCapability(Identifier.DOORS,
                new Bytes("0203040506"))));
        builder.addCapability(new Property(new SupportedCapability(Identifier.CHARGING,
                new Bytes("020811"))));

        Capabilities.State state = builder.build();
        assertTrue(bytesTheSame(state, bytes));
        testState(state);
    }

    @Test public void zeroProperties() {
        Capabilities.State.Builder builder = new Capabilities.State.Builder();
        Capabilities.State capabilities = builder.build();
        testEmptyCommand(capabilities);
        assertTrue(capabilities.getLength() == Command.HEADER_LENGTH + 3);

        byte[] bytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "00100100");
        testEmptyCommand((Capabilities.State) CommandResolver.resolve(bytes));
    }

    void testEmptyCommand(Capabilities.State capabilities) {
        assertTrue(capabilities.getCapabilities().length == 0);
        assertTrue(capabilities.getSupported(Identifier.DOORS, (byte) 0x01) == false);
    }
}