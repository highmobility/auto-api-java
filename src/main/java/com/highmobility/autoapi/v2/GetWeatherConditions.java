// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all weather conditions properties.
 */
public class GetWeatherConditions extends GetCommand {
    public GetWeatherConditions() {
        super(Identifier.WEATHER_CONDITIONS, getStateIdentifiers());
    }

    GetWeatherConditions(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}