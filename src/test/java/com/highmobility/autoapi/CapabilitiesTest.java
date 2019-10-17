package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.SupportedCapability;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CapabilitiesTest extends BaseTest {
    Bytes bytes = new Bytes
            ("001001" +
                    "01000C010009002000050203040506" +
                    "01000A01000700230003020811");

    @Test
    public void capabilities() {
        Command command = CommandResolver.resolve(bytes);
        testState((CapabilitiesState) command);
    }

    private void testState(CapabilitiesState state) {
        // l11. now capabilities are defined property by property

        // doors
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x02));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x03));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x04));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x05));
        assertTrue(state.getSupported(Identifier.DOORS, (byte) 0x06));

        // trunk
        assertTrue(state.getSupported(Identifier.CHARGING, (byte) 0x02));
        assertTrue(state.getSupported(Identifier.CHARGING, (byte) 0x08));
        assertTrue(state.getSupported(Identifier.CHARGING, (byte) 0x11));

        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void getCapabilities() {
        byte[] bytes = ByteUtils.bytesFromHex("001000");
        byte[] commandBytes = new GetCapabilities().getByteArray();
        assertTrue(Arrays.equals(bytes, commandBytes));
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetCapabilities);
    }

    @Test public void build() {
        CapabilitiesState.Builder builder = new CapabilitiesState.Builder();

        builder.addCapability(new Property(new SupportedCapability(Identifier.DOORS,
                new Bytes("0203040506"))));
        builder.addCapability(new Property(new SupportedCapability(Identifier.CHARGING,
                new Bytes("020811"))));

        CapabilitiesState state = builder.build();
        assertTrue(bytesTheSame(state, bytes));
        testState(state);
    }

    @Test public void zeroProperties() {
        CapabilitiesState.Builder builder = new CapabilitiesState.Builder();
        CapabilitiesState capabilities = builder.build();
        testEmptyCommand(capabilities);
        assertTrue(capabilities.getLength() == 3);

        byte[] bytes = ByteUtils.bytesFromHex("00100100");
        testEmptyCommand((CapabilitiesState) CommandResolver.resolve(bytes));
    }

    void testEmptyCommand(CapabilitiesState capabilities) {
        assertTrue(capabilities.getCapabilities().length == 0);
        assertTrue(capabilities.getSupported(Identifier.DOORS, (byte) 0x01) == false);
    }
}