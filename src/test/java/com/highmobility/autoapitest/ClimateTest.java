package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetClimateState;
import com.highmobility.autoapi.SetHvacStartingTimes;
import com.highmobility.autoapi.SetTemperatureSettings;
import com.highmobility.autoapi.StartStopDefogging;
import com.highmobility.autoapi.StartStopDefrosting;
import com.highmobility.autoapi.StartStopHvac;
import com.highmobility.autoapi.StartStopIonising;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ClimateTest {
    Bytes bytes = new Bytes(
            "002401" +
                    "01000441980000" +
                    "02000441400000" +
                    "03000441AC0000" +
                    "04000441AC0000" +
                    "05000101" +
                    "06000100" +
                    "07000100" +
                    "08000100" +
                    "09000441AC0000" +
                    "0B000305121E" +
                    "0B000306121E" +
                    "0C000441AC0000");

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        if (command == null) fail();

        assertTrue(command.is(ClimateState.TYPE));
        ClimateState state = (ClimateState) command;

        assertTrue(command.getClass() == ClimateState.class);
        assertTrue(state.getInsideTemperature() == 19f);
        assertTrue(state.getOutsideTemperature() == 12f);
        assertTrue(state.getDriverTemperatureSetting() == 21.5f);
        assertTrue(state.getPassengerTemperatureSetting() == 21.5f);

        assertTrue(state.isHvacActive() == true);
        assertTrue(state.isDefoggingActive() == false);
        assertTrue(state.isDefrostingActive() == false);
        assertTrue(state.isIonisingActive() == false);
        assertTrue(state.getDefrostingTemperature() == 21.5f);

        assertTrue(state.getHvacStartingTime(HvacStartingTime.Weekday.MONDAY) == null);

        HvacStartingTime time1 = state.getHvacStartingTime(HvacStartingTime.Weekday.SATURDAY);
        HvacStartingTime time2 = state.getHvacStartingTime(HvacStartingTime.Weekday.SUNDAY);
        assertTrue(time1.getTime().getHour() == 18);
        assertTrue(time1.getTime().getMinute() == 30);
        assertTrue(time2.getTime().getHour() == 18);
        assertTrue(time2.getTime().getMinute() == 30);

        assertTrue(state.getRearTemperatureSetting() == 21.5f);
    }

    @Test public void get() {
        String waitingForBytes = "002400";
        String commandBytes = ByteUtils.hexFromBytes(new GetClimateState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopDefogging() {
        Bytes waitingForBytes = new Bytes("00241401000101");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopDefogging(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopDefogging command = (StartStopDefogging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.start());
    }

    @Test public void startStopDefrosting() {
        Bytes waitingForBytes = new Bytes("00241501000101");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopDefrosting(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopDefrosting command = (StartStopDefrosting) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.start() == true);
    }

    @Test public void startStopHvac() {
        Bytes waitingForBytes = new Bytes("00241301000101");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopHvac(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopHvac command = (StartStopHvac) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.start() == true);
    }

    @Test public void StartStopIonising() {
        Bytes waitingForBytes = new Bytes("00241601000100");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopIonising(false).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopIonising command = (StartStopIonising) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.start() == false);
    }

    @Test public void setTemperatureSettings() {
        Bytes bytes = new Bytes("002417" +
                "01000441a40000" +
                "02000441a40000" +
                "03000441980000");

        SetTemperatureSettings cmd = new SetTemperatureSettings(20.5f, 20.5f, 19f);
        assertTrue(cmd.equals(bytes));

        SetTemperatureSettings settings = (SetTemperatureSettings) CommandResolver.resolve(bytes);
        assertTrue(settings.getDriverTemperature() == 20.5f);
        assertTrue(settings.getPassengerTemperature() == 20.5f);
        assertTrue(settings.getRearTemperature() == 19f);
    }

    @Test public void setClimateProfile() {
        Bytes bytes = new Bytes("002412" +
                "010003000800" +
                "01000302080A");

        HvacStartingTime[] times = new HvacStartingTime[2];
        times[0] = new HvacStartingTime(HvacStartingTime.Weekday.MONDAY, new Time(8, 0));
        times[1] = new HvacStartingTime(HvacStartingTime.Weekday.WEDNESDAY, new Time(8, 10));
        Bytes commandBytes = new SetHvacStartingTimes(times);
        assertTrue(TestUtils.bytesTheSame(commandBytes, bytes));

        SetHvacStartingTimes profile = (SetHvacStartingTimes) CommandResolver.resolve(bytes);

        assertTrue(profile.getHvacStartingTimes().length == 2);
        assertTrue(profile.getHvacStartingTime(HvacStartingTime.Weekday.TUESDAY) == null);

        assertTrue(profile.getHvacStartingTime(HvacStartingTime.Weekday.MONDAY).getTime().equals
                (new Time(8, 0)));
        assertTrue(profile.getHvacStartingTime(HvacStartingTime.Weekday.WEDNESDAY).getTime().equals
                (new Time(8, 10)));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002401");
        ClimateState state = (ClimateState) CommandResolver.resolve(bytes);
        assertTrue(state.getRearTemperatureSetting() == null);
    }

    @Test public void build() {
        // TBODO: 29/10/2018
/*        byte[] expectedBytes = ByteUtils.bytesFromHex(
                "002401010004419800000200044140000003000441ac000004000441ac00000500010106000100070001000800010009000441ac00000A000FE000000000000000000000071E071E");
        ClimateState.Builder builder = new ClimateState.Builder();

        builder.setInsideTemperature(19f);

        builder.setOutsideTemperature(12f);
        builder.setDriverTemperatureSetting(21.5f);
        builder.setPassengerTemperatureSetting(21.5f);

        builder.setHvacActive(true);
        builder.setDefoggingActive(false);
        builder.setDefrostingActive(false);
        builder.setIonisingActive(false);
        builder.setDefrostingTemperature(21.5f);

        AutoHvacProperty.WeekdayState[] weekdayWeekdayStates = new AutoHvacProperty.WeekdayState[7];
        for (int i = 0; i < 7; i++) {
            AutoHvacProperty.WeekdayState weekdayState;
            if (i < 5) {
                weekdayState = new AutoHvacProperty.WeekdayState(false, i, 0, 0);
            } else {
                weekdayState = new AutoHvacProperty.WeekdayState(true, i, 7, 30);
            }

            weekdayWeekdayStates[i] = weekdayState;
        }
        AutoHvacProperty autoHvac = new AutoHvacProperty(weekdayWeekdayStates, true);
        builder.setAutoHvacState(autoHvac);

        ClimateState command = builder.build();
        byte[] bytes = command.getByteArray();
        assertTrue(Arrays.equals(bytes, expectedBytes));*/
    }
}
