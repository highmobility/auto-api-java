package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetSeatsState;
import com.highmobility.autoapi.GetVehicleTime;
import com.highmobility.autoapi.SeatsState;
import com.highmobility.autoapi.VehicleTime;
import com.highmobility.autoapi.property.SeatStateProperty;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class SeatsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "005601010003000101010003010000");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == SeatsState.class);
        SeatsState state = (SeatsState) command;
        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_LEFT).isPersonDetected() == true);
        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_LEFT).isSeatbeltFastened() == true);

        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_RIGHT).isPersonDetected() == false);
        assertTrue(state.getSeatState(SeatStateProperty.Position.FRONT_RIGHT).isSeatbeltFastened() == false);

        assertTrue(state.getSeatsStates().length == 2);
    }

    @Test public void get() {
        String waitingForBytes = "005600";
        String commandBytes = Bytes.hexFromBytes(new GetSeatsState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}