package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ConnectionState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MobileTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "006601" +
                    "01000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        Mobile.State state = (Mobile.State) command;
        testState(state);
    }

    private void testState(Mobile.State state) {
        assertTrue(state.getConnection().getValue() == ConnectionState.CONNECTED);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "006600";
        String commandBytes = ByteUtils.hexFromBytes(new Mobile.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        Mobile.State.Builder builder = new Mobile.State.Builder();
        builder.setConnection(new Property(ConnectionState.CONNECTED));
        testState(builder.build());
    }
}