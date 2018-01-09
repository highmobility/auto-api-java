package com.highmobility.autoapi;

/**
 * Get the car environment state. The car will respond with the Weather Conditions message.
 */
public class GetWeatherConditions extends Command {
    public static final Type TYPE = new Type(Identifier.WEATHER_CONDITIONS, 0x00);

    public GetWeatherConditions() {
        super(TYPE);
    }

    GetWeatherConditions(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
