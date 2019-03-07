package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetSeatsState;
import com.highmobility.autoapi.SeatsState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.seats.PersonDetected;
import com.highmobility.autoapi.property.seats.SeatBeltFastened;
import com.highmobility.autoapi.property.seats.SeatLocation;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class SeatsTest {
    Bytes bytes = new Bytes("005601" +
            "0200050100020201" +
            "0200050100020300" +
            "0300050100020201" +
            "0300050100020300"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == SeatsState.class);
        SeatsState state = (SeatsState) command;
        assertTrue(state.getPersonDetection(SeatLocation.REAR_RIGHT).getValue().isDetected());
        assertTrue(state.getPersonDetection(SeatLocation.REAR_LEFT).getValue().isDetected() == false);

        assertTrue(state.getSeatBeltFastened(SeatLocation.REAR_RIGHT).getValue().isFastened());
        assertTrue(state.getSeatBeltFastened(SeatLocation.REAR_LEFT).getValue().isFastened() == false);

        assertTrue(state.getPersonsDetected().length == 2);
    }

    @Test public void build() {
        SeatsState.Builder builder = new SeatsState.Builder();

        builder.addPersonDetected(new Property(new PersonDetected(SeatLocation.REAR_RIGHT, true)));
        builder.addPersonDetected(new Property(new PersonDetected(SeatLocation.REAR_LEFT, false)));

        builder.addSeatBeltFastened(new Property(new SeatBeltFastened(SeatLocation.REAR_RIGHT, true)));
        builder.addSeatBeltFastened(new Property(new SeatBeltFastened(SeatLocation.REAR_LEFT, false)));

        SeatsState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005600";
        String commandBytes = ByteUtils.hexFromBytes(new GetSeatsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((SeatsState) state).getPersonsDetected().length == 0);
    }
}