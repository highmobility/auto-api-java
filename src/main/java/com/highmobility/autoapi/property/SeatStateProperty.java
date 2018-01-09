package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

import java.io.UnsupportedEncodingException;

public class SeatStateProperty extends Property {
    public enum Position {
        FRONT_LEFT((byte)0x00),
        FRONT_RIGHT((byte)0x01),
        REAR_LEFT((byte)0x02),
        REAR_RIGHT((byte)0x03),
        REAR_CENTER((byte)0x04);

        public static Position fromByte(byte byteValue) throws CommandParseException {
            Position[] values = Position.values();

            for (int i = 0; i < values.length; i++) {
                Position state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
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

    /**
     *
     * @return The position of the seat
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     * @return Person detected
     */
    public boolean isPersonDetected() {
        return personDetected;
    }

    /**
     *
     * @return Seatbelt fastened
     */
    public boolean isSeatbeltFastened() {
        return seatbeltFastened;
    }

    Position position;
    boolean personDetected;
    boolean seatbeltFastened;

    public SeatStateProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        position = Position.fromByte(bytes[3]);
        personDetected = Property.getBool(bytes[4]);
        seatbeltFastened = Property.getBool(bytes[4]);
    }

    public SeatStateProperty(byte identifier, Position position, boolean personDetected,
                             boolean seatbeltFastened) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = position.getByte();
        bytes[4] = Property.boolToByte(personDetected);
        bytes[5] = Property.boolToByte(seatbeltFastened);

    }
}