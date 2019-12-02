package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ConnectionState;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WifiTest extends BaseTest {
    Bytes bytes = new Bytes(
            "005901" +
                    "01000401000101" +
                    "02000401000101" +
                    "030007010004484F4D45" +
                    "04000401000103"
    );

    @Test
    public void state() {
        testState((WiFiState) CommandResolver.resolve(bytes));
    }

    private void testState(WiFiState state) {
        assertTrue(state.getStatus().getValue() == EnabledState.ENABLED);
        assertTrue(state.getNetworkConnected().getValue() == ConnectionState.CONNECTED);
        assertTrue(state.getNetworkSSID().getValue().equals("HOME"));
        assertTrue(state.getNetworkSecurity().getValue() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        WiFiState.Builder builder = new WiFiState.Builder();

        builder.setStatus(new Property(EnabledState.ENABLED));
        builder.setNetworkConnected(new Property(ConnectionState.CONNECTED));
        builder.setNetworkSSID(new Property("HOME"));
        builder.setNetworkSecurity(new Property(NetworkSecurity.WPA2_PERSONAL));

        testState(builder.build());
    }

    @Test public void get() {
        String waitingForBytes = "005900";
        String commandBytes = ByteUtils.hexFromBytes(new GetWiFiState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void connectToNetwork() {
        Bytes waitingForBytes = new Bytes(
                "005901" +
                        "030007010004484f4d45" +
                        "04000401000103" +
                        "05000D01000A5a57337641524e554265");

        byte[] commandBytes = null;
        commandBytes =
                new ConnectToNetwork("HOME", NetworkSecurity.WPA2_PERSONAL, "ZW3vARNUBe").getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ConnectToNetwork command = (ConnectToNetwork) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getNetworkSSID().getValue().equals("HOME"));
        assertTrue(command.getNetworkSecurity().getValue() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(command.getPassword().getValue().equals("ZW3vARNUBe"));
    }

    @Test public void forgetNetwork() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("005901" +
                "030007010004484f4d45");
        byte[] commandBytes = new ForgetNetwork("HOME").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ForgetNetwork command = (ForgetNetwork) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getNetworkSSID().getValue().equals("HOME"));
    }

    @Test public void enableDisableWifi() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("005901" +
                "01000401000101");
        byte[] commandBytes = new EnableDisableWiFi(EnabledState.ENABLED).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        EnableDisableWiFi command = (EnableDisableWiFi) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == EnabledState.ENABLED);
    }
}