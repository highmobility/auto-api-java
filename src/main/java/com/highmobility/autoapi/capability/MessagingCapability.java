package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by root on 6/21/17.
 */

public class MessagingCapability extends FeatureCapability {
    AvailableCapability.Capability messageReceived;
    AvailableCapability.Capability sendMessage;

    public MessagingCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.MESSAGING);
        if (bytes.length != 5) throw new CommandParseException();
        messageReceived= AvailableCapability.Capability.fromByte(bytes[3]);
        sendMessage = AvailableCapability.Capability.fromByte(bytes[4]);
    }

    public AvailableCapability.Capability getMessageReceived() {
        return messageReceived;
    }

    public AvailableCapability.Capability getSendMessage() {
        return sendMessage;
    }
}
