package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.EndParking;
import com.highmobility.autoapi.GetParkingTicket;
import com.highmobility.autoapi.ParkingTicket;
import com.highmobility.autoapi.StartParking;
import com.highmobility.autoapi.property.ObjectPropertyString;
import com.highmobility.autoapi.property.ParkingTicketState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

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
        Bytes bytes = new Bytes(
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
        assertTrue(state.getOperatorName().getValue().equals("Berlin Parking"));
        assertTrue(state.getOperatorTicketId().getValue().equals("64894233"));

        assertTrue(TestUtils.dateIsSameUTC(state.getTicketStartDate(), "2017-01-10T17:34:00"));
        assertTrue(TestUtils.dateIsSameUTC(state.getTicketEndDate(), "2018-02-20T22:11:00"));
    }

    @Test public void build() throws ParseException {
        ParkingTicket.Builder builder = new ParkingTicket.Builder();

        builder.setState(ParkingTicketState.STARTED);
        builder.setOperatorName(new ObjectPropertyString("Berlin Parking"));
        builder.setOperatorTicketId(new ObjectPropertyString("64894233"));
        builder.setTicketStart(TestUtils.getUTCCalendar("2017-01-10T17:34:00"));
        builder.setTicketEnd(TestUtils.getUTCCalendar("2018-02-20T22:11:00"));

        ParkingTicket command = builder.build();
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex
                ("0047010100010102000E4265726c696e205061726b696e67030008363438393432333304000811010a1122000000050008120214160B000000")));
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004700");
        byte[] bytes = new GetParkingTicket().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void startParking() throws ParseException {
        Bytes waitingForBytes = new Bytes(
                "00470201000E4265726c696e205061726b696e67020008363438393432333303000811010a112200003C");

        Calendar startDate = new GregorianCalendar();
        startDate.set(2017, 0, 10, 17, 34, 0);
        long timeZoneOffset = 3600000;
        startDate.setTimeZone(new SimpleTimeZone((int) timeZoneOffset, "CET"));

        StartParking command = new StartParking("Berlin Parking", "64894233", startDate, null);
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        command = (StartParking) CommandResolver.resolve(waitingForBytes);
        Calendar expected = TestUtils.getUTCCalendar("2017-01-10T16:34:00", (int) (timeZoneOffset /
                60 / 1000));

        assertTrue(TestUtils.dateIsSameIgnoreTimezone(command.getStartDate(), expected));
        assertTrue(command.getEndDate() == null);
        assertTrue(command.getOperatorName().getValue().equals("Berlin Parking"));
        assertTrue(command.getOperatorTicketId().getValue().equals("64894233"));
    }

    @Test public void endParking() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004703");

        byte[] bytes = new EndParking().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004701");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ParkingTicket) state).getState() == null);
    }
}
