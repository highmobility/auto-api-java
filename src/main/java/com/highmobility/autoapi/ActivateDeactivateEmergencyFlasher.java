// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Activate deactivate emergency flasher
 */
public class ActivateDeactivateEmergencyFlasher extends SetCommand {
    Property<ActiveState> emergencyFlashersState = new Property(ActiveState.class, 0x04);

    /**
     * @return The emergency flashers state
     */
    public Property<ActiveState> getEmergencyFlashersState() {
        return emergencyFlashersState;
    }
    
    /**
     * Activate deactivate emergency flasher
     *
     * @param emergencyFlashersState The emergency flashers state
     */
    public ActivateDeactivateEmergencyFlasher(ActiveState emergencyFlashersState) {
        super(Identifier.HONK_HORN_FLASH_LIGHTS);
    
        addProperty(this.emergencyFlashersState.update(emergencyFlashersState), true);
    }

    ActivateDeactivateEmergencyFlasher(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x04: return emergencyFlashersState.update(p);
                }
                return null;
            });
        }
        if (this.emergencyFlashersState.getValue() == null) 
            throw new NoPropertiesException();
    }
}