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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.LaneKeepAssistState;
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.autoapi.value.ParkAssist;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The ADAS capability
 */
public class Adas {
    public static final int IDENTIFIER = Identifier.ADAS;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_ALERTNESS_SYSTEM_STATUS = 0x02;
    public static final byte PROPERTY_FORWARD_COLLISION_WARNING_SYSTEM = 0x03;
    public static final byte PROPERTY_BLIND_SPOT_WARNING_STATE = 0x04;
    public static final byte PROPERTY_BLIND_SPOT_WARNING_SYSTEM_COVERAGE = 0x05;
    public static final byte PROPERTY_REAR_CROSS_WARNING_SYSTEM = 0x06;
    public static final byte PROPERTY_AUTOMATED_PARKING_BRAKE = 0x07;
    public static final byte PROPERTY_LANE_KEEP_ASSIST_SYSTEM = 0x08;
    public static final byte PROPERTY_LANE_KEEP_ASSISTS_STATES = 0x09;
    public static final byte PROPERTY_PARK_ASSISTS = 0x0a;
    public static final byte PROPERTY_BLIND_SPOT_WARNING_SYSTEM = 0x0b;

    /**
     * Get ADAS property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every ADAS property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific ADAS property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific ADAS property availabilities
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
     * Get ADAS properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all ADAS properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific ADAS properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific ADAS properties
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
     * Get specific ADAS properties
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
     * The adas state
     */
    public static class State extends SetCommand {
        Property<OnOffState> status = new Property<>(OnOffState.class, PROPERTY_STATUS);
        Property<ActiveState> alertnessSystemStatus = new Property<>(ActiveState.class, PROPERTY_ALERTNESS_SYSTEM_STATUS);
        Property<ActiveState> forwardCollisionWarningSystem = new Property<>(ActiveState.class, PROPERTY_FORWARD_COLLISION_WARNING_SYSTEM);
        Property<ActiveState> blindSpotWarningState = new Property<>(ActiveState.class, PROPERTY_BLIND_SPOT_WARNING_STATE);
        Property<BlindSpotWarningSystemCoverage> blindSpotWarningSystemCoverage = new Property<>(BlindSpotWarningSystemCoverage.class, PROPERTY_BLIND_SPOT_WARNING_SYSTEM_COVERAGE);
        Property<ActiveState> rearCrossWarningSystem = new Property<>(ActiveState.class, PROPERTY_REAR_CROSS_WARNING_SYSTEM);
        Property<ActiveState> automatedParkingBrake = new Property<>(ActiveState.class, PROPERTY_AUTOMATED_PARKING_BRAKE);
        Property<OnOffState> laneKeepAssistSystem = new Property<>(OnOffState.class, PROPERTY_LANE_KEEP_ASSIST_SYSTEM);
        List<Property<LaneKeepAssistState>> laneKeepAssistsStates;
        List<Property<ParkAssist>> parkAssists;
        Property<OnOffState> blindSpotWarningSystem = new Property<>(OnOffState.class, PROPERTY_BLIND_SPOT_WARNING_SYSTEM);
    
        /**
         * @return Indicates whether the driver assistance system is active or not.
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
    
        /**
         * @return Indicates if the driver alertness warning is active or inactive.
         */
        public Property<ActiveState> getAlertnessSystemStatus() {
            return alertnessSystemStatus;
        }
    
        /**
         * @return Indicates whether the forward collision warning system is active or inactive.
         */
        public Property<ActiveState> getForwardCollisionWarningSystem() {
            return forwardCollisionWarningSystem;
        }
    
        /**
         * @return Indicates whether the blind spot warning system is active or not.
         */
        public Property<ActiveState> getBlindSpotWarningState() {
            return blindSpotWarningState;
        }
    
        /**
         * @return Blind spot warning system coverage.
         */
        public Property<BlindSpotWarningSystemCoverage> getBlindSpotWarningSystemCoverage() {
            return blindSpotWarningSystemCoverage;
        }
    
        /**
         * @return Indicates whether the rear cross warning system is active or not.
         */
        public Property<ActiveState> getRearCrossWarningSystem() {
            return rearCrossWarningSystem;
        }
    
        /**
         * @return Automatic brake state
         */
        public Property<ActiveState> getAutomatedParkingBrake() {
            return automatedParkingBrake;
        }
    
        /**
         * @return Indicates if the lane keep assist system is turned on or not.
         */
        public Property<OnOffState> getLaneKeepAssistSystem() {
            return laneKeepAssistSystem;
        }
    
        /**
         * @return Lane keeping assist state indicating the vehicle is actively controlling the wheels.
         */
        public List<Property<LaneKeepAssistState>> getLaneKeepAssistsStates() {
            return laneKeepAssistsStates;
        }
    
        /**
         * @return If the alarm is active and the driver has muted or not park assists.
         */
        public List<Property<ParkAssist>> getParkAssists() {
            return parkAssists;
        }
    
