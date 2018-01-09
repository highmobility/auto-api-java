package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

import java.io.UnsupportedEncodingException;

public class BrakeTorqueVectoringProperty extends Property {
    Axle axle;
    boolean active;

    /**
     *
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     *
     * @return Whether brake torque vectoring is active or not.
     */
    public boolean isActive() {
        return active;
    }

    public BrakeTorqueVectoringProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        axle = Axle.fromByte(bytes[3]);
        active = Property.getBool(bytes[4]);
    }

    public BrakeTorqueVectoringProperty(byte identifier, Axle type, boolean active) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = type.getByte();
        bytes[4] = Property.boolToByte(active);
    }
}