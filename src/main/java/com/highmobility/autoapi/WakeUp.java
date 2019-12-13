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

import com.highmobility.autoapi.capability.DisabledIn;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * The Wake Up capability
 */
public class WakeUp {
    public static final int IDENTIFIER = Identifier.WAKE_UP;

    public static final byte PROPERTY_STATUS = 0x01;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.BLE };

    /**
     * Wake up command
     */
    public static class WakeUpCommand extends SetCommand {
        Property<Status> status = new Property(Status.class, PROPERTY_STATUS);
    
        /**
         * Wake up command
         */
        public WakeUpCommand() {
            super(IDENTIFIER);
        
            addProperty(status.addValueComponent(new Bytes("00")));
            createBytes();
        }
    
        WakeUpCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: status.update(p);
                    }
                    return null;
                });
            }
            if ((status.getValue() == null || status.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
                throw new NoPropertiesException();
        }
    }

    public enum Status implements ByteEnum {
        WAKE_UP((byte) 0x00),
        SLEEP((byte) 0x01);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}