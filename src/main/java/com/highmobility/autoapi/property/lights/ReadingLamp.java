package com.highmobility.autoapi.property.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValue;
import com.highmobility.autoapi.property.value.Location;

import java.util.Calendar;

import javax.annotation.Nullable;

public class ReadingLamp extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public ReadingLamp(byte identifier) {
        super(identifier);
    }

    public ReadingLamp(@Nullable Value value, @Nullable Calendar timestamp,
                       @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public ReadingLamp(Value value) {
        super(value);

        this.value = value;

        if (value != null) {
            bytes[3] = value.location.getByte();
            bytes[4] = Property.boolToByte(value.active);
        }
    }

    public ReadingLamp(Location location, boolean active) {
        this(new Value(location, active));
    }

    public ReadingLamp(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 2) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
        Location location;
        boolean active;

        /**
         * @return The light location.
         */
        public Location getLocation() {
            return location;
        }

        /**
         * @return The light state.
         */
        public boolean isActive() {
            return active;
        }

        private Value(Property bytes) throws CommandParseException {
            if (bytes.getValueLength() >= 2) {
                location = Location.fromByte(bytes.get(3));
                active = Property.getBool(bytes.get(4));
            } else {
                throw new IllegalArgumentException();
            }
        }

        private Value(Location location, boolean active) {
            this.location = location;
            this.active = active;
        }

        @Override public int getLength() {
            return 2;
        }
    }
}
