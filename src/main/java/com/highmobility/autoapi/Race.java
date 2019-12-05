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
import com.highmobility.autoapi.value.Acceleration;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.Axle;
import com.highmobility.autoapi.value.BrakeTorqueVectoring;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Race capability
 */
public class Race {
    public static final int IDENTIFIER = Identifier.RACE;

    public static final byte PROPERTY_ACCELERATIONS = 0x01;
    public static final byte PROPERTY_UNDERSTEERING = 0x02;
    public static final byte PROPERTY_OVERSTEERING = 0x03;
    public static final byte PROPERTY_GAS_PEDAL_POSITION = 0x04;
    public static final byte PROPERTY_STEERING_ANGLE = 0x05;
    public static final byte PROPERTY_BRAKE_PRESSURE = 0x06;
    public static final byte PROPERTY_YAW_RATE = 0x07;
    public static final byte PROPERTY_REAR_SUSPENSION_STEERING = 0x08;
    public static final byte PROPERTY_ELECTRONIC_STABILITY_PROGRAM = 0x09;
    public static final byte PROPERTY_BRAKE_TORQUE_VECTORINGS = 0x0a;
    public static final byte PROPERTY_GEAR_MODE = 0x0b;
    public static final byte PROPERTY_SELECTED_GEAR = 0x0c;
    public static final byte PROPERTY_BRAKE_PEDAL_POSITION = 0x0d;
    public static final byte PROPERTY_BRAKE_PEDAL_SWITCH = 0x0e;
    public static final byte PROPERTY_CLUTCH_PEDAL_SWITCH = 0x0f;
    public static final byte PROPERTY_ACCELERATOR_PEDAL_IDLE_SWITCH = 0x10;
    public static final byte PROPERTY_ACCELERATOR_PEDAL_KICKDOWN_SWITCH = 0x11;
    public static final byte PROPERTY_VEHICLE_MOVING = 0x12;

    /**
     * Get all race properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(IDENTIFIER);
        }
    
        GetState(byte[] bytes) {
            super(bytes);
        }
    }
    
    /**
     * Get specific race properties
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
    
        GetProperties(byte[] bytes) {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The race state
     */
    public static class State extends SetCommand {
        Property<Acceleration>[] accelerations;
        Property<Double> understeering = new Property(Double.class, PROPERTY_UNDERSTEERING);
        Property<Double> oversteering = new Property(Double.class, PROPERTY_OVERSTEERING);
        Property<Double> gasPedalPosition = new Property(Double.class, PROPERTY_GAS_PEDAL_POSITION);
        PropertyInteger steeringAngle = new PropertyInteger(PROPERTY_STEERING_ANGLE, true);
        Property<Float> brakePressure = new Property(Float.class, PROPERTY_BRAKE_PRESSURE);
        Property<Float> yawRate = new Property(Float.class, PROPERTY_YAW_RATE);
        PropertyInteger rearSuspensionSteering = new PropertyInteger(PROPERTY_REAR_SUSPENSION_STEERING, true);
        Property<ActiveState> electronicStabilityProgram = new Property(ActiveState.class, PROPERTY_ELECTRONIC_STABILITY_PROGRAM);
        Property<BrakeTorqueVectoring>[] brakeTorqueVectorings;
        Property<GearMode> gearMode = new Property(GearMode.class, PROPERTY_GEAR_MODE);
        PropertyInteger selectedGear = new PropertyInteger(PROPERTY_SELECTED_GEAR, true);
        Property<Double> brakePedalPosition = new Property(Double.class, PROPERTY_BRAKE_PEDAL_POSITION);
        Property<ActiveState> brakePedalSwitch = new Property(ActiveState.class, PROPERTY_BRAKE_PEDAL_SWITCH);
        Property<ActiveState> clutchPedalSwitch = new Property(ActiveState.class, PROPERTY_CLUTCH_PEDAL_SWITCH);
        Property<ActiveState> acceleratorPedalIdleSwitch = new Property(ActiveState.class, PROPERTY_ACCELERATOR_PEDAL_IDLE_SWITCH);
        Property<ActiveState> acceleratorPedalKickdownSwitch = new Property(ActiveState.class, PROPERTY_ACCELERATOR_PEDAL_KICKDOWN_SWITCH);
        Property<VehicleMoving> vehicleMoving = new Property(VehicleMoving.class, PROPERTY_VEHICLE_MOVING);
    
