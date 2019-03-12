package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetKeyFobPosition;
import com.highmobility.autoapi.KeyFobPosition;
import com.highmobility.autoapi.value.KeyFobPositionValue;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class KeyFobPositionTest {
    Bytes bytes = new Bytes("004801" +
            "01000401000105");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(KeyFobPosition.TYPE));
        KeyFobPosition state = (KeyFobPosition) command;
        assertTrue(state.getKeyFobPosition().getValue() == KeyFobPositionValue.INSIDE_CAR);
    }

    @Test public void get() {
        String waitingForBytes = "004800";
        String commandBytes = ByteUtils.hexFromBytes(new GetKeyFobPosition().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((KeyFobPosition) state).getKeyFobPosition().getValue() == null);
    }
}