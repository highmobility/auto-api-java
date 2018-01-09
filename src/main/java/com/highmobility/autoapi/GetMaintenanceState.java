package com.highmobility.autoapi;

/**
 * Get the vehicle status. The car will respond with the Vehicle Status message.
 */
public class GetMaintenanceState extends Command {
    public static final Type TYPE = new Type(Identifier.MAINTENANCE, 0x00);

    public GetMaintenanceState() {
        super(TYPE);
    }

    GetMaintenanceState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
