package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ConnectToNetwork;
import com.highmobility.autoapi.EnableDisableWifi;
import com.highmobility.autoapi.ForgetNetwork;
import com.highmobility.autoapi.GetWifiState;
import com.highmobility.autoapi.WifiState;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

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
        Bytes bytes = new Bytes(
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

    @Test public void build() {
        WifiState.Builder builder = new WifiState.Builder();

        builder.setEnabled(true);
        builder.setConnected(true);
        builder.setSsid("HOME");
        builder.setSecurity(NetworkSecurity.WPA2_PERSONAL);

        WifiState state = builder.build();
        assertTrue(Arrays.equals(state.getByteArray(), ByteUtils.bytesFromHex
                ("0059010100010102000101030004484f4d4504000103")));
    }

    @Test public void get() {
        String waitingForBytes = "005900";
        String commandBytes = ByteUtils.hexFromBytes(new GetWifiState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void connectToNetwork() {
        Bytes waitingForBytes = new Bytes(
                "005902030004484f4d450400010305000A5a57337641524e554265");
        byte[] commandBytes = null;
        commandBytes = new ConnectToNetwork("HOME", NetworkSecurity
                .WPA2_PERSONAL, "ZW3vARNUBe")
                .getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        ConnectToNetwork command = (ConnectToNetwork) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSsid().equals("HOME"));
        assertTrue(command.getSecurity() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(command.getPassword().equals("ZW3vARNUBe"));
    }

    @Test public void forgetNetwork() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("005903030004484f4d45");
        byte[] commandBytes = new ForgetNetwork("HOME").getByteArray();

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        ForgetNetwork command = (ForgetNetwork) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSsid().equals("HOME"));
    }

    @Test public void enableDisableWifi() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("00590401");
        byte[] commandBytes = new EnableDisableWifi(true).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        EnableDisableWifi command = (EnableDisableWifi) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.enable() == true);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005901");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((WifiState) state).getSecurity() == null);
    }
}