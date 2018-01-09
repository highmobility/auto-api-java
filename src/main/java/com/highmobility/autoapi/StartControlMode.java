package com.highmobility.autoapi;

/**
 * Attempt to start the control mode of the car. The result is not received by the ack but instead
 * sent through the evented Control Mode message with either the mode 0x02 Control Started or 0x03
 * Control Failed to Start
 */
public class StartControlMode extends Command {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x02);

    public StartControlMode() {
        super(TYPE.getIdentifierAndType(), true);
    }

    StartControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
