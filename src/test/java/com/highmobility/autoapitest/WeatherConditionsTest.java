package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetWeatherConditions;
import com.highmobility.autoapi.WeatherConditions;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WeatherConditionsTest {
    Bytes bytes = new Bytes(
            "005501" +
                    "01000B0100083FF0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == WeatherConditions.class);
        WeatherConditions state = (WeatherConditions) command;
        testState(state);
    }

    private void testState(WeatherConditions state) {
        assertTrue(state.getRainIntensity().getValue() == 100);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005500";
        String commandBytes = ByteUtils.hexFromBytes(new GetWeatherConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005501");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((WeatherConditions) state).getRainIntensity().getValue() == null);
    }

    @Test public void builder() {
        WeatherConditions.Builder builder = new WeatherConditions.Builder();
        builder.setRainIntensity(new Property<>(1d));
        testState(builder.build());
    }
}