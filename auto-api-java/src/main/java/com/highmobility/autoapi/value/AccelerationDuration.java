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
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

public class AccelerationDuration extends PropertyValueObject {
    public static final int SIZE = 12;

    Direction direction;
    Type type;
    Duration duration;

    /**
     * @return The direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return The type.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The duration of the acceleration.
     */
    public Duration getDuration() {
        return duration;
    }

    public AccelerationDuration(Direction direction, Type type, Duration duration) {
        super(0);

        this.direction = direction;
        this.type = type;
        this.duration = duration;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, direction.getByte());
        bytePosition += 1;

        set(bytePosition, type.getByte());
        bytePosition += 1;

        set(bytePosition, duration);
    }

    public AccelerationDuration(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 12) throw new CommandParseException();

        int bytePosition = 0;
        direction = Direction.fromByte(get(bytePosition));
        bytePosition += 1;

        type = Type.fromByte(get(bytePosition));
        bytePosition += 1;

        int durationSize = Duration.SIZE;
        duration = new Duration(getRange(bytePosition, bytePosition + durationSize));
    }

    @Override public int getLength() {
        return 1 + 1 + 10;
    }

    public enum Direction implements ByteEnum {
        LONGITUDINAL((byte) 0x00),
        LATERAL((byte) 0x01);
    
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

    public enum Type implements ByteEnum {
        REGULAR((byte) 0x00),
        POSITIVE_OUTLIER((byte) 0x01),
        NEGATIVE_OUTLIER((byte) 0x02);
    
        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();
    
            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Type.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Type(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}