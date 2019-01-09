package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetIgnitionState;
import com.highmobility.autoapi.IgnitionState;
import com.highmobility.autoapi.TurnIgnitionOnOff;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class EngineTest {
    Bytes bytes = new Bytes(
            "0035010100010102000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(IgnitionState.TYPE));
        IgnitionState state = (IgnitionState) command;
        assertTrue(state.isOn().getValue() == true);
        assertTrue(state.isAccessoriesIgnitionOn().getValue() == true);
    }

    @Test public void get() {
        String waitingForBytes = "003500";
        String commandBytes = ByteUtils.hexFromBytes(new GetIgnitionState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes("00351201000101");
        Bytes commandBytes = new TurnIgnitionOnOff(true);
        assertTrue(waitingForBytes.equals(commandBytes));

        TurnIgnitionOnOff incoming = (TurnIgnitionOnOff) CommandResolver.resolve(waitingForBytes);
        assertTrue(incoming.isOn() == true);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("003501");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((IgnitionState) state).isOn() == null);
    }

    @Test public void build() {
        IgnitionState.Builder builder = new IgnitionState.Builder();
        builder.setIsOn(new BooleanProperty(true));
        builder.setAccessoriesIgnition(new BooleanProperty(true));

        IgnitionState state = builder.build();

        assertTrue(state.equals(bytes));
        assertTrue(state.isOn().getValue() == true);
        assertTrue(state.isAccessoriesIgnitionOn().getValue() == true);
        assertTrue(state.getType() == IgnitionState.TYPE);
    }
}