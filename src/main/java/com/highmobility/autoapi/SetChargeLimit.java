package com.highmobility.autoapi;

/**
 * Set the charge limit, to which point the car will charge itself. The result is sent through the
 * evented Charge State message.
 */
public class SetChargeLimit extends Command {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x03);

    /**
     * Get the set charge limit command bytes.
     *
     * @param percentage The charge limit percentage
     * @return The command bytes
     */
    public SetChargeLimit(float percentage) throws IllegalArgumentException {
        super(Identifier.CHARGING.getBytesWithType(TYPE, (byte) (percentage * 100)), true);
    }

    SetChargeLimit(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
