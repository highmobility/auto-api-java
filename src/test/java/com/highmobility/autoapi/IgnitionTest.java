package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class IgnitionTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "003501" +
                    "01000401000101" +
                    "02000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Ignition.State) command);
    }

    private void testState(Ignition.State state) {
        assertTrue(state.getStatus().getValue() == OnOffState.ON);
        assertTrue(state.getAccessoriesStatus().getValue() == OnOffState.ON);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "003500";
        String commandBytes = ByteUtils.hexFromBytes(new Ignition.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "003501" +
                "01000401000101");
        Bytes commandBytes = new Ignition.TurnIgnitionOnOff(OnOffState.ON);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Ignition.TurnIgnitionOnOff incoming =
                (Ignition.TurnIgnitionOnOff) CommandResolver.resolve(waitingForBytes);
        assertTrue(incoming.getStatus().getValue() == OnOffState.ON);
    }

    @Test public void build() {
        Ignition.State.Builder builder = new Ignition.State.Builder();
        builder.setStatus(new Property(OnOffState.ON));
        builder.setAccessoriesStatus(new Property(OnOffState.ON));

        Ignition.State state = builder.build();
        testState(state);
    }
}