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

import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.SpringRate;
import com.highmobility.autoapi.value.Axle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Chassis Settings is received by the car.
 */
public class ChassisSettings extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x01);

    public static final byte IDENTIFIER_DRIVING_MODE = 0x01;
    public static final byte IDENTIFIER_SPORT_CHRONO_ACTIVE = 0x02;

    public static final byte CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x05;
    public static final byte MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x06;
    public static final byte MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x07;

    public static final byte IDENTIFIER_CURRENT_CHASSIS_POSITION = 0x08;
    public static final byte IDENTIFIER_MAXIMUM_CHASSIS_POSITION = 0x09;
    public static final byte IDENTIFIER_MINIMUM_CHASSIS_POSITION = 0x0A;

    Property<DrivingMode> drivingMode = new Property(DrivingMode.class,
            IDENTIFIER_DRIVING_MODE);
    Property<Boolean> sportChronoActive = new Property(Boolean.class,
            IDENTIFIER_SPORT_CHRONO_ACTIVE);

    Property<SpringRate>[] currentSpringRates;
    Property<SpringRate>[] maximumSpringRates;
    Property<SpringRate>[] minimumSpringRates;

    Property<Integer> currentChassisPosition =
            new PropertyInteger(IDENTIFIER_CURRENT_CHASSIS_POSITION, true);

    Property<Integer> maximumChassisPosition =
            new PropertyInteger(IDENTIFIER_MAXIMUM_CHASSIS_POSITION, true);

    Property<Integer> minimumChassisPosition =
            new PropertyInteger(IDENTIFIER_MINIMUM_CHASSIS_POSITION, true);

    /**
     * @return The driving mode.
     */
    public Property<DrivingMode> getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return ObjectProperty<Boolean> indicating whether the sport chronometer is active.
     */
    public Property<Boolean> isSportChronoActive() {
        return sportChronoActive;
    }

    /**
     * @return The current spring rate values.
     */
    public Property<SpringRate>[] getCurrentSpringRates() {
        return currentSpringRates;
    }

    /**
     * @return The maximum possible values for the spring rates.
     */
    public Property<SpringRate>[] getMaximumSpringRates() {
        return maximumSpringRates;
    }

    /**
     * @return The minimum possible values for the spring rates.
     */
    public Property<SpringRate>[] getMinimumSpringRates() {
        return minimumSpringRates;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The current spring rate for the given axle. Null if does not exists.
     */
    @Nullable public Property<SpringRate> getCurrentSpringRate(Axle axle) {
        for (int i = 0; i < currentSpringRates.length; i++) {
            Property<SpringRate> property = currentSpringRates[i];
            if (property.getValue() != null && property.getValue().getAxle() == axle)
                return property;
        }

        return null;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The maximum spring rate for the given axle. Null if does not exists.
     */
    @Nullable public Property<SpringRate> getMaximumSpringRate(Axle axle) {
        for (int i = 0; i < maximumSpringRates.length; i++) {
            Property<SpringRate> property = maximumSpringRates[i];
            if (property.getValue() != null && property.getValue().getAxle() == axle)
                return property;
        }

        return null;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The current spring rate for the given axle. Null if does not exists.
     */
    @Nullable public Property<SpringRate> getMinimumSpringRate(Axle axle) {
        for (int i = 0; i < minimumSpringRates.length; i++) {
            Property<SpringRate> property = minimumSpringRates[i];
            if (property.getValue() != null && property.getValue().getAxle() == axle)
                return property;
        }

        return null;
    }

    /**
     * @return The chassis position in mm calculated from the lowest point.
     */
    public Property<Integer> getCurrentChassisPosition() {
        return currentChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    public Property<Integer> getMaximumChassisPosition() {
        return maximumChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    public Property<Integer> getMinimumChassisPosition() {
        return minimumChassisPosition;
    }

    ChassisSettings(byte[] bytes) {
        super(bytes);

        ArrayList<Property> currentSpringRates = new ArrayList<>();
        ArrayList<Property> minimumSpringRates = new ArrayList<>();
        ArrayList<Property> maximumSpringRates = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DRIVING_MODE:
                        return drivingMode.update(p);
                    case IDENTIFIER_SPORT_CHRONO_ACTIVE:
                        return sportChronoActive.update(p);
                    case CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        Property<SpringRate> newProperty =
                                new Property(SpringRate.class, p);
                        currentSpringRates.add(newProperty);
                        return newProperty;
                    case MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        Property<SpringRate> newProperty2 =
                                new Property(SpringRate.class, p);
                        maximumSpringRates.add(newProperty2);
                        return newProperty2;
                    case MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        Property<SpringRate> newProperty3 =
                                new Property(SpringRate.class, p);
                        minimumSpringRates.add(newProperty3);
                        return newProperty3;
                    case IDENTIFIER_CURRENT_CHASSIS_POSITION:
                        return currentChassisPosition.update(p);
                    case IDENTIFIER_MAXIMUM_CHASSIS_POSITION:
                        return maximumChassisPosition.update(p);
                    case IDENTIFIER_MINIMUM_CHASSIS_POSITION:
                        return minimumChassisPosition.update(p);
                }

                return null;
            });
        }

        this.currentSpringRates = currentSpringRates.toArray(new Property[0]);
        this.minimumSpringRates = minimumSpringRates.toArray(new Property[0]);
        this.maximumSpringRates = maximumSpringRates.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChassisSettings(Builder builder) {
        super(builder);
        drivingMode = builder.drivingMode;
        sportChronoActive = builder.sportChronoActive;

        currentSpringRates = builder.currentSpringRates.toArray(new Property[0]);
        minimumSpringRates = builder.minimumSpringRates.toArray(new Property[0]);
        maximumSpringRates = builder.maximumSpringRates.toArray(new Property[0]);

        currentChassisPosition = builder.currentChassisPosition;
        minimumChassisPosition = builder.minimumChassisPosition;
        maximumChassisPosition = builder.maximumChassisPosition;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<DrivingMode> drivingMode;
        private Property<Boolean> sportChronoActive;

        List<Property<SpringRate>> currentSpringRates = new ArrayList<>();
        List<Property<SpringRate>> maximumSpringRates = new ArrayList<>();
        List<Property<SpringRate>> minimumSpringRates = new ArrayList<>();

        Property<Integer> currentChassisPosition;
        Property<Integer> maximumChassisPosition;
        Property<Integer> minimumChassisPosition;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param drivingMode Set the driving mode.
         * @return The builder.
         */
        public Builder setDrivingMode(Property<DrivingMode> drivingMode) {
            this.drivingMode = drivingMode;
            addProperty(drivingMode.setIdentifier(IDENTIFIER_DRIVING_MODE));
            return this;
        }

        /**
         * @param sportChronoActive Set the sport chronometer state.
         * @return The builder.
         */
        public Builder setSportChronoActive(Property<Boolean> sportChronoActive) {
            this.sportChronoActive = sportChronoActive;
            addProperty(sportChronoActive.setIdentifier(IDENTIFIER_SPORT_CHRONO_ACTIVE));
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setCurrentChassisPosition(Property<Integer> chassisPosition) {
            this.currentChassisPosition = new PropertyInteger(IDENTIFIER_CURRENT_CHASSIS_POSITION
                    , true, 1, chassisPosition);
            addProperty(this.currentChassisPosition);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMinimumChassisPosition(Property<Integer> chassisPosition) {
            this.minimumChassisPosition = new PropertyInteger(IDENTIFIER_MINIMUM_CHASSIS_POSITION
                    , true, 1, chassisPosition);
            addProperty(this.minimumChassisPosition);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMaximumChassisPosition(Property<Integer> chassisPosition) {
            this.maximumChassisPosition = new PropertyInteger(IDENTIFIER_MAXIMUM_CHASSIS_POSITION
                    , true, 1, chassisPosition);
            addProperty(this.maximumChassisPosition);
            return this;
        }

        /**
         * Set the current spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setCurrentSpringRates(Property<SpringRate>[] springRates) {
            this.currentSpringRates.clear();
            for (int i = 0; i < springRates.length; i++) {
                addCurrentSpringRate(springRates[i]);
            }

            return this;
        }

        /**
         * Add a current spring rate value.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addCurrentSpringRate(Property<SpringRate> springRate) {
            springRate.setIdentifier(CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER);
            addProperty(springRate);
            this.currentSpringRates.add(springRate);
            return this;
        }

        /**
         * Set the minimum spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setMinimumSpringRates(Property<SpringRate>[] springRates) {
            this.minimumSpringRates.clear();

            for (int i = 0; i < springRates.length; i++) {
                addMinimumSpringRate(springRates[i]);
            }

            return this;
        }

        /**
         * Add a minimum spring rate value.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addMinimumSpringRate(Property<SpringRate> springRate) {
            springRate.setIdentifier(MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER);
            addProperty(springRate);
            this.minimumSpringRates.add(springRate);
            return this;
        }

        /**
         * Set the minimum spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setMaximumSpringRates(Property<SpringRate>[] springRates) {
            this.maximumSpringRates.clear();

            for (int i = 0; i < springRates.length; i++) {
                addMaximumSpringRate(springRates[i]);
            }

            return this;
        }

        /**
         * Add a minimum spring rate value.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addMaximumSpringRate(Property<SpringRate> springRate) {
            springRate.setIdentifier(MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER);
            addProperty(springRate);
            this.maximumSpringRates.add(springRate);
            return this;
        }

        public ChassisSettings build() {
            return new ChassisSettings(this);
        }
    }
}