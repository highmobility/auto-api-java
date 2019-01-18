package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyTimestamp;

import java.util.Calendar;

import javax.annotation.Nullable;

public class PlugType extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public PlugType(Value value) {
        this((byte) 0x00, value);
    }

    public PlugType(@Nullable Value value, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public PlugType(byte identifier, Value value) {
        super(identifier, value == null ? 0 : 1);
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public PlugType(byte identifier) {
        super(identifier);
    }

    public PlugType(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) value = value.fromByte(p.get(3));
        return super.update(p, failure, timestamp, propertyInArray);
    }

    public enum Value {
        TYPE_1((byte) 0x00),
        TYPE_2((byte) 0x01),
        COMBINED_CHARGING_SYSTEM((byte) 0x02),
        CHA_DE_MO((byte) 0x03);

        public static Value fromByte(byte byteValue) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
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
    }
}