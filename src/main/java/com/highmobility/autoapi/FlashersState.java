package com.highmobility.autoapi;

/**
 * This message is sent when a Get Flashers State message is received by the car.
 */
public class FlashersState extends Command {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x01);

    public enum State {
        INACTIVE((byte)0x00),
        EMERGENCY_ACTIVE((byte)0x01),
        LEFT_ACTIVE((byte)0x02),
        RIGHT_ACTIVE((byte)0x03);

        public static State fromByte(byte value) throws CommandParseException {
            State[] allValues = State.values();

            for (int i = 0; i < allValues.length; i++) {
                State value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        State(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    State state;

    /**
     *
     * @return The flashers state.
     */
    public State getState() {
        return state;
    }

    FlashersState(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 9) throw new CommandParseException();
        state = State.fromByte(bytes[8]);
    }
}
