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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command to notify the car that a message has been received. Depending on the car system, it will
 * display or read it loud to the driver.
 */
public class MessageReceived extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x00);

    private static final byte IDENTIFIER_RECIPIENT = 0x01;
    private static final byte IDENTIFIER_MESSAGE = 0x02;

    private Property<String> handle = new Property(String.class, IDENTIFIER_RECIPIENT);
    private Property<String> message = new Property(String.class, IDENTIFIER_MESSAGE);
    ;

    /**
     * @return The sender handle (e.g. phone number).
     */
    @Nullable public Property<String> getSenderHandle() {
        return handle;
    }

    /**
     * @return The message content text.
     */
    public Property<String> getMessage() {
        return message;
    }

    /**
     * @param senderHandle The sender handle (e.g. phone number).
     * @param message      The message content text.
     */
    public MessageReceived(@Nullable String senderHandle, String message) {
        super(TYPE);

        List<Property> properties = new ArrayList<>();

        if (senderHandle != null) {
            this.handle.update(senderHandle);
            properties.add(this.handle);
        }

        this.message.update(message);
        properties.add(this.message);

        createBytes(properties);
    }

    MessageReceived(byte[] bytes) throws CommandParseException {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_RECIPIENT:
                        return handle.update(p);
                    case IDENTIFIER_MESSAGE:
                        return message.update(p);
                }
                return null;
            });
        }

        if (message.getValue() == null) throw new CommandParseException();
    }
}
