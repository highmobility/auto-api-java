// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Start stop defogging
 */
public class StartStopDefogging extends SetCommand {
    Property<ActiveState> defoggingState = new Property(ActiveState.class, 0x06);

    /**
     * @return The defogging state
     */
    public Property<ActiveState> getDefoggingState() {
        return defoggingState;
    }
    
    /**
     * Start stop defogging
     *
     * @param defoggingState The defogging state
     */
    public StartStopDefogging(ActiveState defoggingState) {
        super(Identifier.CLIMATE);
    
        addProperty(this.defoggingState.update(defoggingState), true);
    }

    StartStopDefogging(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x06: return defoggingState.update(p);
                }
                return null;
            });
        }
        if (this.defoggingState.getValue() == null) 
            throw new NoPropertiesException();
    }
}