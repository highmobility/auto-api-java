package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FirmwareVersion;
import com.highmobility.autoapi.GetFirmwareVersion;
import com.highmobility.autoapi.property.Property<int[]>;

import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class FirmwareVersionTest {
    Bytes bytes = new Bytes(
            "000301" +
                    "010006010003010F21" +
                    "02000F01000C6274737461636B2D75617274" +
                    "03000C01000976312E352D70726F64"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((FirmwareVersion) command);
    }

    void testState(FirmwareVersion state) {
        assertTrue(Arrays.equals(state.getCarSDKVersion().getValue(), new int[]{1, 15, 33}));
        assertTrue(state.getCarSDKBuild().getValue().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().getValue().equals("v1.5-prod"));
        assertTrue(state.equals(bytes));
    }

    @Test public void build() {
        FirmwareVersion.Builder builder = new FirmwareVersion.Builder();

        builder.setCarSdkVersion(new Property<int[]>(new int[]{1, 15, 33}));
        builder.setCarSDKBuild(new PropertyString("btstack-uart"));
        builder.setApplicationVersion(new PropertyString("v1.5-prod"));

        FirmwareVersion command = builder.build();
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = "000300";
        assertTrue(new GetFirmwareVersion().equals(waitingForBytes));
    }
}