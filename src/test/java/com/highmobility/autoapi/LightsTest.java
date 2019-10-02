package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.Light;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.LocationLongitudinal;
import com.highmobility.autoapi.value.ReadingLamp;
import com.highmobility.autoapi.value.RgbColour;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LightsTest extends BaseTest {
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
        LightsState command = (LightsState) CommandResolver.resolve(bytes);
        testState(command);
    }

    void testState(LightsState state) {
        assertTrue(state.getFrontExteriorLight().getValue() == LightsState.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM);
        assertTrue(state.getRearExteriorLight().getValue() == ActiveState.ACTIVE);

        assertTrue(state.getAmbientLightColour().getValue().getRed() == 255);
        assertTrue(state.getAmbientLightColour().getValue().getGreen() == 0);
        assertTrue(state.getAmbientLightColour().getValue().getBlue() == 0);

        assertTrue(state.getReverseLight().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getEmergencyBrakeLight().getValue() == ActiveState.INACTIVE);

        assertTrue(state.getFogLights().length == 2);
        assertTrue(state.getFogLight(LocationLongitudinal.FRONT).getValue().getActiveState() == ActiveState.INACTIVE);
        assertTrue(state.getFogLight(LocationLongitudinal.REAR).getValue().getActiveState() == ActiveState.ACTIVE);

        assertTrue(state.getReadingLamps().length == 2);
        assertTrue(state.getReadingLamp(Location.FRONT_LEFT).getValue().getActiveState() == ActiveState.INACTIVE);
        assertTrue(state.getReadingLamp(Location.FRONT_RIGHT).getValue().getActiveState() == ActiveState.ACTIVE);

        assertTrue(state.getInteriorLights().length == 2);
        assertTrue(state.getInteriorLight(LocationLongitudinal.FRONT).getValue().getActiveState() == ActiveState.INACTIVE);
        assertTrue(state.getInteriorLight(LocationLongitudinal.REAR).getValue().getActiveState() == ActiveState.INACTIVE);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        LightsState.Builder builder = new LightsState.Builder();

        builder.setFrontExteriorLight(new Property(LightsState.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM));
        builder.setRearExteriorLight(new Property(ActiveState.ACTIVE));

        RgbColour ambientColor = new RgbColour(255, 0, 0);
        builder.setAmbientLightColour(new Property(ambientColor));
        builder.setReverseLight(new Property(ActiveState.INACTIVE));
        builder.setEmergencyBrakeLight(new Property(ActiveState.INACTIVE));

        builder.addFogLight(new Property(new Light(LocationLongitudinal.FRONT,
                ActiveState.INACTIVE)));
        builder.addFogLight(new Property(new Light(LocationLongitudinal.REAR, ActiveState.ACTIVE)));

        builder.addReadingLamp(new Property(new ReadingLamp(Location.FRONT_LEFT,
                ActiveState.INACTIVE)));
        builder.addReadingLamp(new Property(new ReadingLamp(Location.FRONT_RIGHT,
                ActiveState.ACTIVE)));

        builder.addInteriorLight(new Property(new Light(LocationLongitudinal.FRONT,
                ActiveState.INACTIVE)));
        builder.addInteriorLight(new Property(new Light(LocationLongitudinal.REAR,
                ActiveState.INACTIVE)));

        LightsState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        String waitingForBytes = "003600";
        String commandBytes = ByteUtils.hexFromBytes(new GetLightsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("003601" +
                "01000401000102" +
                "02000401000100" +
                "040006010003ff0000" +
                "0700050100020000" +
                "0700050100020101" +
                "0800050100020000" +
                "0800050100020101" +
                "0900050100020000" +
                "0900050100020100");

        Light[] fogLights = new Light[2];
        ReadingLamp[] readingLamps = new ReadingLamp[2];
        Light[] interiorLamps = new Light[2];

        fogLights[0] = new Light(LocationLongitudinal.FRONT, ActiveState.INACTIVE);
        fogLights[1] = new Light(LocationLongitudinal.REAR, ActiveState.ACTIVE);

        readingLamps[0] = new ReadingLamp(Location.FRONT_LEFT, ActiveState.INACTIVE);
        readingLamps[1] = new ReadingLamp(Location.FRONT_RIGHT, ActiveState.ACTIVE);

        interiorLamps[0] = new Light(LocationLongitudinal.FRONT, ActiveState.INACTIVE);
        interiorLamps[1] = new Light(LocationLongitudinal.REAR, ActiveState.INACTIVE);

        Bytes bytes = new ControlLights(
                LightsState.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM,
                ActiveState.INACTIVE,
                new RgbColour(255, 0, 0),
                fogLights,
                readingLamps,
                interiorLamps);

        assertTrue(bytesTheSame(bytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ControlLights command = (ControlLights) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getFrontExteriorLight().getValue() == LightsState.FrontExteriorLight.ACTIVE_WITH_FULL_BEAM);

        assertTrue(command.getRearExteriorLight().getValue() == ActiveState.INACTIVE);
        assertTrue(command.getAmbientLightColour().getValue().getRed() == 255);
        assertTrue(command.getAmbientLightColour().getValue().getGreen() == 0);
        assertTrue(command.getAmbientLightColour().getValue().getBlue() == 0);

        assertTrue(command.getFogLights().length == 2);
        for (Property<Light> fogLight : command.getFogLights()) {
            if (fogLight.getValue().getLocationLongitudinal() == LocationLongitudinal.FRONT) {
                assertTrue(fogLight.getValue().getActiveState() == ActiveState.INACTIVE);
            }
            if (fogLight.getValue().getLocationLongitudinal() == LocationLongitudinal.REAR) {
                assertTrue(fogLight.getValue().getActiveState() == ActiveState.ACTIVE);
            }
        }

        assertTrue(command.getReadingLamps().length == 2);

        for (Property<ReadingLamp> readingLamp : command.getReadingLamps()) {
            if (readingLamp.getValue().getLocation() == Location.FRONT_LEFT) {
                assertTrue(readingLamp.getValue().getActiveState() == ActiveState.INACTIVE);
            }
            if (readingLamp.getValue().getLocation() == Location.FRONT_RIGHT) {
                assertTrue(readingLamp.getValue().getActiveState() == ActiveState.ACTIVE);
            }
        }

        assertTrue(command.getInteriorLights().length == 2);

        for (Property<Light> interiorLight : command.getInteriorLights()) {
            if (interiorLight.getValue().getLocationLongitudinal() == LocationLongitudinal.FRONT) {
                assertTrue(interiorLight.getValue().getActiveState() == ActiveState.INACTIVE);
            }
            if (interiorLight.getValue().getLocationLongitudinal() == LocationLongitudinal.REAR) {
                assertTrue(interiorLight.getValue().getActiveState() == ActiveState.INACTIVE);
            }
        }
    }
}