        /**
         * @return The accelerations
         */
        public Property<Acceleration>[] getAccelerations() {
            return accelerations;
        }
    
        /**
         * @return The understeering percentage between 0.0-1.0 whereas up to 0.2 (20%) is considered OK, up to 0.3 (30%) marginal, over 30% critical
         */
        public Property<Double> getUndersteering() {
            return understeering;
        }
    
        /**
         * @return The oversteering percentage between 0.0-1.0 whereas up to 0.2 (20%) is considered OK, up to 30% marginal, over 30% critical
         */
        public Property<Double> getOversteering() {
            return oversteering;
        }
    
        /**
         * @return The gas pedal position between 0.0-1.0, whereas 1.0 (100%) is full throttle
         */
        public Property<Double> getGasPedalPosition() {
            return gasPedalPosition;
        }
    
        /**
         * @return The steering angle in degrees, whereas 0° is straight ahead, positive number to the right and negative number to the left
         */
        public PropertyInteger getSteeringAngle() {
            return steeringAngle;
        }
    
        /**
         * @return Brake pressure in bar, whereas 100 bar is max value, full brake
         */
        public Property<Float> getBrakePressure() {
            return brakePressure;
        }
    
        /**
         * @return Yaw rate in degrees per second [°/s]
         */
        public Property<Float> getYawRate() {
            return yawRate;
        }
    
        /**
         * @return Rear suspension steering in degrees
         */
        public PropertyInteger getRearSuspensionSteering() {
            return rearSuspensionSteering;
        }
    
        /**
         * @return The electronic stability program
         */
        public Property<ActiveState> getElectronicStabilityProgram() {
            return electronicStabilityProgram;
        }
    
        /**
         * @return The brake torque vectorings
         */
        public Property<BrakeTorqueVectoring>[] getBrakeTorqueVectorings() {
            return brakeTorqueVectorings;
        }
    
        /**
         * @return The gear mode
         */
        public Property<GearMode> getGearMode() {
            return gearMode;
        }
    
        /**
         * @return The selected gear value, if any
         */
        public PropertyInteger getSelectedGear() {
            return selectedGear;
        }
    
        /**
         * @return The brake pedal position between 0.0-1.0, wheras 1.0 (100%) is full brakes
         */
        public Property<Double> getBrakePedalPosition() {
            return brakePedalPosition;
        }
    
        /**
         * @return The brake pedal switch
         */
        public Property<ActiveState> getBrakePedalSwitch() {
            return brakePedalSwitch;
        }
    
        /**
         * @return The clutch pedal switch
         */
        public Property<ActiveState> getClutchPedalSwitch() {
            return clutchPedalSwitch;
        }
    
        /**
         * @return The accelerator pedal idle switch
         */
        public Property<ActiveState> getAcceleratorPedalIdleSwitch() {
            return acceleratorPedalIdleSwitch;
        }
    
        /**
         * @return The accelerator pedal kickdown switch
         */
        public Property<ActiveState> getAcceleratorPedalKickdownSwitch() {
            return acceleratorPedalKickdownSwitch;
        }
    
        /**
         * @return The vehicle moving
         */
        public Property<VehicleMoving> getVehicleMoving() {
            return vehicleMoving;
        }
    
        /**
         * @param direction The acceleration type.
         * @return Acceleration for the given acceleration type. Null if doesnt exist.
         */
        @Nullable public Property<Acceleration> getAcceleration(Acceleration.Direction direction) {
            for (int i = 0; i < accelerations.length; i++) {
                Property<Acceleration> property = accelerations[i];
                if (property.getValue() != null && property.getValue().getDirection() == direction)
                    return property;
            }
    
            return null;
        }
    
