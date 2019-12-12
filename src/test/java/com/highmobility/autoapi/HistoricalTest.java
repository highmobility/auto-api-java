package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class HistoricalTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "001201" +
            "01001D01001A" + // >> length 19 + 3 + 3
            COMMAND_HEADER + "0020010300050100020000" + // 1 outside lock front left unlocked
            "A2000B01000800000160E1560840"
    );

    @Test
    public void state() {
        Historical.State states = (Historical.State) CommandResolver.resolve(bytes);
        testState(states);
    }

    private void testState(Historical.State state) {
        assertTrue(state.getStates().length == 1);
        Doors.State lockState = (Doors.State) state.getStates()[0].getValue();

        assertTrue(lockState.getLocks()[0].getValue().getLocation() == Location.FRONT_LEFT);
        assertTrue(lockState.getLocks()[0].getValue().getLockState() == LockState.UNLOCKED);

        assertTrue(TestUtils.dateIsSame(lockState.getTimestamp(), "2018-01-10T18:30:00"));
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Lock outsideLock = new Lock(Location.FRONT_LEFT, LockState.UNLOCKED);
        Calendar timestamp = TestUtils.getCalendar("2018-01-10T18:30:00");

        Doors.State.Builder doorsBuilder = new Doors.State.Builder();
        doorsBuilder.addLock(new Property(outsideLock));
        doorsBuilder.setTimestamp(timestamp);
        Doors.State doorsS = doorsBuilder.build();

        Historical.State.Builder historicalBuilder = new Historical.State.Builder();
        historicalBuilder.addState(new Property(doorsS));

        Historical.State historicalStates = historicalBuilder.build();
        testState(historicalStates);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "001201" +
                "0200050100020020" +
                "03000B01000800000160E0EA1388" +
                "04000B01000800000160E1560840"
        );

        Command command = new Historical.RequestStates(
                Identifier.DOORS,
                TestUtils.getCalendar("2018-01-10T16:32:05"),
                TestUtils.getCalendar("2018-01-10T18:30:00")
        );

        assertTrue(bytesTheSame(command, waitingForBytes));
    }
}