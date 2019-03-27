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
import com.highmobility.autoapi.value.Acceleration;
import com.highmobility.autoapi.value.Axle;
import com.highmobility.autoapi.value.BrakeTorqueVectoring;
import com.highmobility.autoapi.value.GearMode;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Race State is received by the car.
 */
public class RaceState extends Command {
    public static final Type TYPE = new Type(Identifier.RACE, 0x01);

    private static final byte IDENTIFIER_UNDER_STEERING = 0x02;
    private static final byte IDENTIFIER_OVER_STEERING = 0x03;
    private static final byte IDENTIFIER_GAS_PEDAL_POSITION = 0x04;
    private static final byte IDENTIFIER_STEERING_ANGLE = 0x05;
    private static final byte IDENTIFIER_BRAKE_PRESSURE = 0x06;
    private static final byte IDENTIFIER_YAW_RATE = 0x07;
    private static final byte IDENTIFIER_REAR_SUSPENSION_STEERING = 0x08;
    private static final byte IDENTIFIER_ESP_INTERVENTION_ACTIVE = 0x09;
    private static final byte IDENTIFIER_SELECTED_GEAR = 0x0C;
    private static final byte IDENTIFIER_BRAKE_PEDAL_POSITION = 0x0D;

    private static final byte IDENTIFIER_BRAKE_PEDAL_SWITCH_ACTIVE = 0x0E;
    private static final byte IDENTIFIER_CLUTCH_PEDAL_SWITCH_ACTIVE = 0x0F;
    private static final byte IDENTIFIER_ACCELERATOR_PEDAL_IDLE_SWITCH_ACTIVE = 0x10;
    private static final byte IDENTIFIER_ACCELERATOR_PEDAL_KICKDOWN_SWITCH_ACTIVE = 0x11;

    private static final byte IDENTIFIER_VEHICLE_MOVING = 0x12;
    public static final byte IDENTIFIER_GEAR_MODE = 0x0B;

    public static final byte IDENTIFIER_ACCELERATION = 0x01;
    public static final byte IDENTIFIER_BRAKE_TORQUE_VECTORING = 0x0A;

    Property<Acceleration>[] accelerations;

    Property<Double> underSteering =
            new Property(Double.class, IDENTIFIER_UNDER_STEERING);
    Property<Double> overSteering = new Property(Double.class,
            IDENTIFIER_OVER_STEERING);
    Property<Double> gasPedalPosition =
            new Property(Double.class, IDENTIFIER_GAS_PEDAL_POSITION);
    Property<Integer> steeringAngle = new PropertyInteger(IDENTIFIER_STEERING_ANGLE,
            true);
    Property<Float> brakePressure = new Property(Float.class,
            IDENTIFIER_BRAKE_PRESSURE);
    Property<Float> yawRate = new Property(Float.class, IDENTIFIER_YAW_RATE);
    PropertyInteger rearSuspensionSteering =
            new PropertyInteger(IDENTIFIER_REAR_SUSPENSION_STEERING, false);
    Property<Boolean> espInterventionActive = new Property(Boolean.class,
            IDENTIFIER_ESP_INTERVENTION_ACTIVE);
    Property<BrakeTorqueVectoring>[] brakeTorqueVectorings;
    Property<GearMode> gearMode = new Property(GearMode.class, IDENTIFIER_GEAR_MODE);
    Property<Integer> selectedGear = new PropertyInteger(IDENTIFIER_SELECTED_GEAR, false);
    Property<Double> brakePedalPosition =
            new Property(Double.class, IDENTIFIER_BRAKE_PEDAL_POSITION);
    // level7
    Property<Boolean> brakePedalSwitchActive = new Property(Boolean.class,
            IDENTIFIER_BRAKE_PEDAL_SWITCH_ACTIVE);
    Property<Boolean> clutchPedalSwitchActive = new Property(Boolean.class,
            IDENTIFIER_CLUTCH_PEDAL_SWITCH_ACTIVE);
    Property<Boolean> acceleratorPedalIdleSwitchActive = new Property(Boolean.class
            , IDENTIFIER_ACCELERATOR_PEDAL_IDLE_SWITCH_ACTIVE);
    Property<Boolean> acceleratorPedalKickdownSwitchActive =
            new Property(Boolean.class,
                    IDENTIFIER_ACCELERATOR_PEDAL_KICKDOWN_SWITCH_ACTIVE);
    // level8
    Property<Boolean> vehicleMoving = new Property(Boolean.class,
            IDENTIFIER_VEHICLE_MOVING);

