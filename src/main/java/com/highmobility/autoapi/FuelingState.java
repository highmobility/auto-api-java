// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;

public class FuelingState extends SetCommand {
    Property<LockState> gasFlapLock = new Property(LockState.class, 0x02);
    Property<Position> gasFlapPosition = new Property(Position.class, 0x03);

    /**
     * @return The gas flap lock
     */
    public Property<LockState> getGasFlapLock() {
        return gasFlapLock;
    }

    /**
     * @return The gas flap position
     */
    public Property<Position> getGasFlapPosition() {
        return gasFlapPosition;
    }

    FuelingState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return gasFlapLock.update(p);
                    case 0x03: return gasFlapPosition.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private FuelingState(Builder builder) {
        super(builder);

        gasFlapLock = builder.gasFlapLock;
        gasFlapPosition = builder.gasFlapPosition;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<LockState> gasFlapLock;
        private Property<Position> gasFlapPosition;

        public Builder() {
            super(Identifier.FUELING);
        }

        public FuelingState build() {
            return new FuelingState(this);
        }

        /**
         * @param gasFlapLock The gas flap lock
         * @return The builder
         */
        public Builder setGasFlapLock(Property<LockState> gasFlapLock) {
            this.gasFlapLock = gasFlapLock.setIdentifier(0x02);
            addProperty(this.gasFlapLock);
            return this;
        }
        
        /**
         * @param gasFlapPosition The gas flap position
         * @return The builder
         */
        public Builder setGasFlapPosition(Property<Position> gasFlapPosition) {
            this.gasFlapPosition = gasFlapPosition.setIdentifier(0x03);
            addProperty(this.gasFlapPosition);
            return this;
        }
    }
}