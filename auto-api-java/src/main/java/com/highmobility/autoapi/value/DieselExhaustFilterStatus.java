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
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class DieselExhaustFilterStatus extends PropertyValueObject {
    public static final int SIZE = 3;

    Status status;
    Component component;
    Cleaning cleaning;

    /**
     * @return The status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return The component.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * @return The cleaning.
     */
    public Cleaning getCleaning() {
        return cleaning;
    }

    public DieselExhaustFilterStatus(Status status, Component component, Cleaning cleaning) {
        super(0);

        this.status = status;
        this.component = component;
        this.cleaning = cleaning;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, status.getByte());
        bytePosition += 1;

        set(bytePosition, component.getByte());
        bytePosition += 1;

        set(bytePosition, cleaning.getByte());
    }

    public DieselExhaustFilterStatus(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        status = Status.fromByte(get(bytePosition));
        bytePosition += 1;

        component = Component.fromByte(get(bytePosition));
        bytePosition += 1;

        cleaning = Cleaning.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1 + 1;
    }

    public enum Status implements ByteEnum {
        UNKNOWN((byte) 0x00),
        NORMAL_OPERATION((byte) 0x01),
        OVERLOADED((byte) 0x02),
        AT_LIMIT((byte) 0x03),
        OVER_LIMIT((byte) 0x04);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("DieselExhaustFilterStatus.Status does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Component implements ByteEnum {
        UNKNOWN((byte) 0x00),
        EXHAUST_FILTER((byte) 0x01),
        DIESEL_PARTICULATE_FILTER((byte) 0x02),
        OVERBOOST_CODE_REGULATOR((byte) 0x03),
        OFF_BOARD_REGENERATION((byte) 0x04);
    
        public static Component fromByte(byte byteValue) throws CommandParseException {
            Component[] values = Component.values();
    
            for (int i = 0; i < values.length; i++) {
                Component state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("DieselExhaustFilterStatus.Component does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Component(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Cleaning implements ByteEnum {
        UNKNOWN((byte) 0x00),
        IN_PROGRESS((byte) 0x01),
        COMPLETE((byte) 0x02),
        INTERRUPTED((byte) 0x03);
    
        public static Cleaning fromByte(byte byteValue) throws CommandParseException {
            Cleaning[] values = Cleaning.values();
    
            for (int i = 0; i < values.length; i++) {
                Cleaning state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("DieselExhaustFilterStatus.Cleaning does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Cleaning(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}