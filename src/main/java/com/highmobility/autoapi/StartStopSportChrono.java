package com.highmobility.autoapi;

/**
 * Start/stop the sports chronometer. The result is sent through the Chassis Settings message.
 */
public class StartStopSportChrono extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x03);

    public enum Command {
        START((byte) 0x00),
        STOP((byte) 0x01),
        RESET((byte) 0x02);

        public static Command fromByte(byte byteValue) throws CommandParseException {
            Command[] values = Command.values();

            for (int i = 0; i < values.length; i++) {
                Command state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Command(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    public StartStopSportChrono(Command command) {
        super(TYPE.addByte(command.getByte()), true);
    }

    StartStopSportChrono(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
