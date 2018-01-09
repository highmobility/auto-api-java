package com.highmobility.autoapi;

/**
 * Get the current navigation destination. The car will respond with the Navi Destination message.
*/
 public class GetNaviDestination extends Command {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x00);

    public GetNaviDestination() {
        super(TYPE);
    }

    GetNaviDestination(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
