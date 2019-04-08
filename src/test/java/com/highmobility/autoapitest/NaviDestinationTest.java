package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetNaviDestination;
import com.highmobility.autoapi.NaviDestination;
import com.highmobility.autoapi.SetNaviDestination;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NaviDestinationTest {
    Bytes bytes = new Bytes(
            "003101" +
                    "070013010010404A428F9F44D445402ACF562174C4CE" +
                    "0200090100064265726C696E");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == NaviDestination.class);
        NaviDestination state = (NaviDestination) command;
        testState(state);
    }

    private void testState(NaviDestination state) {
        assertTrue(state.getName().getValue().equals("Berlin"));
        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "003100";
        assertTrue(new GetNaviDestination().equals(waitingForBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes("003102" +
                "070013010010404A428F9F44D445402ACF562174C4CE" +
                "0200090100064265726c696e");

        Bytes commandBytes = new SetNaviDestination(new Coordinates(52.520008, 13.404954),
                "Berlin");

        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        SetNaviDestination command = (SetNaviDestination) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getName().getValue().equals("Berlin"));
        assertTrue(command.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(command.getCoordinates().getValue().getLongitude() == 13.404954);
    }

    @Test public void build() {
        NaviDestination.Builder builder = new NaviDestination.Builder();
        builder.setCoordinates(new Property(new Coordinates(52.520008, 13.404954)));
        builder.setName(new Property("Berlin"));
        NaviDestination state = builder.build();
        testState(state);
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(() -> {
            assertTrue(CommandResolver.resolve(SetNaviDestination.TYPE.getIdentifierAndType()).getClass() == Command.class);
        });
    }
}