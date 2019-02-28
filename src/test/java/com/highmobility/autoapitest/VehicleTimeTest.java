package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleTime;
import com.highmobility.autoapi.VehicleTime;
import com.highmobility.autoapi.property.CalendarProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleTimeTest {
    Bytes bytes = new Bytes("005001" +
            "01000B0100080000015999E5BFC0"
    );

    @Test
    public void state() throws ParseException {

        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == VehicleTime.class);
        VehicleTime state = (VehicleTime) command;
        testState(state);
    }

    private void testState(VehicleTime state) throws ParseException {
        Calendar c = state.getVehicleTime().getValue();
        assertTrue(TestUtils.dateIsSame(c, "2017-01-13T22:14:48"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000001"));
        VehicleTime command = (VehicleTime) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getVehicleTime().getTimestamp() != null);
    }

    @Test public void get() {
        String waitingForBytes = "005000";
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleTime().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((VehicleTime) state).getVehicleTime().getValue() == null);
    }

    @Test public void build() throws ParseException {
        VehicleTime.Builder builder = new VehicleTime.Builder();
        Calendar c = TestUtils.getCalendar("2017-01-13T22:14:48");
        builder.setVehicleTime(new CalendarProperty(c));
        testState(builder.build());
    }
}