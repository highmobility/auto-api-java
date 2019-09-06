package com.highmobility.autoapi.v2;

class GetCommand extends Command {
    GetCommand(Identifier identifier, byte[] propertyIdentifiers) {
        super(identifier, 3 + (propertyIdentifiers != null ? propertyIdentifiers.length : 0));

        set(0, identifier.getBytes());
        set(2, (byte) 0x00);
        set(3, propertyIdentifiers);

        type = Type.GET;
    }

    GetCommand(byte[] bytes) {
        super(bytes);
    }
}
