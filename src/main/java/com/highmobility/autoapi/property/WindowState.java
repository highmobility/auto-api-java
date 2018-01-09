package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class WindowState {
    public enum Position {
        FRONT_LEFT((byte)0x00),
        FRONT_RIGHT((byte)0x01),
        REAR_RIGHT((byte)0x02),
        REAR_LEFT((byte)0x03),
        HATCH((byte)0x04);

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
    }

    public enum State {
        CLOSED((byte)0x00),
        OPEN((byte)0x01);

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

    Position position;
    State state;


    public Position getPosition() {
        return position;
    }

    public State getState() {
        return state;
    }

    public WindowState(Position position, State state) {
        this.state = state;
        this.position = position;
    }

    public WindowState(byte positionByte, byte stateByte) throws CommandParseException {
        this (Position.fromByte(positionByte), State.fromByte(stateByte));
    }


    public WindowState(byte[] bytes) throws CommandParseException {
        if (bytes.length != 2) throw new CommandParseException();
        position = Position.fromByte(bytes[0]);
        state = State.fromByte(bytes[1]);
    }
}
