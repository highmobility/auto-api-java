package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChargeState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChargeState;
import com.highmobility.autoapi.OpenCloseChargePort;
import com.highmobility.autoapi.SetChargeLimit;
import com.highmobility.autoapi.SetChargeMode;
import com.highmobility.autoapi.SetChargeTimer;
import com.highmobility.autoapi.SetReductionOfChargingCurrentTimes;
import com.highmobility.autoapi.StartStopCharging;
import com.highmobility.autoapi.property.ChargeMode;
import com.highmobility.autoapi.property.ChargePortState;
import com.highmobility.autoapi.property.ChargingState;
import com.highmobility.autoapi.property.charging.ChargingTimer;
import com.highmobility.autoapi.property.charging.DepartureTime;
import com.highmobility.autoapi.property.charging.PlugType;
import com.highmobility.autoapi.property.charging.ReductionTime;
import com.highmobility.autoapi.property.value.StartStop;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;

public class ChargingTest {
    Bytes bytes = new Bytes(
            "002301" +
                    "02000501000201B0" +
                    "03000B0100083FE0000000000000" +
                    "040007010004BF19999A" +
                    "050007010004BF19999A" +
                    "06000701000443C80000" +
                    "070007010004BF19999A" +
                    "08000B0100083FECCCCCCCCCCCCD" +
                    "0900040100013C" +
                    "0A000701000440600000" +
                    "0B000401000101" +
                    "0C000401000101" +
                    "0E000701000441C80000" +
                    "0F000401000101" +
                    "10000401000100" +
                    "110006010003011020" +
                    "110006010003000B33" +
                    "130006010003001121" +
                    "130006010003010C34" +
                    "1400070100044219999A" +
                    "15000C0100090000000160E0EA1388" +
                    "15000C0100090100000160E1560840" +
                    "16000401000101" +
                    "17000401000101"

    );

    @Test
    public void state() throws ParseException {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(ChargeState.TYPE));
        ChargeState state = (ChargeState) command;

        assertTrue(state.getEstimatedRange() == 432);
        assertTrue(state.getBatteryLevel() == .5d);
        assertTrue(state.getBatteryCurrentAC() == -.6f);
        assertTrue(state.getBatteryCurrentDC() == -.6f);
        assertTrue(state.getChargerVoltageAC() == 400f);
        assertTrue(state.getChargerVoltageDC() == -.6f);
        assertTrue(state.getTimeToCompleteCharge() == 60);
        assertTrue(state.getChargeLimit() == .9d);
        assertTrue(state.getChargingRate() == 3.5f);
        assertTrue(state.getChargeChargePortState() == ChargePortState.OPEN);
        assertTrue(state.getChargeMode() == ChargeMode.TIMER_BASED);

        assertTrue(state.getMaxChargingCurrent() == 25f);
        assertTrue(state.getPlugType() == PlugType.TYPE_2);
        assertTrue(state.getChargingWindowChosen() == false);

        assertTrue(state.getDepartureTimes().length == 2);
        int timeExists = 0;
        for (DepartureTime time : state.getDepartureTimes()) {
            if (time.getTime().getHour() == 16 && time.getTime().getMinute() == 32 && time
                    .isActive()) {
                timeExists++;
            }

            if (time.getTime().getHour() == 11 && time.getTime().getMinute() == 51 && !time
                    .isActive()) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);
        timeExists = 0;
        assertTrue(state.getReductionOfChargingCurrentTimes().length == 2);

        for (ReductionTime time : state.getReductionOfChargingCurrentTimes()) {
            if (time.getTime().getHour() == 17 && time.getTime().getMinute() == 33 && time
                    .getStartStop() == StartStop.START) {
                timeExists++;
            }
            if (time.getTime().getHour() == 12 && time.getTime().getMinute() == 52 && time
                    .getStartStop() == StartStop.STOP) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);

        assertTrue(state.getBatteryTemperature() == 38.4f);

