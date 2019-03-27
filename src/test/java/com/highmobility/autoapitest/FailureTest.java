package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Failure;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.FailureReason;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        testState((Failure) command);
    }

    private void testState(Failure state) {
        assertTrue(state.getFailedType().equals(GetTrunkState.TYPE));
        assertTrue(state.getFailureReason().getValue() == FailureReason.UNAUTHORISED);
        assertTrue(state.getFailureDescription().getValue().equals("Try again"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Failure.Builder builder = new Failure.Builder();
        builder.setFailedIdentifier(new Property(GetTrunkState.TYPE.getIdentifier()));
        builder.setFailedTypeByte(new Property(GetTrunkState.TYPE.getType()));
        builder.setFailureReason(new Property(FailureReason.UNAUTHORISED));
        builder.setFailureDescription(new Property("Try again"));

        Failure failure = builder.build();
        testState(failure);
    }
}
