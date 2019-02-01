package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.lights.LightLocation;
import com.highmobility.value.Bytes;

import java.util.Calendar;

import javax.annotation.Nullable;

public class FogLight extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public FogLight(byte identifier) {
        super(identifier);
    }

    public FogLight(@Nullable Value value, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        super(value, timestamp, failure);
        update(value);
    }

    public FogLight(LightLocation location, boolean active) {
        this(new Value(location, active));
    }

    public FogLight(Value value) {
        super(value);
        update(value);
    }

    public FogLight(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        ignoreInvalidByteSizeException(() -> value = new Value(p.getValueBytes()));
        return this;
    }

    public FogLight update(Value value) {
        super.update(value);
        this.value = value;
        return this;
    }

    public static class Value extends PropertyValueObject {
        LightLocation location;
        boolean active;

        /**
         * @return The light location.
         */
        public LightLocation getLocation() {
            return location;
        }

        /**
         * @return The light state.
         */
        public boolean isActive() {
            return active;
        }

        private Value(Bytes bytes) throws CommandParseException {
            super(bytes);

            if (bytes.getLength() >= 2) {
                location = LightLocation.fromByte(bytes.get(0));
                active = Property.getBool(bytes.get(1));
            } else {
                throw new IllegalArgumentException();
            }
        }

        private Value(LightLocation location, boolean active) {
            this.location = location;
            this.active = active;

            byte[] bytes = new byte[2];
            bytes[0] = location.getByte();
            bytes[1] = Property.boolToByte(active);

            this.bytes = new Bytes(bytes);
        }
    }
}
