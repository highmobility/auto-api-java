// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.property.PropertyInteger;

public class CruiseControlState extends SetCommand {
    Property<ActiveState> cruiseControl = new Property(ActiveState.class, 0x01);
    Property<Limiter> limiter = new Property(Limiter.class, 0x02);
    PropertyInteger targetSpeed = new PropertyInteger(0x03, true);
    Property<ActiveState> adaptiveCruiseControl = new Property(ActiveState.class, 0x04);
    PropertyInteger accTargetSpeed = new PropertyInteger(0x05, true);

    /**
     * @return The cruise control
     */
    public Property<ActiveState> getCruiseControl() {
        return cruiseControl;
    }

    /**
     * @return The limiter
     */
    public Property<Limiter> getLimiter() {
        return limiter;
    }

    /**
     * @return The target speed in km/h
     */
    public PropertyInteger getTargetSpeed() {
        return targetSpeed;
    }

    /**
     * @return The adaptive cruise control
     */
    public Property<ActiveState> getAdaptiveCruiseControl() {
        return adaptiveCruiseControl;
    }

    /**
     * @return The target speed in km/h of the Adaptive Cruise Control
     */
    public PropertyInteger getAccTargetSpeed() {
        return accTargetSpeed;
    }

    CruiseControlState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return cruiseControl.update(p);
                    case 0x02: return limiter.update(p);
                    case 0x03: return targetSpeed.update(p);
                    case 0x04: return adaptiveCruiseControl.update(p);
                    case 0x05: return accTargetSpeed.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum Limiter implements ByteEnum {
        NOT_SET((byte) 0x00),
        HIGHER_SPEED_REQUESTED((byte) 0x01),
        LOWER_SPEED_REQUESTED((byte) 0x02),
        SPEED_FIXED((byte) 0x03);
    
        public static Limiter fromByte(byte byteValue) throws CommandParseException {
            Limiter[] values = Limiter.values();
    
            for (int i = 0; i < values.length; i++) {
                Limiter state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Limiter(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}