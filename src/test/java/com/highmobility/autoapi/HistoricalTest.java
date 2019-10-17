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
    Bytes bytes = new Bytes(
            "001201" +
                    "01001C010019" + // >> length 19 + 3 + 3
                    "0020010300050100020000" + // 1 outside lock front left unlocked
                    "A2000B01000800000160E1560840"
    );

    @Test
    public void state() {
        HistoricalState states = (HistoricalState) CommandResolver.resolve(bytes);
        testState(states);
    }

    private void testState(HistoricalState state) {
        assertTrue(state.getStates().length == 1);
        DoorsState lockState = (DoorsState) state.getStates()[0].getValue();

        assertTrue(lockState.getLocks()[0].getValue().getLocation() == Location.FRONT_LEFT);
        assertTrue(lockState.getLocks()[0].getValue().getLockState() == LockState.UNLOCKED);

        assertTrue(TestUtils.dateIsSame(lockState.getTimestamp(), "2018-01-10T18:30:00"));
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Lock outsideLock = new Lock(Location.FRONT_LEFT, LockState.UNLOCKED);
        Calendar timestamp = TestUtils.getCalendar("2018-01-10T18:30:00");

        DoorsState.Builder doorsBuilder = new DoorsState.Builder();
        doorsBuilder.addLock(new Property(outsideLock));
        doorsBuilder.setTimestamp(timestamp);
        DoorsState doorsS = doorsBuilder.build();

        HistoricalState.Builder historicalBuilder = new HistoricalState.Builder();
        historicalBuilder.addState(new Property(doorsS));

        HistoricalState historicalStates = historicalBuilder.build();
        testState(historicalStates);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("001201" +
                "0200050100020020" +
                "03000B01000800000160E0EA1388" +
                "04000B01000800000160E1560840"
        );

        Command command = new RequestStates(
                Identifier.DOORS,
                TestUtils.getCalendar("2018-01-10T16:32:05"),
                TestUtils.getCalendar("2018-01-10T18:30:00")
        );

        assertTrue(bytesTheSame(command, waitingForBytes));
    }
}