package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class LightConditionsTest extends BaseTest {
    Bytes bytes = new Bytes(
            "005401" +
                    "01000701000447D8CC00" +
                    "0200070100043E800000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == LightConditions.State.class);
        LightConditions.State state = (LightConditions.State) command;
        testState(state);
    }

    private void testState(LightConditions.State state) {
        assertTrue(state.getOutsideLight().getValue() == 111000f);
        assertTrue(state.getInsideLight().getValue() == .25f);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        LightConditions.State.Builder builder = new LightConditions.State.Builder();
        builder.setOutsideLight(new Property(111000f));
        builder.setInsideLight(new Property(.25f));
        LightConditions.State command = builder.build();
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = "005400";
        String commandBytes = ByteUtils.hexFromBytes(new LightConditions.GetLightConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}