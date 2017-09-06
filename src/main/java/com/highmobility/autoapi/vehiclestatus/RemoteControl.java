package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class RemoteControl extends FeatureState {
    public enum State {
        UNAVAILABLE, AVAILABLE, STARTED;

        static State fromByte(byte value) throws CommandParseException {
            switch (value) {
                case 0x00: return UNAVAILABLE;
                case 0x01: return AVAILABLE;
                case 0x02: return STARTED;
            }

            throw new CommandParseException();
        }
    }

    State state;

    public State getState() {
        return state;
    }

    RemoteControl(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.REMOTE_CONTROL);

        if (bytes.length != 4) throw new CommandParseException();
        state = State.fromByte(bytes[3]);
    }
}
