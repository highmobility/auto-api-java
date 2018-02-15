package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.OpenCloseTrunk;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0021010100010002000101");

        Command command = CommandResolver.resolve(bytes);
        if (command == null) fail("init failed");

        assertTrue(command.getClass() == TrunkState.class);
        TrunkState state = (TrunkState) command;
        assertTrue(state.getLockState() == TrunkLockState.UNLOCKED);
        assertTrue(state.getPosition() == TrunkPosition.OPEN);
    }

    @Test public void get() throws CommandParseException {
        String waitingForBytes = "002100";
        String commandBytes = Bytes.hexFromBytes(new GetTrunkState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(Bytes.bytesFromHex(waitingForBytes));
        assertTrue(command instanceof GetTrunkState);
    }

    @Test public void openClose() throws CommandParseException {
        String waitingForBytes = "0021020100010002000101";
        String commandBytes = Bytes.hexFromBytes(new OpenCloseTrunk(TrunkLockState.UNLOCKED,
                TrunkPosition.OPEN).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(Bytes.bytesFromHex(waitingForBytes));
        assertTrue(command instanceof OpenCloseTrunk);

        OpenCloseTrunk state = (OpenCloseTrunk)command;
        assertTrue(state.getState() == TrunkLockState.UNLOCKED);
        assertTrue(state.getPosition() == TrunkPosition.OPEN);
    }
}