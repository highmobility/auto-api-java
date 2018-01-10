package com.highmobility.autoapi;

import com.highmobility.autoapi.property.FatigueLevel;
import com.highmobility.autoapi.property.Property;

/**
 * An evented message that notifies about driver fatigue. Sent continously when level 1 or higher.
 */
public class DriverFatigueDetected extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DRIVER_FATIGUE, 0x01);

    FatigueLevel fatigueLevel;

    /**
     * @return The driver fatigue level
     */
    public FatigueLevel getFatigueLevel() {
        return fatigueLevel;
    }

    public DriverFatigueDetected(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    fatigueLevel = FatigueLevel.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}