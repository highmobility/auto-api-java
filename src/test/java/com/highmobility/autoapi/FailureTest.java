package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FailureTest extends BaseTest {
    Bytes bytes = new Bytes("000201" +
            "0100050100020021" +
            "02000401000101" + // set
            "03000401000101" + // reason
            "04000C01000954727920616761696E" + // description
            "05000401000101" // property ids
    );

    @Test
    public void failure() {
        Command command = CommandResolver.resolve(bytes);
        testState((FailureMessageState) command);
    }

    private void testState(FailureMessageState state) {
        assertTrue(state.getFailedMessageType().getValue() == Type.SET);
        assertTrue(state.getFailureReason().getValue() == FailureMessageState.FailureReason.UNAUTHORISED);
        assertTrue(state.getFailureDescription().getValue().equals("Try again"));
        assertTrue(state.getFailedPropertyIDs().getValue().equals("01"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        FailureMessageState.Builder builder = new FailureMessageState.Builder();
        builder.setFailedMessageID(new Property(Identifier.TRUNK));

        builder.setFailedMessageType(new Property(Type.SET));

        builder.setFailureReason(new Property(FailureMessageState.FailureReason.UNAUTHORISED));
        builder.setFailureDescription(new Property("Try again"));

        builder.setFailedPropertyIDs(new Property(new Bytes("01")));

        FailureMessageState failure = builder.build();
        testState(failure);
    }
}
