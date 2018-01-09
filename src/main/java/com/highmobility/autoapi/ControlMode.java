package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 *
 * This is an evented command that is sent from the car every time the remote control mode changes.
 * It is also sent when a Get Control Mode is received by the car. The new mode is
 * included in the command and may be the result of both user or car triggered action.
 */
public class ControlMode extends Command {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x01);

    /**
     * The possible control modes
     */
    public enum Mode {
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
    }

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
}