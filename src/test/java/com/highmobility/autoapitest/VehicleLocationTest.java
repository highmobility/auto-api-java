package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleLocationTest {
    byte[] bytes = ByteUtils.bytesFromHex(
            "0030010100084252147d41567ab10200044252147d"
                    + "03000443058000");

    @Test
    public void state() {
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
        assertTrue(state.getAltitude() == 133.5f);
    }

    @Test public void get() {
        String waitingForBytes = "003000";
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleLocation().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("003001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((VehicleLocation) state).getCoordinates() == null);
    }

    @Test public void build() {
        VehicleLocation.Builder builder = new VehicleLocation.Builder();
        CoordinatesProperty coordinates = new CoordinatesProperty(52.520008f, 13.404954f);
        builder.setCoordinates(coordinates);
        builder.setHeading(52.520008f);
        builder.setAltitude(133.5f);
        assertTrue(Arrays.equals(builder.build().getByteArray(), bytes));
    }
}