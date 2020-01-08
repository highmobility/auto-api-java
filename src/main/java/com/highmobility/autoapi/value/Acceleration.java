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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class Acceleration extends PropertyValueObject {
    public static final int SIZE = 5;

    Direction direction;
    Float gForce;

    /**
     * @return The direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return The accelaration in g-force.
     */
    public Float getGForce() {
        return gForce;
    }

    public Acceleration(Direction direction, Float gForce) {
        super(5);
        update(direction, gForce);
    }

    public Acceleration(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public Acceleration() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        direction = Direction.fromByte(get(bytePosition));
        bytePosition += 1;

        gForce = Property.getFloat(bytes, bytePosition);
    }

    public void update(Direction direction, Float gForce) {
        this.direction = direction;
        this.gForce = gForce;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, direction.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(gForce));
    }

    public void update(Acceleration value) {
        update(value.direction, value.gForce);
    }

    @Override public int getLength() {
        return 1 + 4;
    }

    public enum Direction implements ByteEnum {
        LONGITUDINAL((byte) 0x00),
        LATERAL((byte) 0x01),
        FRONT_LATERAL((byte) 0x02),
        REAR_LATERAL((byte) 0x03);
    
        public static Direction fromByte(byte byteValue) throws CommandParseException {
            Direction[] values = Direction.values();
    
            for (int i = 0; i < values.length; i++) {
                Direction state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Direction(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}