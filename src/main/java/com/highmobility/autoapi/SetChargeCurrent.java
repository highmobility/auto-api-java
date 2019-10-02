// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Set charge current
 */
public class SetChargeCurrent extends SetCommand {
    Property<Float> chargeCurrentDC = new Property(Float.class, 0x0e);

    /**
     * @return The charge current dc
     */
    public Property<Float> getChargeCurrentDC() {
        return chargeCurrentDC;
    }
    
    /**
     * Set charge current
     *
     * @param chargeCurrentDC The The charge direct current
     */
    public SetChargeCurrent(Float chargeCurrentDC) {
        super(Identifier.HOME_CHARGER);
    
        addProperty(this.chargeCurrentDC.update(chargeCurrentDC), true);
    }

    SetChargeCurrent(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x0e: return chargeCurrentDC.update(p);
                }
                return null;
            });
        }
        if (this.chargeCurrentDC.getValue() == null) 
            throw new NoPropertiesException();
    }
}