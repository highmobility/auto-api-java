package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.OpenCloseTrunk;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "0021010100010002000101");

        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}
        if (command == null) fail("init failed");

        assertTrue(command.getClass() == TrunkState.class);
        TrunkState state = (TrunkState) command;
        assertTrue(state.getLockState() == TrunkLockState.UNLOCKED);
        assertTrue(state.getPosition() == TrunkPosition.OPEN);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("002100");
        Bytes commandBytes = new GetTrunkState();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetTrunkState);
    }

    @Test public void openClose() {
        Bytes waitingForBytes = new Bytes("0021020100010002000101");
        String commandBytes = ByteUtils.hexFromBytes(new OpenCloseTrunk(TrunkLockState.UNLOCKED,
                TrunkPosition.OPEN).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof OpenCloseTrunk);

        OpenCloseTrunk state = (OpenCloseTrunk)command;
        assertTrue(state.getState() == TrunkLockState.UNLOCKED);
        assertTrue(state.getPosition() == TrunkPosition.OPEN);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002101");
        TrunkState state = (TrunkState) CommandResolver.resolve(bytes);
        assertTrue(state.getLockState() == null);
    }
}