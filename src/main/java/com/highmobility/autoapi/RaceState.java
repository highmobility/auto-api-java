package com.highmobility.autoapi;

import com.highmobility.autoapi.property.AccelerationProperty;
import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.BrakeTorqueVectoringProperty;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

/**
 * This message is sent when a Get Race State is received by the car.
 */
public class RaceState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.RACE, 0x01);

    AccelerationProperty[] accelerationProperties;
    AccelerationProperty lateralAcceleration;
    Float understeering;
    Float oversteering;
    Float gasPedalPosition;
    Float steeringAngle;
    Float brakePressure;
    Float yawRate;
    Float rearSuspensionSteering;

    Boolean espInterventionActive;
    BrakeTorqueVectoringProperty[] brakeTorqueVectorings;


    GearMode gearMode;
    Integer selectedGear;

    /**
     * @param accelerationType The acceleration type
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
     * @return The understeering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical
     */
    public Float getUndersteering() {
        return understeering;
    }

    /**
     * @return The oversteering percentage between 0-1 whereas up to .2 is considered OK, up to .3
     * marginal, over .3 critical
     */
    public Float getOversteering() {
        return oversteering;
    }

    /**
     * @return The gas pedal position between 0-1, whereas 1 is full throttle
     */
    public Float getGasPedalPosition() {
        return gasPedalPosition;
    }

    /**
     * @return The steering angle in degrees, whereas 0 degrees is straight ahead, positive number
     * to the left and negative number to the right
     */
    public Float getSteeringAngle() {
        return steeringAngle;
    }

    /**
     * @return Brake pressure in bar, whereas 100bar is max value, full brake
     */
    public Float getBrakePressure() {
        return brakePressure;
    }

    /**
     * @return Yaw rate in degrees per second [°/s]
     */
    public Float getYawRate() {
        return yawRate;
    }

    /**
     * @return Rear suspension steering in degrees
     */
    public Float getRearSuspensionSteering() {
        return rearSuspensionSteering;
    }

    /**
     * @return ESP (Electronic Stability Program) intervention state
     */
    public Boolean isEspInterventionActive() {
        return espInterventionActive;
    }

    /**
     * @param axle The axle
     * @return Brake Torque Vectoring for the given axle. Null if doesnt exist.
     */
    public BrakeTorqueVectoringProperty getBrakeTorqueVectoring(Axle axle) {
        for (int i = 0; i < brakeTorqueVectorings.length; i++) {
            BrakeTorqueVectoringProperty property = brakeTorqueVectorings[i];
            if (property.getAxle() == axle) return property;
        }

        return null;
    }

    /**
     * @return The gear mode
     */
    public GearMode getGearMode() {
        return gearMode;
    }

    /**
     * @return The selected gear
     */
    public Integer getSelectedGear() {
        return selectedGear;
    }

    public RaceState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<AccelerationProperty> accelerationProperties = new ArrayList<>();
        ArrayList<BrakeTorqueVectoringProperty> brakeTorqueVectoringProperties = new ArrayList<>();
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    accelerationProperties.add(
                            new AccelerationProperty(property.getPropertyBytes()));
                    break;
                case 0x02:
                    understeering = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x03:
                    oversteering = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x04:
                    gasPedalPosition = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x05:
                    steeringAngle = Property.getSignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x06:
                    brakePressure = Property.getFloat(property.getValueBytes());
                    break;
                case 0x07:
                    yawRate = Property.getFloat(property.getValueBytes());
                    break;
                case 0x08:
                    rearSuspensionSteering = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x09:
                    espInterventionActive = Property.getBool(property.getValueByte());
                    break;
                case 0x0A:
                    brakeTorqueVectoringProperties.add(
                            new BrakeTorqueVectoringProperty(property.getPropertyBytes()));
                    break;
                case 0x0B:
                    gearMode = GearMode.fromByte(property.getValueByte());
                    break;
                case 0x0C:
                    selectedGear = Property.getUnsignedInt(property.getValueByte());
                    break;
            }
        }

        this.accelerationProperties = accelerationProperties.toArray(
                new AccelerationProperty[accelerationProperties.size()]);

        this.brakeTorqueVectorings = brakeTorqueVectoringProperties.toArray(
                new BrakeTorqueVectoringProperty[brakeTorqueVectoringProperties.size()]);
    }
}