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
public class EngineTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "006901" +
                    "01000401000100"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Engine.State) command);
    }

    private void testState(Engine.State state) {
        assertTrue(state.getStatus().getValue() == OnOffState.OFF);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "006900";
        String commandBytes = ByteUtils.hexFromBytes(new Engine.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006901" +
                "01000401000100");
        Bytes commandBytes = new Engine.TurnEngineOnOff(OnOffState.OFF);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Engine.TurnEngineOnOff incoming =
                (Engine.TurnEngineOnOff) CommandResolver.resolve(waitingForBytes);
        assertTrue(incoming.getStatus().getValue() == OnOffState.OFF);
    }

    @Test public void build() {
        Engine.State.Builder builder = new Engine.State.Builder();
        builder.setStatus(new Property(OnOffState.OFF));
        Engine.State state = builder.build();
        testState(state);
    }
}