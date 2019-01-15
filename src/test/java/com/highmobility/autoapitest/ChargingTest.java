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
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ChargeModeProperty;
import com.highmobility.autoapi.property.ChargePortState;
import com.highmobility.autoapi.property.ChargingState;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.charging.ChargeMode;
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
            "00230102000200FF03000132040004bf19999a050004bf19999a06000443c8000007000443cd00000800015A0900013C0A0004000000000B0001010C000100"
                    // start of l8
                    + "0E000441c800000F00010110000100"
                    + "110003011020" // departure times
                    + "110003001220"
                    + "130003000000" // reduction times
                    + "130003011020"
                    + "1400044219999a"
                    + "1500090212010A1020050000" // timers
                    + "1500090112010A1020060000"
                    + "1600010117000101" // 16 and 17
    );

    @Test
    public void state() throws ParseException {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(ChargeState.TYPE));
        ChargeState state = (ChargeState) command;

        assertTrue(state.getEstimatedRange().getValue() == 255);
        assertTrue(state.getBatteryLevel().getValue() == .5f);
        assertTrue(state.getBatteryCurrentAC().getValue() == -.6f);
        assertTrue(state.getBatteryCurrentDC().getValue() == -.6f);
        assertTrue(state.getChargerVoltageAC().getValue() == 400f);
        assertTrue(state.getChargerVoltageDC().getValue() == 410f);
        assertTrue(state.getTimeToCompleteCharge().getValue() == 60);
        assertTrue(state.getChargeLimit().getValue() == .9f);
        assertTrue(state.getChargingRate().getValue() == 0f);
        assertTrue(state.getChargeChargePortState() == ChargePortState.OPEN);
        assertTrue(state.getChargeMode().getValue() == ChargeMode.IMMEDIATE);

        assertTrue(state.getMaxChargingCurrent().getValue() == 25);
        assertTrue(state.getPlugType() == PlugType.TYPE_2);
        assertTrue(state.getChargingWindowChosen().getValue() == false);

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

        for (ReductionTime time : state.getReductionOfChargingCurrentTimes()) {
            if (time.getTime().getHour() == 0 && time.getTime().getMinute() == 0 && time
                    .getStartStop() == StartStop.START) {
                timeExists++;
            }
            if (time.getTime().getHour() == 16 && time.getTime().getMinute() == 32 && time
                    .getStartStop() == StartStop.STOP) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);

        assertTrue(state.getBatteryTemperature().getValue() == 38.4f);

        assertTrue(state.getTimers().length == 2);
        Calendar departureDate = state.getTimer(ChargingTimer.Type.DEPARTURE_TIME).getTime();
        assertTrue(TestUtils.dateIsSameUTC(departureDate, "2018-01-10T16:32:05"));

        Calendar preferredEndTime = state.getTimer(ChargingTimer.Type.PREFERRED_END_TIME).getTime();
        assertTrue(TestUtils.dateIsSameUTC(preferredEndTime, "2018-01-10T16:32:06"));

        assertTrue(state.getPluggedIn().getValue() == true);
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
        builder.setEstimatedRange(new IntegerProperty(255));
        builder.setBatteryLevel(new PercentageProperty(.5f));
        builder.setBatteryCurrentAC(new FloatProperty(-.6f));
        builder.setBatteryCurrentDC(new FloatProperty(-.6f));
        builder.setChargerVoltageAC(new FloatProperty(400f));
        builder.setChargerVoltageDC(new FloatProperty(410f));
        builder.setChargeLimit(new PercentageProperty(.9f));
        builder.setTimeToCompleteCharge(new IntegerProperty(60));
        builder.setChargingRate(new FloatProperty(0f));
        builder.setChargePortState(ChargePortState.OPEN);
        builder.setChargeMode(new ChargeModeProperty(ChargeMode.IMMEDIATE));

        builder.setMaxChargingCurrent(new FloatProperty(25f));
        builder.setPlugType(PlugType.TYPE_2);
        builder.setChargingWindowChosen(new BooleanProperty(false));

        builder.addDepartureTime(new DepartureTime(true, new Time(16, 32)));
        builder.addDepartureTime(new DepartureTime(false, new Time(18, 32)));

        builder.addReductionOfChargingCurrentTime(new ReductionTime(StartStop.START, new Time(0,
                0)));
        builder.addReductionOfChargingCurrentTime(new ReductionTime(StartStop.STOP, new Time(16,
                32)));

        builder.setBatteryTemperature(new FloatProperty(38.4f));

        Calendar departureDate = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
        Calendar preferredEndTime = TestUtils.getUTCCalendar("2018-01-10T16:32:06");
        ChargingTimer timer = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME,
                departureDate);
        ChargingTimer timer2 = new ChargingTimer(ChargingTimer.Type.PREFERRED_END_TIME,
                preferredEndTime);
        builder.addTimer(timer);
        builder.addTimer(timer2);

        builder.setPluggedIn(new BooleanProperty(true));
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

    @Test public void openCloseChargePort() {
        Bytes expected = new Bytes("00231401000101");

        Bytes commandBytes = new OpenCloseChargePort(ChargePortState.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        OpenCloseChargePort command = (OpenCloseChargePort) CommandResolver.resolve(expected);
        assertTrue(command.getChargePortState() == ChargePortState.OPEN);
    }

    @Test public void startStopCharging() {
        Bytes waitingForBytes = new Bytes("00231201000101");
        Bytes commandBytes = new StartStopCharging(true);

        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopCharging command = (StartStopCharging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStart() == true);
    }

    @Test public void setChargeMode() {
        Bytes waitingForBytes = new Bytes("00231501000102");
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
                ("0023160D00090212010a10200500000D00090113010a1020070000");
        Calendar c = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
        Calendar c2 = TestUtils.getUTCCalendar("2019-01-10T16:32:07");

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

        assertTrue(TestUtils.dateIsSameUTC(departureTime, "2018-01-10T16:32:05"));
        assertTrue(TestUtils.dateIsSameUTC(preferredEndTime, "2019-01-10T16:32:07"));
    }

    @Test public void SetReductionTimes() {
        Bytes waitingForBytes = new Bytes("002317" +
                "010003000000" + // reduction times
                "010003011020");

        ReductionTime[] timers = new ReductionTime[2];
        timers[0] = new ReductionTime(StartStop.START, new Time(0, 0));
        timers[1] = new ReductionTime(StartStop.STOP, new Time(16, 32));

        Command commandBytes = new SetReductionOfChargingCurrentTimes(timers);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        SetReductionOfChargingCurrentTimes command = (SetReductionOfChargingCurrentTimes)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getReductionTimes().length == 2);

        int times = 0;
        for (int i = 0; i < command.getReductionTimes().length; i++) {
            ReductionTime reductionTime = command.getReductionTimes()[i];
            if ((reductionTime.getTime().equals(new Time(0, 0)) && reductionTime.getStartStop() == StartStop.START) ||
                    (reductionTime.getTime().equals(new Time(16, 32)) && reductionTime.getStartStop() == StartStop.STOP)) {
                times++;
            }
        }
        
        assertTrue(times == 2);
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
