package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Failure;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.property.FailureReason;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FailureTest {
    Bytes bytes = new Bytes("000201" +
            "0100050100020021" +
            "02000401000100" +
            "03000401000101" +
            "04000C01000954727920616761696E"
    );

    @Test
    public void failure() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(Failure.TYPE));
        Failure failure = (Failure) command;
        assertTrue(failure.getFailedType().equals(GetTrunkState.TYPE));
        assertTrue(failure.getFailureReason() == FailureReason.UNAUTHORISED);
        assertTrue(failure.getFailureDescription().equals("Try again"));
    }

    @Test public void build() {
        Failure.Builder builder = new Failure.Builder();
        builder.setFailedType(GetTrunkState.TYPE);
        builder.setFailureReason(FailureReason.UNAUTHORISED);
        builder.setFailureDescription("Try again");
        Failure failure = builder.build();
        assertTrue(TestUtils.bytesTheSame(failure, bytes));
        assertTrue(failure.getFailedType() == GetTrunkState.TYPE);
        assertTrue(failure.getFailureReason() == FailureReason.UNAUTHORISED);
        assertTrue(failure.getType() == Failure.TYPE);
    }
}
