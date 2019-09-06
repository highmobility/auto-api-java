// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyInteger;
import com.highmobility.autoapi.v2.value.DrivingMode;
import com.highmobility.autoapi.v2.value.SpringRate;
import java.util.ArrayList;
import java.util.List;

public class ChassisSettingsState extends SetCommand {
    Property<DrivingMode> drivingMode = new Property(DrivingMode.class, 0x01);
    Property<SportChrono> sportChrono = new Property(SportChrono.class, 0x02);
    Property<SpringRate> currentSpringRates[];
    Property<SpringRate> maximumSpringRates[];
    Property<SpringRate> minimumSpringRate[];
    PropertyInteger currentChassisPosition = new PropertyInteger(0x08, true);
    PropertyInteger maximumChassisPosition = new PropertyInteger(0x09, true);
    PropertyInteger minimumChassisPosition = new PropertyInteger(0x0a, true);

    /**
     * @return The driving mode
     */
    public Property<DrivingMode> getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return The sport chrono
     */
    public Property<SportChrono> getSportChrono() {
        return sportChrono;
    }

    /**
     * @return The current values for the spring rates
     */
    public Property<SpringRate>[] getCurrentSpringRates() {
        return currentSpringRates;
    }

    /**
     * @return The maximum possible values for the spring rates
     */
    public Property<SpringRate>[] getMaximumSpringRates() {
        return maximumSpringRates;
    }

    /**
     * @return The minimum possible values for the spring rates
     */
    public Property<SpringRate>[] getMinimumSpringRate() {
        return minimumSpringRate;
    }

    /**
     * @return The chassis position in mm calculated from the lowest point
     */
    public PropertyInteger getCurrentChassisPosition() {
        return currentChassisPosition;
    }

    /**
     * @return The maximum chassis position
     */
    public PropertyInteger getMaximumChassisPosition() {
        return maximumChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position
     */
    public PropertyInteger getMinimumChassisPosition() {
        return minimumChassisPosition;
    }

    ChassisSettingsState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> currentSpringRatesBuilder = new ArrayList<>();
        ArrayList<Property> maximumSpringRatesBuilder = new ArrayList<>();
        ArrayList<Property> minimumSpringRateBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return drivingMode.update(p);
                    case 0x02: return sportChrono.update(p);
                    case 0x05:
                        Property<SpringRate> currentSpringRate = new Property(SpringRate.class, p);
                        currentSpringRatesBuilder.add(currentSpringRate);
                        return currentSpringRate;
                    case 0x06:
                        Property<SpringRate> maximumSpringRate = new Property(SpringRate.class, p);
                        maximumSpringRatesBuilder.add(maximumSpringRate);
                        return maximumSpringRate;
                    case 0x07:
                        Property<SpringRate> minimumSpringRate = new Property(SpringRate.class, p);
                        minimumSpringRateBuilder.add(minimumSpringRate);
                        return minimumSpringRate;
                    case 0x08: return currentChassisPosition.update(p);
                    case 0x09: return maximumChassisPosition.update(p);
                    case 0x0a: return minimumChassisPosition.update(p);
                }

