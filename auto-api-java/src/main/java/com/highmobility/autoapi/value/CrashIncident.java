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

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

public class CrashIncident extends PropertyValueObject {
    public static final int SIZE = 3;

    Location location;
    Severity severity;
    Repairs repairs;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The severity.
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * @return The repairs.
     */
    public Repairs getRepairs() {
        return repairs;
    }

    public CrashIncident(Location location, Severity severity, Repairs repairs) {
        super(0);

        this.location = location;
        this.severity = severity;
        this.repairs = repairs;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, severity.getByte());
        bytePosition += 1;

        set(bytePosition, repairs.getByte());
    }

    public CrashIncident(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        severity = Severity.fromByte(get(bytePosition));
        bytePosition += 1;

        repairs = Repairs.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1 + 1;
    }

    public enum Location implements ByteEnum {
        FRONT((byte) 0x00),
        LATERAL((byte) 0x01),
        REAR((byte) 0x02);
    
        public static Location fromByte(byte byteValue) throws CommandParseException {
            Location[] values = Location.values();
    
            for (int i = 0; i < values.length; i++) {
                Location state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Location.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Location(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Severity implements ByteEnum {
        VERY_HIGH((byte) 0x00),
        HIGH((byte) 0x01),
        MEDIUM((byte) 0x02),
        LOW((byte) 0x03);
    
        public static Severity fromByte(byte byteValue) throws CommandParseException {
            Severity[] values = Severity.values();
    
            for (int i = 0; i < values.length; i++) {
                Severity state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Severity.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Severity(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Repairs implements ByteEnum {
        UNKNOWN((byte) 0x00),
        NEEDED((byte) 0x01),
        NOT_NEEDED((byte) 0x02);
    
        public static Repairs fromByte(byte byteValue) throws CommandParseException {
            Repairs[] values = Repairs.values();
    
            for (int i = 0; i < values.length; i++) {
                Repairs state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Repairs.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Repairs(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}