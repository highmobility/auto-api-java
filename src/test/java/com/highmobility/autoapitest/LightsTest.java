package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlLights;
import com.highmobility.autoapi.GetLightsState;
import com.highmobility.autoapi.LightsState;
import com.highmobility.autoapi.property.lights.FogLight;
import com.highmobility.autoapi.property.lights.FrontExteriorLightState;
import com.highmobility.autoapi.property.lights.InteriorLamp;
import com.highmobility.autoapi.property.lights.LightLocation;
import com.highmobility.autoapi.property.lights.ReadingLamp;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class LightsTest {
    Bytes bytes = new Bytes(
            "003601" +
                    "01000102" +
                    "02000101" +
                    "040003FF0000" +
                    "05000101" +
                    "06000101" +
                    "0700020000" +
                    "0700020101" +
                    "0800020000" +
                    "0800020101" +
                    "0900020000" +
                    "0900020100");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(LightsState.TYPE));

        LightsState state = (LightsState) command;
        assertTrue(command.is(LightsState.TYPE));
        assertTrue(state.getFrontExteriorLightState() == FrontExteriorLightState.ACTIVE_FULL_BEAM);
        assertTrue(state.isRearExteriorLightActive() == true);

        assertTrue(state.getAmbientColor()[0] == 0xFF);
        assertTrue(state.getAmbientColor()[1] == 0);
        assertTrue(state.getAmbientColor()[2] == 0);

        assertTrue(state.isReverseLightActive() == true);
        assertTrue(state.isEmergencyBrakeLightActive() == true);

        assertTrue(state.getFogLights().length == 2);
        assertTrue(state.getFogLight(LightLocation.FRONT).isActive() == false);
        assertTrue(state.getFogLight(LightLocation.REAR).isActive() == true);

        assertTrue(state.getReadingLamps().length == 2);
        assertTrue(state.getReadingLamp(Location.FRONT_LEFT).isActive() == false);
        assertTrue(state.getReadingLamp(Location.FRONT_RIGHT).isActive() == true);

        assertTrue(state.getInteriorLamps().length == 2);
        assertTrue(state.getInteriorLamp(LightLocation.FRONT).isActive() == false);
        assertTrue(state.getInteriorLamp(LightLocation.REAR).isActive() == false);
    }

    @Test public void build() {
        LightsState.Builder builder = new LightsState.Builder();

        builder.setFrontExteriorLightState(FrontExteriorLightState.ACTIVE_FULL_BEAM);
        builder.setRearExteriorLightActive(true);

        int[] ambientColor = new int[]{0xFF, 0, 0};
        builder.setAmbientColor(ambientColor);
        builder.setReverseLightActive(true);
        builder.setEmergencyBrakeLightActive(true);

        builder.addFogLight(new FogLight(LightLocation.FRONT, false));
        builder.addFogLight(new FogLight(LightLocation.REAR, true));

        builder.addReadingLamp(new ReadingLamp(Location.FRONT_LEFT, false));
        builder.addReadingLamp(new ReadingLamp(Location.FRONT_RIGHT, true));

        builder.addInteriorLamp(new InteriorLamp(LightLocation.FRONT, false));
        builder.addInteriorLamp(new InteriorLamp(LightLocation.REAR, false));

        Bytes actualBytes = builder.build();
        assertTrue(actualBytes.equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "003600";
        String commandBytes = ByteUtils.hexFromBytes(new GetLightsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("003612010001020200010003000100040003ff0000");

        Bytes bytes = new ControlLights(FrontExteriorLightState.ACTIVE_FULL_BEAM, false,
                false, new int[]{255, 0, 0, 255});

        assertTrue(TestUtils.bytesTheSame(waitingForBytes, bytes));

        ControlLights command = (ControlLights) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getFrontExteriorLightState() == FrontExteriorLightState
                .ACTIVE_FULL_BEAM);
        assertTrue(command.getRearExteriorLightActive() == false);
        assertTrue(command.getInteriorLightActive() == false);
        assertTrue(Arrays.equals(command.getAmbientColor(), new int[]{255, 0, 0, 255}));
    }

    @Test public void state0Properties() {
        Bytes waitingForBytes = new Bytes("003601");
        Command state = CommandResolver.resolve(waitingForBytes);
        assertTrue(((LightsState) state).getAmbientColor() == null);
    }
}
