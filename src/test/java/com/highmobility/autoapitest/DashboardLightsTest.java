package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DashboardLights;
import com.highmobility.autoapi.GetDashboardLights;
import com.highmobility.autoapi.value.DashboardLight;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DashboardLightsTest {
    Bytes bytes = new Bytes("006101" +
            "0100050100020000" +
            "0100050100020201" +
            "0100050100020F03" +
            "0100050100021502"
    );

    @Test
    public void state() {
        DashboardLights state = (DashboardLights) CommandResolver.resolve(bytes);
        testState(state);
    }

    private void testState(DashboardLights state) {
        assertTrue(state.getLights().length == 4);

        assertTrue(state.getLight(DashboardLight.Type.HIGH_BEAM_MAIN_BEAM).getValue().getState() ==
                DashboardLight.State.INACTIVE);
        assertTrue(state.getLight(DashboardLight.Type.HAZARD_WARNING).getValue().getState() ==
                DashboardLight.State.INFO);
        assertTrue(state.getLight(DashboardLight.Type.TRANSMISSION_FLUID_TEMPERATURE).getValue().getState()
                == DashboardLight.State.RED);
        assertTrue(state.getLight(DashboardLight.Type.ENGINE_OIL_LEVEL).getValue().getState() ==
                DashboardLight.State.YELLOW);

        assertTrue(TestUtils.bytesTheSame(bytes, state));
    }

    @Test public void get() {
        String waitingForBytes = "006100";
        String commandBytes = ByteUtils.hexFromBytes(new GetDashboardLights().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        DashboardLights.Builder builder = new DashboardLights.Builder();
        builder.addLight(new Property(new DashboardLight(DashboardLight.Type.HIGH_BEAM_MAIN_BEAM,
                DashboardLight.State.INACTIVE)));
        builder.addLight(new Property(new DashboardLight(DashboardLight.Type.HAZARD_WARNING, DashboardLight
                .State.INFO)));
        builder.addLight(new Property(new DashboardLight(DashboardLight.Type.TRANSMISSION_FLUID_TEMPERATURE,
                DashboardLight.State.RED)));
        builder.addLight(new Property(new DashboardLight(DashboardLight.Type.ENGINE_OIL_LEVEL, DashboardLight
                .State.YELLOW)));
        DashboardLights state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }
}