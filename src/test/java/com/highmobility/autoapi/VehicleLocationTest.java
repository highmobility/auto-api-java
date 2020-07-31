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
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleLocationTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "003001" +
                    "040013010010404A428F9F44D445402ACF562174C4CE" +
                    "05000B010008402ABD80C308FEAC" +
                    "06000B0100084060B00000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        VehicleLocation.State state = (VehicleLocation.State) command;
        testState(state);
    }

    private void testState(VehicleLocation.State state) {
        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954);
        assertTrue(state.getHeading().getValue().getValue() == 13.370123);
        assertTrue(state.getAltitude().getValue().getValue() == 133.5);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "003000";
        String commandBytes = ByteUtils.hexFromBytes(new VehicleLocation.GetVehicleLocation().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        VehicleLocation.State.Builder builder = new VehicleLocation.State.Builder();
        Coordinates coordinates = new Coordinates(52.520008, 13.404954);
        builder.setCoordinates(new Property(coordinates));
        builder.setHeading(new Property(13.370123));
        builder.setAltitude(new Property(133.5));
        testState(builder.build());
    }
}