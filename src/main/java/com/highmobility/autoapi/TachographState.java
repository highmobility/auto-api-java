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
import com.highmobility.autoapi.property.DriverCard;
import com.highmobility.autoapi.property.DriverTimeState;
import com.highmobility.autoapi.property.DriverWorkingState;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Tachograph State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class TachographState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TACHOGRAPH, 0x01);

    private static final byte VEHICLE_MOTION_DETECTED_IDENTIFIER = (byte) 0x04;
    private static final byte VEHICLE_OVERSPEED_IDENTIFIER = (byte) 0x05;
    private static final byte VEHICLE_DIRECTION_IDENTIFIER = (byte) 0x06;
    private static final byte VEHICLE_SPEED_IDENTIFIER = (byte) 0x07;

    DriverWorkingState[] driverWorkingStates;
    DriverTimeState[] driverTimeStates;
    DriverCard[] driverCards;
    Boolean vehicleMotionDetected;
    Boolean vehicleOverspeed;
    VehicleDirection vehicleDirection;
    Integer vehicleSpeed;

    /**
     * @return The driver working states.
     */
    public DriverWorkingState[] getDriverWorkingStates() {
        return driverWorkingStates;
    }

    /**
     * Get the driver working state for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver working state.
     */
    @Nullable public DriverWorkingState getDriverWorkingState(int driverNumber) {
        for (int i = 0; i < driverWorkingStates.length; i++) {
            DriverWorkingState state = driverWorkingStates[i];
            if (state.getDriverNumber() == driverNumber) return state;
        }
        return null;
    }

    /**
     * @return The driver time states.
     */
    public DriverTimeState[] getDriverTimeStates() {
        return driverTimeStates;
    }

    /**
     * Get the driver time state for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver time state.
     */
    @Nullable public DriverTimeState getDriverTimeState(int driverNumber) {
        for (int i = 0; i < driverTimeStates.length; i++) {
            DriverTimeState state = driverTimeStates[i];
            if (state.getDriverNumber() == driverNumber) return state;
        }
        return null;
    }

    /**
     * @return The driver cards.
     */
    public DriverCard[] getDriverCards() {
        return driverCards;
    }

    /**
     * Get the driver card for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver card.
     */
    @Nullable public DriverCard getDriverCard(int driverNumber) {
        for (int i = 0; i < driverCards.length; i++) {
            DriverCard state = driverCards[i];
            if (state.getDriverNumber() == driverNumber) return state;
        }
        return null;
    }

    /**
     * @return Whether vehicle motion is detected.
     */
    @Nullable public Boolean isVehicleMotionDetected() {
        return vehicleMotionDetected;
    }

    /**
     * @return Whether vehicle is overspeeding.
     */
    @Nullable public Boolean isVehicleOverspeeding() {
        return vehicleOverspeed;
    }

    /**
     * @return The vehicle direction.
     */
    @Nullable public VehicleDirection getVehicleDirection() {
        return vehicleDirection;
    }

    /**
     * @return The tachograph vehicle speed in km/h.
     */
    @Nullable public Integer getVehicleSpeed() {
        return vehicleSpeed;
    }

    TachographState(byte[] bytes) throws CommandParseException {
        super(bytes);

        List<DriverTimeState> timeStateBuilder = new ArrayList<>();
        List<DriverWorkingState> workingStateBuilder = new ArrayList<>();
        List<DriverCard> cardsBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DriverTimeState.IDENTIFIER:
                    timeStateBuilder.add(new DriverTimeState(property.getPropertyBytes()));
                    break;
                case DriverWorkingState.IDENTIFIER:
                    workingStateBuilder.add(new DriverWorkingState(property.getPropertyBytes()));
                    break;
                case DriverCard.IDENTIFIER:
                    cardsBuilder.add(new DriverCard(property.getPropertyBytes()));
                    break;
                case VEHICLE_MOTION_DETECTED_IDENTIFIER:
                    vehicleMotionDetected = Property.getBool(property.getValueByte());
                    break;
                case VEHICLE_OVERSPEED_IDENTIFIER:
                    vehicleOverspeed = Property.getBool(property.getValueByte());
                    break;
                case VEHICLE_DIRECTION_IDENTIFIER:
                    vehicleDirection = VehicleDirection.fromByte(property.getValueByte());
                    break;
                case VEHICLE_SPEED_IDENTIFIER:
                    vehicleSpeed = Property.getUnsignedInt(property.getValueBytes());
                    break;
            }
        }

        driverTimeStates = timeStateBuilder.toArray(new DriverTimeState[0]);
        driverWorkingStates = workingStateBuilder.toArray(new DriverWorkingState[0]);
        driverCards = cardsBuilder.toArray(new DriverCard[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private TachographState(Builder builder) {
        super(builder);

        this.driverTimeStates = builder.driverTimeStates.toArray(new DriverTimeState[0]);
        this.driverWorkingStates = builder.driverWorkingStates.toArray(new DriverWorkingState[0]);
        this.driverCards = builder.driverCards.toArray(new DriverCard[0]);
        this.vehicleMotionDetected = builder.vehicleMotionDetected;
        this.vehicleOverspeed = builder.vehicleOverspeed;
        this.vehicleDirection = builder.vehicleDirection;
        this.vehicleSpeed = builder.vehicleSpeed;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<DriverWorkingState> driverWorkingStates = new ArrayList<>();
        List<DriverTimeState> driverTimeStates = new ArrayList<>();
        List<DriverCard> driverCards = new ArrayList<>();
        Boolean vehicleMotionDetected;
        Boolean vehicleOverspeed;
        VehicleDirection vehicleDirection;
        Integer vehicleSpeed;

        /**
         * Add a single driver working state.
         *
         * @param driverWorkingState The driver working state.
         * @return The builder.
         */
        public Builder addDriverWorkingState(DriverWorkingState driverWorkingState) {
            this.driverWorkingStates.add(driverWorkingState);
            addProperty(driverWorkingState);
            return this;
        }

        /**
         * @param driverWorkingStates The driver working states.
         * @return The builder.
         */
        public Builder setDriverWorkingStates(DriverWorkingState[] driverWorkingStates) {
            this.driverWorkingStates = Arrays.asList(driverWorkingStates);
            for (DriverWorkingState driverWorkingState : driverWorkingStates) {
                addProperty(driverWorkingState);
            }
            return this;
        }

        /**
         * Add a single driver time state.
         *
         * @param driverTimeState The driver time state.
         * @return The builder.
         */
        public Builder addDriverTimeState(DriverTimeState driverTimeState) {
            this.driverTimeStates.add(driverTimeState);
            addProperty(driverTimeState);
            return this;
        }

        /**
         * @param driverTimeStates The driver time states.
         * @return The builder.
         */
        public Builder setDriverTimeStates(DriverTimeState[] driverTimeStates) {
            this.driverTimeStates = Arrays.asList(driverTimeStates);
            for (DriverTimeState driverTimeState : driverTimeStates) {
                addProperty(driverTimeState);
            }
            return this;
        }

        /**
         * Add a single driver card.
         *
         * @param driverCard The driver card.
         * @return The builder.
         */
        public Builder addDriverCard(DriverCard driverCard) {
            this.driverCards.add(driverCard);
            addProperty(driverCard);
            return this;
        }

        /**
         * @param driverCards The driver cards.
         * @return The builder.
         */
        public Builder setDriverCards(DriverCard[] driverCards) {
            this.driverCards = Arrays.asList(driverCards);
            for (DriverCard driverCard : driverCards) {
                addProperty(driverCard);
            }
            return this;
        }

        /**
         * @param vehicleMotionDetected Whether vehicle motion is detected.
         * @return The builder.
         */
        public Builder setVehicleMotionDetected(Boolean vehicleMotionDetected) {
            this.vehicleMotionDetected = vehicleMotionDetected;
            addProperty(new BooleanProperty(VEHICLE_MOTION_DETECTED_IDENTIFIER,
                    vehicleMotionDetected));
            return this;
        }

        /**
         * @param vehicleOverspeed The vehicle overspeed.
         * @return The builder.
         */
        public Builder setVehicleOverspeed(Boolean vehicleOverspeed) {
            this.vehicleOverspeed = vehicleOverspeed;
            addProperty(new BooleanProperty(VEHICLE_OVERSPEED_IDENTIFIER, vehicleOverspeed));
            return this;
        }

        /**
         * @param vehicleDirection The vehicle direction.
         * @return The builder.
         */
        public Builder setVehicleDirection(VehicleDirection vehicleDirection) {
            this.vehicleDirection = vehicleDirection;
            addProperty(vehicleDirection);
            return this;
        }

        /**
         * @param vehicleSpeed The tachograph vehicle speed in km/h.
         * @return The builder.
         */
        public Builder setVehicleSpeed(Integer vehicleSpeed) {
            this.vehicleSpeed = vehicleSpeed;
            addProperty(new IntegerProperty(VEHICLE_SPEED_IDENTIFIER, vehicleSpeed, 2));
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public TachographState build() {
            return new TachographState(this);
        }
    }

    public enum VehicleDirection implements HMProperty {
        FORWARD((byte) 0x00),
        REVERSE((byte) 0x01),;

        public static VehicleDirection fromByte(byte byteValue) throws CommandParseException {
            VehicleDirection[] values = VehicleDirection.values();

            for (int i = 0; i < values.length; i++) {
                VehicleDirection state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        VehicleDirection(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        @Override public byte getPropertyIdentifier() {
            return VEHICLE_DIRECTION_IDENTIFIER;
        }

        @Override public int getPropertyLength() {
            return 1;
        }

        @Override public byte[] getPropertyBytes() {
            return Property.getPropertyBytes(VEHICLE_DIRECTION_IDENTIFIER, value);
        }
    }
}
