// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Activate deactivate solar charging
 */
public class ActivateDeactivateSolarCharging extends SetCommand {
    Property<ActiveState> solarCharging = new Property(ActiveState.class, 0x05);

    /**
     * @return The solar charging
     */
    public Property<ActiveState> getSolarCharging() {
        return solarCharging;
    }
    
    /**
     * Activate deactivate solar charging
     *
     * @param solarCharging The solar charging
     */
    public ActivateDeactivateSolarCharging(ActiveState solarCharging) {
        super(Identifier.HOME_CHARGER);
    
        addProperty(this.solarCharging.update(solarCharging), true);
    }

    ActivateDeactivateSolarCharging(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x05: return solarCharging.update(p);
                }
                return null;
            });
        }
        if (this.solarCharging.getValue() == null) 
            throw new NoPropertiesException();
    }
}