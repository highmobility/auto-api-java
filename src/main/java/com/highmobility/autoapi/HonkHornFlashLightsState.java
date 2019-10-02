// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;

public class HonkHornFlashLightsState extends SetCommand {
    Property<Flashers> flashers = new Property(Flashers.class, 0x01);

    /**
     * @return The flashers
     */
    public Property<Flashers> getFlashers() {
        return flashers;
    }

    HonkHornFlashLightsState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return flashers.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private HonkHornFlashLightsState(Builder builder) {
        super(builder);

        flashers = builder.flashers;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Flashers> flashers;

        public Builder() {
            super(Identifier.HONK_HORN_FLASH_LIGHTS);
        }

        public HonkHornFlashLightsState build() {
            return new HonkHornFlashLightsState(this);
        }

        /**
         * @param flashers The flashers
         * @return The builder
         */
        public Builder setFlashers(Property<Flashers> flashers) {
            this.flashers = flashers.setIdentifier(0x01);
            addProperty(this.flashers);
            return this;
        }
    }

    public enum Flashers implements ByteEnum {
        INACTIVE((byte) 0x00),
        EMERGENCY_FLASHER_ACTIVE((byte) 0x01),
        LEFT_FLASHER_ACTIVE((byte) 0x02),
        RIGHT_FLASHER_ACTIVE((byte) 0x03);
    
        public static Flashers fromByte(byte byteValue) throws CommandParseException {
            Flashers[] values = Flashers.values();
    
            for (int i = 0; i < values.length; i++) {
                Flashers state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Flashers(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}