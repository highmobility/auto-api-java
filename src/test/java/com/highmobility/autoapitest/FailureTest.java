package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Failure;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.property.FailureReason;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FailureTest {
    @Test
    public void failure() {
        byte[] bytes = Bytes.bytesFromHex("00020101000300210002000101");

        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}
        if (command == null) fail();

        assertTrue(command.is(Failure.TYPE));
        Failure failure = (Failure) command;
        assertTrue(failure.getFailedType().equals(GetTrunkState.TYPE));
        assertTrue(failure.getFailureReason() == FailureReason.UNAUTHORIZED);
    }

    @Test public void build() {
        byte[] bytes = Bytes.bytesFromHex("00020101000300210002000101");
        Failure.Builder builder = new Failure.Builder();
        builder.setFailedType(GetTrunkState.TYPE);
        builder.setFailureReason(FailureReason.UNAUTHORIZED);
        Failure failure = builder.build();
        byte[] builtBytes = failure.getBytes();
        assertTrue(Arrays.equals(builtBytes, bytes));
        assertTrue(failure.getFailedType() == GetTrunkState.TYPE);
        assertTrue(failure.getFailureReason() == FailureReason.UNAUTHORIZED);
        assertTrue(failure.getType() == Failure.TYPE);
    }
}
