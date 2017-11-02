package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import static com.highmobility.autoapi.incoming.KeyfobPosition.Position;

/**
 * Created by ttiganik on 16/12/2016.
 */
public class KeyfobPosition extends FeatureState {
    Position position;

    public Position getPosition() {
        return position;
    }

    public KeyfobPosition(Position position) {
        super(Command.Identifier.KEYFOB_POSITION);
        this.position = position;
        bytes = getBytesWithOneByteLongFields(1);
        bytes[3] = position.getByte();
    }

    KeyfobPosition(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.KEYFOB_POSITION);
        if (bytes.length != 4) throw new CommandParseException();
        position = Position.fromByte(bytes[3]);
        this.bytes = bytes;
    }
}