        assertTrue(state.getTimers().length == 2);
        Calendar departureDate = state.getTimer(ChargingTimer.Type.PREFERRED_START_TIME).getTime();
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(departureDate, "2018-01-10T16:32:05"));

        Calendar preferredEndTime = state.getTimer(ChargingTimer.Type.PREFERRED_END_TIME).getTime();
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(preferredEndTime, "2018-01-10T18:30:00"));

        assertTrue(state.getPluggedIn() == true);
        assertTrue(state.getActiveState() == ChargingState.CHARGING);
    }

    @Test public void get() {
        String waitingForBytes = "002300";
        String commandBytes = ByteUtils.hexFromBytes(new GetChargeState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test(expected = IllegalArgumentException.class) public void failSameChargingTimers() {
        try {
            Calendar c = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
            ChargingTimer[] timers = new ChargingTimer[2];
            timers[0] = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME, c);
            timers[1] = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME, c);
            new SetChargeTimer(timers);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test public void build() throws ParseException {
        ChargeState.Builder builder = new ChargeState.Builder();
        builder.setEstimatedRange(432);
        builder.setBatteryLevel(.5d);
        builder.setBatteryCurrentAC(-.6f);
        builder.setBatteryCurrentDC(-.6f);
        builder.setChargerVoltageAC(400f);
        builder.setChargerVoltageDC(-.6f);
        builder.setChargeLimit(.9d);
        builder.setTimeToCompleteCharge(60);
        builder.setChargingRate(3.5f);
        builder.setChargePortState(ChargePortState.OPEN);
        builder.setChargeMode(ChargeMode.TIMER_BASED);

        builder.setMaxChargingCurrent(25f);
        builder.setPlugType(PlugType.TYPE_2);
        builder.setChargingWindowChosen(false);

        builder.addDepartureTime(new DepartureTime(true, new Time(16, 32)));
        builder.addDepartureTime(new DepartureTime(false, new Time(11, 51)));

        builder.addReductionOfChargingCurrentTime(new ReductionTime(StartStop.START, new Time(17,
                33)));
        builder.addReductionOfChargingCurrentTime(new ReductionTime(StartStop.STOP, new Time(12,
                52)));

        builder.setBatteryTemperature(38.4f);

        Calendar departureDate = TestUtils.getCalendar("2018-01-10T16:32:05");
        Calendar preferredEndTime = TestUtils.getCalendar("2018-01-10T18:30:00");
        ChargingTimer timer = new ChargingTimer(ChargingTimer.Type.PREFERRED_START_TIME,
                departureDate);
        ChargingTimer timer2 = new ChargingTimer(ChargingTimer.Type.PREFERRED_END_TIME,
                preferredEndTime);
        builder.addTimer(timer);
        builder.addTimer(timer2);
        builder.setPluggedIn(true);
        builder.setActiveState(ChargingState.CHARGING);

        ChargeState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("002301");
        ChargeState state = (ChargeState) CommandResolver.resolve(bytes);
        assertTrue(state.getBatteryCurrentAC() == null);
    }

    @Test public void setChargeLimit() {
        Bytes expected = new Bytes("0023130100040100015A");

        Bytes commandBytes = new SetChargeLimit(.9f);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        SetChargeLimit command = (SetChargeLimit) CommandResolver.resolve(expected);
        assertTrue(command.getChargeLimit() == .9f);
    }

    @Test public void openCloseChargePort() {
        Bytes expected = new Bytes("00231401000401000101");

        Bytes commandBytes = new OpenCloseChargePort(ChargePortState.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        OpenCloseChargePort command = (OpenCloseChargePort) CommandResolver.resolve(expected);
        assertTrue(command.getChargePortState() == ChargePortState.OPEN);
    }

    @Test public void startStopCharging() {
        Bytes waitingForBytes = new Bytes("00231201000401000101");
        Bytes commandBytes = new StartStopCharging(true);

        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopCharging command = (StartStopCharging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStart() == true);
    }

    @Test public void setChargeMode() {
        Bytes waitingForBytes = new Bytes("00231501000401000102");
        Bytes commandBytes = new SetChargeMode(ChargeMode.INDUCTIVE);

        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));
        SetChargeMode command = (SetChargeMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getChargeMode() == ChargeMode.INDUCTIVE);
    }

    @Test(expected = IllegalArgumentException.class) public void setChargeModeThrowsOnImmediate() {
        new SetChargeMode(ChargeMode.IMMEDIATE);
    }

    @Test public void SetChargeTimer() throws ParseException {
        Bytes waitingForBytes = new Bytes
                ("002316" +
                        "0D000C0100090200000160E0EA1388" +
                        "0D000C0100090100000160E1560840");
        Calendar c = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
        Calendar c2 = TestUtils.getUTCCalendar("2018-01-10T18:30:00");

        ChargingTimer[] timers = new ChargingTimer[2];
        timers[0] = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME, c);
        timers[1] = new ChargingTimer(ChargingTimer.Type.PREFERRED_END_TIME, c2);

        Command commandBytes = new SetChargeTimer(timers);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        SetChargeTimer command = (SetChargeTimer) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getChargingTimers().length == 2);

        Calendar departureTime = command.getChargingTimer(ChargingTimer.Type.DEPARTURE_TIME)
                .getTime();
        Calendar preferredEndTime = command.getChargingTimer(ChargingTimer.Type.PREFERRED_END_TIME)
                .getTime();

        assertTrue(TestUtils.dateIsSame(departureTime, "2018-01-10T16:32:05"));
        assertTrue(TestUtils.dateIsSame(preferredEndTime, "2018-01-10T18:30:00"));
    }

    @Test public void SetReductionTimes() {
        Bytes waitingForBytes = new Bytes("002317" +
                "010006010003000000" + // reduction times
                "010006010003011020");

        ReductionTime[] timers = new ReductionTime[2];
        timers[0] = new ReductionTime(StartStop.START, new Time(0, 0));
        timers[1] = new ReductionTime(StartStop.STOP, new Time(16, 32));

        Command commandBytes = new SetReductionOfChargingCurrentTimes(timers);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        SetReductionOfChargingCurrentTimes command = (SetReductionOfChargingCurrentTimes)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getReductionTimes().length == 2);

        assertTrue(command.getReductionTimes()[0].getStartStop() == StartStop.START);
        assertTrue(command.getReductionTimes()[0].getTime().equals(new Time(0, 0)));

        assertTrue(command.getReductionTimes()[1].getStartStop() == StartStop.STOP);
        assertTrue(command.getReductionTimes()[1].getTime().equals(new Time(16, 32)));
    }

    @Test public void SetReductionTimes0Properties() {
        Bytes waitingForBytes = new Bytes("002317");

        ReductionTime[] timers = new ReductionTime[0];
        SetReductionOfChargingCurrentTimes commandBytes =
                new SetReductionOfChargingCurrentTimes(timers);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));
        SetReductionOfChargingCurrentTimes command = (SetReductionOfChargingCurrentTimes)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getProperties().length == 0);

    }
}
