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
import com.highmobility.autoapi.value.measurement.Angle;
import com.highmobility.autoapi.value.measurement.Length;
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
                    "05000D01000A0200402abd80c308feac" + // Heading direction is 13.370123°
                    "06000D01000A12004060b00000000000" + // Vehicle altitude is 133.5m
                    "07000D01000A1200407f400000000000" // Precision is 500m
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        VehicleLocation.State state = (VehicleLocation.State) command;
        testState(state);
    }

    private void testState(VehicleLocation.State state) {
        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008d);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954d);
        assertTrue(state.getHeading().getValue().getValue() == 13.370123d);
        assertTrue(state.getAltitude().getValue().getValue() == 133.5d);
        assertTrue(state.getPrecision().getValue().getValue() == 500d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "003000";
        String commandBytes = ByteUtils.hexFromBytes(new VehicleLocation.GetVehicleLocation().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        VehicleLocation.State.Builder builder = new VehicleLocation.State.Builder();
        Coordinates coordinates = new Coordinates(52.520008d, 13.404954d);
        builder.setCoordinates(new Property(coordinates));
        builder.setHeading(new Property(new Angle(13.370123d, Angle.Unit.DEGREES)));
        builder.setAltitude(new Property(new Length(133.5d, Length.Unit.METERS)));
        builder.setPrecision(new Property(new Length(500d, Length.Unit.METERS)));
        testState(builder.build());
    }
}