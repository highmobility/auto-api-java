// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;

/**
 * Honk flash
 */
public class HonkFlash extends SetCommand {
    @Nullable PropertyInteger honkSeconds = new PropertyInteger(0x02, false);
    @Nullable PropertyInteger flashTimes = new PropertyInteger(0x03, false);

    /**
     * @return The honk seconds
     */
    public @Nullable PropertyInteger getHonkSeconds() {
        return honkSeconds;
    }
    
    /**
     * @return The flash times
     */
    public @Nullable PropertyInteger getFlashTimes() {
        return flashTimes;
    }
    
    /**
     * Honk flash
     *
     * @param honkSeconds The Number of seconds to honk the horn
     * @param flashTimes The Number of times to flash the lights
     */
    public HonkFlash(@Nullable Integer honkSeconds, @Nullable Integer flashTimes) {
        super(Identifier.HONK_HORN_FLASH_LIGHTS);
    
        addProperty(this.honkSeconds.update(false, 1, honkSeconds));
        addProperty(this.flashTimes.update(false, 1, flashTimes), true);
        if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new IllegalArgumentException();
    }

    HonkFlash(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return honkSeconds.update(p);
                    case 0x03: return flashTimes.update(p);
                }
                return null;
            });
        }
        if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new NoPropertiesException();
    }
}