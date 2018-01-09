package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FirmwareVersion;
import com.highmobility.autoapi.GetFirmwareVersion;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class FirmwareVersionTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "000301010003010f2102000C6274737461636b2d7561727403000976312e352d70726f64");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(FirmwareVersion.TYPE));
        FirmwareVersion state = (FirmwareVersion) command;
        assertTrue(state.getCarSDKVersion().equals("1.15.33"));
        assertTrue(state.getCarSDKBuild().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().equals("v1.5-prod"));
    }

    @Test public void get() {
        String waitingForBytes = "000300";
        String commandBytes = Bytes.hexFromBytes(new GetFirmwareVersion().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}