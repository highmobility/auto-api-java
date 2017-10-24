package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class RemoteControl extends FeatureState {
    public enum State {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        STARTED((byte)0x02);

        public static State fromByte(byte value) throws CommandParseException {
            State[] capabilities = State.values();

            for (int i = 0; i < capabilities.length; i++) {
                State capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        State(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    State state;

    public State getState() {
        return state;
    }

    public RemoteControl(State state) {
        super(Command.Identifier.REMOTE_CONTROL);
        this.state = state;

        bytes = getBytesWithOneByteLongFields(1);
        bytes[3] = state.getByte();
    }

    RemoteControl(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.REMOTE_CONTROL);

        if (bytes.length != 4) throw new CommandParseException();
        state = State.fromByte(bytes[3]);
        this.bytes = bytes;
    }
}