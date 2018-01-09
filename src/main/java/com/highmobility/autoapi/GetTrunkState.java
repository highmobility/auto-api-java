package com.highmobility.autoapi;

/**
 * Get the trunk state, if it's locked/unlocked and closed/open. The car will respond with the Trunk
 * State message.
 */
public class GetTrunkState extends Command {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x00);

    public GetTrunkState() {
        super(TYPE);
    }

    GetTrunkState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
