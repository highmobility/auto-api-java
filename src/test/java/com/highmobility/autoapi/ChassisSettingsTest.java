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
import com.highmobility.autoapi.value.Axle;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.SpringRate;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.Torque;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest extends BaseTest {
    Bytes bytes = new Bytes
            (COMMAND_HEADER + "005301" +
                    "01000401000101" +
                    "02000401000101" +
                    "0500050100020015" +
                    "0500050100020117" +
                    "0600050100020025" +
                    "0600050100020127" +
                    "0700050100020010" +
                    "0700050100020112" +
                    "08000401000119" +
                    "09000401000137" +
                    "0A0004010001E4");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((ChassisSettings.State) command);
    }

    void testState(ChassisSettings.State state) {
        assertTrue(state.getDrivingMode().getValue() == DrivingMode.ECO);
        assertTrue(state.getSportChrono().getValue() == ChassisSettings.SportChrono.ACTIVE);

        assertTrue(getSpringRate(state.currentSpringRates, Axle.FRONT).getSpringRate().getValue() == 21);
        assertTrue(getSpringRate(state.currentSpringRates, Axle.REAR).getSpringRate().getValue() == 23);

        assertTrue(getSpringRate(state.maximumSpringRates, Axle.FRONT).getSpringRate().getValue() == 37);
        assertTrue(getSpringRate(state.maximumSpringRates, Axle.REAR).getSpringRate().getValue() == 39);

        assertTrue(getSpringRate(state.minimumSpringRates, Axle.FRONT).getSpringRate().getValue() == 16);
        assertTrue(getSpringRate(state.minimumSpringRates, Axle.REAR).getSpringRate().getValue() == 18);

        assertTrue(state.getCurrentChassisPosition().getValue().getValue() == 25);
        assertTrue(state.getMaximumChassisPosition().getValue().getValue() == 55);
        assertTrue(state.getMinimumChassisPosition().getValue().getValue() == -28);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    SpringRate getSpringRate(Property<SpringRate>[] springRates, Axle axle) {
        for (Property<SpringRate> springRate : springRates) {
            if (springRate.getValue().getAxle() == axle) {
                return springRate.getValue();
            }
        }

        return null;
    }

    @Test
    public void get() {

        String waitingForBytes = COMMAND_HEADER + "005300";
        String commandBytes =
                ByteUtils.hexFromBytes(new ChassisSettings.GetChassisSettings().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test
    public void setDrivingMode() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "00530101000401000103");
        Bytes commandBytes = new ChassisSettings.SetDrivingMode(DrivingMode.SPORT_PLUS);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ChassisSettings.SetDrivingMode drivingMode =
                (ChassisSettings.SetDrivingMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(drivingMode.getDrivingMode().getValue() == DrivingMode.SPORT_PLUS);
    }

    @Test
    public void startChrono() {
        String waitingForBytes = COMMAND_HEADER + "00530102000401000101";
        String commandBytes =
                ByteUtils.hexFromBytes(new ChassisSettings.StartStopSportsChrono(ChassisSettings.SportChrono.ACTIVE)
                        .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ChassisSettings.StartStopSportsChrono command =
                (ChassisSettings.StartStopSportsChrono) CommandResolver.resolve(ByteUtils
                        .bytesFromHex(waitingForBytes));
        assertTrue(command.getSportChrono().getValue() == ChassisSettings.SportChrono.ACTIVE);
    }

    @Test
    public void setSpringRate() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "005301" +
                "0500050100020119");

        SpringRate prop = new SpringRate(Axle.REAR, new Torque(25d, Torque.Unit.NEWTON_MILLIMETERS));
        SpringRate[] props = new SpringRate[]{prop};
        Bytes commandBytes = new ChassisSettings.SetSpringRates(props);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ChassisSettings.SetSpringRates command =
                (ChassisSettings.SetSpringRates) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getCurrentSpringRates().length == 1);
        assertTrue(getSpringRate(command.getCurrentSpringRates(), Axle.REAR).getSpringRate().getValue() == 25d);
    }

    @Test
    public void setChassisPosition() throws CommandParseException, NoPropertiesException {
        String waitingForBytes = COMMAND_HEADER + "00530108000401000132";
        String commandBytes =
                ByteUtils.hexFromBytes(new ChassisSettings.SetChassisPosition(new Length(50d, Length.Unit.MILLIMETERS)).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ChassisSettings.SetChassisPosition command =
                (ChassisSettings.SetChassisPosition) CommandResolver.resolve(ByteUtils
                        .bytesFromHex(waitingForBytes));
        assertTrue(command.getCurrentChassisPosition().getValue().getValue() == 50d);
    }

    @Test
    public void setNegativeChassisPosition() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "005301" +
                "080004010001E4");
        Command state = new ChassisSettings.SetChassisPosition(new Length(-28d, Length.Unit.MILLIMETERS));
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ChassisSettings.SetChassisPosition command =
                (ChassisSettings.SetChassisPosition) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getCurrentChassisPosition().getValue().getValue() == -28d);
    }

    @Test
    public void build() {
        ChassisSettings.State.Builder builder = new ChassisSettings.State.Builder();

        builder.setDrivingMode(new Property(DrivingMode.ECO));
        builder.setSportChrono(new Property(ChassisSettings.SportChrono.ACTIVE));

        builder.addCurrentSpringRate(new Property(new SpringRate(Axle.FRONT, new Torque(21d, Torque.Unit.NEWTON_MILLIMETERS))));
        builder.addCurrentSpringRate(new Property(new SpringRate(Axle.REAR, new Torque(23d, Torque.Unit.NEWTON_MILLIMETERS))));

        builder.addMaximumSpringRate(new Property(new SpringRate(Axle.FRONT, new Torque(37d, Torque.Unit.NEWTON_MILLIMETERS))));
        builder.addMaximumSpringRate(new Property(new SpringRate(Axle.REAR, new Torque(39d, Torque.Unit.NEWTON_MILLIMETERS))));

        builder.addMinimumSpringRate(new Property(new SpringRate(Axle.FRONT, new Torque(16d, Torque.Unit.NEWTON_MILLIMETERS))));
        builder.addMinimumSpringRate(new Property(new SpringRate(Axle.REAR, new Torque(18d, Torque.Unit.NEWTON_MILLIMETERS))));

        builder.setCurrentChassisPosition(new Property(new Length(25, Length.Unit.MILLIMETERS)));
        builder.setMaximumChassisPosition(new Property(new Length(55, Length.Unit.MILLIMETERS)));
        builder.setMinimumChassisPosition(new Property(new Length(-28, Length.Unit.MILLIMETERS)));

        ChassisSettings.State state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }
}