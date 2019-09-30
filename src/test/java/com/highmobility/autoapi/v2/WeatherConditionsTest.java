package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapitest.TestUtils;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WeatherConditionsTest extends BaseTest {
    Bytes bytes = new Bytes(
            "005501" +
                    "01000B0100083FF0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        WeatherConditionsState state = (WeatherConditionsState) command;
        testState(state);
    }

    private void testState(WeatherConditionsState state) {
        assertTrue(state.getRainIntensity().getValue() == 1d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005500";
        String commandBytes = ByteUtils.hexFromBytes(new GetWeatherConditions().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void builder() {
        WeatherConditionsState.Builder builder = new WeatherConditionsState.Builder();
        builder.setRainIntensity(new Property(1d));
        testState(builder.build());
    }
}