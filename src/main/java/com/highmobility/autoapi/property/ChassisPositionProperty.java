package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

import java.io.UnsupportedEncodingException;

public class ChassisPositionProperty extends Property {
    Integer position;
    Integer maximumPossibleRate;
    Integer minimumPossibleRate;

    /**
     *
     * @return The chassis position in mm calculated from the lowest point
     */
    public Integer getPosition() {
        return position;
    }

    /**
     *
     * @return The maximum possible value for the chassis position
     */
    public Integer getMaximumPossibleValue() {
        return maximumPossibleRate;
    }

    /**
     *
     * @return The minimum value for the chassis position
     */
    public Integer getMinimumPossibleValue() {
        return minimumPossibleRate;
    }

    public ChassisPositionProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        position = Property.getSignedInt(bytes[3]);
        maximumPossibleRate = Property.getSignedInt(bytes[4]);
        minimumPossibleRate = Property.getSignedInt(bytes[5]);
    }

    public ChassisPositionProperty(byte identifier, Integer position, Integer
            maximumPossibleRate, Integer minimumPossibleRate) throws UnsupportedEncodingException {
        super(identifier, 5);

        bytes[3] = position.byteValue();
        bytes[4] = maximumPossibleRate.byteValue();
        bytes[5] = minimumPossibleRate.byteValue();
    }
}