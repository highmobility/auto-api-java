package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetWeatherConditions;
import com.highmobility.autoapi.WeatherConditions;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WeatherConditionsTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "00550101000164");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == WeatherConditions.class);
        WeatherConditions state = (WeatherConditions) command;
        assertTrue(state.getRainIntensity() == 1f);
    }

    @Test public void get() {
        String waitingForBytes = "005500";
        String commandBytes = ByteUtils.hexFromBytes(new GetWeatherConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005501");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((WeatherConditions)state).getRainIntensity() == null);
    }

    @Test public void builder() {
        WeatherConditions.Builder builder = new WeatherConditions.Builder();
        builder.setRainIntensity(1f);
        byte[] bytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(bytes, ByteUtils.bytesFromHex("00550101000164")));
    }
}