package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FirmwareVersion;
import com.highmobility.autoapi.GetFirmwareVersion;
import com.highmobility.utils.ByteUtils;
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
        byte[] bytes = ByteUtils.bytesFromHex(
                "000301010003010f2102000C6274737461636b2d7561727403000976312e352d70726f64");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(FirmwareVersion.TYPE));
        FirmwareVersion state = (FirmwareVersion) command;
        assertTrue(state.getCarSDKVersion().equals("1.15.33"));
        assertTrue(state.getCarSDKBuild().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().equals("v1.5-prod"));
    }

    @Test public void build() {
        FirmwareVersion.Builder builder = new FirmwareVersion.Builder();

        builder.setCarSDKVersion(new int[]{1, 15, 33});
        builder.setCarSDKBuild("btstack-uart");
        builder.setApplicationVersion("v1.5-prod");

        FirmwareVersion command = builder.build();
        assertTrue(command.equals
                ("000301010003010f2102000C6274737461636b2d7561727403000976312e352d70726f64"));
        // 000301010007312E31352E333302000C6274737461636B2D7561727403000976312E352D70726F64
    }

    @Test public void get() {
        String waitingForBytes = "000300";
        assertTrue(new GetFirmwareVersion().equals(waitingForBytes));
    }
}