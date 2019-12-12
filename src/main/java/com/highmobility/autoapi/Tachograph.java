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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.Detected;
import com.highmobility.autoapi.value.DriverCardPresent;
import com.highmobility.autoapi.value.DriverTimeState;
import com.highmobility.autoapi.value.DriverWorkingState;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Tachograph capability
 */
public class Tachograph {
    public static final int IDENTIFIER = Identifier.TACHOGRAPH;

    public static final byte PROPERTY_DRIVERS_WORKING_STATES = 0x01;
    public static final byte PROPERTY_DRIVERS_TIME_STATES = 0x02;
    public static final byte PROPERTY_DRIVERS_CARDS_PRESENT = 0x03;
    public static final byte PROPERTY_VEHICLE_MOTION = 0x04;
    public static final byte PROPERTY_VEHICLE_OVERSPEED = 0x05;
    public static final byte PROPERTY_VEHICLE_DIRECTION = 0x06;
    public static final byte PROPERTY_VEHICLE_SPEED = 0x07;

    /**
     * Get all tachograph properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }
    
    /**
     * Get specific tachograph properties
     */
    public static class GetProperties extends GetCommand {
        Bytes propertyIdentifiers;
    
        /**
         * @return The property identifiers.
         */
        public Bytes getPropertyIdentifiers() {
            return propertyIdentifiers;
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(COMMAND_TYPE_POSITION + 1, getLength());
        }
    }

    /**
     * The tachograph state
     */
    public static class State extends SetCommand {
        Property<DriverWorkingState>[] driversWorkingStates;
        Property<DriverTimeState>[] driversTimeStates;
        Property<DriverCardPresent>[] driversCardsPresent;
        Property<Detected> vehicleMotion = new Property(Detected.class, PROPERTY_VEHICLE_MOTION);
        Property<VehicleOverspeed> vehicleOverspeed = new Property(VehicleOverspeed.class, PROPERTY_VEHICLE_OVERSPEED);
        Property<VehicleDirection> vehicleDirection = new Property(VehicleDirection.class, PROPERTY_VEHICLE_DIRECTION);
        PropertyInteger vehicleSpeed = new PropertyInteger(PROPERTY_VEHICLE_SPEED, true);
    
        /**
         * @return The drivers working states
         */
        public Property<DriverWorkingState>[] getDriversWorkingStates() {
            return driversWorkingStates;
        }
    
        /**
         * @return The drivers time states
         */
        public Property<DriverTimeState>[] getDriversTimeStates() {
            return driversTimeStates;
        }
    
        /**
         * @return The drivers cards present
         */
        public Property<DriverCardPresent>[] getDriversCardsPresent() {
            return driversCardsPresent;
        }
    
        /**
         * @return The vehicle motion
         */
        public Property<Detected> getVehicleMotion() {
            return vehicleMotion;
        }
    
        /**
         * @return The vehicle overspeed
         */
        public Property<VehicleOverspeed> getVehicleOverspeed() {
            return vehicleOverspeed;
        }
    
        /**
         * @return The vehicle direction
         */
        public Property<VehicleDirection> getVehicleDirection() {
            return vehicleDirection;
        }
    
        /**
         * @return The tachograph vehicle speed in km/h
         */
        public PropertyInteger getVehicleSpeed() {
            return vehicleSpeed;
        }
    
        /**
         * Get the driver working state for a specific driver.
         *
         * @param driverNumber The driver number.
         * @return The driver working state.
         */
        @Nullable public Property<DriverWorkingState> getDriverWorkingState(int driverNumber) {
            for (int i = 0; i < driversWorkingStates.length; i++) {
                Property<DriverWorkingState> state = driversWorkingStates[i];
                if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                    return state;
            }
            return null;
        }
    
        /**
         * Get the driver time state for a specific driver.
         *
         * @param driverNumber The driver number.
         * @return The driver time state.
         */
        @Nullable public Property<DriverTimeState> getDriverTimeState(int driverNumber) {
            for (int i = 0; i < driversTimeStates.length; i++) {
                Property<DriverTimeState> state = driversTimeStates[i];
                if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                    return state;
            }
            return null;
        }
    
        /**
         * Get the driver card for a specific driver.
         *
         * @param driverNumber The driver number.
         * @return The driver card.
         */
        @Nullable public Property<DriverCardPresent> getDriverCard(int driverNumber) {
            for (int i = 0; i < driversCardsPresent.length; i++) {
                Property<DriverCardPresent> state = driversCardsPresent[i];
                if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                    return state;
            }
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> driversWorkingStatesBuilder = new ArrayList<>();
            ArrayList<Property> driversTimeStatesBuilder = new ArrayList<>();
            ArrayList<Property> driversCardsPresentBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVERS_WORKING_STATES:
                            Property<DriverWorkingState> driverWorkingState = new Property(DriverWorkingState.class, p);
                            driversWorkingStatesBuilder.add(driverWorkingState);
                            return driverWorkingState;
                        case PROPERTY_DRIVERS_TIME_STATES:
                            Property<DriverTimeState> driversTimeState = new Property(DriverTimeState.class, p);
                            driversTimeStatesBuilder.add(driversTimeState);
                            return driversTimeState;
                        case PROPERTY_DRIVERS_CARDS_PRESENT:
                            Property<DriverCardPresent> driversCardPresent = new Property(DriverCardPresent.class, p);
                            driversCardsPresentBuilder.add(driversCardPresent);
                            return driversCardPresent;
                        case PROPERTY_VEHICLE_MOTION: return vehicleMotion.update(p);
                        case PROPERTY_VEHICLE_OVERSPEED: return vehicleOverspeed.update(p);
                        case PROPERTY_VEHICLE_DIRECTION: return vehicleDirection.update(p);
                        case PROPERTY_VEHICLE_SPEED: return vehicleSpeed.update(p);
                    }
    
