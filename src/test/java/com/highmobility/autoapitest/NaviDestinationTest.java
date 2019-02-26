package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetNaviDestination;
import com.highmobility.autoapi.NaviDestination;
import com.highmobility.autoapi.SetNaviDestination;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

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

        assertTrue(state.getName().equals("Berlin"));
        assertTrue(state.getCoordinates().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getLongitude() == 13.404954);
    }

    @Test public void get() {
        String waitingForBytes = "003100";
        assertTrue(new GetNaviDestination().equals(waitingForBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes("003102" +
                "070013010010404A428F9F44D445402ACF562174C4CE" +
                "0200090100064265726c696e");

        Bytes commandBytes = new SetNaviDestination(new CoordinatesProperty(52.520008, 13.404954),
                "Berlin");

        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        SetNaviDestination command = (SetNaviDestination) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getName().equals("Berlin"));
        assertTrue(command.getCoordinates().getLatitude() == 52.520008);
        assertTrue(command.getCoordinates().getLongitude() == 13.404954);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("003101");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((NaviDestination) state).getName() == null);
    }

    @Test public void build() {
        NaviDestination.Builder builder = new NaviDestination.Builder();
        builder.setCoordinates(new CoordinatesProperty(52.520008, 13.404954));
        builder.setName("Berlin");
        Command state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }
}