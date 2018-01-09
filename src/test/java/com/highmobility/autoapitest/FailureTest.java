package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Failure;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.utils.Bytes;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FailureTest {
    @Test
    public void failure() {
        byte[] bytes = Bytes.bytesFromHex(
                "00020101000300210002000101"
        );

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            Assert.fail("init failed");
        }

        assertTrue(command.is(Failure.TYPE));
        Failure failure = (Failure) command;
        assertTrue(failure.getFailedType().equals(GetTrunkState.TYPE));
        assertTrue(failure.getFailureReason() == Failure.Reason.UNAUTHORIZED);
    }
}
