package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Send an action to a previously received Notification message.
 */
public class NotificationAction extends Command {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x01);

    int actionIdentifier;

    /**
     * @return The identifier of selected action item
     */
    public int getActionIdentifier() {
        return actionIdentifier;
    }

    public NotificationAction(int actionIdentifier) {
        super(TYPE.addByte((byte) actionIdentifier), true);
        this.actionIdentifier = actionIdentifier;
    }

    public NotificationAction(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 4) throw new CommandParseException();
        actionIdentifier = Property.getUnsignedInt(bytes[3]);
    }
}