    /**
     * @param accelerationType The acceleration type.
     * @return Acceleration for the given acceleration type. Null if doesnt exist.
     */
    @Nullable public Property<Acceleration> getAcceleration(Acceleration.AccelerationType
                                                                    accelerationType) {
        for (int i = 0; i < accelerations.length; i++) {
            Property<Acceleration> property = accelerations[i];
            if (property.getValue() != null && property.getValue().getAccelerationType() == accelerationType)
                return property;
        }

        return null;
    }

    /**
     * @return All of the accelerations.
     */
    public Property<Acceleration>[] getAccelerations() {
        return accelerations;
    }

    /**
     * @return The under steering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical.
     */
    public Property<Double> getUnderSteering() {
        return underSteering;
    }

    /**
     * @return The over steering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical.
     */
    public Property<Double> getOverSteering() {
        return overSteering;
    }

    /**
     * @return The gas pedal position between 0-1, whereas 1 is full throttle.
     */
    public Property<Double> getGasPedalPosition() {
        return gasPedalPosition;
    }

    /**
     * @return The steering angle in degrees, whereas 0 degrees is straight ahead, positive number
     * to the left and negative number to the right.
     */
    public Property<Integer> getSteeringAngle() {
        return steeringAngle;
    }

    /**
     * @return The Brake pressure in bar, whereas 100bar is max value, full brake.
     */
    public Property<Float> getBrakePressure() {
        return brakePressure;
    }

    /**
     * @return The yaw rate in degrees per second [°/s].
     */
    public Property<Float> getYawRate() {
        return yawRate;
    }

    /**
     * @return The rear suspension steering in degrees.
     */
    public Property<Integer> getRearSuspensionSteering() {
        return rearSuspensionSteering;
    }

