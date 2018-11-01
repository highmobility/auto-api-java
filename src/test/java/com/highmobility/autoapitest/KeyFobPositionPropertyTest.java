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
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class KeyFobPositionPropertyTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes("00480101000105");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(KeyFobPosition.TYPE));
        KeyFobPosition state = (KeyFobPosition) command;
        assertTrue(state.getKeyFobPosition() == KeyFobPositionProperty
                .INSIDE_CAR);
    }

    @Test public void get() {
        String waitingForBytes = "004800";
        String commandBytes = ByteUtils.hexFromBytes(new GetKeyfobPosition().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((KeyFobPosition)state).getKeyFobPosition() == null);
    }
}