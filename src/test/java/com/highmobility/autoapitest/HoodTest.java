package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetHoodState;
import com.highmobility.autoapi.HoodState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HoodTest {
    Bytes bytes = new Bytes("006701" + "01000401000101");
    
    @Test
    public void state() {
        Command state = CommandResolver.resolve(bytes);
        testState((HoodState) state);
    }

    private void testState(HoodState state) {
        assertTrue(state.getPosition().getValue() == Position.OPEN);
        assertTrue(state.equals(bytes));
    }

    @Test public void build() {
        HoodState.Builder builder = new HoodState.Builder();
        builder.setPosition(new Property(Position.OPEN));
        HoodState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006700");
        Bytes bytes = new GetHoodState();
        assertTrue(waitingForBytes.equals(bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetHoodState);
    }
}
