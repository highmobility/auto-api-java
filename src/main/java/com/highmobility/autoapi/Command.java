package com.highmobility.autoapi;

public class Command {
    byte[] bytes;
    Type type;

    public Type getType() {
        return type;
    }

    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param type The command value to compare this command with.
     * @return True if the command has the given commandType.
     */
    public boolean is(Type type) {
        return type.equals(this.type);
    }

    Command(byte[] bytes) throws CommandParseException {
        if (bytes == null || bytes.length == 0) return; // empty IncomingCommand
        if (bytes.length < 3) throw new CommandParseException();
        this.bytes = bytes;
        type = new Type(bytes[0], bytes[1], bytes[2]);
    }

    /**
     * This is used when we do not want to throw when we know the bytes are ok (we construct them
     * ourselves)
     */
    Command(byte[] bytes, boolean internalConstructor) {
        this.bytes = bytes;
    }

    Command(Type type) {
        this.bytes = type.getIdentifierAndType();
    }
}
