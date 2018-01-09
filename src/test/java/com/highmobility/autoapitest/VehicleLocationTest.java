package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.ValetMode;
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
                "003001" +
                    "0100044252147d" +
                    "02000441567ab1");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == VehicleLocation.class);
        VehicleLocation state = (VehicleLocation) command;
        assertTrue(state.getLatitude() == 52.520008f);
        assertTrue(state.getLongitude() == 13.404954f);
    }

    @Test public void get() {
        String waitingForBytes = "003000";
        String commandBytes = Bytes.hexFromBytes(new GetVehicleLocation().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}