// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.ActiveState;
import javax.annotation.Nullable;

/**
 * Activate deactivate cruise control
 */
public class ActivateDeactivateCruiseControl extends SetCommand {
    Property<ActiveState> cruiseControl = new Property(ActiveState.class, 0x01);
    @Nullable PropertyInteger targetSpeed = new PropertyInteger(0x03, true);

    /**
     * @return The cruise control
     */
    public Property<ActiveState> getCruiseControl() {
        return cruiseControl;
    }
    
    /**
     * @return The target speed
     */
    public @Nullable PropertyInteger getTargetSpeed() {
        return targetSpeed;
    }
    
    /**
     * Activate deactivate cruise control
     *
     * @param cruiseControl The cruise control
     * @param targetSpeed The The target speed in km/h
     */
    public ActivateDeactivateCruiseControl(ActiveState cruiseControl, @Nullable Integer targetSpeed) {
        super(Identifier.CRUISE_CONTROL);
    
        addProperty(this.cruiseControl.update(cruiseControl));
        addProperty(this.targetSpeed.update(true, 2, targetSpeed), true);
    }

    ActivateDeactivateCruiseControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return cruiseControl.update(p);
                    case 0x03: return targetSpeed.update(p);
                }
                return null;
            });
        }
        if (this.cruiseControl.getValue() == null) 
            throw new NoPropertiesException();
    }
}