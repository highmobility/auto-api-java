package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FirmwareVersion;
import com.highmobility.autoapi.GetFirmwareVersion;
import com.highmobility.autoapi.property.IntArrayProperty;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class FirmwareVersionTest {
    Bytes bytes = new Bytes(
            "000301010003010f2102000C6274737461636b2d7561727403000976312e352d70726f64");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(FirmwareVersion.TYPE));
        FirmwareVersion state = (FirmwareVersion) command;
        assertTrue(Arrays.equals(state.getCarSDKVersion().getValue(), new int[]{1,15,33}));
        assertTrue(state.getCarSDKBuild().getValue().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().getValue().equals("v1.5-prod"));
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000002"));
        FirmwareVersion command = (FirmwareVersion) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getCarSDKBuild().getTimestamp() != null);
    }

    @Test public void build() {
        FirmwareVersion.Builder builder = new FirmwareVersion.Builder();

        builder.setCarSdkVersion(new IntArrayProperty(new int[]{1, 15, 33}));
        builder.setCarSDKBuild(new StringProperty("btstack-uart"));
        builder.setApplicationVersion(new StringProperty("v1.5-prod"));

        FirmwareVersion command = builder.build();
        assertTrue(command.equals(bytes));
    }

    @Test(expected = IllegalArgumentException.class) public void throwsIfInvalidFormat() {
        FirmwareVersion.Builder builder = new FirmwareVersion.Builder();
        builder.setCarSdkVersion(new IntArrayProperty(new int[]{1, 15}));
    }

    @Test public void get() {
        String waitingForBytes = "000300";
        assertTrue(new GetFirmwareVersion().equals(waitingForBytes));
    }
}