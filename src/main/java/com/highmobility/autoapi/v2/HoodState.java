// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

public class HoodState extends Command {
    Property<Position> position = new Property(Position.class, 0x01);

    /**
     * @return The position
     */
    public Property<Position> getPosition() {
        return position;
    }

    HoodState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return position.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private HoodState(Builder builder) {
        super(builder);

        position = builder.position;
    }

    public static final class Builder extends Command.Builder {
        private Property<Position> position;

        public Builder() {
            super(Identifier.HOOD);
        }

        public HoodState build() {
            return new HoodState(this);
        }

        /**
         * @param position The position
         * @return The builder
         */
        public Builder setPosition(Property<Position> position) {
            this.position = position.setIdentifier(0x01);
            addProperty(position);
            return this;
        }
    }

    public enum Position {
        CLOSED((byte)0x00),
        OPEN((byte)0x01),
        INTERMEDIATE((byte)0x02);
    
        public static Position fromByte(byte byteValue) throws CommandParseException {
            Position[] values = Position.values();
    
            for (int i = 0; i < values.length; i++) {
                Position state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Position(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}