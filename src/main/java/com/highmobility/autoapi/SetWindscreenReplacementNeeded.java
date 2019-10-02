// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.WindscreenState.WindscreenNeedsReplacement;
import com.highmobility.autoapi.property.Property;

/**
 * Set windscreen replacement needed
 */
public class SetWindscreenReplacementNeeded extends SetCommand {
    Property<WindscreenNeedsReplacement> windscreenNeedsReplacement = new Property(WindscreenNeedsReplacement.class, 0x06);

    /**
     * @return The windscreen needs replacement
     */
    public Property<WindscreenNeedsReplacement> getWindscreenNeedsReplacement() {
        return windscreenNeedsReplacement;
    }
    
    /**
     * Set windscreen replacement needed
     *
     * @param windscreenNeedsReplacement The windscreen needs replacement
     */
    public SetWindscreenReplacementNeeded(WindscreenNeedsReplacement windscreenNeedsReplacement) {
        super(Identifier.WINDSCREEN);
    
        addProperty(this.windscreenNeedsReplacement.update(windscreenNeedsReplacement), true);
    }

    SetWindscreenReplacementNeeded(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x06: return windscreenNeedsReplacement.update(p);
                }
                return null;
            });
        }
        if (this.windscreenNeedsReplacement.getValue() == null) 
            throw new NoPropertiesException();
    }
}