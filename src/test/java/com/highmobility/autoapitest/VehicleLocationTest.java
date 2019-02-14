package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.autoapi.property.Coordinates;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleLocationTest {
    Bytes bytes = new Bytes(
            "003001" +
                    "040010404A428F9F44D445402ACF562174C4CE" +
                    "050008402ABD80C308FEAC" +
                    "0600084060B00000000000");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == VehicleLocation.class);
        VehicleLocation state = (VehicleLocation) command;

        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954);
        assertTrue(state.getHeading().getValue() == 13.370123);
        assertTrue(state.getAltitude().getValue() == 133.5);
    }

    @Test public void get() {
        String waitingForBytes = "003000";
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleLocation().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("003001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((VehicleLocation) state).getCoordinates().getValue() == null);
    }

    @Test public void build() {
        VehicleLocation.Builder builder = new VehicleLocation.Builder();
        Coordinates coordinates = new Coordinates(52.520008, 13.404954);
        builder.setCoordinates(new ObjectProperty<>(coordinates));
        builder.setHeading(new ObjectProperty<>(13.370123));
        builder.setAltitude(new ObjectProperty<>(133.5));
        assertTrue(builder.build().equals(bytes));
    }
}