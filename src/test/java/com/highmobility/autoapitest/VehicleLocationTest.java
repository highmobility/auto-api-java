package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleLocationTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0030010100084252147d41567ab10200044252147d");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == VehicleLocation.class);
        VehicleLocation state = (VehicleLocation) command;
        assertTrue(state.getCoordinates().getLatitude() == 52.520008f);
        assertTrue(state.getCoordinates().getLongitude() == 13.404954f);
        assertTrue(state.getHeading() == 52.520008f);
    }

    @Test public void get() {
        String waitingForBytes = "003000";
        String commandBytes = Bytes.hexFromBytes(new GetVehicleLocation().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("003001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((VehicleLocation)state).getCoordinates() == null);
    }
}