package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.EndParking;
import com.highmobility.autoapi.GetParkingTicket;
import com.highmobility.autoapi.ParkingTicket;
import com.highmobility.autoapi.StartParking;
import com.highmobility.autoapi.property.ParkingTicketState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ParkingTicketTest {
    @Test
    public void state() throws ParseException {
        byte[] bytes = Bytes.bytesFromHex(
                "0047010100010102000E4265726c696e205061726b696e67030008363438393432333304000811010a1122000000050008120214160B000000");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(ParkingTicket.TYPE));
        ParkingTicket state = (ParkingTicket) command;

        assertTrue(((ParkingTicket) command).getState() == ParkingTicketState.STARTED);
        assertTrue(state.getOperatorName().equals("Berlin Parking"));
        assertTrue(state.getOperatorTicketId().equals("64894233"));

        assertTrue(TestUtils.dateIsSame(state.getTicketStartDate(), "2017-01-10T17:34:00"));
        assertTrue(TestUtils.dateIsSame(state.getTicketEndDate(), "2018-02-20T22:11:00"));

    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004700");
        byte[] bytes = new GetParkingTicket().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void startParking() {
        byte[] waitingForBytes = Bytes.bytesFromHex
                ("00470201000E4265726c696e205061726b696e67020008363438393432333303000811010a112200003C");

        Calendar startDate = new GregorianCalendar();
        startDate.set(2017, 0, 10, 17, 34, 0);
        startDate.setTimeZone(new SimpleTimeZone(3600000, "CET"));

        byte[] bytes = new StartParking("Berlin Parking", "64894233", startDate, null).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void endParking() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004703");

        byte[] bytes = new EndParking().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("004701");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ParkingTicket)state).getState() == null);
    }
}
