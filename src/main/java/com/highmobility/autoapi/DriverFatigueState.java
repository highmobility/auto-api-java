// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;

public class DriverFatigueState extends SetCommand {
    Property<DetectedFatigueLevel> detectedFatigueLevel = new Property(DetectedFatigueLevel.class, 0x01);

    /**
     * @return The detected fatigue level
     */
    public Property<DetectedFatigueLevel> getDetectedFatigueLevel() {
        return detectedFatigueLevel;
    }

    DriverFatigueState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return detectedFatigueLevel.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum DetectedFatigueLevel implements ByteEnum {
        LIGHT((byte) 0x00),
        PAUSE_RECOMMENDED((byte) 0x01),
        ACTION_NEEDED((byte) 0x02),
        CAR_READY_TO_TAKE_OVER((byte) 0x03);
    
        public static DetectedFatigueLevel fromByte(byte byteValue) throws CommandParseException {
            DetectedFatigueLevel[] values = DetectedFatigueLevel.values();
    
            for (int i = 0; i < values.length; i++) {
                DetectedFatigueLevel state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        DetectedFatigueLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}