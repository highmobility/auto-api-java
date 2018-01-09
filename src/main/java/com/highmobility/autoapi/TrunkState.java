package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This is an evented message that is sent from the car every time the trunk state changes. This
 * message is also sent when a Get Trunk State is received by the car. The new status is included in
 * the message payload and may be the result of user, device or car triggered action.
 */
public class TrunkState extends Command {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x01);

    /**
     * The possible trunk lock states
     */
    public enum LockState {
        UNLOCKED((byte) 0x00),
        LOCKED((byte) 0x01);

        public static LockState fromByte(byte value) throws CommandParseException {
            LockState[] allValues = LockState.values();

            for (int i = 0; i < allValues.length; i++) {
                LockState value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        LockState(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        public byte[] getPropertyBytes() {
            return new byte[] { 0x01, 0x00, 0x01, value};
        }
    }

    /**
     * The possible trunk positions
     */
    public enum Position {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] allValues = Position.values();

            for (int i = 0; i < allValues.length; i++) {
                Position value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Position(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        public byte[] getPropertyBytes() {
            return new byte[] { 0x02, 0x00, 0x01, value};
        }
    }

    /**
     * @return the current lock status of the trunk
     */
    public LockState getLockState() {
        return lockState;
    }

    /**
     * @return the current position of the trunk
     */
    public Position getPosition() {
        return position;
    }

    LockState lockState;
    Position position;

    public TrunkState(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    lockState = LockState.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    position = Position.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}