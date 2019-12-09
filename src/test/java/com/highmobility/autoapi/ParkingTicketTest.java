package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingTicketTest extends BaseTest {
    Bytes bytes = new Bytes(
            "004701" +
                    "01000401000101" +
                    "02001101000E4265726C696E205061726B696E67" +
                    "03000B0100083634383934323333" +
                    "04000B01000800000160E0EA1388" +
                    "05000B01000800000160E1560840"
    );

    @Test
    public void state() {
        CommandResolver._runtime = CommandResolver.RunTime.ANDROID;
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof ParkingTicket.State);
        ParkingTicket.State state = (ParkingTicket.State) command;
        testState(state);
    }

    private void testState(ParkingTicket.State state) {
        assertTrue(state.getStatus().getValue() == ParkingTicket.Status.STARTED);
        assertTrue(state.getOperatorName().getValue().equals("Berlin Parking"));
        assertTrue(state.getOperatorTicketID().getValue().equals("64894233"));
        assertTrue(TestUtils.dateIsSame(state.getTicketStartTime().getValue(), "2018-01-10T16:32" +
                ":05"));
        assertTrue(TestUtils.dateIsSame(state.getTicketEndTime().getValue(), "2018-01-10T18:30:00"
        ));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        ParkingTicket.State.Builder builder = new ParkingTicket.State.Builder();

        builder.setStatus(new Property(ParkingTicket.Status.STARTED));
        builder.setOperatorName(new Property("Berlin Parking"));
        builder.setOperatorTicketID(new Property("64894233"));
        builder.setTicketStartTime(new Property(TestUtils.getCalendar("2018-01-10T16:32:05")));
        builder.setTicketEndTime(new Property(TestUtils.getCalendar("2018-01-10T18:30:00")));

        ParkingTicket.State command = builder.build();
        testState(command);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004700");
        byte[] bytes = new ParkingTicket.GetParkingTicket().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void startParking() {
        setRuntime(CommandResolver.RunTime.ANDROID);
        Bytes waitingForBytes = new Bytes(
                "004701" +
                        "01000401000101" +
                        "02001101000E4265726c696e205061726b696e67" +
                        "03000B0100083634383934323333" +
                        "04000B01000800000160E1560840");

        Calendar expected = TestUtils.getCalendar("2018-01-10T18:30:00");
        ParkingTicket.StartParking command = new ParkingTicket.StartParking("Berlin Parking", "64894233", expected, null);
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        command = (ParkingTicket.StartParking) CommandResolver.resolve(waitingForBytes);
        assertTrue(TestUtils.dateIsSame(command.getTicketStartTime().getValue(), "2018-01-10T18" +
                ":30:00"));
        assertTrue(command.getTicketEndTime().getValue() == null);
        assertTrue(command.getOperatorName().getValue().equals("Berlin Parking"));
        assertTrue(command.getOperatorTicketID().getValue().equals("64894233"));
    }

    @Test public void endParking() {
        setRuntime(CommandResolver.RunTime.ANDROID);
        Bytes waitingForBytes = new Bytes("00470101000401000100");
        Bytes command = new ParkingTicket.EndParking();
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof ParkingTicket.EndParking);
    }
}
