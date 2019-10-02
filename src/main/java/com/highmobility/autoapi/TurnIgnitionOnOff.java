// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.OnOffState;

/**
 * Turn ignition on off
 */
public class TurnIgnitionOnOff extends SetCommand {
    Property<OnOffState> status = new Property(OnOffState.class, 0x01);

    /**
     * @return The status
     */
    public Property<OnOffState> getStatus() {
        return status;
    }
    
    /**
     * Turn ignition on off
     *
     * @param status The status
     */
    public TurnIgnitionOnOff(OnOffState status) {
        super(Identifier.IGNITION);
    
        addProperty(this.status.update(status), true);
    }

    TurnIgnitionOnOff(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                }
                return null;
            });
        }
        if (this.status.getValue() == null) 
            throw new NoPropertiesException();
    }
}