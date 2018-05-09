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
import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.BrakeTorqueVectoringProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    AccelerationProperty[] accelerationProperties;

    Float underSteering;
    Float overSteering;
    Float gasPedalPosition;
    Integer steeringAngle;
    Float brakePressure;
    Float yawRate;
    Integer rearSuspensionSteering;
    Boolean espInterventionActive;
    BrakeTorqueVectoringProperty[] brakeTorqueVectorings;
    GearMode gearMode;
    Integer selectedGear;
    Float brakePedalPosition;
    // level7
    Boolean brakePedalSwitchActive;
    Boolean clutchPedalSwitchActive;
    Boolean acceleratorPedalIdleSwitchActive;
    Boolean acceleratorPedalKickdownSwitchActive;

    /**
     * @param accelerationType The acceleration type.
     * @return Acceleration for the given acceleration type. Null if doesnt exist.
     */
    public AccelerationProperty getAcceleration(AccelerationProperty.AccelerationType
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
    public Float getUnderSteering() {
        return underSteering;
    }

    /**
     * @return The over steering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical.
     */
    public Float getOverSteering() {
        return overSteering;
    }

    /**
     * @return The gas pedal position between 0-1, whereas 1 is full throttle.
     */
    public Float getGasPedalPosition() {
        return gasPedalPosition;
    }

    /**
     * @return The steering angle in degrees, whereas 0 degrees is straight ahead, positive number
     * to the left and negative number to the right.
     */
    public Integer getSteeringAngle() {
        return steeringAngle;
    }

    /**
     * @return The Brake pressure in bar, whereas 100bar is max value, full brake.
     */
    public Float getBrakePressure() {
        return brakePressure;
    }

    /**
     * @return The yaw rate in degrees per second [°/s].
     */
    public Float getYawRate() {
        return yawRate;
    }

    /**
     * @return The rear suspension steering in degrees.
     */
    public Integer getRearSuspensionSteering() {
        return rearSuspensionSteering;
    }

    /**
     * @return The ESP (Electronic Stability Program) intervention state.
     */
    public Boolean isEspInterventionActive() {
        return espInterventionActive;
    }

    /**
     * @param axle The axle.
     * @return The Brake Torque Vectoring for the given axle. Null if doesn't exist.
     */
    public BrakeTorqueVectoringProperty getBrakeTorqueVectoring(Axle axle) {
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
    public GearMode getGearMode() {
        return gearMode;
    }

    /**
     * @return The selected gear.
     */
    public Integer getSelectedGear() {
        return selectedGear;
    }

    /**
     * @return The brake pedal position between 0-1, whereas 1 is full brakes.
     */
    public Float getBrakePedalPosition() {
        return brakePedalPosition;
    }

    /**
     * @return The brake pedal switch state.
     */
    public Boolean isBrakePedalSwitchActive() {
        return brakePedalSwitchActive;
    }

    /**
     * @return The clutch pedal switch state.
     */
    public Boolean isClutchPedalSwitchActive() {
        return clutchPedalSwitchActive;
    }

    /**
     * @return The accelerator pedal idle switch state. If active, pedal is fully released.
     */
    public Boolean isAcceleratorPedalIdleSwitchActive() {
        return acceleratorPedalIdleSwitchActive;
    }

    /**
     * @return The accelerator pedal kickdown switch state. If active, pedal is fully depressed.
     */
    public Boolean isAcceleratorPedalKickdownSwitchActive() {
        return acceleratorPedalKickdownSwitchActive;
    }

    public RaceState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<AccelerationProperty> accelerationProperties = new ArrayList<>();
        ArrayList<BrakeTorqueVectoringProperty> brakeTorqueVectoringProperties = new ArrayList<>();
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case AccelerationProperty.IDENTIFIER:
                    accelerationProperties.add(
                            new AccelerationProperty(property.getPropertyBytes()));
                    break;
                case UNDER_STEERING_IDENTIFIER:
                    underSteering = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case OVER_STEERING_IDENTIFIER:
                    overSteering = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case GAS_PEDAL_POSITION_IDENTIFIER:
                    gasPedalPosition = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case STEERING_ANGLE_IDENTIFIER:
                    steeringAngle = Property.getSignedInt(property.getValueByte());
                    break;
                case BRAKE_PRESSURE_IDENTIFIER:
                    brakePressure = Property.getFloat(property.getValueBytes());
                    break;
                case YAW_RATE_IDENTIFIER:
                    yawRate = Property.getFloat(property.getValueBytes());
                    break;
                case REAR_SUSPENSION_STEERING_IDENTIFIER:
                    rearSuspensionSteering = Property.getUnsignedInt(property.getValueByte());
                    break;
                case ESP_INTERVENTION_ACTIVE_IDENTIFIER:
                    espInterventionActive = Property.getBool(property.getValueByte());
                    break;
                case BrakeTorqueVectoringProperty.IDENTIFIER:
                    brakeTorqueVectoringProperties.add(
                            new BrakeTorqueVectoringProperty(property.getPropertyBytes()));
                    break;
                case GearMode.IDENTIFIER:
                    gearMode = GearMode.fromByte(property.getValueByte());
                    break;
                case SELECTED_GEAR_IDENTIFIER:
                    selectedGear = Property.getUnsignedInt(property.getValueByte());
                    break;
                case BRAKE_PEDAL_POSITION_IDENTIFIER:
                    brakePedalPosition = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case BRAKE_PEDAL_SWITCH_IDENTIFIER:
                    brakePedalSwitchActive = Property.getBool(property.getValueByte());
                    break;
                case CLUTCH_PEDAL_SWITCH_IDENTIFIER:
                    clutchPedalSwitchActive = Property.getBool(property.getValueByte());
                    break;
                case ACCELERATOR_PEDAL_IDLE_SWITCH_IDENTIFIER:
                    acceleratorPedalIdleSwitchActive = Property.getBool(property.getValueByte());
                    break;
                case ACCELERATOR_PEDAL_KICKDOWN_SWITCH_IDENTIFIER:
                    acceleratorPedalKickdownSwitchActive = Property.getBool(property.getValueByte
                            ());
                    break;
            }
        }

        this.accelerationProperties = accelerationProperties.toArray(
                new AccelerationProperty[accelerationProperties.size()]);

        this.brakeTorqueVectorings = brakeTorqueVectoringProperties.toArray(
                new BrakeTorqueVectoringProperty[brakeTorqueVectoringProperties.size()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private RaceState(Builder builder) {
        super(builder);
        accelerationProperties = builder.accelerationProperties.toArray(new
                AccelerationProperty[builder.accelerationProperties.size()]);
        underSteering = builder.underSteering;
        overSteering = builder.overSteering;
        gasPedalPosition = builder.gasPedalPosition;
        steeringAngle = builder.steeringAngle;
        brakePressure = builder.brakePressure;
        yawRate = builder.yawRate;
        rearSuspensionSteering = builder.rearSuspensionSteering;
        espInterventionActive = builder.espInterventionActive;
        brakeTorqueVectorings = builder.brakeTorqueVectorings.toArray(new
                BrakeTorqueVectoringProperty[builder.brakeTorqueVectorings.size()]);
        gearMode = builder.gearMode;
        selectedGear = builder.selectedGear;
        brakePedalPosition = builder.brakePedalPosition;

        brakePedalSwitchActive = builder.brakePedalSwitchActive;
        clutchPedalSwitchActive = builder.clutchPedalSwitchActive;
        acceleratorPedalIdleSwitchActive = builder.acceleratorPedalIdleSwitchActive;
        acceleratorPedalKickdownSwitchActive = builder.acceleratorPedalKickdownSwitchActive;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<AccelerationProperty> accelerationProperties = new ArrayList<>();

        private Float underSteering;
        private Float overSteering;
        private Float gasPedalPosition;
        private Integer steeringAngle;
        private Float brakePressure;
        private Float yawRate;
        private Integer rearSuspensionSteering;
        private Boolean espInterventionActive;
        private List<BrakeTorqueVectoringProperty> brakeTorqueVectorings = new ArrayList<>();
        private GearMode gearMode;
        private Integer selectedGear;
        private Float brakePedalPosition;

        private Boolean brakePedalSwitchActive;
        private Boolean clutchPedalSwitchActive;
        private Boolean acceleratorPedalIdleSwitchActive;
        private Boolean acceleratorPedalKickdownSwitchActive;

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
        public Builder setUnderSteering(Float underSteering) {
            this.underSteering = underSteering;
            addProperty(new PercentageProperty(UNDER_STEERING_IDENTIFIER, underSteering));
            return this;
        }

        /**
         * @param overSteering The over steering percentage between 0-1 whereas up to .2 is
         *                     considered OK, up to .3 marginal, over .3 critical
         * @return The builder.
         */
        public Builder setOverSteering(Float overSteering) {
            this.overSteering = overSteering;
            // TODO: 04/05/2018 change other floatToIntPercentage as well (in other commands)
            addProperty(new PercentageProperty(OVER_STEERING_IDENTIFIER, overSteering));
            return this;
        }

        /**
         * @param gasPedalPosition The gas pedal position between 0-1, whereas 1 is full throttle.
         * @return The builder.
         */
        public Builder setGasPedalPosition(Float gasPedalPosition) {
            this.gasPedalPosition = gasPedalPosition;
            addProperty(new PercentageProperty(GAS_PEDAL_POSITION_IDENTIFIER, gasPedalPosition));
            return this;
        }

        /**
         * @param steeringAngle The steering angle in degrees, whereas 0 degrees is straight ahead,
         *                      positive number to the left and negative number to the right.
         * @return The builder.
         */
        public Builder setSteeringAngle(Integer steeringAngle) {
            this.steeringAngle = steeringAngle;
            addProperty(new IntegerProperty(STEERING_ANGLE_IDENTIFIER, steeringAngle, 1));
            return this;
        }

        /**
         * @param brakePressure The brake pressure in bar, whereas 100bar is max value, full brake.
         * @return The builder.
         */
        public Builder setBrakePressure(Float brakePressure) {
            this.brakePressure = brakePressure;
            addProperty(new FloatProperty(BRAKE_PRESSURE_IDENTIFIER, brakePressure));
            return this;
        }

        /**
         * @param yawRate The yaw rate in degrees per second [°/s].
         * @return The builder.
         */
        public Builder setYawRate(Float yawRate) {
            this.yawRate = yawRate;
            addProperty(new FloatProperty(YAW_RATE_IDENTIFIER, yawRate));
            return this;
        }

        /**
         * @param rearSuspensionSteering The rear suspension steering in degrees.
         * @return The builder
         */
        public Builder setRearSuspensionSteering(Integer rearSuspensionSteering) {
            this.rearSuspensionSteering = rearSuspensionSteering;
            addProperty(new IntegerProperty(REAR_SUSPENSION_STEERING_IDENTIFIER,
                    rearSuspensionSteering, 1));
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
            addProperty(gearMode);
            return this;
        }

        /**
         * @param selectedGear The selected gear.
         * @return The builder.
         */
        public Builder setSelectedGear(Integer selectedGear) {
            this.selectedGear = selectedGear;
            addProperty(new IntegerProperty(SELECTED_GEAR_IDENTIFIER, selectedGear, 1));
            return this;
        }

        /**
         * @param brakePedalPosition The brake pedal position.
         * @return The builder.
         */
        public Builder setBrakePedalPosition(Float brakePedalPosition) {
            this.brakePedalPosition = brakePedalPosition;
            addProperty(new PercentageProperty(BRAKE_PEDAL_POSITION_IDENTIFIER,
                    brakePedalPosition));
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

        public RaceState build() {
            return new RaceState(this);
        }
    }
}