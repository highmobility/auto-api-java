// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DrivingMode;

/**
 * Set driving mode
 */
public class SetDrivingMode extends SetCommand {
    Property<DrivingMode> drivingMode = new Property(DrivingMode.class, 0x01);

    /**
     * @return The driving mode
     */
    public Property<DrivingMode> getDrivingMode() {
        return drivingMode;
    }
    
    /**
     * Set driving mode
     *
     * @param drivingMode The driving mode
     */
    public SetDrivingMode(DrivingMode drivingMode) {
        super(Identifier.CHASSIS_SETTINGS);
    
        addProperty(this.drivingMode.update(drivingMode), true);
    }

    SetDrivingMode(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return drivingMode.update(p);
                }
                return null;
            });
        }
        if (this.drivingMode.getValue() == null) 
            throw new NoPropertiesException();
    }
}