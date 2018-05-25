package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetNaviDestination;
import com.highmobility.autoapi.NaviDestination;
import com.highmobility.autoapi.SetNaviDestination;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NaviDestinationTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "0031010100084252147d41567ab10200064265726c696e");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == NaviDestination.class);
        NaviDestination state = (NaviDestination) command;

        assertTrue(state.getName().equals("Berlin"));
        assertTrue(state.getCoordinates().getLatitude() == 52.520008f);
        assertTrue(state.getCoordinates().getLongitude() == 13.404954f);
    }

    @Test public void get() {
        String waitingForBytes = "003100";
        assertTrue(new GetNaviDestination().equals(waitingForBytes));
    }

    @Test public void set() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex
                ("0031020100084252147D41567AB10200064265726C696E");

        byte[] commandBytes = null;
        try {
            commandBytes = new SetNaviDestination(new CoordinatesProperty(52.520008f, 13.404954f),
                    "Berlin").getByteArray();
        } catch (Exception e) {
            fail();
        }

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        SetNaviDestination command = (SetNaviDestination) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getName().equals("Berlin"));
        assertTrue(command.getCoordinates().getLatitude() == 52.520008f);
        assertTrue(command.getCoordinates().getLongitude() == 13.404954f);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("003101");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((NaviDestination) state).getName() == null);
    }

    @Test public void build() {
        NaviDestination.Builder builder = new NaviDestination.Builder();
        builder.setCoordinates(new CoordinatesProperty(52.520008f, 13.404954f));
        builder.setName("Berlin");
        byte[] bytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(bytes, ByteUtils.bytesFromHex("0031010100084252147d41567ab10200064265726c696e")));

    }
}