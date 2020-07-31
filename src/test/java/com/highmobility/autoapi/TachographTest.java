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
import com.highmobility.autoapi.value.Detected;
import com.highmobility.autoapi.value.DriverCardPresent;
import com.highmobility.autoapi.value.DriverTimeState;
import com.highmobility.autoapi.value.DriverWorkingState;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TachographTest extends BaseTest {
    Bytes bytes = new Bytes
            (COMMAND_HEADER + "006401" +
                    "0100050100020102" +
                    "0100050100020200" +
                    "0200050100020102" +
                    "0200050100020206" +
                    "0300050100020101" +
                    "0300050100020201" +
                    "04000401000101" +
                    "05000401000100" +
                    "06000401000100" +
                    "0700050100020050"
            );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        Tachograph.State state = (Tachograph.State) command;
        testState(state);
    }

    private void testState(Tachograph.State state) {
        assertTrue(state.getDriverWorkingState(1).getValue().getWorkingState() == DriverWorkingState.WorkingState.WORKING);
        assertTrue(state.getDriverWorkingState(2).getValue().getWorkingState() == DriverWorkingState.WorkingState.RESTING);
        assertTrue(state.getDriverTimeState(1).getValue().getTimeState() == DriverTimeState.TimeState.FOUR_REACHED);
        assertTrue(state.getDriverTimeState(2).getValue().getTimeState() == DriverTimeState.TimeState.SIXTEEN_REACHED);

        assertTrue(state.getDriverCard(1).getValue().getCardPresent() == DriverCardPresent.CardPresent.PRESENT);
        assertTrue(state.getDriverCard(2).getValue().getCardPresent() == DriverCardPresent.CardPresent.PRESENT);

        assertTrue(state.getVehicleMotion().getValue() == Detected.DETECTED);
        assertTrue(state.getVehicleOverspeed().getValue() == Tachograph.VehicleOverspeed.NO_OVERSPEED);
        assertTrue(state.getVehicleDirection().getValue() == Tachograph.VehicleDirection.FORWARD);
        assertTrue(state.getVehicleSpeed().getValue().getValue() == 80);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Tachograph.State.Builder builder = new Tachograph.State.Builder();

        builder.addDriverWorkingState(new Property(new DriverWorkingState(1,
                DriverWorkingState.WorkingState.WORKING)));
        builder.addDriverWorkingState(new Property(new DriverWorkingState(2,
                DriverWorkingState.WorkingState.RESTING)));

        builder.addDriversTimeState(new Property(new DriverTimeState(1,
                DriverTimeState.TimeState.FOUR_REACHED)));
        builder.addDriversTimeState(new Property(new DriverTimeState(2,
                DriverTimeState.TimeState.SIXTEEN_REACHED)));

        builder.addDriversCardPresent(new Property(new DriverCardPresent(1,
                DriverCardPresent.CardPresent.PRESENT)));
        builder.addDriversCardPresent(new Property(new DriverCardPresent(2,
                DriverCardPresent.CardPresent.PRESENT)));

        builder.setVehicleMotion(new Property(Detected.DETECTED));
        builder.setVehicleOverspeed(new Property(Tachograph.VehicleOverspeed.NO_OVERSPEED));
        builder.setVehicleDirection(new Property(Tachograph.VehicleDirection.FORWARD));
        builder.setVehicleSpeed(new Property(80));

        Tachograph.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006400");
        byte[] commandBytes = new Tachograph.GetState().getByteArray();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof Tachograph.GetState);
    }
}
