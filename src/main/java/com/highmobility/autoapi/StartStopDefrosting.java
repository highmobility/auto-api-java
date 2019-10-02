// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Start stop defrosting
 */
public class StartStopDefrosting extends SetCommand {
    Property<ActiveState> defrostingState = new Property(ActiveState.class, 0x07);

    /**
     * @return The defrosting state
     */
    public Property<ActiveState> getDefrostingState() {
        return defrostingState;
    }
    
    /**
     * Start stop defrosting
     *
     * @param defrostingState The defrosting state
     */
    public StartStopDefrosting(ActiveState defrostingState) {
        super(Identifier.CLIMATE);
    
        addProperty(this.defrostingState.update(defrostingState), true);
    }

    StartStopDefrosting(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x07: return defrostingState.update(p);
                }
                return null;
            });
        }
        if (this.defrostingState.getValue() == null) 
            throw new NoPropertiesException();
    }
}