package com.highmobility.autoapi;

/**
 * Stop the control mode of the car. The result is not received by the ack but instead sent through
 * the evented Control Mode message with the mode 0x05 Control Ended.
 */
public class StopControlMode extends Command {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x03);

    public StopControlMode() {
        super(TYPE.getIdentifierAndType(), true);
    }

    StopControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
