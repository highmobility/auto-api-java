package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleTime;
import com.highmobility.autoapi.VehicleTime;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

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
public class VehicleTimeTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes("00500101000811010A1020330078");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
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
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleTime().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((VehicleTime) state).getVehicleTime() == null);
    }

    @Test public void build() throws ParseException {
        VehicleTime.Builder builder = new VehicleTime.Builder();
        Calendar c = TestUtils.getCalendar("2017-01-10T14:32:51", 120);
        builder.setVehicleTime(c);
        byte[] bytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(bytes, ByteUtils.bytesFromHex("00500101000811010A1020330078")));

    }
}