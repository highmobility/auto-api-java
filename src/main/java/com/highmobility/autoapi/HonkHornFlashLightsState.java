/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;

/**
 * The honk horn flash lights state
 */
public class HonkHornFlashLightsState extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.HONK_HORN_FLASH_LIGHTS;

    public static final byte IDENTIFIER_FLASHERS = 0x01;

    Property<Flashers> flashers = new Property(Flashers.class, IDENTIFIER_FLASHERS);

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
                    case IDENTIFIER_FLASHERS: return flashers.update(p);
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
            super(IDENTIFIER);
        }

        public HonkHornFlashLightsState build() {
            return new HonkHornFlashLightsState(this);
        }

        /**
         * @param flashers The flashers
         * @return The builder
         */
        public Builder setFlashers(Property<Flashers> flashers) {
            this.flashers = flashers.setIdentifier(IDENTIFIER_FLASHERS);
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