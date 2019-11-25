package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HoodTest extends BaseTest{
    Bytes bytes = new Bytes(COMMAND_HEADER + "006701" + "01000401000101");
    
    @Test
    public void state() {
        Command state = CommandResolver.resolve(bytes);
        testState((HoodState) state);
    }

    private void testState(HoodState state) {
        assertTrue(state.getPosition().getValue() == HoodState.Position.OPEN);
        assertTrue(state.equals(bytes));
    }

    @Test public void build() {
        HoodState.Builder builder = new HoodState.Builder();
        builder.setPosition(new Property(HoodState.Position.OPEN));
        HoodState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006700");
        Bytes bytes = new GetHoodState();
        assertTrue(waitingForBytes.equals(bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetHoodState);
    }
}
