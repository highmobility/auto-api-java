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

    public MessagingCapability(AvailableCapability.Capability messageReceived, AvailableCapability.Capability sendMessage) {
        super(Command.Identifier.MESSAGING);
        this.messageReceived = messageReceived;
        this.sendMessage = sendMessage;
    }

    public AvailableCapability.Capability getMessageReceived() {
        return messageReceived;
    }

    public AvailableCapability.Capability getSendMessage() {
        return sendMessage;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(2);
        bytes[3] = messageReceived.getByte();
        bytes[4] = sendMessage.getByte();
        return bytes;
    }
}
