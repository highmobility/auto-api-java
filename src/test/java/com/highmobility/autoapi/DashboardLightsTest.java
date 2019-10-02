package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DashboardLight;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DashboardLightsTest extends BaseTest {
    Bytes bytes = new Bytes("006101" +
            "0100050100020000" +
            "0100050100020201" +
            "0100050100020F03" +
            "0100050100021502"
    );

    @Test
    public void state() {
        DashboardLightsState state = (DashboardLightsState) CommandResolver.resolve(bytes);
        testState(state);
    }

    private void testState(DashboardLightsState state) {
        assertTrue(state.getDashboardLights().length == 4);

        assertTrue(getDashboardLight(state.getDashboardLights(), DashboardLight.Name.HIGH_BEAM).getState() ==
                DashboardLight.State.INACTIVE);
        assertTrue(getDashboardLight(state.getDashboardLights(),
                DashboardLight.Name.HAZARD_WARNING).getState() == DashboardLight.State.INFO);
        assertTrue(getDashboardLight(state.getDashboardLights(),
                DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE).getState() == DashboardLight.State.RED);
        assertTrue(getDashboardLight(state.getDashboardLights(),
                DashboardLight.Name.ENGINE_OIL_LEVEL).getState() == DashboardLight.State.YELLOW);

        assertTrue(TestUtils.bytesTheSame(bytes, state));
    }

    DashboardLight getDashboardLight(Property<DashboardLight>[] lights, DashboardLight.Name name) {
        for (Property<DashboardLight> light : lights) {
            if (light.getValue().getName() == name) return light.getValue();
        }

        return null;
    }

    @Test public void get() {
        String waitingForBytes = "006100";
        String commandBytes = ByteUtils.hexFromBytes(new GetDashboardLights().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        DashboardLightsState.Builder builder = new DashboardLightsState.Builder();
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.HIGH_BEAM,
                DashboardLight.State.INACTIVE)));
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.HAZARD_WARNING,
                DashboardLight
                        .State.INFO)));
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE,
                DashboardLight.State.RED)));
        builder.addDashboardLight(new Property(new DashboardLight(DashboardLight.Name.ENGINE_OIL_LEVEL,
                DashboardLight
                        .State.YELLOW)));
        DashboardLightsState state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }
}