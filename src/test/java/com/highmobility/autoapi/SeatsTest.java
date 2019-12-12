package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Detected;
import com.highmobility.autoapi.value.PersonDetected;
import com.highmobility.autoapi.value.SeatLocation;
import com.highmobility.autoapi.value.SeatbeltState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class SeatsTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "005601" +
            "0200050100020201" +
            "0200050100020300" +
            "0300050100020201" +
            "0300050100020300"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == Seats.State.class);
        Seats.State state = (Seats.State) command;
        testState(state);
    }

    private void testState(Seats.State state) {
        assertTrue(state.getPersonDetection(SeatLocation.REAR_RIGHT).getValue().getDetected() == Detected.DETECTED);
        assertTrue(state.getPersonDetection(SeatLocation.REAR_LEFT).getValue().getDetected() == Detected.NOT_DETECTED);

        assertTrue(state.getSeatBeltFastened(SeatLocation.REAR_RIGHT).getValue().getFastenedState() == SeatbeltState.FastenedState.FASTENED);
        assertTrue(state.getSeatBeltFastened(SeatLocation.REAR_LEFT).getValue().getFastenedState() == SeatbeltState.FastenedState.NOT_FASTENED);

        assertTrue(state.getPersonsDetected().length == 2);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Seats.State.Builder builder = new Seats.State.Builder();

        builder.addPersonDetected(new Property(new PersonDetected(SeatLocation.REAR_RIGHT, Detected.DETECTED)));
        builder.addPersonDetected(new Property(new PersonDetected(SeatLocation.REAR_LEFT, Detected.NOT_DETECTED)));

        builder.addSeatbeltState(new Property(new SeatbeltState(SeatLocation.REAR_RIGHT,
                SeatbeltState.FastenedState.FASTENED)));
        builder.addSeatbeltState(new Property(new SeatbeltState(SeatLocation.REAR_LEFT,
                SeatbeltState.FastenedState.NOT_FASTENED)));

        Seats.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "005600";
        String commandBytes = ByteUtils.hexFromBytes(new Seats.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}