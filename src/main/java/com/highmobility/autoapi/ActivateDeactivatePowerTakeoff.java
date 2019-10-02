// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Activate deactivate power takeoff
 */
public class ActivateDeactivatePowerTakeoff extends SetCommand {
    Property<ActiveState> status = new Property(ActiveState.class, 0x01);

    /**
     * @return The status
     */
    public Property<ActiveState> getStatus() {
        return status;
    }
    
    /**
     * Activate deactivate power takeoff
     *
     * @param status The status
     */
    public ActivateDeactivatePowerTakeoff(ActiveState status) {
        super(Identifier.POWER_TAKEOFF);
    
        addProperty(this.status.update(status), true);
    }

    ActivateDeactivatePowerTakeoff(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                }
                return null;
            });
        }
        if (this.status.getValue() == null) 
            throw new NoPropertiesException();
    }
}