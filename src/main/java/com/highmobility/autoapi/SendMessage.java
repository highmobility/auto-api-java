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
import com.highmobility.autoapi.property.StringProperty;

import javax.annotation.Nullable;

/**
 * Command to tell the smart device to send a message. This could be a response to a received
 * message or input through voice by the driver.
 */
public class SendMessage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x01);

    private static final byte RECIPIENT_IDENTIFIER = 0x01;
    private static final byte MESSAGE_IDENTIFIER = 0x02;

    String recipientHandle;
    String message;

    /**
     * @return The recipient handle (e.g. phone number).
     */
    @Nullable public String getRecipientHandle() {
        return recipientHandle;
    }

    /**
     * @return The message content text.
     */
    @Nullable public String getMessage() {
        return message;
    }

    SendMessage(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case RECIPIENT_IDENTIFIER:
                        recipientHandle = Property.getString(p.getValueBytesArray());
                        return recipientHandle;
                    case MESSAGE_IDENTIFIER:
                        message = Property.getString(p.getValueBytesArray());
                        return message;
                }
                
                return null;
            });
        }
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }

    private SendMessage(Builder builder) {
        super(builder);
        message = builder.message;
        recipientHandle = builder.recipientHandle;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private String message;
        private String recipientHandle;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param message The message content text.
         * @return The builder.
         */
        public Builder setMessage(String message) {
            this.message = message;
            addProperty(new StringProperty(MESSAGE_IDENTIFIER, message));
            return this;
        }

        /**
         * @param recipientHandle The recipient handle (e.g. phone number).
         * @return The builder.
         */
        public Builder setRecipientHandle(String recipientHandle) {
            this.recipientHandle = recipientHandle;
            addProperty(new StringProperty(RECIPIENT_IDENTIFIER, recipientHandle));
            return this;
        }

        public SendMessage build() {
            return new SendMessage(this);
        }
    }
}