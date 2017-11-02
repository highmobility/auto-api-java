package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 13/09/16.
 *
 * This is an evented message that is sent by the car every time the relative position of the
 * keyfob changes. This message is also sent when a Get Keyfob Position message is received by the
 * car.
 */
public class KeyfobPosition extends IncomingCommand {
    public enum Position {
        OUT_OF_RANGE((byte)0x00),
        OUTSIDE_DRIVER_SIDE((byte)0x01),
        OUTSIDE_IN_FRONT_OF_CAR((byte)0x02),
        OUTSIDE_PASSENGER_SIDE((byte)0x03),
        OUTSIDE_BEHIND_CAR((byte)0x04),
        INSIDE_CAR((byte)0x05);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] values = Position.values();

            for (int i = 0; i < values.length; i++) {
                Position possibleValue = values[i];
                if (possibleValue.getByte() == value) {
                    return possibleValue;
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

    Position position;

    /**
     *
     * @return Keyfob relative position to the car
     */
    public Position getPosition() {
        return position;
    }

    public KeyfobPosition(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 4) throw new CommandParseException();

        position = Position.fromByte(bytes[3]);
    }
}
