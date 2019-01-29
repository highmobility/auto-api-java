package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValue;

import java.util.Calendar;

import javax.annotation.Nullable;

public class Lock extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public Lock(byte identifier) {
        super(identifier);
    }

    public Lock(@Nullable Value value, @Nullable Calendar timestamp,
                @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public Lock(Value value) {
        this((byte) 0x00, value);
    }

    public Lock(byte identifier, Value value) {
        super(identifier, value);
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public Lock(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() == 1) value = Value.fromByte(p.getValueByte());
        return this;
    }

    /**
     * The possible states of the car doorLock.
     */
    public enum Value implements PropertyValue {
        UNLOCKED((byte) 0x00),
        LOCKED((byte) 0x01);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Value(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        @Override public int getLength() {
            return 1;
        }
    }
}