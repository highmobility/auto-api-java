// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Position;

/**
 * Open close charging port
 */
public class OpenCloseChargingPort extends SetCommand {
    Property<Position> chargePortState = new Property(Position.class, 0x0b);

    /**
     * @return The charge port state
     */
    public Property<Position> getChargePortState() {
        return chargePortState;
    }
    
    /**
     * Open close charging port
     *
     * @param chargePortState The charge port state
     */
    public OpenCloseChargingPort(Position chargePortState) {
        super(Identifier.CHARGING);
    
        addProperty(this.chargePortState.update(chargePortState), true);
    }

    OpenCloseChargingPort(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x0b: return chargePortState.update(p);
                }
                return null;
            });
        }
        if (this.chargePortState.getValue() == null) 
            throw new NoPropertiesException();
    }
}