package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlLights;
import com.highmobility.autoapi.GetLightsState;
import com.highmobility.autoapi.LightsState;
import com.highmobility.autoapi.value.Color;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.lights.FogLight;
import com.highmobility.autoapi.value.lights.FrontExteriorLightState;
import com.highmobility.autoapi.value.lights.InteriorLamp;
import com.highmobility.autoapi.value.lights.LightLocation;
import com.highmobility.autoapi.value.lights.ReadingLamp;
import com.highmobility.autoapi.value.Location;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LightsTest {
    Bytes bytes = new Bytes(
            "003601" +
                    "01000401000102" +
                    "02000401000101" +
                    "040006010003FF0000" +
                    "05000401000100" +
                    "06000401000100" +
                    "0700050100020000" +
                    "0700050100020101" +
                    "0800050100020000" +
                    "0800050100020101" +
                    "0900050100020000" +
                    "0900050100020100"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(LightsState.TYPE));

        LightsState state = (LightsState) command;
        testState(state);
    }

    void testState(LightsState state) {
        assertTrue(state.getFrontExteriorLightState().getValue() == FrontExteriorLightState.ACTIVE_FULL_BEAM);
        assertTrue(state.isRearExteriorLightActive().getValue() == true);

        assertTrue(state.getAmbientColor().getValue().getRed() == 1f);
        assertTrue(state.getAmbientColor().getValue().getGreen() == 0);
        assertTrue(state.getAmbientColor().getValue().getBlue() == 0);

        assertTrue(state.isReverseLightActive().getValue() == false);
        assertTrue(state.isEmergencyBrakeLightActive().getValue() == false);

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

    @Test public void build() {
        LightsState.Builder builder = new LightsState.Builder();

        builder.setFrontExteriorLightState(new Property(FrontExteriorLightState.ACTIVE_FULL_BEAM));
        builder.setRearExteriorLightActive(new Property(true));

        Color ambientColor = new Color(new float[]{1f, 0, 0});
        builder.setAmbientColor(new Property(ambientColor));
        builder.setReverseLightActive(new Property(false));
        builder.setEmergencyBrakeLightActive(new Property(false));

        builder.addFogLight(new Property(new FogLight(LightLocation.FRONT, false)));
        builder.addFogLight(new Property(new FogLight(LightLocation.REAR, true)));

        builder.addReadingLamp(new Property(new ReadingLamp(Location.FRONT_LEFT, false)));
        builder.addReadingLamp(new Property(new ReadingLamp(Location.FRONT_RIGHT, true)));

        builder.addInteriorLamp(new Property(new InteriorLamp(LightLocation.FRONT, false)));
        builder.addInteriorLamp(new Property(new InteriorLamp(LightLocation.REAR, false)));

        LightsState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }

    @Test public void get() {
        String waitingForBytes = "003600";
        String commandBytes = ByteUtils.hexFromBytes(new GetLightsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("003612" +
                "01000401000102" +
                "02000401000100" +
                "040006010003ff0000" +
                "0700050100020000" +
                "0700050100020101" +
                "0800050100020000" +
                "0800050100020101" +
                "0900050100020000" +
                "0900050100020100");

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
                FrontExteriorLightState.ACTIVE_FULL_BEAM,
                false,
                new Color(new float[]{1f, 0f, 0f}), fogLights, readingLamps, interiorLamps);

        assertTrue(TestUtils.bytesTheSame(bytes, waitingForBytes));

        ControlLights command = (ControlLights) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getFrontExteriorLightState().getValue() == FrontExteriorLightState.ACTIVE_FULL_BEAM);

        assertTrue(command.getRearExteriorLightActive().getValue() == false);
        assertTrue(command.getAmbientColor().getValue().getRed() == 1f);
        assertTrue(command.getAmbientColor().getValue().getGreen() == 0f);
        assertTrue(command.getAmbientColor().getValue().getBlue() == 0f);

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
