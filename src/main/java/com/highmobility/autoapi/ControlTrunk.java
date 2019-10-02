// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import javax.annotation.Nullable;

/**
 * Control trunk
 */
public class ControlTrunk extends SetCommand {
    @Nullable Property<LockState> lock = new Property(LockState.class, 0x01);
    @Nullable Property<Position> position = new Property(Position.class, 0x02);

    /**
     * @return The lock
     */
    public @Nullable Property<LockState> getLock() {
        return lock;
    }
    
    /**
     * @return The position
     */
    public @Nullable Property<Position> getPosition() {
        return position;
    }
    
    /**
     * Control trunk
     *
     * @param lock The lock
     * @param position The position
     */
    public ControlTrunk(@Nullable LockState lock, @Nullable Position position) {
        super(Identifier.TRUNK);
    
        addProperty(this.lock.update(lock));
        addProperty(this.position.update(position), true);
        if (this.lock.getValue() == null && this.position.getValue() == null) throw new IllegalArgumentException();
    }

    ControlTrunk(byte[] bytes) throws CommandParseException, NoPropertiesException {
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
        if (this.lock.getValue() == null && this.position.getValue() == null) throw new NoPropertiesException();
    }
}