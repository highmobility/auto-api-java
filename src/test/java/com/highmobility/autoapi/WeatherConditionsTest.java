package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WeatherConditionsTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "005501" +
                    "01000B0100083FF0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        WeatherConditions.State state = (WeatherConditions.State) command;
        testState(state);
    }

    private void testState(WeatherConditions.State state) {
        assertTrue(state.getRainIntensity().getValue() == 1d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "005500";
        String commandBytes = ByteUtils.hexFromBytes(new WeatherConditions.GetWeatherConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void builder() {
        WeatherConditions.State.Builder builder = new WeatherConditions.State.Builder();
        builder.setRainIntensity(new Property(1d));
        testState(builder.build());
    }
}