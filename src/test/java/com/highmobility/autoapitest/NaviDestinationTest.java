package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetNaviDestination;
import com.highmobility.autoapi.GetVehicleTime;
import com.highmobility.autoapi.NaviDestination;
import com.highmobility.autoapi.SetNaviDestination;
import com.highmobility.autoapi.VehicleTime;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NaviDestinationTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "003101" +
                    "0100044252147d" +
                    "02000441567ab1" +
                    "0300064265726c696e");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == NaviDestination.class);
        NaviDestination state = (NaviDestination) command;

        assertTrue(state.getName().equals("Berlin"));
        assertTrue(state.getLatitude() == 52.520008f);
        assertTrue(state.getLongitude() == 13.404954f);
    }

    @Test public void get() {
        String waitingForBytes = "003100";
        String commandBytes = Bytes.hexFromBytes(new GetNaviDestination().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        byte[] waitingForBytes = Bytes.bytesFromHex("003102" +
                "0100044252147d" +
                "02000441567ab1" +
                "0300064265726c696e");

        byte[] commandBytes = null;
        try {
            commandBytes = new SetNaviDestination(
                    52.520008f,
                    13.404954f,
                    "Berlin").getBytes();
        } catch (Exception e) {
            fail();
        }

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }
}