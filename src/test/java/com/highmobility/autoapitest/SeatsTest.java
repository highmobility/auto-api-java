package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetSeatsState;
import com.highmobility.autoapi.SeatsState;
import com.highmobility.autoapi.property.SeatStateProperty;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class SeatsTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "005601010003000101010003010000");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == SeatsState.class);
        SeatsState state = (SeatsState) command;
        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_LEFT).isPersonDetected()
                == true);
        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_LEFT).isSeatBeltFastened()
                == true);

        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_RIGHT).isPersonDetected()
                == false);
        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_RIGHT).isSeatBeltFastened
                () == false);

        assertTrue(state.getSeatsStates().length == 2);
    }

    @Test public void build() {
        SeatsState.Builder builder = new SeatsState.Builder();
        SeatStateProperty seat1 = new SeatStateProperty(SeatStateProperty.Position.FRONT_LEFT,
                true, true);
        SeatStateProperty seat2 = new SeatStateProperty(SeatStateProperty.Position.FRONT_RIGHT,
                false, false);
        builder.addSeatState(seat1).addSeatState(seat2);
        SeatsState state = builder.build();

        assertTrue(Arrays.equals(state.getByteArray(), ByteUtils.bytesFromHex
                ("005601010003000101010003010000")));
    }

    @Test public void get() {
        String waitingForBytes = "005600";
        String commandBytes = ByteUtils.hexFromBytes(new GetSeatsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((SeatsState) state).getSeatsStates().length == 0);
    }
}