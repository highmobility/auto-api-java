package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetNaviDestination;
import com.highmobility.autoapi.NaviDestination;
import com.highmobility.autoapi.SetNaviDestination;
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
        byte[] bytes = Bytes.bytesFromHex(
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
        String commandBytes = Bytes.hexFromBytes(new GetNaviDestination().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        byte[] waitingForBytes = Bytes.bytesFromHex("003102" +
                "0100044252147d" +
                "02000441567ab1" +
                "0300064265726c696e");

        byte[] commandBytes = null;
        try {
            commandBytes = new SetNaviDestination(
                    52.520008f,
                    13.404954f,
                    "Berlin").getBytes();
        } catch (Exception e) {
            fail();
        }

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("003101");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((NaviDestination)state).getName() == null);
    }
}