                    return null;
                });
            }
    
            driversWorkingStates = driversWorkingStatesBuilder.toArray(new Property[0]);
            driversTimeStates = driversTimeStatesBuilder.toArray(new Property[0]);
            driversCardsPresent = driversCardsPresentBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            driversWorkingStates = builder.driversWorkingStates.toArray(new Property[0]);
            driversTimeStates = builder.driversTimeStates.toArray(new Property[0]);
            driversCardsPresent = builder.driversCardsPresent.toArray(new Property[0]);
            vehicleMotion = builder.vehicleMotion;
            vehicleOverspeed = builder.vehicleOverspeed;
            vehicleDirection = builder.vehicleDirection;
            vehicleSpeed = builder.vehicleSpeed;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private List<Property> driversWorkingStates = new ArrayList<>();
            private List<Property> driversTimeStates = new ArrayList<>();
            private List<Property> driversCardsPresent = new ArrayList<>();
            private Property<Detected> vehicleMotion;
            private Property<VehicleOverspeed> vehicleOverspeed;
            private Property<VehicleDirection> vehicleDirection;
            private PropertyInteger vehicleSpeed;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of drivers working states.
             * 
             * @param driversWorkingStates The drivers working states
             * @return The builder
             */
            public Builder setDriversWorkingStates(Property<DriverWorkingState>[] driversWorkingStates) {
                this.driversWorkingStates.clear();
                for (int i = 0; i < driversWorkingStates.length; i++) {
                    addDriverWorkingState(driversWorkingStates[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single driver working state.
             * 
             * @param driverWorkingState The driver working state
             * @return The builder
             */
            public Builder addDriverWorkingState(Property<DriverWorkingState> driverWorkingState) {
                driverWorkingState.setIdentifier(PROPERTY_DRIVERS_WORKING_STATES);
                addProperty(driverWorkingState);
                driversWorkingStates.add(driverWorkingState);
                return this;
            }
            
            /**
             * Add an array of drivers time states.
             * 
             * @param driversTimeStates The drivers time states
             * @return The builder
             */
            public Builder setDriversTimeStates(Property<DriverTimeState>[] driversTimeStates) {
                this.driversTimeStates.clear();
                for (int i = 0; i < driversTimeStates.length; i++) {
                    addDriversTimeState(driversTimeStates[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single drivers time state.
             * 
             * @param driversTimeState The drivers time state
             * @return The builder
             */
            public Builder addDriversTimeState(Property<DriverTimeState> driversTimeState) {
                driversTimeState.setIdentifier(PROPERTY_DRIVERS_TIME_STATES);
                addProperty(driversTimeState);
                driversTimeStates.add(driversTimeState);
                return this;
            }
            
            /**
             * Add an array of drivers cards present.
             * 
             * @param driversCardsPresent The drivers cards present
             * @return The builder
             */
            public Builder setDriversCardsPresent(Property<DriverCardPresent>[] driversCardsPresent) {
                this.driversCardsPresent.clear();
                for (int i = 0; i < driversCardsPresent.length; i++) {
                    addDriversCardPresent(driversCardsPresent[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single drivers card present.
             * 
             * @param driversCardPresent The drivers card present
             * @return The builder
             */
            public Builder addDriversCardPresent(Property<DriverCardPresent> driversCardPresent) {
                driversCardPresent.setIdentifier(PROPERTY_DRIVERS_CARDS_PRESENT);
                addProperty(driversCardPresent);
                driversCardsPresent.add(driversCardPresent);
                return this;
            }
            
            /**
             * @param vehicleMotion The vehicle motion
             * @return The builder
             */
            public Builder setVehicleMotion(Property<Detected> vehicleMotion) {
                this.vehicleMotion = vehicleMotion.setIdentifier(PROPERTY_VEHICLE_MOTION);
                addProperty(this.vehicleMotion);
                return this;
            }
            
            /**
             * @param vehicleOverspeed The vehicle overspeed
             * @return The builder
             */
            public Builder setVehicleOverspeed(Property<VehicleOverspeed> vehicleOverspeed) {
                this.vehicleOverspeed = vehicleOverspeed.setIdentifier(PROPERTY_VEHICLE_OVERSPEED);
                addProperty(this.vehicleOverspeed);
                return this;
            }
            
            /**
             * @param vehicleDirection The vehicle direction
             * @return The builder
             */
            public Builder setVehicleDirection(Property<VehicleDirection> vehicleDirection) {
                this.vehicleDirection = vehicleDirection.setIdentifier(PROPERTY_VEHICLE_DIRECTION);
                addProperty(this.vehicleDirection);
                return this;
            }
            
            /**
             * @param vehicleSpeed The tachograph vehicle speed in km/h
             * @return The builder
             */
            public Builder setVehicleSpeed(Property<Integer> vehicleSpeed) {
                this.vehicleSpeed = new PropertyInteger(PROPERTY_VEHICLE_SPEED, true, 2, vehicleSpeed);
                addProperty(this.vehicleSpeed);
                return this;
            }
        }
    }

    public enum VehicleOverspeed implements ByteEnum {
        NO_OVERSPEED((byte) 0x00),
        OVERSPEED((byte) 0x01);
    
        public static VehicleOverspeed fromByte(byte byteValue) throws CommandParseException {
            VehicleOverspeed[] values = VehicleOverspeed.values();
    
            for (int i = 0; i < values.length; i++) {
                VehicleOverspeed state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        VehicleOverspeed(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
    
    public enum VehicleDirection implements ByteEnum {
        FORWARD((byte) 0x00),
        REVERSE((byte) 0x01);
    
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
    
        @Override public byte getByte() {
            return value;
        }
    }
}