// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Set charge limit
 */
public class SetChargeLimit extends SetCommand {
    Property<Double> chargeLimit = new Property(Double.class, 0x08);

    /**
     * @return The charge limit
     */
    public Property<Double> getChargeLimit() {
        return chargeLimit;
    }
    
    /**
     * Set charge limit
     *
     * @param chargeLimit The Charge limit percentage between 0.0-1.0
     */
    public SetChargeLimit(Double chargeLimit) {
        super(Identifier.CHARGING);
    
        addProperty(this.chargeLimit.update(chargeLimit), true);
    }

    SetChargeLimit(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x08: return chargeLimit.update(p);
                }
                return null;
            });
        }
        if (this.chargeLimit.getValue() == null) 
            throw new NoPropertiesException();
    }
}