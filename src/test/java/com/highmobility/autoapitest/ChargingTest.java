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
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.charging.ChargePortState;
import com.highmobility.autoapi.property.charging.ChargingState;
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
        testState(state);
    }

    private void testState(ChargeState state) throws ParseException {
        assertTrue(state.getEstimatedRange().getValue() == 432);
        assertTrue(state.getBatteryLevel().getValue() == .5d);
        assertTrue(state.getBatteryCurrentAC().getValue() == -.6f);
        assertTrue(state.getBatteryCurrentDC().getValue() == -.6f);
        assertTrue(state.getChargerVoltageAC().getValue() == 400f);
        assertTrue(state.getChargerVoltageDC().getValue() == -.6f);
        assertTrue(state.getTimeToCompleteCharge().getValue() == 60);
        assertTrue(state.getChargeLimit().getValue() == .9d);
        assertTrue(state.getChargingRate().getValue() == 3.5f);
        assertTrue(state.getChargePortState().getValue() == ChargePortState.OPEN);
        assertTrue(state.getChargeMode().getValue() == ChargeMode.TIMER_BASED);

        assertTrue(state.getMaxChargingCurrent().getValue() == 25f);
        assertTrue(state.getPlugType().getValue() == PlugType.TYPE_2);
        assertTrue(state.getChargingWindowChosen().getValue() == false);

        assertTrue(state.getDepartureTimes().length == 2);
        int timeExists = 0;
        for (ObjectProperty<DepartureTime> time : state.getDepartureTimes()) {
            if (time.getValue().getTime().getHour() == 16 && time.getValue().getTime().getMinute() == 32 && time
                    .getValue().isActive()) {
                timeExists++;
            }

            if (time.getValue().getTime().getHour() == 11 && time.getValue().getTime().getMinute() == 51 && !time.getValue()
                    .isActive()) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);
        timeExists = 0;
        assertTrue(state.getReductionOfChargingCurrentTimes().length == 2);

        for (ObjectProperty<ReductionTime> time : state.getReductionOfChargingCurrentTimes()) {
            if (time.getValue().getTime().getHour() == 17 && time.getValue().getTime().getMinute() == 33 && time
                    .getValue().getStartStop() == StartStop.START) {
                timeExists++;
            }
            if (time.getValue().getTime().getHour() == 12 && time.getValue().getTime().getMinute() == 52 && time
                    .getValue().getStartStop() == StartStop.STOP) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);

        assertTrue(state.getBatteryTemperature().getValue() == 38.4f);

        assertTrue(state.getTimers().length == 2);

        Calendar departureDate =
                state.getTimer(ChargingTimer.Type.PREFERRED_START_TIME).getValue().getTime();
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(departureDate, "2018-01-10T16:32:05"));

        Calendar preferredEndTime =
                state.getTimer(ChargingTimer.Type.PREFERRED_END_TIME).getValue().getTime();
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(preferredEndTime, "2018-01-10T18:30:00"));

        assertTrue(state.getPluggedIn().getValue() == true);
        assertTrue(state.getActiveState().getValue() == ChargingState.CHARGING);
    }

    @Test public void get() {
        String waitingForBytes = "002300";
        String commandBytes = ByteUtils.hexFromBytes(new GetChargeState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failSameChargingTimers() throws ParseException {
        Calendar c = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
        ChargingTimer[] timers = new ChargingTimer[2];
        timers[0] = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME, c);
        timers[1] = new ChargingTimer(ChargingTimer.Type.DEPARTURE_TIME, c);
        new SetChargeTimer(timers);
    }

    @Test public void build() throws ParseException {
        ChargeState.Builder builder = new ChargeState.Builder();

        builder.setEstimatedRange(new ObjectPropertyInteger(432));
        builder.setBatteryLevel(new ObjectProperty(.5d));
        builder.setBatteryCurrentAC(new ObjectProperty(-.6f));
        builder.setBatteryCurrentDC(new ObjectProperty(-.6f));
        builder.setChargerVoltageAC(new ObjectProperty(400f));
        builder.setChargerVoltageDC(new ObjectProperty(-.6f));
        builder.setChargeLimit(new ObjectProperty(.9d));
        builder.setTimeToCompleteCharge(new ObjectPropertyInteger(60));
        builder.setChargingRate(new ObjectProperty(3.5f));
        builder.setChargePortState(new ObjectProperty(ChargePortState.OPEN));
        builder.setChargeMode(new ObjectProperty(ChargeMode.TIMER_BASED));

        builder.setMaxChargingCurrent(new ObjectProperty(25f));
        builder.setPlugType(new ObjectProperty(PlugType.TYPE_2));
        builder.setChargingWindowChosen(new ObjectProperty(false));

        builder.addDepartureTime(new ObjectProperty(new DepartureTime(true, new Time(16, 32))));
        builder.addDepartureTime(new ObjectProperty(new DepartureTime(false, new Time(11, 51))));

        builder.addReductionOfChargingCurrentTime(new ObjectProperty(new ReductionTime(StartStop.START, new Time(17,
                33))));
        builder.addReductionOfChargingCurrentTime(new ObjectProperty(new ReductionTime(StartStop.STOP, new Time(12,
                52))));

        builder.setBatteryTemperature(new ObjectProperty(38.4f));

        Calendar departureDate = TestUtils.getCalendar("2018-01-10T16:32:05");
        Calendar preferredEndTime = TestUtils.getCalendar("2018-01-10T18:30:00");
        ChargingTimer timer = new ChargingTimer(ChargingTimer.Type.PREFERRED_START_TIME,
                departureDate);
        ChargingTimer timer2 = new ChargingTimer(ChargingTimer.Type.PREFERRED_END_TIME,
                preferredEndTime);
        builder.addTimer(new ObjectProperty(timer));
        builder.addTimer(new ObjectProperty(timer2));
        builder.setPluggedIn(new ObjectProperty(true));
        builder.setActiveState(new ObjectProperty(ChargingState.CHARGING));

        ChargeState state = builder.build();
        testState(state);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("002301");
        ChargeState state = (ChargeState) CommandResolver.resolve(bytes);
        assertTrue(state.getBatteryCurrentAC().getValue() == null);
    }

    @Test public void setChargeLimit() {
        Bytes expected = new Bytes("002313" +
                "01000B0100083FECCCCCCCCCCCCD");

        Bytes commandBytes = new SetChargeLimit(.9d);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        SetChargeLimit command = (SetChargeLimit) CommandResolver.resolve(expected);
        assertTrue(command.getChargeLimit().getValue() == .9d);
    }

    @Test public void openCloseChargePort() {
        Bytes expected = new Bytes("00231401000401000101");

        Bytes commandBytes = new OpenCloseChargePort(ChargePortState.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        OpenCloseChargePort command = (OpenCloseChargePort) CommandResolver.resolve(expected);
        assertTrue(command.getChargePortState().getValue() == ChargePortState.OPEN);
    }

    @Test public void startStopCharging() {
        Bytes waitingForBytes = new Bytes("002312" +
                "01000401000101");
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
        assertTrue(command.getChargeMode().getValue() == ChargeMode.INDUCTIVE);
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
                .getValue().getTime();
        Calendar preferredEndTime =
                command.getChargingTimer(ChargingTimer.Type.PREFERRED_END_TIME)
                        .getValue().getTime();

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

        int times = 0;
        for (int i = 0; i < command.getReductionTimes().length; i++) {
            ObjectProperty<ReductionTime> reductionTime = command.getReductionTimes()[i];
            if ((reductionTime.getValue().getTime().equals(new Time(0, 0)) && reductionTime.getValue().getStartStop() == StartStop.START) ||
                    (reductionTime.getValue().getTime().equals(new Time(16, 32)) && reductionTime.getValue().getStartStop() == StartStop.STOP)) {
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
