package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HoodTest extends BaseTest{
    Bytes bytes = new Bytes("006701" + "01000401000101");
    
    @Test
    public void state() {
        Command state = CommandResolver.resolve(bytes);
        testState((Hood.State) state);
    }

    private void testState(Hood.State state) {
        assertTrue(state.getPosition().getValue() == Hood.Position.OPEN);
        assertTrue(state.equals(bytes));
    }

    @Test public void build() {
        Hood.State.Builder builder = new Hood.State.Builder();
        builder.setPosition(new Property(Hood.Position.OPEN));
        Hood.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006700");
        Bytes bytes = new Hood.GetState();
        assertTrue(waitingForBytes.equals(bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof Hood.GetState);
    }
}
