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
        assertTrue(state.getStatus().getValue() == OnOffState.ON);
        assertTrue(state.getAccessoriesStatus().getValue() == OnOffState.ON);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "003500";
        String commandBytes = ByteUtils.hexFromBytes(new GetIgnitionState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        Bytes waitingForBytes = new Bytes("003501" +
                "01000401000101");
        Bytes commandBytes = new TurnIgnitionOnOff(OnOffState.ON);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        TurnIgnitionOnOff incoming = (TurnIgnitionOnOff) CommandResolver.resolve(waitingForBytes);
        assertTrue(incoming.getStatus().getValue() == OnOffState.ON);
    }

    @Test public void build() {
        IgnitionState.Builder builder = new IgnitionState.Builder();
        builder.setStatus(new Property(OnOffState.ON));
        builder.setAccessoriesStatus(new Property(OnOffState.ON));

        IgnitionState state = builder.build();
        testState(state);
    }
}