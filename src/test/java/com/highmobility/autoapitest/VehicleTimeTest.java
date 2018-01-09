package com.highmobility.autoapitest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleTime;
import com.highmobility.autoapi.VehicleTime;
import com.highmobility.utils.Bytes;

import org.junit.Test;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleTimeTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "005001" +
                    "01000811010A1020330078");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == VehicleTime.class);
        VehicleTime state = (VehicleTime) command;
        Calendar c = state.getVehicleTime();

        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = 120 * 60 * 1000;
        assertTrue(rawOffset == expectedRawOffset);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date commandDate = c.getTime();
            // hour is 16 - 2 = 14 because timezone is UTC+2
            Date expectedDate = format.parse("2017-01-10T14:32:51");
            assertTrue((format.format(commandDate).equals(format.format(expectedDate))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test public void get() {
        String waitingForBytes = "005000";
        String commandBytes = Bytes.hexFromBytes(new GetVehicleTime().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}