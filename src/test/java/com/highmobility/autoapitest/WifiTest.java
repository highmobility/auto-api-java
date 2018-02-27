package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ConnectToNetwork;
import com.highmobility.autoapi.EnableDisableWifi;
import com.highmobility.autoapi.ForgetNetwork;
import com.highmobility.autoapi.GetWifiState;
import com.highmobility.autoapi.WifiState;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WifiTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0059010100010102000101030004484f4d4504000103");


        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == WifiState.class);
        WifiState state = (WifiState) command;
        assertTrue(state.isEnabled() == true);
        assertTrue(state.isConnected() == true);
        assertTrue(state.getSsid().equals("HOME"));
        assertTrue(state.getSecurity() == NetworkSecurity.WPA2_PERSONAL);
    }

    @Test public void get() {
        String waitingForBytes = "005900";
        String commandBytes = Bytes.hexFromBytes(new GetWifiState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void connectToNetwork() {
        byte[] waitingForBytes = Bytes.bytesFromHex
                ("005902030004484f4d450400010305000A5a57337641524e554265");
        byte[] commandBytes = null;
        commandBytes = new ConnectToNetwork("HOME", NetworkSecurity.WPA2_PERSONAL, "ZW3vARNUBe")
                .getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void forgetNetwork() {
        byte[] waitingForBytes = Bytes.bytesFromHex("005903030004484f4d45");
        byte[] commandBytes = null;
        commandBytes = new ForgetNetwork("HOME").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void enableDisableWifi() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00590400");
        byte[] commandBytes = new EnableDisableWifi(false).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }
}