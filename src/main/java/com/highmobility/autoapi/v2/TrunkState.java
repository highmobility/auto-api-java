// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.LockState;
import com.highmobility.autoapi.v2.value.Position;

public class TrunkState extends Command {
    Property<LockState> lock = new Property(LockState.class, 0x01);
    Property<Position> position = new Property(Position.class, 0x02);

    /**
     * @return The lock
     */
    public Property<LockState> getLock() {
        return lock;
    }

    /**
     * @return The position
     */
    public Property<Position> getPosition() {
        return position;
    }

    TrunkState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return lock.update(p);
                    case 0x02: return position.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private TrunkState(Builder builder) {
        super(builder);

        lock = builder.lock;
        position = builder.position;
    }

    public static final class Builder extends Command.Builder {
        private Property<LockState> lock;
        private Property<Position> position;

        public Builder() {
            super(Identifier.TRUNK);
        }

        public TrunkState build() {
            return new TrunkState(this);
        }

        /**
         * @param lock The lock
         * @return The builder
         */
        public Builder setLock(Property<LockState> lock) {
            this.lock = lock.setIdentifier(0x01);
            addProperty(lock);
            return this;
        }
        
        /**
         * @param position The position
         * @return The builder
         */
        public Builder setPosition(Property<Position> position) {
            this.position = position.setIdentifier(0x02);
            addProperty(position);
            return this;
        }
    }
}