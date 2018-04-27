package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlLights;
import com.highmobility.autoapi.GetLightsState;
import com.highmobility.autoapi.LightsState;
import com.highmobility.autoapi.property.FrontExteriorLightState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LightsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "003601" +
                        "01000102" +
                        "02000101" +
                        "03000100" +
                        "040003ff0000");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(LightsState.TYPE));

        LightsState state = (LightsState) command;
        assertTrue(command.is(LightsState.TYPE));
        assertTrue(state.getFrontExteriorLightState() == FrontExteriorLightState
                .ACTIVE_WITH_FULL_BEAM);
        assertTrue(state.isRearExteriorLightActive() == true);
        assertTrue(state.isInteriorLightActive() == false);

        assertTrue(state.getAmbientColor()[0] == 0xFF);
        assertTrue(state.getAmbientColor()[1] == 0);
        assertTrue(state.getAmbientColor()[2] == 0);
    }

    @Test public void get() {
        String waitingForBytes = "003600";
        String commandBytes = Bytes.hexFromBytes(new GetLightsState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void control() {
        byte[] expecting = Bytes.bytesFromHex("003602" +
                "01000102" +
                "02000100" +
                "03000100" +
                "040003ff0000");

        byte[] bytes = new ControlLights(
                FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM
                , false, false, new int[]{255, 0, 0, 255}
        ).getBytes();

        assertTrue(Arrays.equals(expecting, bytes));

        ControlLights command = (ControlLights) CommandResolver.resolve(expecting);
        assertTrue(command.getFrontExteriorLightState() == FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM);
        assertTrue(command.getRearExteriorLightActive() == false);
        assertTrue(command.getInteriorLightActive() == false);
        assertTrue(Arrays.equals(command.getAmbientColor(), new int[]{255, 0, 0, 255}));

    }

    @Test public void build() {
        byte[] expectedBytes = Bytes.bytesFromHex(
                "003601" +
                        "01000102" +
                        "02000101" +
                        "03000100" +
                        "040003ff0000");

        LightsState.Builder builder = new LightsState.Builder();

        builder.setFrontExteriorLightState(FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM);
        builder.setRearExteriorLightActive(true);
        builder.setInteriorLightActive(false);

        int[] ambientColor = new int[]{0xFF, 0, 0};
        builder.setAmbientColor(ambientColor);

        byte[] actualBytes = builder.build().getBytes();
        assertTrue(Arrays.equals(actualBytes, expectedBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("003601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((LightsState) state).getAmbientColor() == null);
    }
}
