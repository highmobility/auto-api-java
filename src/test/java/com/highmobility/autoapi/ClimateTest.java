package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import com.highmobility.autoapi.value.Time;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClimateTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "002401" +
                    "01000701000441980000" +
                    "02000701000441400000" +
                    "03000701000441AC0000" +
                    "04000701000441AC0000" +
                    "05000401000101" +
                    "06000401000100" +
                    "07000401000100" +
                    "08000401000100" +
                    "09000701000441AC0000" +
                    "0B000601000305121E" +
                    "0B000601000306121E" +
                    "0C000701000441AC0000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Climate.State) command);
    }

    private void testState(Climate.State state) {
        assertTrue(state.getInsideTemperature().getValue() == 19f);
        assertTrue(state.getOutsideTemperature().getValue() == 12f);
        assertTrue(state.getDriverTemperatureSetting().getValue() == 21.5f);
        assertTrue(state.getPassengerTemperatureSetting().getValue() == 21.5f);

        assertTrue(state.getHvacState().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getDefoggingState().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getDefrostingState().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getIonisingState().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getDefrostingTemperatureSetting().getValue() == 21.5f);

        assertTrue(getHvacWeekdayStartingTime(state.getHvacWeekdayStartingTimes(),
                HvacWeekdayStartingTime.Weekday.MONDAY) == null);

        HvacWeekdayStartingTime time1 =
                getHvacWeekdayStartingTime(state.getHvacWeekdayStartingTimes(),
                        HvacWeekdayStartingTime.Weekday.SATURDAY);
        HvacWeekdayStartingTime time2 =
                getHvacWeekdayStartingTime(state.getHvacWeekdayStartingTimes(),
                        HvacWeekdayStartingTime.Weekday.SUNDAY);

        assertTrue(time1.getTime().getHour() == 18);
        assertTrue(time1.getTime().getMinute() == 30);
        assertTrue(time2.getTime().getHour() == 18);
        assertTrue(time2.getTime().getMinute() == 30);

        assertTrue(state.getRearTemperatureSetting().getValue() == 21.5f);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    HvacWeekdayStartingTime getHvacWeekdayStartingTime(Property<HvacWeekdayStartingTime>[] times,
                                                       HvacWeekdayStartingTime.Weekday weekday) {
        for (Property<HvacWeekdayStartingTime> time : times) {
            if (time.getValue().getWeekday() == weekday) {
                return time.getValue();
            }
        }

        return null;
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "002400";
        String commandBytes = ByteUtils.hexFromBytes(new Climate.GetState().getByteArray());

        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopDefogging() {
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

    @Test public void startStopDefrosting() {
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

    @Test public void startStopHvac() {
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

    @Test public void StartStopIonising() {
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

    @Test public void setTemperatureSettings() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "002401" +
                "03000701000441a40000" +
                "04000701000441a40000" +
                "0C000701000441980000");

        Climate.SetTemperatureSettings cmd = new Climate.SetTemperatureSettings(20.5f, 20.5f, 19f);
        assertTrue(TestUtils.bytesTheSame(cmd, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.SetTemperatureSettings settings =
                (Climate.SetTemperatureSettings) CommandResolver.resolve(bytes);
        assertTrue(settings.getDriverTemperatureSetting().getValue() == 20.5f);
        assertTrue(settings.getPassengerTemperatureSetting().getValue() == 20.5f);
        assertTrue(settings.getRearTemperatureSetting().getValue() == 19f);
    }

    @Test public void setClimateProfile() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "002401" +
                "0b0006010003000800" +
                "0b000601000302080A");

        HvacWeekdayStartingTime[] times = new HvacWeekdayStartingTime[2];
        times[0] = new HvacWeekdayStartingTime(HvacWeekdayStartingTime.Weekday.MONDAY, new Time(8
                , 0));
        times[1] = new HvacWeekdayStartingTime(HvacWeekdayStartingTime.Weekday.WEDNESDAY,
                new Time(8, 10));
        Bytes commandBytes = new Climate.ChangeStartingTimes(times);
        assertTrue(TestUtils.bytesTheSame(commandBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Climate.ChangeStartingTimes profile =
                (Climate.ChangeStartingTimes) CommandResolver.resolve(bytes);
        assertTrue(profile.getHvacWeekdayStartingTimes().length == 2);
        assertTrue(getHvacWeekdayStartingTime(profile.getHvacWeekdayStartingTimes(),
                HvacWeekdayStartingTime.Weekday.TUESDAY) == null);
        assertTrue(getHvacWeekdayStartingTime(profile.getHvacWeekdayStartingTimes(),
                HvacWeekdayStartingTime.Weekday.MONDAY).getTime().equals
                (new Time(8, 0)));
        assertTrue(getHvacWeekdayStartingTime(profile.getHvacWeekdayStartingTimes(),
                HvacWeekdayStartingTime.Weekday.WEDNESDAY).getTime().equals
                (new Time(8, 10)));
    }

    @Test public void setClimateProfile0Properties() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "002401");
        HvacWeekdayStartingTime[] times = new HvacWeekdayStartingTime[0];
        Bytes commandBytes = new Climate.ChangeStartingTimes(times);
        assertTrue(TestUtils.bytesTheSame(commandBytes, bytes));
    }

    @Test public void build() {
        Climate.State.Builder builder = new Climate.State.Builder();

        builder.setInsideTemperature(new Property(19f));

        builder.setOutsideTemperature(new Property(12f));
        builder.setDriverTemperatureSetting(new Property(21.5f));
        builder.setPassengerTemperatureSetting(new Property(21.5f));

        builder.setHvacState(new Property(ActiveState.ACTIVE));
        builder.setDefoggingState(new Property(ActiveState.INACTIVE));
        builder.setDefrostingState(new Property(ActiveState.INACTIVE));
        builder.setIonisingState(new Property(ActiveState.INACTIVE));
        builder.setDefrostingTemperatureSetting(new Property(21.5f));

        builder.addHvacWeekdayStartingTime(new Property(new HvacWeekdayStartingTime(HvacWeekdayStartingTime.Weekday.SATURDAY,
                new Time(18, 30))));
        builder.addHvacWeekdayStartingTime(new Property(new HvacWeekdayStartingTime(HvacWeekdayStartingTime.Weekday.SUNDAY, new Time(18
                , 30))));

        builder.setRearTemperatureSetting(new Property(21.5f));
        Climate.State command = builder.build();
        testState(command);
    }
}
