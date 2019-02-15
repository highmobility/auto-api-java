package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetHistoricalStates;
import com.highmobility.autoapi.HistoricalStates;
import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.doors.DoorLocation;
import com.highmobility.autoapi.property.doors.DoorLockState;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class HistoricalTest {
    Bytes bytes = new Bytes(
            "001201" +
                    "010013" + // 0x13 = 19
                    "0020010300020000" +
                    "A2000812010a1020050000" +
                    "010013" + // 0x13 = 19
                    "0020010300020101" +
                    "A2000812010a1020060000"
    );

    @Test
    public void state() throws ParseException {
        Command command = CommandResolver.resolve(bytes);
        HistoricalStates states = (HistoricalStates) command;

        assertTrue(states.getStates().length == 2);

        int found = 0;
        for (int i = 0; i < states.getStates().length; i++) {

            LockState state = (LockState) states.getStates()[i].getValue();
            DoorLockState doorLockState = state.getLocks()[0];

            if (doorLockState.getLocation() == DoorLocation.FRONT_LEFT) {
                if (doorLockState.getLock() == Lock.UNLOCKED &&
                        TestUtils.dateIsSame(state.getTimestamp(), "2018-01-10T16:32:05+0000")) {
                    found++;
                }
            } else if (doorLockState.getLocation() == DoorLocation.FRONT_RIGHT) {

                if (doorLockState.getLock() == Lock.LOCKED &&
                        TestUtils.dateIsSame(state.getTimestamp(), "2018-01-10T16:32:06+0000")) {
                    found++;
                }
            }
        }

        assertTrue(found == 2);
    }

    @Test public void stateWithTimestamp() {
//        0020010300020000A2000812010a1020050000
        String prop = "010013" + "0020010300020000" + "A2000812010a1020050000";
        Bytes timestampBytes = bytes.concat(new Bytes("A4001F11010A112200000001" + prop));
        HistoricalStates command = (HistoricalStates) CommandResolver.resolve(timestampBytes);
        int hasTimestamp = 0;
        for (int i = 0; i < command.getStates().length; i++) {
            if (command.getStates()[i].getTimestamp() != null) hasTimestamp++;
        }

        assertTrue(hasTimestamp == 1);
    }

    @Test public void build() {
        // TBODO:
    }

    @Test public void get() throws ParseException {
        Bytes waitingForBytes = new Bytes("001200" +
                "0100020020" +
                "02000812010a1020050000" +
                "03000812010a1123000000"
        );

        Command command = new GetHistoricalStates(
                Identifier.DOOR_LOCKS,
                TestUtils.getCalendar("2018-01-10T16:32:05+0000"),
                TestUtils.getCalendar("2018-01-10T17:35:00+0000")
        );

        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005701");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((RaceState) state).getGearMode() == null);
    }
}