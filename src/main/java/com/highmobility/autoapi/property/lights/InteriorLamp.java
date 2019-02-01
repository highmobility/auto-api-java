package com.highmobility.autoapi.property.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValue;

import java.util.Calendar;

import javax.annotation.Nullable;

public class InteriorLamp extends Property {

    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public InteriorLamp(byte identifier) {
        super(identifier);
    }

    public InteriorLamp(@Nullable Value value, @Nullable Calendar timestamp,
                        @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public InteriorLamp(Value value) {
        super(value);

        this.value = value;

        if (value != null) {
            bytes[3] = value.location.getByte();
            bytes[4] = Property.boolToByte(value.active);
        }
    }

    public InteriorLamp(LightLocation location, boolean active) {
        this(new Value(location, active));
    }

    public InteriorLamp(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 2) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
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

        private Value(Property bytes) throws CommandParseException {
            location = LightLocation.fromByte(bytes.get(3));
            active = Property.getBool(bytes.get(4));
        }

        private Value(LightLocation location, boolean active) {
            this.location = location;
            this.active = active;
        }

        @Override public int getLength() {
            return 2;
        }
    }
}
