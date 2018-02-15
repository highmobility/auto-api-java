package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetLightConditions;
import com.highmobility.autoapi.LightConditions;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class LightConditionsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00540101000447d8cc000200043e800000");



        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == LightConditions.class);
        LightConditions state = (LightConditions) command;
        assertTrue(state.getOutsideLight() == 111000f);
        assertTrue(state.getInsideLight() == .25f);
    }

    @Test public void get() {
        String waitingForBytes = "005400";
        String commandBytes = Bytes.hexFromBytes(new GetLightConditions().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}