        /**
         * @return Indicates whether the blind spot warning system is turned on or not.
         */
        public Property<OnOffState> getBlindSpotWarningSystem() {
            return blindSpotWarningSystem;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
    
            final ArrayList<Property<LaneKeepAssistState>> laneKeepAssistsStatesBuilder = new ArrayList<>();
            final ArrayList<Property<ParkAssist>> parkAssistsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_ALERTNESS_SYSTEM_STATUS: return alertnessSystemStatus.update(p);
                        case PROPERTY_FORWARD_COLLISION_WARNING_SYSTEM: return forwardCollisionWarningSystem.update(p);
                        case PROPERTY_BLIND_SPOT_WARNING_STATE: return blindSpotWarningState.update(p);
                        case PROPERTY_BLIND_SPOT_WARNING_SYSTEM_COVERAGE: return blindSpotWarningSystemCoverage.update(p);
                        case PROPERTY_REAR_CROSS_WARNING_SYSTEM: return rearCrossWarningSystem.update(p);
                        case PROPERTY_AUTOMATED_PARKING_BRAKE: return automatedParkingBrake.update(p);
                        case PROPERTY_LANE_KEEP_ASSIST_SYSTEM: return laneKeepAssistSystem.update(p);
                        case PROPERTY_LANE_KEEP_ASSISTS_STATES:
                            Property<LaneKeepAssistState> laneKeepAssistsState = new Property<>(LaneKeepAssistState.class, p);
                            laneKeepAssistsStatesBuilder.add(laneKeepAssistsState);
                            return laneKeepAssistsState;
                        case PROPERTY_PARK_ASSISTS:
                            Property<ParkAssist> parkAssist = new Property<>(ParkAssist.class, p);
                            parkAssistsBuilder.add(parkAssist);
                            return parkAssist;
                        case PROPERTY_BLIND_SPOT_WARNING_SYSTEM: return blindSpotWarningSystem.update(p);
                    }
    
