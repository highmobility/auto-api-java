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

import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.SpringRate;
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

    public static final byte IDENTIFIER_DRIVING_MODE = 0x01;
    public static final byte IDENTIFIER_SPORT_CHRONO_ACTIVE = 0x02;

    public static final byte CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x05;
    public static final byte MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x06;
    public static final byte MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER = 0x07;

    public static final byte IDENTIFIER_CURRENT_CHASSIS_POSITION = 0x08;
    public static final byte IDENTIFIER_MAXIMUM_CHASSIS_POSITION = 0x09;
    public static final byte IDENTIFIER_MINIMUM_CHASSIS_POSITION = 0x0A;

    ObjectProperty<DrivingMode> drivingMode = new ObjectProperty<>(DrivingMode.class,
            IDENTIFIER_DRIVING_MODE);
    ObjectProperty<Boolean> sportChronoActive = new ObjectProperty<>(Boolean.class,
            IDENTIFIER_SPORT_CHRONO_ACTIVE);

    ObjectProperty<SpringRate>[] currentSpringRates;
    ObjectProperty<SpringRate>[] maximumSpringRates;
    ObjectProperty<SpringRate>[] minimumSpringRates;

    ObjectProperty<Integer> currentChassisPosition =
            new ObjectPropertyInteger(IDENTIFIER_CURRENT_CHASSIS_POSITION, true);

    ObjectProperty<Integer> maximumChassisPosition =
            new ObjectPropertyInteger(IDENTIFIER_MAXIMUM_CHASSIS_POSITION, true);

    ObjectProperty<Integer> minimumChassisPosition =
            new ObjectPropertyInteger(IDENTIFIER_MINIMUM_CHASSIS_POSITION, true);

    /**
     * @return The driving mode.
     */
    @Nullable public ObjectProperty<DrivingMode> getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return ObjectProperty<Boolean> indicating whether the sport chronometer is active.
     */
    @Nullable public ObjectProperty<Boolean> isSportChronoActive() {
        return sportChronoActive;
    }

    /**
     * @return The current spring rate values.
     */
    public ObjectProperty<SpringRate>[] getCurrentSpringRates() {
        return currentSpringRates;
    }

    /**
     * @return The maximum possible values for the spring rates.
     */
    public ObjectProperty<SpringRate>[] getMaximumSpringRates() {
        return maximumSpringRates;
    }

    /**
     * @return The minimum possible values for the spring rates.
     */
    public ObjectProperty<SpringRate>[] getMinimumSpringRates() {
        return minimumSpringRates;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The current spring rate for the given axle. Null if does not exists.
     */
    @Nullable public ObjectProperty<SpringRate> getCurrentSpringRate(Axle axle) {
        for (int i = 0; i < currentSpringRates.length; i++) {
            ObjectProperty<SpringRate> property = currentSpringRates[i];
            if (property.getValue() != null && property.getValue().getAxle() == axle)
                return property;
        }

        return null;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The maximum spring rate for the given axle. Null if does not exists.
     */
    @Nullable public ObjectProperty<SpringRate> getMaximumSpringRate(Axle axle) {
        for (int i = 0; i < maximumSpringRates.length; i++) {
            ObjectProperty<SpringRate> property = maximumSpringRates[i];
            if (property.getValue() != null && property.getValue().getAxle() == axle)
                return property;
        }

        return null;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The current spring rate for the given axle. Null if does not exists.
     */
    @Nullable public ObjectProperty<SpringRate> getMinimumSpringRate(Axle axle) {
        for (int i = 0; i < minimumSpringRates.length; i++) {
            ObjectProperty<SpringRate> property = minimumSpringRates[i];
            if (property.getValue() != null && property.getValue().getAxle() == axle)
                return property;
        }

        return null;
    }

    /**
     * @return The chassis position in mm calculated from the lowest point.
     */
    @Nullable public ObjectProperty<Integer> getCurrentChassisPosition() {
        return currentChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    @Nullable public ObjectProperty<Integer> getMaximumChassisPosition() {
        return maximumChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    @Nullable public ObjectProperty<Integer> getMinimumChassisPosition() {
        return minimumChassisPosition;
    }

    ChassisSettings(byte[] bytes) {
        super(bytes);

        ArrayList<ObjectProperty<SpringRate>> currentSpringRates = new ArrayList<>();
        ArrayList<ObjectProperty<SpringRate>> minimumSpringRates = new ArrayList<>();
        ArrayList<ObjectProperty<SpringRate>> maximumSpringRates = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DRIVING_MODE:
                        return drivingMode.update(p);
                    case IDENTIFIER_SPORT_CHRONO_ACTIVE:
                        return sportChronoActive.update(p);
                    case CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        ObjectProperty<SpringRate> newProperty =
                                new ObjectProperty<>(SpringRate.class, p);
                        currentSpringRates.add(newProperty);
                        return newProperty;
                    case MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        ObjectProperty<SpringRate> newProperty2 =
                                new ObjectProperty<>(SpringRate.class, p);
                        maximumSpringRates.add(newProperty2);
                        return newProperty2;
                    case MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                        ObjectProperty<SpringRate> newProperty3 =
                                new ObjectProperty<>(SpringRate.class, p);
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

        this.currentSpringRates = currentSpringRates.toArray(new ObjectProperty[0]);
        this.minimumSpringRates = minimumSpringRates.toArray(new ObjectProperty[0]);
        this.maximumSpringRates = maximumSpringRates.toArray(new ObjectProperty[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChassisSettings(Builder builder) {
        super(builder);
        drivingMode = builder.drivingMode;
        sportChronoActive = builder.sportChronoActive;

        currentSpringRates = builder.currentSpringRates.toArray(new ObjectProperty[0]);
        minimumSpringRates = builder.minimumSpringRates.toArray(new ObjectProperty[0]);
        maximumSpringRates = builder.maximumSpringRates.toArray(new ObjectProperty[0]);

        currentChassisPosition = builder.currentChassisPosition;
        minimumChassisPosition = builder.minimumChassisPosition;
        maximumChassisPosition = builder.maximumChassisPosition;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ObjectProperty<DrivingMode> drivingMode;
        private ObjectProperty<Boolean> sportChronoActive;

        List<ObjectProperty<SpringRate>> currentSpringRates = new ArrayList<>();
        List<ObjectProperty<SpringRate>> maximumSpringRates = new ArrayList<>();
        List<ObjectProperty<SpringRate>> minimumSpringRates = new ArrayList<>();

        ObjectProperty<Integer> currentChassisPosition;
        ObjectProperty<Integer> maximumChassisPosition;
        ObjectProperty<Integer> minimumChassisPosition;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param drivingMode Set the driving mode.
         * @return The builder.
         */
        public Builder setDrivingMode(ObjectProperty<DrivingMode> drivingMode) {
            this.drivingMode = drivingMode;
            addProperty(drivingMode);
            drivingMode.setIdentifier(IDENTIFIER_DRIVING_MODE);
            return this;
        }

        /**
         * @param sportChronoActive Set the sport chronometer state.
         * @return The builder.
         */
        public Builder setSportChronoActive(ObjectProperty<Boolean> sportChronoActive) {
            this.sportChronoActive = sportChronoActive;
            addProperty(sportChronoActive);
            sportChronoActive.setIdentifier(IDENTIFIER_SPORT_CHRONO_ACTIVE);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setCurrentChassisPosition(ObjectPropertyInteger chassisPosition) {
            this.currentChassisPosition = chassisPosition;
            addProperty(chassisPosition);
            chassisPosition.update(IDENTIFIER_CURRENT_CHASSIS_POSITION, true, 1);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMinimumChassisPosition(ObjectPropertyInteger chassisPosition) {
            this.minimumChassisPosition = chassisPosition;
            addProperty(chassisPosition);
            chassisPosition.update(IDENTIFIER_MINIMUM_CHASSIS_POSITION, true, 1);
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMaximumChassisPosition(ObjectPropertyInteger chassisPosition) {
            this.maximumChassisPosition = chassisPosition;
            addProperty(chassisPosition);
            chassisPosition.update(IDENTIFIER_MAXIMUM_CHASSIS_POSITION, true, 1);
            return this;
        }

        /**
         * Set the current spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setCurrentSpringRates(ObjectProperty<SpringRate>[] springRates) {
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
        public Builder addCurrentSpringRate(ObjectProperty<SpringRate> springRate) {
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
        public Builder setMinimumSpringRates(ObjectProperty<SpringRate>[] springRates) {
            this.minimumSpringRates = Arrays.asList(springRates);

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
        public Builder addMinimumSpringRate(ObjectProperty<SpringRate> springRate) {
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
        public Builder setMaximumSpringRates(ObjectProperty<SpringRate>[] springRates) {
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
        public Builder addMaximumSpringRate(ObjectProperty<SpringRate> springRate) {
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