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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.tachograph.DriverCard;
import com.highmobility.autoapi.value.tachograph.DriverTimeState;
import com.highmobility.autoapi.value.tachograph.DriverWorkingState;
import com.highmobility.autoapi.value.tachograph.VehicleDirection;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Tachograph State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class TachographState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TACHOGRAPH, 0x01);

    public static final byte IDENTIFIER_DRIVER_WORKING_STATE = 0x01;
    public static final byte IDENTIFIER_DRIVER_TIME_STATE = 0x02;
    public static final byte IDENTIFIER_DRIVER_CARD = 0x03;

    private static final byte IDENTIFIER_VEHICLE_MOTION_DETECTED = (byte) 0x04;
    private static final byte IDENTIFIER_VEHICLE_OVERSPEED = (byte) 0x05;
    private static final byte IDENTIFIER_VEHICLE_DIRECTION = (byte) 0x06;
    private static final byte IDENTIFIER_VEHICLE_SPEED = (byte) 0x07;

    Property<DriverWorkingState>[] driverWorkingStates;
    Property<DriverTimeState>[] driverTimeStates;
    Property<DriverCard>[] driverCards;
    Property<Boolean> vehicleMotionDetected = new Property(Boolean.class,
            IDENTIFIER_VEHICLE_MOTION_DETECTED);
    Property<Boolean> vehicleOverspeed = new Property(Boolean.class, IDENTIFIER_VEHICLE_OVERSPEED);
    Property<VehicleDirection> vehicleDirection = new Property(VehicleDirection.class,
            IDENTIFIER_VEHICLE_DIRECTION);
    Property<Integer> vehicleSpeed = new PropertyInteger(IDENTIFIER_VEHICLE_SPEED, false);

    /**
     * @return The driver working states.
     */
    public Property<DriverWorkingState>[] getDriverWorkingStates() {
        return driverWorkingStates;
    }

    /**
     * Get the driver working state for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver working state.
     */
    @Nullable public Property<DriverWorkingState> getDriverWorkingState(int driverNumber) {
        for (int i = 0; i < driverWorkingStates.length; i++) {
            Property<DriverWorkingState> state = driverWorkingStates[i];
            if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                return state;
        }
        return null;
    }

    /**
     * @return The driver time states.
     */
    public Property<DriverTimeState>[] getDriverTimeStates() {
        return driverTimeStates;
    }

    /**
     * Get the driver time state for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver time state.
     */
    @Nullable public Property<DriverTimeState> getDriverTimeState(int driverNumber) {
        for (int i = 0; i < driverTimeStates.length; i++) {
            Property<DriverTimeState> state = driverTimeStates[i];
            if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                return state;
        }
        return null;
    }

    /**
     * @return The driver cards.
     */
    public Property<DriverCard>[] getDriverCards() {
        return driverCards;
    }

    /**
     * Get the driver card for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver card.
     */
    @Nullable public Property<DriverCard> getDriverCard(int driverNumber) {
        for (int i = 0; i < driverCards.length; i++) {
            Property<DriverCard> state = driverCards[i];
            if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                return state;
        }
        return null;
    }

    /**
     * @return Whether vehicle motion is detected.
     */
    public Property<Boolean> isVehicleMotionDetected() {
        return vehicleMotionDetected;
    }

    /**
     * @return Whether vehicle is overspeeding.
     */
    public Property<Boolean> isVehicleOverspeeding() {
        return vehicleOverspeed;
    }

    /**
     * @return The vehicle direction.
     */
    public Property<VehicleDirection> getVehicleDirection() {
        return vehicleDirection;
    }

    /**
     * @return The tachograph vehicle speed in km/h.
     */
    public Property<Integer> getVehicleSpeed() {
        return vehicleSpeed;
    }

    TachographState(byte[] bytes) {
        super(bytes);

        List<Property> timeStateBuilder = new ArrayList<>();
        List<Property> workingStateBuilder = new ArrayList<>();
        List<Property> cardsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DRIVER_TIME_STATE:
                        Property driverTimeState = new Property(DriverTimeState.class, p);
                        timeStateBuilder.add(driverTimeState);
                        return driverTimeState;
                    case IDENTIFIER_DRIVER_WORKING_STATE:
                        Property driverWorkingState = new Property(DriverWorkingState.class, p);
                        workingStateBuilder.add(driverWorkingState);
                        return driverWorkingState;
                    case IDENTIFIER_DRIVER_CARD:
                        Property driverCard = new Property(DriverCard.class, p);
                        cardsBuilder.add(driverCard);
                        return driverCard;
                    case IDENTIFIER_VEHICLE_MOTION_DETECTED:
                        return vehicleMotionDetected.update(p);
                    case IDENTIFIER_VEHICLE_OVERSPEED:
                        return vehicleOverspeed.update(p);
                    case IDENTIFIER_VEHICLE_DIRECTION:
                        return vehicleDirection.update(p);
                    case IDENTIFIER_VEHICLE_SPEED:
                        return vehicleSpeed.update(p);
                }

                return null;
            });
        }

        driverTimeStates = timeStateBuilder.toArray(new Property[0]);
        driverWorkingStates = workingStateBuilder.toArray(new Property[0]);
        driverCards = cardsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private TachographState(Builder builder) {
        super(builder);

        this.driverTimeStates = builder.driverTimeStates.toArray(new Property[0]);
        this.driverWorkingStates = builder.driverWorkingStates.toArray(new Property[0]);
        this.driverCards = builder.driverCards.toArray(new Property[0]);
        this.vehicleMotionDetected = builder.vehicleMotionDetected;
        this.vehicleOverspeed = builder.vehicleOverspeed;
        this.vehicleDirection = builder.vehicleDirection;
        this.vehicleSpeed = builder.vehicleSpeed;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<Property<DriverWorkingState>> driverWorkingStates = new ArrayList<>();
        List<Property<DriverTimeState>> driverTimeStates = new ArrayList<>();
        List<Property<DriverCard>> driverCards = new ArrayList<>();
        Property<Boolean> vehicleMotionDetected;
        Property<Boolean> vehicleOverspeed;
        Property<VehicleDirection> vehicleDirection;
        PropertyInteger vehicleSpeed;

        /**
         * Add a single driver working state.
         *
         * @param driverWorkingState The driver working state.
         * @return The builder.
         */
        public Builder addDriverWorkingState(Property<DriverWorkingState> driverWorkingState) {
            this.driverWorkingStates.add(driverWorkingState.setIdentifier(IDENTIFIER_DRIVER_WORKING_STATE));
            addProperty(driverWorkingState);
            return this;
        }

        /**
         * @param driverWorkingStates The driver working states.
         * @return The builder.
         */
        public Builder setDriverWorkingStates(Property<DriverWorkingState>[] driverWorkingStates) {
            this.driverWorkingStates.clear();
            for (Property<DriverWorkingState> driverWorkingState : driverWorkingStates) {
                addDriverWorkingState(driverWorkingState);
            }
            return this;
        }

        /**
         * Add a single driver time state.
         *
         * @param driverTimeState The driver time state.
         * @return The builder.
         */
        public Builder addDriverTimeState(Property<DriverTimeState> driverTimeState) {
            this.driverTimeStates.add(driverTimeState.setIdentifier(IDENTIFIER_DRIVER_TIME_STATE));
            addProperty(driverTimeState);
            return this;
        }

        /**
         * @param driverTimeStates The driver time states.
         * @return The builder.
         */
        public Builder setDriverTimeStates(Property<DriverTimeState>[] driverTimeStates) {
            this.driverTimeStates.clear();
            for (Property<DriverTimeState> driverTimeState : driverTimeStates) {
                addDriverTimeState(driverTimeState);
            }
            return this;
        }

        /**
         * Add a single driver card.
         *
         * @param driverCard The driver card.
         * @return The builder.
         */
        public Builder addDriverCard(Property<DriverCard> driverCard) {
            this.driverCards.add(driverCard.setIdentifier(IDENTIFIER_DRIVER_CARD));
            addProperty(driverCard);
            return this;
        }

        /**
         * @param driverCards The driver cards.
         * @return The builder.
         */
        public Builder setDriverCards(Property<DriverCard>[] driverCards) {
            this.driverCards.clear();

            for (Property<DriverCard> driverCard : driverCards) {
                addDriverCard(driverCard);
            }

            return this;
        }

        /**
         * @param vehicleMotionDetected Whether vehicle motion is detected.
         * @return The builder.
         */
        public Builder setVehicleMotionDetected(Property<Boolean> vehicleMotionDetected) {
            this.vehicleMotionDetected = vehicleMotionDetected;
            vehicleMotionDetected.setIdentifier(IDENTIFIER_VEHICLE_MOTION_DETECTED);
            addProperty(vehicleMotionDetected);
            return this;
        }

        /**
         * @param vehicleOverSpeed The vehicle overspeed.
         * @return The builder.
         */
        public Builder setVehicleOverspeed(Property<Boolean> vehicleOverSpeed) {
            this.vehicleOverspeed = vehicleOverSpeed;
            vehicleOverSpeed.setIdentifier(IDENTIFIER_VEHICLE_OVERSPEED);
            addProperty(vehicleOverSpeed);
            return this;
        }

        /**
         * @param vehicleDirection The vehicle direction.
         * @return The builder.
         */
        public Builder setVehicleDirection(Property<VehicleDirection> vehicleDirection) {
            this.vehicleDirection = vehicleDirection;
            addProperty(vehicleDirection.setIdentifier(IDENTIFIER_VEHICLE_DIRECTION));
            return this;
        }

        /**
         * @param vehicleSpeed The tachograph vehicle speed in km/h.
         * @return The builder.
         */
        public Builder setVehicleSpeed(Property<Integer> vehicleSpeed) {
            this.vehicleSpeed = new PropertyInteger(IDENTIFIER_VEHICLE_SPEED, false, 2,
                    vehicleSpeed);
            addProperty(this.vehicleSpeed);
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public TachographState build() {
            return new TachographState(this);
        }
    }
}
