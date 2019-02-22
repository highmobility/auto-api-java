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
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleTimeTest {
    Bytes bytes = new Bytes("005001" +
            "01000B0100080000015999E5BFC0"
    );

    @Test
    public void state() throws ParseException {

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == VehicleTime.class);
        VehicleTime state = (VehicleTime) command;
        Calendar c = state.getVehicleTime();
        assertTrue(TestUtils.dateIsSame(c, "2017-01-13T22:14:48"));

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
        Calendar c = TestUtils.getCalendar("2017-01-13T22:14:48");
        builder.setVehicleTime(c);
        VehicleTime time = builder.build();
        assertTrue(time.equals(bytes));

    }
}