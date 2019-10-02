package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.DepartureTime;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.ReductionTime;
import com.highmobility.autoapi.value.StartStop;
import com.highmobility.autoapi.value.Time;
import com.highmobility.autoapi.value.Timer;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChargingTest extends BaseTest {
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
        assertTrue(command instanceof ChargingState);
        ChargingState state = (ChargingState) command;
        testState(state);
    }

    private void testState(ChargingState state) {
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.getEstimatedRange().getValue() == 432);
        assertTrue(state.getBatteryLevel().getValue() == .5d);
        assertTrue(state.getBatteryCurrentAC().getValue() == -.6f);
        assertTrue(state.getBatteryCurrentDC().getValue() == -.6f);
        assertTrue(state.getChargerVoltageAC().getValue() == 400f);
        assertTrue(state.getChargerVoltageDC().getValue() == -.6f);
        assertTrue(state.getTimeToCompleteCharge().getValue() == 60);
        assertTrue(state.getChargeLimit().getValue() == .9d);
        assertTrue(state.getChargingRateKW().getValue() == 3.5f);
        assertTrue(state.getChargePortState().getValue() == Position.OPEN);
        assertTrue(state.getChargeMode().getValue() == ChargingState.ChargeMode.TIMER_BASED);

        assertTrue(state.getMaxChargingCurrent().getValue() == 25f);
        assertTrue(state.getPlugType().getValue() == ChargingState.PlugType.TYPE_2);
        assertTrue(state.getChargingWindowChosen().getValue() == ChargingState.ChargingWindowChosen.NOT_CHOSEN);

        assertTrue(state.getDepartureTimes().length == 2);
        int timeExists = 0;
        for (Property<DepartureTime> time : state.getDepartureTimes()) {
            if (time.getValue().getTime().getHour() == 16 && time.getValue().getTime().getMinute() == 32 && time
                    .getValue().getActiveState() == ActiveState.ACTIVE) {
                timeExists++;
            }

            if (time.getValue().getTime().getHour() == 11 && time.getValue().getTime().getMinute() == 51 && time.getValue()
                    .getActiveState() != ActiveState.ACTIVE) {
                timeExists++;
            }
        }

        assertTrue(timeExists == 2);
        timeExists = 0;
        assertTrue(state.getReductionTimes().length == 2);

        for (Property<ReductionTime> time : state.getReductionTimes()) {
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
                getTimer(state.timers, Timer.TimerType.PREFERRED_START_TIME).getDate();
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(departureDate, "2018-01-10T16:32:05"));

        Calendar preferredEndTime =
                getTimer(state.timers, Timer.TimerType.PREFERRED_END_TIME).getDate();
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(preferredEndTime, "2018-01-10T18:30:00"));

        assertTrue(state.getPluggedIn().getValue() == ChargingState.PluggedIn.PLUGGED_IN);
        assertTrue(state.getStatus().getValue() == ChargingState.Status.CHARGING);
    }

    Timer getTimer(Property<Timer>[] timers, Timer.TimerType timerType) {
        for (Property<Timer> timer : timers) {
            if (timer.getValue().getTimerType() == timerType) return timer.getValue();
        }

        return null;
    }

    @Test public void get() {
        String waitingForBytes = "002300";
        Bytes get = new GetChargingState();
        String commandBytes = ByteUtils.hexFromBytes(new GetChargingState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    /*@Test public void failSameTimers() throws ParseException {
        Calendar c = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
        Timer[] timers = new Timer[2];
        timers[0] = new Timer(Timer.TimerType.DEPARTURE_DATE, c);
        timers[1] = new Timer(Timer.TimerType.DEPARTURE_DATE, c);
        assertThrows(IllegalArgumentException.class, () -> new SetChargingTimers(timers));
        // TODO: this is not defined in yaml
    }*/

    @Test public void build() throws ParseException {
        ChargingState.Builder builder = new ChargingState.Builder();

        builder.setEstimatedRange(new Property(432));
        builder.setBatteryLevel(new Property(.5d));
        builder.setBatteryCurrentAC(new Property(-.6f));
        builder.setBatteryCurrentDC(new Property(-.6f));
        builder.setChargerVoltageAC(new Property(400f));
        builder.setChargerVoltageDC(new Property(-.6f));
        builder.setChargeLimit(new Property(.9d));
        builder.setTimeToCompleteCharge(new Property(60));
        builder.setChargingRateKW(new Property(3.5f));
        builder.setChargePortState(new Property(Position.OPEN));
        builder.setChargeMode(new Property(ChargingState.ChargeMode.TIMER_BASED));

        builder.setMaxChargingCurrent(new Property(25f));
        builder.setPlugType(new Property(ChargingState.PlugType.TYPE_2));
        builder.setChargingWindowChosen(new Property(ChargingState.ChargingWindowChosen.NOT_CHOSEN));

        builder.addDepartureTime(new Property(new DepartureTime(ActiveState.ACTIVE, new Time(16,
                32))));
        builder.addDepartureTime(new Property(new DepartureTime(ActiveState.INACTIVE, new Time(11
                , 51))));

        builder.addReductionTime(new Property(new ReductionTime(StartStop.START,
                new Time(17, 33))));
        builder.addReductionTime(new Property(new ReductionTime(StartStop.STOP, new Time(12, 52))));

        builder.setBatteryTemperature(new Property(38.4f));

        Calendar departureDate = TestUtils.getCalendar("2018-01-10T16:32:05");
        Calendar preferredEndTime = TestUtils.getCalendar("2018-01-10T18:30:00");
        Timer timer = new Timer(Timer.TimerType.PREFERRED_START_TIME,
                departureDate);
        Timer timer2 = new Timer(Timer.TimerType.PREFERRED_END_TIME,
                preferredEndTime);
        builder.addTimer(new Property(timer));
        builder.addTimer(new Property(timer2));
        builder.setPluggedIn(new Property(ChargingState.PluggedIn.PLUGGED_IN));
        builder.setStatus(new Property(ChargingState.Status.CHARGING));

        ChargingState state = builder.build();
        testState(state);
    }

    @Test public void setChargeLimit() {
        Bytes expected = new Bytes("002301" +
                "08000B0100083FECCCCCCCCCCCCD");

        Bytes commandBytes = new SetChargeLimit(.9d);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetChargeLimit command = (SetChargeLimit) CommandResolver.resolve(expected);
        assertTrue(command.getChargeLimit().getValue() == .9d);
    }

    @Test public void openCloseChargePort() {
        Bytes expected = new Bytes("0023010B000401000101");

        Bytes commandBytes = new OpenCloseChargingPort(Position.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, expected));

        setRuntime(CommandResolver.RunTime.JAVA);
        OpenCloseChargingPort command = (OpenCloseChargingPort) CommandResolver.resolve(expected);
        assertTrue(command.getChargePortState().getValue() == Position.OPEN);
    }

    @Test public void startStopCharging() {
        Bytes waitingForBytes = new Bytes("002301" +
                "17000401000101");
        Bytes commandBytes = new StartStopCharging(ChargingState.Status.CHARGING);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        StartStopCharging command = (StartStopCharging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ChargingState.Status.CHARGING);
    }

    @Test public void setChargeMode() {
        // TODO: 23/09/2019 verify INDUCTIVE > IMMEDIATE correct
        Bytes waitingForBytes = new Bytes("0023010C000401000100");
        Bytes commandBytes = new SetChargeMode(ChargingState.ChargeMode.IMMEDIATE);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetChargeMode command = (SetChargeMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getChargeMode().getValue() == ChargingState.ChargeMode.IMMEDIATE);
    }

    @Test public void setChargeModeThrowsOnImmediate() {
        assertThrows(IllegalArgumentException.class, () -> {
            // TODO: 23/09/2019  verify IMMEDIATE > INDUCTIVE  correct
            new SetChargeMode(ChargingState.ChargeMode.INDUCTIVE);
        });
    }

    @Test public void SetChargeTimer() throws ParseException {
        Bytes waitingForBytes = new Bytes
                ("002301" +
                        "15000C0100090200000160E0EA1388" +
                        "15000C0100090100000160E1560840");
        Calendar c = TestUtils.getUTCCalendar("2018-01-10T16:32:05");
        Calendar c2 = TestUtils.getUTCCalendar("2018-01-10T18:30:00");

        Timer[] timers = new Timer[2];
        timers[0] = new Timer(Timer.TimerType.DEPARTURE_DATE, c);
        timers[1] = new Timer(Timer.TimerType.PREFERRED_END_TIME, c2);

        Command commandBytes = new SetChargingTimers(timers);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetChargingTimers command = (SetChargingTimers) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getTimers().length == 2);

        Calendar departureTime = getTimer(command.timers, Timer.TimerType.DEPARTURE_DATE)
                .getDate();
        Calendar preferredEndTime = getTimer(command.timers, Timer.TimerType.PREFERRED_END_TIME)
                .getDate();

        assertTrue(TestUtils.dateIsSame(departureTime, "2018-01-10T16:32:05"));
        assertTrue(TestUtils.dateIsSame(preferredEndTime, "2018-01-10T18:30:00"));
    }

    @Test public void SetReductionTimes() {
        Bytes waitingForBytes = new Bytes("002301" +
                "130006010003000000" + // reduction times
                "130006010003011020");

        ReductionTime[] timers = new ReductionTime[2];
        timers[0] = new ReductionTime(StartStop.START, new Time(0, 0));
        timers[1] = new ReductionTime(StartStop.STOP, new Time(16, 32));

        Command commandBytes = new SetReductionOfChargingCurrentTimes(timers);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetReductionOfChargingCurrentTimes command = (SetReductionOfChargingCurrentTimes)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getReductionTimes().length == 2);

        int times = 0;
        for (int i = 0; i < command.getReductionTimes().length; i++) {
            Property<ReductionTime> reductionTime = command.getReductionTimes()[i];
            if ((reductionTime.getValue().getTime().equals(new Time(0, 0)) && reductionTime.getValue().getStartStop() == StartStop.START) ||
                    (reductionTime.getValue().getTime().equals(new Time(16, 32)) && reductionTime.getValue().getStartStop() == StartStop.STOP)) {
                times++;
            }
        }

        assertTrue(times == 2);
    }
}
