/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Detected;
import com.highmobility.autoapi.value.DriverCardPresent;
import com.highmobility.autoapi.value.DriverTimeState;
import com.highmobility.autoapi.value.DriverWorkingState;
import com.highmobility.autoapi.value.measurement.Speed;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

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
     * Get Tachograph property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Tachograph property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Tachograph property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Tachograph property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Tachograph properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Tachograph properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Tachograph properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Tachograph properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Tachograph properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The tachograph state
     */
    public static class State extends SetCommand {
        List<Property<DriverWorkingState>> driversWorkingStates;
        List<Property<DriverTimeState>> driversTimeStates;
        List<Property<DriverCardPresent>> driversCardsPresent;
        Property<Detected> vehicleMotion = new Property<>(Detected.class, PROPERTY_VEHICLE_MOTION);
        Property<VehicleOverspeed> vehicleOverspeed = new Property<>(VehicleOverspeed.class, PROPERTY_VEHICLE_OVERSPEED);
        Property<VehicleDirection> vehicleDirection = new Property<>(VehicleDirection.class, PROPERTY_VEHICLE_DIRECTION);
        Property<Speed> vehicleSpeed = new Property<>(Speed.class, PROPERTY_VEHICLE_SPEED);
    
        /**
         * @return The drivers working states
         */
        public List<Property<DriverWorkingState>> getDriversWorkingStates() {
            return driversWorkingStates;
        }
    
        /**
         * @return The drivers time states
         */
        public List<Property<DriverTimeState>> getDriversTimeStates() {
            return driversTimeStates;
        }
    
        /**
         * @return The drivers cards present
         */
        public List<Property<DriverCardPresent>> getDriversCardsPresent() {
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
         * @return The tachograph vehicle speed
         */
        public Property<Speed> getVehicleSpeed() {
            return vehicleSpeed;
        }
    
        /**
         * Get the driver working state for a specific driver.
         *
         * @param driverNumber The driver number.
         * @return The driver working state.
         */
        @Nullable public Property<DriverWorkingState> getDriverWorkingState(int driverNumber) {
            for (int i = 0; i < driversWorkingStates.size(); i++) {
                Property<DriverWorkingState> state = driversWorkingStates.get(i);
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
            for (int i = 0; i < driversTimeStates.size(); i++) {
                Property<DriverTimeState> state = driversTimeStates.get(i);
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
            for (int i = 0; i < driversCardsPresent.size(); i++) {
                Property<DriverCardPresent> state = driversCardsPresent.get(i);
                if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                    return state;
            }
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
    
            final ArrayList<Property<DriverWorkingState>> driversWorkingStatesBuilder = new ArrayList<>();
            final ArrayList<Property<DriverTimeState>> driversTimeStatesBuilder = new ArrayList<>();
            final ArrayList<Property<DriverCardPresent>> driversCardsPresentBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DRIVERS_WORKING_STATES:
                            Property<DriverWorkingState> driverWorkingState = new Property<>(DriverWorkingState.class, p);
                            driversWorkingStatesBuilder.add(driverWorkingState);
                            return driverWorkingState;
                        case PROPERTY_DRIVERS_TIME_STATES:
                            Property<DriverTimeState> driversTimeState = new Property<>(DriverTimeState.class, p);
                            driversTimeStatesBuilder.add(driversTimeState);
                            return driversTimeState;
                        case PROPERTY_DRIVERS_CARDS_PRESENT:
                            Property<DriverCardPresent> driversCardPresent = new Property<>(DriverCardPresent.class, p);
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
    
            driversWorkingStates = driversWorkingStatesBuilder;
            driversTimeStates = driversTimeStatesBuilder;
            driversCardsPresent = driversCardsPresentBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            driversWorkingStates = builder.driversWorkingStates;
            driversTimeStates = builder.driversTimeStates;
            driversCardsPresent = builder.driversCardsPresent;
            vehicleMotion = builder.vehicleMotion;
            vehicleOverspeed = builder.vehicleOverspeed;
            vehicleDirection = builder.vehicleDirection;
            vehicleSpeed = builder.vehicleSpeed;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private final List<Property<DriverWorkingState>> driversWorkingStates = new ArrayList<>();
            private final List<Property<DriverTimeState>> driversTimeStates = new ArrayList<>();
            private final List<Property<DriverCardPresent>> driversCardsPresent = new ArrayList<>();
            private Property<Detected> vehicleMotion;
            private Property<VehicleOverspeed> vehicleOverspeed;
            private Property<VehicleDirection> vehicleDirection;
            private Property<Speed> vehicleSpeed;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of drivers working states
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
             * Add a single driver working state
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
             * Add an array of drivers time states
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
             * Add a single drivers time state
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
             * Add an array of drivers cards present
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
             * Add a single drivers card present
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
             * @param vehicleSpeed The tachograph vehicle speed
             * @return The builder
             */
            public Builder setVehicleSpeed(Property<Speed> vehicleSpeed) {
                this.vehicleSpeed = vehicleSpeed.setIdentifier(PROPERTY_VEHICLE_SPEED);
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(VehicleOverspeed.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(VehicleDirection.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        VehicleDirection(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}