package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetIgnitionState;
import com.highmobility.autoapi.IgnitionState;
import com.highmobility.autoapi.TurnIgnitionOnOff;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class EngineTest {
    Bytes bytes = new Bytes(
            "003501" +
                    "01000401000101" +
                    "02000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((IgnitionState) command);
    }

    private void testState(IgnitionState state) {
        assertTrue(state.isOn().getValue() == true);
        assertTrue(state.isAccessoriesIgnitionOn().getValue() == true);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "003500";
        String commandBytes = ByteUtils.hexFromBytes(new GetIgnitionState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes("00351201000401000101");
        Bytes commandBytes = new TurnIgnitionOnOff(true);
        assertTrue(waitingForBytes.equals(commandBytes));

        TurnIgnitionOnOff incoming = (TurnIgnitionOnOff) CommandResolver.resolve(waitingForBytes);
        assertTrue(incoming.isOn().getValue() == true);
    }

    @Test public void build() {
        IgnitionState.Builder builder = new IgnitionState.Builder();
        builder.setIsOn(new Property(true));
        builder.setAccessoriesIgnition(new Property(true));

        IgnitionState state = builder.build();
        testState(state);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(TurnIgnitionOnOff.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}