package com.highmobility.autoapi.v2;

class SetCommand extends Command {
    SetCommand(Identifier identifier) {
        super(identifier, 3);
        setBaseBytes();
    }

    SetCommand(Identifier identifier, byte[] propertyIdentifiers) {
        super(identifier, 3 + (propertyIdentifiers != null ? propertyIdentifiers.length : 0));
        setBaseBytes();
        set(3, propertyIdentifiers);
    }

    SetCommand(byte[] bytes) {
        super(bytes);
    }

    private void setBaseBytes() {
        set(0, identifier.getBytes());
        set(2, (byte) 0x00);
        type = Type.GET;

    }

}