        /**
         * @param axle The axle.
         * @return The Brake Torque Vectoring for the given axle. Null if doesn't exist.
         */
        @Nullable public Property<BrakeTorqueVectoring> getBrakeTorqueVectoring(Axle axle) {
            for (int i = 0; i < brakeTorqueVectorings.length; i++) {
                Property<BrakeTorqueVectoring> property = brakeTorqueVectorings[i];
                if (property.getValue() != null && property.getValue().getAxle() == axle)
                    return property;
            }
    
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> accelerationsBuilder = new ArrayList<>();
            ArrayList<Property> brakeTorqueVectoringsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_ACCELERATIONS:
                            Property<Acceleration> acceleration = new Property(Acceleration.class, p);
                            accelerationsBuilder.add(acceleration);
                            return acceleration;
                        case PROPERTY_UNDERSTEERING: return understeering.update(p);
                        case PROPERTY_OVERSTEERING: return oversteering.update(p);
                        case PROPERTY_GAS_PEDAL_POSITION: return gasPedalPosition.update(p);
                        case PROPERTY_STEERING_ANGLE: return steeringAngle.update(p);
                        case PROPERTY_BRAKE_PRESSURE: return brakePressure.update(p);
                        case PROPERTY_YAW_RATE: return yawRate.update(p);
                        case PROPERTY_REAR_SUSPENSION_STEERING: return rearSuspensionSteering.update(p);
                        case PROPERTY_ELECTRONIC_STABILITY_PROGRAM: return electronicStabilityProgram.update(p);
                        case PROPERTY_BRAKE_TORQUE_VECTORINGS:
                            Property<BrakeTorqueVectoring> brakeTorqueVectoring = new Property(BrakeTorqueVectoring.class, p);
                            brakeTorqueVectoringsBuilder.add(brakeTorqueVectoring);
                            return brakeTorqueVectoring;
                        case PROPERTY_GEAR_MODE: return gearMode.update(p);
                        case PROPERTY_SELECTED_GEAR: return selectedGear.update(p);
                        case PROPERTY_BRAKE_PEDAL_POSITION: return brakePedalPosition.update(p);
                        case PROPERTY_BRAKE_PEDAL_SWITCH: return brakePedalSwitch.update(p);
                        case PROPERTY_CLUTCH_PEDAL_SWITCH: return clutchPedalSwitch.update(p);
                        case PROPERTY_ACCELERATOR_PEDAL_IDLE_SWITCH: return acceleratorPedalIdleSwitch.update(p);
                        case PROPERTY_ACCELERATOR_PEDAL_KICKDOWN_SWITCH: return acceleratorPedalKickdownSwitch.update(p);
                        case PROPERTY_VEHICLE_MOVING: return vehicleMoving.update(p);
                    }
    
