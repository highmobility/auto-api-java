package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetMobileState;
import com.highmobility.autoapi.MobileState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MobileTest {
    Bytes bytes = new Bytes(
            "006601" +
                    "01000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        MobileState state = (MobileState) command;
        testState(state);
    }

    private void testState(MobileState state) {
        assertTrue(state.isConnected().getValue() == true);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "006600";
        String commandBytes = ByteUtils.hexFromBytes(new GetMobileState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        MobileState.Builder builder = new MobileState.Builder();
        builder.setConnected(new Property(true));
        testState(builder.build());
    }
}