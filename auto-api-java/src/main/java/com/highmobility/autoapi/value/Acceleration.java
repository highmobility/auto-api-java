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
import com.highmobility.autoapi.value.measurement.AccelerationUnit;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

public class Acceleration extends PropertyValueObject {
    public static final int SIZE = 11;

    Direction direction;
    AccelerationUnit acceleration;

    /**
     * @return The direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return The accelaration.
     */
    public AccelerationUnit getAcceleration() {
        return acceleration;
    }

    public Acceleration(Direction direction, AccelerationUnit acceleration) {
        super(0);

        this.direction = direction;
        this.acceleration = acceleration;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, direction.getByte());
        bytePosition += 1;

        set(bytePosition, acceleration);
    }

    public Acceleration(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 11) throw new CommandParseException();

        int bytePosition = 0;
        direction = Direction.fromByte(get(bytePosition));
        bytePosition += 1;

        int accelerationSize = AccelerationUnit.SIZE;
        acceleration = new AccelerationUnit(getRange(bytePosition, bytePosition + accelerationSize));
    }

    @Override public int getLength() {
        return 1 + 10;
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(Direction.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Direction(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}