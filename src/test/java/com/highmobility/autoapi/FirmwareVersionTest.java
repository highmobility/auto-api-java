package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.HmkitVersion;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class FirmwareVersionTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "000301" +
                    "010006010003010F21" +
                    "02000F01000C6274737461636B2D75617274" +
                    "03000C01000976312E352D70726F64"
    );

    @Test
    public void state() {
        FirmwareVersionState command = (FirmwareVersionState) CommandResolver.resolve(bytes);
        testState(command);
    }

    void testState(FirmwareVersionState state) {
        HmkitVersion version = state.getHmKitVersion().getValue();

        assertTrue(version.getMajor() == 1);
        assertTrue(version.getMinor() == 15);
        assertTrue(version.getPatch() == 33);
        assertTrue(state.getHmKitBuildName().getValue().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().getValue().equals("v1.5-prod"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        FirmwareVersionState.Builder builder = new FirmwareVersionState.Builder();

        builder.setHmKitVersion(new Property(new HmkitVersion(1, 15, 33)));
        builder.setHmKitBuildName(new Property("btstack-uart"));
        builder.setApplicationVersion(new Property("v1.5-prod"));

        FirmwareVersionState command = builder.build();
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "000300";
        assertTrue(new GetFirmwareVersion().equals(waitingForBytes));
    }
}