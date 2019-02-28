package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetKeyfobPosition;
import com.highmobility.autoapi.KeyFobPosition;
import com.highmobility.autoapi.property.KeyFobPositionProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class KeyFobPositionTest {
    Bytes bytes = new Bytes("00480101000401000105");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(KeyFobPosition.TYPE));
        KeyFobPosition state = (KeyFobPosition) command;
        assertTrue(state.getKeyFobPosition().getValue() == KeyFobPositionProperty.Value.INSIDE_CAR);
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000001"));
        KeyFobPosition command = (KeyFobPosition) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getKeyFobPosition().getTimestamp() != null);
    }

    @Test public void get() {
        String waitingForBytes = "004800";
        String commandBytes = ByteUtils.hexFromBytes(new GetKeyfobPosition().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((KeyFobPosition) state).getKeyFobPosition().getValue() == null);
    }
}