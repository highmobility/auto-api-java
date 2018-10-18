package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChargeState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChargeState;
import com.highmobility.autoapi.SetChargeLimit;
import com.highmobility.autoapi.SetChargeMode;
import com.highmobility.autoapi.SetChargeTimer;
import com.highmobility.autoapi.StartStopCharging;
import com.highmobility.autoapi.property.ChargeMode;
import com.highmobility.autoapi.property.ChargingState;
import com.highmobility.autoapi.property.PortState;
import com.highmobility.autoapi.property.charging.ChargingTimer;
import com.highmobility.autoapi.property.charging.DepartureTime;
import com.highmobility.autoapi.property.charging.PlugType;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChargingTest {
    Bytes bytes = new Bytes(
            "00230102000200FF03000132040004bf19999a050004bf19999a06000443c8000007000443cd00000800015A0900013C0A0004000000000B0001010C000100"
                    // start of l8
                    + "0E000441c800000F00010110000100"
                    + "110003011020" // departure times
                    + "110003001220"
                    + "1300020000" // reduction times
                    + "1300021010"
                    + "1400044219999a"
                    + "1500090212010A1020050000" // timers
                    + "1500090112010A1020060000"
                    + "1600010117000101" // 16 and 17
    );

    @Test
    public void state() throws ParseException {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(ChargeState.TYPE));
        ChargeState state = (ChargeState) command;

        assertTrue(state.getEstimatedRange() == 255);
        assertTrue(state.getBatteryLevel() == .5f);
        assertTrue(state.getBatteryCurrentAC() == -.6f);
        assertTrue(state.getBatteryCurrentDC() == -.6f);
        assertTrue(state.getChargerVoltageAC() == 400f);
        assertTrue(state.getChargerVoltageDC() == 410f);
        assertTrue(state.getTimeToCompleteCharge() == 60);
        assertTrue(state.getChargeLimit() == .9f);
        assertTrue(state.getChargingRate() == 0f);
        assertTrue(state.getChargePortState() == PortState.OPEN);
        assertTrue(state.getChargeMode() == ChargeMode.IMMEDIATE);

        assertTrue(state.getMaxChargingCurrent() == 25);
        assertTrue(state.getPlugType() == PlugType.TYPE_2);
        assertTrue(state.getChargingWindowChosen() == false);

        assertTrue(state.getDepartureTimes().length == 2);
        int timeExists = 0;
        for (DepartureTime time : state.getDepartureTimes()) {
            if (time.getTime().getHour() == 16 && time.getTime().getMinute() == 32 && time
                    .isActive()) {
                timeExists++;
            }

            if (time.getTime().getHour() == 18 && time.getTime().getMinute() == 32 && !time
                    .isActive()) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);
        timeExists = 0;
        assertTrue(state.getReductionOfChargingCurrentTimes().length == 2);

        for (Time time : state.getReductionOfChargingCurrentTimes()) {
            if (time.getHour() == 0 && time.getMinute() == 0) {
                timeExists++;
            }
            if (time.getHour() == 16 && time.getMinute() == 16) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);

        assertTrue(state.getBatteryTemperature() == 38.4f);

        assertTrue(state.getTimers().length == 2);
        Calendar departureDate = state.getTimer(ChargingTimer.Type.DEPARTURE_TIME).getTime();
        assertTrue(TestUtils.dateIsSame(departureDate, "2018-01-10T16:32:05"));

        Calendar preferredEndTime = state.getTimer(ChargingTimer.Type.PREFERRED_END_TIME).getTime();
        assertTrue(TestUtils.dateIsSame(preferredEndTime, "2018-01-10T16:32:06"));

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
            Calendar c = TestUtils.getCalendar("2018-01-10T16:32:05");
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
        builder.setEstimatedRange(255);
        builder.setBatteryLevel(.5f);
        builder.setBatteryCurrentAC(-.6f);
        builder.setBatteryCurrentDC(-.6f);
        builder.setChargerVoltageAC(400f);
        builder.setChargerVoltageDC(410f);
        builder.setChargeLimit(.9f);
        builder.setTimeToCompleteCharge(60);
        builder.setChargingRate(0f);
        builder.setChargePortState(PortState.OPEN);
        builder.setChargeMode(ChargeMode.IMMEDIATE);

        builder.setMaxChargingCurrent(25f);
        builder.setPlugType(PlugType.TYPE_2);
        builder.setChargingWindowChosen(false);

        builder.addDepartureTime(new DepartureTime(true, new Time(16, 32)));
        builder.addDepartureTime(new DepartureTime(false, new Time(18, 32)));

        builder.addReductionOfChargingCurrentTime(new Time(0, 0));
        builder.addReductionOfChargingCurrentTime(new Time(16, 16));

        builder.setBatteryTemperature(38.4f);

        Calendar departureDate = TestUtils.getCalendar("2018-01-10T16:32:05");
        Calendar preferredEndTime = TestUtils.getCalendar("2018-01-10T16:32:06");
        ChargingTimer timer = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME,
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
        Bytes expected = new Bytes("0023130100015A");

        Bytes commandBytes = new SetChargeLimit(.9f);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        SetChargeLimit command = (SetChargeLimit) CommandResolver.resolve(expected);
        assertTrue(command.getChargeLimit() == .9f);
    }

    @Test public void startStopCharging() {
        Bytes waitingForBytes = new Bytes("00231201000101");
        Bytes commandBytes = new StartStopCharging(true);

        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopCharging command = (StartStopCharging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStart() == true);
    }

    @Test public void setChargeMode() {
        Bytes waitingForBytes = new Bytes("00231501000102"); // TODO: 18/10/2018 fix in docs (15
        // vs 5 in example)
        Bytes commandBytes = new SetChargeMode(ChargeMode.INDUCTIVE);

        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));
        SetChargeMode command = (SetChargeMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getChargeMode() == ChargeMode.INDUCTIVE);
    }

    @Test(expected = IllegalArgumentException.class) public void setChargeModeThrowsOnImmediate() {
        // TODO: 18/10/2018
        new SetChargeMode(ChargeMode.IMMEDIATE);
    }

    @Test public void SetChargeTimer() throws ParseException {
        Bytes waitingForBytes = new Bytes
                ("0023160D00090212010a10200500000D00090113010a1020070000"); // TODO: 18/10/2018
        // update doc, has 06 instead of 16 as identifier

        Calendar c = TestUtils.getCalendar("2018-01-10T16:32:05");
        Calendar c2 = TestUtils.getCalendar("2019-01-10T16:32:07");

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
        assertTrue(TestUtils.dateIsSame(preferredEndTime, "2019-01-10T16:32:07"));
    }
}
