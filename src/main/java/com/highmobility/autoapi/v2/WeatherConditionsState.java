// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

public class WeatherConditionsState extends Command {
    Property<Double> rainIntensity = new Property(Double.class, 0x01);

    /**
     * @return Measured raining intensity percentage, whereas 0% is no rain and 100% is maximum rain
     */
    public Property<Double> getRainIntensity() {
        return rainIntensity;
    }

    WeatherConditionsState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return rainIntensity.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WeatherConditionsState(Builder builder) {
        super(builder);

        rainIntensity = builder.rainIntensity;
    }

    public static final class Builder extends Command.Builder {
        private Property<Double> rainIntensity;

        public Builder() {
            super(Identifier.WEATHER_CONDITIONS);
        }

        public WeatherConditionsState build() {
            return new WeatherConditionsState(this);
        }

        /**
         * @param rainIntensity Measured raining intensity percentage, whereas 0% is no rain and 100% is maximum rain
         * @return The builder
         */
        public Builder setRainIntensity(Property<Double> rainIntensity) {
            this.rainIntensity = rainIntensity.setIdentifier(0x01);
            addProperty(rainIntensity);
            return this;
        }
    }
}