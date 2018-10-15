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

import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ChassisPositionProperty;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.SpringRateProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Command sent when a Get Chassis Settings is received by the car.
 */
public class ChassisSettings extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x01);

    public static final byte DRIVING_MODE_IDENTIFIER = 0x01;
    public static final byte SPORT_CHRONO_ACTIVE_IDENTIFIER = 0x02;
    public static final byte SPRING_RATE_PROPERTIES_IDENTIFIER = 0x03;
    public static final byte CHASSIS_POSITION_IDENTIFIER = 0x04;

    DrivingMode drivingMode;
    Boolean sportChronoActive;
    SpringRateProperty[] springRates;
    ChassisPositionProperty chassisPosition;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return Boolean indicating whether the sport chronometer is active.
     */
    public Boolean isSportChronoActive() {
        return sportChronoActive;
    }

    /**
     * @param axle The spring rate's axle.
     * @return The spring rate for the given axle. Null if does not exists.
     */
    public SpringRateProperty getSpringRate(Axle axle) {
        for (int i = 0; i < springRates.length; i++) {
            SpringRateProperty property = springRates[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @return All of the spring rates.
     */
    public SpringRateProperty[] getSpringRates() {
        return springRates;
    }

    /**
     * @return The chassis position
     */
    public ChassisPositionProperty getChassisPosition() {
        return chassisPosition;
    }

    public ChassisSettings(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<SpringRateProperty> springRateProperties = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DRIVING_MODE_IDENTIFIER:
                    drivingMode = DrivingMode.fromByte(property.getValueByte());
                    break;
                case SPORT_CHRONO_ACTIVE_IDENTIFIER:
                    sportChronoActive = Property.getBool(property.getValueByte());
                    break;
                case SPRING_RATE_PROPERTIES_IDENTIFIER:
                    springRateProperties.add(new SpringRateProperty(property.getPropertyBytes()));
                    break;
                case CHASSIS_POSITION_IDENTIFIER:
                    chassisPosition = new ChassisPositionProperty(property.getPropertyBytes());
                    break;
            }
        }

        springRates = springRateProperties.toArray(new SpringRateProperty[springRateProperties
                .size()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChassisSettings(Builder builder) {
        super(builder);
        drivingMode = builder.drivingMode;
        sportChronoActive = builder.sportChronoActive;
        chassisPosition = builder.chassisPosition;
        springRates = builder.springRates.toArray(new SpringRateProperty[builder.springRates
                .size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private DrivingMode drivingMode;
        private Boolean sportChronoActive;
        private List<SpringRateProperty> springRates = new ArrayList<>();
        private ChassisPositionProperty chassisPosition;

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
        public Builder setChassisPosition(ChassisPositionProperty chassisPosition) {
            this.chassisPosition = chassisPosition;
            chassisPosition.setIdentifier(CHASSIS_POSITION_IDENTIFIER);
            addProperty(chassisPosition);
            return this;
        }

        /**
         * Set the spring rates.
         *
         * @param springRates The spring rates.
         * @return The builder.
         */
        public Builder setSpringRates(SpringRateProperty[] springRates) {
            this.springRates = Arrays.asList(springRates);

            for (int i = 0; i < springRates.length; i++) {
                springRates[i].setIdentifier(SPRING_RATE_PROPERTIES_IDENTIFIER);
                addProperty(springRates[i]);
            }

            return this;
        }

        /**
         * Add a single spring rate.
         *
         * @param springRate A spring rate.
         * @return The builder.
         */
        public Builder addSpringRate(SpringRateProperty springRate) {
            springRate.setIdentifier(SPRING_RATE_PROPERTIES_IDENTIFIER);
            this.springRates.add(springRate);
            addProperty(springRate);
            return this;
        }

        public ChassisSettings build() {
            return new ChassisSettings(this);
        }
    }
}