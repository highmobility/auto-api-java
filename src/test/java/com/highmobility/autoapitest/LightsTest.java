package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlLights;
import com.highmobility.autoapi.GetLightsState;
import com.highmobility.autoapi.LightsState;
import com.highmobility.autoapi.property.IntegerArrayProperty;
import com.highmobility.autoapi.property.FogLight;
import com.highmobility.autoapi.property.ObjectProperty;
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
        testState(state);
    }

    void testState(LightsState state) {
        assertTrue(state.getFrontExteriorLightState().getValue() == FrontExteriorLightState.Value.ACTIVE_FULL_BEAM);
        assertTrue(state.isRearExteriorLightActive().getValue() == true);

        assertTrue(state.getAmbientColor().getValue()[0] == 0xFF);
        assertTrue(state.getAmbientColor().getValue()[1] == 0);
        assertTrue(state.getAmbientColor().getValue()[2] == 0);

        assertTrue(state.isReverseLightActive().getValue() == true);
        assertTrue(state.isEmergencyBrakeLightActive().getValue() == true);

        assertTrue(state.getFogLights().length == 2);
        assertTrue(state.getFogLight(LightLocation.FRONT).getValue().isActive() == false);
        assertTrue(state.getFogLight(LightLocation.REAR).getValue().isActive() == true);

        assertTrue(state.getReadingLamps().length == 2);
        assertTrue(state.getReadingLamp(Location.FRONT_LEFT).getValue().isActive() == false);
        assertTrue(state.getReadingLamp(Location.FRONT_RIGHT).getValue().isActive() == true);

        assertTrue(state.getInteriorLamps().length == 2);
        assertTrue(state.getInteriorLamp(LightLocation.FRONT).getValue().isActive() == false);
        assertTrue(state.getInteriorLamp(LightLocation.REAR).getValue().isActive() == false);
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000002"));
        LightsState command = (LightsState) CommandResolver.resolve(timestampBytes);
        assertTrue(command.isRearExteriorLightActive().getTimestamp() != null);
    }

    @Test public void build() {
        LightsState.Builder builder = new LightsState.Builder();

        builder.setFrontExteriorLightState(new FrontExteriorLightState(FrontExteriorLightState.Value.ACTIVE_FULL_BEAM));
        builder.setRearExteriorLightActive(new ObjectProperty<>(true));

        int[] ambientColor = new int[]{0xFF, 0, 0};
        builder.setAmbientColor(new IntegerArrayProperty(ambientColor));
        builder.setReverseLightActive(new ObjectProperty<>(true));
        builder.setEmergencyBrakeLightActive(new ObjectProperty<>(true));

        builder.addFogLight(new FogLight(LightLocation.FRONT, false));
        builder.addFogLight(new FogLight(LightLocation.REAR, true));

        builder.addReadingLamp(new ReadingLamp(Location.FRONT_LEFT, false));
        builder.addReadingLamp(new ReadingLamp(Location.FRONT_RIGHT, true));

        builder.addInteriorLamp(new InteriorLamp(LightLocation.FRONT, false));
        builder.addInteriorLamp(new InteriorLamp(LightLocation.REAR, false));

        LightsState state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }

    @Test public void get() {
        String waitingForBytes = "003600";
        String commandBytes = ByteUtils.hexFromBytes(new GetLightsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("0036120100010202000100040003ff0000" +
                "0700020000" +
                "0700020101" +
                "0800020000" +
                "0800020101" +
                "0900020000" +
                "0900020100");

        FogLight[] fogLights = new FogLight[2];
        ReadingLamp[] readingLamps = new ReadingLamp[2];
        InteriorLamp[] interiorLamps = new InteriorLamp[2];

        fogLights[0] = new FogLight(LightLocation.FRONT, false);
        fogLights[1] = new FogLight(LightLocation.REAR, true);

        readingLamps[0] = new ReadingLamp(Location.FRONT_LEFT, false);
        readingLamps[1] = new ReadingLamp(Location.FRONT_RIGHT, true);

        interiorLamps[0] = new InteriorLamp(LightLocation.FRONT, false);
        interiorLamps[1] = new InteriorLamp(LightLocation.REAR, false);

        Bytes bytes = new ControlLights(
                FrontExteriorLightState.Value.ACTIVE_FULL_BEAM,
                false,
                new int[]{255, 0, 0}, fogLights, readingLamps, interiorLamps);

        assertTrue(TestUtils.bytesTheSame(bytes, waitingForBytes));

        ControlLights command = (ControlLights) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getFrontExteriorLightState() == FrontExteriorLightState.Value
                .ACTIVE_FULL_BEAM);
        assertTrue(command.getRearExteriorLightActive() == false);
        assertTrue(Arrays.equals(command.getAmbientColor(), new int[]{255, 0, 0}));

        assertTrue(command.getFogLights().length == 2);
        assertTrue(command.getFogLight(LightLocation.FRONT).getValue().isActive() == false);
        assertTrue(command.getFogLight(LightLocation.REAR).getValue().isActive() == true);

        assertTrue(command.getReadingLamps().length == 2);
        assertTrue(command.getReadingLamp(Location.FRONT_LEFT).getValue().isActive() == false);
        assertTrue(command.getReadingLamp(Location.FRONT_RIGHT).getValue().isActive() == true);

        assertTrue(command.getInteriorLamps().length == 2);
        assertTrue(command.getInteriorLamp(LightLocation.FRONT).getValue().isActive() == false);
        assertTrue(command.getInteriorLamp(LightLocation.REAR).getValue().isActive() == false);
    }

    @Test public void state0Properties() {
        Bytes waitingForBytes = new Bytes("003601");
        Command state = CommandResolver.resolve(waitingForBytes);
        assertTrue(((LightsState) state).getAmbientColor().getValue() == null);
    }
}
