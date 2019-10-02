// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Start stop ionising
 */
public class StartStopIonising extends SetCommand {
    Property<ActiveState> ionisingState = new Property(ActiveState.class, 0x08);

    /**
     * @return The ionising state
     */
    public Property<ActiveState> getIonisingState() {
        return ionisingState;
    }
    
    /**
     * Start stop ionising
     *
     * @param ionisingState The ionising state
     */
    public StartStopIonising(ActiveState ionisingState) {
        super(Identifier.CLIMATE);
    
        addProperty(this.ionisingState.update(ionisingState), true);
    }

    StartStopIonising(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x08: return ionisingState.update(p);
                }
                return null;
            });
        }
        if (this.ionisingState.getValue() == null) 
            throw new NoPropertiesException();
    }
}