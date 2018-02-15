package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetIgnitionState;
import com.highmobility.autoapi.GetKeyfobPosition;
import com.highmobility.autoapi.IgnitionState;
import com.highmobility.autoapi.KeyfobPosition;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class KeyfobPositionTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00480101000105");



        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(KeyfobPosition.TYPE));
        KeyfobPosition state = (KeyfobPosition) command;
        assertTrue(state.getKeyfobPosition() == com.highmobility.autoapi.property.KeyfobPosition.INSIDE_CAR);
    }

    @Test public void get() {
        String waitingForBytes = "004800";
        String commandBytes = Bytes.hexFromBytes(new GetKeyfobPosition().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}