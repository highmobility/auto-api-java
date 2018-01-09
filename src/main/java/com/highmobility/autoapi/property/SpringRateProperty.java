package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import java.io.UnsupportedEncodingException;

public class SpringRateProperty extends Property {
    Axle axle;
    Integer springRate;
    Integer maximumPossibleRate;
    Integer minimumPossibleRate;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     *
     * @return The suspension spring rate in N/mm
     */
    public Integer getSpringRate() {
        return springRate;
    }

    /**
     *
     * @return The maximum possible value for the spring rate
     */
    public Integer getMaximumPossibleRate() {
        return maximumPossibleRate;
    }

    /**
     *
     * @return The minimum possible value for the spring rate
     */
    public Integer getMinimumPossibleRate() {
        return minimumPossibleRate;
    }

    public SpringRateProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        axle = Axle.fromByte(bytes[3]);
        springRate = Property.getUnsignedInt(bytes[4]);
        maximumPossibleRate = Property.getUnsignedInt(bytes[5]);
        minimumPossibleRate = Property.getUnsignedInt(bytes[6]);
    }

    public SpringRateProperty(byte identifier, Axle type, Integer springRate, Integer
            maximumPossibleRate, Integer minimumPossibleRate) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = type.getByte();
        bytes[4] = springRate.byteValue();
        bytes[5] = maximumPossibleRate.byteValue();
        bytes[6] = minimumPossibleRate.byteValue();
    }
}