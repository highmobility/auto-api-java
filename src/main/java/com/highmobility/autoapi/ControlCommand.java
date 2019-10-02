// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;

/**
 * Control command
 */
public class ControlCommand extends SetCommand {
    @Nullable PropertyInteger angle = new PropertyInteger(0x02, true);
    @Nullable PropertyInteger speed = new PropertyInteger(0x03, true);

    /**
     * @return The angle
     */
    public @Nullable PropertyInteger getAngle() {
        return angle;
    }
    
    /**
     * @return The speed
     */
    public @Nullable PropertyInteger getSpeed() {
        return speed;
    }
    
    /**
     * Control command
     *
     * @param angle The Wheel base angle in degrees
     * @param speed The Speed in km/h
     */
    public ControlCommand(@Nullable Integer angle, @Nullable Integer speed) {
        super(Identifier.REMOTE_CONTROL);
    
        addProperty(this.angle.update(true, 2, angle));
        addProperty(this.speed.update(true, 1, speed), true);
        if (this.angle.getValue() == null && this.speed.getValue() == null) throw new IllegalArgumentException();
    }

    ControlCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return angle.update(p);
                    case 0x03: return speed.update(p);
                }
                return null;
            });
        }
        if (this.angle.getValue() == null && this.speed.getValue() == null) throw new NoPropertiesException();
    }
}