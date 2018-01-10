package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.Property;

/**
 *
 * This is an evented command that is sent from the car every time the remote control mode changes.
 * It is also sent when a Get Control Mode is received by the car. The new mode is
 * included in the command and may be the result of both user or car triggered action.
 */
public class ControlMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x01);

    Mode mode;
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
    public Mode getMode() {
        return mode;
    }

    public ControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];

            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    mode = Mode.fromByte(property.getValueByte());
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
        private Mode mode;

        public Builder() {
            super(TYPE);
        }

        public Builder setAngle(int angle) {
            this.angle = angle;
            addProperty(new IntProperty((byte) 0x01, angle, 2));
            return this;
        }

        public Builder setMode(Mode mode) {
            this.mode = mode;
            addProperty(mode);
            return this;
        }

        public ControlMode build() {
            return new ControlMode(this);
        }
    }

    /**
     * The possible control modes
     */
    public enum Mode implements HMProperty {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        STARTED((byte)0x02),
        FAILED_TO_START((byte)0x03),
        ABORTED((byte)0x04),
        ENDED((byte)0x05),
        UNSUPPORTED((byte)0xFF);

        public static Mode fromByte(byte value) throws CommandParseException {
            Mode[] allValues = Mode.values();

            for (int i = 0; i < allValues.length; i++) {
                Mode value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Mode(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        @Override public byte getPropertyIdentifier() {
            return 0x01;
        }

        @Override public int getPropertyLength() {
            return 1;
        }

        @Override public byte[] getPropertyBytes() {
            return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
        }
    }
}