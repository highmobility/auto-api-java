// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

public class DriverFatigueState extends Command {
    Property<DetectedFatigueLevel> detectedFatigueLevel = new Property(DetectedFatigueLevel.class, 0x01);

    /**
     * @return The detected fatigue level
     */
    public Property<DetectedFatigueLevel> getDetectedFatigueLevel() {
        return detectedFatigueLevel;
    }

    DriverFatigueState(byte[] bytes) {
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

    public enum DetectedFatigueLevel {
        LIGHT((byte)0x00),
        PAUSE_RECOMMENDED((byte)0x01),
        ACTION_NEEDED((byte)0x02),
        CAR_READY_TO_TAKE_OVER((byte)0x03);
    
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
    
        public byte getByte() {
            return value;
        }
    }
}