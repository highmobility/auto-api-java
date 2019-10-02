// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;

/**
 * Lock unlock doors
 */
public class LockUnlockDoors extends SetCommand {
    Property<LockState> insideLocksState = new Property(LockState.class, 0x05);

    /**
     * @return The inside locks state
     */
    public Property<LockState> getInsideLocksState() {
        return insideLocksState;
    }
    
    /**
     * Lock unlock doors
     *
     * @param insideLocksState The Inside locks state for the whole car (combines all specific lock states if available)
     */
    public LockUnlockDoors(LockState insideLocksState) {
        super(Identifier.DOORS);
    
        addProperty(this.insideLocksState.update(insideLocksState), true);
    }

    LockUnlockDoors(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x05: return insideLocksState.update(p);
                }
                return null;
            });
        }
        if (this.insideLocksState.getValue() == null) 
            throw new NoPropertiesException();
    }
}