package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetWeatherConditions;
import com.highmobility.autoapi.WeatherConditions;
import com.highmobility.utils.Bytes;

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
        byte[] bytes = Bytes.bytesFromHex(
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
        String commandBytes = Bytes.hexFromBytes(new GetWeatherConditions().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("005501");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((WeatherConditions)state).getRainIntensity() == null);
    }

    @Test public void builder() {
        WeatherConditions.Builder builder = new WeatherConditions.Builder();
        builder.setRainIntensity(1f);
        byte[] bytes = builder.build().getBytes();
        assertTrue(Arrays.equals(bytes, Bytes.bytesFromHex("00550101000164")));
    }
}