                    return null;
                });
            }
    
            laneKeepAssistsStates = laneKeepAssistsStatesBuilder;
            parkAssists = parkAssistsBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            alertnessSystemStatus = builder.alertnessSystemStatus;
            forwardCollisionWarningSystem = builder.forwardCollisionWarningSystem;
            blindSpotWarningState = builder.blindSpotWarningState;
            blindSpotWarningSystemCoverage = builder.blindSpotWarningSystemCoverage;
            rearCrossWarningSystem = builder.rearCrossWarningSystem;
            automatedParkingBrake = builder.automatedParkingBrake;
            laneKeepAssistSystem = builder.laneKeepAssistSystem;
            laneKeepAssistsStates = builder.laneKeepAssistsStates;
            parkAssists = builder.parkAssists;
            blindSpotWarningSystem = builder.blindSpotWarningSystem;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<OnOffState> status;
            private Property<ActiveState> alertnessSystemStatus;
            private Property<ActiveState> forwardCollisionWarningSystem;
            private Property<ActiveState> blindSpotWarningState;
            private Property<BlindSpotWarningSystemCoverage> blindSpotWarningSystemCoverage;
            private Property<ActiveState> rearCrossWarningSystem;
            private Property<ActiveState> automatedParkingBrake;
            private Property<OnOffState> laneKeepAssistSystem;
            private final List<Property<LaneKeepAssistState>> laneKeepAssistsStates = new ArrayList<>();
            private final List<Property<ParkAssist>> parkAssists = new ArrayList<>();
            private Property<OnOffState> blindSpotWarningSystem;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param status Indicates whether the driver assistance system is active or not.
             * @return The builder
             */
            public Builder setStatus(Property<OnOffState> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param alertnessSystemStatus Indicates if the driver alertness warning is active or inactive.
             * @return The builder
             */
            public Builder setAlertnessSystemStatus(Property<ActiveState> alertnessSystemStatus) {
                this.alertnessSystemStatus = alertnessSystemStatus.setIdentifier(PROPERTY_ALERTNESS_SYSTEM_STATUS);
                addProperty(this.alertnessSystemStatus);
                return this;
            }
            
            /**
             * @param forwardCollisionWarningSystem Indicates whether the forward collision warning system is active or inactive.
             * @return The builder
             */
            public Builder setForwardCollisionWarningSystem(Property<ActiveState> forwardCollisionWarningSystem) {
                this.forwardCollisionWarningSystem = forwardCollisionWarningSystem.setIdentifier(PROPERTY_FORWARD_COLLISION_WARNING_SYSTEM);
                addProperty(this.forwardCollisionWarningSystem);
                return this;
            }
            
            /**
             * @param blindSpotWarningState Indicates whether the blind spot warning system is active or not.
             * @return The builder
             */
            public Builder setBlindSpotWarningState(Property<ActiveState> blindSpotWarningState) {
                this.blindSpotWarningState = blindSpotWarningState.setIdentifier(PROPERTY_BLIND_SPOT_WARNING_STATE);
                addProperty(this.blindSpotWarningState);
                return this;
            }
            
            /**
             * @param blindSpotWarningSystemCoverage Blind spot warning system coverage.
             * @return The builder
             */
            public Builder setBlindSpotWarningSystemCoverage(Property<BlindSpotWarningSystemCoverage> blindSpotWarningSystemCoverage) {
                this.blindSpotWarningSystemCoverage = blindSpotWarningSystemCoverage.setIdentifier(PROPERTY_BLIND_SPOT_WARNING_SYSTEM_COVERAGE);
                addProperty(this.blindSpotWarningSystemCoverage);
                return this;
            }
            
            /**
             * @param rearCrossWarningSystem Indicates whether the rear cross warning system is active or not.
             * @return The builder
             */
            public Builder setRearCrossWarningSystem(Property<ActiveState> rearCrossWarningSystem) {
                this.rearCrossWarningSystem = rearCrossWarningSystem.setIdentifier(PROPERTY_REAR_CROSS_WARNING_SYSTEM);
                addProperty(this.rearCrossWarningSystem);
                return this;
            }
            
            /**
             * @param automatedParkingBrake Automatic brake state
             * @return The builder
             */
            public Builder setAutomatedParkingBrake(Property<ActiveState> automatedParkingBrake) {
                this.automatedParkingBrake = automatedParkingBrake.setIdentifier(PROPERTY_AUTOMATED_PARKING_BRAKE);
                addProperty(this.automatedParkingBrake);
                return this;
            }
            
            /**
             * @param laneKeepAssistSystem Indicates if the lane keep assist system is turned on or not.
             * @return The builder
             */
            public Builder setLaneKeepAssistSystem(Property<OnOffState> laneKeepAssistSystem) {
                this.laneKeepAssistSystem = laneKeepAssistSystem.setIdentifier(PROPERTY_LANE_KEEP_ASSIST_SYSTEM);
                addProperty(this.laneKeepAssistSystem);
                return this;
            }
            
            /**
             * Add an array of lane keep assists states
             * 
             * @param laneKeepAssistsStates The lane keep assists states. Lane keeping assist state indicating the vehicle is actively controlling the wheels.
             * @return The builder
             */
            public Builder setLaneKeepAssistsStates(Property<LaneKeepAssistState>[] laneKeepAssistsStates) {
                this.laneKeepAssistsStates.clear();
                for (int i = 0; i < laneKeepAssistsStates.length; i++) {
                    addLaneKeepAssistsState(laneKeepAssistsStates[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single lane keep assists state
             * 
             * @param laneKeepAssistsState The lane keep assists state. Lane keeping assist state indicating the vehicle is actively controlling the wheels.
             * @return The builder
             */
            public Builder addLaneKeepAssistsState(Property<LaneKeepAssistState> laneKeepAssistsState) {
                laneKeepAssistsState.setIdentifier(PROPERTY_LANE_KEEP_ASSISTS_STATES);
                addProperty(laneKeepAssistsState);
                laneKeepAssistsStates.add(laneKeepAssistsState);
                return this;
            }
            
            /**
             * Add an array of park assists
             * 
             * @param parkAssists The park assists. If the alarm is active and the driver has muted or not park assists.
             * @return The builder
             */
            public Builder setParkAssists(Property<ParkAssist>[] parkAssists) {
                this.parkAssists.clear();
                for (int i = 0; i < parkAssists.length; i++) {
                    addParkAssist(parkAssists[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single park assist
             * 
             * @param parkAssist The park assist. If the alarm is active and the driver has muted or not park assists.
             * @return The builder
             */
            public Builder addParkAssist(Property<ParkAssist> parkAssist) {
                parkAssist.setIdentifier(PROPERTY_PARK_ASSISTS);
                addProperty(parkAssist);
                parkAssists.add(parkAssist);
                return this;
            }
            
            /**
             * @param blindSpotWarningSystem Indicates whether the blind spot warning system is turned on or not.
             * @return The builder
             */
            public Builder setBlindSpotWarningSystem(Property<OnOffState> blindSpotWarningSystem) {
                this.blindSpotWarningSystem = blindSpotWarningSystem.setIdentifier(PROPERTY_BLIND_SPOT_WARNING_SYSTEM);
                addProperty(this.blindSpotWarningSystem);
                return this;
            }
        }
    }

    public enum BlindSpotWarningSystemCoverage implements ByteEnum {
        REGULAR((byte) 0x00),
        TRAILER((byte) 0x01);
    
        public static BlindSpotWarningSystemCoverage fromByte(byte byteValue) throws CommandParseException {
            BlindSpotWarningSystemCoverage[] values = BlindSpotWarningSystemCoverage.values();
    
            for (int i = 0; i < values.length; i++) {
                BlindSpotWarningSystemCoverage state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(BlindSpotWarningSystemCoverage.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        BlindSpotWarningSystemCoverage(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}