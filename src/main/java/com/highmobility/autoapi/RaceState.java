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

import com.highmobility.autoapi.property.AccelerationProperty;
import com.highmobility.autoapi.property.BrakeTorqueVectoring;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyPercentage;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Axle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Race State is received by the car.
 */
public class RaceState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.RACE, 0x01);

    private static final byte IDENTIFIER_UNDER_STEERING = 0x02;
    private static final byte IDENTIFIER_OVER_STEERING = 0x03;
    private static final byte IDENTIFIER_GAS_PEDAL_POSITION = 0x04;
    private static final byte IDENTIFIER_STEERING_ANGLE = 0x05;
    private static final byte BRAKE_PRESSURE_IDENTIFIER = 0x06;
    private static final byte YAW_RATE_IDENTIFIER = 0x07;
    private static final byte IDENTIFIER_REAR_SUSPENSION_STEERING = 0x08;
    private static final byte ESP_INTERVENTION_ACTIVE_IDENTIFIER = 0x09;
    private static final byte IDENTIFIER_SELECTED_GEAR = 0x0C;
    private static final byte IDENTIFIER_BRAKE_PEDAL_POSITION = 0x0D;

    private static final byte BRAKE_PEDAL_SWITCH_IDENTIFIER = 0x0E;
    private static final byte CLUTCH_PEDAL_SWITCH_IDENTIFIER = 0x0F;
    private static final byte ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER = 0x10;
    private static final byte ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER = 0x11;

    private static final byte IDENTIFIER_VEHICLE_MOVING = 0x12;
    public static final byte IDENTIFIER_GEAR_MODE = 0x0B;

    AccelerationProperty[] accelerationProperties;

    ObjectPropertyPercentage underSteering =
            new ObjectPropertyPercentage(IDENTIFIER_UNDER_STEERING);
    ObjectPropertyPercentage overSteering = new ObjectPropertyPercentage(IDENTIFIER_OVER_STEERING);
    ObjectPropertyPercentage gasPedalPosition =
            new ObjectPropertyPercentage(IDENTIFIER_GAS_PEDAL_POSITION);
    ObjectPropertyInteger steeringAngle = new ObjectPropertyInteger(IDENTIFIER_STEERING_ANGLE,
            true);
    FloatProperty brakePressure;
    FloatProperty yawRate;
    ObjectPropertyInteger rearSuspensionSteering =
            new ObjectPropertyInteger(IDENTIFIER_REAR_SUSPENSION_STEERING, false);
    ObjectProperty<Boolean> espInterventionActive;
    BrakeTorqueVectoring[] brakeTorqueVectorings;
    GearMode gearMode;
    ObjectPropertyInteger selectedGear = new ObjectPropertyInteger(IDENTIFIER_SELECTED_GEAR, false);
    ObjectPropertyPercentage brakePedalPosition =
            new ObjectPropertyPercentage(IDENTIFIER_BRAKE_PEDAL_POSITION);
    // level7
    ObjectProperty<Boolean> brakePedalSwitchActive;
    ObjectProperty<Boolean> clutchPedalSwitchActive;
    ObjectProperty<Boolean> acceleratorPedalIdleSwitchActive;
    ObjectProperty<Boolean> acceleratorPedalKickdownSwitchActive;
    // level8
    ObjectProperty<Boolean> vehicleMoving;

    /**
     * @param accelerationType The acceleration type.
     * @return Acceleration for the given acceleration type. Null if doesnt exist.
     */
    @Nullable public AccelerationProperty getAcceleration(AccelerationProperty.AccelerationType
                                                                  accelerationType) {
        for (int i = 0; i < accelerationProperties.length; i++) {
            AccelerationProperty property = accelerationProperties[i];
            if (property.getAccelerationType() == accelerationType) return property;
        }

        return null;
    }

    /**
     * @return All of the accelerations.
     */
    public AccelerationProperty[] getAccelerationProperties() {
        return accelerationProperties;
    }

    /**
     * @return The under steering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical.
     */
    @Nullable public ObjectPropertyPercentage getUnderSteering() {
        return underSteering;
    }

    /**
     * @return The over steering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical.
     */
    @Nullable public ObjectPropertyPercentage getOverSteering() {
        return overSteering;
    }

    /**
     * @return The gas pedal position between 0-1, whereas 1 is full throttle.
     */
    @Nullable public ObjectPropertyPercentage getGasPedalPosition() {
        return gasPedalPosition;
    }

    /**
     * @return The steering angle in degrees, whereas 0 degrees is straight ahead, positive number
     * to the left and negative number to the right.
     */
    @Nullable public ObjectPropertyInteger getSteeringAngle() {
        return steeringAngle;
    }

    /**
     * @return The Brake pressure in bar, whereas 100bar is max value, full brake.
     */
    @Nullable public FloatProperty getBrakePressure() {
        return brakePressure;
    }

    /**
     * @return The yaw rate in degrees per second [°/s].
     */
    @Nullable public FloatProperty getYawRate() {
        return yawRate;
    }

    /**
     * @return The rear suspension steering in degrees.
     */
    @Nullable public ObjectPropertyInteger getRearSuspensionSteering() {
        return rearSuspensionSteering;
    }

    /**
     * @return The ESP (Electronic Stability Program) intervention state.
     */
    @Nullable public ObjectProperty<Boolean> isEspInterventionActive() {
        return espInterventionActive;
    }

    /**
     * @param axle The axle.
     * @return The Brake Torque Vectoring for the given axle. Null if doesn't exist.
     */
    @Nullable public BrakeTorqueVectoring getBrakeTorqueVectoring(Axle axle) {
        for (int i = 0; i < brakeTorqueVectorings.length; i++) {
            BrakeTorqueVectoring property = brakeTorqueVectorings[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @return All of the brake torque vectorings.
     */
    public BrakeTorqueVectoring[] getBrakeTorqueVectorings() {
        return brakeTorqueVectorings;
    }

    /**
     * @return The gear mode.
     */
    @Nullable public GearMode getGearMode() {
        return gearMode;
    }

    /**
     * @return The selected gear.
     */
    @Nullable public ObjectPropertyInteger getSelectedGear() {
        return selectedGear;
    }

    /**
     * @return The brake pedal position between 0-1, whereas 1 is full brakes.
     */
    @Nullable public ObjectPropertyPercentage getBrakePedalPosition() {
        return brakePedalPosition;
    }

    /**
     * @return The brake pedal switch state.
     */
    @Nullable public ObjectProperty<Boolean> isBrakePedalSwitchActive() {
        return brakePedalSwitchActive;
    }

    /**
     * @return The clutch pedal switch state.
     */
    @Nullable public ObjectProperty<Boolean> isClutchPedalSwitchActive() {
        return clutchPedalSwitchActive;
    }

    /**
     * @return The accelerator pedal idle switch state. If active, pedal is fully released.
     */
    @Nullable public ObjectProperty<Boolean> isAcceleratorPedalIdleSwitchActive() {
        return acceleratorPedalIdleSwitchActive;
    }

    /**
     * @return The accelerator pedal kickdown switch state. If active, pedal is fully depressed.
     */
    @Nullable public ObjectProperty<Boolean> isAcceleratorPedalKickdownSwitchActive() {
        return acceleratorPedalKickdownSwitchActive;
    }

    /**
     * @return The vehicle moving state.
     */
    @Nullable public ObjectProperty<Boolean> isVehicleMoving() {
        return vehicleMoving;
    }

    public RaceState(byte[] bytes) {
        super(bytes);

        ArrayList<AccelerationProperty> accelerationProperties = new ArrayList<>();
        ArrayList<BrakeTorqueVectoring> brakeTorqueVectoringProperties = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case AccelerationProperty.IDENTIFIER:
                        AccelerationProperty a = new AccelerationProperty(p.getByteArray());
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
                    case BRAKE_PRESSURE_IDENTIFIER:
                        brakePressure = new FloatProperty(p);
                        return brakePressure;
                    case YAW_RATE_IDENTIFIER:
                        yawRate = new FloatProperty(p);
                        return yawRate;
                    case IDENTIFIER_REAR_SUSPENSION_STEERING:
                        return rearSuspensionSteering.update(p);
                    case ESP_INTERVENTION_ACTIVE_IDENTIFIER:
                        espInterventionActive = new ObjectProperty<>(Boolean.class, p);
                        return espInterventionActive;
                    case BrakeTorqueVectoring.IDENTIFIER:
                        BrakeTorqueVectoring b =
                                new BrakeTorqueVectoring(p.getByteArray());
                        brakeTorqueVectoringProperties.add(b);
                        return b;
                    case IDENTIFIER_GEAR_MODE:
                        gearMode = GearMode.fromByte(p.getValueByte());
                        return null;
                    case IDENTIFIER_SELECTED_GEAR:
                        return selectedGear.update(p);
                    case IDENTIFIER_BRAKE_PEDAL_POSITION:
                        return brakePedalPosition.update(p);
                    case BRAKE_PEDAL_SWITCH_IDENTIFIER:
                        brakePedalSwitchActive = new ObjectProperty<>(Boolean.class, p);
                        return brakePedalSwitchActive;
                    case CLUTCH_PEDAL_SWITCH_IDENTIFIER:
                        clutchPedalSwitchActive = new ObjectProperty<>(Boolean.class, p);
                        return clutchPedalSwitchActive;
                    case ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER:
                        acceleratorPedalIdleSwitchActive = new ObjectProperty<>(Boolean.class, p);
                        return acceleratorPedalIdleSwitchActive;
                    case ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER:
                        acceleratorPedalKickdownSwitchActive = new ObjectProperty<>(Boolean.class
                                , p);
                        return acceleratorPedalKickdownSwitchActive;
                    case IDENTIFIER_VEHICLE_MOVING:
                        vehicleMoving = new ObjectProperty<>(Boolean.class, p);
                        return vehicleMoving;
                }

                return null;
            });
        }

        this.accelerationProperties = accelerationProperties.toArray(
                new AccelerationProperty[0]);

        this.brakeTorqueVectorings = brakeTorqueVectoringProperties.toArray(
                new BrakeTorqueVectoring[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private RaceState(Builder builder) {
        super(builder);
        accelerationProperties =
                builder.accelerationProperties.toArray(new AccelerationProperty[0]);
        underSteering = builder.underSteering;
        overSteering = builder.overSteering;
        gasPedalPosition = builder.gasPedalPosition;
        steeringAngle = builder.steeringAngle;
        brakePressure = builder.brakePressure;
        yawRate = builder.yawRate;
        rearSuspensionSteering = builder.rearSuspensionSteering;
        espInterventionActive = builder.espInterventionActive;
        brakeTorqueVectorings = builder.brakeTorqueVectorings.toArray(new
                BrakeTorqueVectoring[0]);
        gearMode = builder.gearMode;
        selectedGear = builder.selectedGear;
        brakePedalPosition = builder.brakePedalPosition;

        brakePedalSwitchActive = builder.brakePedalSwitchActive;
        clutchPedalSwitchActive = builder.clutchPedalSwitchActive;
        acceleratorPedalIdleSwitchActive = builder.acceleratorPedalIdleSwitchActive;
        acceleratorPedalKickdownSwitchActive = builder.acceleratorPedalKickdownSwitchActive;
        vehicleMoving = builder.vehicleMoving;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<AccelerationProperty> accelerationProperties = new ArrayList<>();

        private ObjectPropertyPercentage underSteering;
        private ObjectPropertyPercentage overSteering;
        private ObjectPropertyPercentage gasPedalPosition;
        private ObjectPropertyInteger steeringAngle;
        private FloatProperty brakePressure;
        private FloatProperty yawRate;
        private ObjectPropertyInteger rearSuspensionSteering;
        private ObjectProperty<Boolean> espInterventionActive;
        private List<BrakeTorqueVectoring> brakeTorqueVectorings = new ArrayList<>();
        private GearMode gearMode;
        private ObjectPropertyInteger selectedGear;
        private ObjectPropertyPercentage brakePedalPosition;

        private ObjectProperty<Boolean> brakePedalSwitchActive;
        private ObjectProperty<Boolean> clutchPedalSwitchActive;
        private ObjectProperty<Boolean> acceleratorPedalIdleSwitchActive;
        private ObjectProperty<Boolean> acceleratorPedalKickdownSwitchActive;

        public ObjectProperty<Boolean> vehicleMoving;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param accelerationProperties All of the accelerations.
         * @return The builder.
         */
        public Builder setAccelerationProperties(AccelerationProperty[] accelerationProperties) {
            this.accelerationProperties = Arrays.asList(accelerationProperties);
            for (AccelerationProperty accelerationProperty : accelerationProperties) {
                addProperty(accelerationProperty);
            }
            return this;
        }

        /**
         * Add a single acceleration property.
         *
         * @param accelerationProperty The acceleration property.
         * @return The builder.
         */
        public Builder addAccelerationProperty(AccelerationProperty accelerationProperty) {
            this.accelerationProperties.add(accelerationProperty);
            addProperty(accelerationProperty);
            return this;
        }

        /**
         * @param underSteering The under steering percentage between 0-1 whereas up to .2 is
         *                      considered OK, up to .3 * marginal, over .3 critical.
         * @return The builder.
         */
        public Builder setUnderSteering(ObjectPropertyPercentage underSteering) {
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
        public Builder setOverSteering(ObjectPropertyPercentage overSteering) {
            this.overSteering = overSteering;
            overSteering.setIdentifier(IDENTIFIER_OVER_STEERING);
            addProperty(overSteering);
            return this;
        }

        /**
         * @param gasPedalPosition The gas pedal position between 0-1, whereas 1 is full throttle.
         * @return The builder.
         */
        public Builder setGasPedalPosition(ObjectPropertyPercentage gasPedalPosition) {
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
        public Builder setSteeringAngle(ObjectPropertyInteger steeringAngle) {
            this.steeringAngle = steeringAngle;
            steeringAngle.update(IDENTIFIER_STEERING_ANGLE, false, 1);
            addProperty(steeringAngle);
            return this;
        }

        /**
         * @param brakePressure The brake pressure in bar, whereas 100bar is max value, full brake.
         * @return The builder.
         */
        public Builder setBrakePressure(FloatProperty brakePressure) {
            this.brakePressure = brakePressure;
            brakePressure.setIdentifier(BRAKE_PRESSURE_IDENTIFIER);
            addProperty(brakePressure);
            return this;
        }

        /**
         * @param yawRate The yaw rate in degrees per second [°/s].
         * @return The builder.
         */
        public Builder setYawRate(FloatProperty yawRate) {
            this.yawRate = yawRate;
            yawRate.setIdentifier(YAW_RATE_IDENTIFIER);
            addProperty(yawRate);
            return this;
        }

        /**
         * @param rearSuspensionSteering The rear suspension steering in degrees.
         * @return The builder
         */
        public Builder setRearSuspensionSteering(ObjectPropertyInteger rearSuspensionSteering) {
            this.rearSuspensionSteering = rearSuspensionSteering;
            rearSuspensionSteering.update(IDENTIFIER_REAR_SUSPENSION_STEERING, false, 1);
            addProperty(rearSuspensionSteering);
            return this;
        }

        /**
         * @param espInterventionActive The ESP (Electronic Stability Program) intervention state.
         * @return The builder.
         */
        public Builder setEspInterventionActive(ObjectProperty<Boolean> espInterventionActive) {
            this.espInterventionActive = espInterventionActive;
            espInterventionActive.setIdentifier(ESP_INTERVENTION_ACTIVE_IDENTIFIER);
            addProperty(espInterventionActive);
            return this;
        }

        /**
         * @param brakeTorqueVectorings The brake torque vectorings.
         * @return The builder.
         */
        public Builder setBrakeTorqueVectorings(BrakeTorqueVectoring[]
                                                        brakeTorqueVectorings) {
            this.brakeTorqueVectorings = Arrays.asList(brakeTorqueVectorings);
            for (BrakeTorqueVectoring brakeTorqueVectoring : brakeTorqueVectorings) {
                addProperty(brakeTorqueVectoring);
            }
            return this;
        }

        /**
         * Add a single brake torque vectoring property.
         *
         * @param brakeTorqueVectoring The brake torque vectoring.
         * @return The builder.
         */
        public Builder addBrakeTorqueVectoring(BrakeTorqueVectoring brakeTorqueVectoring) {
            this.brakeTorqueVectorings.add(brakeTorqueVectoring);
            addProperty(brakeTorqueVectoring);
            return this;
        }

        /**
         * @param gearMode The gear mode.
         * @return The builder.
         */
        public Builder setGearMode(GearMode gearMode) {
            this.gearMode = gearMode;
            addProperty(new Property(IDENTIFIER_GEAR_MODE, gearMode.getByte()));
            return this;
        }

        /**
         * @param selectedGear The selected gear.
         * @return The builder.
         */
        public Builder setSelectedGear(ObjectPropertyInteger selectedGear) {
            this.selectedGear = selectedGear;
            selectedGear.update(IDENTIFIER_SELECTED_GEAR, false, 1);
            addProperty(selectedGear);
            return this;
        }

        /**
         * @param brakePedalPosition The brake pedal position.
         * @return The builder.
         */
        public Builder setBrakePedalPosition(ObjectPropertyPercentage brakePedalPosition) {
            this.brakePedalPosition = brakePedalPosition;
            brakePedalPosition.setIdentifier(IDENTIFIER_BRAKE_PEDAL_POSITION);
            addProperty(brakePedalPosition);
            return this;
        }

        /**
         * @param brakePedalSwitchActive The brake pedal switch state.
         * @return The builder.
         */
        public Builder setBrakePedalSwitchActive(ObjectProperty<Boolean> brakePedalSwitchActive) {
            this.brakePedalSwitchActive = brakePedalSwitchActive;
            brakePedalSwitchActive.setIdentifier(BRAKE_PEDAL_SWITCH_IDENTIFIER);
            addProperty(brakePedalSwitchActive);
            return this;
        }

        /**
         * @param clutchPedalSwitchActive The clutch pedal switch state.
         * @return The builder.
         */
        public Builder setClutchPedalSwitchActive(ObjectProperty<Boolean> clutchPedalSwitchActive) {
            this.clutchPedalSwitchActive = clutchPedalSwitchActive;
            clutchPedalSwitchActive.setIdentifier(CLUTCH_PEDAL_SWITCH_IDENTIFIER);
            addProperty(clutchPedalSwitchActive);
            return this;
        }

        /**
         * @param acceleratorPedalIdleSwitchActive The accelerator pedal idle switch state. If
         *                                         active, pedal is fully released.
         * @return The builder.
         */
        public Builder setAcceleratorPedalIdleSwitchActive(ObjectProperty<Boolean> acceleratorPedalIdleSwitchActive) {
            this.acceleratorPedalIdleSwitchActive = acceleratorPedalIdleSwitchActive;
            acceleratorPedalIdleSwitchActive.setIdentifier(ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER);
            addProperty(acceleratorPedalIdleSwitchActive);
            return this;
        }

        /**
         * @param acceleratorPedalKickdownSwitchActive The accelerator pedal kickdown switch state.
         *                                             If active, pedal is fully depressed.
         * @return The builder.
         */
        public Builder setAcceleratorPedalKickdownSwitchActive(ObjectProperty<Boolean> acceleratorPedalKickdownSwitchActive) {
            this.acceleratorPedalKickdownSwitchActive = acceleratorPedalKickdownSwitchActive;
            acceleratorPedalKickdownSwitchActive.setIdentifier(ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER);
            addProperty(acceleratorPedalKickdownSwitchActive);
            return this;
        }

        /**
         * @param vehicleMoving The vehicle moving state.
         * @return The builder.
         */
        public Builder setVehicleMoving(ObjectProperty<Boolean> vehicleMoving) {
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