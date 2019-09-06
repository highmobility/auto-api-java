// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyInteger;
import com.highmobility.autoapi.v2.value.Acceleration;
import com.highmobility.autoapi.v2.value.ActiveState;
import com.highmobility.autoapi.v2.value.BrakeTorqueVectoring;
import java.util.ArrayList;
import java.util.List;

public class RaceState extends Command {
    Property<Acceleration> accelerations[];
    Property<Double> understeering = new Property(Double.class, 0x02);
    Property<Double> oversteering = new Property(Double.class, 0x03);
    Property<Double> gasPedalPosition = new Property(Double.class, 0x04);
    PropertyInteger steeringAngle = new PropertyInteger(0x05, true);
    Property<Float> brakePressure = new Property(Float.class, 0x06);
    Property<Float> yawRate = new Property(Float.class, 0x07);
    PropertyInteger rearSuspensionSteering = new PropertyInteger(0x08, true);
    Property<ActiveState> electronicStabilityProgram = new Property(ActiveState.class, 0x09);
    Property<BrakeTorqueVectoring> brakeTorqueVectorings[];
    Property<GearMode> gearMode = new Property(GearMode.class, 0x0b);
    PropertyInteger selectedGear = new PropertyInteger(0x0c, true);
    Property<Double> brakePedalPosition = new Property(Double.class, 0x0d);
    Property<ActiveState> brakePedalSwitch = new Property(ActiveState.class, 0x0e);
    Property<ActiveState> clutchPedalSwitch = new Property(ActiveState.class, 0x0f);
    Property<ActiveState> acceleratorPedalIdleSwitch = new Property(ActiveState.class, 0x10);
    Property<ActiveState> acceleratorPedalKickdownSwitch = new Property(ActiveState.class, 0x11);
    Property<VehicleMoving> vehicleMoving = new Property(VehicleMoving.class, 0x12);

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
     * @return The steering angle in degrees, whereas 0 degrees is straight ahead, positive number to the right and negative number to the left
     */
    public PropertyInteger getSteeringAngle() {
        return steeringAngle;
    }

    /**
     * @return Brake pressure in bar, whereas 100bar is max value, full brake
     */
    public Property<Float> getBrakePressure() {
        return brakePressure;
    }

    /**
     * @return Yaw rate in degrees per second [°/s] per IEEE 754 formatting
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

    RaceState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> accelerationsBuilder = new ArrayList<>();
        ArrayList<Property> brakeTorqueVectoringsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01:
                        Property<Acceleration> acceleration = new Property(Acceleration.class, p);
                        accelerationsBuilder.add(acceleration);
                        return acceleration;
                    case 0x02: return understeering.update(p);
                    case 0x03: return oversteering.update(p);
                    case 0x04: return gasPedalPosition.update(p);
                    case 0x05: return steeringAngle.update(p);
                    case 0x06: return brakePressure.update(p);
                    case 0x07: return yawRate.update(p);
                    case 0x08: return rearSuspensionSteering.update(p);
                    case 0x09: return electronicStabilityProgram.update(p);
                    case 0x0a:
                        Property<BrakeTorqueVectoring> brakeTorqueVectoring = new Property(BrakeTorqueVectoring.class, p);
                        brakeTorqueVectoringsBuilder.add(brakeTorqueVectoring);
                        return brakeTorqueVectoring;
                    case 0x0b: return gearMode.update(p);
                    case 0x0c: return selectedGear.update(p);
                    case 0x0d: return brakePedalPosition.update(p);
                    case 0x0e: return brakePedalSwitch.update(p);
                    case 0x0f: return clutchPedalSwitch.update(p);
                    case 0x10: return acceleratorPedalIdleSwitch.update(p);
                    case 0x11: return acceleratorPedalKickdownSwitch.update(p);
                    case 0x12: return vehicleMoving.update(p);
                }

                return null;
            });
        }

        accelerations = accelerationsBuilder.toArray(new Property[0]);
        brakeTorqueVectorings = brakeTorqueVectoringsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private RaceState(Builder builder) {
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

    public static final class Builder extends Command.Builder {
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
            super(Identifier.RACE);
        }

        public RaceState build() {
            return new RaceState(this);
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
            acceleration.setIdentifier(0x01);
            addProperty(acceleration);
            accelerations.add(acceleration);
            return this;
        }
        
        /**
         * @param understeering The understeering percentage between 0.0-1.0 whereas up to 0.2 (20%) is considered OK, up to 0.3 (30%) marginal, over 30% critical
         * @return The builder
         */
        public Builder setUndersteering(Property<Double> understeering) {
            this.understeering = understeering.setIdentifier(0x02);
            addProperty(understeering);
            return this;
        }
        
        /**
         * @param oversteering The oversteering percentage between 0.0-1.0 whereas up to 0.2 (20%) is considered OK, up to 30% marginal, over 30% critical
         * @return The builder
         */
        public Builder setOversteering(Property<Double> oversteering) {
            this.oversteering = oversteering.setIdentifier(0x03);
            addProperty(oversteering);
            return this;
        }
        
        /**
         * @param gasPedalPosition The gas pedal position between 0.0-1.0, whereas 1.0 (100%) is full throttle
         * @return The builder
         */
        public Builder setGasPedalPosition(Property<Double> gasPedalPosition) {
            this.gasPedalPosition = gasPedalPosition.setIdentifier(0x04);
            addProperty(gasPedalPosition);
            return this;
        }
        
