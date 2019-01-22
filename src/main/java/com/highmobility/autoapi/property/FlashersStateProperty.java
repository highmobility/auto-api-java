package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

import java.util.Calendar;

import javax.annotation.Nullable;

public class FlashersStateProperty extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public FlashersStateProperty(byte identifier) {
        super(identifier);
    }

    public FlashersStateProperty(@Nullable Value value, @Nullable Calendar timestamp,
                                 @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public FlashersStateProperty(Value value) {
        this((byte) 0x00, value);
    }

    public FlashersStateProperty(byte identifier, Value value) {
        super(identifier, value == null ? 0 : value.getLength());
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public FlashersStateProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() == 1) value = Value.fromByte(p.getValueByte());
        return this;
    }

    public enum Value implements PropertyValue {
        INACTIVE((byte) 0x00),
        EMERGENCY_ACTIVE((byte) 0x01),
        LEFT_ACTIVE((byte) 0x02),
        RIGHT_ACTIVE((byte) 0x03);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] allValues = Value.values();

            for (int i = 0; i < allValues.length; i++) {
                Value value1 = allValues[i];
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