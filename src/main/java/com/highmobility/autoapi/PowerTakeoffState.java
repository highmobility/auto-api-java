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
import com.highmobility.autoapi.value.ActiveState;

/**
 * The power takeoff state
 */
public class PowerTakeoffState extends SetCommand {
    public static final Identifier identifier = Identifier.POWER_TAKEOFF;

    Property<ActiveState> status = new Property(ActiveState.class, 0x01);
    Property<Engaged> engaged = new Property(Engaged.class, 0x02);

    /**
     * @return The status
     */
    public Property<ActiveState> getStatus() {
        return status;
    }

    /**
     * @return The engaged
     */
    public Property<Engaged> getEngaged() {
        return engaged;
    }

    PowerTakeoffState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                    case 0x02: return engaged.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private PowerTakeoffState(Builder builder) {
        super(builder);

        status = builder.status;
        engaged = builder.engaged;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<ActiveState> status;
        private Property<Engaged> engaged;

        public Builder() {
            super(identifier);
        }

        public PowerTakeoffState build() {
            return new PowerTakeoffState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<ActiveState> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(this.status);
            return this;
        }
        
        /**
         * @param engaged The engaged
         * @return The builder
         */
        public Builder setEngaged(Property<Engaged> engaged) {
            this.engaged = engaged.setIdentifier(0x02);
            addProperty(this.engaged);
            return this;
        }
    }

    public enum Engaged implements ByteEnum {
        NOT_ENGAGED((byte) 0x00),
        ENGAGED((byte) 0x01);
    
        public static Engaged fromByte(byte byteValue) throws CommandParseException {
            Engaged[] values = Engaged.values();
    
            for (int i = 0; i < values.length; i++) {
                Engaged state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Engaged(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}