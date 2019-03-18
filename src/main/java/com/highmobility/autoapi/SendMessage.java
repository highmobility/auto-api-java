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

import javax.annotation.Nullable;

/**
 * Command to tell the smart device to send a message. This could be a response to a received
 * message or input through voice by the driver.
 */
public class SendMessage extends Command {
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x01);

    private static final byte RECIPIENT_IDENTIFIER = 0x01;
    private static final byte MESSAGE_IDENTIFIER = 0x02;

    Property<String> recipientHandle = new Property(String.class, RECIPIENT_IDENTIFIER);
    Property<String> message = new Property(String.class, MESSAGE_IDENTIFIER);

    /**
     * @return The recipient handle (e.g. phone number).
     */
    public Property<String> getRecipientHandle() {
        return recipientHandle;
    }

    /**
     * @return The message content text.
     */
    public Property<String> getMessage() {
        return message;
    }

    SendMessage(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case RECIPIENT_IDENTIFIER:
                        return recipientHandle.update(p);
                    case MESSAGE_IDENTIFIER:
                        return message.update(p);
                }
                return null;
            });
        }
    }

    private SendMessage(Builder builder) {
        super(builder);
        message = builder.message;
        recipientHandle = builder.recipientHandle;
    }

    public static final class Builder extends Command.Builder {
        private Property<String> message;
        private Property<String> recipientHandle;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param message The message content text.
         * @return The builder.
         */
        public Builder setMessage(Property<String> message) {
            this.message = message;
            addProperty(message.setIdentifier(MESSAGE_IDENTIFIER));
            return this;
        }

        /**
         * @param recipientHandle The recipient handle (e.g. phone number).
         * @return The builder.
         */
        public Builder setRecipientHandle(Property<String> recipientHandle) {
            this.recipientHandle = recipientHandle;
            addProperty(recipientHandle.setIdentifier(RECIPIENT_IDENTIFIER));
            return this;
        }

        public SendMessage build() {
            return new SendMessage(this);
        }
    }
}