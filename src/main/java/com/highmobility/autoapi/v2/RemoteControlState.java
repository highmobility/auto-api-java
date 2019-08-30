// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyInteger;

public class RemoteControlState extends Command {
    Property<ControlMode> controlMode = new Property(ControlMode.class, 0x01);
    PropertyInteger angle = new PropertyInteger(0x02, true);

    /**
     * @return The control mode
     */
    public Property<ControlMode> getControlMode() {
        return controlMode;
    }

    /**
     * @return Wheel base angle in degrees
     */
    public PropertyInteger getAngle() {
        return angle;
    }

    RemoteControlState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01:
                        return controlMode.update(p);
                    case 0x02:
                        return angle.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private RemoteControlState(Builder builder) {
        super(builder);

        controlMode = builder.controlMode;
        angle = builder.angle;
    }

    public static final class Builder extends Command.Builder {
        private Property<ControlMode> controlMode;
        private PropertyInteger angle;

        public Builder() {
            super(Identifier.REMOTE_CONTROL);
        }

        public RemoteControlState build() {
            return new RemoteControlState(this);
        }

        /**
         * @param controlMode The control mode
         * @return The builder
         */
        public Builder setControlMode(Property<ControlMode> controlMode) {
            this.controlMode = controlMode.setIdentifier(0x01);
            addProperty(controlMode);
            return this;
        }

        /**
         * @param angle Wheel base angle in degrees
         * @return The builder
         */
        public Builder setAngle(PropertyInteger angle) {
            this.angle = new PropertyInteger(0x02, true, 2, angle);
            addProperty(angle);
            return this;
        }
    }

    public enum ControlMode {
        UNAVAILABLE((byte) 0x00),
        AVAILABLE((byte) 0x01),
        STARTED((byte) 0x02),
        FAILED_TO_START((byte) 0x03),
        ABORTED((byte) 0x04),
        ENDED((byte) 0x05);

        public static ControlMode fromByte(byte byteValue) throws CommandParseException {
            ControlMode[] values = ControlMode.values();

            for (int i = 0; i < values.length; i++) {
                ControlMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        ControlMode(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}