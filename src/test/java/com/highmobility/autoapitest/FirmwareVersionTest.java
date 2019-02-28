package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FirmwareVersion;
import com.highmobility.autoapi.GetFirmwareVersion;
import com.highmobility.autoapi.property.ObjectPropertyIntegerArray;
import com.highmobility.autoapi.property.ObjectPropertyString;
import com.highmobility.value.Bytes;

import org.junit.Test;

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

    // TODO: 2019-02-27 test state
    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        testState((FirmwareVersion) command);
    }

    void testState(FirmwareVersion state) {
        assertTrue(state.getCarSDKVersion().equals("1.15.33"));
        assertTrue(state.getCarSDKBuild().equals("btstack-uart"));
        assertTrue(state.getApplicationVersion().equals("v1.5-prod"));

    }

    @Test public void build() {
        FirmwareVersion.Builder builder = new FirmwareVersion.Builder();

        builder.setCarSdkVersion(new ObjectPropertyIntegerArray(new int[]{1, 15, 33}));
        builder.setCarSDKBuild(new ObjectPropertyString("btstack-uart"));
        builder.setApplicationVersion(new ObjectPropertyString("v1.5-prod"));

        FirmwareVersion command = builder.build();
        assertTrue(command.equals(bytes));
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = "000300";
        assertTrue(new GetFirmwareVersion().equals(waitingForBytes));
    }
}