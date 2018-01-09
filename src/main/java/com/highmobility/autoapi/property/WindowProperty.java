package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class WindowProperty extends Property {
    WindowState state;

    public WindowState getState() {
        return state;
    }

    public WindowProperty(byte positionByte, byte stateByte) throws CommandParseException {
        this(new WindowState(WindowState.Position.fromByte(positionByte), WindowState.State.fromByte(stateByte)));
    }

    public WindowProperty(WindowState state) throws CommandParseException {
        super((byte) 0x01, 2);
        this.state = state;
        bytes[3] = state.getPosition().getByte();
        bytes[4] = state.getState().getByte();
    }

    public WindowProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        WindowState.Position position = WindowState.Position.fromByte(bytes[3]);
        WindowState.State windowState = WindowState.State.fromByte(bytes[4]);
        this.state = new WindowState(position, windowState);
    }
}
