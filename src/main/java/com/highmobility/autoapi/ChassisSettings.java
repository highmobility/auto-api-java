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

import com.highmobility.autoapi.property.value.Axle;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.SpringRateProperty;

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
    Boolean sportChronoActive;

    SpringRateProperty[] currentSpringRates;
    SpringRateProperty[] maximumSpringRates;
    SpringRateProperty[] minimumSpringRates;

    Integer currentChassisPosition;
    Integer maximumChassisPosition;
    Integer minimumChassisPosition;

    /**
     * @return The driving mode.
     */
    @Nullable public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return Boolean indicating whether the sport chronometer is active.
     */
    @Nullable public Boolean isSportChronoActive() {
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
    @Nullable public Integer getCurrentChassisPosition() {
        return currentChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    @Nullable public Integer getMaximumChassisPosition() {
        return maximumChassisPosition;
    }

    /**
     * @return The minimum possible value for the chassis position.
     */
    @Nullable public Integer getMinimumChassisPosition() {
        return minimumChassisPosition;
    }

    public ChassisSettings(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<SpringRateProperty> currentSpringRates = new ArrayList<>();
        ArrayList<SpringRateProperty> minimumSpringRates = new ArrayList<>();
        ArrayList<SpringRateProperty> maximumSpringRates = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DRIVING_MODE_IDENTIFIER:
                    drivingMode = DrivingMode.fromByte(property.getValueByte());
                    break;
                case SPORT_CHRONO_ACTIVE_IDENTIFIER:
                    sportChronoActive = Property.getBool(property.getValueByte());
                    break;
                case CURRENT_SPRING_RATE_PROPERTIES_IDENTIFIER:
                    currentSpringRates.add(new SpringRateProperty(property.getPropertyBytes()));
                    break;
                case MAXIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                    maximumSpringRates.add(new SpringRateProperty(property.getPropertyBytes()));
                    break;
                case MINIMUM_SPRING_RATE_PROPERTIES_IDENTIFIER:
                    minimumSpringRates.add(new SpringRateProperty(property.getPropertyBytes()));
                    break;
                case CURRENT_CHASSIS_POSITION_IDENTIFIER:
                    currentChassisPosition = Property.getSignedInt(property.getValueByte());
                    break;
                case MAXIMUM_CHASSIS_POSITION_IDENTIFIER:
                    maximumChassisPosition = Property.getSignedInt(property.getValueByte());
                    break;
                case MINIMUM_CHASSIS_POSITION_IDENTIFIER:
                    minimumChassisPosition = Property.getSignedInt(property.getValueByte());
                    break;
            }
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
        private Boolean sportChronoActive;

        List<SpringRateProperty> currentSpringRates = new ArrayList<>();
        List<SpringRateProperty> maximumSpringRates = new ArrayList<>();
        List<SpringRateProperty> minimumSpringRates = new ArrayList<>();

        Integer currentChassisPosition;
        Integer maximumChassisPosition;
        Integer minimumChassisPosition;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param drivingMode Set the driving mode.
         * @return The builder.
         */
        public Builder setDrivingMode(DrivingMode drivingMode) {
            this.drivingMode = drivingMode;
            addProperty(drivingMode);
            return this;
        }

        /**
         * @param sportChronoActive Set the sport chronometer state.
         * @return The builder.
         */
        public Builder setSportChronoActive(Boolean sportChronoActive) {
            this.sportChronoActive = sportChronoActive;
            addProperty(new BooleanProperty(SPORT_CHRONO_ACTIVE_IDENTIFIER, sportChronoActive));
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setCurrentChassisPosition(Integer chassisPosition) {
            this.currentChassisPosition = chassisPosition;
            addProperty(new IntegerProperty(CURRENT_CHASSIS_POSITION_IDENTIFIER, chassisPosition,
                    1));
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMinimumChassisPosition(Integer chassisPosition) {
            this.minimumChassisPosition = chassisPosition;
            addProperty(new IntegerProperty(MINIMUM_CHASSIS_POSITION_IDENTIFIER, chassisPosition,
                    1));
            return this;
        }

        /**
         * @param chassisPosition The chassis position.
         * @return The builder.
         */
        public Builder setMaximumChassisPosition(Integer chassisPosition) {
            this.maximumChassisPosition = chassisPosition;
            addProperty(new IntegerProperty(MAXIMUM_CHASSIS_POSITION_IDENTIFIER, chassisPosition,
                    1));
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