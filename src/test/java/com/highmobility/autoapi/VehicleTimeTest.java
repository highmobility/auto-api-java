package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Time;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleTimeTest extends BaseTest {
    Bytes bytes = new Bytes("005001" +
            "010005010002160E"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == VehicleTimeState.class);
        VehicleTimeState state = (VehicleTimeState) command;
        testState(state);
    }

    private void testState(VehicleTimeState state) {
        Time c = state.getVehicleTime().getValue();
        assertTrue(c.getHour() == 22);
        assertTrue(c.getMinute() == 14);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005000";
        String commandBytes = ByteUtils.hexFromBytes(new GetVehicleTime().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        VehicleTimeState.Builder builder = new VehicleTimeState.Builder();
        builder.setVehicleTime(new Property(new Time(22, 14)));
        testState(builder.build());
    }
}