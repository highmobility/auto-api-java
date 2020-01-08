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
import com.highmobility.autoapi.value.HmkitVersion;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class FirmwareVersionTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "000301" +
                    "010006010003010F21" +
                    "02000F01000C6274737461636B2D75617274" +
                    "03000C01000976312E352D70726F64"
    );

    @Test
    public void state() {
        FirmwareVersion.State command = (FirmwareVersion.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    void testState(FirmwareVersion.State state) {
        HmkitVersion version = state.getHmKitVersion().getValue();

        assertTrue(version.getMajor() == 1);
        assertTrue(version.getMinor() == 15);
        assertTrue(version.getPatch() == 33);
        assertTrue(state.getHmKitBuildName().getValue().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().getValue().equals("v1.5-prod"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        FirmwareVersion.State.Builder builder = new FirmwareVersion.State.Builder();

        builder.setHmKitVersion(new Property(new HmkitVersion(1, 15, 33)));
        builder.setHmKitBuildName(new Property("btstack-uart"));
        builder.setApplicationVersion(new Property("v1.5-prod"));

        FirmwareVersion.State command = builder.build();
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "000300";
        assertTrue(new FirmwareVersion.GetFirmwareVersion().equals(waitingForBytes));
    }
}