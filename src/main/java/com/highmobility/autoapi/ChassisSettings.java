/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.SpringRateProperty;
import com.highmobility.autoapi.property.value.Axle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Chassis Settings is received by the car.
 */
public class ChassisSettings extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x01);

    public static final byte DRIVING_MODE_IDENTIFIER = 0x01;
    public static final byte SPORT_CHRONO_ACTIVE_IDENTIFIER = 0x02;

    public static final byte CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x05;
    public static final byte MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x06;
    public static final byte MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x07;

    public static final byte CURRENT_CHASSIS_POSITION_IDENTIFIER = 0x08;
    public static final byte MAXIMUM_CHASSIS_POSITION_IDENTIFIER = 0x09;
    public static final byte MINIMUM_CHASSIS_POSITION_IDENTIFIER = 0x0A;

    DrivingMode drivingMode;
    BooleanProperty sportChronoActive;

    SpringRateProperty[] currentSpringRates;
    SpringRateProperty[] maximumSpringRates;
    SpringRateProperty[] minimumSpringRates;

    IntegerProperty currentChassisPosition;
    IntegerProperty maximumChassisPosition;
    IntegerProperty minimumChassisPosition;

    /**
     * @return The driving mode.
     */
    @Nullable public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return BooleanProperty indicating whether the sport chronometer is active.
     */
    @Nullable public BooleanProperty isSportChronoActive() {
        return sportChronoActive;
    }

    /**
     * @return The current spring rate values.
     */
    public SpringRateProperty[] getCurrentSpringRates() {
        return currentSpringRates;
    }

    /**
     * @return The maximum possible values for the spring rates.
     */
    public SpringRateProperty[] getMaximumSpringRates() {
        return maximumSpringRates;
    }

    /**
     * @return The minimum possible values for the spring rates.
     */
    public SpringRateProperty[] getMinimumSpringRates() {
        return minimumSpringRates;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The current spring rate for the given axle. Null if does not exists.
     */
    @Nullable public SpringRateProperty getCurrentSpringRate(Axle axle) {
        for (int i = 0; i < currentSpringRates.length; i++) {
            SpringRateProperty property = currentSpringRates[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The maximum spring rate for the given axle. Null if does not exists.
     */
    @Nullable public SpringRateProperty getMaximumSpringRate(Axle axle) {
        for (int i = 0; i < maximumSpringRates.length; i++) {
            SpringRateProperty property = maximumSpringRates[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The current spring rate for the given axle. Null if does not exists.
     */
    @Nullable public SpringRateProperty getMinimumSpringRate(Axle axle) {
        for (int i = 0; i < minimumSpringRates.length; i++) {
            SpringRateProperty property = minimumSpringRates[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @return The chassis position in mm calculated from the lowest point.
     */
    @Nullable public IntegerProperty getCurrentChassisPosition() {
        return currentChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    @Nullable public IntegerProperty getMaximumChassisPosition() {
        return maximumChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    @Nullable public IntegerProperty getMinimumChassisPosition() {
        return minimumChassisPosition;
    }

    ChassisSettings(byte[] bytes) {
        super(bytes);

        ArrayList<SpringRateProperty> currentSpringRates = new ArrayList<>();
        ArrayList<SpringRateProperty> minimumSpringRates = new ArrayList<>();
        ArrayList<SpringRateProperty> maximumSpringRates = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case DRIVING_MODE_IDENTIFIER:
                        drivingMode = DrivingMode.fromByte(p.getValueByte());
                        return drivingMode;
                    case SPORT_CHRONO_ACTIVE_IDENTIFIER:
                        sportChronoActive = new BooleanProperty(p);
                        return sportChronoActive;
                    case CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        SpringRateProperty prop1 = new SpringRateProperty(p.getByteArray());
                        currentSpringRates.add(prop1);
                        return prop1;
                    case MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        SpringRateProperty prop2 = new SpringRateProperty(p.getByteArray());
                        maximumSpringRates.add(prop2);
                        return prop2;
                    case MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        SpringRateProperty prop = new SpringRateProperty(p.getByteArray());
                        minimumSpringRates.add(prop);
                        return prop;
                    case CURRENT_CHASSIS_POSITION_IDENTIFIER:
                        currentChassisPosition = new IntegerProperty(p, true);
                        return currentChassisPosition;
                    case MAXIMUM_CHASSIS_POSITION_IDENTIFIER:
                        maximumChassisPosition = new IntegerProperty(p, true);
                        return maximumChassisPosition;
                    case MINIMUM_CHASSIS_POSITION_IDENTIFIER:
                        minimumChassisPosition = new IntegerProperty(p, true);
                        return minimumChassisPosition;
                }

                return null;
            });
        }

        this.currentSpringRates = currentSpringRates.toArray(new SpringRateProperty[0]);
        this.minimumSpringRates = minimumSpringRates.toArray(new SpringRateProperty[0]);
        this.maximumSpringRates = maximumSpringRates.toArray(new SpringRateProperty[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChassisSettings(Builder builder) {
        super(builder);
        drivingMode = builder.drivingMode;
        sportChronoActive = builder.sportChronoActive;

        currentSpringRates = builder.currentSpringRates.toArray(new SpringRateProperty[0]);
        minimumSpringRates = builder.minimumSpringRates.toArray(new SpringRateProperty[0]);
        maximumSpringRates = builder.maximumSpringRates.toArray(new SpringRateProperty[0]);

        currentChassisPosition = builder.currentChassisPosition;
        minimumChassisPosition = builder.minimumChassisPosition;
        maximumChassisPosition = builder.maximumChassisPosition;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private DrivingMode drivingMode;
        private BooleanProperty sportChronoActive;

        List<SpringRateProperty> currentSpringRates = new ArrayList<>();
        List<SpringRateProperty> maximumSpringRates = new ArrayList<>();
        List<SpringRateProperty> minimumSpringRates = new ArrayList<>();

        IntegerProperty currentChassisPosition;
        IntegerProperty maximumChassisPosition;
        IntegerProperty minimumChassisPosition;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param drivingMode Set the driving mode.
         * @return The builder.
         */
        public Builder setDrivingMode(DrivingMode drivingMode) {
            this.drivingMode = drivingMode;
            addProperty(new Property(DRIVING_MODE_IDENTIFIER, drivingMode.getByte()));
            return this;
        }

        /**
         * @param sportChronoActive Set the sport chronometer state.
         * @return The builder.
         */
        public Builder setSportChronoActive(BooleanProperty sportChronoActive) {
            this.sportChronoActive = sportChronoActive;
            sportChronoActive.setIdentifier(SPORT_CHRONO_ACTIVE_IDENTIFIER);
            addProperty(sportChronoActive);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setCurrentChassisPosition(IntegerProperty chassisPosition) {
            this.currentChassisPosition = chassisPosition;
            chassisPosition.setIdentifier(CURRENT_CHASSIS_POSITION_IDENTIFIER, 1);
            addProperty(chassisPosition);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMinimumChassisPosition(IntegerProperty chassisPosition) {
            this.minimumChassisPosition = chassisPosition;
            chassisPosition.setIdentifier(MINIMUM_CHASSIS_POSITION_IDENTIFIER, 1);
            addProperty(chassisPosition);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMaximumChassisPosition(IntegerProperty chassisPosition) {
            this.maximumChassisPosition = chassisPosition;
            chassisPosition.setIdentifier(MAXIMUM_CHASSIS_POSITION_IDENTIFIER, 1);
            addProperty(chassisPosition);
            return this;
        }

        /**
         * Set the current spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setCurrentSpringRates(SpringRateProperty[] springRates) {
            this.currentSpringRates = Arrays.asList(springRates);

            for (int i = 0; i < springRates.length; i++) {
                springRates[i].setIdentifier(CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER);
                addProperty(springRates[i]);
            }

            return this;
        }

        /**
         * Add a current spring rate value.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addCurrentSpringRate(SpringRateProperty springRate) {
            springRate.setIdentifier(CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER);
            this.currentSpringRates.add(springRate);
            addProperty(springRate);
            return this;
        }

        /**
         * Set the minimum spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setMinimumSpringRates(SpringRateProperty[] springRates) {
            this.currentSpringRates = Arrays.asList(springRates);

            for (int i = 0; i < springRates.length; i++) {
                springRates[i].setIdentifier(MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER);
                addProperty(springRates[i]);
            }

            return this;
        }

        /**
         * Add a minimum spring rate value.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addMinimumSpringRate(SpringRateProperty springRate) {
            springRate.setIdentifier(MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER);
            this.currentSpringRates.add(springRate);
            addProperty(springRate);
            return this;
        }

        /**
         * Set the minimum spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setMaximumSpringRates(SpringRateProperty[] springRates) {
            this.maximumSpringRates = Arrays.asList(springRates);

            for (int i = 0; i < springRates.length; i++) {
                springRates[i].setIdentifier(MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER);
                addProperty(springRates[i]);
            }

            return this;
        }

        /**
         * Add a minimum spring rate value.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addMaximumSpringRate(SpringRateProperty springRate) {
            springRate.setIdentifier(MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER);
            this.maximumSpringRates.add(springRate);
            addProperty(springRate);
            return this;
        }

        public ChassisSettings build() {
            return new ChassisSettings(this);
        }
    }
}