        /**
         * @param steeringAngle The steering angle in degrees, whereas 0 degrees is straight ahead, positive number to the right and negative number to the left
         * @return The builder
         */
        public Builder setSteeringAngle(PropertyInteger steeringAngle) {
            this.steeringAngle = new PropertyInteger(0x05, true, 1, steeringAngle);
            addProperty(steeringAngle);
            return this;
        }
        
        /**
         * @param brakePressure Brake pressure in bar, whereas 100bar is max value, full brake
         * @return The builder
         */
        public Builder setBrakePressure(Property<Float> brakePressure) {
            this.brakePressure = brakePressure.setIdentifier(0x06);
            addProperty(brakePressure);
            return this;
        }
        
        /**
         * @param yawRate Yaw rate in degrees per second [°/s] per IEEE 754 formatting
         * @return The builder
         */
        public Builder setYawRate(Property<Float> yawRate) {
            this.yawRate = yawRate.setIdentifier(0x07);
            addProperty(yawRate);
            return this;
        }
        
        /**
         * @param rearSuspensionSteering Rear suspension steering in degrees
         * @return The builder
         */
        public Builder setRearSuspensionSteering(PropertyInteger rearSuspensionSteering) {
            this.rearSuspensionSteering = new PropertyInteger(0x08, true, 1, rearSuspensionSteering);
            addProperty(rearSuspensionSteering);
            return this;
        }
        
        /**
         * @param electronicStabilityProgram The electronic stability program
         * @return The builder
         */
        public Builder setElectronicStabilityProgram(Property<ActiveState> electronicStabilityProgram) {
            this.electronicStabilityProgram = electronicStabilityProgram.setIdentifier(0x09);
            addProperty(electronicStabilityProgram);
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
            brakeTorqueVectoring.setIdentifier(0x0a);
            addProperty(brakeTorqueVectoring);
            brakeTorqueVectorings.add(brakeTorqueVectoring);
            return this;
        }
        
        /**
         * @param gearMode The gear mode
         * @return The builder
         */
        public Builder setGearMode(Property<GearMode> gearMode) {
            this.gearMode = gearMode.setIdentifier(0x0b);
            addProperty(gearMode);
            return this;
        }
        
        /**
         * @param selectedGear The selected gear value, if any
         * @return The builder
         */
        public Builder setSelectedGear(PropertyInteger selectedGear) {
            this.selectedGear = new PropertyInteger(0x0c, true, 1, selectedGear);
            addProperty(selectedGear);
            return this;
        }
        
        /**
         * @param brakePedalPosition The brake pedal position between 0.0-1.0, wheras 1.0 (100%) is full brakes
         * @return The builder
         */
        public Builder setBrakePedalPosition(Property<Double> brakePedalPosition) {
            this.brakePedalPosition = brakePedalPosition.setIdentifier(0x0d);
            addProperty(brakePedalPosition);
            return this;
        }
        
        /**
         * @param brakePedalSwitch The brake pedal switch
         * @return The builder
         */
        public Builder setBrakePedalSwitch(Property<ActiveState> brakePedalSwitch) {
            this.brakePedalSwitch = brakePedalSwitch.setIdentifier(0x0e);
            addProperty(brakePedalSwitch);
            return this;
        }
        
        /**
         * @param clutchPedalSwitch The clutch pedal switch
         * @return The builder
         */
        public Builder setClutchPedalSwitch(Property<ActiveState> clutchPedalSwitch) {
            this.clutchPedalSwitch = clutchPedalSwitch.setIdentifier(0x0f);
            addProperty(clutchPedalSwitch);
            return this;
        }
        
        /**
         * @param acceleratorPedalIdleSwitch The accelerator pedal idle switch
         * @return The builder
         */
        public Builder setAcceleratorPedalIdleSwitch(Property<ActiveState> acceleratorPedalIdleSwitch) {
            this.acceleratorPedalIdleSwitch = acceleratorPedalIdleSwitch.setIdentifier(0x10);
            addProperty(acceleratorPedalIdleSwitch);
            return this;
        }
        
        /**
         * @param acceleratorPedalKickdownSwitch The accelerator pedal kickdown switch
         * @return The builder
         */
        public Builder setAcceleratorPedalKickdownSwitch(Property<ActiveState> acceleratorPedalKickdownSwitch) {
            this.acceleratorPedalKickdownSwitch = acceleratorPedalKickdownSwitch.setIdentifier(0x11);
            addProperty(acceleratorPedalKickdownSwitch);
            return this;
        }
        
        /**
         * @param vehicleMoving The vehicle moving
         * @return The builder
         */
        public Builder setVehicleMoving(Property<VehicleMoving> vehicleMoving) {
            this.vehicleMoving = vehicleMoving.setIdentifier(0x12);
            addProperty(vehicleMoving);
            return this;
        }
    }

    public enum GearMode {
        MANUAL((byte)0x00),
        PARK((byte)0x01),
        REVERSE((byte)0x02),
        NEUTRAL((byte)0x03),
        DRIVE((byte)0x04),
        LOW_GEAR((byte)0x05),
        SPORT((byte)0x06);
    
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
    
        public byte getByte() {
            return value;
        }
    }

    public enum VehicleMoving {
        NOT_MOVING((byte)0x00),
        MOVING((byte)0x01);
    
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
    
        public byte getByte() {
            return value;
        }
    }
}