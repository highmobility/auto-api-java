// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;

public class KeyfobPositionState extends SetCommand {
    Property<Location> location = new Property(Location.class, 0x01);

    /**
     * @return The location
     */
    public Property<Location> getLocation() {
        return location;
    }

    KeyfobPositionState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return location.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum Location implements ByteEnum {
        OUT_OF_RANGE((byte) 0x00),
        OUTSIDE_DRIVER_SIDE((byte) 0x01),
        OUTSIDE_IN_FRONT_OF_CAR((byte) 0x02),
        OUTSIDE_PASSENGER_SIDE((byte) 0x03),
        OUTSIDE_BEHIND_CAR((byte) 0x04),
        INSIDE_CAR((byte) 0x05);
    
        public static Location fromByte(byte byteValue) throws CommandParseException {
            Location[] values = Location.values();
    
            for (int i = 0; i < values.length; i++) {
                Location state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Location(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}