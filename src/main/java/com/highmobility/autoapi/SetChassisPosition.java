// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;

/**
 * Set chassis position
 */
public class SetChassisPosition extends SetCommand {
    PropertyInteger currentChassisPosition = new PropertyInteger(0x08, true);

    /**
     * @return The current chassis position
     */
    public PropertyInteger getCurrentChassisPosition() {
        return currentChassisPosition;
    }
    
    /**
     * Set chassis position
     *
     * @param currentChassisPosition The The chassis position in mm calculated from the lowest point
     */
    public SetChassisPosition(Integer currentChassisPosition) {
        super(Identifier.CHASSIS_SETTINGS);
    
        addProperty(this.currentChassisPosition.update(true, 1, currentChassisPosition), true);
    }

    SetChassisPosition(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x08: return currentChassisPosition.update(p);
                }
                return null;
            });
        }
        if (this.currentChassisPosition.getValue() == null) 
            throw new NoPropertiesException();
    }
}