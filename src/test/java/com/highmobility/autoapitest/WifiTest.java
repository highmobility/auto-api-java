package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ConnectToNetwork;
import com.highmobility.autoapi.EnableDisableWifi;
import com.highmobility.autoapi.ForgetNetwork;
import com.highmobility.autoapi.GetWifiState;
import com.highmobility.autoapi.WifiState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WifiTest {
    Bytes bytes = new Bytes(
            "005901" +
                    "01000401000101" +
                    "02000401000101" +
                    "030007010004484F4D45" +
                    "04000401000103"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == WifiState.class);
        WifiState state = (WifiState) command;
        assertTrue(state.isEnabled().getValue() == true);
        assertTrue(state.isConnected().getValue() == true);
        assertTrue(state.getSsid().getValue().equals("HOME"));
        assertTrue(state.getSecurity().getValue() == NetworkSecurity.WPA2_PERSONAL);
    }

    @Test public void build() {
        WifiState.Builder builder = new WifiState.Builder();

        builder.setEnabled(new Property(true));
        builder.setConnected(new Property(true));
        builder.setSsid(new Property("HOME"));
        builder.setSecurity(new Property(NetworkSecurity.WPA2_PERSONAL));

        WifiState state = builder.build();
        assertTrue(state.equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005900";
        String commandBytes = ByteUtils.hexFromBytes(new GetWifiState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void connectToNetwork() {
        Bytes waitingForBytes = new Bytes(
                "005902" +
                        "030007010004484f4d45" +
                        "04000401000103" +
                        "05000D01000A5a57337641524e554265");

        byte[] commandBytes = null;
        commandBytes =
                new ConnectToNetwork("HOME", NetworkSecurity.WPA2_PERSONAL, "ZW3vARNUBe").getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        ConnectToNetwork command = (ConnectToNetwork) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSsid().getValue().equals("HOME"));
        assertTrue(command.getSecurity().getValue() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(command.getPassword().getValue().equals("ZW3vARNUBe"));
    }

    @Test public void forgetNetwork() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("005903" +
                "030007010004484f4d45");
        byte[] commandBytes = new ForgetNetwork("HOME").getByteArray();

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        ForgetNetwork command = (ForgetNetwork) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSsid().getValue().equals("HOME"));
    }

    @Test public void enableDisableWifi() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("005904" +
                "01000401000101");
        byte[] commandBytes = new EnableDisableWifi(true).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        EnableDisableWifi command = (EnableDisableWifi) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.enable().getValue() == true);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(ConnectToNetwork.TYPE.getIdentifierAndType()).getClass() == Command.class);
        assertTrue(CommandResolver.resolve(ForgetNetwork.TYPE.getIdentifierAndType()).getClass() == Command.class);
        assertTrue(CommandResolver.resolve(EnableDisableWifi.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}