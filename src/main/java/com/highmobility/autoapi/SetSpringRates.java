// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.SpringRate;
import java.util.ArrayList;

/**
 * Set spring rates
 */
public class SetSpringRates extends SetCommand {
    Property<SpringRate>[] currentSpringRates;

    /**
     * @return The current spring rates
     */
    public Property<SpringRate>[] getCurrentSpringRates() {
        return currentSpringRates;
    }
    
    /**
     * Set spring rates
     *
     * @param currentSpringRates The The current values for the spring rates
     */
    public SetSpringRates(SpringRate[] currentSpringRates) {
        super(Identifier.CHASSIS_SETTINGS);
    
        ArrayList<Property> currentSpringRatesBuilder = new ArrayList<>();
        if (currentSpringRates != null) {
            for (SpringRate currentSpringRate : currentSpringRates) {
                Property prop = new Property(0x05, currentSpringRate);
                currentSpringRatesBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
    }

    SetSpringRates(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<SpringRate>> currentSpringRatesBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x05: {
                        Property currentSpringRate = new Property(SpringRate.class, p);
                        currentSpringRatesBuilder.add(currentSpringRate);
                        return currentSpringRate;
                    }
                }
                return null;
            });
        }
    
        currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
        if (this.currentSpringRates.length == 0) 
            throw new NoPropertiesException();
    }
}