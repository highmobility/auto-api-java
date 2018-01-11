package com.highmobility.autoapi;

import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.Property;

/**
 *
 * This is an evented command that is sent from the car every time the remote control mode changes.
 * It is also sent when a Get Control ControlMode is received by the car. The new mode is
 * included in the command and may be the result of both user or car triggered action.
 */
public class ControlMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x01);

    com.highmobility.autoapi.property.ControlMode mode;
    int angle;

    /**
     *
     * @return the angle
     */
    public int getAngle() {
        return angle;
    }

    /**
     *
     * @return the control mode
     */
    public com.highmobility.autoapi.property.ControlMode getMode() {
        return mode;
    }

    public ControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];

            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    mode = com.highmobility.autoapi.property.ControlMode.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    angle = Property.getUnsignedInt(property.getValueBytes());
                    break;
            }
        }
    }

    private ControlMode(Builder builder) {
        super(TYPE, builder.getProperties());
        angle = builder.angle;
        mode = builder.mode;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private int angle;
        private com.highmobility.autoapi.property.ControlMode mode;

        public Builder() {
            super(TYPE);
        }

        public Builder setAngle(int angle) {
            this.angle = angle;
            addProperty(new IntProperty((byte) 0x01, angle, 2));
            return this;
        }

        public Builder setMode(com.highmobility.autoapi.property.ControlMode mode) {
            this.mode = mode;
            addProperty(mode);
            return this;
        }

        public ControlMode build() {
            return new ControlMode(this);
        }
    }
}