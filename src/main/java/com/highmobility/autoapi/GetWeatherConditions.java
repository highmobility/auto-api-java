// TODO: license

package com.highmobility.autoapi;

/**
 * Get all weather conditions properties.
 */
public class GetWeatherConditions extends GetCommand {
    public GetWeatherConditions() {
        super(Identifier.WEATHER_CONDITIONS);
    }

    GetWeatherConditions(byte[] bytes) {
        super(bytes);
    }
}