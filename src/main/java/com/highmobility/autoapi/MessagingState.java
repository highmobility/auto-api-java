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

/**
 * The messaging state
 */
public class MessagingState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.MESSAGING;

    public static final byte IDENTIFIER_TEXT = 0x01;
    public static final byte IDENTIFIER_HANDLE = 0x02;

    Property<String> text = new Property(String.class, IDENTIFIER_TEXT);
    Property<String> handle = new Property(String.class, IDENTIFIER_HANDLE);

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @return The optional handle of message
     */
    public Property<String> getHandle() {
        return handle;
    }

    MessagingState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_TEXT: return text.update(p);
                    case IDENTIFIER_HANDLE: return handle.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private MessagingState(Builder builder) {
        super(builder);

        text = builder.text;
        handle = builder.handle;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<String> text;
        private Property<String> handle;

        public Builder() {
            super(IDENTIFIER);
        }

        public MessagingState build() {
            return new MessagingState(this);
        }

        /**
         * @param text The text
         * @return The builder
         */
        public Builder setText(Property<String> text) {
            this.text = text.setIdentifier(IDENTIFIER_TEXT);
            addProperty(this.text);
            return this;
        }
        
        /**
         * @param handle The optional handle of message
         * @return The builder
         */
        public Builder setHandle(Property<String> handle) {
            this.handle = handle.setIdentifier(IDENTIFIER_HANDLE);
            addProperty(this.handle);
            return this;
        }
    }
}