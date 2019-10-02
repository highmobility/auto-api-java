// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.WindscreenState.Wipers;
import com.highmobility.autoapi.WindscreenState.WipersIntensity;
import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Control wipers
 */
public class ControlWipers extends SetCommand {
    Property<Wipers> wipers = new Property(Wipers.class, 0x01);
    @Nullable Property<WipersIntensity> wipersIntensity = new Property(WipersIntensity.class, 0x02);

    /**
     * @return The wipers
     */
    public Property<Wipers> getWipers() {
        return wipers;
    }
    
    /**
     * @return The wipers intensity
     */
    public @Nullable Property<WipersIntensity> getWipersIntensity() {
        return wipersIntensity;
    }
    
    /**
     * Control wipers
     *
     * @param wipers The wipers
     * @param wipersIntensity The wipers intensity
     */
    public ControlWipers(Wipers wipers, @Nullable WipersIntensity wipersIntensity) {
        super(Identifier.WINDSCREEN);
    
        addProperty(this.wipers.update(wipers));
        addProperty(this.wipersIntensity.update(wipersIntensity), true);
    }

    ControlWipers(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return wipers.update(p);
                    case 0x02: return wipersIntensity.update(p);
                }
                return null;
            });
        }
        if (this.wipers.getValue() == null) 
            throw new NoPropertiesException();
    }
}