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
import com.highmobility.autoapi.value.DashboardLight;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DashboardLightsTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "006101" +
            "0100050100020000" +
            "0100050100020201" +
            "0100050100020F03" +
            "0100050100021502"
    );

    @Test
    public void state() {
        DashboardLights.State state = (DashboardLights.State) CommandResolver.resolve(bytes);
        testState(state);
    }

    private void testState(DashboardLights.State state) {
        assertTrue(state.getDashboardLights().length == 4);

        assertTrue(getDashboardLight(state.getDashboardLights(), DashboardLight.Name.HIGH_BEAM).getState() ==
                DashboardLight.State.INACTIVE);
        assertTrue(getDashboardLight(state.getDashboardLights(),
                DashboardLight.Name.HAZARD_WARNING).getState() == DashboardLight.State.INFO);
        assertTrue(getDashboardLight(state.getDashboardLights(),
                DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE).getState() == DashboardLight.State.RED);
        assertTrue(getDashboardLight(state.getDashboardLights(),
                DashboardLight.Name.ENGINE_OIL_LEVEL).getState() == DashboardLight.State.YELLOW);

        assertTrue(TestUtils.bytesTheSame(bytes, state));
    }

    DashboardLight getDashboardLight(Property<DashboardLight>[] lights, DashboardLight.Name name) {
        for (Property<DashboardLight> light : lights) {
            if (light.getValue().getName() == name) return light.getValue();
        }

        return null;
    }

    @Test public void get() {

        String waitingForBytes = COMMAND_HEADER + "006100";
        String commandBytes =
                ByteUtils.hexFromBytes(new DashboardLights.GetDashboardLights().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        DashboardLights.State.Builder builder = new DashboardLights.State.Builder();
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.HIGH_BEAM,
                DashboardLight.State.INACTIVE)));
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.HAZARD_WARNING,
                DashboardLight
                        .State.INFO)));
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE,
                DashboardLight.State.RED)));
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.ENGINE_OIL_LEVEL,
                DashboardLight
                        .State.YELLOW)));
        DashboardLights.State state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }
}