    /**
     * @return The ESP (Electronic Stability Program) intervention state.
     */
    public Property<Boolean> isEspInterventionActive() {
        return espInterventionActive;
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

    /**
     * @return All of the brake torque vectorings.
     */
    public Property<BrakeTorqueVectoring>[] getBrakeTorqueVectorings() {
        return brakeTorqueVectorings;
    }

    /**
     * @return The gear mode.
     */
    public Property<GearMode> getGearMode() {
        return gearMode;
    }

    /**
     * @return The selected gear.
     */
    public Property<Integer> getSelectedGear() {
        return selectedGear;
    }

    /**
     * @return The brake pedal position between 0-1, whereas 1 is full brakes.
     */
    public Property<Double> getBrakePedalPosition() {
        return brakePedalPosition;
    }

    /**
     * @return The brake pedal switch state.
     */
    public Property<Boolean> isBrakePedalSwitchActive() {
        return brakePedalSwitchActive;
    }

    /**
     * @return The clutch pedal switch state.
     */
    public Property<Boolean> isClutchPedalSwitchActive() {
        return clutchPedalSwitchActive;
    }

    /**
     * @return The accelerator pedal idle switch state. If active, pedal is fully released.
     */
    public Property<Boolean> isAcceleratorPedalIdleSwitchActive() {
        return acceleratorPedalIdleSwitchActive;
    }

    /**
     * @return The accelerator pedal kickdown switch state. If active, pedal is fully depressed.
     */
    public Property<Boolean> isAcceleratorPedalKickdownSwitchActive
    () {
        return acceleratorPedalKickdownSwitchActive;
    }

    /**
     * @return The vehicle moving state.
     */
    public Property<Boolean> isVehicleMoving() {
        return vehicleMoving;
    }

    public RaceState(byte[] bytes) {
        super(bytes);

        ArrayList<Property<Acceleration>> accelerationProperties = new ArrayList<>();
        ArrayList<Property<BrakeTorqueVectoring>> brakeTorqueVectoringProperties =
                new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ACCELERATION:
                        Property a = new Property(Acceleration.class, p);
                        accelerationProperties.add(a);
                        return a;
                    case IDENTIFIER_UNDER_STEERING:
                        return underSteering.update(p);
                    case IDENTIFIER_OVER_STEERING:
                        return overSteering.update(p);
                    case IDENTIFIER_GAS_PEDAL_POSITION:
                        return gasPedalPosition.update(p);
                    case IDENTIFIER_STEERING_ANGLE:
                        return steeringAngle.update(p);
                    case IDENTIFIER_BRAKE_PRESSURE:
                        return brakePressure.update(p);
                    case IDENTIFIER_YAW_RATE:
                        return yawRate.update(p);
                    case IDENTIFIER_REAR_SUSPENSION_STEERING:
                        return rearSuspensionSteering.update(p);
                    case IDENTIFIER_ESP_INTERVENTION_ACTIVE:
                        return espInterventionActive.update(p);
                    case IDENTIFIER_BRAKE_TORQUE_VECTORING:
                        Property b = new Property(BrakeTorqueVectoring.class, p);
                        brakeTorqueVectoringProperties.add(b);
                        return b;
                    case IDENTIFIER_GEAR_MODE:
                        return gearMode.update(p);
                    case IDENTIFIER_SELECTED_GEAR:
                        return selectedGear.update(p);
                    case IDENTIFIER_BRAKE_PEDAL_POSITION:
                        return brakePedalPosition.update(p);
                    case IDENTIFIER_BRAKE_PEDAL_SWITCH_ACTIVE:
                        return brakePedalSwitchActive.update(p);
                    case IDENTIFIER_CLUTCH_PEDAL_SWITCH_ACTIVE:
                        return clutchPedalSwitchActive.update(p);
                    case IDENTIFIER_ACCELERATOR_PEDAL_IDLE_SWITCH_ACTIVE:
                        return acceleratorPedalIdleSwitchActive.update(p);
                    case IDENTIFIER_ACCELERATOR_PEDAL_KICKDOWN_SWITCH_ACTIVE:
                        return acceleratorPedalKickdownSwitchActive.update(p);
                    case IDENTIFIER_VEHICLE_MOVING:
                        return vehicleMoving.update(p);
                }

                return null;
            });
        }

        this.accelerations = accelerationProperties.toArray(new Property[0]);
        this.brakeTorqueVectorings = brakeTorqueVectoringProperties.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private RaceState(Builder builder) {
        super(builder);
        accelerations = builder.accelerationProperties.toArray(new Property[0]);
        underSteering = builder.underSteering;
        overSteering = builder.overSteering;
        gasPedalPosition = builder.gasPedalPosition;
        steeringAngle = builder.steeringAngle;
        brakePressure = builder.brakePressure;
        yawRate = builder.yawRate;
        rearSuspensionSteering = builder.rearSuspensionSteering;
        espInterventionActive = builder.espInterventionActive;
        brakeTorqueVectorings = builder.brakeTorqueVectorings.toArray(new Property[0]);
        gearMode = builder.gearMode;
        selectedGear = builder.selectedGear;
        brakePedalPosition = builder.brakePedalPosition;

        brakePedalSwitchActive = builder.brakePedalSwitchActive;
        clutchPedalSwitchActive = builder.clutchPedalSwitchActive;
        acceleratorPedalIdleSwitchActive = builder.acceleratorPedalIdleSwitchActive;
        acceleratorPedalKickdownSwitchActive =
                builder.acceleratorPedalKickdownSwitchActive;
        vehicleMoving = builder.vehicleMoving;
    }

    public static final class Builder extends Command.Builder {
        private List<Property<Acceleration>> accelerationProperties = new ArrayList<>();

        private Property<Double> underSteering;
        private Property<Double> overSteering;
        private Property<Double> gasPedalPosition;
        private PropertyInteger steeringAngle;
        private Property<Float> brakePressure;
        private Property<Float> yawRate;
        private PropertyInteger rearSuspensionSteering;
        private Property<Boolean> espInterventionActive;
        private List<Property<BrakeTorqueVectoring>> brakeTorqueVectorings = new ArrayList<>();
        private Property<GearMode> gearMode;
        private PropertyInteger selectedGear;
        private Property<Double> brakePedalPosition;

        private Property<Boolean> brakePedalSwitchActive;
        private Property<Boolean> clutchPedalSwitchActive;
        private Property<Boolean> acceleratorPedalIdleSwitchActive;
        private Property<Boolean> acceleratorPedalKickdownSwitchActive;

        public Property<Boolean> vehicleMoving;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param accelerationProperties All of the accelerations.
         * @return The builder.
         */
        public Builder setAccelerationProperties(Property<Acceleration>[] accelerationProperties) {
            this.accelerationProperties.clear();
            for (Property<Acceleration> acceleration : accelerationProperties) {
                addAccelerationProperty(acceleration);
            }
            return this;
        }

        /**
         * Add a single acceleration property.
         *
         * @param acceleration The acceleration property.
         * @return The builder.
         */
        public Builder addAccelerationProperty(Property<Acceleration> acceleration) {
            this.accelerationProperties.add(acceleration);
            addProperty(acceleration.setIdentifier(IDENTIFIER_ACCELERATION));
            return this;
        }

        /**
         * @param underSteering The under steering percentage between 0-1 whereas up to .2 is
         *                      considered OK, up to .3 marginal, over .3 critical.
         * @return The builder.
         */
        public Builder setUnderSteering(Property<Double> underSteering) {
            this.underSteering = underSteering;
            underSteering.setIdentifier(IDENTIFIER_UNDER_STEERING);
            addProperty(underSteering);
            return this;
        }

        /**
         * @param overSteering The over steering percentage between 0-1 whereas up to .2 is
         *                     considered OK, up to .3 marginal, over .3 critical
         * @return The builder.
         */
        public Builder setOverSteering(Property<Double> overSteering) {
            this.overSteering = overSteering;
            overSteering.setIdentifier(IDENTIFIER_OVER_STEERING);
            addProperty(overSteering);

            return this;
        }

        /**
         * @param gasPedalPosition The gas pedal position between 0-1, whereas 1 is full throttle.
         * @return The builder.
         */
        public Builder setGasPedalPosition(Property<Double> gasPedalPosition) {
            this.gasPedalPosition = gasPedalPosition;
            gasPedalPosition.setIdentifier(IDENTIFIER_GAS_PEDAL_POSITION);
            addProperty(gasPedalPosition);
            return this;
        }

        /**
         * @param steeringAngle The steering angle in degrees, whereas 0 degrees is straight ahead,
         *                      positive number to the left and negative number to the right.
         * @return The builder.
         */
        public Builder setSteeringAngle(Property<Integer> steeringAngle) {
            this.steeringAngle = new PropertyInteger(IDENTIFIER_STEERING_ANGLE, false, 1,
                    steeringAngle);
            addProperty(this.steeringAngle);
            return this;
        }

        /**
         * @param brakePressure The brake pressure in bar, whereas 100bar is max value, full brake.
         * @return The builder.
         */
        public Builder setBrakePressure
        (Property<Float> brakePressure) {
            this.brakePressure = brakePressure;
            brakePressure.setIdentifier(IDENTIFIER_BRAKE_PRESSURE);
            addProperty(brakePressure);
            return this;
        }

        /**
         * @param yawRate The yaw rate in degrees per second [°/s].
         * @return The builder.
         */
        public Builder setYawRate(Property<Float> yawRate) {
            this.yawRate = yawRate;
            yawRate.setIdentifier(IDENTIFIER_YAW_RATE);
            addProperty(yawRate);
            return this;
        }

        /**
         * @param rearSuspensionSteering The rear suspension steering in degrees.
         * @return The builder
         */
        public Builder setRearSuspensionSteering(Property<Integer> rearSuspensionSteering) {
            this.rearSuspensionSteering = new PropertyInteger(IDENTIFIER_REAR_SUSPENSION_STEERING
                    , false, 1, rearSuspensionSteering);
            addProperty(this.rearSuspensionSteering);
            return this;
        }

        /**
         * @param espInterventionActive The ESP (Electronic Stability Program) intervention state.
         * @return The builder.
         */
        public Builder setEspInterventionActive(Property<Boolean> espInterventionActive) {
            this.espInterventionActive = espInterventionActive;
            espInterventionActive.setIdentifier(IDENTIFIER_ESP_INTERVENTION_ACTIVE);
            addProperty(espInterventionActive);
            return this;
        }

        /**
         * @param brakeTorqueVectorings The brake torque vectorings.
         * @return The builder.
         */
        public Builder setBrakeTorqueVectorings(Property<BrakeTorqueVectoring>[] brakeTorqueVectorings) {
            this.brakeTorqueVectorings.clear();
            for (Property<BrakeTorqueVectoring> brakeTorqueVectoring : brakeTorqueVectorings) {
                addBrakeTorqueVectoring(brakeTorqueVectoring);
            }
            return this;
        }

        /**
         * Add a single brake torque vectoring property.
         *
         * @param brakeTorqueVectoring The brake torque vectoring.
         * @return The builder.
         */
        public Builder addBrakeTorqueVectoring(Property<BrakeTorqueVectoring> brakeTorqueVectoring) {
            this.brakeTorqueVectorings.add(brakeTorqueVectoring);
            addProperty(brakeTorqueVectoring.setIdentifier(IDENTIFIER_BRAKE_TORQUE_VECTORING));
            return this;
        }

        /**
         * @param gearMode The gear mode.
         * @return The builder.
         */
        public Builder setGearMode(Property<GearMode> gearMode) {
            this.gearMode = gearMode;
            addProperty(gearMode.setIdentifier(IDENTIFIER_GEAR_MODE));
            return this;
        }

        /**
         * @param selectedGear The selected gear.
         * @return The builder.
         */
        public Builder setSelectedGear(Property<Integer> selectedGear) {
            this.selectedGear = new PropertyInteger(IDENTIFIER_SELECTED_GEAR, false, 1,
                    selectedGear);
            addProperty(this.selectedGear);
            return this;
        }

        /**
         * @param brakePedalPosition The brake pedal position.
         * @return The builder.
         */
        public Builder setBrakePedalPosition(Property<Double> brakePedalPosition) {
            this.brakePedalPosition = brakePedalPosition;
            brakePedalPosition.setIdentifier(IDENTIFIER_BRAKE_PEDAL_POSITION);
            addProperty(brakePedalPosition);
            return this;
        }

        /**
         * @param brakePedalSwitchActive The brake pedal switch state.
         * @return The builder.
         */
        public Builder setBrakePedalSwitchActive(Property<Boolean> brakePedalSwitchActive) {
            this.brakePedalSwitchActive = brakePedalSwitchActive;
            brakePedalSwitchActive.setIdentifier(IDENTIFIER_BRAKE_PEDAL_SWITCH_ACTIVE);
            addProperty(brakePedalSwitchActive);
            return this;
        }

        /**
         * @param clutchPedalSwitchActive The clutch pedal switch state.
         * @return The builder.
         */
        public Builder setClutchPedalSwitchActive(Property<Boolean> clutchPedalSwitchActive) {
            this.clutchPedalSwitchActive = clutchPedalSwitchActive;
            clutchPedalSwitchActive.setIdentifier(IDENTIFIER_CLUTCH_PEDAL_SWITCH_ACTIVE);
            addProperty(clutchPedalSwitchActive);
            return this;
        }

        /**
         * @param acceleratorPedalIdleSwitchActive The accelerator pedal idle switch state. If
         *                                         active, pedal is fully released.
         * @return The builder.
         */
        public Builder setAcceleratorPedalIdleSwitchActive(Property<Boolean> acceleratorPedalIdleSwitchActive) {
            this.acceleratorPedalIdleSwitchActive =
                    acceleratorPedalIdleSwitchActive;
            acceleratorPedalIdleSwitchActive.setIdentifier(IDENTIFIER_ACCELERATOR_PEDAL_IDLE_SWITCH_ACTIVE);
            addProperty(acceleratorPedalIdleSwitchActive);
            return this;
        }

        /**
         * @param acceleratorPedalKickdownSwitchActive The accelerator pedal kickdown switch state.
         *                                             If active, pedal is fully depressed.
         * @return The builder.
         */
        public Builder setAcceleratorPedalKickdownSwitchActive(Property<Boolean> acceleratorPedalKickdownSwitchActive) {
            this.acceleratorPedalKickdownSwitchActive =
                    acceleratorPedalKickdownSwitchActive;
            acceleratorPedalKickdownSwitchActive.setIdentifier(IDENTIFIER_ACCELERATOR_PEDAL_KICKDOWN_SWITCH_ACTIVE);
            addProperty(acceleratorPedalKickdownSwitchActive);
            return this;
        }

        /**
         * @param vehicleMoving The vehicle moving state.
         * @return The builder.
         */
        public Builder setVehicleMoving(Property<Boolean> vehicleMoving) {
            this.vehicleMoving = vehicleMoving;
            vehicleMoving.setIdentifier(IDENTIFIER_VEHICLE_MOVING);
            addProperty(vehicleMoving);
            return this;
        }

        public RaceState build() {
            return new RaceState(this);
        }
    }
}