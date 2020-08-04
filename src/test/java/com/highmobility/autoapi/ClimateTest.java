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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import com.highmobility.autoapi.value.Time;
import com.highmobility.autoapi.value.Weekday;
import com.highmobility.autoapi.value.measurement.Temperature;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static com.highmobility.autoapi.value.Weekday.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClimateTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "002401" +
                    "01000D01000A1701403319999999999a" + // Inside temperature is 19.1°C
                    "02000D01000A17014028666666666666" + // Outside temperature is 12.2°C
                    "03000D01000A17014035800000000000" + // Driver temperature setting is 21.5°C
                    "04000D01000A17014035b33333333333" + // Passenger temperature setting is 21.7°C
                    "05000401000101" +
                    "06000401000100" +
                    "07000401000100" +
                    "08000401000100" +
                    "09000D01000A17014035333333333333" + // Defrosting temperature setting is 21.2°C
                    "0B000601000305121E" +
                    "0B000601000306121E" +
                    "0C000D01000A1701403599999999999a" // Rear temperature setting is 21.6°C
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Climate.State) command);
    }

    private void testState(Climate.State state) {
        assertTrue(state.getInsideTemperature().getValue().getValue() == 19.1d);
        assertTrue(state.getOutsideTemperature().getValue().getValue() == 12.2d);
        assertTrue(state.getDriverTemperatureSetting().getValue().getValue() == 21.5d);
        assertTrue(state.getPassengerTemperatureSetting().getValue().getValue() == 21.7d);

        assertTrue(state.getHvacState().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getDefoggingState().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getDefrostingState().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getIonisingState().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getDefrostingTemperatureSetting().getValue().getValue() == 21.2d);

        assertTrue(getHvacWeekdayStartingTime(state.getHvacWeekdayStartingTimes(), MONDAY) == null);

        HvacWeekdayStartingTime time1 =
                getHvacWeekdayStartingTime(state.getHvacWeekdayStartingTimes(), Weekday.SATURDAY);
        HvacWeekdayStartingTime time2 =
                getHvacWeekdayStartingTime(state.getHvacWeekdayStartingTimes(), Weekday.SUNDAY);

        assertTrue(time1.getTime().getHour() == 18);
        assertTrue(time1.getTime().getMinute() == 30);
        assertTrue(time2.getTime().getHour() == 18);
        assertTrue(time2.getTime().getMinute() == 30);

        assertTrue(state.getRearTemperatureSetting().getValue().getValue() == 21.6d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    HvacWeekdayStartingTime getHvacWeekdayStartingTime(Property<HvacWeekdayStartingTime>[] times, Weekday weekday) {
        for (Property<HvacWeekdayStartingTime> time : times) {
            if (time.getValue().getWeekday() == weekday) {
                return time.getValue();
            }
        }

        return null;
    }

    @Test
    public void get() {
        String waitingForBytes = COMMAND_HEADER + "002400";
        String commandBytes = ByteUtils.hexFromBytes(new Climate.GetState().getByteArray());

        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test
    public void startStopDefogging() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002401" +
                "06000401000101");
        String commandBytes =
                ByteUtils.hexFromBytes(new Climate.StartStopDefogging(ActiveState.ACTIVE).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.StartStopDefogging command =
                (Climate.StartStopDefogging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDefoggingState().getValue() == ActiveState.ACTIVE);
    }

    @Test
    public void startStopDefrosting() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002401" +
                "07000401000101");
        String commandBytes =
                ByteUtils.hexFromBytes(new Climate.StartStopDefrosting(ActiveState.ACTIVE).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.StartStopDefrosting command =
                (Climate.StartStopDefrosting) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDefrostingState().getValue() == ActiveState.ACTIVE);
    }

    @Test
    public void startStopHvac() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002401" +
                "05000401000101");
        String commandBytes =
                ByteUtils.hexFromBytes(new Climate.StartStopHvac(ActiveState.ACTIVE).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.StartStopHvac command =
                (Climate.StartStopHvac) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getHvacState().getValue() == ActiveState.ACTIVE);
    }

    @Test
    public void StartStopIonising() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002401" +
                "08000401000100");
        String commandBytes =
                ByteUtils.hexFromBytes(new Climate.StartStopIonising(ActiveState.INACTIVE).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.StartStopIonising command =
                (Climate.StartStopIonising) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getIonisingState().getValue() == ActiveState.INACTIVE);
    }

    @Test
    public void setTemperatureSettings() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "002401" +
                "03000D01000A17014035800000000000" +
                "04000D01000A17014035b33333333333" +
                "0C000D01000A1701403599999999999a");

        Climate.SetTemperatureSettings cmd = new Climate.SetTemperatureSettings(
                new Temperature(21.5d, Temperature.Unit.CELSIUS),
                new Temperature(21.7d, Temperature.Unit.CELSIUS),
                new Temperature(21.6d, Temperature.Unit.CELSIUS));

        assertTrue(TestUtils.bytesTheSame(cmd, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.SetTemperatureSettings settings =
                (Climate.SetTemperatureSettings) CommandResolver.resolve(bytes);
        assertTrue(settings.getDriverTemperatureSetting().getValue().getValue() == 21.5d);
        assertTrue(settings.getPassengerTemperatureSetting().getValue().getValue() == 21.7d);
        assertTrue(settings.getRearTemperatureSetting().getValue().getValue() == 21.6d);
    }

    @Test
    public void setClimateProfile() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "002401" +
                "0b0006010003000800" +
                "0b000601000302080A");

        HvacWeekdayStartingTime[] times = new HvacWeekdayStartingTime[2];
        times[0] = new HvacWeekdayStartingTime(Weekday.MONDAY, new Time(8
                , 0));
        times[1] = new HvacWeekdayStartingTime(Weekday.WEDNESDAY,
                new Time(8, 10));
        Bytes commandBytes = new Climate.ChangeStartingTimes(times);
        assertTrue(TestUtils.bytesTheSame(commandBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.ChangeStartingTimes profile =
                (Climate.ChangeStartingTimes) CommandResolver.resolve(bytes);
        assertTrue(profile.getHvacWeekdayStartingTimes().length == 2);
        assertTrue(getHvacWeekdayStartingTime(profile.getHvacWeekdayStartingTimes(),
                Weekday.TUESDAY) == null);
        assertTrue(getHvacWeekdayStartingTime(profile.getHvacWeekdayStartingTimes(),
                Weekday.MONDAY).getTime().equals
                (new Time(8, 0)));
        assertTrue(getHvacWeekdayStartingTime(profile.getHvacWeekdayStartingTimes(),
                Weekday.WEDNESDAY).getTime().equals
                (new Time(8, 10)));
    }

    @Test
    public void setClimateProfile0Properties() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "002401");
        HvacWeekdayStartingTime[] times = new HvacWeekdayStartingTime[0];
        Bytes commandBytes = new Climate.ChangeStartingTimes(times);
        assertTrue(TestUtils.bytesTheSame(commandBytes, bytes));
    }

    @Test
    public void build() {
        Climate.State.Builder builder = new Climate.State.Builder();

        builder.setInsideTemperature(new Property(new Temperature(19.1d, Temperature.Unit.CELSIUS)));

        builder.setOutsideTemperature(new Property(new Temperature(12.2d, Temperature.Unit.CELSIUS)));
        builder.setDriverTemperatureSetting(new Property(new Temperature(21.5d, Temperature.Unit.CELSIUS)));
        builder.setPassengerTemperatureSetting(new Property(new Temperature(21.7d, Temperature.Unit.CELSIUS)));

        builder.setHvacState(new Property(ActiveState.ACTIVE));
        builder.setDefoggingState(new Property(ActiveState.INACTIVE));
        builder.setDefrostingState(new Property(ActiveState.INACTIVE));
        builder.setIonisingState(new Property(ActiveState.INACTIVE));
        builder.setDefrostingTemperatureSetting(new Property(new Temperature(21.2d, Temperature.Unit.CELSIUS)));

        builder.addHvacWeekdayStartingTime(new Property(new HvacWeekdayStartingTime(Weekday.SATURDAY,
                new Time(18, 30))));
        builder.addHvacWeekdayStartingTime(new Property(new HvacWeekdayStartingTime(Weekday.SUNDAY, new Time(18
                , 30))));

        builder.setRearTemperatureSetting(new Property(new Temperature(21.6d, Temperature.Unit.CELSIUS)));

        Climate.State command = builder.build();
        testState(command);
    }
}
