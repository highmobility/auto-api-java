// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * Start stop hvac
 */
public class StartStopHvac extends SetCommand {
    Property<ActiveState> hvacState = new Property(ActiveState.class, 0x05);

    /**
     * @return The hvac state
     */
    public Property<ActiveState> getHvacState() {
        return hvacState;
    }
    
    /**
     * Start stop hvac
     *
     * @param hvacState The hvac state
     */
    public StartStopHvac(ActiveState hvacState) {
        super(Identifier.CLIMATE);
    
        addProperty(this.hvacState.update(hvacState), true);
    }

    StartStopHvac(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x05: return hvacState.update(p);
                }
                return null;
            });
        }
        if (this.hvacState.getValue() == null) 
            throw new NoPropertiesException();
    }
}