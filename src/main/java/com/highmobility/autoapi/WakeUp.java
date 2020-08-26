/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.capability.DisabledIn;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

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
        Property status = new Property<>(Status.class, PROPERTY_STATUS);
    
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
    
            throw new CommandParseException("Enum Status does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}