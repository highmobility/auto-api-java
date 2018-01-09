package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Weather Conditions is received by the car.
 */
public class WeatherConditions extends Command {
    public static final Type TYPE = new Type(Identifier.WEATHER_CONDITIONS, 0x01);

    Float rainIntensity;

    /**
     * @return Measured raining intensity percentage, whereas 0 is no rain and 1 is maximum rain
     */
    public Float getRainIntensity() {
        return rainIntensity;
    }

    public WeatherConditions(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    rainIntensity = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }
}