                return null;
            });
        }

        currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
        maximumSpringRates = maximumSpringRatesBuilder.toArray(new Property[0]);
        minimumSpringRate = minimumSpringRateBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChassisSettingsState(Builder builder) {
        super(builder);

        drivingMode = builder.drivingMode;
        sportChrono = builder.sportChrono;
        currentSpringRates = builder.currentSpringRates.toArray(new Property[0]);
        maximumSpringRates = builder.maximumSpringRates.toArray(new Property[0]);
        minimumSpringRate = builder.minimumSpringRate.toArray(new Property[0]);
        currentChassisPosition = builder.currentChassisPosition;
        maximumChassisPosition = builder.maximumChassisPosition;
        minimumChassisPosition = builder.minimumChassisPosition;
    }

    public static final class Builder extends Command.Builder {
        private Property<DrivingMode> drivingMode;
        private Property<SportChrono> sportChrono;
        private List<Property> currentSpringRates = new ArrayList<>();
        private List<Property> maximumSpringRates = new ArrayList<>();
        private List<Property> minimumSpringRate = new ArrayList<>();
        private PropertyInteger currentChassisPosition;
        private PropertyInteger maximumChassisPosition;
        private PropertyInteger minimumChassisPosition;

        public Builder() {
            super(Identifier.CHASSIS_SETTINGS);
        }

        public ChassisSettingsState build() {
            return new ChassisSettingsState(this);
        }

        /**
         * @param drivingMode The driving mode
         * @return The builder
         */
        public Builder setDrivingMode(Property<DrivingMode> drivingMode) {
            this.drivingMode = drivingMode.setIdentifier(0x01);
            addProperty(drivingMode);
            return this;
        }

        /**
         * @param sportChrono The sport chrono
         * @return The builder
         */
        public Builder setSportChrono(Property<SportChrono> sportChrono) {
            this.sportChrono = sportChrono.setIdentifier(0x02);
            addProperty(sportChrono);
            return this;
        }

        /**
         * Add an array of current spring rates.
         *
         * @param currentSpringRates The current spring rates. The current values for the spring rates
         * @return The builder
         */
        public Builder setCurrentSpringRates(Property<SpringRate>[] currentSpringRates) {
            this.currentSpringRates.clear();
            for (int i = 0; i < currentSpringRates.length; i++) {
                addCurrentSpringRate(currentSpringRates[i]);
            }

            return this;
        }

        /**
         * Add a single current spring rate.
         *
         * @param currentSpringRate The current spring rate. The current values for the spring rates
         * @return The builder
         */
        public Builder addCurrentSpringRate(Property<SpringRate> currentSpringRate) {
            currentSpringRate.setIdentifier(0x05);
            addProperty(currentSpringRate);
            currentSpringRates.add(currentSpringRate);
            return this;
        }

        /**
         * Add an array of maximum spring rates.
         *
         * @param maximumSpringRates The maximum spring rates. The maximum possible values for the spring rates
         * @return The builder
         */
        public Builder setMaximumSpringRates(Property<SpringRate>[] maximumSpringRates) {
            this.maximumSpringRates.clear();
            for (int i = 0; i < maximumSpringRates.length; i++) {
                addMaximumSpringRate(maximumSpringRates[i]);
            }

            return this;
        }

        /**
         * Add a single maximum spring rate.
         *
         * @param maximumSpringRate The maximum spring rate. The maximum possible values for the spring rates
         * @return The builder
         */
        public Builder addMaximumSpringRate(Property<SpringRate> maximumSpringRate) {
            maximumSpringRate.setIdentifier(0x06);
            addProperty(maximumSpringRate);
            maximumSpringRates.add(maximumSpringRate);
            return this;
        }

        /**
         * Add an array of minimum spring rates.
         *
         * @param minimumSpringRate The minimum spring rates. The minimum possible values for the spring rates
         * @return The builder
         */
        public Builder setMinimumSpringRate(Property<SpringRate>[] minimumSpringRate) {
            this.minimumSpringRate.clear();
            for (int i = 0; i < minimumSpringRate.length; i++) {
                addMinimumSpringRat(minimumSpringRate[i]);
            }

            return this;
        }

        /**
         * Add a single minimum spring rate.
         *
         * @param minimumSpringRat The minimum spring rate. The minimum possible values for the spring rates
         * @return The builder
         */
        public Builder addMinimumSpringRat(Property<SpringRate> minimumSpringRat) {
            minimumSpringRat.setIdentifier(0x07);
            addProperty(minimumSpringRat);
            minimumSpringRate.add(minimumSpringRat);
            return this;
        }

        /**
         * @param currentChassisPosition The chassis position in mm calculated from the lowest point
         * @return The builder
         */
        public Builder setCurrentChassisPosition(PropertyInteger currentChassisPosition) {
            this.currentChassisPosition = new PropertyInteger(0x08, true, 1, currentChassisPosition);
            addProperty(currentChassisPosition);
            return this;
        }

        /**
         * @param maximumChassisPosition The maximum chassis position
         * @return The builder
         */
        public Builder setMaximumChassisPosition(PropertyInteger maximumChassisPosition) {
            this.maximumChassisPosition = new PropertyInteger(0x09, true, 1, maximumChassisPosition);
            addProperty(maximumChassisPosition);
            return this;
        }

        /**
         * @param minimumChassisPosition The minimum possible value for the chassis position
         * @return The builder
         */
        public Builder setMinimumChassisPosition(PropertyInteger minimumChassisPosition) {
            this.minimumChassisPosition = new PropertyInteger(0x0a, true, 1, minimumChassisPosition);
            addProperty(minimumChassisPosition);
            return this;
        }
    }

    public enum SportChrono {
        INACTIVE((byte)0x00),
        ACTIVE((byte)0x01),
        RESET((byte)0x02);

        public static SportChrono fromByte(byte byteValue) throws CommandParseException {
            SportChrono[] values = SportChrono.values();

            for (int i = 0; i < values.length; i++) {
                SportChrono state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        SportChrono(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}