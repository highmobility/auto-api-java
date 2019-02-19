package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetHistoricalStates;
import com.highmobility.autoapi.HistoricalStates;
import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.value.Location;
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
                    "01001C010019" + // >> length 19 + 3 + 3
                    "0020010300050100020000" +
                    "A2000B01000812010a1020050000"
    );

    @Test
    public void state() throws ParseException {
        HistoricalStates states = (HistoricalStates) CommandResolver.resolve(bytes);
        assertTrue(states.getStates().length == 1);
        LockState state = (LockState) states.getStates()[0];
        assertTrue(state.getOutsideLock(Location.FRONT_LEFT).getLock() == Lock.UNLOCKED);

        assertTrue(TestUtils.dateIsSame(state.getTimestamp(), "2018-01-10T16:32:05+0000"));
    }

    @Test public void build() {
        // TBODO:
    }

    @Test public void get() throws ParseException {
        Bytes waitingForBytes = new Bytes("001200" +
                "0100050100020020" +
                "02000B01000812010a1020050000" +
                "03000B01000812010a1123000000"
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