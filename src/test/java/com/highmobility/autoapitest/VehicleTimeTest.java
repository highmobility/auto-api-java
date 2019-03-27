package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleTime;
import com.highmobility.autoapi.VehicleTime;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleTimeTest {
    Bytes bytes = new Bytes("005001" +
            "01000B0100080000015999E5BFC0"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == VehicleTime.class);
        VehicleTime state = (VehicleTime) command;
        testState(state);
    }

    private void testState(VehicleTime state) {
        Calendar c = state.getVehicleTime().getValue();
        assertTrue(TestUtils.dateIsSame(c, "2017-01-13T22:14:48"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005000";
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleTime().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() throws ParseException {
        VehicleTime.Builder builder = new VehicleTime.Builder();
        Calendar c = TestUtils.getCalendar("2017-01-13T22:14:48");
        builder.setVehicleTime(new Property(c));
        testState(builder.build());
    }
}