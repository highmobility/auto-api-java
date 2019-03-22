package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetHistoricalStates;
import com.highmobility.autoapi.HistoricalStates;
import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.doors.DoorLockState;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class HistoricalTest {
    Bytes bytes = new Bytes(
            "001201" +
                    "01001C010019" + // >> length 19 + 3 + 3
                    "0020010300050100020000" + // 1 outside lock front left unlocked
                    "A2000B01000800000160E1560840"
    );

    @Test
    public void state() {
        HistoricalStates states = (HistoricalStates) CommandResolver.resolve(bytes);
        testState(states);
    }

    private void testState(HistoricalStates state) {
        assertTrue(state.getStates().length == 1);
        LockState lockState = (LockState) state.getStates()[0].getValue();

        assertTrue(lockState.getOutsideLock(Location.FRONT_LEFT).getValue().getLock() == Lock.UNLOCKED);
        assertTrue(TestUtils.dateIsSame(lockState.getTimestamp(), "2018-01-10T18:30:00"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        DoorLockState outsideLock = new DoorLockState(Location.FRONT_LEFT, Lock.UNLOCKED);
        Calendar timestamp = TestUtils.getCalendar("2018-01-10T18:30:00");
        Property outsideLockProperty = new Property(outsideLock);
        LockState.Builder lockStateBuilder = new LockState.Builder();
        lockStateBuilder.addOutsideLock(outsideLockProperty);
        lockStateBuilder.setTimestamp(timestamp);
        LockState lockState = lockStateBuilder.build();

        HistoricalStates.Builder historicalStatesBuilder = new HistoricalStates.Builder();
        historicalStatesBuilder.addState(new Property(lockState));

        HistoricalStates historicalStates = historicalStatesBuilder.build();
        testState(historicalStates);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("001200" +
                "0100050100020020" +
                "02000B01000800000160E0EA1388" +
                "03000B01000800000160E1560840"
        );

        Command command = new GetHistoricalStates(
                Identifier.DOOR_LOCKS,
                TestUtils.getCalendar("2018-01-10T16:32:05"),
                TestUtils.getCalendar("2018-01-10T18:30:00")
        );

        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005701");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((RaceState) state).getGearMode().getValue() == null);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(GetHistoricalStates.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}