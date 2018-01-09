package com.highmobility.autoapi;

/**
 * Clear the Notification in either the car or device that has previously been sent, ignoring driver
 * feedback.
 */
public class ClearNotification extends Command {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x02);

    public ClearNotification() {
        super(TYPE);
    }

    public ClearNotification(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}