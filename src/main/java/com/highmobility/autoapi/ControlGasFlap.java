// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import javax.annotation.Nullable;

/**
 * Control gas flap
 */
public class ControlGasFlap extends SetCommand {
    @Nullable Property<LockState> gasFlapLock = new Property(LockState.class, 0x02);
    @Nullable Property<Position> gasFlapPosition = new Property(Position.class, 0x03);

    /**
     * @return The gas flap lock
     */
    public @Nullable Property<LockState> getGasFlapLock() {
        return gasFlapLock;
    }
    
    /**
     * @return The gas flap position
     */
    public @Nullable Property<Position> getGasFlapPosition() {
        return gasFlapPosition;
    }
    
    /**
     * Control gas flap
     *
     * @param gasFlapLock The gas flap lock
     * @param gasFlapPosition The gas flap position
     */
    public ControlGasFlap(@Nullable LockState gasFlapLock, @Nullable Position gasFlapPosition) {
        super(Identifier.FUELING);
    
        addProperty(this.gasFlapLock.update(gasFlapLock));
        addProperty(this.gasFlapPosition.update(gasFlapPosition), true);
        if (this.gasFlapLock.getValue() == null && this.gasFlapPosition.getValue() == null) throw new IllegalArgumentException();
    }

    ControlGasFlap(byte[] bytes) throws CommandParseException, NoPropertiesException {
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
        if (this.gasFlapLock.getValue() == null && this.gasFlapPosition.getValue() == null) throw new NoPropertiesException();
    }
}