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
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.BrakeTorqueVectoringProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
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

    private static final byte UNDER_STEERING_IDENTIFIER = 0x02;
    private static final byte OVER_STEERING_IDENTIFIER = 0x03;
    private static final byte GAS_PEDAL_POSITION_IDENTIFIER = 0x04;
    private static final byte STEERING_ANGLE_IDENTIFIER = 0x05;
    private static final byte BRAKE_PRESSURE_IDENTIFIER = 0x06;
    private static final byte YAW_RATE_IDENTIFIER = 0x07;
    private static final byte REAR_SUSPENSION_STEERING_IDENTIFIER = 0x08;
    private static final byte ESP_INTERVENTION_ACTIVE_IDENTIFIER = 0x09;
    private static final byte SELECTED_GEAR_IDENTIFIER = 0x0C;
    private static final byte BRAKE_PEDAL_POSITION_IDENTIFIER = 0x0D;

    private static final byte BRAKE_PEDAL_SWITCH_IDENTIFIER = 0x0E;
    private static final byte CLUTCH_PEDAL_SWITCH_IDENTIFIER = 0x0F;
    private static final byte ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER = 0x10;
    private static final byte ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER = 0x11;

    private static final byte IDENTIFIER_VEHICLE_MOVING = 0x12;
    public static final byte IDENTIFIER_GEAR_MODE = 0x0B;

    AccelerationProperty[] accelerationProperties;

    PercentageProperty underSteering;
    PercentageProperty overSteering;
    PercentageProperty gasPedalPosition;
    IntegerProperty steeringAngle;
    FloatProperty brakePressure;
    FloatProperty yawRate;
    IntegerProperty rearSuspensionSteering;
    Boolean espInterventionActive;
    BrakeTorqueVectoringProperty[] brakeTorqueVectorings;
    GearMode gearMode;
    IntegerProperty selectedGear;
    PercentageProperty brakePedalPosition;
    // level7
    Boolean brakePedalSwitchActive;
    Boolean clutchPedalSwitchActive;
    Boolean acceleratorPedalIdleSwitchActive;
    Boolean acceleratorPedalKickdownSwitchActive;
    // level8
    Boolean vehicleMoving;

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
    @Nullable public PercentageProperty getUnderSteering() {
        return underSteering;
    }

    /**
     * @return The over steering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical.
     */
    @Nullable public PercentageProperty getOverSteering() {
        return overSteering;
    }

    /**
     * @return The gas pedal position between 0-1, whereas 1 is full throttle.
     */
    @Nullable public PercentageProperty getGasPedalPosition() {
        return gasPedalPosition;
    }

    /**
     * @return The steering angle in degrees, whereas 0 degrees is straight ahead, positive number
     * to the left and negative number to the right.
     */
    @Nullable public IntegerProperty getSteeringAngle() {
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
    @Nullable public IntegerProperty getRearSuspensionSteering() {
        return rearSuspensionSteering;
    }

    /**
     * @return The ESP (Electronic Stability Program) intervention state.
     */
    @Nullable public Boolean isEspInterventionActive() {
        return espInterventionActive;
    }

    /**
     * @param axle The axle.
     * @return The Brake Torque Vectoring for the given axle. Null if doesn't exist.
     */
    @Nullable public BrakeTorqueVectoringProperty getBrakeTorqueVectoring(Axle axle) {
        for (int i = 0; i < brakeTorqueVectorings.length; i++) {
            BrakeTorqueVectoringProperty property = brakeTorqueVectorings[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @return All of the brake torque vectorings.
     */
    public BrakeTorqueVectoringProperty[] getBrakeTorqueVectorings() {
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
    @Nullable public IntegerProperty getSelectedGear() {
        return selectedGear;
    }

    /**
     * @return The brake pedal position between 0-1, whereas 1 is full brakes.
     */
    @Nullable public PercentageProperty getBrakePedalPosition() {
        return brakePedalPosition;
    }

    /**
     * @return The brake pedal switch state.
     */
    @Nullable public Boolean isBrakePedalSwitchActive() {
        return brakePedalSwitchActive;
    }

    /**
     * @return The clutch pedal switch state.
     */
    @Nullable public Boolean isClutchPedalSwitchActive() {
        return clutchPedalSwitchActive;
    }

    /**
     * @return The accelerator pedal idle switch state. If active, pedal is fully released.
     */
    @Nullable public Boolean isAcceleratorPedalIdleSwitchActive() {
        return acceleratorPedalIdleSwitchActive;
    }

    /**
     * @return The accelerator pedal kickdown switch state. If active, pedal is fully depressed.
     */
    @Nullable public Boolean isAcceleratorPedalKickdownSwitchActive() {
        return acceleratorPedalKickdownSwitchActive;
    }

    /**
     * @return The vehicle moving state.
     */
    @Nullable public Boolean isVehicleMoving() {
        return vehicleMoving;
    }

    public RaceState(byte[] bytes) {
        super(bytes);

        ArrayList<AccelerationProperty> accelerationProperties = new ArrayList<>();
        ArrayList<BrakeTorqueVectoringProperty> brakeTorqueVectoringProperties = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case AccelerationProperty.IDENTIFIER:
                        AccelerationProperty a = new AccelerationProperty(p.getByteArray());
                        accelerationProperties.add(a);
                        return a;
                    case UNDER_STEERING_IDENTIFIER:
                        underSteering = new PercentageProperty(p);
                        return underSteering;
                    case OVER_STEERING_IDENTIFIER:
                        overSteering = new PercentageProperty(p);
                        return overSteering;
                    case GAS_PEDAL_POSITION_IDENTIFIER:
                        gasPedalPosition = new PercentageProperty(p);
                        return gasPedalPosition;
                    case STEERING_ANGLE_IDENTIFIER:
                        steeringAngle = new IntegerProperty(p, true);
                        return steeringAngle;
                    case BRAKE_PRESSURE_IDENTIFIER:
                        brakePressure = new FloatProperty(p);
                        return brakePressure;
                    case YAW_RATE_IDENTIFIER:
                        yawRate = new FloatProperty(p);
                        return yawRate;
                    case REAR_SUSPENSION_STEERING_IDENTIFIER:
                        rearSuspensionSteering = new IntegerProperty(p, false);
                        return rearSuspensionSteering;
                    case ESP_INTERVENTION_ACTIVE_IDENTIFIER:
                        espInterventionActive = Property.getBool(p.getValueByte());
                        return espInterventionActive;
                    case BrakeTorqueVectoringProperty.IDENTIFIER:
                        BrakeTorqueVectoringProperty b =
                                new BrakeTorqueVectoringProperty(p.getByteArray());
                        brakeTorqueVectoringProperties.add(b);
                        return b;
                    case IDENTIFIER_GEAR_MODE:
                        gearMode = GearMode.fromByte(p.getValueByte());
                        return gearMode;
                    case SELECTED_GEAR_IDENTIFIER:
                        selectedGear = new IntegerProperty(p, false);
                        return selectedGear;
                    case BRAKE_PEDAL_POSITION_IDENTIFIER:
                        brakePedalPosition = new PercentageProperty(p);
                        return brakePedalPosition;
                    case BRAKE_PEDAL_SWITCH_IDENTIFIER:
                        brakePedalSwitchActive = Property.getBool(p.getValueByte());
                        return brakePedalSwitchActive;
                    case CLUTCH_PEDAL_SWITCH_IDENTIFIER:
                        clutchPedalSwitchActive = Property.getBool(p.getValueByte());
                        return clutchPedalSwitchActive;
                    case ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER:
                        acceleratorPedalIdleSwitchActive = Property.getBool(p.getValueByte());
                        return acceleratorPedalIdleSwitchActive;
                    case ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER:
                        acceleratorPedalKickdownSwitchActive = Property.getBool(p.getValueByte());
                        return acceleratorPedalKickdownSwitchActive;
                    case IDENTIFIER_VEHICLE_MOVING:
                        vehicleMoving = Property.getBool(p.getValueByte());
                        return vehicleMoving;
                }

                return null;
            });
        }

        this.accelerationProperties = accelerationProperties.toArray(
                new AccelerationProperty[0]);

        this.brakeTorqueVectorings = brakeTorqueVectoringProperties.toArray(
                new BrakeTorqueVectoringProperty[0]);
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
                BrakeTorqueVectoringProperty[0]);
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

        private PercentageProperty underSteering;
        private PercentageProperty overSteering;
        private PercentageProperty gasPedalPosition;
        private IntegerProperty steeringAngle;
        private FloatProperty brakePressure;
        private FloatProperty yawRate;
        private IntegerProperty rearSuspensionSteering;
        private Boolean espInterventionActive;
        private List<BrakeTorqueVectoringProperty> brakeTorqueVectorings = new ArrayList<>();
        private GearMode gearMode;
        private IntegerProperty selectedGear;
        private PercentageProperty brakePedalPosition;

        private Boolean brakePedalSwitchActive;
        private Boolean clutchPedalSwitchActive;
        private Boolean acceleratorPedalIdleSwitchActive;
        private Boolean acceleratorPedalKickdownSwitchActive;

        public Boolean vehicleMoving;

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
        public Builder setUnderSteering(PercentageProperty underSteering) {
            this.underSteering = underSteering;
            underSteering.setIdentifier(UNDER_STEERING_IDENTIFIER);
            addProperty(underSteering);
            return this;
        }

        /**
         * @param overSteering The over steering percentage between 0-1 whereas up to .2 is
         *                     considered OK, up to .3 marginal, over .3 critical
         * @return The builder.
         */
        public Builder setOverSteering(PercentageProperty overSteering) {
            this.overSteering = overSteering;
            overSteering.setIdentifier(OVER_STEERING_IDENTIFIER);
            addProperty(overSteering);
            return this;
        }

        /**
         * @param gasPedalPosition The gas pedal position between 0-1, whereas 1 is full throttle.
         * @return The builder.
         */
        public Builder setGasPedalPosition(PercentageProperty gasPedalPosition) {
            this.gasPedalPosition = gasPedalPosition;
            gasPedalPosition.setIdentifier(GAS_PEDAL_POSITION_IDENTIFIER);
            addProperty(gasPedalPosition);
            return this;
        }

        /**
         * @param steeringAngle The steering angle in degrees, whereas 0 degrees is straight ahead,
         *                      positive number to the left and negative number to the right.
         * @return The builder.
         */
        public Builder setSteeringAngle(IntegerProperty steeringAngle) {
            this.steeringAngle = steeringAngle;
            steeringAngle.setIdentifier(STEERING_ANGLE_IDENTIFIER, 1);
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
        public Builder setRearSuspensionSteering(IntegerProperty rearSuspensionSteering) {
            this.rearSuspensionSteering = rearSuspensionSteering;
            rearSuspensionSteering.setIdentifier(REAR_SUSPENSION_STEERING_IDENTIFIER, 1);
            addProperty(rearSuspensionSteering);
            return this;
        }

        /**
         * @param espInterventionActive The ESP (Electronic Stability Program) intervention state.
         * @return The builder.
         */
        public Builder setEspInterventionActive(Boolean espInterventionActive) {
            this.espInterventionActive = espInterventionActive;
            addProperty(new BooleanProperty(ESP_INTERVENTION_ACTIVE_IDENTIFIER,
                    espInterventionActive));
            return this;
        }

        /**
         * @param brakeTorqueVectorings The brake torque vectorings.
         * @return The builder.
         */
        public Builder setBrakeTorqueVectorings(BrakeTorqueVectoringProperty[]
                                                        brakeTorqueVectorings) {
            this.brakeTorqueVectorings = Arrays.asList(brakeTorqueVectorings);
            for (BrakeTorqueVectoringProperty brakeTorqueVectoring : brakeTorqueVectorings) {
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
        public Builder addBrakeTorqueVectoring(BrakeTorqueVectoringProperty brakeTorqueVectoring) {
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
        public Builder setSelectedGear(IntegerProperty selectedGear) {
            this.selectedGear = selectedGear;
            selectedGear.setIdentifier(SELECTED_GEAR_IDENTIFIER, 1);
            addProperty(selectedGear);
            return this;
        }

        /**
         * @param brakePedalPosition The brake pedal position.
         * @return The builder.
         */
        public Builder setBrakePedalPosition(PercentageProperty brakePedalPosition) {
            this.brakePedalPosition = brakePedalPosition;
            brakePedalPosition.setIdentifier(BRAKE_PEDAL_POSITION_IDENTIFIER);
            addProperty(brakePedalPosition);
            return this;
        }

        /**
         * @param brakePedalSwitchActive The brake pedal switch state.
         * @return The builder.
         */
        public Builder setBrakePedalSwitchActive(Boolean brakePedalSwitchActive) {
            this.brakePedalSwitchActive = brakePedalSwitchActive;
            addProperty(new BooleanProperty(BRAKE_PEDAL_SWITCH_IDENTIFIER, brakePedalSwitchActive));
            return this;
        }

        /**
         * @param clutchPedalSwitchActive The clutch pedal switch state.
         * @return The builder.
         */
        public Builder setClutchPedalSwitchActive(Boolean clutchPedalSwitchActive) {
            this.clutchPedalSwitchActive = clutchPedalSwitchActive;
            addProperty(new BooleanProperty(CLUTCH_PEDAL_SWITCH_IDENTIFIER,
                    clutchPedalSwitchActive));
            return this;
        }

        /**
         * @param acceleratorPedalIdleSwitchActive The accelerator pedal idle switch state. If
         *                                         active, pedal is fully released.
         * @return The builder.
         */
        public Builder setAcceleratorPedalIdleSwitchActive(Boolean acceleratorPedalIdleSwitchActive) {
            this.acceleratorPedalIdleSwitchActive = acceleratorPedalIdleSwitchActive;
            addProperty(new BooleanProperty(ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER,
                    acceleratorPedalIdleSwitchActive));
            return this;
        }

        /**
         * @param acceleratorPedalKickdownSwitchActive The accelerator pedal kickdown switch state.
         *                                             If active, pedal is fully depressed.
         * @return The builder.
         */
        public Builder setAcceleratorPedalKickdownSwitchActive(Boolean acceleratorPedalKickdownSwitchActive) {
            this.acceleratorPedalKickdownSwitchActive = acceleratorPedalKickdownSwitchActive;
            addProperty(new BooleanProperty(ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER,
                    acceleratorPedalKickdownSwitchActive));
            return this;
        }

        /**
         * @param vehicleMoving The vehicle moving state.
         * @return The builder.
         */
        public Builder setVehicleMoving(Boolean vehicleMoving) {
            this.vehicleMoving = vehicleMoving;
            addProperty(new BooleanProperty(IDENTIFIER_VEHICLE_MOVING, vehicleMoving));
            return this;
        }

        public RaceState build() {
            return new RaceState(this);
        }
    }
}