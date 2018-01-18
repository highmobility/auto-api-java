/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

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