                    return null;
                });
            }
    
            accelerations = accelerationsBuilder.toArray(new Property[0]);
            brakeTorqueVectorings = brakeTorqueVectoringsBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            accelerations = builder.accelerations.toArray(new Property[0]);
            understeering = builder.understeering;
            oversteering = builder.oversteering;
            gasPedalPosition = builder.gasPedalPosition;
            steeringAngle = builder.steeringAngle;
            brakePressure = builder.brakePressure;
            yawRate = builder.yawRate;
            rearSuspensionSteering = builder.rearSuspensionSteering;
            electronicStabilityProgram = builder.electronicStabilityProgram;
            brakeTorqueVectorings = builder.brakeTorqueVectorings.toArray(new Property[0]);
            gearMode = builder.gearMode;
            selectedGear = builder.selectedGear;
            brakePedalPosition = builder.brakePedalPosition;
            brakePedalSwitch = builder.brakePedalSwitch;
            clutchPedalSwitch = builder.clutchPedalSwitch;
            acceleratorPedalIdleSwitch = builder.acceleratorPedalIdleSwitch;
            acceleratorPedalKickdownSwitch = builder.acceleratorPedalKickdownSwitch;
            vehicleMoving = builder.vehicleMoving;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private List<Property> accelerations = new ArrayList<>();
            private Property<Double> understeering;
            private Property<Double> oversteering;
            private Property<Double> gasPedalPosition;
            private PropertyInteger steeringAngle;
            private Property<Float> brakePressure;
            private Property<Float> yawRate;
            private PropertyInteger rearSuspensionSteering;
            private Property<ActiveState> electronicStabilityProgram;
            private List<Property> brakeTorqueVectorings = new ArrayList<>();
            private Property<GearMode> gearMode;
            private PropertyInteger selectedGear;
            private Property<Double> brakePedalPosition;
            private Property<ActiveState> brakePedalSwitch;
            private Property<ActiveState> clutchPedalSwitch;
            private Property<ActiveState> acceleratorPedalIdleSwitch;
            private Property<ActiveState> acceleratorPedalKickdownSwitch;
            private Property<VehicleMoving> vehicleMoving;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of accelerations.
             * 
             * @param accelerations The accelerations
             * @return The builder
             */
            public Builder setAccelerations(Property<Acceleration>[] accelerations) {
                this.accelerations.clear();
                for (int i = 0; i < accelerations.length; i++) {
                    addAcceleration(accelerations[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single acceleration.
             * 
             * @param acceleration The acceleration
             * @return The builder
             */
            public Builder addAcceleration(Property<Acceleration> acceleration) {
                acceleration.setIdentifier(PROPERTY_ACCELERATIONS);
                addProperty(acceleration);
                accelerations.add(acceleration);
                return this;
            }
            
            /**
             * @param understeering The understeering percentage between 0.0-1.0 whereas up to 0.2 (20%) is considered OK, up to 0.3 (30%) marginal, over 30% critical
             * @return The builder
             */
            public Builder setUndersteering(Property<Double> understeering) {
                this.understeering = understeering.setIdentifier(PROPERTY_UNDERSTEERING);
                addProperty(this.understeering);
                return this;
            }
            
            /**
             * @param oversteering The oversteering percentage between 0.0-1.0 whereas up to 0.2 (20%) is considered OK, up to 30% marginal, over 30% critical
             * @return The builder
             */
            public Builder setOversteering(Property<Double> oversteering) {
                this.oversteering = oversteering.setIdentifier(PROPERTY_OVERSTEERING);
                addProperty(this.oversteering);
                return this;
            }
            
            /**
             * @param gasPedalPosition The gas pedal position between 0.0-1.0, whereas 1.0 (100%) is full throttle
             * @return The builder
             */
            public Builder setGasPedalPosition(Property<Double> gasPedalPosition) {
                this.gasPedalPosition = gasPedalPosition.setIdentifier(PROPERTY_GAS_PEDAL_POSITION);
                addProperty(this.gasPedalPosition);
                return this;
            }
            
            /**
             * @param steeringAngle The steering angle in degrees, whereas 0° is straight ahead, positive number to the right and negative number to the left
             * @return The builder
             */
            public Builder setSteeringAngle(Property<Integer> steeringAngle) {
                this.steeringAngle = new PropertyInteger(PROPERTY_STEERING_ANGLE, true, 1, steeringAngle);
                addProperty(this.steeringAngle);
                return this;
            }
            
            /**
             * @param brakePressure Brake pressure in bar, whereas 100 bar is max value, full brake
             * @return The builder
             */
            public Builder setBrakePressure(Property<Float> brakePressure) {
                this.brakePressure = brakePressure.setIdentifier(PROPERTY_BRAKE_PRESSURE);
                addProperty(this.brakePressure);
                return this;
            }
            
            /**
             * @param yawRate Yaw rate in degrees per second [°/s]
             * @return The builder
             */
            public Builder setYawRate(Property<Float> yawRate) {
                this.yawRate = yawRate.setIdentifier(PROPERTY_YAW_RATE);
                addProperty(this.yawRate);
                return this;
            }
            
            /**
             * @param rearSuspensionSteering Rear suspension steering in degrees
             * @return The builder
             */
            public Builder setRearSuspensionSteering(Property<Integer> rearSuspensionSteering) {
                this.rearSuspensionSteering = new PropertyInteger(PROPERTY_REAR_SUSPENSION_STEERING, true, 1, rearSuspensionSteering);
                addProperty(this.rearSuspensionSteering);
                return this;
            }
            
            /**
             * @param electronicStabilityProgram The electronic stability program
             * @return The builder
             */
            public Builder setElectronicStabilityProgram(Property<ActiveState> electronicStabilityProgram) {
                this.electronicStabilityProgram = electronicStabilityProgram.setIdentifier(PROPERTY_ELECTRONIC_STABILITY_PROGRAM);
                addProperty(this.electronicStabilityProgram);
                return this;
            }
            
            /**
             * Add an array of brake torque vectorings.
             * 
             * @param brakeTorqueVectorings The brake torque vectorings
             * @return The builder
             */
            public Builder setBrakeTorqueVectorings(Property<BrakeTorqueVectoring>[] brakeTorqueVectorings) {
                this.brakeTorqueVectorings.clear();
                for (int i = 0; i < brakeTorqueVectorings.length; i++) {
                    addBrakeTorqueVectoring(brakeTorqueVectorings[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single brake torque vectoring.
             * 
             * @param brakeTorqueVectoring The brake torque vectoring
             * @return The builder
             */
            public Builder addBrakeTorqueVectoring(Property<BrakeTorqueVectoring> brakeTorqueVectoring) {
                brakeTorqueVectoring.setIdentifier(PROPERTY_BRAKE_TORQUE_VECTORINGS);
                addProperty(brakeTorqueVectoring);
                brakeTorqueVectorings.add(brakeTorqueVectoring);
                return this;
            }
            
            /**
             * @param gearMode The gear mode
             * @return The builder
             */
            public Builder setGearMode(Property<GearMode> gearMode) {
                this.gearMode = gearMode.setIdentifier(PROPERTY_GEAR_MODE);
                addProperty(this.gearMode);
                return this;
            }
            
            /**
             * @param selectedGear The selected gear value, if any
             * @return The builder
             */
            public Builder setSelectedGear(Property<Integer> selectedGear) {
                this.selectedGear = new PropertyInteger(PROPERTY_SELECTED_GEAR, true, 1, selectedGear);
                addProperty(this.selectedGear);
                return this;
            }
            
            /**
             * @param brakePedalPosition The brake pedal position between 0.0-1.0, wheras 1.0 (100%) is full brakes
             * @return The builder
             */
            public Builder setBrakePedalPosition(Property<Double> brakePedalPosition) {
                this.brakePedalPosition = brakePedalPosition.setIdentifier(PROPERTY_BRAKE_PEDAL_POSITION);
                addProperty(this.brakePedalPosition);
                return this;
            }
            
            /**
             * @param brakePedalSwitch The brake pedal switch
             * @return The builder
             */
            public Builder setBrakePedalSwitch(Property<ActiveState> brakePedalSwitch) {
                this.brakePedalSwitch = brakePedalSwitch.setIdentifier(PROPERTY_BRAKE_PEDAL_SWITCH);
                addProperty(this.brakePedalSwitch);
                return this;
            }
            
            /**
             * @param clutchPedalSwitch The clutch pedal switch
             * @return The builder
             */
            public Builder setClutchPedalSwitch(Property<ActiveState> clutchPedalSwitch) {
                this.clutchPedalSwitch = clutchPedalSwitch.setIdentifier(PROPERTY_CLUTCH_PEDAL_SWITCH);
                addProperty(this.clutchPedalSwitch);
                return this;
            }
            
            /**
             * @param acceleratorPedalIdleSwitch The accelerator pedal idle switch
             * @return The builder
             */
            public Builder setAcceleratorPedalIdleSwitch(Property<ActiveState> acceleratorPedalIdleSwitch) {
                this.acceleratorPedalIdleSwitch = acceleratorPedalIdleSwitch.setIdentifier(PROPERTY_ACCELERATOR_PEDAL_IDLE_SWITCH);
                addProperty(this.acceleratorPedalIdleSwitch);
                return this;
            }
            
            /**
             * @param acceleratorPedalKickdownSwitch The accelerator pedal kickdown switch
             * @return The builder
             */
            public Builder setAcceleratorPedalKickdownSwitch(Property<ActiveState> acceleratorPedalKickdownSwitch) {
                this.acceleratorPedalKickdownSwitch = acceleratorPedalKickdownSwitch.setIdentifier(PROPERTY_ACCELERATOR_PEDAL_KICKDOWN_SWITCH);
                addProperty(this.acceleratorPedalKickdownSwitch);
                return this;
            }
            
            /**
             * @param vehicleMoving The vehicle moving
             * @return The builder
             */
            public Builder setVehicleMoving(Property<VehicleMoving> vehicleMoving) {
                this.vehicleMoving = vehicleMoving.setIdentifier(PROPERTY_VEHICLE_MOVING);
                addProperty(this.vehicleMoving);
                return this;
            }
        }
    }

    public enum GearMode implements ByteEnum {
        MANUAL((byte) 0x00),
        PARK((byte) 0x01),
        REVERSE((byte) 0x02),
        NEUTRAL((byte) 0x03),
        DRIVE((byte) 0x04),
        LOW_GEAR((byte) 0x05),
        SPORT((byte) 0x06);
    
        public static GearMode fromByte(byte byteValue) throws CommandParseException {
            GearMode[] values = GearMode.values();
    
            for (int i = 0; i < values.length; i++) {
                GearMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        GearMode(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
    
    public enum VehicleMoving implements ByteEnum {
        NOT_MOVING((byte) 0x00),
        MOVING((byte) 0x01);
    
        public static VehicleMoving fromByte(byte byteValue) throws CommandParseException {
            VehicleMoving[] values = VehicleMoving.values();
    
            for (int i = 0; i < values.length; i++) {
                VehicleMoving state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        VehicleMoving(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}