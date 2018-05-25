package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DashboardLights;
import com.highmobility.autoapi.GetDashboardLights;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DashboardLightsTest {
    byte[] bytes = ByteUtils.bytesFromHex(
            "006101010002000001000202010100020F030100021500");

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == DashboardLights.class);
        DashboardLights state = (DashboardLights) command;
        assertTrue(state.getLights().length == 4);

        assertTrue(state.getLight(DashboardLight.Type.HIGH_BEAM_MAIN_BEAM).getState() ==
                DashboardLight.State.INACTIVE);
        assertTrue(state.getLight(DashboardLight.Type.HAZARD_WARNING).getState() ==
                DashboardLight.State.INFO);
        assertTrue(state.getLight(DashboardLight.Type.TRANSMISSION_FLUID_TEMPERATURE).getState()
                == DashboardLight.State.RED);
        assertTrue(state.getLight(DashboardLight.Type.ENGINE_OIL_LEVEL).getState() ==
                DashboardLight.State.INACTIVE);
    }

    @Test public void get() {
        String waitingForBytes = "006100";
        String commandBytes = ByteUtils.hexFromBytes(new GetDashboardLights().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        DashboardLights.Builder builder = new DashboardLights.Builder();
        builder.addLight(new DashboardLight(DashboardLight.Type.HIGH_BEAM_MAIN_BEAM,
                DashboardLight.State.INACTIVE));
        builder.addLight(new DashboardLight(DashboardLight.Type.HAZARD_WARNING, DashboardLight
                .State.INFO));
        builder.addLight(new DashboardLight(DashboardLight.Type.TRANSMISSION_FLUID_TEMPERATURE,
                DashboardLight.State.RED));
        builder.addLight(new DashboardLight(DashboardLight.Type.ENGINE_OIL_LEVEL, DashboardLight
                .State.INACTIVE));
        byte[] actualBytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(actualBytes, bytes));
    }
}