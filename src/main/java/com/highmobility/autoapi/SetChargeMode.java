// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.ChargingState.ChargeMode;
import com.highmobility.autoapi.property.Property;

/**
 * Set charge mode
 */
public class SetChargeMode extends SetCommand {
    Property<ChargeMode> chargeMode = new Property(ChargeMode.class, 0x0c);

    /**
     * @return The charge mode
     */
    public Property<ChargeMode> getChargeMode() {
        return chargeMode;
    }
    
    /**
     * Set charge mode
     *
     * @param chargeMode The charge mode
     */
    public SetChargeMode(ChargeMode chargeMode) {
        super(Identifier.CHARGING);
    
        if (chargeMode == ChargeMode.INDUCTIVE) throw new IllegalArgumentException();
    
        addProperty(this.chargeMode.update(chargeMode), true);
    }

    SetChargeMode(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x0c: return chargeMode.update(p);
                }
                return null;
            });
        }
        if (this.chargeMode.getValue() == null) 
            throw new NoPropertiesException();
    }
}