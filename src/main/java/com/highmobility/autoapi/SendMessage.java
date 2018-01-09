package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;

/**
 * Message to be sent by the smart device. This could be a response to a received message or input
 * through voice by the driver.
 */
public class SendMessage extends Command {
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x01);

    String recipientHandle;
    String text;

    /**
     * @return The recipient handle (e.g. phone number)
     */
    public String getRecipientHandle() {
        return recipientHandle;
    }

    /**
     * @return The text message
     */
    public String getText() {
        return text;
    }

    public SendMessage(byte[] bytes) throws CommandParseException {
        super(bytes);
        
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    try {
                        recipientHandle = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
                case 0x02:
                    try {
                        text = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
            }
        }
    }
}