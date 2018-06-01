package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetLightConditions;
import com.highmobility.autoapi.LightConditions;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class LightConditionsTest {
    Bytes bytes = new Bytes(
            "00540101000447d8cc000200043e800000");
    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == LightConditions.class);
        LightConditions state = (LightConditions) command;
        assertTrue(state.getOutsideLight() == 111000f);
        assertTrue(state.getInsideLight() == .25f);
    }

    @Test public void build() {
        LightConditions.Builder builder = new LightConditions.Builder();
        builder.setOutsideLight(111000f);
        builder.setInsideLight(.25f);
        Command command = builder.build();
        assertTrue(command.equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005400";
        String commandBytes = ByteUtils.hexFromBytes(new GetLightConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005401");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((LightConditions)state).getOutsideLight() == null);
    }
}