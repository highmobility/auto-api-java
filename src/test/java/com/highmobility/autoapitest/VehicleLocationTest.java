package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleLocationTest {
    Bytes bytes = new Bytes(
            "003001" +
                    "040013010010404A428F9F44D445402ACF562174C4CE" +
                    "05000B010008402ABD80C308FEAC" +
                    "06000B0100084060B00000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        VehicleLocation state = (VehicleLocation) command;
        testState(state);
    }

    private void testState(VehicleLocation state) {
        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954);
        assertTrue(state.getHeading().getValue() == 13.370123);
        assertTrue(state.getAltitude().getValue() == 133.5);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "003000";
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleLocation().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        VehicleLocation.Builder builder = new VehicleLocation.Builder();
        Coordinates coordinates = new Coordinates(52.520008, 13.404954);
        builder.setCoordinates(new Property(coordinates));
        builder.setHeading(new Property(13.370123));
        builder.setAltitude(new Property(133.5));
        testState(builder.build());
    }
}