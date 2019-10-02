// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.WindscreenState.WindscreenDamage;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Zone;
import javax.annotation.Nullable;

/**
 * Set windscreen damage
 */
public class SetWindscreenDamage extends SetCommand {
    Property<WindscreenDamage> windscreenDamage = new Property(WindscreenDamage.class, 0x03);
    @Nullable Property<Zone> windscreenDamageZone = new Property(Zone.class, 0x05);

    /**
     * @return The windscreen damage
     */
    public Property<WindscreenDamage> getWindscreenDamage() {
        return windscreenDamage;
    }
    
    /**
     * @return The windscreen damage zone
     */
    public @Nullable Property<Zone> getWindscreenDamageZone() {
        return windscreenDamageZone;
    }
    
    /**
     * Set windscreen damage
     *
     * @param windscreenDamage The windscreen damage
     * @param windscreenDamageZone The Representing the position in the zone, seen from the inside of the vehicle (1-based index)
     */
    public SetWindscreenDamage(WindscreenDamage windscreenDamage, @Nullable Zone windscreenDamageZone) {
        super(Identifier.WINDSCREEN);
    
        addProperty(this.windscreenDamage.update(windscreenDamage));
        addProperty(this.windscreenDamageZone.update(windscreenDamageZone), true);
    }

    SetWindscreenDamage(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return windscreenDamage.update(p);
                    case 0x05: return windscreenDamageZone.update(p);
                }
                return null;
            });
        }
        if (this.windscreenDamage.getValue() == null) 
            throw new NoPropertiesException();
    }
}